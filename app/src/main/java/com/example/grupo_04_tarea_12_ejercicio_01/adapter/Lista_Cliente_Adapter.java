package com.example.grupo_04_tarea_12_ejercicio_01.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.grupo_04_tarea_12_ejercicio_01.R;
import com.example.grupo_04_tarea_12_ejercicio_01.modelo.Cliente;

import java.util.ArrayList;

public class Lista_Cliente_Adapter extends ArrayAdapter {
    private Context context;
    private ArrayList<Cliente> clientes;

    public Lista_Cliente_Adapter(@NonNull Context context, ArrayList<Cliente> clientes) {
        super(context, R.layout.cliente_item_list, clientes);
        this.context = context;
        this.clientes = clientes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cliente_item_list, null);
        Cliente cliente = clientes.get(position);
        TextView tv_cliente = view.findViewById(R.id.tv_cliente);
        TextView tv_dir_01 = view.findViewById(R.id.tv_dir_01);
        TextView tv_dir_02 = view.findViewById(R.id.tv_dir_02);
        TextView tv_dir_03 = view.findViewById(R.id.tv_dir_03);
        ImageView iv_opt_01 = view.findViewById(R.id.iv_opt_01);
        ImageView iv_opt_02 = view.findViewById(R.id.iv_opt_02);
        ImageView iv_opt_03 = view.findViewById(R.id.iv_opt_03);

        tv_cliente.setText(cliente.getNombre());

        iv_opt_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Editar direccion 01", Toast.LENGTH_SHORT).show();
            }
        });

        iv_opt_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Editar direccion 02", Toast.LENGTH_SHORT).show();
            }
        });

        iv_opt_03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Editar direccion 03", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
