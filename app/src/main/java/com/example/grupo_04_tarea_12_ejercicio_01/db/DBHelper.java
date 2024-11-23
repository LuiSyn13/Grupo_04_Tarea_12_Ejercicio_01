package com.example.grupo_04_tarea_12_ejercicio_01.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.grupo_04_tarea_12_ejercicio_01.db.Tables.ArticuloTable;
import com.example.grupo_04_tarea_12_ejercicio_01.db.Tables.ClienteTable;
import com.example.grupo_04_tarea_12_ejercicio_01.db.Tables.DetalleTable;
import com.example.grupo_04_tarea_12_ejercicio_01.db.Tables.DireccionTable;
import com.example.grupo_04_tarea_12_ejercicio_01.db.Tables.PedidoTable;
import com.example.grupo_04_tarea_12_ejercicio_01.modelo.Articulo;
import com.example.grupo_04_tarea_12_ejercicio_01.modelo.Cliente;
import com.example.grupo_04_tarea_12_ejercicio_01.modelo.Detalle;
import com.example.grupo_04_tarea_12_ejercicio_01.modelo.Direccion;
import com.example.grupo_04_tarea_12_ejercicio_01.modelo.Pedido;

import java.util.ArrayList;

public class DBHelper {
    private final DBAdapter dbAdapter;

    public  DBHelper(Context context) {
        dbAdapter = new DBAdapter(context);
    }

    /* METHODS TABLE CLIENT */
    public int Insert_Cliente(Cliente objCliente) {
        dbAdapter.open();
        int i = ClienteTable.Insert_Cliente(dbAdapter.getDb(), objCliente);
        dbAdapter.close();
        return i;
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

    /* METHODS TABLE ADDRESS */
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

    public ArrayList<Direccion> get_All_Direcciones(int idcliente) {
        dbAdapter.open();
        ArrayList<Direccion> list = DireccionTable.get_All_Direcciones(dbAdapter.getDb(), idcliente);
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
    public int Insert_Pedido(Pedido objPedido) {
        dbAdapter.open();
        int i = PedidoTable.Insert_Pedido(dbAdapter.getDb(), objPedido);
        dbAdapter.close();
        return i;
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

    public String obtenerNombreClientePorId(int idCliente) {
        dbAdapter.open();
        Cliente objCliente = ClienteTable.get_Cliente(dbAdapter.getDb(), idCliente); // Obtiene el cliente
        dbAdapter.close();

        // Si el cliente existe, retornar su nombre, si no, retornar un mensaje vacío o nulo
        if (objCliente != null) {
            return objCliente.getNombre();
        } else {
            return "Cliente no encontrado";  // Si el cliente no existe
        }
    }

    public String obtenerDireccionPorId(int iddireccion) {
        dbAdapter.open();
        Direccion direccion = DireccionTable.get_Direccion(dbAdapter.getDb(), iddireccion);  // Suponiendo que este método existe en tu tabla
        dbAdapter.close();
        return direccion != null ? direccion.getDireccionCompleta() : "Dirección no encontrada";  // Asumiendo que tienes un método `getDireccionCompleta`
    }



    /* METHODS TABLE ARTICLE */
    public void Insert_Articulo(Articulo objArticulo) {
        dbAdapter.open();
        ArticuloTable.Insert_Articulo(dbAdapter.getDb(), objArticulo);
        dbAdapter.close();
    }

    public void Update_Stock_Articulo(int idarticulo, int stock) {
        dbAdapter.open();
        ArticuloTable.UpdateStock_Articulo(dbAdapter.getDb(), idarticulo, stock);
        dbAdapter.close();
    }

    public void Update_Articulo(Articulo objArticulo) {
        dbAdapter.open();
        ArticuloTable.Update_Articulo(dbAdapter.getDb(), objArticulo);
        dbAdapter.close();
    }

    public void Delete_Articulo(int idarticulo) {
        dbAdapter.open();
        ArticuloTable.Delete_Articulo(dbAdapter.getDb(), idarticulo);
        dbAdapter.close();
    }

    public ArrayList<Articulo> get_All_Articulos() {
        dbAdapter.open();
        ArrayList<Articulo> list = ArticuloTable.get_All_Articulos(dbAdapter.getDb());
        dbAdapter.close();
        return list;
    }

    public Articulo get_Articulo(int idarticulo) {
        dbAdapter.open();
        Articulo objArticulo = ArticuloTable.get_Articulo(dbAdapter.getDb(), idarticulo);
        dbAdapter.close();
        return objArticulo;
    }

    /* METHODS TABLE DETAIL */
    public void Insert_Detalle(Detalle objDetalle) {
        dbAdapter.open();
        DetalleTable.Insert_Detalle(dbAdapter.getDb(), objDetalle);
        dbAdapter.close();
    }

    public void Update_Detalle(Detalle objDetalle) {
        dbAdapter.open();
        DetalleTable.Update_Detalle(dbAdapter.getDb(), objDetalle);
        dbAdapter.close();
    }

    public void Delete_Detalle(int idarticulo, int idpedido) {
        dbAdapter.open();
        DetalleTable.Delete_Detalle(dbAdapter.getDb(), idarticulo, idpedido);
        dbAdapter.close();
    }

    public ArrayList<Detalle> get_All_Detalles() {
        dbAdapter.open();
        ArrayList<Detalle> list = DetalleTable.get_All_Detalles(dbAdapter.getDb());
        dbAdapter.close();
        return list;
    }

    public ArrayList<Object[]> get_All_DetallesTOTAL() {
        dbAdapter.open();
        ArrayList<Object[]> list = DetalleTable.get_All_DetallesTOTAL(dbAdapter.getDb());
        dbAdapter.close();
        return list;
    }

    public Detalle get_Detalle(int idpedido, int idarticulo) {
        dbAdapter.open();
        Detalle objDetalle = DetalleTable.get_Detalle(dbAdapter.getDb(), idpedido, idarticulo);
        dbAdapter.close();
        return objDetalle;
    }
}
