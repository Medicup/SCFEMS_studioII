package com.example.android.scfems.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.util.Log;
import com.example.android.scfems.R;
import com.example.android.scfems.data.DataContract.*;

import java.net.URLEncoder;
import java.util.Set;

/**
 * Project: SCFEMS
 * Created by stitched on 11/11/2016.
 */
public class DataProvider extends ContentProvider {
    public static final String TAG = DataProvider.class.getSimpleName();

    /*
     * Creates a UriMatcher object for the ContentProvider. Variable
     * declared with  lower s to indicate it is a static variable
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static{
        /*
         * calls for UriMatcher are declared here as static URI's for reference.
         * Each table is set with a three digit integer code and the specific action
         * to perform on the table has its own unique integer.  For example: the
         * incidents table is assigned 100 to query all and 101 to query a specific
         * row. _ID pattern is for a single row.
         */
        sUriMatcher.addURI(DataContract.CONTENT_AUTHORITY, DataContract.PATH_INCIDENTS,
                DataContract.URI_INCIDENTS);

        sUriMatcher.addURI(DataContract.CONTENT_AUTHORITY, DataContract.PATH_INCIDENTS + "/#",
                DataContract.URI_INCIDENT_ID);
    }

    // Database helper object
    private DbHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new DbHelper(getContext());
        return true;
    }

    /*
     * Cursor query requires a uri from the activity and can accept additional
     * parameter if desired.
     */
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        /*
         * Access the database using the dbHelper initiated with the onCreate method
         * in the DataProvider class. Then access the object as readable for the query
         */
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor;

        /*
         * int variable match called to use the uriMatcher.  Then use the switch
         * process to identify if there is a matching URI and what action to perform
         */
        int match = sUriMatcher.match(uri);
        switch (match){
            //All data in incident table
            case DataContract.URI_INCIDENTS:
                cursor = db.query(IncidentEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            //Unique row in incident table
            case DataContract.URI_INCIDENT_ID:
                selection = IncidentEntry._ID + "=?";
                selectionArgs = new String [] {String.valueOf(ContentUris.parseId(uri))};

                cursor = db.query(IncidentEntry.TABLE_NAME, projection, selection, selectionArgs,
                    null, null, sortOrder);
                break;

            //All data in Incident Type table
            case DataContract.URI_INCTYPES:
                cursor = db.query(IncidentTypeEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            //Unique row in incident type table
            case DataContract.URI_INCTYPE_ID:
                selection = IncidentTypeEntry._ID + "=?";
                selectionArgs = new String [] {String.valueOf(ContentUris.parseId(uri))};

                cursor = db.query(IncidentTypeEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            //All data in Requests Entry table
            case DataContract.URI_RESOURCE_REQUESTS:
                cursor = db.query(RequestsEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            //Unique row in incident type table
            case DataContract.URI_RESOURCE_REQUEST_ID:
                selection = RequestsEntry._ID + "=?";
                selectionArgs = new String [] {String.valueOf(ContentUris.parseId(uri))};

                cursor = db.query(RequestsEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            //All data in Resources Entry table
            case DataContract.URI_RESOURCES:
                cursor = db.query(ResourcesEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            //Unique row in resource type table
            case DataContract.URI_RESOURCE_ID:
                selection = ResourcesEntry._ID + "=?";
                selectionArgs = new String [] {String.valueOf(ContentUris.parseId(uri))};

                cursor = db.query(ResourcesEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            //All data in Unit Entry table
            case DataContract.URI_UNITS:
                cursor = db.query(UnitsEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            //Unique row in resource type table
            case DataContract.URI_UNIT_ID:
                selection = UnitsEntry._ID + "=?";
                selectionArgs = new String [] {String.valueOf(ContentUris.parseId(uri))};

                cursor = db.query(UnitsEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            //All data in Unit Entry table
            case DataContract.URI_USERS:
                cursor = db.query(UsersEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            //Unique row in resource type table
            case DataContract.URI_USER_ID:
                selection = UsersEntry._ID + "=?";
                selectionArgs = new String [] {String.valueOf(ContentUris.parseId(uri))};

                cursor = db.query(UsersEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;







            default:
                throw new IllegalArgumentException(DataContract.ERR_ILLEGAL_ARG_QUERY + uri);
        }

        /*
         * Set content notification uri so we can update the query when the database changes
         */
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case DataContract.URI_INCIDENTS:
                return IncidentEntry.CONTENT_LIST_TYPE;
            case DataContract.URI_INCIDENT_ID:
                return IncidentEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown uri + " + uri);
        }
    }
    //TODO need to implement MIME for gettype Section 3

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case DataContract.URI_INCIDENTS:
                return insertIncident(uri, contentValues);
            default:
                throw new IllegalArgumentException(DataContract.ERR_ILLEGAL_ARG_INSERT + uri);
        }
    }

    /*
     * Helper method for insert
     */
    private Uri insertIncident(Uri uri, ContentValues values){
        /*
         * sanity checks TODO finish sanity checks
         */
        String incidentNumber = values.getAsString(IncidentEntry.COLUMN_INCIDENT_NUMBER);
        Log.i(TAG, "SANITY CHECK***"); //TODO remove
        if (incidentNumber == null){
            throw new IllegalArgumentException("Incident requires incident number");
          }

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        long id = database.insert(IncidentEntry.TABLE_NAME, null, values);

        /*
         * check to see that the insert was successful. Return of a -1 long
         * is the result of an error. Log the error for debug
         */
        if (id == -1){
            Log.d(TAG, "Failed to insert row for " + uri);
            return null;
        }

        /*
         * Notify contentResolver that the dataset has changed and should
         * the contentResolver should be refreshed.
         * Null allows for cursorResolver to be recalled
         */
        getContext().getContentResolver().notifyChange(uri,null);

        //Return URI with inserted ID
        return ContentUris.withAppendedId(uri, id);
    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);

        //Variable to store number of rows deleted
        int rowsDeleted;

        switch (match){
            case DataContract.URI_INCIDENTS:
                rowsDeleted = database.delete(IncidentEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case DataContract.URI_INCIDENT_ID:
                selection = IncidentEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted =  database.delete(IncidentEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        //if one or more rows deleted notify the contentResolver

        if(rowsDeleted != 0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {

        final int match = sUriMatcher.match(uri);

        switch (match){
            case DataContract.URI_INCIDENTS:
                return updateIncident(uri, contentValues, selection, selectionArgs);
            case DataContract.URI_INCIDENT_ID:
                selection = IncidentEntry._ID + "=?";
                selectionArgs = new String [] {String.valueOf(ContentUris.parseId(uri))};

                return updateIncident(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateIncident(Uri uri, ContentValues contentValues, String selection,
                               String[] selectionArgs){
//TODO need to fix. uri is not called
        if(contentValues.containsKey(IncidentEntry.COLUMN_INCIDENT_NUMBER)){
            String incidentNumber = contentValues.getAsString(IncidentEntry.COLUMN_INCIDENT_NUMBER);
            if(incidentNumber == null){
                throw new IllegalArgumentException("Incident requires incident number");
            }
        }

        if (contentValues.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ///update database and return number of rows updated
        int rowsUpdated = database.update(IncidentEntry.TABLE_NAME, contentValues,
                selection, selectionArgs);

        /*
         * Check to make sure at least one row updated. If so, notify change to contentResolver
         */
        if (rowsUpdated != 0){
            getContext().getContentResolver().notifyChange(uri,null);
        }

        //return the total rows updated
        return rowsUpdated;
    }
}
