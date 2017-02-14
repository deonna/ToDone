package com.deonna.todone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> todoItems;
    ArrayAdapter<String> todoAdapter;
    ListView lvItems;
    EditText etNewItem;

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
    }

    private void removeTodo(int position) {
        todoItems.remove(position);
        todoAdapter.notifyDataSetChanged();
    }

    private void populateTodoItems() {
        todoItems = new ArrayList<String>();

        for(int i = 1; i < 4; i++) {
            todoItems.add("Item " + i);
        }

        todoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                todoItems);
    }

    public void onAddItem(View view) {
        String newItem = etNewItem.getText().toString();

        if(!newItem.equals("")) {
            todoAdapter.add(etNewItem.getText().toString());
            etNewItem.setText("");
        }
    }
}
