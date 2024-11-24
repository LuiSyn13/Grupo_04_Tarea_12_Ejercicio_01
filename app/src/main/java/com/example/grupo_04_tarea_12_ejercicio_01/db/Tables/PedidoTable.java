package com.example.grupo_04_tarea_12_ejercicio_01.db.Tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.grupo_04_tarea_12_ejercicio_01.modelo.Pedido;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PedidoTable {
    private static final String TABLE_PEDIDO = "pedido";

    private static final String KEY_IDPEDIDO = "idpedido";
    private static final String KEY_IDCLIENTE = "idcliente";
    private static final String KEY_FECHA_ENVIO = "fecha_envio";
    private static final String KEY_IDDIRECCION = "iddireccion";

    private static final String TABLE_CREATE_PEDIDO = "CREATE TABLE " + TABLE_PEDIDO +
            "(" +
            KEY_IDPEDIDO + " integer PRIMARY KEY AUTOINCREMENT, " +
            KEY_IDCLIENTE + " integer not null, " +
            KEY_FECHA_ENVIO + " datetime not null, " +
            KEY_IDDIRECCION + " integer not null, " +
            "FOREIGN KEY(" + KEY_IDCLIENTE + ") " +
            "REFERENCES " + ClienteTable.table_cliente() + "(" + KEY_IDCLIENTE + ") ON DELETE CASCADE, " +
            "FOREIGN KEY(" + KEY_IDDIRECCION + ") " +
            "REFERENCES " + DireccionTable.table_direccion() + "(" + KEY_IDDIRECCION + ") ON DELETE CASCADE" +
            ")";

    public static String table_pedido() {
        return TABLE_PEDIDO;
    }

    public static void onCreate_TABLE_PEDIDO(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_PEDIDO);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static int Insert_Pedido(SQLiteDatabase db, Pedido objPedido) {
        ContentValues values = new ContentValues();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fechaEnvioFormatted = objPedido.getFecha_envio().format(formatter);

        values.put(KEY_IDCLIENTE, objPedido.getIdcliente());
        values.put(KEY_FECHA_ENVIO, fechaEnvioFormatted);
        values.put(KEY_IDDIRECCION, objPedido.getIddireccion());
        long i = db.insert(TABLE_PEDIDO, null, values);
        return (int) i;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void Update_Pedido(SQLiteDatabase db, Pedido objPedido) {
        ContentValues values = new ContentValues();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fechaEnvioFormatted = objPedido.getFecha_envio().format(formatter);

        values.put(KEY_IDCLIENTE, objPedido.getIdcliente());
        values.put(KEY_FECHA_ENVIO, fechaEnvioFormatted);
        values.put(KEY_IDDIRECCION, objPedido.getIddireccion());
        db.update(TABLE_PEDIDO, values, KEY_IDPEDIDO + " = " + objPedido.getIdpedido(), null);
    }

    public static void Delete_Pedido(SQLiteDatabase db, int idpedido) {
        db.delete(TABLE_PEDIDO, KEY_IDPEDIDO + " = " + idpedido, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<Pedido> get_All_Pedidos(SQLiteDatabase db) {
        ArrayList<Pedido> list = new ArrayList<>();
        try {
            String query = "SELECT*FROM " + TABLE_PEDIDO;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    Pedido objPedido = new Pedido();
                    objPedido.setIdpedido(cursor.getInt(0));
                    objPedido.setIdcliente(cursor.getInt(1));

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String fechaEnvioFormatted = cursor.getString(2);

                    objPedido.setFecha_envio(LocalDateTime.parse(fechaEnvioFormatted, formatter));
                    objPedido.setIddireccion(cursor.getInt(3));
                    list.add(objPedido);
                } while (cursor.moveToNext());
            }
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Pedido get_Pedido(SQLiteDatabase db, int idpedido) {
        try {
            String query = "SELECT*FROM pedido WHERE " + KEY_IDPEDIDO + " = " + idpedido;
            Cursor cursor = db.rawQuery(query, null);
            Pedido objPedido = null;
            if (cursor.moveToFirst()) {
                objPedido = new Pedido();
                objPedido.setIdpedido(cursor.getInt(0));
                objPedido.setIdcliente(cursor.getInt(1));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String fechaEnvioFormatted = cursor.getString(2);
                objPedido.setFecha_envio(LocalDateTime.parse(fechaEnvioFormatted, formatter));
                objPedido.setIddireccion(cursor.getInt(3));
            }
            return objPedido;
        } catch (Exception e) {
            return null;
        }
    }

    public static String obtenerNombreClientePorId(SQLiteDatabase db, int idCliente) {
        String nombreCliente = "";
        Cursor cursor = null;
        try {
            // Consulta para obtener el nombre del cliente
            cursor = db.rawQuery("SELECT " + ClienteTable.KEY_NOMBRE + " FROM " + ClienteTable.table_cliente() + " WHERE " + ClienteTable.KEY_IDCLIENTE + " = ?",
                    new String[]{String.valueOf(idCliente)});

            // Si el resultado contiene datos, los extraemos
            if (cursor.moveToFirst()) {
                nombreCliente = cursor.getString(0); // Obtiene el nombre del cliente
            }
        } catch (Exception e) {
            e.printStackTrace(); // Manejo de errores
        } finally {
            // Cierra el cursor si está abierto
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return nombreCliente;
    }

}
