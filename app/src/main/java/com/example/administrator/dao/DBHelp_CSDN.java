package com.example.administrator.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2015/11/13.
 */
public class DBHelp_CSDN extends SQLiteOpenHelper {
    private static final String DB_NAME="csdn_app";

    public DBHelp_CSDN(Context context){
        super(context,DB_NAME,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
     String sql_csdn="create table csdn_tb_newsItem(_id integer primary key autoincrement,"
        +" title text ,content text ,link text ,date text , imgLink text , newsType integer )";
        db.execSQL(sql_csdn);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         if (oldVersion!=newVersion){
             String sql="drop table csdn_tb_newsItem if exit";
             db.execSQL(sql);
         }
        onCreate(db);
    }
}
