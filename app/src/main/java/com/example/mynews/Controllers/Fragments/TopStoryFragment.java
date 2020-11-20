package com.example.mynews.Controllers.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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
import com.example.mynews.Models.News;
import com.example.mynews.R;
import com.example.mynews.Utils.JSONQueryParser;
import com.example.mynews.views.TopStoryViewModel;
import com.example.mynews.views.TopStoryAdapter;

import org.json.JSONException;

import java.util.List;
import java.util.Objects;

public class TopStoryFragment extends Fragment {
    //the URL having the json data
    private static final String JSON_URL = "https://api.nytimes.com/svc/topstories/v2/home.json?api-key=k5Eg30P0RAAy4bav3zB7RBXK5NrPjjCv";
    //The list where we will store all the News object after parsing JSON
   // private List<News> mNewsList;
    private TopStoryAdapter mTopStoryAdapter;
    private TopStoryViewModel mTopStoryViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mNewsList = new ArrayList<>();
        mTopStoryViewModel = new TopStoryViewModel();
        final JSONQueryParser JSONQuery = new JSONQueryParser();

        //Creating the string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            mTopStoryViewModel.setNews(JSONQuery.parseAPIResponse(response));
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
        //LiveData Observer
        mTopStoryViewModel.getList().observe(getViewLifecycleOwner(), new Observer<List<News>>() {
            @Override
            public void onChanged(List<News> news) {
                recyclerView.setLayoutManager( new LinearLayoutManager(getContext()));
                mTopStoryAdapter = new TopStoryAdapter(getContext(), news);
                recyclerView.setAdapter(mTopStoryAdapter);
            }
        });

        return root;
    }
}
