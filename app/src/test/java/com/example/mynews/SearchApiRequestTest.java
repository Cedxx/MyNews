package com.example.mynews;

import org.apache.http.ProtocolVersion;
import org.apache.http.message.BasicHttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.BasicNetwork;
import com.example.mynews.Models.News;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SearchApiRequestTest {

    private List<News> mNewsList;

    @Test
    public void canSendRequest(){
        mNewsList = new ArrayList<>();

        MockHttpStack mockHttpStack = new MockHttpStack();
        BasicHttpResponse fakeResponse = new BasicHttpResponse(new ProtocolVersion("HTTP",1,1),200, "OK");
        File jsonAPIFile = new File("com/example/mynews/testValideJSONApi.json");
        try {
            FileInputStream inputStream = new FileInputStream((jsonAPIFile));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            final StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            //fakeResponse.setEntity(stringBuilder.toString());
            mockHttpStack.setResponseToReturn(fakeResponse);
            BasicNetwork basicNetwork = new BasicNetwork(mockHttpStack);
            Request<String> fakeRequest = new Request<String>(Request.Method.GET, "http://test.com", null) {
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    return null;
                }

                @Override
                protected void deliverResponse(String response) {
                    //assertEquals(stringBuilder.toString(), response);
                    try {
                        JSONObject obj = new JSONObject(response);
                        JSONArray newsArray = obj.getJSONArray("results");

                        for (int i = 0; i < newsArray.length(); i++) {
                            //getting the json object of the particular index inside the array
                            JSONObject newsObject = newsArray.getJSONObject(i);
                            String sectionObject = newsObject.getString("section");
                            String mediaUrlObject = newsObject.getString("url");
                            JSONArray mediaArray = newsObject.getJSONArray("media");
                            JSONObject mediaIndex;
                            if(mediaArray.length() > 0){
                                JSONObject mediaObject = mediaArray.getJSONObject(0);
                                JSONArray mediaData = mediaObject.getJSONArray("media-metadata");
                                mediaIndex = mediaData.getJSONObject(0);

                                //creating a news object and giving them the values from json object
                                News news = new News(newsObject.getString("title"), newsObject.getString("published_date"), sectionObject, mediaIndex.getString("url"), mediaUrlObject);

                                //adding the news to newsList
                                mNewsList.add(news);
                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    assertEquals(mNewsList.toString(), response);

                }
            };
        } catch (Exception e){
        }
    }

    @Test
    public void JsonIsNotCorrectResponse(){
        mNewsList = new ArrayList<>();

        MockHttpStack mockHttpStack = new MockHttpStack();
        BasicHttpResponse fakeResponse = new BasicHttpResponse(new ProtocolVersion("HTTP",1,1),200, "OK");
        File jsonAPIFile = new File("com/example/mynews/testWrongJSONApi.json");
        try {
            FileInputStream inputStream = new FileInputStream((jsonAPIFile));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            final StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            //fakeResponse.setEntity(stringBuilder.toString());
            mockHttpStack.setResponseToReturn(fakeResponse);
            BasicNetwork basicNetwork = new BasicNetwork(mockHttpStack);
            Request<String> fakeRequest = new Request<String>(Request.Method.GET, "http://test.com", null) {
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    return null;
                }

                @Override
                protected void deliverResponse(String response) {
                    //assertEquals(stringBuilder.toString(), response);
                    try {
                        JSONObject obj = new JSONObject(response);
                        JSONArray newsArray = obj.getJSONArray("results");

                        for (int i = 0; i < newsArray.length(); i++) {
                            //getting the json object of the particular index inside the array
                            JSONObject newsObject = newsArray.getJSONObject(i);
                            String sectionObject = newsObject.getString("section");
                            String mediaUrlObject = newsObject.getString("url");
                            JSONArray mediaArray = newsObject.getJSONArray("media");
                            JSONObject mediaIndex;
                            if(mediaArray.length() > 0){
                                JSONObject mediaObject = mediaArray.getJSONObject(0);
                                JSONArray mediaData = mediaObject.getJSONArray("media-metadata");
                                mediaIndex = mediaData.getJSONObject(0);

                                //creating a news object and giving them the values from json object
                                News news = new News(newsObject.getString("title"), newsObject.getString("published_date"), sectionObject, mediaIndex.getString("url"), mediaUrlObject);

                                //adding the news to newsList
                                mNewsList.add(news);
                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    assertEquals(mNewsList.toString(), response);

                }
            };
        } catch (Exception e){
        }

    }
}
