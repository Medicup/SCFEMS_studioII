package com.example.android.scfems.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.SearchRecentSuggestionsProvider;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.NinePatch;
import android.util.Log;
import android.widget.Switch;

import com.example.android.scfems.RequestActivity;
import com.example.android.scfems.data.DataContract.*;
/**
 * Project: SCFEMS
 * Created by stitched on 11/11/2016.
 */
public class DbHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = DbHelper.class.getSimpleName();

    public static final String DATABASE_NAME = "usar.db";
    private static final int DATABASE_VERSION = 1;

    private static final String INTEGER = " INTEGER";
    private static final String TEXT = " TEXT";
    private static final String DOUBLE = " DOUBLE";
    private static final String ID_INTEGER = " INTEGER PRIMARY KEY AUTOINCREMENT";
    private static final String COMMA = ", ";
    private static final String NULL = " NULL";
    private static final String NOT_NULL = " NOT NULL";
    private static final String DEFAULT = " DEFAULT ";

    /* Foreign key syntax calls for Foreign Key to declare itself and the
     * table and field it references. Example using the resource table is:
     * FOREIGN KEY(requestsEntry.COLUMN_INCIDENT_NUMBER) REFERENCES
     * incidentEntry.TABLE_NAME(incidentEntry.COLUMN_INCIDENT_NUMBER)
     */
    private static final String FOREIGN_KEY = " FOREIGN KEY ";
    private static final String REFERENCES = " REFERENCES ";

    //Constructor for database
    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_INCIDENTS_TABLE = "CREATE TABLE IF NOT EXISTS " +
                IncidentEntry.TABLE_NAME + " (" +
                IncidentEntry._ID + ID_INTEGER + COMMA +
                IncidentEntry.COLUMN_INCIDENT_NUMBER + INTEGER + NOT_NULL + COMMA +
                IncidentEntry.COLUMN_DATE + TEXT + COMMA +
                IncidentEntry.COLUMN_TIME + TEXT + COMMA +
                IncidentEntry.COLUMN_UNIT_ID + TEXT + COMMA +
                IncidentEntry.COLUMN_GPS_LONG + DOUBLE + COMMA +
                IncidentEntry.COLUMN_GPS_LAT + DOUBLE + COMMA +
                IncidentEntry.COLUMN_STREET_NUMBER + INTEGER + COMMA +
                IncidentEntry.COLUMN_STREET_NAME + TEXT + COMMA +
                IncidentEntry.COLUMN_CITY + TEXT + COMMA +
                IncidentEntry.COLUMN_STATE + TEXT + COMMA +
                IncidentEntry.COLUMN_RECEIVED_INC_TYPE + TEXT + COMMA +
                IncidentEntry.COLUMN_FOUND_INC_TYPE + TEXT + COMMA +
                IncidentEntry.COLUMN_NOTES + TEXT + COMMA +
                IncidentEntry.COLUMN_STATUS + INTEGER + DEFAULT + IncidentEntry.STATUS_OPEN + " );";

        String SQL_CREATE_INC_TYPE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                IncidentTypeEntry.TABLE_NAME + " (" +
                IncidentTypeEntry._ID + ID_INTEGER + COMMA +
                IncidentTypeEntry.COLUMN_INCTYPE_CODE + TEXT + COMMA +
                IncidentTypeEntry.COLUMN_INCTYPE_DESC + TEXT + " );";

        String SQL_CREATE_REQUESTS_TABLE = "CREATE TABLE IF NOT EXISTS " +
                RequestsEntry.TABLE_NAME + " (" +
                RequestsEntry._ID + ID_INTEGER + COMMA +
                RequestsEntry.COLUMN_INCIDENT_NUMBER + INTEGER + NOT_NULL + COMMA +
                RequestsEntry.COLUMN_UNIT_ID + TEXT + NOT_NULL + COMMA +
                RequestsEntry.COLUMN_REQUEST_RESOURCE_ID + TEXT + COMMA +
                RequestsEntry.COLUMN_QUANTITY + INTEGER + COMMA +
                RequestsEntry.COLUMN_REQUEST_STATUS + INTEGER + DEFAULT + " 0" + COMMA +
                RequestsEntry.COLUMN_REQUEST_NOTES + TEXT + COMMA +
                FOREIGN_KEY + " (" + RequestsEntry.COLUMN_INCIDENT_NUMBER + ") " +
                    REFERENCES + IncidentEntry.TABLE_NAME +
                    " (" + IncidentEntry.COLUMN_INCIDENT_NUMBER + ") " + COMMA +
                FOREIGN_KEY + " (" + RequestsEntry.COLUMN_UNIT_ID + ") " +
                    REFERENCES + IncidentEntry.TABLE_NAME + " (" + IncidentEntry.COLUMN_UNIT_ID + "));";

        String SQL_CREATE_RESOURCE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                ResourcesEntry.TABLE_NAME + " (" +
                ResourcesEntry._ID + ID_INTEGER + COMMA +
                ResourcesEntry.COLUMN_RESOURCE_ID + TEXT + COMMA +
                ResourcesEntry.COLUMN_RESOURCE_DESC + TEXT + " );";

        String SQL_CREATE_UNIT_TABLE = "CREATE TABLE IF NOT EXISTS " +
                UnitsEntry.TABLE_NAME + " (" +
                UnitsEntry._ID + ID_INTEGER + COMMA +
                UnitsEntry.COLUMN_UNIT_ID + TEXT + COMMA +
                UnitsEntry.COLUMN_UNIT_DESC + TEXT + " );";

        String SQL_CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " +
                UsersEntry.TABLE_NAME + " (" +
                UsersEntry._ID + ID_INTEGER + COMMA +
                UsersEntry.COLUMN_USER_NAME + TEXT + COMMA +
                UsersEntry.COLUMN_PASSWORD + TEXT + COMMA +
                UsersEntry.COLUMN_NAME_FIRST + TEXT + COMMA +
                UsersEntry.COLUMN_NAME_LAST + TEXT + " );";

        //Execute the sql statement
        db.execSQL(SQL_CREATE_INCIDENTS_TABLE);
        db.execSQL(SQL_CREATE_INC_TYPE_TABLE);
        db.execSQL(SQL_CREATE_REQUESTS_TABLE);
        db.execSQL(SQL_CREATE_RESOURCE_TABLE);
        db.execSQL(SQL_CREATE_UNIT_TABLE);
        db.execSQL(SQL_CREATE_USER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void delete(String tableName){
        SQLiteDatabase database = this.getWritableDatabase();
        switch (tableName){
            case UnitsEntry.TABLE_NAME:
                database.delete(tableName, null, null);
                break;
            case IncidentTypeEntry.TABLE_NAME:
                database.delete(tableName, null, null);
                break;
            case ResourcesEntry.TABLE_NAME:
                database.delete(tableName, null, null);
                break;
            case UsersEntry.TABLE_NAME:
                database.delete(tableName,null,null);
                break;
        }
    }
    public void insert(String tableName, ContentValues contentValues){
        SQLiteDatabase database = this.getWritableDatabase();
        try{
            database.insert(tableName,null,contentValues);
            Log.i("LOGGER", tableName + " " + contentValues.toString());
        }catch (SQLiteException e){
            Log.i(LOG_TAG, " SQLite error on insert ", e);
        }
        Log.i("LOGGER", tableName + " " + contentValues.toString());
    }
}
