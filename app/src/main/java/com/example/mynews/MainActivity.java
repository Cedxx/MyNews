package com.example.mynews;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mynews.ui.main.ArticleSearchViewModel;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Observer;
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
    //ViewModel variable
    private ArticleSearchViewModel mArticleSearchViewModel;
    private static final int SEARCH_RESULT_STATUS_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Load the views and the viewpager in the onCreate
        setContentView(R.layout.activity_main);
        final SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        //Load the tab view in the onCreate
        final TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        //Load the Article Search ViewModel and the LiveData to display the correct tab depending on search argument
        mArticleSearchViewModel = new ArticleSearchViewModel();
        mArticleSearchViewModel.getTabTitle().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                TabLayout.Tab tab = tabs.getTabAt(2);
                assert tab != null;
                tab.setText(s);
            }
        });

        //Configuring Toolbar
        this.configureToolbar();

    }

    //Method to start the Search Intent when the icon is clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_activity_main_search) {
            Intent i = new Intent(this, SearchActivity.class);
            this.startActivityForResult(i, SEARCH_RESULT_STATUS_CODE);
            return true;
        }
        if (item.getItemId() == R.id.notif){
            Intent i = new Intent(this, NotificationsActivity.class);
            this.startActivity(i);
        }
        if (item.getItemId() == R.id.about){
            Toast.makeText(this, "NYT newspaper", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (item.getItemId() == R.id.help){
            Toast.makeText(this, "If you need help contact us : mynews@nyt.com", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case (SEARCH_RESULT_STATUS_CODE): {
                if(resultCode == Activity.RESULT_OK) {
                    this.mArticleSearchViewModel.setNewsTabTitle(data.getStringExtra("TAB RESULT TITLE"));
                }
                break;
            }
        }
    }


    //Method to inflate the top bar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu and add it to the Toolbar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    //Method to configure the Top bar view
    private void configureToolbar() {
        //Get the toolbar view inside the activity layout
        Toolbar toolbar = findViewById(R.id.activity_main_toolbar);
        //Sets the Toolbar
        setSupportActionBar(toolbar);
    }

}

