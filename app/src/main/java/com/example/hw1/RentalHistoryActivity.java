package com.example.exampractice;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RentalHistoryActivity extends AppCompatActivity {

    private ListView rentalHistoryListView;
    private Button clearHistoryButton, backtoAdminButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rental_history);

        rentalHistoryListView = findViewById(R.id.lvRentalHistory);
        clearHistoryButton = findViewById(R.id.btnClearHistory);
        backtoAdminButton = findViewById(R.id.btnBackToAdmin);
        databaseHelper = new DatabaseHelper(this);

        loadRentalHistory();

        // Set up the Clear History button
        clearHistoryButton.setOnClickListener(v -> showClearHistoryConfirmation());
        backtoAdminButton.setOnClickListener(v -> redirectToAdminDashboard());
    }

    private void loadRentalHistory() {
        ArrayList<String> rentalHistory = new ArrayList<>();
        Cursor cursor = databaseHelper.getRentalHistory();

        if (cursor.moveToFirst()) {
            do {
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
                } else {
                    Toast.makeText(this, "Error loading rental history: Column not found.", Toast.LENGTH_SHORT).show();
                    break;
                }
            } while (cursor.moveToNext());
        } else {
            rentalHistory.add("No rental history found.");
        }


        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, rentalHistory);
        rentalHistoryListView.setAdapter(adapter);
    }

    private void showClearHistoryConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Deletion");
        builder.setMessage("Are you sure you want to clear the rental history? This action cannot be undone.");

        builder.setPositiveButton("Yes", (dialog, which) -> clearRentalHistory());
        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void clearRentalHistory() {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + DatabaseHelper.TABLE_RENTALS);
        Toast.makeText(this, "Rental history cleared.", Toast.LENGTH_SHORT).show();
        loadRentalHistory(); // Refresh the list
    }

    private void redirectToAdminDashboard() {
        Intent intent = new Intent(RentalHistoryActivity.this, AdminDashboardActivity.class);
        startActivity(intent);
        finish();
    }
}
