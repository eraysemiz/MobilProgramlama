package com.example.plakaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    ListView randomPlateListView, randomCitiesListView;
    Button updateButton;
    HashMap<String, Integer> cityPlates;
    ArrayList<String> randomPlateNumbers;
    ArrayList<String> randomCities;



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

        updateButton = findViewById(R.id.button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateListView();
            }
        });

        cityPlates = new HashMap<>();
        String[] cities = {
                "Adana", "Adıyaman", "Afyonkarahisar", "Ağrı", "Amasya", "Ankara", "Antalya", "Artvin", "Aydın", "Balıkesir",
                "Bilecik", "Bingöl", "Bitlis", "Bolu", "Burdur", "Bursa", "Çanakkale", "Çankırı", "Çorum", "Denizli",
                "Diyarbakır", "Edirne", "Elazığ", "Erzincan", "Erzurum", "Eskişehir", "Gaziantep", "Giresun", "Gümüşhane", "Hakkari",
                "Hatay", "Isparta", "Mersin", "İstanbul", "İzmir", "Kars", "Kastamonu", "Kayseri", "Kırklareli", "Kırşehir",
                "Kocaeli", "Konya", "Kütahya", "Malatya", "Manisa", "Kahramanmaraş", "Mardin", "Muğla", "Muş", "Nevşehir",
                "Niğde", "Ordu", "Rize", "Sakarya", "Samsun", "Siirt", "Sinop", "Sivas", "Tekirdağ", "Tokat",
                "Trabzon", "Tunceli", "Şanlıurfa", "Uşak", "Van", "Yozgat", "Zonguldak", "Aksaray", "Bayburt", "Karaman",
                "Kırıkkale", "Batman", "Şırnak", "Bartın", "Ardahan", "Iğdır", "Yalova", "Karabük", "Kilis", "Osmaniye",
                "Düzce"
        };



        for (int i = 0; i < cities.length; i++) {
            cityPlates.put(cities[i], i + 1); // Plakaları ata
        }

        randomPlateListView = findViewById(R.id.listViewPlaka);
        randomCitiesListView = findViewById(R.id.listViewSehir);

        randomPlateNumbers = getRandomPlateNumbers(10);
        randomCities = getRandomCities(10);

        randomPlateListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, randomPlateNumbers));
        randomCitiesListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, randomCities));

        randomCitiesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCity = randomCities.get(position);
                int correctPlate = cityPlates.get(selectedCity); // Correct plate from the map
                int selectedPlate = Integer.parseInt(randomPlateNumbers.get(position)); // Selected plate number from the list of random numbers

                String resultMessage;

                if (correctPlate == selectedPlate) {
                    resultMessage = "Doğru! Plaka: " + correctPlate;
                } else {
                    resultMessage = "Yanlış! Seçilen plaka: " + selectedPlate + " - Doğru plaka: " + correctPlate;
                }

                // Show a toast message
                Toast.makeText(MainActivity.this, resultMessage, Toast.LENGTH_SHORT).show();

                // Start second activity and send result
                Intent intent = new Intent(MainActivity.this, IkinciEkran.class);
                intent.putExtra("result", resultMessage);
                startActivity(intent);
            }
        });

    }

    private ArrayList<String> getRandomCities(int count) {
        ArrayList<String> cityList = new ArrayList<>(cityPlates.keySet());
        Collections.shuffle(cityList);
        return new ArrayList<>(cityList.subList(0, count));
    }


    private ArrayList<String> getRandomPlateNumbers(int count) {
        ArrayList<String> numbers = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            numbers.add(String.valueOf(random.nextInt(81) + 1));
        }

        return numbers;
    }

    private void UpdateListView() {
        // Generate new random values
        randomPlateNumbers = getRandomPlateNumbers(10);
        randomCities = getRandomCities(10);

        // Update ListViews with new data
        randomPlateListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, randomPlateNumbers));
        randomCitiesListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, randomCities));

        // Optionally show a toast message
        Toast.makeText(MainActivity.this, "Değerler Güncellendi!", Toast.LENGTH_SHORT).show();
    }
}




