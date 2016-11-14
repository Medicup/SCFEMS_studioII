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

    /*
    * Unique integers assigned for each UriMatch
    */
    public static final int URI_INCIDENTS = 100;
    public static final int URI_INCIDENT_ID = 101;

    /*
     * CONTENT_AUTHORITY represents the entire name for the content provider
     * It is found in the AndroidManifest.xml and necessary for the app to
     * forward all content requests.  The provider section in the AndroidManifest
     * then directs the requests to the provider class identified as the
     * provider name: .data.DataProvider
     */
    public static final String CONTENT_AUTHORITY = "com.example.android.scfems";

    /*
     * BASE_CONTEENT_URI is derived from parsing the URI of the
     * "content://" string with the CONTENT_AUTHORITY.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /*
     * Each table must identify a unique PATH outside of their respective
     * class.  This will be used with the BASE_CONTENT URI to uniquely
     * identify them
     */
    public static final String PATH_INCIDENTS = "incidents";
    public static final String PATH_INCIDENT_ID = "incidents/#";
    public static final String PATH_REQUESTS = "requests";

    /*
     * Global static error messages
     */
    public static final String ERR_ILLEGAL_ARG_QUERY = "Cannot query unknown URI ";
    public static final String ERR_ILLEGAL_ARG_INSERT = "Insertion is not supported for ";

    // Inner class for incidents table
    public static final class IncidentEntry implements BaseColumns {

        /*
         * CONTENT_URI is the unique uri for the table name. It
         * concatenates the BASE_CONTENT_URI with the unique table name.
         * Each table will have its own unique CONTENT_URI configured
         */
        public static final Uri CONTENT_URI =
            BASE_CONTENT_URI.withAppendedPath(BASE_CONTENT_URI,PATH_INCIDENTS);


        public static final String CONTENT_LIST_TYPE =
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
        public static final String COLUMN_CITY = "city";
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

    public static class RequestsEntry implements BaseColumns {
        // URI for access
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_REQUESTS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                        CONTENT_AUTHORITY + PATH_REQUESTS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                        CONTENT_AUTHORITY + PATH_REQUESTS;


        public static final String TABLE_NAME = "requests";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_INCIDENT_NUMBER = "incidentID";
        public static final String COLUMN_UNIT_ID = "unitID";
        public static final String COLUMN_REQUEST_RESOURCE = "resource";
        public static final String COLUMN_REQUEST_STATUS = "status";

        public static final int STATUS_REQUESTED = 0;
        public static final int STATUS_PENDING = 1;
        public static final int STATUS_FILLED = 2;
        public static final int STATUS_DENIED = 3;
    }

    public static class UnitsEntry implements BaseColumns{

    }

    public static class UsersEntry implements BaseColumns{

    }

    public static class CitiesEntry implements BaseColumns{

    }

    public static class StatesEntry implements BaseColumns{

    }

}
