package com.example.grupo_04_tarea_12_ejercicio_01.db.Tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.grupo_04_tarea_12_ejercicio_01.modelo.Direccion;

import java.util.ArrayList;

public class DireccionTable {
    private SQLiteDatabase db;

    private static final String TABLE_DIRECCION = "direccion";

    private static final String KEY_IDDIRECCION = "iddireccion";
    private static final String KEY_CALLE = "calle";
    private static final String KEY_COMUNA = "comuna";
    private static final String KEY_CIUDAD = "ciudad";
    private static final String KEY_IDCLIENTE = "idcliente";

    private static final String TABLE_CREATE_DIRECCION = "CREATE TABLE " + TABLE_DIRECCION +
            "(" +
            KEY_IDDIRECCION + " integer PRIMARY KEY AUTOINCREMENT, " +
            KEY_CALLE + " text not null, " +
            KEY_COMUNA + " text not null, " +
            KEY_CIUDAD + " text not null, " +
            KEY_IDCLIENTE + " integer, " +
            "FOREIGN KEY (" + KEY_IDCLIENTE + ") REFERENCES " + ClienteTable.table_cliente() + "(" + KEY_IDCLIENTE + ")" +
            ")";

    public static String table_direccion() {
        return TABLE_DIRECCION;
    }

    public static void onCreate_TABLE_DIRECCION(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_DIRECCION);
    }

    public static void Insert_Direccion(SQLiteDatabase db, Direccion objDireccion) {
        ContentValues values = new ContentValues();
        values.put(KEY_CALLE, objDireccion.getNumero());
        values.put(KEY_COMUNA, objDireccion.getCalle());
        values.put(KEY_CIUDAD, objDireccion.getComuna());
        values.put(KEY_IDCLIENTE, objDireccion.getIdcliente());
        db.insert(TABLE_DIRECCION, null, values);
    }

    public static void Update_Direccion(SQLiteDatabase db, Direccion objDireccion) {
        ContentValues values = new ContentValues();
        values.put(KEY_CALLE, objDireccion.getNumero());
        values.put(KEY_COMUNA, objDireccion.getCalle());
        values.put(KEY_CIUDAD, objDireccion.getComuna());
        values.put(KEY_IDCLIENTE, objDireccion.getIdcliente());
        db.update(TABLE_DIRECCION, values, KEY_IDDIRECCION + " = " + objDireccion.getIddireccion(), null);
    }

    public static void Delete_Direccion(SQLiteDatabase db, int iddireccion) {
        db.delete(TABLE_DIRECCION, KEY_IDDIRECCION + " = " + iddireccion, null);
    }

    public static ArrayList<Direccion> get_All_Direcciones(SQLiteDatabase db, int idcliente) {
        ArrayList<Direccion> list = new ArrayList<>();
        try {
            String query = "SELECT*FROM " + TABLE_DIRECCION + " d" + " INNER JOIN " + ClienteTable.table_cliente() + " c ON d." + KEY_IDCLIENTE + " = c." + KEY_IDCLIENTE + " WHERE c." + KEY_IDCLIENTE + " = " + idcliente;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    Direccion objDireccion = new Direccion();
                    objDireccion.setIddireccion(cursor.getInt(0));
                    objDireccion.setNumero(cursor.getString(1));
                    objDireccion.setCalle(cursor.getString(2));
                    objDireccion.setComuna(cursor.getString(3));
                    objDireccion.setCiudad(cursor.getString(4));
                    objDireccion.setIdcliente(cursor.getInt(5));
                    list.add(objDireccion);
                } while (cursor.moveToNext());
            }
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    public static Direccion get_Direccion(SQLiteDatabase db, int iddireccion) {
        try {
            String query = "SELECT*FROM direccion WHERE " + KEY_IDDIRECCION + " = " + iddireccion;
            Cursor cursor = db.rawQuery(query, null);
            Direccion objDireccion = null;
            if (cursor.moveToFirst()) {
                objDireccion = new Direccion();
                objDireccion.setIddireccion(cursor.getInt(0));
                objDireccion.setNumero(cursor.getString(1));
                objDireccion.setCalle(cursor.getString(2));
                objDireccion.setComuna(cursor.getString(3));
                objDireccion.setCiudad(cursor.getString(4));
                objDireccion.setIdcliente(cursor.getInt(5));
            }
            return objDireccion;
        }  catch (Exception e) {
            return null;
        }
    }

}
