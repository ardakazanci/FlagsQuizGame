package com.ardakazanci.flagsquizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ardakazanci.flagsquizgame.Common.Common;
import com.ardakazanci.flagsquizgame.DBHelper.DatabaseAccess;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    TextView txtMode;
    Button btnPlay, btnScore;
    DatabaseAccess db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);
        txtMode = findViewById(R.id.txtMode);
        btnPlay = findViewById(R.id.btnPlay);
        btnScore = findViewById(R.id.btnScore);




        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (progress == 0) {
                    txtMode.setText(Common.MODE.EASY.toString());
                } else if (progress == 1) {
                    txtMode.setText(Common.MODE.MEDIUM.toString());
                } else if (progress == 2) {
                    txtMode.setText(Common.MODE.HARD.toString());
                } else if (progress == 3) {
                    txtMode.setText(Common.MODE.HARDEST.toString());
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = DatabaseAccess.getInstance(getApplicationContext());
                db.open();
                Intent intent = new Intent(getApplicationContext(), PlayingActivity.class);
                intent.putExtra("MODE", getPlayMode());
                startActivity(intent);
                finish();

            }
        });

        btnScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),ScoreActivity.class);
                startActivity(intent);
                finish();


            }
        });


    }

    private String getPlayMode() {

        if (seekBar.getProgress() == 0) {
            return Common.MODE.EASY.toString();
        } else if (seekBar.getProgress() == 1) {
            return Common.MODE.MEDIUM.toString();
        } else if (seekBar.getProgress() == 2) {
            return Common.MODE.HARD.toString();
        } else if (seekBar.getProgress() == 3) {
            return Common.MODE.HARDEST.toString();
        }
        return null;
    }




}
