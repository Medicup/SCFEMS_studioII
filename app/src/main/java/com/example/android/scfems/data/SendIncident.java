package com.example.android.scfems.data;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.android.scfems.MainActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Project: SCFEMS
 * Created by stitched on 11/24/2016.
 */
public class SendIncident extends AsyncTask<String, Void, String>{



    @Override
    protected String doInBackground(String... strings) {
        try {
            sendIncident();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void sendIncident() throws IOException{
        String urlString = "http://192.168.1.250/get_incident.php";
        String response = "";
        URL url = new URL(urlString);
        try{
            String jsonIncident = "";

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000 /* milliseconds */);
            connection.setConnectTimeout(15000 /* milliseconds */);
            //TODO TRYING THE BELOW LINE:

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("incidentID","12345");

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(jsonObject.toString());
            writer.flush();
            //connection.connect();

//            OutputStream outputStream = connection.getOutputStream();
//            outputStream.write(jsonIncident.getBytes("UTF-8"));
//            outputStream.close();
            //testing above code
            /*JSONObject jsonObject = new JSONObject();
            jsonObject.put("incidentID","12345");
            //OutputStream outputStream = connection.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(URLEncoder.encode(jsonObject.toString(),"UTF-8"));
            writer.flush();*/
            //writer.close();
//
//            jsonIncident = jsonObject.toString();
            //connection.connect();
            Log.i("***",jsonIncident);

            //get response from service
            StringBuilder sb = new StringBuilder();
            int HttpResult = connection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                System.out.println("" + sb.toString());
            } else {
                System.out.println(connection.getResponseMessage());
            }

        }catch (Exception e){
            Log.e("Exception", e.getMessage());
        }
    }
}
