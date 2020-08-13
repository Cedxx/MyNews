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

    // SharedPreferences variable
    private static final String MyPref = "MyPrefsFile";
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mSharedPreferences;

    //LiveData variable
    private MutableLiveData<List<News>> mNews = new MutableLiveData<>();
    private MutableLiveData<String> newsTabTitle = new MutableLiveData<>();
        private LiveData<List<News>> mList = Transformations.map(mNews, new Function<List<News>, List<News>>() {
        @Override
        public List<News> apply(List<News> input) {
            return input;
        }
    });

    public LiveData<String> getTabTitle(){
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
}
