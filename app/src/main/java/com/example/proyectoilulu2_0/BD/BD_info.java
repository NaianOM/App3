package com.example.proyectoilulu2_0.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class BD_info extends BD_api{

    private static Context context;

    public BD_info(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public boolean comprobarInfo(int id){
        BD_api dbPagina = new BD_api(context);
        SQLiteDatabase db = dbPagina.getWritableDatabase();

        boolean Info;
        Cursor cursorInfo;

        cursorInfo = db.rawQuery("SELECT * FROM " + TABLE_INFO + " WHERE id = " + id + " LIMIT 1", null);

        if (cursorInfo.moveToFirst()) {
            Info = true;
        }else{
            Info = false;
        }

        cursorInfo.close();

        return Info;
    }

    public String verInfo(int id) {

        BD_api dbPagina = new BD_api(context);
        SQLiteDatabase db = dbPagina.getWritableDatabase();

        String Info = null;
        Cursor cursorInfo;

        cursorInfo = db.rawQuery("SELECT * FROM " + TABLE_INFO + " WHERE id = " + id + " LIMIT 1", null);

        if (cursorInfo.moveToFirst()) {
            Info = cursorInfo.getString(1);
        }

        cursorInfo.close();

        return Info;
    }

    public static long insertarInfo(int id, String textoC) {

        long status = 0;

        try {
            BD_api dbPagina = new BD_api(context);
            SQLiteDatabase db = dbPagina.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("id", id);
            values.put("textoC", textoC);

            status = db.insert(TABLE_INFO, null, values);
        } catch (Exception ex) {
            ex.toString();
        }

        return status;
    }

    public boolean editarInfo(int id, String textoC) {

        boolean correcto = false;

        BD_api dbPagina = new BD_api(context);
        SQLiteDatabase db = dbPagina.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_INFO + " SET textoC = '" + textoC + "' WHERE id = " + id + "");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }
}
