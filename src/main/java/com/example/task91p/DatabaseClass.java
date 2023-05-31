package com.example.task91p;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseClass extends SQLiteOpenHelper {

    // Database version and name
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "LostFoundDb.db";

    // Table name and column names
    private static final String TABLE_NAME = "lost_found";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_IS_LOST_OR_FOUND = "is_lost_or_found";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_LOCATION = "location";

    public DatabaseClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the table query
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_IS_LOST_OR_FOUND + " TEXT, " +
                COLUMN_PHONE + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_LOCATION + " TEXT)";

        // Execute the create table query
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Recreate the table
        onCreate(db);
    }

    public long insertData(DATA data) {
        ContentValues values = new ContentValues();
        // Put data values into ContentValues object
        values.put(COLUMN_NAME, data.getName());
        values.put(COLUMN_IS_LOST_OR_FOUND, data.getIsLostOrFound());
        values.put(COLUMN_PHONE, data.getPhone());
        values.put(COLUMN_DESCRIPTION, data.getDescription());
        values.put(COLUMN_DATE, data.getDate());
        values.put(COLUMN_LOCATION, data.getLocation());

        SQLiteDatabase db = getWritableDatabase();
        // Insert the data into the table
        long id = db.insert(TABLE_NAME, null, values);
        db.close();

        return id;
    }

    public ArrayList<DATA> getData() {
        ArrayList<DATA> data = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(COLUMN_ID);
                int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
                int lostOrFoundIndex = cursor.getColumnIndex(COLUMN_IS_LOST_OR_FOUND);
                int phoneIndex = cursor.getColumnIndex(COLUMN_PHONE);
                int descriptionIndex = cursor.getColumnIndex(COLUMN_DESCRIPTION);
                int dateIndex = cursor.getColumnIndex(COLUMN_DATE);
                int locationIndex = cursor.getColumnIndex(COLUMN_LOCATION);

                if (idIndex != -1 && nameIndex != -1 && lostOrFoundIndex != -1 &&
                        phoneIndex != -1 && descriptionIndex != -1 && dateIndex != -1 &&
                        locationIndex != -1) {

                    DATA model = new DATA(
                            cursor.getString(lostOrFoundIndex),
                            cursor.getString(nameIndex),
                            cursor.getString(phoneIndex),
                            cursor.getString(descriptionIndex),
                            cursor.getString(dateIndex),
                            cursor.getString(locationIndex),
                            cursor.getInt(idIndex)
                    );

                    data.add(model);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return data;
    }

    public DATA getDataById(int id) {
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + id;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        DATA data = null;
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(COLUMN_ID);
            int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
            int lostOrFoundIndex = cursor.getColumnIndex(COLUMN_IS_LOST_OR_FOUND);
            int phoneIndex = cursor.getColumnIndex(COLUMN_PHONE);
            int descriptionIndex = cursor.getColumnIndex(COLUMN_DESCRIPTION);
            int dateIndex = cursor.getColumnIndex(COLUMN_DATE);
            int locationIndex = cursor.getColumnIndex(COLUMN_LOCATION);

            if (idIndex != -1 && nameIndex != -1 && lostOrFoundIndex != -1 &&
                    phoneIndex != -1 && descriptionIndex != -1 && dateIndex != -1 &&
                    locationIndex != -1) {

                data = new DATA(
                        cursor.getString(lostOrFoundIndex),
                        cursor.getString(nameIndex),
                        cursor.getString(phoneIndex),
                        cursor.getString(descriptionIndex),
                        cursor.getString(dateIndex),
                        cursor.getString(locationIndex),
                        cursor.getInt(idIndex)
                );
            }
        }

        cursor.close();
        db.close();

        return data;
    }

    public int deleteDataById(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };
        // Delete the data from the table based on the ID
        int deletedRows = db.delete(TABLE_NAME, selection, selectionArgs);
        db.close();

        return deletedRows;
    }
}