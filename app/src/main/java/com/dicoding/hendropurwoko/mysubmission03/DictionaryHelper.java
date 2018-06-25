package com.dicoding.hendropurwoko.mysubmission03;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.dicoding.hendropurwoko.mysubmission03.DatabaseHelper.ENGLISH;
import static com.dicoding.hendropurwoko.mysubmission03.DatabaseHelper.INDONESIA;

public class DictionaryHelper {
    //private static String DATABASE_TABLE = TABLE_NOTE;
    private Context context;
    private DatabaseHelper dataBaseHelper;
    private SQLiteDatabase database;

    public DictionaryHelper(Context context) {
        this.context = context;
    }

    public DictionaryHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dataBaseHelper.close();
    }

    public ArrayList<DictionaryModel> query(boolean isEng){
        String TABLE = isEng ? ENGLISH : INDONESIA;
        ArrayList<DictionaryModel> dictionaryModels = new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE + " ORDER BY " + DatabaseHelper.FIELD_ID + " ASC", null);
        cursor.moveToFirst();

        DictionaryModel dictionaryModel;

        if (cursor.getCount()>0) {
            do {
                dictionaryModel = new DictionaryModel();
                dictionaryModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                dictionaryModel.setWord(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.FIELD_WORD)));
                dictionaryModel.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.FIELD_DESCRIPTION)));

                dictionaryModels.add(dictionaryModel);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return dictionaryModels;
    }

    public void beginTransaction(){
        database.beginTransaction();
    }

    public void setTransactionSuccess(){
        database.setTransactionSuccessful();
    }

    public void endTransaction(){
        database.endTransaction();
    }

    public void insertTransaction(ArrayList<DictionaryModel> dictionaryModels, boolean isEng){

        String TABLE = isEng ? ENGLISH : INDONESIA;
        String sql = "INSERT INTO "+ TABLE +" ("+ DatabaseHelper.FIELD_WORD +", "+ DatabaseHelper.FIELD_DESCRIPTION +") VALUES (?, ?)";

        //open();
        //beginTransaction();
        SQLiteStatement stmt = database.compileStatement(sql);

        for (int i = 0; i < dictionaryModels.size(); i++) {
            stmt.bindString(1, dictionaryModels.get(i).getWord());
            stmt.bindString(2, dictionaryModels.get(i).getDescription());
            stmt.execute();
            stmt.clearBindings();
        }

        //setTransactionSuccess();
        //endTransaction();
        //close();
    }
}
