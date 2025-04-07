
package com.example.a7nisan2;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    EditText editText;
    Button buttonBasla, buttonOnayla;
    TextView textViewPlaka;

    String[] sehirler = {
            "Adana", "Adiyaman", "Afyonkarahisar", "Agri", "Amasya", "Ankara", "Antalya", "Artvin", "Aydin", "Balikesir",
            "Bilecik", "Bingol", "Bitlis", "Bolu", "Burdur", "Bursa", "Canakkale", "Cankiri", "Corum", "Denizli",
            "Diyarbakir", "Edirne", "Elazig", "Erzincan", "Erzurum", "Eskisehir", "Gaziantep", "Giresun", "Gumushane", "Hakkari",
            "Hatay", "Isparta", "Mersin", "Istanbul", "Izmir", "Kars", "Kastamonu", "Kayseri", "Kirkareli", "Kirsehir",
            "Kocaeli", "Konya", "Kutahya", "Malatya", "Manisa", "Kahramanmaras", "Mardin", "Mugla", "Mus", "Nevsehir",
            "Nigde", "Ordu", "Rize", "Sakarya", "Samsun", "Siirt", "Sinop", "Sivas", "Tekirdag", "Tokat",
            "Trabzon", "Tunceli", "Sanliurfa", "Usak", "Van", "Yozgat", "Zonguldak", "Aksaray", "Bayburt", "Karaman",
            "Kirikale", "Batman", "Sirnak", "Bartin", "Ardahan", "Igdir", "Yalova", "Karabuk", "Kilis", "Osmaniye", "Duzce"
    };


    int secilenPlaka = 1;
    long baslangicZamani;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);
        editText = findViewById(R.id.editTextSehir);
        buttonBasla = findViewById(R.id.buttonBasla);
        buttonOnayla = findViewById(R.id.buttonOnayla);
        textViewPlaka = findViewById(R.id.textViewPlaka);


        seekBar.setMax(81);
        seekBar.setMin(1);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int plaka = progress;
                textViewPlaka.setText(String.valueOf(plaka));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        buttonBasla.setOnClickListener(v -> {
            secilenPlaka = seekBar.getProgress();
            editText.setText("");
            baslangicZamani = SystemClock.elapsedRealtime();
            Toast.makeText(this, secilenPlaka + " - " + sehirler[secilenPlaka - 1] + " şehrini yazınız", Toast.LENGTH_SHORT).show();
        });

        buttonOnayla.setOnClickListener(v -> {
            String kullaniciGirdisi = editText.getText().toString().trim();
            String hedefSehir = sehirler[secilenPlaka - 1];

            long bitisZamani = SystemClock.elapsedRealtime();
            float sure = (bitisZamani - baslangicZamani) / 1000f;

            if (kullaniciGirdisi.equalsIgnoreCase(hedefSehir)) {
                Intent intent = new Intent(MainActivity.this, ikinciEkran.class);
                intent.putExtra("sehir", hedefSehir);
                intent.putExtra("sure", sure);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Hatalı giriş! Lütfen doğru yazınız.", Toast.LENGTH_SHORT).show();
            }
        });
    }
} 