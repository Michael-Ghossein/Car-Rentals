package com.example.exampractice;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AdminDashboardActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private ListView carListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        databaseHelper = new DatabaseHelper(this);

        findViewById(R.id.btnViewHistory).setOnClickListener(v -> {
            Log.d("AdminDashboardActivity", "View History Button Clicked");
            loadRentalHistory();
        });

        /*findViewById(R.id.btnAddCar).setOnClickListener(v -> {
            Log.d("AdminDashboardActivity", "Add Car Button Clicked");
            addCar();
        });

        findViewById(R.id.btnUpdateCar).setOnClickListener(v -> {
            Log.d("AdminDashboardActivity", "Update Car Button Clicked");
            updateCar();
        });

        findViewById(R.id.btnDeleteCar).setOnClickListener(v -> {
            Log.d("AdminDashboardActivity", "Delete Car Button Clicked");
            deleteCar();
        });*/
    }




    /*private void addCar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_car, null);
        builder.setView(dialogView);

        EditText etCarName = dialogView.findViewById(R.id.etCarName);
        EditText etCarDescription = dialogView.findViewById(R.id.etCarDescription);
        EditText etCarCost = dialogView.findViewById(R.id.etCarCost);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String name = etCarName.getText().toString().trim();
            String description = etCarDescription.getText().toString().trim();
            String costStr = etCarCost.getText().toString().trim();

            if (name.isEmpty() || description.isEmpty() || costStr.isEmpty()) {
                Toast.makeText(this, "All fields are required.", Toast.LENGTH_SHORT).show();
                return;
            }

            double cost = Double.parseDouble(costStr);
            databaseHelper.insertCar(name, description, cost);
            Toast.makeText(this, "Car added successfully.", Toast.LENGTH_SHORT).show();
            loadCars();
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }*/


    /*private void updateCar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_car_update, null);
        builder.setView(dialogView);

        EditText etCarId = dialogView.findViewById(R.id.etCarId);
        EditText etCarName = dialogView.findViewById(R.id.etCarName);
        EditText etCarDescription = dialogView.findViewById(R.id.etCarDescription);
        EditText etCarCost = dialogView.findViewById(R.id.etCarCost);

        builder.setPositiveButton("Update", (dialog, which) -> {
            String idStr = etCarId.getText().toString().trim();
            String name = etCarName.getText().toString().trim();
            String description = etCarDescription.getText().toString().trim();
            String costStr = etCarCost.getText().toString().trim();

            if (idStr.isEmpty() || name.isEmpty() || description.isEmpty() || costStr.isEmpty()) {
                Toast.makeText(this, "All fields are required.", Toast.LENGTH_SHORT).show();
                return;
            }

            int id = Integer.parseInt(idStr);
            double cost = Double.parseDouble(costStr);
            databaseHelper.updateCar(id, name, description, cost);
            Toast.makeText(this, "Car updated successfully.", Toast.LENGTH_SHORT).show();
            loadCars();
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }*/



    /*private void deleteCar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_car_delete, null);
        builder.setView(dialogView);

        EditText etCarId = dialogView.findViewById(R.id.etCarId);

        builder.setPositiveButton("Delete", (dialog, which) -> {
            String idStr = etCarId.getText().toString().trim();

            if (idStr.isEmpty()) {
                Toast.makeText(this, "Car ID is required.", Toast.LENGTH_SHORT).show();
                return;
            }

            int id = Integer.parseInt(idStr);
            databaseHelper.deleteCar(id);
            Toast.makeText(this, "Car deleted successfully.", Toast.LENGTH_SHORT).show();
            loadCars();
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }*/






    private void loadCars() {
        Cursor cursor = databaseHelper.getAllCars(); // Query to get all cars
        ArrayList<String> cars = new ArrayList<>();

        while (cursor.moveToNext()) { // Iterate through the cursor
            int idIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CAR_ID);
            int nameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CAR_NAME);
            int descriptionIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CAR_DESCRIPTION);
            int costIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CAR_RENTAL_COST);

            if (idIndex != -1 && nameIndex != -1 && descriptionIndex != -1 && costIndex != -1) {
                int id = cursor.getInt(idIndex);
                String name = cursor.getString(nameIndex);
                String description = cursor.getString(descriptionIndex);
                double rentalCost = cursor.getDouble(costIndex);

                cars.add("ID: " + id + "\nName: " + name + "\nDescription: " + description + "\nRental Cost: $" + rentalCost);
            } else {
                Toast.makeText(this, "Error loading car data", Toast.LENGTH_SHORT).show();
                break; // Exit loop if an issue occurs
            }
        }
        cursor.close(); // Ensure the cursor is closed


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cars);
        carListView.setAdapter(adapter);
    }

    private void loadRentalHistory() {
        Intent intent = new Intent(this, RentalHistoryActivity.class); // Create a separate activity for rental history if needed
        startActivity(intent);
    }


}


