package com.example.mynews;

import com.example.mynews.Models.News;
import com.example.mynews.Utils.JSONQueryParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    @Test
    public void JsonIsCorrectResponse() {
        List<News> newsList;
        File jsonAPIFile = new File(Paths.get("src/test/java/com/example/mynews/testValidJSONApi.json").toAbsolutePath().toString());
        try {
            FileInputStream inputStream = new FileInputStream((jsonAPIFile));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            final StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            JSONQueryParser JSONQuery = new JSONQueryParser();
            newsList = JSONQuery.parseAPIResponse(stringBuilder.toString());
            JSONObject mainObject = new JSONObject(stringBuilder.toString());
            int object = mainObject.getInt("num_results");
            assertEquals(newsList.size(), object);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void JsonResponseIsMissingUrl() {
        List<News> newsList;
        File jsonAPIFile = new File(Paths.get("src/test/java/com/example/mynews/testWrongJSONApi.json").toAbsolutePath().toString());
        try {
            FileInputStream inputStream = new FileInputStream((jsonAPIFile));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            final StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            JSONQueryParser JSONQuery = new JSONQueryParser();
            newsList = JSONQuery.parseAPIResponse(stringBuilder.toString());

            assertTrue(newsList.isEmpty());


        } catch (IOException | JSONException e) {
            assertEquals("JSONObject[\"url\"] not found.",e.getMessage());
        }
    }

}