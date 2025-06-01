package com.example.quizapp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;


public class QuizActivity extends AppCompatActivity {

    int quizId = 0;
    private TextView question;
    private RadioGroup radioGroup;
    private RadioButton option1, option2, option3, option4;
    private Button nextButton, backToQuizSelectionButton;
    private int currentQuestionIndex = 0;
    private int correctAnswers = 0;

    DatabaseManager db;

    private List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        question = findViewById(R.id.textViewQuestion);
        radioGroup = findViewById(R.id.radioGroup);
        option1 = findViewById(R.id.rbQuestion1);
        option2 = findViewById(R.id.rbQuestion2);
        option3 = findViewById(R.id.rbQuestion3);
        option4 = findViewById(R.id.rbQuestion4);
        nextButton = findViewById(R.id.buttonNext);
        backToQuizSelectionButton = findViewById(R.id.buttonGoToQuizSelection);

        quizId = getIntent().getIntExtra("QUIZ_ID",0);

        if (quizId == 0)
        {
            quizId = 1;
        }

        db = new DatabaseManager(this);
        questionList = db.getQuestionsForQuiz(quizId);
        displayQuestion();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentQuestionIndex < questionList.size()) {
                    checkAnswer();
                    currentQuestionIndex++;

                    if (currentQuestionIndex < questionList.size()) {
                        displayQuestion();
                    } else {
                        nextButton.setText("Sonuçları Göster");
                    }
                } else {
                    // Eğer tüm sorular bitti ve tekrar basıldıysa, sonuç ekranına git
                    showResults();
                }
            }
        });

        backToQuizSelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuizActivity.this, QuizSelectionActivity.class));
                finish();
            }
        });
    }

    private void displayQuestion()
    {
        radioGroup.clearCheck();
        Question currentQuestion = questionList.get(currentQuestionIndex);
        question.setText(currentQuestion.getQuestionText());
        option1.setText(currentQuestion.getOption1());
        option2.setText(currentQuestion.getOption2());
        option3.setText(currentQuestion.getOption3());
        option4.setText(currentQuestion.getOption4());
    }

    private void checkAnswer() {
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        if (selectedRadioButtonId == -1) {
            return; // Hiçbir şey seçilmediyse kontrol etmeden çık
        }

        int userAnswer = 0;
        if (selectedRadioButtonId == option1.getId()) {
            userAnswer = 1;
        } else if (selectedRadioButtonId == option2.getId()) {
            userAnswer = 2;
        } else if (selectedRadioButtonId == option3.getId()) {
            userAnswer = 3;
        } else if (selectedRadioButtonId == option4.getId()) {
            userAnswer = 4;
        }

        if (userAnswer == questionList.get(currentQuestionIndex).getCorrectAnswer()) {
            correctAnswers++;
        }
    }

    private void showResults() {
        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
        SharedPreferences prefs = getSharedPreferences("quiz_prefs", MODE_PRIVATE);
        String username = prefs.getString("username", null);
        String quizTitle = db.getQuizTitleById(quizId);
        int score = 0;
        int iq = -1;

        if (quizId == 6)
        {
            iq = calculateIQ(correctAnswers);
            intent.putExtra("IQ_SCORE", iq);
            score = iq;
        }
        else
        {
            intent.putExtra("CORRECT_ANSWERS", correctAnswers);
            intent.putExtra("TOTAL_QUESTIONS", questionList.size());
            score = correctAnswers * 10;
        }
        db.insertQuizResult(username, quizTitle, score);
        startActivity(intent);
        finish();
    }

    private int calculateIQ(int correctAnswers) {
        if (correctAnswers <= 4) {
            return 70;
        } else if (correctAnswers <= 8) {
            return 85;
        } else if (correctAnswers <= 12) {
            return 100;
        } else if (correctAnswers <= 16) {
            return 115;
        } else if (correctAnswers <= 18) {
            return 130;
        } else {
            return 145;
        }
    }


}