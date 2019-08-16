package com.ardakazanci.flagsquizgame.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.ardakazanci.flagsquizgame.Common.Common;
import com.ardakazanci.flagsquizgame.Model.Question;
import com.ardakazanci.flagsquizgame.Model.Ranking;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {


    private static String DB_NAME = "question.db";
    private static String DB_PATH = "";
    private SQLiteDatabase mDatabase;
    private Context mContext = null;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, 1);

        this.mContext = context;

        DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";



        try {
            String myPath = DB_PATH + DB_NAME; // also check the extension of you db file

            File dbfile = new File(myPath);

            if (dbfile.exists()) {
                Toast.makeText(context, "database exists", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "cant find database", Toast.LENGTH_LONG).show();
            }

        } catch (SQLiteException e) {
            System.out.println("Database doesn't exist");
        }


    }

    public void openDataBase() {
        String myPath = DB_PATH + DB_NAME;
        mDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }


    public void copyDataBase() throws IOException {
        try {
            InputStream mInput = mContext.getAssets().open(DB_NAME);
            String outFileName = DB_PATH + DB_NAME;
            OutputStream mOutput = new FileOutputStream(outFileName);
            byte[] mBuffer = new byte[2024];
            int mLength;
            while ((mLength = mInput.read(mBuffer)) > 0) {
                mOutput.write(mBuffer, 0, mLength);
            }
            mOutput.flush();
            mOutput.close();
            mInput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean checkDataBase() {

        boolean checkDB = false;
        try
        {
            String myPath = DB_PATH + DB_NAME;
            File dbfile = new File(myPath);
            checkDB = dbfile.exists();
        }
        catch(SQLiteException e)
        {
        }
        return checkDB;



        /*SQLiteDatabase tempDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            tempDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        if (tempDB != null)
            tempDB.close();
        return tempDB != null;*/
    }

    public void createDataBase() throws IOException {
        boolean isDBExists = checkDataBase();
        if (isDBExists) {
            Log.v("DB Exists", "db exists");
        } else {
            this.getReadableDatabase();
            try {
                this.close();
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public synchronized void close() {
        if (mDatabase != null) {
            mDatabase.close();
        }
        super.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // CRUD for table

    // Get All Question
    public List<Question> getAllQuestion() {

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


    }

    public List<Question> getQuestionMode(String mode) {

        List<Question> listQuestion = new ArrayList<Question>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
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

            c = db.rawQuery(String.format("SELECT * FROM Question ORDER BY Random() LIMIT %d", limit), null);
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


    }

    // Score to Ranking insert Table
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

    }


}
