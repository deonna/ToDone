package com.deonna.todone;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TodosAdapter extends ArrayAdapter<Todo> {

    public TodosAdapter(Context context, ArrayList<Todo> todos) {
        super(context, 0, todos);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Todo todo = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todo, parent,
                    false);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);

        tvName.setText(todo.getName());

        initializeCheckboxListener(convertView, todo);
        Utilities.initializePriorityListeners(convertView, todo, R.id.ivLowPriority, R.id
                .ivMediumPriority, R.id.ivHighPriority);

        return convertView;
    }

    private void initializeCheckboxListener(View view, Todo todo) {

        final CheckBox cbIsCompleted = (CheckBox) view.findViewById(R.id.cbIsCompleted);
        final Todo currentTodo = todo;

        cbIsCompleted.setChecked(currentTodo.getIsCompleted());

        cbIsCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                currentTodo.setIsCompleted(isChecked);
                currentTodo.updateInDataSource();
            }
        });
    }
}
