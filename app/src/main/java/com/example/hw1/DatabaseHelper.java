package com.example.exampractice;

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
        db.execSQL("CREATE TABLE " + TABLE_CARS + " (" +
                COLUMN_CAR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CAR_NAME + " TEXT, " +
                COLUMN_CAR_DESCRIPTION + " TEXT, " +
                COLUMN_CAR_RENTAL_COST + " REAL)");

        Log.d("DatabaseHelper", "Table created: " + TABLE_CARS);
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARS);
        onCreate(db);
    }



    public void insertCar(String name, String description, double cost) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO " + TABLE_CARS + " (car_name, car_description, car_rental_cost) VALUES('" +
                name + "', '" + description + "', '" + cost + "')");
        db.close();
    }


    public void updateCar(int id, String name, String description, double cost) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_CARS + " SET car_name='" + name + "', car_description='" +
                description + "', car_rental_cost='" + cost + "' WHERE car_id=" + id);
        db.close();
    }

    public void deleteCar(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_CARS + " WHERE car_id=" + id);
        db.close();
    }

    public Cursor getAllCars() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_CARS, null);
    }

    public Cursor getRentalHistory() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_RENTALS, null);
    }
}

