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
                currentTodo
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
            currentTodo.updateInDataSource();
        }

        @OnClick(R.id.ivLowPriority)
        public void changeToMediumPriority() {


            ImageView ivLowPriority = ButterKnife.findById(view, R.id.ivLowPriority);
            ImageView ivMediumPriority = ButterKnife.findById(view, R.id.ivMediumPriority);

            changePriority(ivLowPriority, ivMediumPriority);
            currentTodo.setPriority(Priority.MEDIUM);
            currentTodo.updateInDataSource();
        }

        @OnClick(R.id.ivMediumPriority)
        public void changeToHighPriority() {

            ImageView ivMediumPriority = ButterKnife.findById(view, R.id.ivMediumPriority);
            ImageView ivHighPriority = ButterKnife.findById(view, R.id.ivHighPriority);

            changePriority(ivMediumPriority,ivHighPriority);
            currentTodo.setPriority(Priority.HIGH);
            currentTodo.updateInDataSource();
        }

        @OnClick(R.id.ivHighPriority)
        public void changeToLowPriority() {

            ImageView ivLowPriority = ButterKnife.findById(view, R.id.ivLowPriority);
            ImageView ivHighPriority = ButterKnife.findById(view, R.id.ivHighPriority);

            changePriority(ivHighPriority, ivLowPriority);
            currentTodo.setPriority(Priority.LOW);
            currentTodo.updateInDataSource();
        }

        private void changePriority(ImageView ivOldPriority, ImageView ivNewPriority) {

            ivOldPriority.setVisibility(View.GONE);
            ivNewPriority.setVisibility(View.VISIBLE);
        }
    }
}
