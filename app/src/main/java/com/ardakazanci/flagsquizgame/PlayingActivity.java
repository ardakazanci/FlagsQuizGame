package com.ardakazanci.flagsquizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ardakazanci.flagsquizgame.DBHelper.DbHelper;
import com.ardakazanci.flagsquizgame.Model.Question;

import java.util.ArrayList;
import java.util.List;

public class PlayingActivity extends AppCompatActivity implements View.OnClickListener {

    final static long INTERVAL = 1000; // 1 Saniye
    final static long TIMEOUT = 7000; // 7 Saniye
    int progressValue = 0;

    CountDownTimer countDownTimer; // ProgressBar için kullanılacak
    List<Question> questionPlay = new ArrayList<Question>();  // Bir oyunda oynanayacak sorular.

    DbHelper db;

    // Oyunda gösterilecek değerler.
    int index = 0, score = 0, thisQuestion = 0, totalQuestion, correctAnswer;

    // Oyunun Modu
    String mode = "";

    ProgressBar progressBar;
    ImageView imageView;
    Button btnA, btnB, btnC, btnD;
    TextView txtScore, txtQuestion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            mode = extra.getString("MODE");
        }

        db = new DbHelper(this);


        progressBar = findViewById(R.id.progressBar);
        imageView = findViewById(R.id.question_flag);
        btnA = findViewById(R.id.btnAnswerA);
        btnB = findViewById(R.id.btnAnswerB);
        btnC = findViewById(R.id.btnAnswerC);
        btnD = findViewById(R.id.btnAnswerD);
        txtScore = findViewById(R.id.txtScore);
        txtQuestion = findViewById(R.id.txtQuestion);

        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);


    }


    @Override
    protected void onResume() {
        super.onResume();

        questionPlay = db.getQuestionMode(mode);

        totalQuestion = questionPlay.size();

        countDownTimer = new CountDownTimer(TIMEOUT, INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                progressBar.setProgress(progressValue);
                progressValue++;
            }

            @Override
            public void onFinish() {
                countDownTimer.cancel();
                showQuestion(++index);
            }
        };

        showQuestion(index);
    }

    private void showQuestion(int index) {

        if (index < totalQuestion) {

            thisQuestion++;
            txtQuestion.setText(String.format("%d/%d", thisQuestion, totalQuestion));
            progressBar.setProgress(0);
            progressValue = 0;

            int imageId = this.getResources().getIdentifier(questionPlay.get(index).getQuestion_image().toLowerCase(), "drawable", getPackageName());
            imageView.setBackgroundResource(imageId);
            btnA.setText(questionPlay.get(index).getQuestion_answer_a());
            btnB.setText(questionPlay.get(index).getQuestion_answer_b());
            btnC.setText(questionPlay.get(index).getQuestion_answer_c());
            btnD.setText(questionPlay.get(index).getQuestion_answer_d());

            countDownTimer.start();


        } else {

            Intent intent = new Intent(this, DoneActivity.class);
            Bundle dataSend = new Bundle();
            dataSend.putInt("SCORE", score);
            dataSend.putInt("TOTAL", totalQuestion);
            dataSend.putInt("CORRECT", correctAnswer);
            intent.putExtras(dataSend);
            startActivity(intent);
            finish();


        }


    }

    @Override
    public void onClick(View v) {

        countDownTimer.cancel();
        if (index < totalQuestion) {
            Button clickedButton = (Button) v;
            if (clickedButton.getText().equals(questionPlay.get(index).getQuestion_correct_answer())) {
                score++;
                correctAnswer++;
                showQuestion(++index);
            } else {
                showQuestion(++index);
            }

            txtScore.setText(String.format("%d",score));
        }


    }
}
