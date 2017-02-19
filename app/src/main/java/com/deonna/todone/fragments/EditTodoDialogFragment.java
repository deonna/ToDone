package com.deonna.todone.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.deonna.todone.constants.Constants;
import com.deonna.todone.R;
import com.deonna.todone.models.Todo;
import com.deonna.todone.utils.Utilities;

public class EditTodoDialogFragment extends DialogFragment implements DatePickerFragment.DatePickerFragmentListener {

    public static final String TITLE = "Edit Todo";

    private EditText etEditedItem;
    private ImageView ivSave;
    private ImageView ivSetDueDate;

    private TextView tvDueDateDialog;

    private Todo currentTodo;
    private int position;

    public EditTodoDialogFragment() {}

    @Override
    public void onFinishSettingDueDate(String newDate) {
        tvDueDateDialog.setText(newDate);
    }

    public interface EditTodoDialogListener {
        void onFinishEditDialog(String name, int position);
    }

    public static EditTodoDialogFragment newInstance(Todo todo, int position) {
        EditTodoDialogFragment fragment = new EditTodoDialogFragment();

        Bundle args = new Bundle();
        args.putSerializable(Constants.CURRENT_TODO, todo);
        args.putInt(Constants.POSITION, position);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_name, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();

        currentTodo = (Todo) args.getSerializable(Constants.CURRENT_TODO);
        position = args.getInt(Constants.POSITION, 0);

        getDialog().setTitle(TITLE);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        tvDueDateDialog = (TextView) view.findViewById(R.id.tvDueDateDialog);

        initializeEditField(view);

        //Utilities.updateDueDateUi((TextView) view.findViewById(R.id.tvDueDateDialog), currentTodo);
        TextView tvDueDateDialog = (TextView) view.findViewById(R.id.tvDueDateDialog);
        tvDueDateDialog.setText(currentTodo.getDueDateText());

        Utilities.initializePriorityListeners(view, currentTodo, R.id.ivLowPriorityDialog, R.id
                .ivMediumPriorityDialog, R.id.ivHighPriorityDialog);

        initializeSaveButton(view);
        initializeSetDueDateButton(view);

        setCancelable(false);
    }

    private void initializeEditField(View view) {
        etEditedItem = (EditText) view.findViewById(R.id.etEditedItem);
        etEditedItem.setText(currentTodo.getName());
        etEditedItem.setSelection(etEditedItem.getText().length());

        etEditedItem.requestFocus();
    }

    private void initializeSaveButton(View view) {
        ivSave = (ImageView) view.findViewById(R.id.ivSave);

        ivSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                EditTodoDialogListener listener = (EditTodoDialogListener) getActivity();
                String newName = etEditedItem.getText().toString().trim();

                listener.onFinishEditDialog(newName, position);

                Utilities.hideSoftKeyboard(view, getActivity());

                dismiss();
            }
        });
    }

    private void initializeSetDueDateButton(View view) {
        ivSetDueDate = (ImageView) view.findViewById(R.id.ivSetDueDate);

        Bundle args = new Bundle();
        args.putSerializable(Constants.CURRENT_TODO, currentTodo);

        final DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);

        ivSetDueDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                DatePickerFragment newFragment = fragment;
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });
    }
}
