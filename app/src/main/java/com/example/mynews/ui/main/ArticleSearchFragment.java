package com.example.mynews.ui.main;

import androidx.lifecycle.Observer;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mynews.News;
import com.example.mynews.R;
import com.example.mynews.views.ArticleSearchAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import static android.content.Context.MODE_PRIVATE;

public class ArticleSearchFragment extends Fragment {

    //the URL having the json data
    private static final String JSON_URL = "https://api.nytimes.com/svc/search/v2/articlesearch.json?";
    private String ApiKey = "&api-key=k5Eg30P0RAAy4bav3zB7RBXK5NrPjjCv";
    String jsonText = "https://api.nytimes.com/svc/search/v2/articlesearch.json?fq=news_desk:(\"Sports\" \"Foreign\")&api-key=k5Eg30P0RAAy4bav3zB7RBXK5NrPjjCv";
    //The list where we will store all the News object after parsing JSON
    private List<News> mNewsList;
    private ArticleSearchAdapter mArticleSearchAdapter;
    private ArticleSearchViewModel mArticleSearchViewModel;
    // SharedPreferences variable
    private static final String MyPref = "MyPrefsFile";
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mSharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsList = new ArrayList<>();
        mArticleSearchViewModel = new ArticleSearchViewModel();

        //retrieve SharedPreferences Data for the JSON query search
        //Retrieving sharedPreferences data for the CheckBox
        mSharedPreferences = getActivity().getSharedPreferences(MyPref, MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        //API key builder
        //https://api.nytimes.com/svc/search/v2/articlesearch.json?fq=news_desk:("Sports" "Foreign")&api-key=k5Eg30P0RAAy4bav3zB7RBXK5NrPjjCv

        //final String jsonApiSearchQuery = JSON_URL+"q="+ stringBuilder()+ApiKey;

        //Creating the string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, jsonText,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named newsArray inside the object
                            //so here we are getting that json array
                            JSONObject responseObj = obj.getJSONObject("response");


                            //now looping through all the elements of the json array
                            for (int i = 0; i < responseObj.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONArray newsArray = responseObj.getJSONArray("docs");
                                JSONObject newsObject = newsArray.getJSONObject(i);
                                String sectionObject = newsObject.getString("source");
                                JSONArray mediaArray = newsObject.getJSONArray("multimedia");
                                JSONObject mediaObject = mediaArray.getJSONObject(60);
                                //JSONArray mediaData = mediaObject.getJSONArray("media-metadata");
                                //JSONObject mediaIndex = mediaData.getJSONObject(0);


                                //creating a news object and giving them the values from json object
                                News news = new News(newsObject.getString("abstract"), newsObject.getString("pub_date"), sectionObject, mediaObject.getString("url"));

                                //adding the news to newsList
                                mNewsList.add(news);
                            }
                            mArticleSearchViewModel.setNews(mNewsList);

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

    private String jsonApiSearchQuery(){
        return JSON_URL+"fq="+ stringBuilder()+ApiKey;
    }

    private String stringBuilder(){
        //test string builder
        StringBuilder myResearchString = new StringBuilder();
        if(isArtChecked()) myResearchString.append("\"arts\"");
        if(isPoliticsIsChecked()) myResearchString.append("\"politics\"");
        if(businessIsChecked()) myResearchString.append("\"business\"");
        if(sportsIsChecked()) myResearchString.append("\"sports\"");
        if(entrepreneursIsChecked()) myResearchString.append("\"entrepreneurs\"");
        if(travelsIsChecked()) myResearchString.append("\"travels\"");
        return myResearchString.toString();
    }

    private boolean isArtChecked() {
        return mSharedPreferences.getBoolean("arts", true);
    }

    private boolean isPoliticsIsChecked(){
        return mSharedPreferences.getBoolean("politics", true);
    }

    private boolean businessIsChecked(){
        return mSharedPreferences.getBoolean("business", false);
    }

    private boolean sportsIsChecked(){
      return mSharedPreferences.getBoolean("sports", false);
    }

    private boolean entrepreneursIsChecked(){
        return mSharedPreferences.getBoolean("entrepreneurs", false);
    }

    private boolean travelsIsChecked(){
        return mSharedPreferences.getBoolean("travels", false);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_article_search, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        //LiveData Observer
        mArticleSearchViewModel.getList().observe(getViewLifecycleOwner(), new Observer<List<News>>() {
            @Override
            public void onChanged(List<News> news) {
                recyclerView.setLayoutManager( new LinearLayoutManager(getContext()));
                mArticleSearchAdapter = new ArticleSearchAdapter(getContext(), mNewsList);
                recyclerView.setAdapter(mArticleSearchAdapter);
            }
        });

        return root;
    }

}
