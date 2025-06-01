package com.example.quizapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

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

        Button scoreboardButton = findViewById(R.id.scoreBoardButton);
        Button logoutButton = findViewById(R.id.buttonLogout);

        quiz1.setTag(1);
        quiz2.setTag(2);
        quiz3.setTag(3);
        quiz4.setTag(4);
        quiz5.setTag(5);
        quiz6.setTag(6);

        SharedPreferences prefs =
                getSharedPreferences("quiz_prefs", MODE_PRIVATE);
        //prefs.edit().remove("db_initialized").apply();

        boolean initialized = prefs.getBoolean("db_initialized", false);
        if (!initialized) {
            DatabaseManager db = new DatabaseManager(this);
            setDB(db);
            prefs.edit().putBoolean("db_initialized", true).apply();
        }

        View.OnClickListener quizClickListener = new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View v) {
                int quizId = (int)v.getTag();
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

        scoreboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizSelectionActivity.this, Scoreboard.class);
                startActivity(intent);
            }
        });


        logoutButton.setOnClickListener(v -> {
            prefs.edit().remove("username").apply();

            // LoginActivity'e yönlendir
            Intent intent = new Intent(QuizSelectionActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Geri tuşuyla dönülmesin
            startActivity(intent);
            finish();
        });
    }



    public void setDB(DatabaseManager db) {
        db.addQuiz(new Quiz(1, "Genel Kültür Türkiye"));
        db.addQuiz(new Quiz(2, "Genel Kültür"));
        db.addQuiz(new Quiz(3, "Bilim"));
        db.addQuiz(new Quiz(4, "Spor"));
        db.addQuiz(new Quiz(5, "Sinema"));
        db.addQuiz(new Quiz(6, "IQ"));

        for (int quizId = 1; quizId <= 6; quizId++) {
            List<Question> mevcut = db.getQuestionsForQuiz(quizId);
            if (mevcut.isEmpty()) {
                db.addQuestions(getQuestions(quizId));
            }
        }
    }

    public List<Question> getQuestions(int quizId) {
        List<Question> questionList = new ArrayList<Question>();
        switch (quizId) {
            case 1:
                questionList.add(new Question(1, "Türkiye'nin başkenti hangisidir?",
                        "Zonguldak", "İstanbul", "Ankara", "Londra",
                        3));
                questionList.add(new Question(1, "İstanbul Boğazı hangi iki denizi birbirine bağlar?",
                        "Akdeniz ve Karadeniz", "Ege ve Marmara", "Karadeniz ve Marmara", "Ege ve Karadeniz",
                        3));
                questionList.add(new Question(1, "Dünya'nın en uzun nehri hangisidir?",
                        "Amazon Nehri", "Nil Nehri", "Mississippi Nehri", "Yangtze Nehri",
                        2));
                questionList.add(new Question(1, "Türkiye'nin en büyük yüzölçümüne sahip ili hangisidir?",
                        "Ankara", "İstanbul", "Konya", "Sivas",
                        3));
                questionList.add(new Question(1, "Hangisi bir elementtir?",
                        "Su", "Hava", "Demir", "Toprak",
                        3));
                questionList.add(new Question(1, "Ay'ın Dünya etrafında bir turu yaklaşık kaç gün sürer?",
                        "30", "15", "60", "7",
                        1));
                questionList.add(new Question(1, "Türkiye'nin en yüksek dağı hangisidir?",
                        "Erciyes", "Kaçkar", "Ağrı Dağı", "Toros",
                        3));
                questionList.add(new Question(1, "Türkiye'nin en uzun nehri hangisidir?",
                        "Kızılırmak", "Fırat", "Dicle", "Meriç",
                        1));
                questionList.add(new Question(1, "Atatürk'ün doğum yılı hangisidir?",
                        "1881", "1875", "1890", "1885",
                        1));
                questionList.add(new Question(1, "Hangisi Osmanlı Padişahı değildir?",
                        "Yavuz Sultan Selim", "Kanuni Sultan Süleyman", "Fatih Sultan Mehmet", "Mehmet Akif Ersoy",
                        4));
                break;
            case 2:
                questionList.add(new Question(2, "Eyfel Kulesi hangi şehirde bulunur?",
                        "Berlin", "Paris", "Roma", "Madrid",
                        2));
                questionList.add(new Question(2, "Dünya'nın en büyük okyanusu hangisidir?",
                        "Atlantik Okyanusu", "Hint Okyanusu", "Arktik Okyanusu", "Pasifik Okyanusu",
                        4));
                questionList.add(new Question(2, "Dünya Sağlık Örgütü'nün (WHO) merkezi nerededir?",
                        "New York", "Cenevre", "Paris", "Berlin",
                        2));
                questionList.add(new Question(2, "Hangisi bir İskandinav ülkesidir?",
                        "İspanya", "İsveç", "İtalya", "Polonya",
                        2));
                questionList.add(new Question(2, "Mona Lisa tablosu hangi sanatçıya aittir?",
                        "Leonardo da Vinci", "Pablo Picasso", "Vincent van Gogh", "Michelangelo",
                        1));
                questionList.add(new Question(2, "Dünyanın en kalabalık ülkesi hangisidir?",
                        "Türkiye", "ABD", "Rusya", "Çin"
                        , 4));
                questionList.add(new Question(2, "İnsan vücudunda en uzun kemik hangisidir?",
                        "Kaval Kemiği", "Kürek Kemiği", "Kafatası", "Omurga",
                        1));
                questionList.add(new Question(2, "Hangisi bir gezegen değildir?",
                        "Venüs", "Mars", "Ay", "Satürn",
                        3));
                questionList.add(new Question(2, "İnsan vücudunda en güçlü kas aşağıdakilerden hangisidir?",
                        "Dil", "Bacak", "Kol", "Boyun",
                        1));
                break;
            case 3:
                questionList.add(new Question(3, "Elektriği ileten en iyi metal hangisidir?",
                        "Altın", "Bakır", "Gümüş", "Demir",
                        3));
                questionList.add(new Question(3, "Dünya'nın uydusunun adı nedir?",
                        "Mars", "Güneş", "Ay", "Venüs",
                        3));
                questionList.add(new Question(3, "DNA'nın yapısında hangi şeker bulunur?",
                        "Glikoz", "Riboz", "Deoksiriboz", "Sakkaroz",
                        3));
                questionList.add(new Question(3, "İlk bilgisayar hangi yılda icat edilmiştir?",
                        "1945", "1920", "1980", "1955",
                        1));
                questionList.add(new Question(3, "İnsanda kalıtsal özelliklerin taşındığı yapıya ne ad verilir?",
                        "Kromozom", "Hücre", "Protein", "Enzim",
                        1));
                questionList.add(new Question(3, "Güneş sisteminde halkaları en belirgin olan gezegen hangisidir?",
                        "Mars", "Satürn", "Venüs", "Dünya",
                        2));
                questionList.add(new Question(3, "En küçük atom parçacığı hangisidir?",
                        "Proton", "Nötron", "Elektron", "Atom çekirdeği",
                        3));
                questionList.add(new Question(3, "Bilgisayarlarda kullanılan 'CPU' neyin kısaltmasıdır?",
                        "Central Processing Unit", "Central Power Unit", "Computer Processing Unit", "Control Processing Unit",
                        1));
                questionList.add(new Question(3, "Hangi bilim insanı yerçekimi kanununu keşfetmiştir?",
                        "Albert Einstein", "Galileo Galilei", "Isaac Newton", "Nikola Tesla",
                        3));
                questionList.add(new Question(3, "Hangisi bir programlama dili değildir?",
                        "Python", "Java", "HTML", "Microsoft",
                        4));
                break;
            case 4:
                questionList.add(new Question(4, "2022 FIFA Dünya Kupası'nı hangi ülke kazandı?",
                        "Arjantin", "Fransa", "Brezilya", "İspanya",
                        1));
                questionList.add(new Question(4, "NBA'de en çok şampiyonluk kazanan takım hangisidir?",
                        "Los Angeles Lakers", "Chicago Bulls", "Boston Celtics", "Golden State Warriors",
                        3));
                questionList.add(new Question(4, "Formula 1'de en fazla dünya şampiyonluğu olan pilot kimdir?",
                        "Sebastian Vettel", "Lewis Hamilton", "Michael Schumacher", "Ayrton Senna",
                        2));
                questionList.add(new Question(4, "Olimpiyatlar kaç yılda bir düzenlenir?",
                        "2", "3", "4", "5",
                        3));
                questionList.add(new Question(4, "Rafael Nadal hangi spor dalıyla ilgilidir?",
                        "Futbol", "Tenis", "Basketbol", "Atletizm",
                        2));
                questionList.add(new Question(4, "Türkiye Süper Ligi'nde 4 büyüklerden biri değildir?",
                        "Beşiktaş", "Fenerbahçe", "Trabzonspor", "Bursaspor",
                        4));
                questionList.add(new Question(4, "Messi'nin 2023 yılında oynadığı kulüp hangisiydi?",
                        "Barcelona", "Paris Saint-Germain", "Manchester City", "Al-Nassr",
                        2));
                questionList.add(new Question(4, "Hangisi bir güreş türü değildir?",
                        "Serbest Güreş", "Greko-Romen Güreş", "Sumo Güreşi", "Badminton",
                        4));
                questionList.add(new Question(4, "Türkiye'de düzenlenen ilk UEFA finali hangi şehirde oynanmıştır?",
                        "İstanbul", "Ankara", "İzmir", "Antalya",
                        1));
                questionList.add(new Question(4, "Basketbolda potaya atılan ve sayı olmayan atışa ne ad verilir?",
                        "Airball", "Smash", "Turnike", "Servis",
                        1));
                break;
            case 5:
                questionList.add(new Question(5, "Titanic filmi hangi yönetmene aittir?",
                        "Steven Spielberg", "James Cameron", "Christopher Nolan", "Quentin Tarantino",
                        2));
                questionList.add(new Question(5, "Oscar ödüllerinde 'En İyi Film' ödülünü kazanan ilk animasyon filmi hangisidir?",
                        "Shrek", "Up", "Beauty and the Beast", "Toy Story",
                        3));
                questionList.add(new Question(5, "The Godfather filminde 'Don Corleone' karakterini kim oynamıştır?",
                        "Robert De Niro", "Al Pacino", "Marlon Brando", "Joe Pesci",
                        3));
                questionList.add(new Question(5, "Avengers serisinde Iron Man karakterini kim canlandırmıştır?",
                        "Chris Evans", "Robert Downey Jr.", "Mark Ruffalo", "Chris Hemsworth",
                        2));
                questionList.add(new Question(5, "Hangisi Christopher Nolan'ın bir filmidir?",
                        "Inception", "The Irishman", "Titanic", "La La Land",
                        1));
                questionList.add(new Question(5, "Yüzüklerin Efendisi serisinin yazarı kimdir?",
                        "J.K. Rowling", "George R.R. Martin", "J.R.R. Tolkien", "Stephen King",
                        3));
                questionList.add(new Question(5, "Oscar tarihinde en çok ödül kazanan film hangisidir?",
                        "Titanic", "The Lord of the Rings: The Return of the King", "Ben-Hur", "Hepsi",
                        4));
                questionList.add(new Question(5, "Matrix filminde Neo karakteri kim tarafından oynanmıştır?",
                        "Keanu Reeves", "Brad Pitt", "Leonardo DiCaprio", "Matt Damon",
                        1));
                questionList.add(new Question(5, "Hangisi bir Quentin Tarantino filmidir?",
                        "Pulp Fiction", "Inception", "The Revenant", "Interstellar",
                        1));
                questionList.add(new Question(5, "Türkiye'nin Oscar adayı olan 'Ahlat Ağacı' filmini kim yönetmiştir?",
                        "Nuri Bilge Ceylan", "Yılmaz Güney", "Ferzan Özpetek", "Çağan Irmak",
                        1));
                break;
            case 6:
                questionList.add(new Question(6, "Bir saatin akrep ve yelkovanı günde kaç kere üst üste gelir?",
                        "22", "24", "12", "23", 1));

                questionList.add(new Question(6, "1, 4, 9, 16, 25, 36, ? Dizisinde ? yerine hangi sayı gelmelidir?",
                        "49", "48", "45", "47", 1));

                questionList.add(new Question(6, "Bir çiftlikte 5 tavuk ve 3 inek varsa, toplam kaç bacak vardır?",
                        "22", "26", "28", "32", 4));

                questionList.add(new Question(6, "Bir adam doğum gününde 32 yaşına girdi. 8 yıl önce kaç yaşındaydı?",
                        "24", "22", "26", "25", 1));

                questionList.add(new Question(6, "Pazartesi günü saat 15.00'te başlayan bir film 150 dakika sürüyor. Film hangi saatte biter?",
                        "17.00", "17.30", "18.00", "18.30", 2));

                questionList.add(new Question(6, "Hangisi farklıdır?",
                        "Elma", "Portakal", "Üzüm", "Havuç", 4));

                questionList.add(new Question(6, "Hangi sayı 3 ile tam bölünür?",
                        "124", "126", "128", "130", 2));

                questionList.add(new Question(6, "Bazı aylar 31 gün, bazıları 30 gün çeker. Kaç ayda 28 gün vardır?",
                        "1", "12", "6", "7", 2));

                questionList.add(new Question(6, "Bir odada 3 lamba ve 2 anahtar var. Kaç farklı kombinasyon yapılabilir?",
                        "6", "4", "8", "12", 3));

                questionList.add(new Question(6, "Eğer bir trende 30 kişi varsa ve 10 kişi daha binerse, toplam kaç kişi olur?",
                        "35", "38", "40", "42", 3));

                questionList.add(new Question(6, "2, 6, 12, 20, 30, ? Dizisinde sıradaki sayı nedir?",
                        "40", "42", "44", "46", 2));

                questionList.add(new Question(6, "Ali'nin yaşı Ahmet'in yaşının 2 katıdır. Eğer Ahmet 15 yaşında ise, Ali kaç yaşındadır?",
                        "25", "30", "20", "40", 2));

                questionList.add(new Question(6, "Bir ağaçtan her gün 3 elma düşüyor. 5 günde kaç elma düşer?",
                        "12", "15", "18", "20", 2));

                questionList.add(new Question(6, "Bir tren 60 km/s hızla giderse, 180 km yolu kaç saatte tamamlar?",
                        "2", "3", "4", "5", 2));

                questionList.add(new Question(6, "Bir karenin çevresi 40 cm ise, bir kenarı kaç cm'dir?",
                        "10", "20", "15", "25", 1));

                questionList.add(new Question(6, "Hangi kelime diğerlerinden farklıdır?",
                        "Kitap", "Defter", "Kalem", "Bardak", 4));

                questionList.add(new Question(6, "Hangisi çift sayıdır?",
                        "11", "13", "17", "18", 4));

                questionList.add(new Question(6, "Bir köprü 50 metre uzunluğunda. Her saniye 2 metre yürürsen kaç saniyede geçersin?",
                        "25", "20", "30", "15", 1));

                questionList.add(new Question(6, "Hangisi üçgen değildir?",
                        "Eşkenar", "İkizkenar", "Kare", "Dik üçgen", 3));

                questionList.add(new Question(6, "5 makine 5 ürünü 5 dakikada yapıyorsa, 10 makine 10 ürünü kaç dakikada yapar?",
                        "5", "10", "2.5", "7.5", 1));
                break;
        }

        return questionList;
    }
}