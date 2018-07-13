package com.example.nandini.mini;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHandler extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "photoHider.db";

//    private static final String TABLE_USER = "user";
//
//    private static final String KEY_NAME = "name";
//    private static final String KEY_ID = "email";
//    private static final String KEY_PWD = "pwd";


    DataBaseHandler(Context context)
    {
        super(context,DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
//        String table1="CREATE TABLE "+ TABLE_USER +"(" +KEY_NAME+ " TEXT ," + KEY_ID + "TEXT," + KEY_PWD + "TEXT )";
//        db.execSQL(table1);
        String CREATE_CONTACTS_TABLE = "CREATE TABLE user (name TEXT,email TEXT,pwd TEXT);";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
        db.execSQL("DROP TABLE IF EXISTS user" );
        // create new table
        onCreate(db);
    }
}
