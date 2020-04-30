package com.example.mynews.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynews.News;
import com.example.mynews.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MostPopularAdapter extends RecyclerView.Adapter<MostPopularAdapter.MostPopularViewHolder> {



    //Member variable
    private List<News> mNewsList;
    private LayoutInflater mInflater;


    // data is passed into the constructor
    public MostPopularAdapter (Context context, List<News> newsList){
        this.mInflater = LayoutInflater.from(context);
        this.mNewsList = newsList;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public MostPopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.most_populat_row, parent, false);
        return new MostPopularViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull MostPopularViewHolder holder, int position) {

        News news = mNewsList.get(position);

        //Set the JSON queried data in the respective section
        holder.newsDescription.setText(news.getTitle());
        holder.newsCategory.setText(news.getSection());
        holder.newsDate.setText(news.getDate());
        String imgUrl = news.getImageUrl();
        Picasso.get().load(imgUrl).into(holder.newsImage);

    }


    // Set the limit size of the list to match the JSON queried item
    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    // stores and recycles views as they are scrolled off screen
    public static class MostPopularViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView newsImage;
        TextView newsDescription, newsCategory, newsDate;

        MostPopularViewHolder(@NonNull View itemView) {
            super(itemView);
            newsDescription = itemView.findViewById(R.id.newsDescription);
            newsCategory = itemView.findViewById(R.id.newsCategory);
            newsDate = itemView.findViewById(R.id.newsDate);
            newsImage = itemView.findViewById(R.id.newsImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}

