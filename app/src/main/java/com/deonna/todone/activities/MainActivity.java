package com.deonna.todone.activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
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

    private TodosAdapter todoAdapter;

    @BindView(R.id.lvItems) ListView lvItems;
    @BindView(R.id.etNewItem) EditText etNewItem;

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

    @OnItemClick(R.id.lvItems)
    public void showEditDialog(int position) {

        Todo todo = todos.get(position);

        FragmentManager fm = getSupportFragmentManager();
        EditTodoDialogFragment editTodoDialogFragment = EditTodoDialogFragment.newInstance(todo);

        editTodoDialogFragment.show(fm, "fragment_edit_todo2");
    }

    @OnItemLongClick(R.id.lvItems)
    public boolean removeTodoFromList(int position) {

        removeTodo(position);
        return true;
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
