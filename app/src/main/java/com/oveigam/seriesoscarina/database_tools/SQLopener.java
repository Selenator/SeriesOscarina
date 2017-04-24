package com.oveigam.seriesoscarina.database_tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Oscarina on 18/11/2016.
 */

public class SQLopener extends SQLiteOpenHelper {
    public final static String NOME_BD = "series_oscar.db";
    public final static int VERSION_BD = 1;


    public SQLopener(Context context) {
        super(context, NOME_BD, null, VERSION_BD);
        // TODO Auto-generated constructor stub
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub


    }
}