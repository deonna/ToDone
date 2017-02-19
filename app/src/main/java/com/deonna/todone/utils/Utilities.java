package com.deonna.todone.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.deonna.todone.constants.Priority;
import com.deonna.todone.R;
import com.deonna.todone.models.Todo;

public class Utilities {

    public static void hideSoftKeyboard(View view, Activity activity) {

        final InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void updatePriorityUi(ImageView ivLowPriority, ImageView ivMediumPriority,
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
}
