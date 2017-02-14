package com.deonna.todone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    private EditText etEditedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        etEditedItem = (EditText) findViewById(R.id.etEditedItem);

        String currentTodo = getIntent().getStringExtra("current_todo");
        etEditedItem.setText(currentTodo);
        etEditedItem.setSelection(etEditedItem.getText().length());
    }

    public void onEditItem(View view) {
        String editedItem = etEditedItem.getText().toString();


        if (!editedItem.equals("")) {
            // TODO: 2/14/17 Send back saved result
        }

        finish();
    }
}
