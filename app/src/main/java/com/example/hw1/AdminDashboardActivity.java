package com.example.hw1;
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

        findViewById(R.id.btnAddCar).setOnClickListener(v -> {
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
        });
    }




    private void addCar() {
        // Logic to show a dialog and insert a new car into the database
        showCarDialog("Add");
    }

    private void updateCar() {
        // Logic to show a dialog and update car details in the database
        showCarDialog("Update");
    }

    private void deleteCar() {
        // Logic to show a dialog and delete a car from the database
        showCarDialog("Delete");
    }





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

    private void showCarDialog(String action) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_car, null);
        dialog.setView(dialogView);

        EditText etCarName = dialogView.findViewById(R.id.etCarName);
        EditText etCarDescription = dialogView.findViewById(R.id.etCarDescription);
        EditText etCarCost = dialogView.findViewById(R.id.etCarCost);

        dialog.setPositiveButton(action, (dialogInterface, i) -> {
            String name = etCarName.getText().toString();
            String description = etCarDescription.getText().toString();
            String costStr = etCarCost.getText().toString();

            if (name.isEmpty() || description.isEmpty() || costStr.isEmpty()) {
                Toast.makeText(this, "All fields must be filled!", Toast.LENGTH_SHORT).show();
                return;
            }

            double cost;
            try {
                cost = Double.parseDouble(costStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid price entered!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (action.equals("Add")) {
                long result = databaseHelper.insertCar(name, description, cost);
                if (result != -1) {
                    Log.d("AdminDashboardActivity", "Car added: " + name);
                    Toast.makeText(this, "Car added successfully", Toast.LENGTH_SHORT).show();
                    loadCars();
                }
            } else {
                    Toast.makeText(this, "Car added successfully", Toast.LENGTH_SHORT).show();
                    loadCars();// Refresh car list
                }



        });


        dialog.setNegativeButton("Cancel", null);
        dialog.show();
    }

}


