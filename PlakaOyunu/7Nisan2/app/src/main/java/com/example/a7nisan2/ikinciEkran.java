package com.example.a7nisan2;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ikinciEkran extends AppCompatActivity {

    ListView listView;
    ArrayList<String> veriListesi;
    ArrayAdapter<String> adapter;
    Button buttonGeri;

    public static ArrayList<String> staticVeriListesi = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ikinci_ekran);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (staticVeriListesi == null)
            veriListesi = new ArrayList<>();
        else
            veriListesi = staticVeriListesi;

        buttonGeri = findViewById(R.id.buttonGeri);
        listView = findViewById(R.id.listView1);

        String sehir = getIntent().getStringExtra("sehir");
        float sure = getIntent().getFloatExtra("sure", 0);

        veriListesi.add(sehir + " - " + sure + " saniye");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, veriListesi);
        listView.setAdapter(adapter);



        buttonGeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (staticVeriListesi.size() == 3) {
                    Toast.makeText(ikinciEkran.this, "Oyun Bitti", Toast.LENGTH_LONG).show();
                    staticVeriListesi.clear();
                    Intent intent = new Intent(ikinciEkran.this, MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    finish();
                }
            }
        });

    }
}