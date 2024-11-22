package com.example.grupo_04_tarea_12_ejercicio_01.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grupo_04_tarea_12_ejercicio_01.R;
import com.example.grupo_04_tarea_12_ejercicio_01.db.DBHelper;
import com.example.grupo_04_tarea_12_ejercicio_01.modelo.Pedido;

import java.util.ArrayList;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder> {
    private ArrayList<Pedido> pedidos;
    private DBHelper dbHelper;

    public PedidoAdapter(Context context, ArrayList<Pedido> pedidos) {
        this.pedidos = pedidos;
        this.dbHelper = new DBHelper(context);
    }

    public void setPedidos(ArrayList<Pedido> pedidos) {
        this.pedidos = pedidos;
        notifyDataSetChanged(); // Notifica al RecyclerView que los datos han cambiado
    }

    @NonNull
    @Override
    public PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedidos_recycler, parent, false);
        return new PedidoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoViewHolder holder, int position) {
        Pedido pedido = pedidos.get(position);

        String nombreCliente = dbHelper.obtenerNombreClientePorId(pedido.getIdcliente());
        String direccionCompleta = dbHelper.obtenerDireccionPorId(pedido.getIddireccion());

        // Ahora, en lugar de mostrar el ID del cliente, mostramos su nombre
        holder.tvCliente.setText(nombreCliente + "");
        holder.tvCodigo.setText(String.valueOf(pedido.getIdpedido())+"");
        holder.tvFecha.setText(pedido.getFecha_envio().toString()+"");
        holder.tvDireccion.setText(direccionCompleta + "");
    }

    @Override
    public int getItemCount() {
        return pedidos.size();
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
