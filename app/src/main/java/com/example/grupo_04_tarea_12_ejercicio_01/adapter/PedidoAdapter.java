package com.example.grupo_04_tarea_12_ejercicio_01.adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grupo_04_tarea_12_ejercicio_01.PedidoFormActivity;
import com.example.grupo_04_tarea_12_ejercicio_01.R;
import com.example.grupo_04_tarea_12_ejercicio_01.db.DBHelper;
import com.example.grupo_04_tarea_12_ejercicio_01.modelo.Articulo;
import com.example.grupo_04_tarea_12_ejercicio_01.modelo.Cliente;
import com.example.grupo_04_tarea_12_ejercicio_01.modelo.Direccion;
import com.example.grupo_04_tarea_12_ejercicio_01.modelo.Pedido;
import com.google.android.material.button.MaterialButton;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder> {
    //private ArrayList<Pedido> pedidos;
    private ArrayList<Object[]> pedidos;
    private DBHelper dbHelper;
    private Context context;

    public PedidoAdapter(Context context, ArrayList<Object[]> pedidos) {
        this.context = context;
        this.pedidos = pedidos;
        this.dbHelper = new DBHelper(context);
    }

    public void setPedidos(ArrayList<Object[]> pedidos) {
        this.pedidos = pedidos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedidos_recycler, parent, false);
        return new PedidoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoViewHolder holder, int position) {
        Object[] pedido = pedidos.get(position);

        String nombreCliente = dbHelper.obtenerNombreClientePorId(Integer.parseInt(pedido[1].toString()));
        String direccionCompleta = dbHelper.obtenerDireccionPorId(Integer.parseInt(pedido[3].toString()));

        // Ahora, en lugar de mostrar el ID del cliente, mostramos su nombre
        holder.tvCliente.setText(nombreCliente + "");
        holder.tvCodigo.setText(String.valueOf(pedido[0])+"");
        holder.tvFecha.setText(pedido[2].toString()+"");
        holder.tvDireccion.setText(direccionCompleta + "");

        holder.itemView.setOnLongClickListener(v -> {
            showOptionsDialog(pedido, position);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return pedidos.size();
    }

    private void showOptionsDialog(Object[] elemento, int position) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_options_date);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);

        MaterialButton btn_editar = dialog.findViewById(R.id.btn_editar);
        MaterialButton btn_eliminar = dialog.findViewById(R.id.btn_eliminar);
        MaterialButton btn_detalle = dialog.findViewById(R.id.btn_detalle);

        btn_editar.setOnClickListener(v -> {
            Intent intent = new Intent(context, PedidoFormActivity.class);
            Bundle contenedor = new Bundle();
            contenedor.putSerializable("option", 2);
            contenedor.putSerializable("pedido", elemento);
            intent.putExtras(contenedor);
            context.startActivity(intent);
            dialog.dismiss();
        });

        btn_eliminar.setOnClickListener(v -> {
            eliminarPedido(elemento, position);
            dialog.dismiss();
        });

        btn_detalle.setOnClickListener(v -> {
            // Lógica para mostrar detalles
            detallePedido(elemento);
            dialog.dismiss();
        });

        dialog.show();
    }



    private void eliminarPedido(Object[] elemento, int position) {
        new AlertDialog.Builder(context)
                .setTitle("Confirmar eliminación")
                .setMessage("¿Está seguro de que desea eliminar este pedido?")
                .setPositiveButton("Eliminar", (dialog, which) -> {
                    dbHelper.Delete_Detalle((Integer) elemento[5], (Integer) elemento[1]);
                    Pedido pedido = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        pedido = dbHelper.get_Pedido(Integer.parseInt(elemento[0].toString()));
                    }
                    dbHelper.Delete_Pedido(pedido.getIdpedido());
                    Articulo objArticulo = dbHelper.get_Articulo(Integer.parseInt(elemento[5].toString()));
                    dbHelper.Update_Stock_Articulo(objArticulo.getIdarticulo(), objArticulo.getStock() + Integer.parseInt(elemento[4].toString()));
                    pedidos.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, pedidos.size());
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void detallePedido(Object[] elemento) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.pedido_view_detail);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);

        TextView tv_idpedido = dialog.findViewById(R.id.tv_idpedido);
        TextView tv_cliente = dialog.findViewById(R.id.tv_cliente);
        TextView tv_articulo = dialog.findViewById(R.id.tv_articulo);
        TextView tv_cant_articulo = dialog.findViewById(R.id.tv_cant_articulo);
        TextView tv_direccion = dialog.findViewById(R.id.tv_direccion);
        TextView tv_fecha = dialog.findViewById(R.id.tv_fecha);

        tv_idpedido.setText(elemento[0].toString());

        Cliente objCliente = dbHelper.get_Cliente(Integer.parseInt(elemento[1].toString()));
        tv_cliente.setText(objCliente.getNombre());

        Articulo objArticulo = dbHelper.get_Articulo(Integer.parseInt(elemento[5].toString()));
        tv_articulo.setText(objArticulo.getDescripcion());
        tv_cant_articulo.setText(elemento[4].toString());

        Direccion objDireccion = dbHelper.get_Direccion(Integer.parseInt(elemento[3].toString()));
        tv_direccion.setText(objDireccion.getCalle() + ", " + objDireccion.getComuna() + ", " + objDireccion.getCiudad());

        tv_fecha.setText(elemento[2].toString());

        dialog.show();
    }

    public static class PedidoViewHolder extends RecyclerView.ViewHolder {
        TextView tvCodigo, tvCliente, tvFecha, tvDireccion;

        public PedidoViewHolder(View itemView) {
            super(itemView);
            tvCodigo = itemView.findViewById(R.id.tv_codigo);
            tvCliente = itemView.findViewById(R.id.tv_cliente);
            tvFecha = itemView.findViewById(R.id.tv_fecha);
            tvDireccion = itemView.findViewById(R.id.tv_direccion);
        }

        public void bind(Pedido pedido) {
            tvFecha.setText(pedido.getFecha_envio()+"");
            tvCliente.setText(pedido.getIdcliente()+"");
            tvDireccion.setText(pedido.getIddireccion()+"");
        }
    }
}
