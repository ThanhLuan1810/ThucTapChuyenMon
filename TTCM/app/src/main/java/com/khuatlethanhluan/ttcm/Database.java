package com.khuatlethanhluan.ttcm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.khuatlethanhluan.ttcm.Model.PhongTro;

public class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="qlphongtro.db";

    public static final String TABLE_NAME1="PHONGTRO";
    public static final String COL_1="ID";
    public static final String COL_2="TEN_PHONG";
    public static final String COL_3="SO_NGUOI";
    public static final String COL_4="DIEN_TICH";
    public static final String COL_5="GIA";
    public static final String COL_6="THONG_TIN_KHAC";
    public static final String COL_7="SO_DIEN";
    public static final String COL_8="SO_NUOC";

    public static final String TABLE_NAME2="DICHVU";
    public static final String TB2_COL_1="MA_DV";
    public static final String TB2_COL_2="TEN_DV";
    public static final String TB2_COL_3="CACH_TINH";
    public static final String TB2_COL_4="DON_GIA";

    public static final String TABLE_NAME3="KHACH";
    public static final String TB3_COL_1="ID";
    public static final String TB3_COL_2="TEN_KH";
    public static final String TB3_COL_3="GIOI_TINH";
    public static final String TB3_COL_4="NAM_SINH";
    public static final String TB3_COL_5="CMND";
    public static final String TB3_COL_6="NGAY_CAP";
    public static final String TB3_COL_7="SDT";
    public static final String TB3_COL_8="PHONG_O";
    public static final String TB3_COL_9="HINH_ANH";


    public static final String TABLE_NAME5="HOADON";
    public static final String TB5_COL_1="ID";
    public static final String TB5_COL_2="THANG";
    public static final String TB5_COL_3="PHONG";
    public static final String TB5_COL_4="SO_DIEN";
    public static final String TB5_COL_5="SO_NUOC";
    public static final String TB5_COL_6="CHI_PHI_KHAC";
    public static final String TB5_COL_7="THANH_TIEN";
    public static final String TB5_COL_8="NGAY_LAP";
    public static final String TB5_COL_9="TINH_TRANG";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME1+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,TEN_PHONG TEXT,SO_NGUOI TEXT,DIEN_TICH TEXT,GIA INTEGER,THONG_TIN_KHAC TEXT,SO_DIEN INTEGER,SO_NUOC INTEGER)");
        db.execSQL("create table "+TABLE_NAME2+" (MA_DV INTEGER PRIMARY KEY AUTOINCREMENT,TEN_DV TEXT,CACH_TINH TEXT,DON_GIA INTEGER)");
        db.execSQL("create table "+TABLE_NAME3+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,TEN_KH TEXT,GIOI_TINH TEXT,NAM_SINH TEXT,CMND TEXT,NGAY_CAP DATE,SDT TEXT,PHONG_O INTEGER,HINH_ANH TEXT)");
        db.execSQL("create table "+TABLE_NAME5+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,THANG INTERGER,PHONG TEXT,SO_DIEN INTEGER,SO_NUOC INTEGER,CHI_PHI_KHAC INTEGER,THANH_TIEN INTEGER,NGAY_LAP DATE,TINH_TRANG TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME3);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME5);
        onCreate(db);
    }

    public boolean themPhongTro(String ten,String sng,String dt,String g,String ttk,String sd,String sn){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,ten);
        contentValues.put(COL_3,sng);
        contentValues.put(COL_4,dt);
        contentValues.put(COL_5,g);
        contentValues.put(COL_6,ttk);
        contentValues.put(COL_7,sd);
        contentValues.put(COL_8,sn);
        long result = db.insert(TABLE_NAME1,null,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor Lay1PhongTro(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res=db.rawQuery("select * from PHONGTRO where "+COL_1+"=?",new String[]{id});
        return res;
    }

    public Cursor getAllPhongTro(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res=db.rawQuery("select * from "+TABLE_NAME1,null);
        return res;
    }

    public boolean capnhatPhongTro(String id,String ten,String sng,String dt,String g,String ttk,String sd,String sn){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,ten);
        contentValues.put(COL_3,sng);
        contentValues.put(COL_4,dt);
        contentValues.put(COL_5,g);
        contentValues.put(COL_6,ttk);
        contentValues.put(COL_7,sd);
        contentValues.put(COL_8,sn);
        db.update(TABLE_NAME1, contentValues,"ID = ?",new String[]{id});
        return true;
    }

    public boolean capnhatDienNuoc(PhongTro p, String sd, String sn){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_1,p.getID());
        contentValues.put(COL_2,p.getTEN_PHONG());
        contentValues.put(COL_3,p.getSO_NGUOI());
        contentValues.put(COL_4,p.getDIEN_TICH());
        contentValues.put(COL_5,p.getGIA());
        contentValues.put(COL_6,p.getTHONG_TIN_KHAC());
        contentValues.put(COL_7,sd);
        contentValues.put(COL_8,sn);
        db.update(TABLE_NAME1, contentValues,"ID = ?",new String[]{p.getID()});
        return true;
    }

    public Integer xoaPhongTro(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_NAME1,"ID = ?",new String[] {id});
    }

    //BANG DICH VU

    public boolean themDichVu(String ten,String ct,String dg){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(TB2_COL_2,ten);
        contentValues.put(TB2_COL_3,ct);
        contentValues.put(TB2_COL_4,dg);
        long result = db.insert(TABLE_NAME2,null,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllDichVu(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor a=db.rawQuery("select * from "+TABLE_NAME2,null);
        return a;
    }

    public int LayGiaDien(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor a=db.rawQuery("select DON_GIA  from DICHVU where "+TB2_COL_1+"=1",null);
        String dien="0";
        while(a.moveToNext()){
            dien=a.getString(0);
        }
        return Integer.parseInt(dien);
    }

    public int LayGiaNuoc(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor a=db.rawQuery("select DON_GIA  from DICHVU where "+TB2_COL_1+"=2",null);
        String nuoc="0";
        while(a.moveToNext()){
            nuoc=a.getString(0);
        }
        return Integer.parseInt(nuoc);
    }

    public Cursor get1DichVu(String id){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor a=db.rawQuery("select * from DICHVU where "+TB2_COL_1+"=?",new String[]{id});
        return a;
    }

    public boolean capnhatDichVu(String m,String ten,String ct,String dg){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(TB2_COL_1,m);
        contentValues.put(TB2_COL_2,ten);
        contentValues.put(TB2_COL_3,ct);
        contentValues.put(TB2_COL_4,dg);
        db.update(TABLE_NAME2, contentValues,"MA_DV = ?",new String[]{m});
        return true;
    }

    public Integer xoaDichVu(String m){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_NAME2,"MA_DV = ?",new String[] {m});
    }

    //BANG KHACH THUE

    public boolean themKhach(String ten,String gt,String ns,String cmnd,String nc,String sdt,String p,String h){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(TB3_COL_2,ten);
        contentValues.put(TB3_COL_3,gt);
        contentValues.put(TB3_COL_4,ns);
        contentValues.put(TB3_COL_5,cmnd);
        contentValues.put(TB3_COL_6,nc);
        contentValues.put(TB3_COL_7,sdt);
        contentValues.put(TB3_COL_8,p);
        contentValues.put(TB3_COL_9,h);
        long result = db.insert(TABLE_NAME3,null,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor Lay1Khach(String id){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("select * from KHACH where "+TB3_COL_1+"=?",new String[]{id});
        return res;
    }


    public Cursor getAllKhach(String p){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("select * from KHACH where "+TB3_COL_8+"=?",new String[]{p});
        return res;
    }

    public boolean capnhatKhach(String id,String ten,String gt,String ns,String cmnd,String nc,String sdt,String p,String h){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(TB3_COL_1,id);
        contentValues.put(TB3_COL_2,ten);
        contentValues.put(TB3_COL_3,gt);
        contentValues.put(TB3_COL_4,ns);
        contentValues.put(TB3_COL_5,cmnd);
        contentValues.put(TB3_COL_6,nc);
        contentValues.put(TB3_COL_7,sdt);
        contentValues.put(TB3_COL_8,p);
        contentValues.put(TB3_COL_9,h);
        db.update(TABLE_NAME3, contentValues,"ID = ?",new String[]{id});
        return true;
    }

    public Integer xoaKhach(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_NAME3,"ID = ?",new String[] {id});
    }

    public Integer xoaAllKhachP(String p){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_NAME3,"PHONG_O = ?",new String[] {p});
    }
    //BANG HOA DON HANG THANG

    public boolean themHoaDon(String t,String p,String sd,String sn,String cpk,String tt,String nl,String tinht){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(TB5_COL_2,t);
        contentValues.put(TB5_COL_3,p);
        contentValues.put(TB5_COL_4,sd);
        contentValues.put(TB5_COL_5,sn);
        contentValues.put(TB5_COL_6,cpk);
        contentValues.put(TB5_COL_7,tt);
        contentValues.put(TB5_COL_8,nl);
        contentValues.put(TB5_COL_9,tinht);
        long result = db.insert(TABLE_NAME5,null,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllHoaDon(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("select * from HOADON order by strftime('%Y',NGAY_LAP) DESC,THANG DESC",null);
        return res;
    }

    public Cursor DaLapHoaDon(String phong,String thang){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("select * from HOADON where THANG = "+thang+" AND PHONG ="+phong,null);
        return res;
    }

    public boolean capnhatHoaDon(String id,String t,String p,String sd,String sn,String cpk,String tt,String nl,String tinht){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(TB5_COL_1,id);
        contentValues.put(TB5_COL_2,t);
        contentValues.put(TB5_COL_3,p);
        contentValues.put(TB5_COL_4,sd);
        contentValues.put(TB5_COL_5,sn);
        contentValues.put(TB5_COL_6,cpk);
        contentValues.put(TB5_COL_7,tt);
        contentValues.put(TB5_COL_8,nl);
        contentValues.put(TB5_COL_9,tinht);
        db.update(TABLE_NAME5, contentValues,"ID = ?",new String[]{id});
        return true;
    }

    public Integer xoaHoaDonT(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        int thang=Integer.parseInt(id);
        if(id.equals("1"))
            return db.delete(TABLE_NAME5,"THANG = ?",new String[] {"10"});
        else if(id.equals("2"))
            return db.delete(TABLE_NAME5,"THANG = ?",new String[] {"11"});
        else if(id.equals("3"))
            return db.delete(TABLE_NAME5,"THANG = ?",new String[] {"12"});
        else{
            String Thang=(thang-2)+"";
            return db.delete(TABLE_NAME5,"THANG = ?",new String[] {Thang});
        }
    }

    public Integer xoaHoaDon(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_NAME5,"ID = ?",new String[] {id});
    }

    public Integer xoaHoaDonP(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_NAME5,"PHONG = ?",new String[] {id});
    }

}
