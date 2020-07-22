package com.example.todocodepath;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
   public  static  final  String KEY_ITEM_TEXT="item_text";
   public  static  final  String KEY_ITEM_POSITION = "item_position";
   public  static  final    int  TEXT_CODE=20;
   List<String> itemList;
   FloatingActionButton addButton;
   EditText addItem;
   TextView itemINList;
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

      ItemAdapter.OnLongClickListener LongListener= new ItemAdapter.OnLongClickListener() {
          @Override
          public void onItemLongClickListener(int position) {
              itemList.remove(position);
              adapter.notifyItemRemoved(position);
              Toast.makeText(getApplicationContext(),"Item was deleted",Toast.LENGTH_SHORT).show();
              saveItem();
          }
      };

      ItemAdapter.OnClickListener listener= new ItemAdapter.OnClickListener() {
          @Override
          public void onItemClickListener(int position) {
              Intent intent= new Intent(getApplicationContext(),UpdateActivity.class);
              intent.putExtra(KEY_ITEM_TEXT,itemList.get(position));
              intent.putExtra(KEY_ITEM_POSITION,position);
              startActivityForResult(intent,TEXT_CODE);
          }
      };
      adapter= new ItemAdapter(itemList,LongListener,listener);
      recView.setAdapter(adapter);
      recView.setLayoutManager(new LinearLayoutManager(this));

        addButton=(FloatingActionButton)findViewById(R.id.addButton);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("Result:"+resultCode);
            System.out.println("Request:"+requestCode);

        if (resultCode ==  RESULT_OK  && requestCode == TEXT_CODE) {
            String intentText=data.getStringExtra(KEY_ITEM_TEXT);
            int pos= data.getExtras().getInt(KEY_ITEM_POSITION);
            itemList.set(pos,intentText);
            saveItem();
            adapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(),"Item Updated",Toast.LENGTH_SHORT).show();
        } else {
            Log.i("MainActivity", "Unknown Call !: ");
        }

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