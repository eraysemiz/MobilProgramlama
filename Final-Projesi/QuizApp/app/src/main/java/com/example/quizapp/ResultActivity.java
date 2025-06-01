package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.SharedPreferences;

public class ResultActivity extends AppCompatActivity {

    TextView textViewResult;
    Button buttonHome;
    Button buttonRestart;
    Button buttonScoreboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textViewResult = findViewById(R.id.textViewScore);
        buttonHome = findViewById(R.id.buttonHome);
        buttonRestart = findViewById(R.id.buttonRestart);
        buttonScoreboard = findViewById(R.id.buttonScoreboard);

        int iq = getIntent().getIntExtra("IQ_SCORE", -1);

        if (iq != -1) {
            // Eğer IQ gönderilmişse
            textViewResult.setText("IQ Sonucun: " + iq);
        }
        else
        {
            int correctAnswers = getIntent().getIntExtra("CORRECT_ANSWERS", 0);
            int totalQuestions = getIntent().getIntExtra("TOTAL_QUESTIONS", 0);

            String resultText = correctAnswers + " / " + totalQuestions;
            textViewResult.setText(resultText);
        }

        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("quiz_prefs", MODE_PRIVATE);
                prefs.edit().remove("username").apply();

                Intent intent = new Intent(ResultActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Geri tuşuyla dönülmesin
                startActivity(intent);
                finish();
            }
        });

        buttonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultActivity.this, QuizSelectionActivity.class));
                finish();
            }
        });

        buttonScoreboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, Scoreboard.class);
                startActivity(intent);
            }
        });
    }
}