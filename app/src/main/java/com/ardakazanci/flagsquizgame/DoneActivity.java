package com.ardakazanci.flagsquizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ardakazanci.flagsquizgame.DBHelper.DatabaseAccess;

public class DoneActivity extends AppCompatActivity {

    Button btnTryAgain;
    TextView txtTotalScore, txtTotalQuestion;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

        DatabaseAccess databaseAccess = new DatabaseAccess(getApplicationContext());

        btnTryAgain = findViewById(R.id.btnTryAgain);
        txtTotalScore = findViewById(R.id.txtTotalScore);
        txtTotalQuestion = findViewById(R.id.txtTotalQuestion);
        progressBar = findViewById(R.id.doneProgressBar);

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();


            }
        });
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            int score = extra.getInt("SCORE");
            int totalQuestion = extra.getInt("TOTAL");
            int correctAnswer = extra.getInt("CORRECT");

            txtTotalScore.setText(String.format("SCORE : %d", score));
            txtTotalQuestion.setText(String.format("PASSED : %d/%d", correctAnswer, totalQuestion));

            progressBar.setMax(totalQuestion);
            progressBar.setProgress(correctAnswer);

            databaseAccess.insertScore(score);

        }

    }
}
