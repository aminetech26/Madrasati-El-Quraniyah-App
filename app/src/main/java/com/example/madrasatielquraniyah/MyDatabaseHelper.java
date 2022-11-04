package com.example.madrasatielquraniyah;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;

class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "StudentList.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_students";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_FNAME = "student_fistname";
    private static final String COLUMN_LNAME = "student_lastname";
    private static final String COLUMN_EMAIL = "student_email";

    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FNAME + " TEXT, " +
                COLUMN_LNAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT);";
        db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addStudent(String firstname, String lastname, String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_FNAME, firstname);
        cv.put(COLUMN_LNAME, lastname);
        cv.put(COLUMN_EMAIL, email);
        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            Toast.makeText(context, "لم يتم حفظ المعلومات", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "تمت الإضافة بنجاح", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateData(String row_id, String firstname, String lastname, String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FNAME, firstname);
        cv.put(COLUMN_LNAME, lastname);
        cv.put(COLUMN_EMAIL, email);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "لم يتم التحديث", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "تم التحديث بنجاح", Toast.LENGTH_SHORT).show();
        }

    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "لم يتم الحذف", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "تم الحذف بنجاح", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

}

