package com.example.mynews.ui.main;

import android.content.SharedPreferences;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.mynews.News;

import java.util.List;

public class ArticleSearchViewModel extends ViewModel {


    //LiveData variable
    private MutableLiveData<List<News>> mNews = new MutableLiveData<>();
    private MutableLiveData<String> newsTabTitle = new MutableLiveData<>();

        private LiveData<List<News>> mList = Transformations.map(mNews, new Function<List<News>, List<News>>() {
        @Override
        public List<News> apply(List<News> input) {
            return input;
        }
    });

    //LiveData for the Tab Title
    public MutableLiveData<String> getTabTitle(){
        if(newsTabTitle == null) {
            return new MutableLiveData<>();
        }
        return newsTabTitle;
    }

    public void setNewsTabTitle(String title){
        newsTabTitle.setValue(title);
    }

    public void setNews(List<News> news){
        mNews.setValue(news);
    }

    public LiveData<List<News>> getList() {
        return mList;
    }

    public void onClickListerner() {
        
    }
}
