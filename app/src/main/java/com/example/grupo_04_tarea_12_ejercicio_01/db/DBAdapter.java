package com.example.grupo_04_tarea_12_ejercicio_01.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.grupo_04_tarea_12_ejercicio_01.db.Tables.ArticuloTable;
import com.example.grupo_04_tarea_12_ejercicio_01.db.Tables.ClienteTable;
import com.example.grupo_04_tarea_12_ejercicio_01.db.Tables.DetalleTable;
import com.example.grupo_04_tarea_12_ejercicio_01.db.Tables.DireccionTable;
import com.example.grupo_04_tarea_12_ejercicio_01.db.Tables.PedidoTable;

public class DBAdapter {
    private static final String DATABASE_NAME = "grupo_04_DBPedidos";
    private static final int DATABASE_VERSION = 1;
    private static Context context;
    private DatabaseHelper databaseHelper;
    private static SQLiteDatabase db;

    public DBAdapter(Context context) {
        DBAdapter.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    public static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            ClienteTable.onCreate_TABLE_CLIENTE(db);
            DireccionTable.onCreate_TABLE_DIRECCION(db);
            PedidoTable.onCreate_TABLE_PEDIDO(db);
            ArticuloTable.onCreate_TABLE_ARTICULO(db);
            DetalleTable.onCreate_TABLE_DETALLE(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + ClienteTable.table_cliente());
            ClienteTable.onCreate_TABLE_CLIENTE(db);

            db.execSQL("DROP TABLE IF EXISTS " + DireccionTable.table_direccion());
            DireccionTable.onCreate_TABLE_DIRECCION(db);

            db.execSQL("DROP TABLE IF EXISTS " + PedidoTable.table_pedido());
            PedidoTable.onCreate_TABLE_PEDIDO(db);

            db.execSQL("DROP TABLE IF EXISTS " + ArticuloTable.table_articulo());
            ArticuloTable.onCreate_TABLE_ARTICULO(db);

            db.execSQL("DROP TABLE IF EXISTS " + DetalleTable.table_detalle());
            DetalleTable.onCreate_TABLE_DETALLE(db);
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
            db.execSQL("PRAGMA foreign_keys = ON;");
        }
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public DBAdapter open() {
        try {
            db = databaseHelper.getWritableDatabase();
        } catch (Exception e) {
            Toast.makeText(context, "Error abrir db", Toast.LENGTH_SHORT).show();
        }
        return this;
    }

    public void close() {
        databaseHelper.close();
        db.close();
    }

}
