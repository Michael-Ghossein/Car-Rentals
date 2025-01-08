package com.example.hw1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "CarRental.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_RENTALS = "rental_history";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USER = "user_name";
    public static final String COLUMN_CARS = "rented_cars";
    public static final String COLUMN_DAYS = "rental_days";
    public static final String COLUMN_TOTAL_COST = "total_cost";

    public static final String TABLE_CARS = "car_inventory";
    public static final String COLUMN_CAR_ID = "car_id";
    public static final String COLUMN_CAR_NAME = "car_name";
    public static final String COLUMN_CAR_DESCRIPTION = "car_description";
    public static final String COLUMN_CAR_RENTAL_COST = "car_rental_cost";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String createRentalHistoryTable = "CREATE TABLE " + TABLE_RENTALS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER + " TEXT, " +
                    COLUMN_CARS + " TEXT, " +
                    COLUMN_DAYS + " INTEGER, " +
                    COLUMN_TOTAL_COST + " REAL)";
            db.execSQL(createRentalHistoryTable);

            String createCarInventoryTable = "CREATE TABLE " + TABLE_CARS + " (" +
                    COLUMN_CAR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_CAR_NAME + " TEXT, " +
                    COLUMN_CAR_DESCRIPTION + " TEXT, " +
                    COLUMN_CAR_RENTAL_COST + " REAL)";
            db.execSQL(createCarInventoryTable);

            Log.d("DatabaseHelper", "Tables created successfully.");
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error creating tables: " + e.getMessage());
        }
    }

    /*@Override
    public void onCreate(SQLiteDatabase db) {
        String createRentalHistoryTable = "CREATE TABLE " + TABLE_RENTALS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER + " TEXT, " +
                COLUMN_CARS + " TEXT, " +
                COLUMN_DAYS + " INTEGER, " +
                COLUMN_TOTAL_COST + " REAL)";
        db.execSQL(createRentalHistoryTable);

        String createCarInventoryTable = "CREATE TABLE " + TABLE_CARS + " (" +
                COLUMN_CAR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CAR_NAME + " TEXT, " +
                COLUMN_CAR_DESCRIPTION + " TEXT, " +
                COLUMN_CAR_RENTAL_COST + " REAL)";
        db.execSQL(createCarInventoryTable);
    }*/

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old tables (if any) and create them again
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RENTALS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARS); // Add this line to drop the missing table
        onCreate(db); // Recreate all tables
    }

    public Cursor getAllCars() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_CARS, null);
    }

    public long insertCar(String name, String description, double rentalCost) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CAR_NAME, name);
        values.put(COLUMN_CAR_DESCRIPTION, description);
        values.put(COLUMN_CAR_RENTAL_COST, rentalCost);

        long result = -1;
        try {
            result = db.insert(TABLE_CARS, null, values);
            Log.d("DatabaseHelper", "Car inserted successfully: " + name);
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error inserting car: " + e.getMessage());
        }
        return result;
    }


    public int updateCar(int id, String name, String description, double rentalCost) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CAR_NAME, name);
        values.put(COLUMN_CAR_DESCRIPTION, description);
        values.put(COLUMN_CAR_RENTAL_COST, rentalCost);
        return db.update(TABLE_CARS, values, COLUMN_CAR_ID + "=?", new String[]{String.valueOf(id)});
    }

    public int deleteCar(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_CARS, COLUMN_CAR_ID + "=?", new String[]{String.valueOf(id)});
    }

    public Cursor getRentalHistory() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_RENTALS, null);
    }
}

