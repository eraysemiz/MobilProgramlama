package com.example.quizapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Scoreboard extends AppCompatActivity {

    TextView scoreTextView;
    Button showAllButton;
    Button backButton;
    DatabaseManager db;
    String currentUsername;
    boolean showingAllScores = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        scoreTextView = findViewById(R.id.textViewScores);
        showAllButton = findViewById(R.id.buttonShowAll);
        backButton = findViewById(R.id.buttonBackToSelection);

        db = new DatabaseManager(this);

        SharedPreferences prefs = getSharedPreferences("quiz_prefs", MODE_PRIVATE);
        currentUsername = prefs.getString("username", null);

        showUserScores();

        showAllButton.setOnClickListener(v -> {
            if (showingAllScores) {
                showUserScores();
                showAllButton.setText("Tüm Skorları Göster");
                showingAllScores = false;
            } else {
                showAllScores();
                showAllButton.setText("Kendi Skorlarını Göster");
                showingAllScores = true;
            }
        });

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(Scoreboard.this, QuizSelectionActivity.class);
            startActivity(intent);
            finish();
        });
    }



    private void showUserScores() {
        Cursor cursor = db.getScoresByUser(currentUsername);
        StringBuilder sb = new StringBuilder();

        while (cursor.moveToNext()) {
            String quiz = cursor.getString(0);
            int score = cursor.getInt(1);
            String time = cursor.getString(2);

            sb.append("Quiz: ").append(quiz).append("\n")
                    .append("Puan: ").append(score).append("\n")
                    .append("Tarih: ").append(time).append("\n\n");
        }
        cursor.close();
        scoreTextView.setText(sb.toString());
    }

    private void showAllScores() {
        Cursor cursor = db.getAllScores();
        StringBuilder sb = new StringBuilder();

        while (cursor.moveToNext()) {
            String user = cursor.getString(0);
            String quiz = cursor.getString(1);
            int score = cursor.getInt(2);
            String time = cursor.getString(3);

            sb.append("Kullanıcı: ").append(user).append("\n")
                    .append("Quiz: ").append(quiz).append("\n")
                    .append("Puan: ").append(score).append("\n")
                    .append("Tarih: ").append(time).append("\n\n");
        }
        cursor.close();
        scoreTextView.setText(sb.toString());
    }
}
