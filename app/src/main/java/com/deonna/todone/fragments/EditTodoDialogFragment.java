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

    @BindView(R.id.etEditedItem) EditText etEditedItem;
    @BindView(R.id.ivSave) ImageView ivSave;
    @BindView(R.id.ivSetDueDate) ImageView ivSetDueDate;

    @BindView(R.id.ivLowPriorityDialog) ImageView ivLowPriorityDialog;
    @BindView(R.id.ivMediumPriorityDialog) ImageView ivMediumPriorityDialog;
    @BindView(R.id.ivHighPriorityDialog) ImageView ivHighPriorityDialog;

    @BindView(R.id.tvDueDateDialog) TextView tvDueDateDialog;

    private Todo currentTodo;
    private int position;

    private View view;
    private Unbinder unbinder;

    public EditTodoDialogFragment() {
    }

    @Override
    public void onFinishSettingDueDate(String newDate) {
        tvDueDateDialog.setText(newDate);
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

        ButterKnife.bind(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_name, container);
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
        position = args.getInt(Constants.POSITION, 0);

        getDialog().setTitle(TITLE);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        initializeEditField();

        tvDueDateDialog.setText(currentTodo.getDueDateText());

        Utilities.updatePriorityUi(
                ivLowPriorityDialog,
                ivMediumPriorityDialog,
                ivHighPriorityDialog,
                currentTodo
        );

        setCancelable(false);
    }

    private void initializeEditField() {
        etEditedItem.setText(currentTodo.getName());
        etEditedItem.setSelection(etEditedItem.getText().length());

        etEditedItem.requestFocus();
    }

    @OnClick(R.id.ivSave)
    public void saveItem() {

        EditTodoDialogListener listener = (EditTodoDialogListener) getActivity();
        String newName = etEditedItem.getText().toString().trim();

        listener.onFinishEditDialog(newName, position);

        dismiss();
    }

    @OnClick(R.id.ivSetDueDate)
    public void openDatePicker(View view) {

        Bundle args = new Bundle();
        args.putSerializable(Constants.CURRENT_TODO, currentTodo);

        final DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);

        DatePickerFragment newFragment = fragment;
        newFragment.show(getFragmentManager(), "datePicker");
    }

    @OnClick(R.id.ivLowPriorityDialog)
    public void changeToMediumPriority() {

        changePriority(R.id.ivLowPriorityDialog, R.id.ivMediumPriorityDialog, Priority.MEDIUM);
    }

    @OnClick(R.id.ivMediumPriorityDialog)
    public void changeToHighPriority() {

        changePriority(R.id.ivMediumPriorityDialog, R.id.ivHighPriorityDialog, Priority.HIGH);
    }

    @OnClick(R.id.ivHighPriorityDialog)
    public void changeToLowPriority() {

        changePriority(R.id.ivHighPriorityDialog,  R.id.ivLowPriorityDialog, Priority.LOW);
    }

    private void changePriority(int oldPriority, int newPriority, Priority currentPriority) {

        ImageView ivOldPriority = ButterKnife.findById(view, oldPriority);
        ImageView ivNewPriority = ButterKnife.findById(view, newPriority);

        ivOldPriority.setVisibility(View.GONE);
        ivNewPriority.setVisibility(View.VISIBLE);

        currentTodo.setPriority(currentPriority);
        currentTodo.updateInDataSource();
    }
}
