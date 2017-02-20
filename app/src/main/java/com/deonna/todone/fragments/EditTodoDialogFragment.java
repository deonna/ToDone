package com.deonna.todone.fragments;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.deonna.todone.constants.Constants;
import com.deonna.todone.R;
import com.deonna.todone.constants.Priority;
import com.deonna.todone.interfaces.DatePickerFragmentListener;
import com.deonna.todone.interfaces.EditTodoDialogListener;
import com.deonna.todone.models.Todo;
import com.deonna.todone.utils.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class EditTodoDialogFragment extends DialogFragment implements DatePickerFragmentListener {

    public static final String TITLE = "Edit Todo";

    @BindView(R.id.etTodoName) EditText etTodoName;

    @BindView(R.id.ivSetDueDate) ImageView ivSetDueDate;
    @BindView(R.id.tvShowDueDate) TextView tvShowDueDate;
    @BindView(R.id.etNote) EditText etNote;

    @BindView(R.id.ivLowPriorityDialog) ImageView ivLowPriorityDialog;
    @BindView(R.id.ivMediumPriorityDialog) ImageView ivMediumPriorityDialog;
    @BindView(R.id.ivHighPriorityDialog) ImageView ivHighPriorityDialog;

    @BindView(R.id.cbIsCompleted) CheckBox cbIsCompleted;

    @BindView(R.id.btnSave) Button btnSave;
    @BindView(R.id.btnCancel) Button btnCancel;

    private Todo currentTodo;
    private Priority currentPriority;

    private View view;
    private Unbinder unbinder;

    public EditTodoDialogFragment() {
    }

    @Override
    public void onFinishSettingDueDate(String newDate) {
        tvShowDueDate.setText(newDate);
    }


    public static EditTodoDialogFragment newInstance(Todo todo) {

        EditTodoDialogFragment fragment = new EditTodoDialogFragment();

        Bundle args = new Bundle();
        args.putParcelable(Constants.CURRENT_TODO, todo);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ButterKnife.bind(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_todo, container);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        this.view = view;

        Bundle args = getArguments();
        currentTodo = args.getParcelable(Constants.CURRENT_TODO);

        getDialog().setTitle(TITLE);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        if (!currentTodo.getDueDateText().isEmpty()) {
            tvShowDueDate.setText(currentTodo.getDueDateText());
        }

        if (!currentTodo.getNote().isEmpty()) {
            initializeEditField(etNote, currentTodo.getNote());
        }

        initializeEditField(etTodoName, currentTodo.getName());

        currentPriority = currentTodo.getPriority();

        Utilities.updatePriorityUi(
                ivLowPriorityDialog,
                ivMediumPriorityDialog,
                ivHighPriorityDialog,
                currentPriority
        );

        setCancelable(false);
    }

    private void initializeEditField(EditText etField, String text) {
        etField.setText(text);
        etField.setSelection(text.length());

        etField.requestFocus();
    }

    @OnClick(R.id.btnSave)
    public void saveItem(View view) {

        currentTodo.setName(etTodoName.getText().toString().trim());
        currentTodo.setPriority(currentPriority);
        currentTodo.setNote(etNote.getText().toString().trim());
        currentTodo.setIsCompleted(cbIsCompleted.isChecked());

        Todo.updateInDataSource(currentTodo);

        EditTodoDialogListener listener = (EditTodoDialogListener) getActivity();
        listener.onFinishEditDialog();

        Utilities.hideSoftKeyboard(view, getActivity());

        dismiss();
    }

    @OnClick(R.id.btnCancel)
    public void cancel() {

        dismiss();
    }

    @OnClick(R.id.ivSetDueDate)
    public void openDatePicker() {

        Bundle args = new Bundle();
        args.putParcelable(Constants.CURRENT_TODO, currentTodo);

        final DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);

        fragment.show(getFragmentManager(), DatePickerFragment.TAG_DATE_PICKER);
    }


    @OnClick(R.id.ivLowPriorityDialog)
    public void changeUiToMediumPriority() {

        currentPriority = Priority.MEDIUM;
        changeUiPriority(R.id.ivLowPriorityDialog, R.id.ivMediumPriorityDialog);
    }

    @OnClick(R.id.ivMediumPriorityDialog)
    public void changeUiToHighPriority() {

        currentPriority = Priority.HIGH;
        changeUiPriority(R.id.ivMediumPriorityDialog, R.id.ivHighPriorityDialog);
    }

    @OnClick(R.id.ivHighPriorityDialog)
    public void changeUiToLowPriority() {

        currentPriority = Priority.LOW;
        changeUiPriority(R.id.ivHighPriorityDialog,  R.id.ivLowPriorityDialog);
    }

    private void changeUiPriority(int oldPriority, int newPriority) {

        ImageView ivOldPriority = ButterKnife.findById(view, oldPriority);
        ImageView ivNewPriority = ButterKnife.findById(view, newPriority);

        ivOldPriority.setVisibility(View.GONE);
        ivNewPriority.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.cbIsCompleted)
    public void completeTodo() {
        boolean isChecked = cbIsCompleted.isChecked();

        updateCheckedUi(isChecked);
    }

    private void updateCheckedUi(boolean isChecked) {

        if (isChecked) {
            etTodoName.setPaintFlags(etTodoName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            etTodoName.setPaintFlags(etTodoName.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
}
