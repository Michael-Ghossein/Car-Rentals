package com.example.hw1;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RentalHistoryActivity extends AppCompatActivity {

    private ListView rentalHistoryListView;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rental_history);

        rentalHistoryListView = findViewById(R.id.lvRentalHistory);
        databaseHelper = new DatabaseHelper(this);

        loadRentalHistory();
    }

    private void loadRentalHistory() {
        Cursor cursor = databaseHelper.getRentalHistory();
        ArrayList<String> rentalHistory = new ArrayList<>();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No rental history found", Toast.LENGTH_SHORT).show();
            return;
        }

        while (cursor.moveToNext()) {
            int userIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USER);
            int carsIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CARS);
            int daysIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_DAYS);
            int totalCostIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_TOTAL_COST);

            if (userIndex != -1 && carsIndex != -1 && daysIndex != -1 && totalCostIndex != -1) {
                String user = cursor.getString(userIndex);
                String cars = cursor.getString(carsIndex);
                int days = cursor.getInt(daysIndex);
                double totalCost = cursor.getDouble(totalCostIndex);

                rentalHistory.add("User: " + user + "\nCars: " + cars + "\nDays: " + days + "\nTotal Cost: $" + totalCost);
            }
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, rentalHistory);
        rentalHistoryListView.setAdapter(adapter);
    }
}

