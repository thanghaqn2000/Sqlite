package com.example.testsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLite";
    // Database Version
    private static final double DATABASE_VERSION = 5.3;

    // Database Name
    private static final String DATABASE_NAME = "test";

    // Table name: Note.
    private static final String TABLE_NOTE = "Table1";

    private static final String COLUMN_USER_ID ="Userid";
    private static final String COLUMN_PASSWORD ="password";

    public MyDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        //super(context, name, factory, version);
        super(context, DATABASE_NAME, null, (int) DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "MyDatabaseHelper.onCreate ... ");
        // Script.
        String script = "CREATE TABLE " + TABLE_NOTE + "("
                + COLUMN_USER_ID + " TEXT PRIMARY KEY," + COLUMN_PASSWORD + " TEXT"+")";
        // Execute Script.
        db.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public void createDefaultNotesIfNeed()  {
        int count = this.getNotesCount();
        if(count ==0 ) {
            Table1 note1 = new Table1("Firstly see Android ListView",
                    "See Android ListView Example in o7planning.org");
            Table1 note2 = new Table1("Learning Android SQLite",
                    "See Android SQLite Example in o7planning.org");
            this.addNote(note1);
            this.addNote(note2);
        }
    }
    public void addNote(Table1 note) {
        //Log.i(TAG, "MyDatabaseHelper.addNote ... " + note.getNoteTitle());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, note.getUserid());
        values.put(COLUMN_PASSWORD, note.getPassword());
        // Inserting Row
        db.insert(TABLE_NOTE, null, values);

        // Closing database connection
        db.close();
    }
    public Table1 getNote(int id) {
        Log.i(TAG, "MyDatabaseHelper.getNote ... " + id);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NOTE, new String[] { COLUMN_USER_ID,
                        COLUMN_PASSWORD }, COLUMN_USER_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Table1 note = new Table1((cursor.getString(0)),
                cursor.getString(1));
        // return note
        return note;
    }


    public List<Table1> getAllNotes() {
        Log.i(TAG, "MyDatabaseHelper.getAllNotes ... " );

        List<Table1> noteList = new ArrayList<Table1>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NOTE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Table1 note = new Table1();
                note.setUserid((cursor.getString(0)));
                note.setPassword(cursor.getString(1));
                // Adding note to list
                noteList.add(note);
            } while (cursor.moveToNext());
        }

        // return note list
        return noteList;
    }

    public int getNotesCount() {
        Log.i(TAG, "MyDatabaseHelper.getNotesCount ... " );
        String countQuery = "SELECT  * FROM " + TABLE_NOTE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }
    public int updateNote(Table1 note) {
        //Log.i(TAG, "MyDatabaseHelper.updateNote ... "  + note.getNoteTitle());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, note.getUserid());
        values.put(COLUMN_PASSWORD, note.getPassword());
        // updating row
        return db.update(TABLE_NOTE, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(note.getUserid())});
    }
    public void deleteNote(Table1 note) {
        //Log.i(TAG, "MyDatabaseHelper.updateNote ... " + note.getNoteTitle() );

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTE, COLUMN_USER_ID + " = ?",
                new String[] { String.valueOf(note.getUserid()) });
        db.close();
    }

}

