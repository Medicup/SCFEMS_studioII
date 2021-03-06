package com.example.android.scfems;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.scfems.data.DataContract;
import com.example.android.scfems.data.DataContract.*;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    //Integer loader constant for Loader
    private static final int USAR_LOADER = 0;
    String mUnitType = "";
    String mUser = "";
    //adapter object for listView
    UsarCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent getIntentFromLogin = getIntent();
        mUnitType = getIntentFromLogin.getStringExtra(IncidentEntry.COLUMN_UNIT_ID);
        mUser = getIntentFromLogin.getStringExtra(IncidentEntry.COLUMN_USER);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);

            }
        });

        //find listView for incidents
        ListView incidentListView = (ListView)findViewById(R.id.main_list_view);

        //check for empty listView and display text of such
        View emptyView = findViewById(R.id.empty_view);
        incidentListView.setEmptyView(emptyView);

        mCursorAdapter = new UsarCursorAdapter(this,null);
        incidentListView.setAdapter(mCursorAdapter);

        /*
         * setOnItemClickListener used to identify when a specific row is
         * selected from the listView on the MainActivity.  It will pass the
         * _id in the URI it sends in the intent for the EditorActivity. This
         * allows the EditorActivity to differentiate a new incident and when to
         * load the current values for a selected incident from the listView.
         * This needs to be accepted in the onCreate on the EditorActivity.
         */
        incidentListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id){
                //Open new EditorActivity
                Intent intentToEditor = new Intent(MainActivity.this, EditorActivity.class);

                /*
                 * Append the id for the specific incident clicked from the listView to
                 * the ContentURI.  This allows the EditorActivity to differentiate between
                 * and empty (new incident) and an id present to load the current values
                 * for.
                 */
                Uri currentIncidentUri = ContentUris.withAppendedId(IncidentEntry.CONTENT_URI, id);

                //Set the intent with the uri from the specific row clicked
                String currentUriString = currentIncidentUri.toString();
                //intentToEditor.setData(currentIncidentUri);
                intentToEditor.putExtra("intentURI", currentUriString);
                intentToEditor.putExtra(IncidentEntry.COLUMN_UNIT_ID, mUnitType);

                startActivity(intentToEditor);
            }
        });

        //TODO need to call the asyncTask
        getLoaderManager().initLoader(USAR_LOADER, null,this);
    }


    @Override //TODO CHECK THIS
    protected void onStart(){
        super.onStart();

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
            //    displayIncidentsInfo();
                return true;
            case R.id.action_insert_test:
                insertTestData();
              //  displayIncidentsInfo();
                return true;
            case R.id.action_login:
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                return true;
            case R.id.action_request_edit:
                Intent request_intent = new Intent(MainActivity.this, RequestActivity.class);
                startActivity(request_intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertTestData(){//TODO remove insertTestData

        ContentValues contentValues = new ContentValues();
        contentValues.put(IncidentEntry.COLUMN_INCIDENT_NUMBER,"12345");
        contentValues.put(IncidentEntry.COLUMN_DATE, "10/10/1910");
        contentValues.put(IncidentEntry.COLUMN_TIME, "10:10");
        contentValues.put(IncidentEntry.COLUMN_UNIT_ID, mUnitType);
        contentValues.put(IncidentEntry.COLUMN_GPS_LAT, "1234");
        contentValues.put(IncidentEntry.COLUMN_GPS_LONG, "5678");
        contentValues.put(IncidentEntry.COLUMN_RECEIVED_INC_TYPE, "Medical");
        contentValues.put(IncidentEntry.COLUMN_FOUND_INC_TYPE, "NEED");
        contentValues.put(IncidentEntry.COLUMN_NOTES, "THIS IS A TEST");
        contentValues.put(IncidentEntry.COLUMN_STREET_NUMBER, "1115");
        contentValues.put(IncidentEntry.COLUMN_STREET_NAME, "Stoneham Dr");
        contentValues.put(IncidentEntry.COLUMN_CITY, "Groveland");
        contentValues.put(IncidentEntry.COLUMN_STATE,"FL");
        contentValues.put(IncidentEntry.COLUMN_USER, mUser);

        Uri newUri = getContentResolver().insert(IncidentEntry.CONTENT_URI, contentValues);
    }

    private void deleteAllData(){
        int rowsDeleted = getContentResolver().delete(IncidentEntry.CONTENT_URI, null, null);
        Log.v(LOG_TAG, rowsDeleted + " rows deleted from database");
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //Projection to define columns to return
        String [] projection = {
                IncidentEntry._ID,
                IncidentEntry.COLUMN_INCIDENT_NUMBER,
                IncidentEntry.COLUMN_UNIT_ID,
                IncidentEntry.COLUMN_STREET_NUMBER,
                IncidentEntry.COLUMN_STREET_NAME
        };

        return new CursorLoader(this,       //activity context
                IncidentEntry.CONTENT_URI,  //provider content uri
                projection,                 //columns to include
                null,                       //selection clause
                null,                       //selection arguments
                null);                      //default sort order
    }


    //@Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        //update with new cursor containing data
        mCursorAdapter.swapCursor(data);
    }

    //@Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //Callback when data to be deleted
        mCursorAdapter.swapCursor(null);
    }

}

