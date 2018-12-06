package com.casasw.iddog.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DogDbHelper extends SQLiteOpenHelper {
    private static final String TAG = DogDbHelper.class.getSimpleName();
    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "dog.db";
    
    public DogDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_DOG_TABLE = "CREATE TABLE " + DogContract.DogEntry.TABLE_NAME + " (" +
                DogContract.DogEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DogContract.DogEntry.COLUMN_DOG_BREED + " TEXT NOT NULL, " +
                DogContract.DogEntry.COLUMN_DOG_IMAGE_URL + " TEXT NOT NULL, "+
                " UNIQUE (" + DogContract.DogEntry.COLUMN_DOG_IMAGE_URL + ") ON CONFLICT REPLACE);";
        
        sqLiteDatabase.execSQL(SQL_CREATE_DOG_TABLE);


        
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DogContract.DogEntry.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }
}
