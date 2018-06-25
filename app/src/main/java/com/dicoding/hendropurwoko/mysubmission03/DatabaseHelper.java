package com.dicoding.hendropurwoko.mysubmission03;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;

/**
 * Created by Hendro on 3/13/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper  {
    public final static String DATABASE_NAME = "dbDictionary.db";
    private final static int DATABASE_VERSION = 1;

    public static String ENGLISH = "english";
    public static String INDONESIA = "indonesia";

    public final static String FIELD_ID = "_id";
    public final static String FIELD_WORD = "word";
    public final static String FIELD_DESCRIPTION = "description";

    private final static String SQL_CREATE_TABLE_ENGlISH = "create table " + ENGLISH + " (_id INTEGER PRIMARY KEY, " +
                                    FIELD_WORD + " TEXT, " +
                                    FIELD_DESCRIPTION + " TEXT )";

    private final static String SQL_CREATE_TABLE_INDONESIA = "create table " + INDONESIA + " (_id INTEGER PRIMARY KEY, " +
            FIELD_WORD + " TEXT, " +
            FIELD_DESCRIPTION + " TEXT )";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_ENGlISH);
        db.execSQL(SQL_CREATE_TABLE_INDONESIA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ENGLISH);
        db.execSQL("DROP TABLE IF EXISTS " + INDONESIA);
        onCreate(db);
    }
}