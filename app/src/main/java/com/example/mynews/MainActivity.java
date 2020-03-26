package com.example.mynews;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mynews.ui.main.SectionsPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // SharedPreferences variable
    public static final String MyPref = "MyPrefsFile";
    protected SharedPreferences.Editor mEditor;
    protected SharedPreferences mSharedPreferences;

    //Volley variable
    //the URL having the json data
    private static final String JSON_URL = "https://api.nytimes.com/svc/mostpopular/v2/emailed/7.json?api-key=k5Eg30P0RAAy4bav3zB7RBXK5NrPjjCv";

    //The list where we will store all the News object after parsing JSON
    List<News>mNewsList;


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

        //Initializing the News List
        mNewsList = new ArrayList<>();

        //this method will fetch and parse the data
        loadNewsList();

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

    private void loadNewsList(){
        //Creating the string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named newsArray inside the object
                            //so here we are getting that json array
                            JSONArray newsArray = obj.getJSONArray("results");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < newsArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject newsObject = newsArray.getJSONObject(i);
                                JSONObject sectionObject = newsObject.getJSONObject("section");
                                JSONArray mediaArray = newsObject.getJSONArray("media");
                                JSONObject mediaObject = mediaArray.getJSONObject(0);
                                JSONArray mediaData = mediaObject.getJSONArray("media-metadata");
                                JSONObject mediaIndex = mediaData.getJSONObject(0);



                                //creating a hero object and giving them the values from json object
                                News news = new News(newsObject.getString("title"), newsObject.getString("published_date"), sectionObject.getString("section"), mediaIndex.getString("url"));

                                //adding the news to newsList
                                mNewsList.add(news);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

}

