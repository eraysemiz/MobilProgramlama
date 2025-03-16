package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class QuizSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz_selection);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button quiz1 = findViewById(R.id.quizButton1);
        Button quiz2 = findViewById(R.id.quizButton2);
        Button quiz3 = findViewById(R.id.quizButton3);
        Button quiz4 = findViewById(R.id.quizButton4);
        Button quiz5 = findViewById(R.id.quizButton5);
        Button quiz6 = findViewById(R.id.quizButton6);

        quiz1.setTag("quiz1");
        quiz2.setTag("quiz2");
        quiz3.setTag("quiz3");
        quiz4.setTag("quiz4");
        quiz5.setTag("quiz5");
        quiz6.setTag("quiz6");

        View.OnClickListener quizClickListener = new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View v) {
                String quizId = v.getTag().toString();
                Intent intent = new Intent(QuizSelectionActivity.this, QuizActivity.class);
                intent.putExtra("QUIZ_ID", quizId);
                startActivity(intent);
            }
        };

        quiz1.setOnClickListener(quizClickListener);
        quiz2.setOnClickListener(quizClickListener);
        quiz3.setOnClickListener(quizClickListener);
        quiz4.setOnClickListener(quizClickListener);
        quiz5.setOnClickListener(quizClickListener);
        quiz6.setOnClickListener(quizClickListener);


    }
}