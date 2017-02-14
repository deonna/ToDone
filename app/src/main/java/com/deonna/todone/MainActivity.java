package com.deonna.todone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> todoItems;
    ArrayAdapter<String> todoAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateTodoItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(todoAdapter);
    }

    private void populateTodoItems() {
        todoItems = new ArrayList<String>();

        for(int i = 1; i < 4; i++) {
            todoItems.add("Item " + i);
        }

        todoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                todoItems);
    }
}
