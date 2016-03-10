package com.example.ghost.task4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ghost on 10/03/2016.
 */
public class DBController extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="Task4.db";
    public static final String TABLE_EXP="expense";
    public static final String TABLE_INC ="income";
    public static final String COL1_EXP="ID_EXP";
    public static final String COL2_EXP="DESC_EXP";
    public static final String COL3_EXP="AMT_EXP";
    public static final String COL4_EXP="TOT_EXP";
    public static final String COL1_INC="ID_INC";
    public static final String COL2_INC="DESC_INC";
    public static final String COL3_INC="AMT_INC";
    public static final String COL4_INC="TOT_INC";


    public static final String TABLE_CREATE_EXP ="CREATE TABLE "+TABLE_EXP+"("
                                +COL1_EXP+" INTEGER PRIMARY KEY  AUTOINCREMENT ,"

                                +COL2_EXP+" TEXT,"

                                +COL3_EXP+" INTEGER, "

                                +COL4_EXP+" INTEGER );";


    public  static final  String TABLE_CREATE_INC = "CREATE TABLE "+TABLE_INC+" ( "
                                +COL1_INC+ " INTEGER PRIMARY KEY AUTOINCREMENT,"

                                +COL2_INC+ " TEXT, "

                                +COL3_INC+ " INTEGER "

                                +COL4_INC+" INTEGER );";;


    public DBController(Context context){

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_EXP);
       db.execSQL(TABLE_CREATE_INC);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_EXP);
       db.execSQL("DROP TABLE IF EXISTS" + TABLE_INC);
        onCreate(db);

    }

    public boolean save_expense(String desc_exp, int amt_exp){


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues content_values_exp = new ContentValues();


        content_values_exp.put(COL2_EXP, desc_exp);
        content_values_exp.put(COL3_EXP, amt_exp);




        long result_exp = db.insert(TABLE_EXP,null,content_values_exp);

        return result_exp !=-1;

    }


   public HashMap<String, String>totalEXP(){
       HashMap<String, String> Ltot;
       Ltot = new HashMap<String, String>();
       String selectQuery = "SELECT sum (" + COL3_EXP + ") FROM " + TABLE_EXP;
       SQLiteDatabase data = this.getWritableDatabase();
       Cursor cursor = data.rawQuery(selectQuery, null);
       int tot_exp;
       if(cursor.moveToFirst()) {
           tot_exp = cursor.getInt(0);
           Ltot.put("total_exp", String.valueOf(tot_exp));
       }
       else {
           tot_exp = 0;
           Ltot.put("total_exp", String.valueOf(tot_exp));
       }

       cursor.close();

        return Ltot;

   }


    public boolean save_income(String desc_inc, Integer amt_inc){

        SQLiteDatabase db=this.getWritableDatabase();

        int tot_inc=0;

        ContentValues content_values_inc=new ContentValues();
        content_values_inc.put(COL2_INC, desc_inc);
        content_values_inc.put(COL3_INC,amt_inc);
        //content_values_inc.put(COL4_INC,tot_inc);

        long result_inc = db.insert(TABLE_INC, null, content_values_inc);

        return result_inc !=-1;

    }

    public ArrayList<HashMap<String, String>> getAllUsersEXP() {
        ArrayList<HashMap<String, String>> wordListExp;
        wordListExp = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM "+TABLE_EXP;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("DESC_EXP", cursor.getString(1));
                map.put("AMT_EXP", cursor.getString(2));
                wordListExp.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return wordListExp;
    }

    public ArrayList<HashMap<String, String>> getAllUsersInc() {
        ArrayList<HashMap<String, String>> wordListInc;
        wordListInc = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM "+TABLE_INC;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("DESC_INC", cursor.getString(1));
                map.put("AMT_INC", cursor.getString(2));
                wordListInc.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return wordListInc;
    }



}
