package com.example.mynews.Controllers.Fragments;

import androidx.lifecycle.Observer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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
import com.example.mynews.Models.News;
import com.example.mynews.R;
import com.example.mynews.Utils.ItemsClickSupport;
import com.example.mynews.Utils.JSONQueryParser;
import com.example.mynews.views.ArticleSearchViewModel;
import com.example.mynews.views.ArticleSearchAdapter;
import com.example.mynews.Controllers.Activities.WebViewActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class ArticleSearchFragment extends Fragment {

    //the URL having the json data
    private static final String JSON_URL = "https://api.nytimes.com/svc/search/v2/articlesearch.json?";
    private String ApiKey = "&api-key=k5Eg30P0RAAy4bav3zB7RBXK5NrPjjCv";
    private String baseImageUrl = "https://static01.nyt.com/";
    String jsonText = "https://api.nytimes.com/svc/search/v2/articlesearch.json?fq=news_desk:(\"Politics\")&begin_date=11112020&end_date=02122020&api-key=k5Eg30P0RAAy4bav3zB7RBXK5NrPjjCv&q=election";
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

    public static final String EXTRA_MESSAGE = "test";
    private Context context;

    private RecyclerView mRecyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsList = new ArrayList<>();
        mArticleSearchViewModel = new ArticleSearchViewModel();
        final JSONQueryParser JSONQuery = new JSONQueryParser();

        //retrieve SharedPreferences Data for the JSON query search
        //Retrieving sharedPreferences data for the CheckBox
        mSharedPreferences = getActivity().getSharedPreferences(MyPref, MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        //final String jsonApiSearchQuery = JSON_URL+"q="+ stringBuilder()+ApiKey;

        //Creating the string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, jsonApiSearchQueryVariable(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            mArticleSearchViewModel.setNews(JSONQuery.parseAPIResponse(response));

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


    //String builder for the JSON API request
    //Here we set the correct String to display all the request data for the user search Query
    private String jsonApiSearchQueryVariable(){
        StringBuilder myJsonApiQuery = new StringBuilder();
        myJsonApiQuery.append(JSON_URL);
        myJsonApiQuery.append("fq=news_desk:(");
        if(isArtChecked()) myJsonApiQuery.append("\"arts\"");
        if(isPoliticsIsChecked()) myJsonApiQuery.append("\"politics\"");
        if(businessIsChecked()) myJsonApiQuery.append("\"business\"");
        if(sportsIsChecked()) myJsonApiQuery.append("\"sports\"");
        if(entrepreneursIsChecked()) myJsonApiQuery.append("\"entrepreneurs\"");
        if(travelsIsChecked()) myJsonApiQuery.append("\"travels\"");
        myJsonApiQuery.append(")");
        myJsonApiQuery.append("&begin_date=").append(beginDateFromQuery());
        myJsonApiQuery.append("&end_date=").append(endDateFromQuery());
        myJsonApiQuery.append("&sort=newest");
        myJsonApiQuery.append("&q=");
        myJsonApiQuery.append(searchQueryValue());
        myJsonApiQuery.append(ApiKey);
        return myJsonApiQuery.toString();
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

    
    //Retrieve the begin from the Search Query and convert it a format readable for the JSONQuery
    private String beginDateFromQuery(){
        String beginDate = mSharedPreferences.getString("beginDate","");

        //The SimpleDateFormat that will be use to check our JSON query value
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        try {
            Date shortDate = dateFormat.parse(beginDate);
            //Returning the date they way I want it to look
            return new SimpleDateFormat("yyyyMMdd", Locale.US).format(Objects.requireNonNull(shortDate));

        } catch (ParseException e) {
            return "";
        }

    }

    //Retrieve the end date from the Search Query and convert it a format readable for the JSONQuery
    private String endDateFromQuery(){
        String endDate = mSharedPreferences.getString("endDate","");

        //The SimpleDateFormat that will be use to check our JSON query value
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        try {
            Date shortDate = dateFormat.parse(endDate);
            //Returning the date they way I want it to look
            return new SimpleDateFormat("yyyyMMdd", Locale.US).format(Objects.requireNonNull(shortDate));

        } catch (ParseException e) {
            return "";
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Calling the method that configuring click on RecyclerView
        this.configureOnClickRecyclerView(container);
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_article_search, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        //LiveData Observer
        mArticleSearchViewModel.getList().observe(getViewLifecycleOwner(), new Observer<List<News>>() {
            @Override
            public void onChanged(List<News> news) {
                recyclerView.setLayoutManager( new LinearLayoutManager(getContext()));
                mArticleSearchAdapter = new ArticleSearchAdapter(getContext(), news);
                recyclerView.setAdapter(mArticleSearchAdapter);
            }
        });
//        mArticleSearchAdapter.setClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                int position = recyclerView.indexOfChild(v);
//                Intent intent = new Intent(context, WebViewActivity.class);
//                intent.putExtra(articleMediaUrl,"www.google.fr");
//                context.startActivities(new Intent[position]);
//            }
//        });

        return root;


    }

    //Configure item click on RecyclerView
    private void configureOnClickRecyclerView(View view){
        final RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        ItemsClickSupport.addTo(recyclerView, R.layout.fragment_article_search).setOnItemClickListener(new ItemsClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Log.d("TAG", "Position : "+position);
                News list = mArticleSearchAdapter.getNewsListPosition(position);
                Toast.makeText(getContext(), "You cliked on position :"+list.getArticleUrl(),Toast.LENGTH_SHORT).show();
//                position = recyclerView.indexOfChild(v);
//                Intent intent = new Intent(context, WebViewActivity.class);
//                intent.putExtra(EXTRA_MESSAGE, "www.google.fr");
//                context.startActivities(new Intent[position]);
            }
        });
    }

    private void callWebViewActivity(String url, int position){
        //position = recyclerView.indexOfChild(v);
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra(EXTRA_MESSAGE, "www.google.fr");
        context.startActivities(new Intent[position]);
    }
}
