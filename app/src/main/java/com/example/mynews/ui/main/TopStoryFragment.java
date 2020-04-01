package com.example.mynews.ui.main;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mynews.News;
import com.example.mynews.R;
import com.example.mynews.views.NewsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class TopStoryFragment extends Fragment {
    //the URL having the json data
    private static final String JSON_URL = "https://api.nytimes.com/svc/mostpopular/v2/emailed/7.json?api-key=k5Eg30P0RAAy4bav3zB7RBXK5NrPjjCv";
    //The list where we will store all the News object after parsing JSON
    private List<News> mNewsList;
    private SectionsPagerAdapter sectionsPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                            sectionsPagerAdapter.setNews(mNewsList);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurs
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_top_story, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager( new LinearLayoutManager(getContext()));
        //mNewsList = new ArrayList<>();
        NewsAdapter newsAdapter = new NewsAdapter(getContext(), mNewsList);
        recyclerView.setAdapter(newsAdapter);
        return root;
    }

    public void setNewsList(List<News> news){
        this.mNewsList = news;
    }
}
