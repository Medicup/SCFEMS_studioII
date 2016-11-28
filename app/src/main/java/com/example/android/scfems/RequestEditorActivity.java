package com.example.android.scfems;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.scfems.data.DataContract;
import com.example.android.scfems.data.DataContract.RequestsEntry;
import com.example.android.scfems.data.SendIncident;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RequestEditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
//TODO move lat / long to foundActivity
    public static final String TAG = RequestEditorActivity.class.getSimpleName();

    /* Identifier for loader */
    private static final int REQUEST_LOADER = 1;

    private Uri mCurrentIncidentUri;

    private String mUnitID;

    //private rowId to send through intent to FoundActivity
    private String mIntentUri;

    //Declare EditText values for method widw variables
    private EditText mIncidentNumber, mDate, mTime, mStreetNumber,
        mStreetName, mCity, mState;

    private Spinner mSpinnerIncidentRequest;

    //TODO update spinner
    private String TEST1 = "TEST one";
    private String TEST2 = "TEST two";
    private String mIncidentType = TEST1;

    //Variable checks to see if incident has been updated
    private boolean mIncidentsChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);


        getMenuOnCreate();
//        setMethodUiVariables();


//        setUpSpinnerIncidentType();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO add next icon

            }
        });

    }

    private void setUpSpinnerIncidentType(){
        ArrayAdapter incidentTypeSpinnerAdapter = ArrayAdapter.createFromResource(
                this,R.array.incident_type_options, R.layout.support_simple_spinner_dropdown_item);

       mSpinnerIncidentRequest.setAdapter(incidentTypeSpinnerAdapter);

        mSpinnerIncidentRequest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = (String)adapterView.getItemAtPosition(i);
                if(!TextUtils.isEmpty(selected)){
                    switch (i){
                        case 1:
                            mIncidentType = TEST1;
                            break;
                        case 2:
                            mIncidentType = TEST2;
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//TODO address back on change dialog

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_editor_save) {
            insertData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void insertData() {
        String incidentNumberString = mIncidentNumber.getText().toString().trim();
        String dateString = mDate.getText().toString().trim();
        String timeString = mTime.getText().toString().trim();
        String streetNumberString = mStreetNumber.getText().toString().trim();
        String streetNameString = mStreetName.getText().toString().trim();
        String cityString = mCity.getText().toString().trim();
        String stateString = mState.getText().toString().trim();


        /*
         * Check to make sure some value has been added to save a new incident.
         * If this is a new incident, i.e. there is no _id passed in the uri,
         * then check to make sure the text fields are not blank. If so, exit
         * and do not bother adding new incident.
         */
        if (mCurrentIncidentUri == null &&
                TextUtils.isEmpty(incidentNumberString) &&
                TextUtils.isEmpty(dateString) &&
                TextUtils.isEmpty(timeString) &&
                TextUtils.isEmpty(streetNumberString) &&
                TextUtils.isEmpty(streetNameString) &&
                TextUtils.isEmpty(cityString) &&
                TextUtils.isEmpty(stateString)) {
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(RequestsEntry.COLUMN_INCIDENT_NUMBER, incidentNumberString);


        //Check to see if we insert new incident or are editing a current one
        if (mCurrentIncidentUri == null) {
            Uri newUri = getContentResolver().insert(RequestsEntry.CONTENT_URI, contentValues);

            //check to see if insert worked. If not, newUri will return null
            if (newUri == null) {
                showToast(getString(R.string.editor_insert_incident_failed));
            } else {
                showToast(getString(R.string.editor_insert_incident_success));
            }
        } else {
            /*
             * Incident already exists. Instead of insert use the update method
             */
            int rowToUpdate = getContentResolver().update(mCurrentIncidentUri,
                    contentValues, null, null);

            /*
             * check to see if update worked
             */
            if (rowToUpdate == 0) {
                showToast(getString(R.string.editor_update_incident_failed));
            } else {
                showToast(getString(R.string.editor_update_incident_success));
            }
        }
    }

    private void spinnerSetup(){
        //Define spinner object from ID
        Spinner spinnerFoundType = (Spinner)findViewById(R.id.spinner_incident_request);

        //TODO Initialize array from unit variables
        String[] listArray = new String[]{"C1","C2","C3"};

        //populate the array list from unitArray
        List<String> itemsList = new ArrayList<>(Arrays.asList(listArray));

        //Create the arrayAdapter
        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, itemsList);

        //specify layout
        listAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFoundType.setAdapter(listAdapter);
    }

    private void showToast(String messageText){
        Toast.makeText(this,messageText,Toast.LENGTH_LONG).show();
    }

    //TODO fix onOptionsItemsSelected and address how to warn when not saved
    private void showUnsavedWarning(DialogInterface.OnClickListener discardButtonListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("TODO: SET ALERT MESSAGE");
        builder.setPositiveButton("TODO: YES BUTTON", discardButtonListener);
        builder.setNegativeButton("TODO: NO BUTTON", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialogInterface, int id){
                if (dialogInterface != null){
                    dialogInterface.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    /*
     * Need to update the back button functionality to account for the
     * change listener to monitor if the data has been updated.  If it has, it should
     * warn the user that there is unsaved data. To do this, overide the default
     * onBackPressed method
     */
    @Override
    public void onBackPressed() {
        //if no change in data, handle back button as normal.
        if (!mIncidentsChanged) {
            super.onBackPressed();
            return;
        }

        //data has changed. Implement overide to warn user of unsaved changes.

        DialogInterface.OnClickListener discardButtonListener =
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int id) {
                finish();
            }
        };
        showUnsavedWarning(discardButtonListener);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String [] projection = {
            DataContract.RequestsEntry._ID,
            DataContract.RequestsEntry.COLUMN_INCIDENT_NUMBER,

        };

        return new CursorLoader(this,
                mCurrentIncidentUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        //If null or less than 1 row exit onLoadFinished
        if (cursor == null || cursor.getCount()<1){
            return;
        }

        //When not null move to first and get cursor values
        if(cursor.moveToFirst()){
            //Get columnIndex for interested columns
            int rowIdColumnIndex = cursor.getColumnIndex(RequestsEntry._ID);
            int incidentNumberColumnIndex = cursor.getColumnIndex(RequestsEntry.COLUMN_INCIDENT_NUMBER);

            //get values from Cursor for the Index
            String incidentNumber = cursor.getString(incidentNumberColumnIndex);

            /*
             * update views on edit screen to show values from database for item
             * to edit.
            */
            mIncidentNumber.setText(incidentNumber);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //if loader reset clear all data out
        mIncidentNumber.setText("");
    }


    /*
     * Assumes when user touches screen something has changed
     */
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mIncidentsChanged = true;
            return false;
        }
    };

    public void checkIfEmpty(){
        //TODO
    }

    private void getMenuOnCreate(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void getIntentFromFoundMenu(){
        /*
         *getIntent from the MainActivity and check to see if we
         * need to open a new incident or update a current one
         */
        Intent intent = getIntent();
        mCurrentIncidentUri = intent.getData();
       // mUnitID = intent.getStringExtra(DataContract.RequestsEntry.COLUMN_UNIT_ID);

        //Empty id on uri results in new incident
        if(mCurrentIncidentUri == null){
            setTitle(getString(R.string.title_activity_request_editor_new));

        }else {
            mIntentUri = mCurrentIncidentUri.toString();
            setTitle(getString(R.string.title_activity_request_editor_edit));

            //initialize loader to read data to load from database
            getLoaderManager().initLoader(REQUEST_LOADER,null,this);
        }
    }

    private void setMethodUiVariables(){

        //Get all relevant views to consider
        mIncidentNumber = (EditText)findViewById(R.id.editText_incidentNumber);

        //setup onTouchListeners to watch for user changes to fields
//        mIncidentNumber.setOnTouchListener(mTouchListener);

    }

}
