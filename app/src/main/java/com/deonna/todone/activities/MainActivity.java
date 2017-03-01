package com.deonna.todone.activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.deonna.todone.fragments.EditTodoDialogFragment;
import com.deonna.todone.R;
import com.deonna.todone.interfaces.EditTodoDialogListener;
import com.deonna.todone.models.ContextHolder;
import com.deonna.todone.models.FilterStates;
import com.deonna.todone.models.Todo;
import com.deonna.todone.models.Todos;
import com.deonna.todone.adapters.TodosAdapter;
import com.deonna.todone.utils.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;

public class MainActivity extends AppCompatActivity implements EditTodoDialogListener {

    private static final int SIDEBAR_WIDTH = 70;
    private static final String FRAGMENT_EDIT_TODO = "fragment_edit_todo";

    private TodosAdapter todosAdapter;

    @BindView(R.id.lvItems) ListView lvItems;
    @BindView(R.id.etNewItem) EditText etNewItem;

    @BindView(R.id.btnShowAll) Button btnShowAll;
    @BindView(R.id.btnShowIncomplete) Button btnShowIncomplete;
    @BindView(R.id.btnShowComplete) Button btnShowComplete;

    private Todos todos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContextHolder.setContext(this);
        ButterKnife.bind(this);

        populateTodoItems();

        lvItems.setAdapter(todosAdapter);

        centerActionBarTitle();
    }

    private void removeTodo(int position) {

        todos.remove(todosAdapter.getCorrectPosition(position));
    }

    private void populateTodoItems() {

        todos = new Todos();
        todosAdapter = new TodosAdapter(todos);
        todos.asObservable().subscribe(todosAdapter);
    }

    public void onAddItem(View view) {

        String newItem = etNewItem.getText().toString().trim();

        if (todos.add(newItem)) {
            etNewItem.setText("");
        }

        Utilities.hideSoftKeyboard(view, this);
    }

    @OnItemClick(R.id.lvItems)
    public void showEditDialog(int position) {

        Todo todo = (Todo) todosAdapter.getItem(position);

        FragmentManager fm = getSupportFragmentManager();
        EditTodoDialogFragment editTodoDialogFragment = EditTodoDialogFragment.newInstance(todo);

        editTodoDialogFragment.show(fm, FRAGMENT_EDIT_TODO);
    }

    @OnItemLongClick(R.id.lvItems)
    public boolean removeTodoFromList(int position) {

        removeTodo(position);
        return true;
    }

    @OnClick(R.id.btnShowAll)
    public void showAll() {
        todosAdapter.getFilter().filter(FilterStates.ALL);
    }

    @OnClick(R.id.btnShowIncomplete)
    public void showIncomplete() {
        todosAdapter.getFilter().filter(FilterStates.INCOMPLETE);
    }

    @OnClick(R.id.btnShowComplete)
    public void showComplete() {
        todosAdapter.getFilter().filter(FilterStates.COMPLETE);
    }

    @Override
    public void onFinishEditDialog() {

        todosAdapter.notifyDataSetChanged();
    }

    private void centerActionBarTitle() {

        Toolbar toolbar = ButterKnife.findById(this, R.id.action_bar);
        TextView tvTitle = (TextView) toolbar.getChildAt(0);
        DisplayMetrics metrics = getResources().getDisplayMetrics();

        Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) tvTitle.getLayoutParams();
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        layoutParams.width = metrics.widthPixels - SIDEBAR_WIDTH;

        tvTitle.setLayoutParams(layoutParams);
        tvTitle.setGravity(Gravity.CENTER);
    }
}
