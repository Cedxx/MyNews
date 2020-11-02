package com.example.mynews;

import com.example.mynews.Utils.JSONQueryParser;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void JsonIsCorrectResponse() {
        File jsonAPIFile = new File("C:\\Projects\\MyNews\\app\\src\\test\\java\\com\\example\\mynews\\testValideJSONApi.json");
        try {
            FileInputStream inputStream = new FileInputStream((jsonAPIFile));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            final StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONQueryParser JSONQuery = new JSONQueryParser();
        JSONQuery.parseAPIResponse(toString());
        //assertEquals();
    }
}