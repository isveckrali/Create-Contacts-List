package com.example.flycodeveloper.kisileruygulamasi;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class KisilerDao {


    ArrayList<Kisiler> tumKisiler(Veritabani vt){
        ArrayList<Kisiler> kisilerArrayList = new ArrayList<>();
        SQLiteDatabase db = vt.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM kisiler",null);

        while (c.moveToNext()) {
            Kisiler k = new Kisiler(c.getInt(c.getColumnIndex("kisi_id")),
                    c.getString(c.getColumnIndex("kisi_ad")),
                    c.getString(c.getColumnIndex("kisi_tel")));
            kisilerArrayList.add(k);
        }
        db.close();
        return  kisilerArrayList;
    }
    ArrayList<Kisiler> kisiArama(Veritabani vt,String aramaKelime){
        ArrayList<Kisiler> kisilerArrayList = new ArrayList<>();
        SQLiteDatabase db = vt.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM kisiler WHERE kisi_ad like '%"+aramaKelime+"%'",null);

        while (c.moveToNext()) {
            Kisiler k = new Kisiler(c.getInt(c.getColumnIndex("kisi_id")),
                    c.getString(c.getColumnIndex("kisi_ad")),
                    c.getString(c.getColumnIndex("kisi_tel")));
            kisilerArrayList.add(k);
        }
        db.close();
        return  kisilerArrayList;
    }

    void kisiSil(Veritabani vt, int kisi_id){
        SQLiteDatabase db = vt.getWritableDatabase();
        db.delete("kisiler","kisi_id=?",new String[]{String.valueOf(kisi_id)});
        db.close();
    }

    void kisiEkle(Veritabani vt,String ad, String tel){
        SQLiteDatabase db = vt.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("kisi_ad",ad);
        values.put("kisi_tel",tel);
        db.insertOrThrow("kisiler",null,values);
        db.close();

    }

    void kisiGuncelle(Veritabani vt,int kisi_İd, String ad, String tel){
        SQLiteDatabase db = vt.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("kisi_ad",ad);
        values.put("kisi_tel",tel);
        db.update("kisiler",values,"kisi_id=?",new String[]{String.valueOf(kisi_İd)});
        db.close();

    }

}
