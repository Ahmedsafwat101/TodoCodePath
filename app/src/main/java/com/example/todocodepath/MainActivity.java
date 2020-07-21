package com.example.todocodepath;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
   List<String> itemList;
   Button addButton;
   EditText addItem;
   RecyclerView recView;
   ItemAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //adding data int list
        loadItems();

      addItem=(EditText)findViewById(R.id.editText);
      recView=(RecyclerView)findViewById(R.id.recyclerView);
      ItemAdapter.OnLongClickListener listener= new ItemAdapter.OnLongClickListener() {
          @Override
          public void onItemLongClickListener(int position) {
              itemList.remove(position);
              adapter.notifyItemRemoved(position);
              Toast.makeText(getApplicationContext(),"Item was deleted",Toast.LENGTH_SHORT).show();
              saveItem();
          }
      };

      adapter= new ItemAdapter(itemList,listener);
      recView.setAdapter(adapter);
      recView.setLayoutManager(new LinearLayoutManager(this));

        addButton=(Button)findViewById(R.id.addButton);
      //add action for the button
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item=addItem.getText().toString();
                if(item.isEmpty())return;
                itemList.add(item);
                adapter.notifyItemInserted(itemList.size()-1);
                addItem.setText("");
                Toast.makeText(getApplicationContext(),"Item was added",Toast.LENGTH_SHORT).show();
                saveItem();
            }
        });

    }
    private File getDataFile()
    {
        return (new File(getFilesDir(),"data.txt"));
    }

    private void loadItems(){
        try {
            itemList=new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity","Error Reading File",e);
            itemList= new ArrayList<>();
        }
    }

    private void saveItem()
    {
        try {
            FileUtils.writeLines(getDataFile(),itemList);
        } catch (IOException e) {
            Log.e("MainActivity","Error Saving File",e);
        }
    }

}