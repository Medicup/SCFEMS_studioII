package com.example.android.scfems;

import android.content.ContentValues;
import android.content.Intent;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.scfems.data.DataContract;
import com.example.android.scfems.data.DataContract.*;
import com.example.android.scfems.data.DbHelper;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    //TODO correct access to sql db
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
              //TODO Consider Snackbar action
              Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

//        dbHelper = new DbHelper(this);
    }

    @Override //TODO CHECK THIS
    protected void onStart(){
        super.onStart();
        displayIncidentsInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_main_delete_all:
                deleteAllData();
                displayIncidentsInfo();
                return true;
            case R.id.action_insert_test:
                insertTestData();
                displayIncidentsInfo();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayIncidentsInfo(){
        /*
         * Identify columns to return on query
         */
        String [] projection = {
                IncidentEntry._ID,
                IncidentEntry.COLUMN_INCIDENT_NUMBER,
                IncidentEntry.COLUMN_UNIT_ID,
                IncidentEntry.COLUMN_STREET_NUMBER,
                IncidentEntry.COLUMN_STREET_NAME
        };


        Cursor cursor = getContentResolver().query(IncidentEntry.CONTENT_URI,projection,null,null,null);


        TextView displayView = (TextView)findViewById(R.id.text_view_incident_sum);

        try{

            displayView.setText("Number of rows in incidents DB: " + cursor.getCount());
            displayView.append("\n" + IncidentEntry._ID + " - " +
                IncidentEntry.COLUMN_INCIDENT_NUMBER + " - " +
                IncidentEntry.COLUMN_UNIT_ID + " - " +
                IncidentEntry.COLUMN_STREET_NUMBER + " - " +
                IncidentEntry.COLUMN_STREET_NAME);

            int idColumnIndex = cursor.getColumnIndex(IncidentEntry._ID);
            int incidentNumberColumnIndex = cursor.getColumnIndex(IncidentEntry.COLUMN_INCIDENT_NUMBER);
            int unitColumnIndex = cursor.getColumnIndex(IncidentEntry.COLUMN_UNIT_ID);
            int streetNumberIndex = cursor.getColumnIndex(IncidentEntry.COLUMN_STREET_NUMBER);
            int streetNameIndex = cursor.getColumnIndex(IncidentEntry.COLUMN_STREET_NAME);

            while (cursor.moveToNext()){
                int currentID = cursor.getInt(idColumnIndex);
                int currentIncNumber = cursor.getInt(incidentNumberColumnIndex);
                String currentUnitId = cursor.getString(unitColumnIndex);
                int currentStreetNumber = cursor.getInt(streetNumberIndex);
                String currentStreetName = cursor.getString(streetNameIndex);

                displayView.append(("\n" + currentID + " - " +
                    currentIncNumber + " - " +
                    currentUnitId + " - " +
                    currentStreetNumber + " - " +
                    currentStreetName));
            }
        }finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void insertTestData(){//TODO remove insertTestData

        ContentValues contentValues = new ContentValues();
        contentValues.put(IncidentEntry.COLUMN_INCIDENT_NUMBER,"12345");
        contentValues.put(IncidentEntry.COLUMN_DATE, "10/10/1910");
        contentValues.put(IncidentEntry.COLUMN_TIME, "10:10");
        contentValues.put(IncidentEntry.COLUMN_UNIT_ID, "C2");
        contentValues.put(IncidentEntry.COLUMN_GPS_LAT, "1234");
        contentValues.put(IncidentEntry.COLUMN_GPS_LONG, "5678");
        contentValues.put(IncidentEntry.COLUMN_RECEIVED_INC_TYPE, "Medical");
        contentValues.put(IncidentEntry.COLUMN_FOUND_INC_TYPE, "Trauma");
        contentValues.put(IncidentEntry.COLUMN_NOTES, "THIS IS A TEST");
        contentValues.put(IncidentEntry.COLUMN_STREET_NUMBER, "1115");
        contentValues.put(IncidentEntry.COLUMN_STREET_NAME, "Stoneham Dr");
        contentValues.put(IncidentEntry.COLUMN_STATE,"FL");

        Uri newUri = getContentResolver().insert(IncidentEntry.CONTENT_URI, contentValues);
    }

    private void deleteAllData(){

        int rowsDeleted = getContentResolver().delete(IncidentEntry.CONTENT_URI, null, null);
        Log.v(TAG, rowsDeleted + " rows deleted from database");

    }
}
