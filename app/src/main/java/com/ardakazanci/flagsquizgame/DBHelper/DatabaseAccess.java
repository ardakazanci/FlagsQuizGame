package com.ardakazanci.flagsquizgame.DBHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ardakazanci.flagsquizgame.Common.Common;
import com.ardakazanci.flagsquizgame.Model.Question;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {


    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;

    Cursor c = null;




    private static String DB_NAME = "question.db";
    private static String DB_PATH = "";
    private SQLiteDatabase mDatabase;
    private Context mContext = null;

    public DatabaseAccess(Context context) {

        this.openHelper = new DatabaseOpenHelper(context);


    }


    public static DatabaseAccess getInstance(Context context){

        if(instance == null){
            instance = new DatabaseAccess(context);
        }

        return instance;

    }


    public void open(){
        this.db = openHelper.getWritableDatabase();
    }

    public void close(){
        if(db!=null){
            this.db.close();
        }
    }



    public List<Question> getQuestionMode(String mode) {

        List<Question> listQuestion = new ArrayList<Question>();

        Cursor c;


        this.db = openHelper.getWritableDatabase();


        int limit = 0;
        if (mode.equals(Common.MODE.EASY.toString())) {
            limit = 30;
        } else if (mode.equals(Common.MODE.MEDIUM.toString())) {
            limit = 50;
        } else if (mode.equals(Common.MODE.HARD.toString())) {
            limit = 100;
        } else if (mode.equals(Common.MODE.HARDEST.toString())) {
            limit = 200;
        }


        try {
            c = this.db.rawQuery("SELECT * FROM Question ORDER BY Random() LIMIT 10",new String[]{});
            if (c == null) {
                return null;
            }

            c.moveToFirst();
            do {

                int id = c.getInt(c.getColumnIndex("ID"));
                String image = c.getString(c.getColumnIndex("Image"));
                String answerA = c.getString(c.getColumnIndex("AnswerA"));
                String answerB = c.getString(c.getColumnIndex("AnswerB"));
                String answerC = c.getString(c.getColumnIndex("AnswerC"));
                String answerD = c.getString(c.getColumnIndex("AnswerD"));
                String correctAnswer = c.getString(c.getColumnIndex("CorrectAnswer"));

                Question question = new Question(id, image, answerA, answerB, answerC, answerD, correctAnswer);
                listQuestion.add(question);


            } while (c.moveToNext());
            c.close();


        } catch (Exception e) {
            e.printStackTrace();
        }


        return listQuestion;


    }











    /*public List<Question> getAllQuestion() {

        List<Question> listQuestion = new ArrayList<Question>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c;

        try {

            c = db.rawQuery("SELECT * FROM Question ORDER BY Random()", null);
            if (c == null) {
                return null;
            }

            c.moveToFirst();
            do {

                int id = c.getInt(c.getColumnIndex("ID"));
                String image = c.getString(c.getColumnIndex("Image"));
                String answerA = c.getString(c.getColumnIndex("AnswerA"));
                String answerB = c.getString(c.getColumnIndex("AnswerB"));
                String answerC = c.getString(c.getColumnIndex("AnswerC"));
                String answerD = c.getString(c.getColumnIndex("AnswerD"));
                String correctAnswer = c.getString(c.getColumnIndex("CorrectAnswer"));

                Question question = new Question(id, image, answerA, answerB, answerC, answerD, correctAnswer);
                listQuestion.add(question);


            } while (c.moveToNext());
            c.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

        db.close();
        return listQuestion;


    }*/



   /* // Score to Ranking insert Table
    public void insertScore(int score) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Score", score);
        db.insert("Ranking", null, contentValues);

    }

    // Get Score and sort ranking
    public List<Ranking> getRanking() {

        List<Ranking> rankingList = new ArrayList<Ranking>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try {

            c = db.rawQuery("SELECT * FROM Ranking ORDER BY Score DESC;", null);
            if (c == null) {
                return null;
            }

            c.moveToNext();
            do {

                int id = c.getInt(c.getColumnIndex("Id"));
                int score = c.getInt(c.getColumnIndex("Score"));

                Ranking ranking = new Ranking(id, score);
                rankingList.add(ranking);

            } while (c.moveToNext());
            c.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

        db.close();
        return rankingList;

    }*/


}
