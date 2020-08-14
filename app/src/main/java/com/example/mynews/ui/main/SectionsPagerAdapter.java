package com.example.mynews.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynews.News;
import com.example.mynews.R;
import com.example.mynews.views.ArticleSearchAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {


    //@StringRes
    //private final int[] TAB_TITLES = new int[]{R.string.top_story, R.string.most_popular, R.string.sport};
    //The ArrayList that will store the TAB names
    private ArrayList<String> TAB_TITLES = new ArrayList<>();
    private final Context mContext;
    private List<Fragment> mFragmentList;

    // SharedPreferences variable
    private static final String MyPref = "MyPrefsFile";
    private SharedPreferences mSharedPreferences;

    public SectionsPagerAdapter(final Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new TopStoryFragment());
        mFragmentList.add(new MostPopularFragment());
        mFragmentList.add(new ArticleSearchFragment());

        mSharedPreferences = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE);

        //set the corresponding name for each tab
        TAB_TITLES.add("Top Stories");
        TAB_TITLES.add("Most Popular");



}

    @NonNull
    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return mFragmentList.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(final int position) {
        if(position == 2){
            return mSharedPreferences.getString("categoriesQuery", "");
        }
        return TAB_TITLES.get(position);
    }

    @Override
    public int getCount() {
        // Show the total pages.
        return mFragmentList.size();
    }
}