package com.deonna.todone;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements EditTodoDialogFragment.EditTodoDialogListener {

    public static final int SIDEBAR_WIDTH = 70;

    private TodosAdapter todoAdapter;
    private ListView lvItems;
    private EditText etNewItem;

    private Todos todos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateTodoItems();

        etNewItem = (EditText) findViewById(R.id.etNewItem);

        initializeListView();

        centerActionBarTitle();
    }

    private void removeTodo(int position) {

        todos.remove(position);
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

    private void showEditDialog(Todo todo, int position) {
        FragmentManager fm = getSupportFragmentManager();
        EditTodoDialogFragment editTodoDialogFragment = EditTodoDialogFragment.newInstance(todo,
                position);

        editTodoDialogFragment.show(fm, "fragment_edit_todo");
    }

    private void initializeListView() {

        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(todoAdapter);

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                removeTodo(position);
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                showEditDialog(todos.get(i), i);
            }
        });
    }

    @Override
    public void onFinishEditDialog(String name, int position) {
        todos.edit(position, name);
        todoAdapter.notifyDataSetChanged();
    }

    private void centerActionBarTitle() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.action_bar);
        TextView tvTitle = (TextView) toolbar.getChildAt(0);
        DisplayMetrics metrics = getResources().getDisplayMetrics();

        Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) tvTitle.getLayoutParams();
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        layoutParams.width = metrics.widthPixels - SIDEBAR_WIDTH;

        tvTitle.setLayoutParams(layoutParams);
        tvTitle.setGravity(Gravity.CENTER);
    }
}
