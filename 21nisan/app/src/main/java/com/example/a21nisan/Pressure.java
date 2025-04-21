package com.example.a21nisan;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Pressure extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor pressureSensor;
    private TextView pressureText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pressure); // Make sure to create the layout for this screen

        pressureText = findViewById(R.id.pressureText); // TextView to display pressure data

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Get the pressure sensor
        pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);

        // Check if the pressure sensor is available
        if (pressureSensor == null) {
            pressureText.setText("Pressure sensor not available on this device.");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register the pressure sensor listener if the sensor is available
        if (pressureSensor != null) {
            sensorManager.registerListener(this, pressureSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the sensor listener when the activity is paused
        if (pressureSensor != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
            // Get the pressure value in hPa (hectopascals)
            float pressure = event.values[0];

            // Display the pressure value on the TextView
            pressureText.setText(String.format("Pressure: %.2f hPa", pressure));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // You can handle accuracy changes here if needed
    }
}