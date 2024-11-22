package com.example.grupo_04_tarea_12_ejercicio_01.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grupo_04_tarea_12_ejercicio_01.R;
import com.example.grupo_04_tarea_12_ejercicio_01.modelo.Pedido;

import java.util.ArrayList;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder> {
    private ArrayList<Pedido> pedidos;

    public PedidoAdapter(ArrayList<Pedido> pedidos) {
        this.pedidos = pedidos;
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
        holder.bind(pedido);
    }

    @Override
    public int getItemCount() {
        return pedidos.size();
    }

    public static class PedidoViewHolder extends RecyclerView.ViewHolder {
        TextView tvFecha, tvCliente, tvDireccion;

        public PedidoViewHolder(View itemView) {
            super(itemView);
            tvFecha = itemView.findViewById(R.id.tv_fecha);
            tvCliente = itemView.findViewById(R.id.tv_cliente);
            tvDireccion = itemView.findViewById(R.id.tv_direccion);
        }

        public void bind(Pedido pedido) {
            tvFecha.setText(pedido.getFecha_envio()+"");
            tvCliente.setText(pedido.getIdcliente()+"");
            tvDireccion.setText(pedido.getIddireccion()+"");
        }
    }
}
