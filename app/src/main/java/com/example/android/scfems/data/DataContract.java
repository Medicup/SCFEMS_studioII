package com.example.android.scfems.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import java.io.FileReader;
import java.net.URI;

/**
 * Project: SCFEMS
 * Created by stitched on 11/11/2016.
 */
public final class DataContract {
    public static final String TAG = DataContract.class.getSimpleName();

    /*
     * making constructor private prevents someone from accidentally
     * instantiating the contract class
     */
    private DataContract(){}

    //CONTENT_AUTHORITY represents the entire name for the content provider
    public static final String CONTENT_AUTHORITY = "com.example.android.scfems";

    //USE CONTENT_AUTHORITY to make the base URI or "domain name" for content provider
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Static paths for looking at incidents
    public static final String PATH_INCIDENTS = "incidents";
    public static final String PATH_REQUESTS = "requests";

    // Inner class for incidents table
    public static class incidentEntry implements BaseColumns {

        // URI for access
        public static final Uri CONTENT_URI =
            BASE_CONTENT_URI.buildUpon().appendPath(PATH_INCIDENTS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                        CONTENT_AUTHORITY + PATH_INCIDENTS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                        CONTENT_AUTHORITY + PATH_INCIDENTS;


        public static final String TABLE_NAME = "incidents";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_INCIDENT_NUMBER = "incidentNumber";
        public static final String COLUMN_UNIT_ID = "unitId";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_GPS_LONG = "longitude";
        public static final String COLUMN_GPS_LAT = "latitude";
        public static final String COLUMN_STREET_NUMBER = "streetNumber";
        public static final String COLUMN_STREET_NAME = "streetName";
        public static final String COLUMN_STATE = "state";
        public static final String COLUMN_RECEIVED_INC_TYPE = "incidentReceived";
        public static final String COLUMN_FOUND_INC_TYPE = "incidentFound";
        public static final String COLUMN_NOTES = "notes";
        public static final String COLUMN_STATUS = "incidentStatus";

        //Constant values
        public static final int STATUS_OPEN = 0;
        public static final int STATUS_CLOSED = 1;

        /*TODO consider working a solution with below for individual rows
        public static Uri buildLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        } */
    }

    public static class requestsEntry implements BaseColumns {

    }

    public static class unitsEntry implements BaseColumns{

    }

    public static class usersEntry implements BaseColumns{

    }

    public static class citiesEntry implements BaseColumns{

    }

    public static class statesEntry implements BaseColumns{

    }

}
