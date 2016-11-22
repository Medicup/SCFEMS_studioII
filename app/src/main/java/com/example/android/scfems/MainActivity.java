package com.example.android.scfems;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.scfems.data.DataContract;
import com.example.android.scfems.data.DataContract.*;
import com.example.android.scfems.data.DbHelper;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    public static final String TAG = MainActivity.class.getSimpleName();

    //Integer loader constant for Loader
    private static final int USAR_LOADER = 0;

    //adapter object for listView
    UsarCursorAdapter mCursorAdapter;

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
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);

                /*
                 * Append the id for the specific incident clicked from the listView to
                 * the ContentURI.  This allows the EditorActivity to differentiate between
                 * and empty (new incident) and an id present to load the current values
                 * for.
                 */
                Uri currentIncidentUri = ContentUris.withAppendedId(IncidentEntry.CONTENT_URI, id);

                //Set the intent with the uri from the specific row clicked
                intent.setData(currentIncidentUri);

                startActivity(intent);
            }
        });

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
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
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
        contentValues.put(IncidentEntry.COLUMN_CITY, "Groveland");
        contentValues.put(IncidentEntry.COLUMN_STATE,"FL");

        Uri newUri = getContentResolver().insert(IncidentEntry.CONTENT_URI, contentValues);
    }

    private void deleteAllData(){
        int rowsDeleted = getContentResolver().delete(IncidentEntry.CONTENT_URI, null, null);
        Log.v(TAG, rowsDeleted + " rows deleted from database");
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
