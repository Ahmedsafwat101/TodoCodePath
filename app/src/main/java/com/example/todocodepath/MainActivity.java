package com.example.todocodepath;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
   List<String> itemList;
   Button addButton;
   EditText addItem;
   RecyclerView recView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //adding data int list
        itemList= new ArrayList<>();
        itemList.add("Buy Milk");
        itemList.add("Go to the gym");
        itemList.add("Have fun!");
        itemList.add("Solve leetcode problem");


      addButton=(Button)findViewById(R.id.addButton);
      addItem=(EditText)findViewById(R.id.editText);
      recView=(RecyclerView)findViewById(R.id.recyclerView);

      ItemAdapter adapter= new ItemAdapter(itemList);
      recView.setAdapter(adapter);
      recView.setLayoutManager(new LinearLayoutManager(this));

    }
}