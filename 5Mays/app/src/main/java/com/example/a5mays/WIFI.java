package com.example.a5mays;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

public class WIFI extends AppCompatActivity {

    private WifiManager modem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);

        modem = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        final ToggleButton btn = findViewById(R.id.toggleButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn.isChecked()) {
                    wifiAc();
                } else {
                    wifiKapat();
                }
            }
        });
    }

    private void wifiKapat() {
        if (modem.isWifiEnabled()) {
            modem.setWifiEnabled(false);
            Toast.makeText(WIFI.this, "Wifi Kapalı", Toast.LENGTH_SHORT).show();
        }
    }

    private void wifiAc() {
        if (!modem.isWifiEnabled()) {
            modem.setWifiEnabled(true);
            Toast.makeText(WIFI.this, "Wifi Açık", Toast.LENGTH_SHORT).show();
        }
    }
}
