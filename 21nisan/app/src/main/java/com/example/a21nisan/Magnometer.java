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

public class Magnometer extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor magnetometerSensor;
    private TextView magnetometerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magnometer); // Make sure to create the layout for this screen

        magnetometerText = findViewById(R.id.magnetometerText); // TextView to display magnetometer data

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Get the magnetometer sensor
        magnetometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        // Check if the magnetometer sensor is available
        if (magnetometerSensor == null) {
            magnetometerText.setText("Magnetometer sensor not available on this device.");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register the magnetometer sensor listener if the sensor is available
        if (magnetometerSensor != null) {
            sensorManager.registerListener(this, magnetometerSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the sensor listener when the activity is paused
        if (magnetometerSensor != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            // Get the magnetic field values in X, Y, and Z axes
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            // Display the magnetic field data on the TextView
            magnetometerText.setText(String.format("Magnetic Field (X): %.2f µT\n" +
                    "Magnetic Field (Y): %.2f µT\n" +
                    "Magnetic Field (Z): %.2f µT", x, y, z));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // You can handle accuracy changes here if needed
    }
}