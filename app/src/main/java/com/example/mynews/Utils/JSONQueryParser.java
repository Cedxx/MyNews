package com.example.mynews.Utils;

import com.example.mynews.Models.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class JSONQueryParser {

   //private JSONArray mediaArray = new JSONArray();
  private JSONObject mediaIndex = new JSONObject();
    //JSON variable
    private JSONObject articleMedia = new JSONObject();
    private String articleMediaUrl = "";
    private String baseImageUrl = "https://static01.nyt.com/";
    private  String dateFormat = "";

    public List<News> parseAPIResponse(String response) throws JSONException {
        List<News> newsList = new ArrayList<>();


        //getting the whole json object from the response
        JSONObject obj = new JSONObject(response);

        if (obj.has("results")) {
        //we have the array named newsArray inside the object
        //so here we are getting that json array
        JSONArray newsArray = obj.getJSONArray("results");

        //now looping through all the elements of the json array
        for (int i = 0; i < newsArray.length(); i++) {
            //getting the json object of the particular index inside the array
            JSONObject newsObject = newsArray.getJSONObject(i);
            String sectionObject = newsObject.getString("section");
            String mediaUrlObject = newsObject.getString("url");
            dateFormat = newsObject.getString("published_date");

            // Check if "media" is present in the response for the MostPopular request and return the correct image url
            if (newsObject.has("media")) {
                JSONArray mediaArray = newsObject.getJSONArray("media");
                if (mediaArray.length() > 0) {
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
            News news = new News(newsObject.getString("title"), convertDate(dateFormat), sectionObject, mediaIndex.getString("url"), mediaUrlObject);

            //adding the news to newsList
            newsList.add(news);
        }
        }  else {
            //getting the main object response for the JSON and its array
            JSONObject responseObj = obj.getJSONObject("response");
            JSONArray newsArray = responseObj.getJSONArray("docs");

            //now looping through all the elements of the json array
            for (int i = 0; i < newsArray.length() -1; i++) {
                //getting the json object of the particular index inside the array
                JSONObject newsObject = newsArray.getJSONObject(i);
                String sectionObject = newsObject.getString("section_name");
                String mediaUrlObject = newsObject.getString("web_url");
                dateFormat = newsObject.getString("pub_date");
                JSONArray mediaArray = newsObject.getJSONArray("multimedia");
                for (int j = 0; j < mediaArray.length() - 1; j++) {
                    JSONObject mediaObject = mediaArray.getJSONObject(0);
                    StringBuilder myMediaImageUrl = new StringBuilder();
                    myMediaImageUrl.append(baseImageUrl);
                    if (mediaObject.getInt("height") == 75) {
                        myMediaImageUrl.append(mediaObject.getString("url"));
                        articleMediaUrl = myMediaImageUrl.toString();
                        articleMedia = mediaObject;
                    } else if (mediaObject.getInt("height") > 75) {
                        myMediaImageUrl.append(mediaObject.getString("url"));
                        articleMediaUrl = myMediaImageUrl.toString();
                        articleMedia = mediaObject;
                    } else {
                        JSONObject mediaDefaultObject = mediaArray.getJSONObject(0);
                        myMediaImageUrl.append(mediaDefaultObject.getString("url"));
                        articleMediaUrl = myMediaImageUrl.toString();
                        articleMedia = mediaArray.getJSONObject(0);
                    }
                }

                //creating a news object and giving them the values from json object
                News news = new News(newsObject.getString("snippet"), convertDate(dateFormat), sectionObject, articleMediaUrl, mediaUrlObject);

                //adding the news to newsList
                newsList.add(news);
            }
        }
        return newsList;

    }


    //Method to convert long date format to easier version to read
    public static String convertDate(String date) {

        //The SimpleDateFormat that will be use to check our JSON query value
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        try {
            Date shortDate = dateFormat.parse(date);
            //Returning the date they way I want it to look
            return new SimpleDateFormat("dd MMMM yyyy", Locale.US).format(Objects.requireNonNull(shortDate));

        } catch (ParseException e) {
            return "";
        }
    }

}
