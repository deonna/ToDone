package com.deonna.todone;

import android.app.Dialog;
import android.content.ReceiverCallNotAllowedException;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private Todo currentTodo;

    public interface DatePickerFragmentListener {
        void onFinishSettingDueDate(String newDate);
    }

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
        currentTodo.updateInDataSource();

        DatePickerFragmentListener listener = (DatePickerFragmentListener) getFragmentManager()
                .getFragments().get(0);

        listener.onFinishSettingDueDate(currentTodo.getDueDateText());
    }
}
