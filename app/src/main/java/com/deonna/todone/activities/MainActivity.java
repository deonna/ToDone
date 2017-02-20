package com.deonna.todone.activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.deonna.todone.fragments.EditTodoDialogFragment;
import com.deonna.todone.R;
import com.deonna.todone.interfaces.EditTodoDialogListener;
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

    public static final int SIDEBAR_WIDTH = 70;
    private static final String FRAGMENT_EDIT_TODO = "fragment_edit_todo";

    private TodosAdapter todoAdapter;

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

        ButterKnife.bind(this);

        populateTodoItems();

        lvItems.setAdapter(todoAdapter);

        centerActionBarTitle();
    }

    private void removeTodo(int position) {

        todos.remove(todoAdapter.getCorrectPosition(position));
        todoAdapter.notifyDataSetChanged();
    }

    private void populateTodoItems() {

        todos = new Todos(this, getFilesDir());
        todoAdapter = new TodosAdapter(this, todos.getItems());
    }

    public void onAddItem(View view) {

        String newItem = etNewItem.getText().toString().trim();

        if (todos.add(newItem)) {
            todoAdapter.notifyDataSetChanged();
            etNewItem.setText("");
        }

        Utilities.hideSoftKeyboard(view, this);
    }

    @OnItemClick(R.id.lvItems)
    public void showEditDialog(int position) {

        Todo todo = (Todo) todoAdapter.getItem(position);

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
        todoAdapter.getFilter().filter(TodosAdapter.ALL);
    }

    @OnClick(R.id.btnShowIncomplete)
    public void showIncomplete() {
        todoAdapter.getFilter().filter(TodosAdapter.INCOMPLETE);
    }

    @OnClick(R.id.btnShowComplete)
    public void showComplete() {
        todoAdapter.getFilter().filter(TodosAdapter.COMPLETE);
    }

    @Override
    public void onFinishEditDialog() {

        todoAdapter.notifyDataSetChanged();
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
