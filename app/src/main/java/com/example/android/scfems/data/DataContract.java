package com.example.android.scfems.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import java.io.FileReader;
import java.net.URI;
import java.net.URL;

/**
 * Project: SCFEMS
 * Created by stitched on 11/11/2016.
 */
public final class DataContract {
    public static final String TAG = DataContract.class.getSimpleName();
    /*
     * Data url to parse
     */
    public static final String INCIDENT_TYPE_URL = "http://192.168.1.250/read_allIncidentType.php";
    public static final String USER_URL = "http://192.168.1.250/read_allUser.php";
    public static final String UNIT_URL = "http://192.168.1.250/read_allUnit.php";
    public static final String RESOURCE_URL = "http://192.168.1.250/read_allResources.php";
    public static final String REQ_RESOURCE_URL = "http://192.168.1.250/read_allResRequests.php";
    public static final String INCIDENT_TYPE_REQUEST = "_incTypeRequest";
    /*
    * Unique integers assigned for each UriMatch
    */
    public static final int URI_INCIDENTS = 100;
    public static final int URI_INCIDENT_ID = 101;
    public static final int URI_INCTYPES = 110;
    public static final int URI_INCTYPE_ID = 111;
    public static final int URI_RESOURCE_REQUESTS = 120;
    public static final int URI_RESOURCE_REQUEST_ID = 121;
    public static final int URI_RESOURCES = 130;
    public static final int URI_RESOURCE_ID = 131;
    public static final int URI_UNITS = 140;
    public static final int URI_UNIT_ID = 141;
    public static final int URI_USERS = 150;
    public static final int URI_USER_ID = 151;
    public static final String TABLE_NAME = "unit";
    public static final String _ID = BaseColumns._ID;
    public static final String COLUMN_UNIT_ID = "unitID";
    public static final String COLUMN_UNIT_DESC = "unitDesc";
    /* Uri string to pass from one intent to another */
    public static final String URI_INTENT_STRING = "setUriString";
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
    public static final String PATH_INCTYPE = "incType";
    public static final String PATH_REQUESTS = "requests";
    public static final String PATH_RESOURCES = "resources";
    public static final String PATH_UNIT = "unit";
    public static final String PATH_USER = "user";
    /*
     * Global static error messages
     */
    public static final String ERR_ILLEGAL_ARG_QUERY = "Cannot query unknown URI ";
    public static final String ERR_ILLEGAL_ARG_INSERT = "Insertion is not supported for ";
    public static final String ERR_ILLEGAL_ARG_INTENT = " Error passing null Uri value through Intent";

    /*
     * making constructor private prevents someone from accidentally
     * instantiating the contract class
     */
    private DataContract() {
    }

    // Inner class for incidents table
    public static final class IncidentEntry implements BaseColumns {
        /*
         * CONTENT_URI is the unique uri for the table name. It
         * concatenates the BASE_CONTENT_URI with the unique table name.
         * Each table will have its own unique CONTENT_URI configured
         */
        public static final Uri CONTENT_URI =
                Uri.withAppendedPath(BASE_CONTENT_URI, PATH_INCIDENTS);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                        CONTENT_AUTHORITY + PATH_INCIDENTS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                        CONTENT_AUTHORITY + PATH_INCIDENTS;


        public static final String TABLE_NAME = "tbl_incident";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_INCIDENT_NUMBER = "incidentId";
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
        public static final String COLUMN_USER = "user";
        public static final String COLUMN_STATUS = "incidentStatus";

        //Constant values
        public static final int STATUS_OPEN = 0;
        public static final int STATUS_CLOSED = 1;

        /*TODO consider working a solution with below for individual rows
        public static Uri buildLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        } */
    }

    public static class IncidentTypeEntry implements BaseColumns{
        // URI for access
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_INCTYPE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                        CONTENT_AUTHORITY + PATH_INCTYPE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                        CONTENT_AUTHORITY + PATH_INCTYPE;

        public static final String TABLE_NAME = "tbl_incType";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_INCTYPE_CODE = "incTypeID";
        public static final String COLUMN_INCTYPE_DESC = "incTypeDesc";
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

        public static final String TABLE_NAME = "tbl_resourceRequest";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_INCIDENT_NUMBER = "incidentID";
        public static final String COLUMN_UNIT_ID = "unitID";
        public static final String COLUMN_REQUEST_RESOURCE_ID = "resource";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_REQUEST_STATUS = "status";
        public static final String COLUMN_REQUEST_NOTES = "outcome_notes";

        public static final int STATUS_REQUESTED = 0;
        public static final int STATUS_PENDING = 1;
        public static final int STATUS_FILLED = 2;
        public static final int STATUS_DENIED = 3;
    }

    public static class ResourcesEntry implements BaseColumns {
        // URI for access
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RESOURCES).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                        CONTENT_AUTHORITY + PATH_RESOURCES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                        CONTENT_AUTHORITY + PATH_RESOURCES;

        public static final String TABLE_NAME = "tbl_resources";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_RESOURCE_ID = "resourceID";
        public static final String COLUMN_RESOURCE_DESC = "ResourceDesc";
    }

    public static class UnitsEntry implements BaseColumns{
        // URI for access
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_UNIT).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                        CONTENT_AUTHORITY + PATH_RESOURCES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                        CONTENT_AUTHORITY + PATH_RESOURCES;

        public static final String TABLE_NAME = "tbl_unit";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_UNIT_ID = "unitID";
        public static final String COLUMN_UNIT_DESC = "unitDesc";

    }

    public static class UsersEntry implements BaseColumns{
        // URI for access
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                        CONTENT_AUTHORITY + PATH_RESOURCES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                        CONTENT_AUTHORITY + PATH_RESOURCES;

        public static final String TABLE_NAME = "tbl_user";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_USER_NAME = "userName";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_NAME_FIRST = "firstName";
        public static final String COLUMN_NAME_LAST = "lastName";
    }

    public static class CitiesEntry implements BaseColumns{
        //TODO enter cities data
    }

    public static class StatesEntry implements BaseColumns{
        //TODO enter states data
    }

}
