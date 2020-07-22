package com.example.todocodepath;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class UpdateActivity extends AppCompatActivity {
    EditText updateEditText;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        updateEditText=(EditText) findViewById(R.id.updateEditText);
        saveButton=(Button)findViewById(R.id.saveButton);
        getSupportActionBar().setTitle("Update Item");

        updateEditText.setText(getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT));

         saveButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
               Intent intent= new Intent();
               intent.putExtra(MainActivity.KEY_ITEM_TEXT,updateEditText.getText().toString());
               intent.putExtra(MainActivity.KEY_ITEM_POSITION,getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION));
               setResult(RESULT_OK,intent);
               finish();
             }
         });

    }
}