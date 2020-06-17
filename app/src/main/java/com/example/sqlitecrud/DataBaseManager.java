package com.example.sqlitecrud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DataBaseManager extends SQLiteOpenHelper {

   private static final String DATABSE_NAME = "EmployeeDB";
   private static  final int DATABSAE_VERSION = 1;
   private static final String TABLE_NAME = "employee";
   private static final String COLUMN_ID = "id";
   private static final String COLUMN_NAME = "name";
   private static final String COLUMN_DAPARTMENT = "department";
   private static final String COLUMN_JOINED_DATE = "joiningdate";
   private static final String COLUMN_SALARY    = "salary";

    public DataBaseManager(Context context) {
        super(context, DATABSE_NAME, null, DATABSAE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS employee  (\n" +
                "    COLUMN_ID INTEGER NOT NULL CONSTRAINT employees_pk PRIMARY KEY AUTOINCREMENT,\n" +
                "    COLUMN_NAME varchar(200) NOT NULL,\n" +
                "    COLUMN_DEPARTMENT varchar(200) NOT NULL,\n" +
                "    COLUMN_JOINED_DATE datetime NOT NULL,\n" +
                "    COLUMN_SALARY DOUBLE NOT NULL\n" +
                ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    boolean AddEmployee(String Name,String Department,Double Salary,String joinDate ) {

        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, Name);
        values.put(COLUMN_DAPARTMENT, Department);
        values.put(COLUMN_SALARY, String.valueOf(Salary));
        values.put(COLUMN_JOINED_DATE, joinDate);

        return database.insert(TABLE_NAME, null, values) != -1;
    }
     Cursor GetAllEmployees(){
         SQLiteDatabase database = getWritableDatabase();
         return  database.rawQuery("SELECT * FROM " + TABLE_NAME,null);
     }

     boolean UpdateEmployee(String Name,Double Salary,String Department,int id){
         SQLiteDatabase database = getWritableDatabase();
         ContentValues values = new ContentValues();
         values.put(COLUMN_NAME, Name);
         values.put(COLUMN_DAPARTMENT, Department);
         values.put(COLUMN_SALARY, String.valueOf(Salary));
         return database.update(TABLE_NAME,values,COLUMN_ID + "= ?",new  String[]{String.valueOf(id)}) > 0;
     }
     boolean DeleteEmployee(int id){
         SQLiteDatabase database = getWritableDatabase();

         return database.delete(TABLE_NAME,COLUMN_ID + "= ?",new String[]{String.valueOf(id)}) > 0;
     }
}
