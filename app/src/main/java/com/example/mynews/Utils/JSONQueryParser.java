package com.example.mynews.Utils;

import android.net.http.DelegatingSSLSession;

import com.example.mynews.Models.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONQueryParser {

   //private JSONArray mediaArray = new JSONArray();
  private JSONObject mediaIndex = new JSONObject();

    public List<News> parseAPIResponse(String response) {
        List<News> newsList = new ArrayList<>();


        try {
            //getting the whole json object from the response
            JSONObject obj = new JSONObject(response);

            //we have the array named newsArray inside the object
            //so here we are getting that json array
            JSONArray newsArray = obj.getJSONArray("results");

            //now looping through all the elements of the json array
            for (int i = 0; i < newsArray.length(); i++) {
                //getting the json object of the particular index inside the array
                JSONObject newsObject = newsArray.getJSONObject(i);
                String sectionObject = newsObject.getString("section");
                String mediaUrlObject = newsObject.getString("url");

                // Check if "media" is present in the response for the MostPopular request and return the correct image url
                if(newsObject.has("media")){
                    JSONArray mediaArray = newsObject.getJSONArray("media");
                    if(mediaArray.length() > 0){
                        JSONObject mediaObject = mediaArray.getJSONObject(0);
                        JSONArray mediaData = mediaObject.getJSONArray("media-metadata");
                        mediaIndex = mediaData.getJSONObject(0);
                    }
                }
                // Check if "multimedia" is present in the response for the TOpStory request and return the correct image url
                if (newsObject.has("multimedia")) {
                    JSONArray mediaArray2 = newsObject.getJSONArray("multimedia");
                    if (mediaArray2.length() > 0) {
                        mediaIndex = mediaArray2.getJSONObject(0);
                    }
                }
                //creating a news object and giving them the values from json object
                News news = new News(newsObject.getString("title"), newsObject.getString("published_date"), sectionObject, mediaIndex.getString("url"), mediaUrlObject);

                //adding the news to newsList
                newsList.add(news);
            }return newsList;

        } catch (JSONException e) {
            e.printStackTrace();
        }return newsList;

    }
}
