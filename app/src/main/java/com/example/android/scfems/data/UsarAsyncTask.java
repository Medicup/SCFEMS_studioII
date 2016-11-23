package com.example.android.scfems.data;

import android.os.AsyncTask;
import android.util.Log;

import com.example.android.scfems.LoginActivity;
import com.example.android.scfems.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Project: SCFEMS
 * Created by stitched on 11/23/2016.
 */
public class UsarAsyncTask extends AsyncTask<String, Void, String> {
    private static final String LOG_TAG = UsarAsyncTask.class.getSimpleName();

    //Setup string array to parse for updates from server
    private String[] urlStrings = {DataContract.USER_URL, DataContract.UNIT_URL,
            DataContract.RESOURCE_URL, DataContract.INCIDENT_TYPE_URL,
            DataContract.REQ_RESOURCE_URL};

    @Override
    protected String doInBackground(String... strings) {
        /*
         * for each urlString matching a table on the server, fetch and
         * parse data to update tables
         */
        try{
            for (int i = 0; i < urlStrings.length; i++){
                downloadData(urlStrings[i]);
                //TODO update database
            }
        }catch (IOException e){
            Log.e(LOG_TAG, "DoInBackground error: ", e);
        }
        return null;
    }


    private String downloadData (String urlString) throws IOException{
        String response = "";
        URL url = new URL(urlString);
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000 /* milliseconds */);
            connection.setConnectTimeout(15000 /* milliseconds */);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            //TODO add output
            connection.connect();
            String data;
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));

            if (reader == null ){
                return null;
            }
            //Read URL data to implement into
            while ((data = reader.readLine()) != null) {
                response += data;
            }
            //TODO JSON import
            Log.i(LOG_TAG, " RESPONSE: " + response);
        }catch (IOException e){
            Log.e(LOG_TAG, " Unable to parse: ", e);
        }
        return response;
    }

}
