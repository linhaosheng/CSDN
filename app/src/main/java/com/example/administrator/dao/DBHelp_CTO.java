package com.example.administrator.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by linhao on 2016/1/15.
 */
public class DBHelp_CTO extends SQLiteOpenHelper {
    private static final String DB_NAME="51cto_app";

    public DBHelp_CTO(Context context){
        super(context,DB_NAME,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_51cto="create table cto_tb_newsItem(_id integer primary key autoincrement,"
                +" title text ,content text ,link text ,date text , imgLink text , newsType integer )";
        db.execSQL(sql_51cto);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion!=newVersion){
            String sql="drop table cto_tb_newsItem if exit";
            db.execSQL(sql);
        }
        onCreate(db);
    }
}
