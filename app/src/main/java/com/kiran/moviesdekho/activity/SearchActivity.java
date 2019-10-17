package com.kiran.moviesdekho.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kiran.moviesdekho.R;

public class SearchActivity extends AppCompatActivity {
    Button searchBtn;
    EditText searchString;
    String searchQuery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        searchBtn=(Button)findViewById(R.id.searchBtn);
        searchString=(EditText)findViewById(R.id.searchEditText);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchQuery=searchString.getText().toString();
                Intent newIntent=new Intent(SearchActivity.this,SearchResultActivity.class);
                newIntent.putExtra("movieName",searchQuery);
                startActivity(newIntent);
            }
        });


    }
}
