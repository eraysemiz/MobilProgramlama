package com.example.quizapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME    = "QuizApp.db";
    private static final int    DATABASE_VERSION = 4;

    // Quiz tablosu
    public static final String TABLE_QUIZ      = "quiz";
    public static final String QUIZ_ID         = "quizId";
    public static final String QUIZ_TITLE      = "quizTitle";

    // Questions tablosu
    public static final String TABLE_QUESTION        = "questions";
    public static final String QUESTION_ID           = "id";
    public static final String QUESTION_QUIZ_ID      = "quizId";
    public static final String QUESTION_TEXT         = "questionText";
    public static final String QUESTION_OPTION1      = "option1";
    public static final String QUESTION_OPTION2      = "option2";
    public static final String QUESTION_OPTION3      = "option3";
    public static final String QUESTION_OPTION4      = "option4";
    public static final String QUESTION_CORRECT_ANS  = "correctAnswer";

    // Kullanıcı tablosı
    public static final String TABLE_USER       = "users";
    public static final String USERNAME  = "username";
    public static final String PASSWORD  = "password";
    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createQuizTable = ""
                + "CREATE TABLE IF NOT EXISTS " + TABLE_QUIZ + " ("
                +     QUIZ_ID    + " TEXT PRIMARY KEY, "
                +     QUIZ_TITLE + " TEXT NOT NULL"
                + ");";
        db.execSQL(createQuizTable);
        // questions tablosu
        String createQuestionTable = ""
                + "CREATE TABLE IF NOT EXISTS " + TABLE_QUESTION + " ("
                +     QUESTION_ID          + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +     QUESTION_QUIZ_ID     + " TEXT NOT NULL, "
                +     QUESTION_TEXT        + " TEXT NOT NULL, "
                +     QUESTION_OPTION1     + " TEXT NOT NULL, "
                +     QUESTION_OPTION2     + " TEXT NOT NULL, "
                +     QUESTION_OPTION3     + " TEXT NOT NULL, "
                +     QUESTION_OPTION4     + " TEXT NOT NULL, "
                +     QUESTION_CORRECT_ANS + " INTEGER NOT NULL, "
                +     "FOREIGN KEY(" + QUESTION_QUIZ_ID + ") REFERENCES "
                +         TABLE_QUIZ + "(" + QUIZ_ID + ")"
                + ");";
        db.execSQL(createQuestionTable);

        String createUserTable = ""
                + "CREATE TABLE IF NOT EXISTS " + TABLE_USER + " ("
                +     USERNAME + " TEXT PRIMARY KEY, "
                +     PASSWORD + " TEXT NOT NULL"
                + ");";
        db.execSQL(createUserTable);

        String CREATE_RESULTS_TABLE = "CREATE TABLE IF NOT EXISTS quiz_results (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT, " +
                "quiz_title TEXT, " +
                "score INTEGER, " +
                "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(CREATE_RESULTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // versiyon yükseldiğinde önceki tabloları sil, sonra yeniden oluştur
        onCreate(db);
    }

    public void addQuiz(Quiz quiz) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT OR IGNORE INTO " + TABLE_QUIZ +
                " (" + QUIZ_ID + ", " + QUIZ_TITLE + ") VALUES (?, ?)";
        db.execSQL(sql, new Object[]{
                quiz.getQuizId(),
                quiz.getQuizTitle()
        });
        db.close();
    }

    public void addQuestions(List<Question> questions) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            String sql = "INSERT INTO " + TABLE_QUESTION + " (" +
                    QUESTION_QUIZ_ID + ", " +
                    QUESTION_TEXT + ", " +
                    QUESTION_OPTION1 + ", " +
                    QUESTION_OPTION2 + ", " +
                    QUESTION_OPTION3 + ", " +
                    QUESTION_OPTION4 + ", " +
                    QUESTION_CORRECT_ANS +
                    ") VALUES (?, ?, ?, ?, ?, ?, ?)";
            for (Question q : questions) {
                db.execSQL(sql, new Object[]{
                        q.getQuizID(),
                        q.getQuestionText(),
                        q.getOption1(),
                        q.getOption2(),
                        q.getOption3(),
                        q.getOption4(),
                        q.getCorrectAnswer()
                });
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public List<Question> getQuestionsForQuiz(int quizId) {
        List<Question> questions = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String sql = ""
                + "SELECT "
                +     QUESTION_TEXT    + ", "
                +     QUESTION_OPTION1 + ", "
                +     QUESTION_OPTION2 + ", "
                +     QUESTION_OPTION3 + ", "
                +     QUESTION_OPTION4 + ", "
                +     QUESTION_CORRECT_ANS
                + " FROM " + TABLE_QUESTION
                + " WHERE " + QUESTION_QUIZ_ID + " = ?";

        Cursor c = db.rawQuery(sql, new String[]{ String.valueOf(quizId) });
        if (c.moveToFirst()) {
            do {
                String text    = c.getString(c.getColumnIndexOrThrow(QUESTION_TEXT));
                String opt1    = c.getString(c.getColumnIndexOrThrow(QUESTION_OPTION1));
                String opt2    = c.getString(c.getColumnIndexOrThrow(QUESTION_OPTION2));
                String opt3    = c.getString(c.getColumnIndexOrThrow(QUESTION_OPTION3));
                String opt4    = c.getString(c.getColumnIndexOrThrow(QUESTION_OPTION4));
                int    correct = c.getInt   (c.getColumnIndexOrThrow(QUESTION_CORRECT_ANS));

                questions.add(
                        new Question(
                                quizId,        // quizID
                                text,
                                opt1,
                                opt2,
                                opt3,
                                opt4,
                                correct
                        )
                );
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return questions;
    }

    public boolean registerUser(String username, String password) {
        SQLiteDatabase db = getWritableDatabase();

        // 1) Kullanıcı adı var mı diye rawQuery ile kontrol et
        Cursor c = db.rawQuery(
                "SELECT 1 FROM " + TABLE_USER + " WHERE " + USERNAME + " = ?",
                new String[]{ username }
        );
        boolean exists = c.moveToFirst();
        c.close();
        if (exists) {
            db.close();
            return false;
        }

        // 2) execSQL ile ekle
        String insertSql =
                "INSERT INTO " + TABLE_USER +
                        " (" + USERNAME + ", " + PASSWORD + ")" +
                        " VALUES (?, ?);";
        db.execSQL(insertSql, new Object[]{ username, password });

        db.close();
        return true;
    }

    public boolean validateLogin(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT 1 FROM " + TABLE_USER +
                        " WHERE " + USERNAME + " = ?" +
                        "   AND " + PASSWORD + " = ?",
                new String[]{ username, password }
        );
        boolean valid = c.moveToFirst();
        c.close();
        db.close();
        return valid;
    }

    public void insertQuizResult(String username, String quizTitle, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO quiz_results (username, quiz_title, score) VALUES (?, ?, ?)";
        db.execSQL(sql, new Object[]{username, quizTitle, score});
    }

    public Cursor getScoresByUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT quiz_title, score, timestamp FROM quiz_results WHERE username = ?";
        return db.rawQuery(sql, new String[]{username});
    }

    public Cursor getAllScores() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT username, quiz_title, score, timestamp FROM quiz_results", null);
    }

    public String getQuizTitleById(int quizId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String title = null;

        Cursor cursor = db.rawQuery("SELECT quizTitle FROM quiz WHERE quizId = ?", new String[]{String.valueOf(quizId)});
        if (cursor.moveToFirst()) {
            title = cursor.getString(0);
        }
        cursor.close();
        return title;
    }

}
