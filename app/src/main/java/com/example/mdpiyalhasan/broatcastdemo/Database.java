package com.example.mdpiyalhasan.broatcastdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by piyal on 2/6/2018.
 */
public class Database extends SQLiteOpenHelper {
    private static String DATABASE_NAME="sms_database";
    private static String DATABASE_TABLE="smstable";
    private static String KEY="SMS_ID";
    private static String SMSTEXT="smstext";
    private static String SMS_TIME="smstime";
    private static String SMS_DATE="sms_date";
    private static String PH_NUMBER="phonenumber";
    private static int VERSION=1;
    public Database(Context context) {
        super(context,DATABASE_NAME,null,VERSION);
    }
    private static String createtable="CREATE TABLE " +
            ""+DATABASE_TABLE+" ("
            +KEY+" INTEGER PRIMARY KEY," +
            ""+SMSTEXT+" TEXT," +
            ""+SMS_TIME+" TEXT," +
            ""+SMS_DATE+" TEXT," +
            ""+ PH_NUMBER+" TEXT" +")";
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
       sqLiteDatabase.execSQL(createtable);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + createtable);
    }
    public long addItem(SmsModel smsModel){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(SMSTEXT,smsModel.getSms());
        values.put(SMS_TIME,smsModel.getTime());
        values.put(SMS_DATE,smsModel.getDate());
        values.put(PH_NUMBER,smsModel.getPhone_number());
        return db.update(DATABASE_TABLE, values, ""+KEY+" = ?",new String[] {String.valueOf(smsModel.getKey())});
    }
    public List<SmsModel> getlist(){
        List<SmsModel> list=new ArrayList<>();
        String sql="Select * from "+DATABASE_TABLE+"";
        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                SmsModel sms=new SmsModel();
                sms.setKey(cursor.getInt(0));
                sms.setSms(cursor.getString(1));
                sms.setPhone_number(cursor.getString(4));
                list.add(sms);
            }while(cursor.moveToNext());
        }
        return list;
    }
}
