package com.deonna.todone.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import com.codetroopers.betterpickers.OnDialogDismissListener;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.deonna.todone.constants.Constants;
import com.deonna.todone.interfaces.DatePickerFragmentListener;
import com.deonna.todone.models.Todo;

import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment {

    public static final String TAG_DATE_PICKER = "fragment_date_picker";

    private CalendarDatePickerDialogFragment datePickerDialog;

    private Todo currentTodo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        currentTodo = args.getParcelable(Constants.CURRENT_TODO);

        setCancelable(false);

        datePickerDialog = new CalendarDatePickerDialogFragment();

        datePickerDialog.setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
            @Override
            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);

                DatePickerFragmentListener listener = (DatePickerFragmentListener) getFragmentManager()
                        .getFragments().get(0);

                String dueDateText = Todo.getFormattedDate(new Date(calendar.getTimeInMillis()));
                listener.onFinishSettingDueDate(dueDateText);

                dismiss();
            }
        });

        datePickerDialog.setOnDismissListener(new OnDialogDismissListener() {
            @Override
            public void onDialogDismiss(DialogInterface dialoginterface) {
                dismiss();
            }
        });

        datePickerDialog.show(getFragmentManager(), TAG_DATE_PICKER);
    }
}
