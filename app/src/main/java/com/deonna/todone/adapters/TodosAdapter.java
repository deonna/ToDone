package com.deonna.todone.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.deonna.todone.R;
import com.deonna.todone.models.Todo;
import com.deonna.todone.utils.Utilities;

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

        Utilities.updateDueDateUi((TextView) convertView.findViewById(R.id.tvDueDate), todo);

        Utilities.initializeCheckboxListener(convertView, todo);
        Utilities.initializePriorityListeners(convertView, todo, R.id.ivLowPriority, R.id
                .ivMediumPriority, R.id.ivHighPriority);

        return convertView;
    }
}
