package com.deonna.todone;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class TodoSQLiteHelper extends SQLiteOpenHelper {

    private static TodoSQLiteHelper sInstance;

    // Database Info
    private static final String DATABASE_NAME = "todosDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_TODOS = "todos";

    // Todos Table Columns
    private static final String COLUMN_NAME = "name";

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

        String CREATE_TODOS_TABLE = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY " +
                "AUTOINCREMENT, %s TEXT)",
                TABLE_TODOS,
                BaseColumns._ID,
                COLUMN_NAME);

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
