package com.example.android.scfems;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.scfems.data.DataContract;

/**
 * Project: SCFEMS
 * Created by stitched on 11/12/2016.
 */
public class RequestCursorAdapter extends CursorAdapter {
    public RequestCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 );
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_requests, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //find individual views for list_view
        TextView incidentTextView = (TextView)view.findViewById(R.id.lv_requests_incidentNumber);
        TextView unitIdTextView = (TextView)view.findViewById(R.id.lv_requests_unit);
        TextView requestResourceTextView = (TextView)view.findViewById(R.id.lv_requests);
        TextView quantityTextView = (TextView)view.findViewById(R.id.lv_requests_quantity);
        TextView notesTextView = (TextView)view.findViewById(R.id.lv_requests_notes);
        TextView statusTextView = (TextView)view.findViewById(R.id.lv_requests_status);

        //find column index for items to view
        int incidentColumnIndex = cursor.getColumnIndex(DataContract.RequestsEntry.COLUMN_INCIDENT_NUMBER);
        int unitIdColumnIndex = cursor.getColumnIndex(DataContract.RequestsEntry.COLUMN_UNIT_ID);
        int requestResourceColumnIndex = cursor.getColumnIndex(DataContract.RequestsEntry.COLUMN_REQUEST_RESOURCE_ID);
        int quantityColumnIndex = cursor.getColumnIndex(DataContract.RequestsEntry.COLUMN_QUANTITY);
        int notesColumnIndex = cursor.getColumnIndex(DataContract.RequestsEntry.COLUMN_REQUEST_NOTES);
        int statusColumnIndex = cursor.getColumnIndex(DataContract.RequestsEntry.COLUMN_REQUEST_STATUS);

        //read attributes from the cursor for current incident
        String incidentNumber = cursor.getString(incidentColumnIndex);
        String unitId = cursor.getString(unitIdColumnIndex);
        String requestedResource = cursor.getString(requestResourceColumnIndex);
        String quantity = cursor.getString(quantityColumnIndex);
        String notes = cursor.getString(notesColumnIndex);
        String status = cursor.getString(statusColumnIndex);

        //Update textViews for listView with current items from cursor
        incidentTextView.setText(incidentNumber);
        unitIdTextView.setText(unitId);
        requestResourceTextView.setText(requestedResource);
        quantityTextView.setText(quantity);
        notesTextView.setText(notes);
        statusTextView.setText(status);
    }
}
