package com.example.grupo_04_tarea_12_ejercicio_01.db.Tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.grupo_04_tarea_12_ejercicio_01.modelo.Cliente;

import java.util.ArrayList;

public class ClienteTable {

    private static final String TABLE_CLIENTE = "cliente";

    public static final String KEY_IDCLIENTE = "idcliente";
    public static final String KEY_NOMBRE = "nombre";

    private static final String TABLE_CREATE_CLIENTE =  "CREATE TABLE " + TABLE_CLIENTE +
            "(" +
            KEY_IDCLIENTE + " integer PRIMARY KEY AUTOINCREMENT, " +
            KEY_NOMBRE + " text not null " +
            ")";

    public static String table_cliente() {
        return TABLE_CLIENTE;
    }

    public static void onCreate_TABLE_CLIENTE(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_CLIENTE);
    }

    public static int Insert_Cliente(SQLiteDatabase db, Cliente objCliente) {
        ContentValues values = new ContentValues();
        values.put(KEY_NOMBRE, objCliente.getNombre());
        long l = db.insert(TABLE_CLIENTE, null, values);
        if (l == -1) {
            return 0;
        }
        return (int) l;
    }

    public static void Update_Cliente(SQLiteDatabase db, Cliente objCliente) {
        ContentValues values = new ContentValues();
        values.put(KEY_NOMBRE, objCliente.getNombre());
        db.update(TABLE_CLIENTE, values, KEY_IDCLIENTE + " = " + objCliente.getIdcliente(), null);
    }

    public static void Delete_Cliente(SQLiteDatabase db, int idcliente) {
        try {
            db.delete(TABLE_CLIENTE, KEY_IDCLIENTE + " = " + idcliente, null);
        } catch (Exception e) {

        }
    }

    public static ArrayList<Cliente> get_All_Clientes(SQLiteDatabase db) {
        ArrayList<Cliente> lista = new ArrayList<>();
        try {
            String query = "SELECT*FROM " + TABLE_CLIENTE;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    Cliente objCliente = new Cliente();
                    objCliente.setIdcliente(cursor.getInt(0));
                    objCliente.setNombre(cursor.getString(1));
                    lista.add(objCliente);
                } while (cursor.moveToNext());
            }
            return lista;
        } catch (Exception e) {
            return null;
        }
    }

    public static Cliente get_Cliente(SQLiteDatabase db, int idcliente) {
        try {
            String query = "SELECT*FROM cliente WHERE " + KEY_IDCLIENTE + " = " + idcliente;
            Cursor cursor = db.rawQuery(query, null);
            Cliente objCliente = null;
            if (cursor.moveToFirst()) {
                objCliente = new Cliente();
                objCliente.setIdcliente(cursor.getInt(0));
                objCliente.setNombre(cursor.getString(1));
            }
            return objCliente;
        } catch (Exception e) {
            return null;
        }
    }
}
