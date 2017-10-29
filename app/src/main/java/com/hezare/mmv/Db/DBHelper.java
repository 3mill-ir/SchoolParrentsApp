package com.hezare.mmv.Db;

/**
 * Created by amirhododi on 8/15/2017.
 */


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "DB.db";
    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table dars " +
                        "(id integer, title text, nomre text,seen integer)"
        );

        db.execSQL(
                "create table students " +
                        "(idin integer primary key,id integer, name text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS dars");
        onCreate(db);
  db.execSQL("DROP TABLE IF EXISTS students");
        onCreate(db);


    }

    public boolean insertDars (Integer id,String title ,String nomre,int seen) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("title", title);
        contentValues.put("nomre", nomre);
        contentValues.put("seen", seen);
        db.insert("dars", null, contentValues);
        return true;
    }



    public boolean insertStudent (Integer id,String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("name", name);
        db.insert("students", null, contentValues);
        return true;
    }



    public Cursor getDarsData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from dars where id="+id+"", null );
        return res;
    }
    public Cursor getStudentData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from students where idin="+id+"", null );
        return res;
    }

    public int numberOfRowsDars(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, "dars");
        return numRows;
    }
    public void LogOut(){
        SQLiteDatabase db = this.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        db.delete("students", null, null);
        db.delete("dars", null, null);
    }

    public void ClearDars(){
        SQLiteDatabase db = this.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        db.delete("dars", null, null);
    }

    public int numberOfRowsDarsAva(int id){
        String countQuery = "select * from dars where id="+id+"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    public int numberOfRowsStudentAva(int id){
        String countQuery = "select * from students where id="+id+"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }
    public int numberOfRowsStudent(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, "students");
        return numRows;
    }
    public boolean updateDars (Integer id,Integer seen) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("seen", seen);
        db.update("dars", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteDars (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("dars",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }




    public ArrayList<Integer> getAllDars() {
        ArrayList<Integer> array_list = new ArrayList<Integer>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from dars ORDER BY id DESC", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getInt(res.getColumnIndex("id")));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<Integer> getAllStudents() {
        ArrayList<Integer> array_list = new ArrayList<Integer>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from students", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getInt(res.getColumnIndex("idin")));
            res.moveToNext();
        }
        return array_list;
    }

}