package com.example.a21nisan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button button1, button2, button3, button4, button5, button6, button7, button8, button9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        button1 = findViewById(R.id.button1); // Accelerometer
        button2 = findViewById(R.id.button2); // Compass
        button3 = findViewById(R.id.button3); // Gyroscope
        button4 = findViewById(R.id.button4); // Humidity
        button5 = findViewById(R.id.button5); // Light
        button6 = findViewById(R.id.button6); // Magnometer
        button7 = findViewById(R.id.button7); // Pressure
        button8 = findViewById(R.id.button8); // Proximity
        button9 = findViewById(R.id.button9); // Thermometer

        button1.setOnClickListener(v -> startActivity(new Intent(this, Accelerometer.class)));
        button2.setOnClickListener(v -> startActivity(new Intent(this, Compass.class)));
        button3.setOnClickListener(v -> startActivity(new Intent(this, Gyroscope.class)));
        button4.setOnClickListener(v -> startActivity(new Intent(this, Humidity.class)));
        button5.setOnClickListener(v -> startActivity(new Intent(this, Light.class)));
        button6.setOnClickListener(v -> startActivity(new Intent(this, Magnometer.class)));
        button7.setOnClickListener(v -> startActivity(new Intent(this, Pressure.class)));
        button8.setOnClickListener(v -> startActivity(new Intent(this, Proximity.class)));
        button9.setOnClickListener(v -> startActivity(new Intent(this, Thermometer.class)));
    }

}