package com.example.mynews.ui.main;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mynews.R;

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
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mSharedPreferences;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new TopStoryFragment());
        mFragmentList.add(new MostPopularFragment());
        mFragmentList.add(new ArticleSearchFragment());

        mSharedPreferences = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        //mEditor = mSharedPreferences.edit();

        //set the corresponding name for each tab
        TAB_TITLES.add("Top Stories");
        TAB_TITLES.add("Most Popular");
        TAB_TITLES.add(categoriesQueryValue());
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
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES.get(position);
    }

    @Override
    public int getCount() {
        // Show the total pages.
        return mFragmentList.size();
    }

    private String categoriesQueryValue(){
        return mSharedPreferences.getString("categoriesQuery","");
    }
}