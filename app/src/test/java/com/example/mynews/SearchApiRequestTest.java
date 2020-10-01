package com.example.mynews;

import org.apache.http.ProtocolVersion;
import org.apache.http.message.BasicHttpResponse;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.HttpStack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;

import static org.junit.Assert.assertEquals;

public class SearchApiRequestTest {
    @Test
    public void canSendRequest(){
        MockHttpStack mockHttpStack = new MockHttpStack();
        BasicHttpResponse fakeResponse = new BasicHttpResponse(new ProtocolVersion("HTTP",1,1),200, "OK");
        File jsonAPIFile = new File("com/example/mynews/testJSONApi.json");
        FileInputStream inputStream = new FileInputStream((jsonAPIFile));
        BufferedReader bufferedReader = new BufferedReader(inputStream,"utf-8");
        final StringBuilder stringBuilder = new StringBuilder();
        String line;
        while((line = bufferedReader.readLine()) != null){
            stringBuilder.append(line);
        }
        fakeResponse.setEntity(stringBuilder.toString());
        mockHttpStack.setResponseToReturn(fakeResponse);
        BasicNetwork basicNetwork = new BasicNetwork(mockHttpStack);
        Request<String> fakeRequest = new Request<String>(Request.Method.GET, "http://test.com", null) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return null;
            }

            @Override
            protected void deliverResponse(String response) {
                assertEquals(stringBuilder.toString(), response);

            }
        };
    }
}
