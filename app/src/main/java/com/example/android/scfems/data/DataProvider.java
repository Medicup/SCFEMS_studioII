package com.example.android.scfems.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.android.scfems.R;
import com.example.android.scfems.data.DataContract.*;

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
      //  sUriMatcher.addURI(DataContract.CONTENT_AUTHORITY, DataContract.PATH_INCIDENTS,101);
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
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor;

        /*
         * int variable match called to use the uriMatcher.  Then use the switch
         * process to identify if there is a matching URI and what action to perform
         */
        int match = sUriMatcher.match(uri);
        switch (match){
            case DataContract.URI_INCIDENTS:
                cursor = db.query(IncidentEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
//            case INCIDENT_ID:
//                selection = IncidentEntry._ID + "=?";
//                selectionArgs = new String [] {String.valueOf(ContentUris.parseId(uri))};
//
//                cursor = db.query(IncidentEntry.TABLE_NAME, projection, selection, selectionArgs,
//                    null, null, sortOrder);
//                break;
            default:
                throw new IllegalArgumentException(DataContract.ERROR_ILLEGAL_ARG + uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
