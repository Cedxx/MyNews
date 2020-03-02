package com.example.mynews;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.mynews.ui.main.SectionsPagerAdapter;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    // SharedPreferences variable
    public static final String MyPref = "MyPrefsFile";
    protected SharedPreferences.Editor mEditor;
    protected SharedPreferences mSharedPreferences;

    //Volley variable
    private static final String TAG = "Event";
    //private static String url = "https://api.nytimes.com/svc/mostpopular/v2";
    private RequestQueue mRequestQueue;



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

        //Volley
        final TextView textView = findViewById(R.id.textViewTest);

        // Instantiate the RequestQueue.
        mRequestQueue = Volley.newRequestQueue(this);
        String url = "http://www.google.com";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        textView.setText("Response is: " + response.substring(0, 500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("That didn't work!");
            }
        });

        // Set the tag on the request.
        stringRequest.setTag(TAG);


        // Add the request to the RequestQueue.
        mRequestQueue.add(stringRequest);
    }

    //Method to start the Search Intent when the icon is clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_activity_main_search) {
            Intent i = new Intent(this, SearchActivity.class);
            this.startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Method show the Setting option when the icon is clicked
    public void onSettingItemClick(MenuItem item) {
        // The setting MenuItem was clicked
        // All other menu item clicks are handled onOptionsItemSelected()
    }


    //Method to inflate the topbar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu and add it to the Toolbar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    //Method to configure the Topbar view
    private void configureToolbar() {
        //Get the toolbar view inside the activity layout
        Toolbar toolbar = findViewById(R.id.activity_main_toolbar);
        //Sets the Toolbar
        setSupportActionBar(toolbar);
    }

    //onStop will close RequestQueue
    @Override
    protected void onStop () {
        super.onStop();
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(TAG);
        }
    }


}

