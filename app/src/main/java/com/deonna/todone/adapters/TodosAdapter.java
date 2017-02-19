package com.deonna.todone.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.deonna.todone.R;
import com.deonna.todone.constants.Priority;
import com.deonna.todone.models.Todo;
import com.deonna.todone.utils.Utilities;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class TodosAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Todo> todos;
    private Todo currentTodo;

    public TodosAdapter(Context context, ArrayList<Todo> todos) {

        this.context = context;
        this.todos = todos;
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

            currentTodo.setIsCompleted(cbIsCompleted.isChecked());
            Todo.updateInDataSource(currentTodo);
        }
    }
}
