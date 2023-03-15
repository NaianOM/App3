package com.example.proyectoilulu2_0.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class BD_cuenta extends BD_api{
    Context context;

    public BD_cuenta(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public boolean comprobarCuenta(int idUsr, int idCut){
        BD_api dbPagina = new BD_cuenta(context);
        SQLiteDatabase db = dbPagina.getWritableDatabase();

        boolean Cuenta;
        Cursor cursorCuenta;

        cursorCuenta = db.rawQuery("SELECT * FROM " + TABLE_CUENTA + " WHERE idUsr = " + idUsr + " and idCut = " + idCut + " LIMIT 1", null);

        if (cursorCuenta.moveToFirst()) {
            Cuenta = true;
        }else{
            Cuenta = false;
        }

        cursorCuenta.close();

        return Cuenta;
    }

    public String verCuenta(int idUsr, int idCut) {

        BD_api dbPagina = new BD_api(context);
        SQLiteDatabase db = dbPagina.getWritableDatabase();

        String Cuenta = null;
        Cursor cursorCuenta;

        cursorCuenta = db.rawQuery("SELECT * FROM " + TABLE_CUENTA + " WHERE idUsr = " + idUsr + " and idCut = " + idCut + " LIMIT 1", null);

        if (cursorCuenta.moveToFirst()) {
            Cuenta = cursorCuenta.getString(3);
        }

        cursorCuenta.close();

        return Cuenta;
    }

    public long insertarCuenta(int idUsr, int idCut, String textoCC) {

        long status = 0;

        try {
            BD_api dbPagina = new BD_api(context);
            SQLiteDatabase db = dbPagina.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("idUsr", idUsr);
            values.put("idCut", idCut);
            values.put("textoCC", textoCC);

            status = db.insert(TABLE_CUENTA, null, values);
        } catch (Exception ex) {
            ex.toString();
        }

        return status;
    }

    public boolean editarCuenta(int idUsr, int idCut, String textoCC) {

        boolean correcto = false;

        BD_api dbPagina = new BD_api(context);
        SQLiteDatabase db = dbPagina.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_CUENTA + " SET textoCC = '" + textoCC + "' WHERE idUsr = " + idUsr + " and idCut = " + idCut + "");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    public boolean eliminarCuenta(int idUsr, int idCut) {

        boolean correcto = false;

        BD_api dbPagina = new BD_api(context);
        SQLiteDatabase db = dbPagina.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_CUENTA + " WHERE idUsr = " + idUsr + " and idCut = " + idCut + "");
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
