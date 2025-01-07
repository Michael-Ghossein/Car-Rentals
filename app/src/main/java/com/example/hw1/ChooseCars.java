package com.example.hw1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ChooseCars extends AppCompatActivity {
    CheckBox Maserati, Rolls, Ferrari, Porche;

    RadioButton Coverage, NoCoverage;

    Spinner Days;
    int payment = 0;
    int days = 0;
    String details = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choose_cars);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Maserati = findViewById(R.id.cbMaserati);
        Rolls = findViewById(R.id.cbRollsRoyce);
        Ferrari = findViewById(R.id.cbFerrari);
        Porche = findViewById(R.id.cbPorche);
        Days = findViewById(R.id.spDays);
        Coverage = findViewById(R.id.rbFullCoverage);
        NoCoverage = findViewById(R.id.rbNoCoverage);
    }

    public void maseratiDetails(View view) {
        Intent i = new Intent(ChooseCars.this, DetailsCar.class);
        details = "*Maserati Levante*\n\nFeel the power of a 345-hp twin-turbo V6 in this luxury SUV. With all-wheel drive and adaptive air suspension, it delivers speed, agility, and style on any road.\n" +
                "\n" +
                "Rental Rates:\n" +
                "\n" +
                "7 Days: $1,750\n" +
                "15 Days: $3,500 (Save $250)\n" +
                "31 Days: $6,750 (Save $650)";
        i.putExtra("DETAILS",details);
        startActivity(i);
    }

    public void rollsDetails(View view) {
        Intent i = new Intent(ChooseCars.this, DetailsCar.class);
        details = "\n" +
                "*Rolls-Royce Phantom*\n\n" +
                "Experience luxury in the Rolls-Royce Phantom, featuring a 6.75L V12 engine with 563 horsepower. Its hand-crafted interior and advanced tech make every drive feel royal.\n" +
                "\n" +
                "Rental Rates:\n" +
                "\n" +
                "7 Days: $5,000\n" +
                "15 Days: $9,500 (Save $500)\n" +
                "31 Days: $18,000 (Save $1,000)";
        i.putExtra("DETAILS",details);
        startActivity(i);
    }

    public void ferrariDetails(View view) {
        Intent i = new Intent(ChooseCars.this, DetailsCar.class);
        details = "\n" +
                "*LaFerrari Aperta*\n\n" +
                "Experience the thrill of a 950-hp hybrid V12 in the LaFerrari Aperta. This limited-edition convertible delivers stunning performance, going 0-60 mph in under 3 seconds.\n" +
                "\n" +
                "Rental Rates:\n" +
                "\n" +
                "7 Days: $12,000\n" +
                "15 Days: $22,500 (Save $1,500)\n" +
                "31 Days: $42,000 (Save $3,000)";
        i.putExtra("DETAILS",details);
        startActivity(i);
    }

    public void porcheDetails(View view) {
        Intent i = new Intent(ChooseCars.this, DetailsCar.class);
        details = "*Porsche 911 GT3 RS*\n\n" +
                "The Porsche 911 GT3 RS features a 520-hp 4.0L flat-six engine, offering unmatched precision and agility. With advanced aerodynamics and lightweight design, it’s built for thrilling performance on road and track.\n" +
                "\n" +
                "Rental Rates:\n" +
                "\n" +
                "7 Days: $3,500\n" +
                "15 Days: $6,750 (Save $500)\n" +
                "31 Days: $12,000 (Save $1,500)";
        i.putExtra("DETAILS",details);
        startActivity(i);
    }

    public void back(View view) {
        finish();
    }

    public void rent(View view) {
        if (!Maserati.isChecked() && !Rolls.isChecked() && !Ferrari.isChecked() && !Porche.isChecked()) {
            Toast.makeText(this, "!You Should at Least \nSelect One Car to Rent!", Toast.LENGTH_LONG).show();
            return;
        }
        days = Integer.parseInt(Days.getSelectedItem().toString());
        if (days == 7) {
            if(Maserati.isChecked()) {
                payment += 1750;
            }
            if(Rolls.isChecked()) {
                payment += 5000;
            }
            if(Ferrari.isChecked()) {
                payment += 12000;
            }
            if(Porche.isChecked()) {
                payment += 3500;
            }
            if (Coverage.isChecked()) {
                payment += 200;
            }
        }
        if (days == 15) {
            if(Maserati.isChecked()) {
                payment += 3500;
            }
            if(Rolls.isChecked()) {
                payment += 9500;
            }
            if(Ferrari.isChecked()) {
                payment += 22500;
            }
            if(Porche.isChecked()) {
                payment += 6750;
            }
            if (Coverage.isChecked()) {
                payment += 350;
            }
        }
        if (days == 31) {
            if(Maserati.isChecked()) {
                payment += 6750;
            }
            if(Rolls.isChecked()) {
                payment += 18000;
            }
            if(Ferrari.isChecked()) {
                payment += 42000;
            }
            if(Porche.isChecked()) {
                payment += 12000;
            }
            if (Coverage.isChecked()) {
                payment += 500;
            }
        }

        Intent i = new Intent(ChooseCars.this, Payment.class);
        i.putExtra("PAYMENT", Integer.toString(payment));
        payment = 0;
        startActivity(i);
    }
}