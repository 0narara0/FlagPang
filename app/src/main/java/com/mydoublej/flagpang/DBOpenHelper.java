package com.mydoublej.flagpang;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBOpenHelper extends SQLiteOpenHelper {
    private  static DBOpenHelper instance;
    private  static SQLiteDatabase mdb;
    public static final  String DB_NAME = "flagpang.db";
    private static  final SQLiteDatabase.CursorFactory FACTORY = null;
    public  static final  int VERSION = 1;

    String sql;
    Cursor cursor;

    public  static DBOpenHelper getInstance(Context context) {
        if(instance == null){
            instance = new DBOpenHelper(context);
        }
        mdb = instance.getWritableDatabase();
        return instance;
    }


    private DBOpenHelper(Context context){
        super(context, DB_NAME, FACTORY, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        sql = "CREATE TABLE flag" +
                "(id integer UNIQUE PRIMARY KEY AUTOINCREMENT, " +
                "country text NOT NULL UNIQUE, " +
                "continent text NOT NULL, " +
                "image text NOT NULL UNIQUE," +
                "level text NOT NULL);";
        db.execSQL(sql);

       table_inputdata(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE flag");
        onCreate(db);
    }

    public ArrayList<GetRecord> selectGetRecord(){
        sql = "SELECT * FROM flag";
        cursor = mdb.rawQuery(sql,null);

        ArrayList<GetRecord> arrayList = new ArrayList<>();
        GetRecord getRecord;
        String country, continent, image, level;
        int id;

        while(cursor.moveToNext()){
            id = cursor.getInt(cursor.getColumnIndex("id"));
            country = cursor.getString(cursor.getColumnIndex("country"));
            continent = cursor.getString(cursor.getColumnIndex("continent"));
            image = cursor.getString(cursor.getColumnIndex("image"));
            level = cursor.getString(cursor.getColumnIndex("level"));

            getRecord = new GetRecord(id, country, continent, image, level);
            arrayList.add(getRecord);
        }

        return arrayList;
    }

    private void table_inputdata(SQLiteDatabase db){
    }


}
