package com.example.grupo_04_tarea_12_ejercicio_01.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.grupo_04_tarea_12_ejercicio_01.db.Tables.ClienteTable;
import com.example.grupo_04_tarea_12_ejercicio_01.db.Tables.DireccionTable;
import com.example.grupo_04_tarea_12_ejercicio_01.db.Tables.PedidoTable;
import com.example.grupo_04_tarea_12_ejercicio_01.modelo.Cliente;
import com.example.grupo_04_tarea_12_ejercicio_01.modelo.Direccion;
import com.example.grupo_04_tarea_12_ejercicio_01.modelo.Pedido;

import java.util.ArrayList;

public class DBHelper {
    private DBAdapter dbAdapter;

    public  DBHelper(Context context) {
        dbAdapter = new DBAdapter(context);
    }

    /* METHODS TABLE CLIENT */
    public void Insert_Cliente(Cliente objCliente) {
        dbAdapter.open();
        ClienteTable.Insert_Cliente(dbAdapter.getDb(), objCliente);
        dbAdapter.close();
    }

    public void Update_Cliente(Cliente objCliente) {
        dbAdapter.open();
        ClienteTable.Update_Cliente(dbAdapter.getDb(), objCliente);
        dbAdapter.close();
    }

    public void Delete_Cliente(int idcliente) {
        dbAdapter.open();
        ClienteTable.Delete_Cliente(dbAdapter.getDb(), idcliente);
        dbAdapter.close();
    }

    public ArrayList<Cliente> get_All_Clientes() {
        dbAdapter.open();
        ArrayList<Cliente> list = ClienteTable.get_All_Clientes(dbAdapter.getDb());
        dbAdapter.close();
        return list;
    }

    public Cliente get_Cliente(int idcliente) {
        dbAdapter.open();
        Cliente objCliente = ClienteTable.get_Cliente(dbAdapter.getDb(), idcliente);
        dbAdapter.close();
        return objCliente;
    }

    /* METHODS TABLE DIRECCION */
    public void Insert_Direccion(Direccion objDireccion) {
        dbAdapter.open();
        DireccionTable.Insert_Direccion(dbAdapter.getDb(), objDireccion);
        dbAdapter.close();
    }

    public void Update_Direccion(Direccion objDireccion) {
        dbAdapter.open();
        DireccionTable.Update_Direccion(dbAdapter.getDb(), objDireccion);
        dbAdapter.close();
    }

    public void Delete_Direccion(int iddireccion) {
        dbAdapter.open();
        DireccionTable.Delete_Direccion(dbAdapter.getDb(), iddireccion);
        dbAdapter.close();
    }

    public ArrayList<Direccion> get_All_Direcciones() {
        dbAdapter.open();
        ArrayList<Direccion> list = DireccionTable.get_All_Direcciones(dbAdapter.getDb());
        dbAdapter.close();
        return list;
    }

    public Direccion get_Direccion(int iddireccion) {
        dbAdapter.open();
        Direccion objDireccion = DireccionTable.get_Direccion(dbAdapter.getDb(), iddireccion);
        dbAdapter.close();
        return objDireccion;
    }

    /* METHODS TABLE PEDIDO */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void Insert_Pedido(Pedido objPedido) {
        dbAdapter.open();
        PedidoTable.Insert_Pedido(dbAdapter.getDb(), objPedido);
        dbAdapter.close();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void Update_Pedido(Pedido objPedido) {
        dbAdapter.open();
        PedidoTable.Update_Pedido(dbAdapter.getDb(), objPedido);
        dbAdapter.close();
    }

    public void Delete_Pedido(int idpedido) {
        dbAdapter.open();
        PedidoTable.Delete_Pedido(dbAdapter.getDb(), idpedido);
        dbAdapter.close();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Pedido> get_All_Pedidos() {
        dbAdapter.open();
        ArrayList<Pedido> list = PedidoTable.get_All_Pedidos(dbAdapter.getDb());
        dbAdapter.close();
        return list;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Pedido get_Pedido(int idpedido) {
        dbAdapter.open();
        Pedido objPedido = PedidoTable.get_Pedido(dbAdapter.getDb(), idpedido);
        dbAdapter.close();
        return objPedido;
    }
}
