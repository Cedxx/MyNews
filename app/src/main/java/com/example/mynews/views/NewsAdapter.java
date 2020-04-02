package com.example.mynews.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynews.R;
import com.example.mynews.News;
import com.squareup.picasso.Picasso;

import java.util.List;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private ImageView mNewsImage;
    private TextView mNewsDescription;
    private TextView mNewsCategory;
    private TextView mNewsDate;

    //Member variable
    private List<News> mNewsList;
    private LayoutInflater mInflater;


    // data is passed into the constructor
    public NewsAdapter(Context context, List<News> newsList){
        this.mInflater = LayoutInflater.from(context);
        this.mNewsList = newsList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

        TextView newsDescription = mNewsDescription.findViewById(R.id.newsDescription);
        ImageView newsImage = mNewsImage.findViewById(R.id.newsImage);
        TextView newsCategory = mNewsCategory.findViewById(R.id.newsCategory);
        TextView newsDate = mNewsDate.findViewById(R.id.newsDate);

        News news = mNewsList.get(position);

        newsDescription.setText(news.getTitle());
        newsCategory.setText(news.getSection());
        newsDate.setText(news.getDate());
        String imgUrl = news.getImageUrl();
        Picasso.get().load(imgUrl).into(newsImage);

    }


    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            mNewsDescription = itemView.findViewById(R.id.newsDescription);
            mNewsCategory = itemView.findViewById(R.id.newsCategory);
            mNewsDate = itemView.findViewById(R.id.newsDate);
            mNewsImage = itemView.findViewById(R.id.newsImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
