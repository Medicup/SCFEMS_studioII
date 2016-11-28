package com.example.android.scfems.data;

import android.app.Activity;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Project: SCFEMS
 * Created by stitched on 11/28/2016.
 */
public class PhpHelper extends Activity {
    RequestQueue requestQueue;
    String sendData = "http://192.168.1.250/get_incident.php";
    String showUsers = "http://192.168.1.250/read_allUser.php";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        requestQueue = Volley.newRequestQueue(getApplicationContext());


    }



}
