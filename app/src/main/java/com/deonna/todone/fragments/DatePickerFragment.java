package com.deonna.todone.fragments;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;

import com.deonna.todone.interfaces.DatePickerFragmentListener;
import com.deonna.todone.models.Todo;
import com.deonna.todone.constants.Constants;

import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private Todo currentTodo;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle args = getArguments();
        currentTodo = (Todo) args.getSerializable(Constants.CURRENT_TODO);

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        setCancelable(false);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        currentTodo.setDueDate(new Date(calendar.getTimeInMillis()));
        Todo.updateInDataSource(currentTodo);

        DatePickerFragmentListener listener = (DatePickerFragmentListener) getFragmentManager()
                .getFragments().get(0);

        listener.onFinishSettingDueDate(currentTodo.getDueDateText());
    }
}
