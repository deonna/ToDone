package com.deonna.todone.adapters;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.deonna.todone.R;
import com.deonna.todone.models.ContextHolder;
import com.deonna.todone.models.Todo;
import com.deonna.todone.models.Todos;
import com.deonna.todone.utils.Utilities;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

public class TodosAdapter extends BaseAdapter implements Filterable, Action1<Todos> {

    public static final String COMPLETE = "Complete";
    public static final String INCOMPLETE = "Incomplete";
    public static final String ALL = "All";

    private Todos todos;
    private ArrayList<Integer> hiddenTodoIndices;
    private Todo currentTodo;

    public TodosAdapter(Todos todos) {

        this.todos = todos;

        hiddenTodoIndices = new ArrayList<>();
        this.todos.sort();
    }

    @Override
    public int getCount() {
        return todos.size() - hiddenTodoIndices.size();
    }

    @Override
    public Object getItem(int position) {

        position = getCorrectPosition(position);

        return todos.get(position);
    }

    public int getCorrectPosition(int position) {

        for(Integer hiddenIndex : hiddenTodoIndices) {
            if(hiddenIndex <= position) {
                position = position + 1;
            }
        }

        return position;
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
            convertView = LayoutInflater.from(ContextHolder.getContext()).inflate(R.layout.item_todo, null);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        currentTodo = todos.get(getCorrectPosition(position));

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

    @Override
    public void notifyDataSetChanged() {

        todos.sort();
        super.notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {

        final Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();

                hiddenTodoIndices.clear();

                ArrayList<Integer> filteredTodoIndices = new ArrayList<>();

                for (int i = 0; i < todos.size(); i++) {
                    Todo todo = todos.get(i);
                    boolean isCompleted = todo.getIsCompleted();

                    if (!(constraint.equals(COMPLETE) && isCompleted) &&
                            !(constraint.equals(INCOMPLETE) && !isCompleted) &&
                                    !(constraint.equals(ALL))
                            ) {
                        filteredTodoIndices.add(i);
                    }
                }

                results.count = filteredTodoIndices.size();
                results.values = filteredTodoIndices;

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                hiddenTodoIndices = (ArrayList<Integer>) results.values;
                notifyDataSetChanged();
            }
        };

        return filter;
    }

    @Override
    public void call(Todos todos) {

        this.todos = todos;
        notifyDataSetChanged();
    }


    public class ViewHolder {

        @BindView(R.id.cbIsCompleted) CheckBox cbIsCompleted;

        @BindView(R.id.tvName) TextView tvName;
        @BindView(R.id.tvDueDate) TextView tvDueDate;

        @BindView(R.id.ivLowPriority) ImageView ivLowPriority;
        @BindView(R.id.ivMediumPriority) ImageView ivMediumPriority;
        @BindView(R.id.ivHighPriority) ImageView ivHighPriority;

        final View view;

        public ViewHolder(View view) {

            this.view = view;
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.cbIsCompleted)
        public void completeTodo() {

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
