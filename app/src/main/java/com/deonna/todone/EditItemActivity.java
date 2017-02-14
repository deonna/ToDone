package com.deonna.todone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    private EditText etEditedItem;

    private int currentTodoPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        etEditedItem = (EditText) findViewById(R.id.etEditedItem);

        String currentTodo = getIntent().getStringExtra(IntentConstants.CURRENT_TODO);
        etEditedItem.setText(currentTodo);
        etEditedItem.setSelection(etEditedItem.getText().length());

        currentTodoPosition = getIntent().getIntExtra(IntentConstants.POSITION, 0);
    }

    public void onEditItem(View view) {

        String editedItem = etEditedItem.getText().toString().trim();

        Intent data = new Intent();
        data.putExtra(IntentConstants.NEW_TODO, editedItem);
        data.putExtra(IntentConstants.POSITION, currentTodoPosition);

        setResult(Code.EDITED.ordinal(), data);

        finish();
    }
}
