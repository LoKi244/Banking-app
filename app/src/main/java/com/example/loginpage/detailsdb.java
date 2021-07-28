package com.example.loginpage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import static android.widget.Toast.makeText;

public class detailsdb extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Banking.db";
    public static final String DETAILS_TABLE = "details";
    public static final String NAME = "Name";
    public static final String USERNAME = "UserName";
    public static final String MOBILE_NUMBER = "Mobile_Number";
    public static final String PASSWORD = "Password";
    public static final String EMAIL = "Email";

    public detailsdb(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ DETAILS_TABLE +"("+NAME+" TEXT,"+USERNAME+" TEXT,"+MOBILE_NUMBER+" INTEGER,"+PASSWORD+" TEXT,"+EMAIL+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+DETAILS_TABLE);
    }

    public boolean insertdata(String name,String user,String number,String password,String mail){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NAME,name);
        cv.put(USERNAME,user);
        cv.put(MOBILE_NUMBER,number);
        cv.put(PASSWORD,password);
        cv.put(EMAIL,mail);
        long result = db.insert(DETAILS_TABLE,null,cv);
        if(result == -1){
            return false;
        }
        else
            return true;
    }

    public boolean verify_register(String user){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+DETAILS_TABLE+" where UserName = ?",new String[]{user});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean verify(String user,String pass){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+DETAILS_TABLE+" where UserName = ? and Password = ?",new String[]{user,pass});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean verify_forgot(String user,String mail){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+DETAILS_TABLE+" where UserName = ? and Email = ?",new String[]{user,mail});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean update(String user,String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PASSWORD,pass);
        int result = db.update(DETAILS_TABLE,cv,"UserName = ?",new String[]{user});
        if(result == 1){
            return true;
        }
        else
            return false;
    }
}
