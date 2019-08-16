package com.ardakazanci.flagsquizgame.Model;

public class Ranking {

    private int ranking_id;
    private int ranking_score;

    public Ranking(int ranking_id, int ranking_score) {
        this.ranking_id = ranking_id;
        this.ranking_score = ranking_score;
    }

    public int getRanking_id() {
        return ranking_id;
    }

    public void setRanking_id(int ranking_id) {
        this.ranking_id = ranking_id;
    }

    public int getRanking_score() {
        return ranking_score;
    }

    public void setRanking_score(int ranking_score) {
        this.ranking_score = ranking_score;
    }
}

