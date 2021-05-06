package com.neda.carwarehousecontentresolver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button searchButton;
    Button deleteButton;
    Button countButton;
    Button insertButton;
    ListView listView;

    final String uriString = "content://ContentProviderWithHelper/cars";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values= new ContentValues();
                values.put("model","model");
                values.put("maker","maker");
                values.put("price","10000");
                values.put("color","red");
                values.put("seats",4);
                values.put("year","2020");
                Uri uri= Uri.parse(uriString);
                getContentResolver().insert(uri,values);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getContentResolver().insert("content://ContentProviderWithHelper/cars",);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] args = {"4"};
                getContentResolver().delete(Uri.parse("content://ContentProviderWithHelper/cars"),"year < ?",args);
            }
        });

        countButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri= Uri.parse("content://ContentProviderWithHelper/cars");
                Cursor result= getContentResolver().query(uri,null,null,null);
                editText.setText(result.getCount()+"");
            }
        });

        ArrayList<String> items = new ArrayList<>();
//        items.add("123");
//        items.add("345");
//        items.add("567");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,items);
        listView.setAdapter(adapter);


    }

    void bindViews(){
        editText = findViewById(R.id.editText);
        searchButton = findViewById(R.id.searchButton);
        deleteButton = findViewById(R.id.deleteButton);
        listView = findViewById(R.id.listView);
        countButton = findViewById(R.id.countButton);
        insertButton = findViewById(R.id.insertButton);
    }
}