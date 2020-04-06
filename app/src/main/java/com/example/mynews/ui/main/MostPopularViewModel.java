package com.example.mynews.ui.main;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.mynews.News;

import java.util.List;

public class MostPopularViewModel  extends ViewModel {

    private MutableLiveData<List<News>> mNews = new MutableLiveData<>();
    private LiveData<List<News>> mList = Transformations.map(mNews, new Function<List<News>, List<News>>() {
        @Override
        public List<News> apply(List<News> input) {
            return input;
        }
    });

    public void setNews(List<News> news){
        mNews.setValue(news);
    }

    public LiveData<List<News>> getList() {
        return mList;
    }
}
