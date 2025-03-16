package com.example.quizapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView question;
    private RadioGroup radioGroup;
    private RadioButton option1, option2, option3, option4;
    private Button nextButton;
    private int currentQuestionIndex = 0;
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

        String quizId = getIntent().getStringExtra("QUIZ_ID");

        if (quizId == null)
        {
            quizId = "quiz1";
        }

        loadQuestions(quizId);
        displayQuestion();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQuestionIndex++;
                if (currentQuestionIndex < questionList.size()) {
                    displayQuestion();
                }
                else
                {
                    nextButton.setEnabled(false);
                    nextButton.setText("Quiz Tamamlandı");
                }
            }
        });
    }

    private void loadQuestions(String quizId)
    {
        questionList = new ArrayList<>();

        if (quizId.equals("quiz1"))
        {
            questionList.add(new Question("Türkiye'nin başkenti hangisidir?",
                    "Zonguldak", "İstanbul", "Ankara", "Londra",
                    3));
            questionList.add(new Question("Dünyanın en kalabalık ülkesi hangisidir?",
                    "Türkiye", "ABD", "Rusya", "Çin", 3));

        }
        else if (quizId.equals("quiz2"))
        {

        }
        else if (quizId.equals("quiz3"))
        {

        }
        else if (quizId.equals("quiz4"))
        {

        }
        else if (quizId.equals("quiz5"))
        {

        }
        else if (quizId.equals("quiz6"))
        {

        }
    }

    private void displayQuestion()
    {
        Question currentQuestion = questionList.get(currentQuestionIndex);
        question.setText(currentQuestion.getQuestionText());
        option1.setText(currentQuestion.getOption1());
        option2.setText(currentQuestion.getOption2());
        option3.setText(currentQuestion.getOption3());
        option4.setText(currentQuestion.getOption4());

        radioGroup.clearCheck();
    }
    private static class Question
    {
        private String questionText, option1, option2, option3, option4;
        private int correctAnswer;

        public Question(String questionText, String option1, String option2, String option3, String option4, int correctAnswer) {
            this.questionText = questionText;
            this.option1 = option1;
            this.option2 = option2;
            this.option3 = option3;
            this.option4 = option4;
            this.correctAnswer = correctAnswer;
        }

        public String getQuestionText() {
            return questionText;
        }

        public String getOption1() {
            return option1;
        }

        public String getOption2() {
            return option2;
        }

        public String getOption3() {
            return option3;
        }

        public String getOption4() {
            return option4;
        }

        public int getCorrectAnswer() {
            return correctAnswer;
        }

    }

}