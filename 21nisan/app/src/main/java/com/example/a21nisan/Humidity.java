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

public class Humidity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor humiditySensor;
    private TextView humidityText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidity); // Make sure to create the layout for this screen

        humidityText = findViewById(R.id.humidityText); // TextView to display humidity data

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Get the humidity sensor
        humiditySensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

        // Check if the humidity sensor is available
        if (humiditySensor == null) {
            humidityText.setText("Humidity sensor not available on this device.");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register the humidity sensor listener if the sensor is available
        if (humiditySensor != null) {
            sensorManager.registerListener(this, humiditySensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the sensor listener when the activity is paused
        if (humiditySensor != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY) {
            // Get the humidity value
            float humidity = event.values[0];

            // Display the humidity data on the TextView
            humidityText.setText(String.format("Humidity: %.2f %%", humidity));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // You can handle accuracy changes here if needed
    }
}