package com.example.myproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    private static final String TAG = "Database";

    public Database(Context context) {
        super(context, "HealthcareDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS users(username TEXT PRIMARY KEY, email TEXT, password TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS cart(username TEXT, product TEXT, price FLOAT, otype TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS orderplace(username TEXT, fullname TEXT, address TEXT, contactno TEXT, pincode INTEGER, date TEXT, time TEXT, amount FLOAT, otype TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS reminders(id integer PRIMARY KEY autoincrement, medicine text, time INTEGER, repeat_intervals INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS cart");
        db.execSQL("DROP TABLE IF EXISTS orderplace");
        db.execSQL("DROP TABLE IF EXISTS reminders");
        onCreate(db);
    }

    public void register(String username, String email, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("email", email);
        cv.put("password", password);

        long result = db.insert("users", null, cv);
        if (result == -1) {
            Log.e(TAG, "Registration failed for: " + username);
        } else {
            Log.d(TAG, "User registered successfully: " + username);
        }
        db.close();
    }

    public int login(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username=? AND password=?", new String[]{username, password});

        int result = (cursor.getCount() > 0) ? 1 : 0;

        cursor.close();
        db.close();
        return result;
    }

    public void addCart(String username, String product, float price, String otype){
        ContentValues cv = new ContentValues();
        cv.put("Username",username);
        cv.put("Product",product);
        cv.put("Price",price);
        cv.put("otype",otype);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("cart",null,cv);
        db.close();
    }
    public int checkCart(String username, String product){
        int result = 0;
        String str[] = new String[2];
        str[0]= username;
        str[1]= product;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from cart where username = ? and product=?",str);

        if (c.moveToFirst()){
            result = 1;
        }
        db.close();
        return result;
    }
    public void removeCart(String username, String otype){
        String str[] = new String[2];
        str[0]= username;
        str[1]= otype;
        SQLiteDatabase db = getWritableDatabase();
        db.delete("cart","username = ? and otype = ?",str);
        db.close();
    }

    public ArrayList getCartData(String username, String otype){
        ArrayList<String> arr = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String str[] = new String[2];
        str[0]= username;
        str[1]= otype;
        Cursor c= db.rawQuery("select * from cart where username = ? and otype = ?",str);
        if (c.moveToFirst()){
            do {
                String product = c.getString(1);
                String price = c.getString(2);
                arr.add(product+"$"+price);
            }while (c.moveToNext());
        }
        db.close();
        return arr;
    }

    public int checkAppintmentExists(String username,String fullname, String address ,String contact ,String date, String time){
        int result = 0;
        String str[] = new String[6];
        str[0]= username;
        str[1]= fullname;
        str[2]= address;
        str[3]= contact;
        str[4]= date;
        str[5]= time;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from orderplace where username = ? and fullname=? and address = ? and contactno = ? and date = ? and time = ?",str);
        if (c.moveToFirst()){
            result =1;
        }
        db.close();
        return result;
    }

    public void addOrder(String username, String fullname, String address ,String contact ,int pincode,String date, String time , float price, String otype) {
        ContentValues cv = new ContentValues();
        cv.put("Username", username);
        cv.put("fullname", fullname);
        cv.put("address", address);
        cv.put("contactno", contact);
        cv.put("pincode", pincode);
        cv.put("date", date);
        cv.put("time", time);
        cv.put("amount", price);
        cv.put("otype", otype);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("orderplace",null,cv);
        db.close();
    }

    public ArrayList getOrderData(String username){
        ArrayList<String> arr = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String str[] = new String[1];
        str[0]= username;
        Cursor c= db.rawQuery("select * from orderplace where username = ?",str);
        if (c.moveToFirst()){
            do {
                arr.add(c.getString(1)+"$"+c.getString(2)+"$"+c.getString(3)+"$"+c.getString(4)
                        +"$"+c.getString(5)+"$"+c.getString(6)+"$"+c.getString(7)+"$"+c.getString(8)+"$");
            }while (c.moveToNext());
        }
        db.close();
        return arr;
    }

    public long insertReminder(String medicine, long timeMillis, long repeatIntervalMillis) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("medicine", medicine);
        values.put("time", timeMillis);
        values.put("repeat_intervals", repeatIntervalMillis);
        return db.insert("reminders", null, values);
    }
}
