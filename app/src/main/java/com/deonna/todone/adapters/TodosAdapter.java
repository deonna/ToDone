package com.deonna.todone.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.deonna.todone.R;
import com.deonna.todone.constants.Priority;
import com.deonna.todone.models.Todo;
import com.deonna.todone.utils.Utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class TodosAdapter extends BaseAdapter implements Filterable {

    public static final String COMPLETE = "complete";
    public static final String INCOMPLETE = "incomplete";
    public static final String ALL = "all";

    private Context context;
    private ArrayList<Todo> todos;
    private ArrayList<Todo> initialTodos;
    private Todo currentTodo;

    public TodosAdapter(Context context, ArrayList<Todo> todos) {

        this.context = context;
        this.todos = todos;
        this.initialTodos = new ArrayList<>(todos);

        sort();
    }

    @Override
    public int getCount() {
        return todos.size();
    }

    @Override
    public Object getItem(int i) {
        return todos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_todo, null);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        currentTodo = todos.get(position);

        holder.tvName.setText(currentTodo.getName());
        holder.updateCheckedUi(currentTodo.getIsCompleted());

        holder.tvDueDate.setText(currentTodo.getDueDateText());
        holder.cbIsCompleted.setChecked(currentTodo.getIsCompleted());

        Utilities.updatePriorityUi(
                holder.ivLowPriority,
                holder.ivMediumPriority,
                holder.ivHighPriority,
                currentTodo.getPriority()
        );

        return convertView;
    }

    private void sort() {

        Collections.sort(todos, new Comparator<Todo>() {
            @Override
            public int compare(Todo todo1, Todo todo2) {

                int priority1 = todo1.getPriority().getValue();
                int priority2 = todo2.getPriority().getValue();

                if (priority1 < priority2) {
                    return 1;
                } else if (priority1 > priority2) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
    }

    @Override
    public void notifyDataSetChanged() {

        sort();
        super.notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {

        final Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();
                ArrayList<Todo> filteredTodos = new ArrayList<>();

                constraint = constraint.toString().toLowerCase();

                for (Todo todo : initialTodos) {

                    boolean isCompleted = todo.getIsCompleted();

                    if (constraint.equals(COMPLETE) && isCompleted) {
                        filteredTodos.add(todo);
                    } else if (constraint.equals(INCOMPLETE) && !isCompleted) {
                        filteredTodos.add(todo);
                    } else if (constraint.equals(ALL)) {
                        filteredTodos.add(todo);
                    }
                }

                results.count = filteredTodos.size();
                results.values = filteredTodos;

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                todos = (ArrayList<Todo>) results.values;
                notifyDataSetChanged();
            }
        };

        return filter;
    }


    public class ViewHolder {

        @BindView(R.id.cbIsCompleted) CheckBox cbIsCompleted;

        @BindView(R.id.tvName) TextView tvName;
        @BindView(R.id.tvDueDate) TextView tvDueDate;

        @BindView(R.id.ivLowPriority) ImageView ivLowPriority;
        @BindView(R.id.ivMediumPriority) ImageView ivMediumPriority;
        @BindView(R.id.ivHighPriority) ImageView ivHighPriority;

        View view;

        public ViewHolder(View view) {

            this.view = view;
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.cbIsCompleted)
        public void completeTodo(View view) {

            boolean isChecked = cbIsCompleted.isChecked();

            currentTodo.setIsCompleted(isChecked);
            Todo.updateInDataSource(currentTodo);

            updateCheckedUi(isChecked);
        }

        private void updateCheckedUi(boolean isChecked) {

            if (isChecked) {
                tvName.setPaintFlags(tvName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                tvName.setPaintFlags(tvName.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }
    }
}
