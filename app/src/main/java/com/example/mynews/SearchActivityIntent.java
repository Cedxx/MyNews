package com.example.mynews;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

class SearchActivityIntent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Linking the elements in the layout to Java code
        setContentView(R.layout.search_main);
    }
}
