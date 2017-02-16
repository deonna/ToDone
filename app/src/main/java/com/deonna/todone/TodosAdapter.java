package com.deonna.todone;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

        Utilities.initializePriorityListeners(convertView, R.id.ivLowPriority, R.id.ivMediumPriority, R.id.ivHighPriority);

        return convertView;
    }

    private void changePriority(ImageView ivOldPriority, ImageView ivNewPriority) {

        ivOldPriority.setVisibility(View.GONE);
        ivNewPriority.setVisibility(View.VISIBLE);
    }

    private void initializePriorityListeners(View convertView) {

        final ImageView ivLowPriority = (ImageView) convertView.findViewById(R.id.ivLowPriority);
        final ImageView ivMediumPriority = (ImageView) convertView.findViewById(R.id
                .ivMediumPriority);
        final ImageView ivHighPriority = (ImageView) convertView.findViewById(R.id.ivHighPriority);

        ivLowPriority.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                changePriority(ivLowPriority, ivMediumPriority);
            }
        });

        ivMediumPriority.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                changePriority(ivMediumPriority,ivHighPriority);
            }
        });

        ivHighPriority.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                changePriority(ivHighPriority, ivLowPriority);
            }
        });
    }
}
