package com.ardakazanci.flagsquizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.ardakazanci.flagsquizgame.Common.CustomAdapter;
import com.ardakazanci.flagsquizgame.DBHelper.DatabaseAccess;
import com.ardakazanci.flagsquizgame.Model.Ranking;

import java.util.List;

public class ScoreActivity extends AppCompatActivity {

    ListView lstRanking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        lstRanking = findViewById(R.id.lstRanking);

        DatabaseAccess databaseAccess = new DatabaseAccess(getApplicationContext());

        List<Ranking> lstRankingList = databaseAccess.getRanking();
        if (lstRankingList.size() > 0) {
            CustomAdapter adapter = new CustomAdapter(getApplicationContext(), lstRankingList);
            lstRanking.setAdapter(adapter);
        }


    }
}
