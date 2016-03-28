package com.example.ghost.task4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by ghost on 10/03/2016.
 */
public class DBController extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="Task4.db";
    public static final String TABLE_EXP="expense";
    public static final String TABLE_INC ="income";
    public static final String COL1_EXP="ID";
    public static final String COL2_EXP="DESC_EXP";
    public static final String COL3_EXP="AMT_EXP";
    public static final String COL4_EXP="TOT_EXP";
    public static final String COL1_INC="ID";
    public static final String COL2_INC="DESC_INC";
    public static final String COL3_INC="AMT_INC";
    public static final String COL4_INC="TOT_INC";


    public static final String TABLE_CREATE_EXP ="CREATE TABLE "+TABLE_EXP+"("
                                +COL1_EXP+" INTEGER PRIMARY KEY  AUTOINCREMENT ,"

                                +COL2_EXP+" TEXT,"

                                +COL3_EXP+" TEXT );";


    public  static final  String TABLE_CREATE_INC = "CREATE TABLE "+TABLE_INC+" ( "
                                +COL1_INC+ " INTEGER PRIMARY KEY AUTOINCREMENT,"

                                +COL2_INC+ " TEXT, "

                                +COL3_INC+ " TEXT );";


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


   public HashMap<String, String>totalInc(){
       HashMap<String, String> LtotInc;
       LtotInc = new HashMap<String, String>();
       String selectQuery = "SELECT sum (" + COL3_INC + ") FROM " + TABLE_INC;
       SQLiteDatabase data = this.getWritableDatabase();
       Cursor cursor = data.rawQuery(selectQuery, null);
       int tot_inc;
       if(cursor.moveToFirst()) {
           tot_inc = cursor.getInt(0);
           LtotInc.put("total_inc", String.valueOf(tot_inc));
       }
       else {
           tot_inc = 0;
           LtotInc.put("total_inc", String.valueOf(tot_inc));
       }

       cursor.close();

        return LtotInc;

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

    public HashMap<String,Integer>balance(){
        HashMap<String, Integer> LBal;
        LBal = new HashMap<String, Integer>();
        String selectQuery = "SELECT sum(AMT_INC) -  sum(AMT_EXP ) FROM income,expense  ";
        SQLiteDatabase data = this.getWritableDatabase();
        Cursor cursor = data.rawQuery(selectQuery, null);
        int bal;
        if(cursor.moveToFirst()) {
            bal = cursor.getInt(0);
            LBal.put("balance", bal);
        }
        else {
            bal = 0;
            LBal.put("balance", bal);
        }

        cursor.close();

        return LBal;
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


    public Cursor list_expenses() {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor expense = db.rawQuery("SELECT "+COL1_EXP+" as id, "+COL2_EXP+" , "+COL3_EXP+" FROM " + TABLE_EXP, null);
        return expense;


    }

    public Cursor list_income() {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor income = db.rawQuery("SELECT "+COL1_INC+" as id, "+COL2_INC+" , "+COL3_INC+" FROM " + TABLE_INC, null);

        return income;

    }
}
