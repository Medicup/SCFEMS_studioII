package com.example.android.scfems;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
//              TODO Consider Snackbar action
//              Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        dbHelper = new DbHelper(this);
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
        if (id == R.id.action_insert_test) {
            insertTestData();
            displayIncidentsInfo();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayIncidentsInfo(){
        //TODO clean this up.
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String [] projection = {
                incidentEntry._ID,
                incidentEntry.COLUMN_INCIDENT_NUMBER,
                incidentEntry.COLUMN_UNIT_ID,
                incidentEntry.COLUMN_STREET_NUMBER,
                incidentEntry.COLUMN_STREET_NAME
        };


        Cursor cursor = db.query(
                incidentEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        TextView displayView = (TextView)findViewById(R.id.text_view_incident_sum);

        try{

            displayView.setText("Number of rows in incidents DB: " + cursor.getCount());
            displayView.append("\n" + incidentEntry._ID + " - " +
                incidentEntry.COLUMN_INCIDENT_NUMBER + " - " +
                incidentEntry.COLUMN_UNIT_ID + " - " +
                incidentEntry.COLUMN_STREET_NUMBER + " - " +
                incidentEntry.COLUMN_STREET_NAME);

            int idColumnIndex = cursor.getColumnIndex(incidentEntry._ID);
            int incidentNumberColumnIndex = cursor.getColumnIndex(incidentEntry.COLUMN_INCIDENT_NUMBER);
            int unitColumnIndex = cursor.getColumnIndex(incidentEntry.COLUMN_UNIT_ID);
            int streetNumberIndex = cursor.getColumnIndex(incidentEntry.COLUMN_STREET_NUMBER);
            int streetNameIndex = cursor.getColumnIndex(incidentEntry.COLUMN_STREET_NAME);

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
            cursor.close();
        }
    }

    private void insertTestData(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(incidentEntry.COLUMN_INCIDENT_NUMBER,"12345");
        contentValues.put(incidentEntry.COLUMN_DATE, "10/10/1910");
        contentValues.put(incidentEntry.COLUMN_TIME, "10:10");
        contentValues.put(incidentEntry.COLUMN_UNIT_ID, "C2");
        contentValues.put(incidentEntry.COLUMN_GPS_LAT, "1234");
        contentValues.put(incidentEntry.COLUMN_GPS_LONG, "5678");
        contentValues.put(incidentEntry.COLUMN_RECEIVED_INC_TYPE, "Medical");
        contentValues.put(incidentEntry.COLUMN_FOUND_INC_TYPE, "Trauma");
        contentValues.put(incidentEntry.COLUMN_NOTES, "THIS IS A TEST");
        contentValues.put(incidentEntry.COLUMN_STREET_NUMBER, "1115");
        contentValues.put(incidentEntry.COLUMN_STREET_NAME, "Stoneham Dr");
        contentValues.put(incidentEntry.COLUMN_STATE,"FL");

        long newRowID = db.insert(incidentEntry.TABLE_NAME, null, contentValues);
        Log.i(TAG, "New row ID = " + newRowID);
    }
}
