package com.deonna.todone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String FILENAME = "todo.txt";

    public static final int EDIT_REQUEST = 1;
    public static final int EDITED = 2;

    private ArrayList<String> todoItems;
    private ArrayAdapter<String> todoAdapter;
    private ListView lvItems;
    private EditText etNewItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateTodoItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(todoAdapter);

        etNewItem = (EditText) findViewById(R.id.etNewItem);

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
                Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
                intent.putExtra("current_todo", todoItems.get(i));
                intent.putExtra("position", i);
                startActivityForResult(intent, EDIT_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EDIT_REQUEST && resultCode == EDITED) {
            String newTodo = data.getExtras().getString("new_todo");

            int position = data.getExtras().getInt("position");

            todoItems.set(position, newTodo);
            todoAdapter.notifyDataSetChanged();

            writeItems();
        }
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File file =  new File(filesDir, FILENAME);

        try {
            todoItems = new ArrayList<String>(FileUtils.readLines(file));
        } catch (IOException e) {
            Log.e(TAG, "Exception caught while reading " + FILENAME + " ", e);
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File file =  new File(filesDir, FILENAME);

        try {
            FileUtils.writeLines(file, todoItems);
        } catch (IOException e) {
            Log.e(TAG, "Exception caught while writing to " + FILENAME + " ", e);
        }
    }

    private void removeTodo(int position) {
        todoItems.remove(position);
        todoAdapter.notifyDataSetChanged();
        writeItems();
    }

    private void populateTodoItems() {
        todoItems = new ArrayList<String>();

        readItems();

        todoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                todoItems);
    }

    public void onAddItem(View view) {
        String newItem = etNewItem.getText().toString();

        if(!newItem.equals("")) {
            todoAdapter.add(etNewItem.getText().toString());
            writeItems();

            etNewItem.setText("");
        }
    }
}
