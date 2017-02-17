package com.deonna.todone.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class TodoSQLiteHelper extends SQLiteOpenHelper {

    private static TodoSQLiteHelper sInstance;

    // Database Info
    private static final String DATABASE_NAME = "todos.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    public static final String TABLE_TODOS = "todos";

    // Todos Table Columns
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_COMPLETED = "completed";
    public static final String COLUMN_PRIORITY = "priority";
    public static final String COLUMN_DUE_DATE = "due_date";

    private TodoSQLiteHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized TodoSQLiteHelper getInstance(Context context) {

        if(sInstance == null) {
            sInstance = new TodoSQLiteHelper(context.getApplicationContext());
        }

        return sInstance;
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {

        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TODOS_TABLE = String.format(
                "CREATE TABLE %s (%s INTEGER PRIMARY KEY " + "AUTOINCREMENT, " +
                        "%s TEXT, %s INTEGER, %s INTEGER, %s TEXT)",
                TABLE_TODOS, BaseColumns._ID,
                COLUMN_NAME, COLUMN_COMPLETED, COLUMN_PRIORITY, COLUMN_DUE_DATE);

        db.execSQL(CREATE_TODOS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODOS);
            onCreate(db);
        }
    }
}
