package com.deonna.todone;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

public class Utilities {

    public static void hideSoftKeyboard(View view, Activity activity) {

        final InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void initializeCheckboxListener(View view, Todo todo) {

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

    public static void initializePriorityListeners(View convertView, Todo todo, int lowPriorityId,
                                                   int mediumPriority, int highPriority) {

        final ImageView ivLowPriority = (ImageView) convertView.findViewById(lowPriorityId);
        final ImageView ivMediumPriority = (ImageView) convertView.findViewById(mediumPriority);
        final ImageView ivHighPriority = (ImageView) convertView.findViewById(highPriority);

        final Todo currentTodo = todo;

        updatePriorityUi(ivLowPriority, ivMediumPriority, ivHighPriority, todo);

        ivLowPriority.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                changePriority(ivLowPriority, ivMediumPriority);
                currentTodo.setPriority(Priority.MEDIUM);
                currentTodo.updateInDataSource();
            }
        });

        ivMediumPriority.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                changePriority(ivMediumPriority,ivHighPriority);
                currentTodo.setPriority(Priority.HIGH);
                currentTodo.updateInDataSource();
            }
        });

        ivHighPriority.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                changePriority(ivHighPriority, ivLowPriority);
                currentTodo.setPriority(Priority.LOW);
                currentTodo.updateInDataSource();
            }
        });
    }

    private static void updatePriorityUi(ImageView ivLowPriority, ImageView ivMediumPriority,
                                         ImageView ivHighPriority, Todo todo) {

        switch (todo.getPriority()) {
            case LOW:
                ivLowPriority.setVisibility(View.VISIBLE);
                ivMediumPriority.setVisibility(View.GONE);
                ivHighPriority.setVisibility(View.GONE);
                break;

            case MEDIUM:
                ivLowPriority.setVisibility(View.GONE);
                ivMediumPriority.setVisibility(View.VISIBLE);
                ivHighPriority.setVisibility(View.GONE);
                break;

            case HIGH:
                ivLowPriority.setVisibility(View.GONE);
                ivMediumPriority.setVisibility(View.GONE);
                ivHighPriority.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }
    
    private static void changePriority(ImageView ivOldPriority, ImageView ivNewPriority) {

        ivOldPriority.setVisibility(View.GONE);
        ivNewPriority.setVisibility(View.VISIBLE);
    }
}
