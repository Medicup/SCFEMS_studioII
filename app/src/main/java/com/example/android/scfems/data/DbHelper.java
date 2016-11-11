package com.example.android.scfems.data;

import android.content.Context;
import android.content.SearchRecentSuggestionsProvider;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.NinePatch;

import com.example.android.scfems.data.DataContract.incidentEntry;
/**
 * Project: SCFEMS
 * Created by stitched on 11/11/2016.
 */
public class DbHelper extends SQLiteOpenHelper {
    public static final String TAG = DbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "usar.db";
    private static final int DATABASE_VERSION = 1;

    private static final String INTEGER = " INTEGER";
    private static final String TEXT = " TEXT";
    private static final String ID_INTEGER = " INTEGER PRIMARY KEY AUTOINCREMENT";
    private static final String COMMA = ", ";
    private static final String NULL = " NULL";
    private static final String NOT_NULL = " NOT NULL";
    private static final String DEFAULT = " DEFAULT ";

    //Constructor for database
    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_INCIDENTS_TABLE = "CREATE TABLE " +
                incidentEntry.TABLE_NAME + " (" +
                incidentEntry._ID + ID_INTEGER + COMMA +
                incidentEntry.COLUMN_INCIDENT_NUMBER + INTEGER + NOT_NULL + COMMA +
                incidentEntry.COLUMN_DATE + TEXT + NOT_NULL + COMMA +
                incidentEntry.COLUMN_TIME + TEXT + NOT_NULL + COMMA +
                incidentEntry.COLUMN_UNIT_ID + TEXT + NOT_NULL + COMMA +
                incidentEntry.COLUMN_GPS_LONG + TEXT + COMMA +
                incidentEntry.COLUMN_GPS_LAT + TEXT + COMMA +
                incidentEntry.COLUMN_STREET_NUMBER + INTEGER + COMMA +
                incidentEntry.COLUMN_STREET_NAME + TEXT + COMMA +
                incidentEntry.COLUMN_CITY + TEXT + COMMA +
                incidentEntry.COLUMN_STATE + TEXT + COMMA +
                incidentEntry.COLUMN_RECEIVED_INC_TYPE + TEXT + COMMA +
                incidentEntry.COLUMN_FOUND_INC_TYPE + TEXT + COMMA +
                incidentEntry.COLUMN_NOTES + TEXT + COMMA +
                incidentEntry.COLUMN_STATUS + INTEGER + DEFAULT + 0 + " );";

        //Execute the sql statement
        db.execSQL(SQL_CREATE_INCIDENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
