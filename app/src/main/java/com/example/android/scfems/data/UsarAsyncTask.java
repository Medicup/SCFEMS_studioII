package com.example.android.scfems.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Switch;

import com.example.android.scfems.LoginActivity;
import com.example.android.scfems.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

/**
 * Project: SCFEMS
 * Created by stitched on 11/23/2016.
 */
public class UsarAsyncTask extends AsyncTask<String, Void, String> {
    private static final String LOG_TAG = UsarAsyncTask.class.getSimpleName();

    DbHelper dbHelper ;

    DataProvider dataProvider = new DataProvider();

    //Setup string array to parse for updates from server
    private String[] urlStrings = {//DataContract.USER_URL,
            DataContract.UNIT_URL,
            //DataContract.RESOURCE_URL, DataContract.INCIDENT_TYPE_URL,
            //DataContract.REQ_RESOURCE_URL
            };

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
            String table = "units";
            JSONObject object = new JSONObject(response);
            Log.i(LOG_TAG," NEW RESPONSE: " + object.toString());

            JSONArray jsonArray = object.getJSONArray(table);

            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject row = jsonArray.getJSONObject(i);
              //  Log.i(LOG_TAG, row.toString()); //TODO remove log

                ContentValues contentValues = new ContentValues();

                String unitID = row.getString("unitID");
                String unitDescription = row.getString("unitDescription");

                contentValues.put(DataContract.UnitsEntry.COLUMN_UNIT_ID,unitID);
                contentValues.put(DataContract.UnitsEntry.COLUMN_UNIT_DESC,unitDescription);

                Log.i(LOG_TAG, " CONTENT VALUES: " + contentValues.toString());

                SQLiteDatabase database = dbHelper.getWritableDatabase();

                database.insert(DataContract.UnitsEntry.TABLE_NAME,null,contentValues);

                //DataProvider dataProvider = new DataProvider();
//                Log.i(LOG_TAG, DataContract.BASE_CONTENT_URI+"/"+DataContract.PATH_UNIT);
//                Uri uriUnit = Uri.parse(DataContract.BASE_CONTENT_URI+"/"+DataContract.PATH_UNIT);
//                dataProvider.update(uriUnit,contentValues,null,null);
                //dataProvider.insert(Uri.parse(DataContract.BASE_CONTENT_URI + "/" +
                 //       DataContract.PATH_UNIT),contentValues);

            }

        }catch (IOException e){
            Log.e(LOG_TAG, " Unable to parse: ", e);
        }catch (JSONException e){
            Log.e(LOG_TAG, " JSONObject error: ", e);
        }
        return response;
    }

}
