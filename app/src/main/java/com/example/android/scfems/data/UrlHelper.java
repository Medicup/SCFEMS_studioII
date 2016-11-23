package com.example.android.scfems.data;

import android.app.LoaderManager;
import android.app.usage.UsageEvents;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Project: SCFEMS
 * Created by stitched on 11/22/2016.
 */
public class UrlHelper {
    public static final String LOG_TAG = UrlHelper.class.getSimpleName();



    public static String fetchUrlData(String urlString){
        URL url = makeUrl(urlString);

        String jsonResponse = null;

        try {
            jsonResponse = getHttp(url);
        }catch (IOException e){
            Log.e(LOG_TAG, " Error closing input stream ", e);
        }

        return jsonResponse;
    }

    private static URL makeUrl(String urlString){
        URL url = null;

        try {
            url = new URL(urlString);
        }catch (MalformedURLException e){
            Log.e(LOG_TAG, " Error creating URL ", e);
        }
        return url;
    }

    private static String getHttp(URL url)throws IOException{
        String jsonFromUrl = "";

        if (url == null){
            return jsonFromUrl;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            Log.i(LOG_TAG, urlConnection.getResponseMessage());
        }catch (IOException e){
            Log.e(LOG_TAG,  "Error code: " + urlConnection.getResponseCode());
        }finally {
            if(urlConnection != null){
                inputStream.close();
            }
        }
        return jsonFromUrl;
    }
}
