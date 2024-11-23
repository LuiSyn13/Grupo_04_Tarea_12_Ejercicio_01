package com.example.grupo_04_tarea_12_ejercicio_01.db.Tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.grupo_04_tarea_12_ejercicio_01.modelo.Detalle;

import java.util.ArrayList;
import java.util.Objects;

public class DetalleTable {
    private static final String TABLE_DETALLE = "detalle";

    private static final String KEY_IDPEDIDO = "idpedido";
    private static final String KEY_IDARTICULO = "idarticulo";
    private static final String KEY_CANTIDAD = "cantidad";

    private static final String TABLE_CREATE_DETALLE = "CREATE TABLE " + TABLE_DETALLE +
            "(" +
            KEY_IDPEDIDO + " integer, " +
            KEY_IDARTICULO + " integer, " +
            KEY_CANTIDAD + " integer not null, " +
            "FOREIGN KEY(" + KEY_IDPEDIDO + ") REFERENCES " + PedidoTable.table_pedido() + "(" + KEY_IDPEDIDO + ") ON DELETE CASCADE, " +
            "FOREIGN KEY(" + KEY_IDARTICULO + ") REFERENCES " + ArticuloTable.table_articulo() + "(" + KEY_IDARTICULO + ") ON DELETE CASCADE " +
            ")";

    public static String table_detalle() {
        return TABLE_DETALLE;
    }

    public static void onCreate_TABLE_DETALLE(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_DETALLE);
    }

    public static void Insert_Detalle(SQLiteDatabase db, Detalle objDetalle) {
        ContentValues values = new ContentValues();
        values.put(KEY_IDPEDIDO, objDetalle.getIdpedido());
        values.put(KEY_IDARTICULO, objDetalle.getIdarticulo());
        values.put(KEY_CANTIDAD, objDetalle.getCantidad());
        long p = db.insert(TABLE_DETALLE, null, values);
        if (p == -1) {
            Log.e("DB_ERROR", "Error al insertar en la tabla detalle" + objDetalle.getIdpedido() + " iar:" + objDetalle.getIdarticulo());
        }
    }

    public static void Update_Detalle(SQLiteDatabase db, Detalle objDetalle) {
        ContentValues values = new ContentValues();
        values.put(KEY_IDPEDIDO, objDetalle.getIdpedido());
        values.put(KEY_IDARTICULO, objDetalle.getIdarticulo());
        values.put(KEY_CANTIDAD, objDetalle.getCantidad());
        db.update(TABLE_DETALLE, values, KEY_IDPEDIDO + " = " + objDetalle.getIdpedido() + " AND " + KEY_IDARTICULO + " = " + objDetalle.getIdarticulo(), null);
    }

    public static void Delete_Detalle(SQLiteDatabase db, int idarticulo, int idpedido) {
        db.delete(TABLE_DETALLE, KEY_IDPEDIDO + " = " + idpedido + " AND " + KEY_IDARTICULO + " = " + idarticulo, null);
    }

    public static ArrayList<Detalle> get_All_Detalles(SQLiteDatabase db) {
        ArrayList<Detalle> list = new ArrayList<>();
        try {
            String query = "SELECT*FROM " + TABLE_DETALLE;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    Detalle objDetalle = new Detalle();
                    objDetalle.setIdpedido(cursor.getInt(0));
                    objDetalle.setIdarticulo(cursor.getInt(1));
                    objDetalle.setCantidad(cursor.getInt(2));
                    list.add(objDetalle);
                } while (cursor.moveToNext());
            }
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    public static ArrayList<Object[]> get_All_DetallesTOTAL(SQLiteDatabase db) {
        ArrayList<Object[]> list = new ArrayList<>();
        try {
            String query = "SELECT p.*, d.cantidad, a.* FROM pedido p INNER JOIN detalle d ON p.idpedido = d.idpedido" +
                    " INNER JOIN articulo a ON d.idarticulo = a.idarticulo";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    Object[] objDetalle = new Object[8];
                    objDetalle[0] = cursor.getInt(0);
                    objDetalle[1] = cursor.getInt(1);
                    objDetalle[2] = cursor.getString(2);
                    objDetalle[3] = cursor.getString(3);
                    objDetalle[4] = cursor.getInt(4);
                    objDetalle[5] = cursor.getInt(5);
                    objDetalle[6] = cursor.getInt(6);
                    objDetalle[7] = cursor.getInt(7);
                    list.add(objDetalle);
                } while (cursor.moveToNext());
            }
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    public static Detalle get_Detalle(SQLiteDatabase db, int idpedido, int idarticulo) {
        try {
            String query = "SELECT*FROM detalle WHERE " + KEY_IDPEDIDO + " = " + idpedido + " AND " + KEY_IDARTICULO + " = " + idarticulo;
            Cursor cursor = db.rawQuery(query, null);
            Detalle objDetalle = null;
            if (cursor.moveToFirst()) {
                objDetalle = new Detalle();
                objDetalle.setIdpedido(cursor.getInt(0));
                objDetalle.setIdarticulo(cursor.getInt(1));
                objDetalle.setCantidad(cursor.getInt(2));
            }
            return objDetalle;
        } catch (Exception e) {
            return null;
        }
    }
}
