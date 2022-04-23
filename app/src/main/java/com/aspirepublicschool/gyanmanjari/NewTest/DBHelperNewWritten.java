package com.aspirepublicschool.gyanmanjari.NewTest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.aspirepublicschool.gyanmanjari.Test.TestTimer;

import java.util.ArrayList;
import java.util.HashMap;

class DBHelperNewWritten extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "written_test_db";
    public static final String CONTACTS_TABLE_NAME = "test";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "testid";
    private HashMap hp;

    public DBHelperNewWritten(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table new_written_test " +
                        "(id integer primary key AUTOINCREMENT, testid text,time text)"
        );
        db.execSQL(
                "create table new_written_test_data " +
                        "(id integer primary key AUTOINCREMENT, testid text,q_id text,question text," +
                        "answer text, sign text,mark integer,subject text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS new_written_test");
        onCreate(db);
    }

    public boolean insertContact (String name,String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("testid", name);
        contentValues.put("time", time);
        db.insert("new_written_test", null, contentValues);
        return true;
    }

    public boolean inserttest (String testid, String q_id,String question,String answer,String sign, int mark,String subject) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("testid", testid);
        contentValues.put("q_id", q_id);
        contentValues.put("question", question);
        contentValues.put("answer", answer);
        contentValues.put("sign", sign);
        contentValues.put("mark", mark);
        contentValues.put("subject", subject);
        db.insert("new_written_test_data", null, contentValues);
        return true;
    }

    public Cursor getData(String testid) {
        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor res =  db.rawQuery( "select * from test where testid='"+testid+"", null );
        Cursor res =  db.rawQuery( "Select * from new_written_test Where testid='"+testid+"'", null );
        return res;
    }

    public Cursor getQuestions(String testid,String q_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor res =  db.rawQuery( "select * from test where testid='"+testid+"", null );
        String sql="Select * from new_written_test_data Where testid='"+testid+"'"+" AND q_id='"+q_id+"'";
        Cursor res =  db.rawQuery( sql, null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact (String name,String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("testid", name);
        contentValues.put("time", time);
        db.update("new_written_test", contentValues, " testid= ? ", new String[] { name } );
        return true;
    }

    public boolean updateTest (String testid,String q_id,String answer,String sign) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("testid", testid);
        contentValues.put("answer", answer);
        contentValues.put("sign", sign);
        db.update("new_written_test_data", contentValues, "testid = ? and q_id=?", new String[] { testid,q_id} );
        return true;
    }

    public boolean updateMark (String testid,String q_id,int mark) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("testid", testid);
        contentValues.put("mark", mark);
        db.update("new_written_test_data", contentValues, "testid = ? and q_id=?", new String[] { testid,q_id} );
        return true;
    }
    public Integer deleteContact (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("new_written_test",
                "testid = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<TestTimer> getAllCotacts(String testid) {
        ArrayList<TestTimer> array_list = new ArrayList<TestTimer>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from new_written_test Where testid='"+testid+"'", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            String tstid=res.getString(res.getColumnIndex("testid"));
            String time=res.getString(res.getColumnIndex("time"));
            array_list.add(new TestTimer(tstid,time));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<TestWrittenAnswer> getAllTestData(String tst_id) {
        ArrayList<TestWrittenAnswer> array_list = new ArrayList<TestWrittenAnswer>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from  new_written_test_data Where testid='"+tst_id+"'", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            String test_id=res.getString(res.getColumnIndex("testid"));
            String q_id=res.getString(res.getColumnIndex("q_id"));
            String answer=res.getString(res.getColumnIndex("answer"));
            String sign=res.getString(res.getColumnIndex("sign"));
            int mark= res.getInt(res.getColumnIndex("mark"));
            array_list.add(new TestWrittenAnswer(test_id,q_id,answer,sign,mark));
            res.moveToNext();
        }
        db.close();
        return array_list;
    }

    public ArrayList<TestWrittenAnswer> getTestData(String tst_id,String qid) {
        ArrayList<TestWrittenAnswer> array_list = new ArrayList<TestWrittenAnswer>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from  new_written_test_data Where testid='"+tst_id+"' and qid='"+qid+"' " , null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            String test_id=res.getString(res.getColumnIndex("testid"));
            String q_id=res.getString(res.getColumnIndex("q_id"));
            String answer=res.getString(res.getColumnIndex("answer"));
            String sign=res.getString(res.getColumnIndex("sign"));
            int mark= res.getInt(res.getColumnIndex("mark"));
            array_list.add(new TestWrittenAnswer(test_id,q_id,answer,sign,mark));
            res.moveToNext();
        }
        return array_list;
    }
}