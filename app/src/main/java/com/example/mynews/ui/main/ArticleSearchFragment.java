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

import static android.content.Context.MODE_PRIVATE;

public class ArticleSearchFragment extends Fragment {

    //the URL having the json data
    private static final String JSON_URL = "https://api.nytimes.com/svc/search/v2/articlesearch.json?";
    private String ApiKey = "&api-key=k5Eg30P0RAAy4bav3zB7RBXK5NrPjjCv";
    private String baseImageUrl = "https://static01.nyt.com/";
    String jsonText = "https://api.nytimes.com/svc/search/v2/articlesearch.json?fq=news_desk:(\"Sports\")&api-key=k5Eg30P0RAAy4bav3zB7RBXK5NrPjjCv&q=motogp";
    //The list where we will store all the News object after parsing JSON
    private List<News> mNewsList;
    private ArticleSearchAdapter mArticleSearchAdapter;
    private ArticleSearchViewModel mArticleSearchViewModel;
    // SharedPreferences variable
    private static final String MyPref = "MyPrefsFile";
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mSharedPreferences;
    //JSON variable
    private JSONObject articleMedia = new JSONObject();
    private String articleMediaUrl = "";




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

                            //getting the main object response for the JSON and its array
                            JSONObject responseObj = obj.getJSONObject("response");
                            JSONArray newsArray = responseObj.getJSONArray("docs");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < newsArray.length() -1; i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject newsObject = newsArray.getJSONObject(i);
                                String sectionObject = newsObject.getString("section_name");
                                JSONArray mediaArray = newsObject.getJSONArray("multimedia");
                                //JSONObject articleMedia = new JSONObject();
                                //String articleMediaUrl = "";
                                for (int j = 0; j < mediaArray.length() -1; j++) {
                                    JSONObject mediaObject = mediaArray.getJSONObject(0);
                                    StringBuilder myMediaImageUrl = new StringBuilder();
                                    myMediaImageUrl.append(baseImageUrl);
                                    if(mediaObject.getInt("height") == 75) {
                                        myMediaImageUrl.append(mediaObject.getString("url"));
                                        articleMediaUrl = myMediaImageUrl.toString();
                                        articleMedia = mediaObject;
                                    }else if(mediaObject.getInt("height") > 75){
                                        myMediaImageUrl.append(mediaObject.getString("url"));
                                        articleMediaUrl = myMediaImageUrl.toString();
                                        articleMedia = mediaObject;
                                    }else {
                                        JSONObject mediaDefaultObject = mediaArray.getJSONObject(0);
                                        myMediaImageUrl.append(mediaDefaultObject.getString("url"));
                                        articleMediaUrl = myMediaImageUrl.toString();
                                        articleMedia = mediaArray.getJSONObject(0);
                                    }
                                }
                                //JSONObject mediaObject = mediaArray.getJSONObject(60);
                                //JSONArray mediaData = mediaObject.getJSONArray("media-metadata");
                                //JSONObject mediaIndex = mediaData.getJSONObject(0);


                                //creating a news object and giving them the values from json object
                                News news = new News(newsObject.getString("snippet"), newsObject.getString("pub_date"), sectionObject, articleMediaUrl);

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

    // Create the Full JSON URL base on sharedVariables data
    private String jsonApiSearchQuery(){
        return JSON_URL+"fq=news_desk:("+ checkBoxStringBuilder()+")"+ApiKey;
    }

    //String builder for the MediaImageUrl
    private String mediaImageUrlString(){
        StringBuilder myMediaImageUrl = new StringBuilder();
        myMediaImageUrl.append(baseImageUrl);
        myMediaImageUrl.append("url");
        return myMediaImageUrl.toString();
    }

    // A String Builder that is base on checkBox saved in SharedVariables
    private String checkBoxStringBuilder(){
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



    //Simple method to retrieve if a checkBox is checked in the searchActivity
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
    // end of the method

    //method to retrieve the search query from the SearchActivity
    private String searchQueryValue(){
        return mSharedPreferences.getString("searchQuery","");
    }

    //method to retrieve the start date from the searchActivity
    private String startDateValue(){
        return mSharedPreferences.getString("beginDate", "");
    }

    //method to retrieve the end date from the searchActivity
    private String endDateValue(){
        return mSharedPreferences.getString("endDate", "");
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
