package com.example.grupo_04_tarea_12_ejercicio_01.db.Tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.grupo_04_tarea_12_ejercicio_01.modelo.Articulo;

import java.util.ArrayList;

public class ArticuloTable {
    private static final String TABLE_ARTICULO = "articulo";

    private static final String KEY_IDARTICULO = "idarticulo";
    private static final String KEY_DESCRIPCION = "descripcion";
    private static final String KEY_STOCK = "stock";

    private static final String TABLE_CREATE_ARTICULO = "CREATE TABLE " + TABLE_ARTICULO +
            "(" +
            KEY_IDARTICULO + " integer PRIMARY KEY AUTOINCREMENT, " +
            KEY_DESCRIPCION + " text not null, " +
            KEY_STOCK + " integer not null " +
            ")";

    public static String table_articulo() {
        return TABLE_ARTICULO;
    }

    public static void onCreate_TABLE_ARTICULO(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_ARTICULO);
    }

    public static void Insert_Articulo(SQLiteDatabase db, Articulo objArticulo) {
        ContentValues values = new ContentValues();
        values.put(KEY_DESCRIPCION, objArticulo.getDescripcion());
        values.put(KEY_STOCK, objArticulo.getStock());
        db.insert(TABLE_ARTICULO, null, values);
    }

    public static void Update_Articulo(SQLiteDatabase db, Articulo objArticulo) {
        ContentValues values = new ContentValues();
        values.put(KEY_DESCRIPCION, objArticulo.getDescripcion());
        values.put(KEY_STOCK, objArticulo.getStock());
        db.update(TABLE_ARTICULO, values, KEY_IDARTICULO + " = " + objArticulo.getIdarticulo(), null);
    }

    public static void Delete_Articulo(SQLiteDatabase db, int idarticulo) {
        db.delete(TABLE_ARTICULO, KEY_IDARTICULO + " = " + idarticulo, null);
    }

    public static ArrayList<Articulo> get_All_Articulos(SQLiteDatabase db) {
        ArrayList<Articulo> list = new ArrayList<>();
        try {
            String query = "SELECT*FROM " + TABLE_ARTICULO;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    Articulo objArticulo = new Articulo();
                    objArticulo.setIdarticulo(cursor.getInt(0));
                    objArticulo.setDescripcion(cursor.getString(1));
                    objArticulo.setStock(cursor.getInt(2));
                    list.add(objArticulo);
                } while (cursor.moveToNext());
            }
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    public static Articulo get_Articulo(SQLiteDatabase db, int idarticulo) {
        try {
            String query = "SELECT*FROM articulo WHERE " + KEY_IDARTICULO + " = " + idarticulo;
            Cursor cursor = db.rawQuery(query, null);
            Articulo objArticulo = null;
            if (cursor.moveToFirst()) {
                objArticulo = new Articulo();
                objArticulo.setIdarticulo(cursor.getInt(0));
                objArticulo.setDescripcion(cursor.getString(1));
                objArticulo.setStock(cursor.getInt(2));
                }
            return objArticulo;
        }  catch (Exception e) {
            return null;
        }
    }
}
