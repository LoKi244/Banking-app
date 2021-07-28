package com.example.loginpage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class transaction extends SQLiteOpenHelper {
    public String username = Register.newuser();

    public transaction(@Nullable Context context) {
        super(context,"transactions.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //username = Register.newuser();
        db.execSQL("Create table "+username+"(ID INTEGER Primary Key Autoincrement,Date TEXT,Deposit INTEGER,Withdraw INTEGER,Balance INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //username = Register.newuser();
        db.execSQL("Drop table if exists "+username);
    }

    public boolean inserttransaction(String date,String deposit,String withdraw,String username){
        SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL("Create table "+username+"(Date TEXT,Deposit INTEGER,Withdraw INTEGER,Balance INTEGER)");
        ContentValues cv = new ContentValues();
        cv.put("Date",date);
        cv.put("Deposit",deposit);
        cv.put("Withdraw",withdraw);
        if(deposit.equals("") && withdraw.equals("")){
            cv.put("Balance",0);
        }
        else if(deposit.equals("")){
            SQLiteDatabase dbb = this.getReadableDatabase();
            Cursor resultSet = dbb.rawQuery("Select * from "+username+" where ID = (Select max(ID) from "+username+")",null);
            resultSet.moveToFirst();
            int bal = resultSet.getInt(4);
            int with = Integer.parseInt(withdraw);
            if(with>bal)
                return false;
            bal = bal-with;
            cv.put("Balance",bal);
        }
        else{
            SQLiteDatabase dbb = this.getReadableDatabase();
            Cursor resultSet = dbb.rawQuery("Select * from "+username+" where ID = (Select max(ID) from "+username+")",null);
            resultSet.moveToFirst();
            int bal = resultSet.getInt(4);
            int depo = Integer.parseInt(deposit);
            bal = bal+depo;
            cv.put("Balance",bal);
        }
        long result = db.insert(username,null,cv);
        if(result == -1){
            return false;
        }
        else
            return true;
    }

    public void createuser(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Create table "+username+"(ID INTEGER Primary Key Autoincrement,Date TEXT,Deposit INTEGER,Withdraw INTEGER,Balance INTEGER)");
    }

    public int checkbalance(String username){
        SQLiteDatabase dbb = this.getReadableDatabase();
        Cursor resultSet = dbb.rawQuery("Select * from "+username+" where ID = (Select max(ID) from "+username+")",null);
        resultSet.moveToFirst();
        int bal = resultSet.getInt(4);
        return bal;
    }

    public Cursor getdata(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("Select * from "+username,null);
        return result;
    }

}
