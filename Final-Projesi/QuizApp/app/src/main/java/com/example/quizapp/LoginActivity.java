package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.SharedPreferences;

public class LoginActivity extends AppCompatActivity {

    Button loginButton;
    Button registerButton;
    EditText usernameInput;
    EditText passwordInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DatabaseManager db = new DatabaseManager(this);

        loginButton = findViewById(R.id.buttonLogin);
        registerButton = findViewById(R.id.buttonRegister);
        usernameInput = findViewById(R.id.editTextUsername);
        passwordInput = findViewById(R.id.editTextPassword);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = usernameInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                SharedPreferences prefs = getSharedPreferences("quiz_prefs", MODE_PRIVATE);
                prefs.edit().putString("username", username).apply();

                if (username.isEmpty() || password.isEmpty())
                    Toast.makeText(LoginActivity.this, "Lütfen kullanıcı adınızı ve şifrenizi giriniz.", Toast.LENGTH_SHORT).show();
                if (db.validateLogin(username, password)){
                    Intent intent = new Intent(LoginActivity.this, QuizSelectionActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                    Toast.makeText(LoginActivity.this, "Kullanıcı adı veya şifre yanlış", Toast.LENGTH_SHORT).show();


            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty())
                    Toast.makeText(LoginActivity.this, "Lütfen kullanıcı adınızı ve şifrenizi giriniz.", Toast.LENGTH_SHORT).show();

                boolean isUsernameAvailable = db.registerUser(username, password);
                if (isUsernameAvailable)
                    Toast.makeText(LoginActivity.this, "Kayıt başarılı!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(LoginActivity.this, "Bu kullanıcı zaten kayıtlı", Toast.LENGTH_SHORT).show();
            }
        });
    }
}