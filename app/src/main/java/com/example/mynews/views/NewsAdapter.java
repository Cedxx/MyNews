package com.example.mynews.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynews.R;
import com.example.mynews.news;

import java.util.ArrayList;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.newsViewHolder> {

    //Member variable
    private ArrayList<news> mData;
    private LayoutInflater mInflater;


    // data is passed into the constructor
    public NewsAdapter(Context context, ArrayList<news> data){
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @NonNull
    @Override
    public newsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.fragment_main, parent, false);
        return new newsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.newsViewHolder holder, int position) {
        news news = mData.get(position);
    }


    @Override
    public int getItemCount() {
        return 0;
    }

    public class newsViewHolder extends RecyclerView.ViewHolder {
        public newsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
