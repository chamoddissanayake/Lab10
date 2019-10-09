package com.test.lab10;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "School.db";

    private static final String SQL_CREATE_USER_ENTRIES =
            "CREATE TABLE " + ReaderContract.UserEntry.TABLE_NAME_USER + " (" +
                    ReaderContract.UserEntry._ID+ " INTEGER PRIMARY KEY," +
                    ReaderContract.UserEntry.COLUMN_NAME_NAME + " TEXT," +
                    ReaderContract.UserEntry.COLUMN_NAME_PASSWORD + " TEXT," +
                    ReaderContract.UserEntry.COLUMN_NAME_TYPE + " TEXT)";

    private static final String SQL_CREATE_MESSAGE_ENTRIES =
            "CREATE TABLE " + ReaderContract.MessageEntry.TABLE_NAME_MESSAGE + " (" +
                    ReaderContract.MessageEntry._ID+ " INTEGER PRIMARY KEY," +
                    ReaderContract.MessageEntry.COLUMN_NAME_USER + " TEXT," +
                    ReaderContract.MessageEntry.COLUMN_NAME_SUBJECT + " TEXT," +
                    ReaderContract.MessageEntry.COLUMN_NAME_MESSAGE + " TEXT)";

    private static final String SQL_DELETE_USER_ENTRIES =
            "DROP TABLE IF EXISTS " + ReaderContract.UserEntry.TABLE_NAME_USER;

    private static final String SQL_DELETE_MESSAGE_ENTRIES =
            "DROP TABLE IF EXISTS " + ReaderContract.MessageEntry.TABLE_NAME_MESSAGE;

    public DbHelper( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_USER_ENTRIES);
        sqLiteDatabase.execSQL(SQL_CREATE_MESSAGE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_USER_ENTRIES);
        sqLiteDatabase.execSQL(SQL_DELETE_MESSAGE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
