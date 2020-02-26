package com.example.mynews;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.DialogFragment;

public class SearchActivityIntent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Linking the elements in the layout to Java code
        setContentView(R.layout.search_main);
    }

    //Method that call the DatePickerFragment and show the DatePicker
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void retrieveSearchArguement(){
        // initiate a search view
        SearchView simpleSearchView = findViewById(R.id.simpleSearchView);
        // get the query string currently in the text field
        CharSequence query = simpleSearchView.getQuery();
    }

}
