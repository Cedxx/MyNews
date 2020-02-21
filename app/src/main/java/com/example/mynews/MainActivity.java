package com.example.mynews;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.mynews.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Load the views and the viewpager in the onCreate
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        //Configuring Toolbar
        this.configureToolbar();

        //onCLick for the Search

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu and add it to the Toolbar
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    private void configureToolbar() {
        //Get the toolbar view inside the activity layout
        Toolbar toolbar = findViewById(R.id.activity_main_toolbar);
        //Sets the Toolbar
        setSupportActionBar(toolbar);
    }
}

