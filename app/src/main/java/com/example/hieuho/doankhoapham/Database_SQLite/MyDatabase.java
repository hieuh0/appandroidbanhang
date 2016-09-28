package com.example.hieuho.doankhoapham.Database_SQLite;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.hieuho.doankhoapham.Helper.Product_Handler;
import com.example.hieuho.doankhoapham.Helper.SanPham;
import com.example.hieuho.doankhoapham.Helper.TaiKhoan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by HieuHo on 7/25/2016.
 */
public class MyDatabase extends SQLiteOpenHelper {
    private static final String TAG = MyDatabase.class.getSimpleName();
    private static final String TABLE_USER = "users";
    private static final String TABLE_TAM = "tam";
    private static final String KEY_ID = "ID";
    private static final String KEY_NAME = "HoTen";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_PASS= "MatKhau";
    private static final String KEY_Hinh = "Hinh";
    public MyDatabase(Context context) {
        super(context,"khoaphamandroid.sqlite", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlUser="CREATE TABLE "+ TABLE_USER + "("
                +KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                +KEY_EMAIL + " TEXT,"
                +KEY_PASS  + " TEXT"
                + ")";
        db.execSQL(sqlUser);
                                                               //AUTOINCREMENT
        String sqlSP = "CREATE TABLE sanpham (id INTEGER PRIMARY KEY AUTOINCREMENT,hinh BLOB,ten TEXT,gia INTEGER,mota TEXT,danhmuc INTEGER,sodt INTEGER,ngay DATETIME DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(sqlSP);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS sanpham" + TABLE_USER);
        onCreate(db);
    }
    public void CreateAcc(TaiKhoan tk){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_EMAIL, tk.email);
        values.put(KEY_PASS, tk.matkhau);
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public ArrayList<TaiKhoan> SelectUser(){
        ArrayList<TaiKhoan> list=new ArrayList<TaiKhoan>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM "+TABLE_USER,null);
       if (c.moveToFirst())
        {
            do{
                int id=c.getInt(0);
                String mail=c.getString(1);
                String mk=c.getString(2);
                list.add(new TaiKhoan(id, mail,mk));
            }while(c.moveToNext());
        }
        return list;
    }
    public void DeleteUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, null, null);
        db.close();
    }
//byte[] hinh,String ten,String gia,String mota,int danhmuc,String so
    public void CreateSP(Product_Handler p){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("hinh", p.Hinh);
        values.put("ten", p.Ten);
        values.put("gia", p.Gia);
        values.put("mota", p.MoTa);
        values.put("danhmuc", p.DanhMuc);
        values.put("sodt",p.SoDT);
        values.put("ngay",getDateTime());
        db.insert("sanpham", null, values);
        db.close();
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
    public ArrayList<SanPham> SelectSP(){
        ArrayList<SanPham> list=new ArrayList<SanPham>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM sanpham",null);
        if (c.moveToFirst())
        {
            do{
                int id = c.getInt(0);
                byte[] h = c.getBlob(1);

                String tieude=c.getString(2);
                String gia = c.getString(3);
                String mota=c.getString(4);
                int dm = c.getInt(5);
                String dt = c.getString(6);
                String ngay = c.getString(7);
                list.add(new SanPham(id,tieude,mota,gia,dt,ngay,dm,h));
            }while(c.moveToNext());
        }
        return list;
    }

}
