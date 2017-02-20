package com.deonna.todone.fragments;

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
        args.putSerializable(Constants.CURRENT_TODO, todo);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogTheme);

        ButterKnife.bind(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_todo2, container);
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

        currentTodo = (Todo) args.getSerializable(Constants.CURRENT_TODO);

        getDialog().setTitle(TITLE);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        initializeEditField();

        if (!currentTodo.getDueDateText().isEmpty()) {
            tvShowDueDate.setText(currentTodo.getDueDateText());
        }

        currentPriority = currentTodo.getPriority();

        Utilities.updatePriorityUi(
                ivLowPriorityDialog,
                ivMediumPriorityDialog,
                ivHighPriorityDialog,
                currentPriority
        );

        setCancelable(false);
    }

    private void initializeEditField() {
        etTodoName.setText(currentTodo.getName());
        etTodoName.setSelection(etTodoName.getText().length());

        etTodoName.requestFocus();
    }

    @OnClick(R.id.btnSave)
    public void saveItem(View view) {

        String newName = etTodoName.getText().toString().trim();

        currentTodo.setName(newName);
        currentTodo.setPriority(currentPriority);

        Todo.updateInDataSource(currentTodo);

        EditTodoDialogListener listener = (EditTodoDialogListener) getActivity();
        listener.onFinishEditDialog();

        Utilities.hideSoftKeyboard(view, getActivity());

        dismiss();
    }

    @OnClick(R.id.ivSetDueDate)
    public void openDatePicker() {

        Bundle args = new Bundle();
        args.putSerializable(Constants.CURRENT_TODO, currentTodo);

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
}
