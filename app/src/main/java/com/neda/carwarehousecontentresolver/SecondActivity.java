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
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    EditText editText;
    Button searchButton;
    Button deleteButton;
    Button countButton;
    Button insertButton;
    ListView listView;
    ArrayList<String> items;

    final String uriString = "content://ContentProviderWithRoom/cars";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();
        items = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("model", "model");
                values.put("maker", "maker");
                values.put("price", "10000");
                values.put("color", "red");
                values.put("seats", 4);
                values.put("year", "2020");
                Uri uri = Uri.parse(uriString);
                getContentResolver().insert(uri, values);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.clear();
                Cursor cursor = getContentResolver().query(Uri.parse(uriString),null,null,null);
                Snackbar.make(v,"There are "+cursor.getCount()+" cars.",Snackbar.LENGTH_SHORT).show();
                while (cursor.moveToNext()){
                    String maker = cursor.getString(cursor.getColumnIndexOrThrow("maker"));
                    String model = cursor.getString(cursor.getColumnIndexOrThrow("model"));
                    String price = cursor.getString(cursor.getColumnIndexOrThrow("price"));
                    String seats = cursor.getString(cursor.getColumnIndexOrThrow("seats"));
                    String color = cursor.getString(cursor.getColumnIndexOrThrow("color"));
                    String year = cursor.getString(cursor.getColumnIndexOrThrow("year"));
                    items.add(maker + ", " + model + ", " + price + ", " +
                            seats + ", " + color + ", " + year);
                }
                adapter.notifyDataSetChanged();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0;
                String userInput = editText.getText().toString();
                if (userInput.length() == 0){
                    count = getContentResolver().delete(Uri.parse(uriString),null,null);
                } else {
                    count = getContentResolver().delete(Uri.parse(uriString+"/year/"+userInput),null,null);
                }
                Snackbar.make(v,""+count+" cars have been deleted",Snackbar.LENGTH_SHORT).show();
            }
        });

        countButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://ContentProviderWithHelper/cars");
                Cursor result = getContentResolver().query(uri, null, null, null);
                Snackbar.make(v,"There are "+result.getCount()+" cars.",Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    void bindViews() {
        editText = findViewById(R.id.editText);
        searchButton = findViewById(R.id.searchButton);
        deleteButton = findViewById(R.id.deleteButton);
        listView = findViewById(R.id.listView);
        countButton = findViewById(R.id.countButton);
        insertButton = findViewById(R.id.insertButton);
    }
}