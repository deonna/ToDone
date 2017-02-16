package com.deonna.todone;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

        initializeDueDate(convertView, todo);

        Utilities.initializeCheckboxListener(convertView, todo);
        Utilities.initializePriorityListeners(convertView, todo, R.id.ivLowPriority, R.id
                .ivMediumPriority, R.id.ivHighPriority);

        return convertView;
    }

    private void initializeDueDate(View view,  Todo todo) {
        TextView tvDueDate = (TextView) view.findViewById(R.id.tvDueDate);

        if (todo.getDueDate() == null) {
            tvDueDate.setVisibility(View.GONE);
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("MMM d, yyyy");
            tvDueDate.setText(formatter.format(todo.getDueDate()));
        }
    }
}
