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

import java.util.ArrayList;
import java.util.List;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private TextView mNewsDescription;
    private ImageView mNewsImage;

    //Member variable
    private List<News> mData;
    private LayoutInflater mInflater;


    // data is passed into the constructor
    public NewsAdapter(Context context, List<News> data){
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

        TextView test1 = mNewsDescription.findViewById(R.id.newsDescription);
        ImageView test2 = mNewsImage.findViewById(R.id.newsImage);

        News news = mData.get(position);

        test1.setText(news.getTitle());
        String imgUrl = news.getImageUrl();
        Picasso.get().load(imgUrl).into(test2);

    }


    @Override
    public int getItemCount() {
        return 0;
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            mNewsDescription = itemView.findViewById(R.id.newsDescription);
            mNewsImage = itemView.findViewById(R.id.newsImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
