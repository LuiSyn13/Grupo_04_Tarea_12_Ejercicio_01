package com.example.grupo_04_tarea_12_ejercicio_01.adapter;

import android.app.Dialog;
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
import com.example.grupo_04_tarea_12_ejercicio_01.db.DBHelper;
import com.example.grupo_04_tarea_12_ejercicio_01.modelo.Cliente;
import com.example.grupo_04_tarea_12_ejercicio_01.modelo.Direccion;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class Lista_Cliente_Adapter extends ArrayAdapter {
    private Context context;
    private DBHelper dbHelper;
    private ArrayList<Cliente> clientes;

    public Lista_Cliente_Adapter(@NonNull Context context, ArrayList<Cliente> clientes) {
        super(context, R.layout.cliente_item_list, clientes);
        this.context = context;
        this.dbHelper = new DBHelper(context);
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

        ArrayList<Direccion> direcciones = dbHelper.get_All_Direcciones(cliente.getIdcliente());
        if (direcciones.size() > 0) {
            tv_dir_01.setText(direcciones.get(0).getNumero() + " " + direcciones.get(0).getCalle());
        }
        if (direcciones.size() > 1) {
            tv_dir_02.setText(direcciones.get(1).getNumero() + " " + direcciones.get(1).getCalle());
        }
        if (direcciones.size() > 2) {
            tv_dir_03.setText(direcciones.get(2).getNumero() + " " + direcciones.get(2).getCalle());
        }

        tv_cliente.setText(cliente.getNombre() + " " + direcciones.size());
        iv_opt_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.direccion_form_register);
                dialog.setCancelable(true);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView tv_nombre = dialog.findViewById(R.id.tv_nombre);
                TextInputEditText tie_numero = dialog.findViewById(R.id.tie_numero);
                TextInputEditText tie_calle = dialog.findViewById(R.id.tie_calle);
                TextInputEditText tie_comuna = dialog.findViewById(R.id.tie_comuna);
                TextInputEditText tie_ciudad = dialog.findViewById(R.id.tie_ciudad);

                MaterialButton btn_aceptar = dialog.findViewById(R.id.btn_aceptar);
                MaterialButton btn_cancelar = dialog.findViewById(R.id.btn_cancelar);
                tv_nombre.setText(cliente.getNombre());


                btn_aceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String numero = tie_numero.getText().toString();
                        String calle = tie_calle.getText().toString();
                        String comuna = tie_comuna.getText().toString();
                        String ciudad = tie_ciudad.getText().toString();
                        Direccion objDireccion = new Direccion(numero, calle, comuna, ciudad, cliente.getIdcliente());
                        dbHelper.Insert_Direccion(objDireccion);
                        dialog.dismiss();
                    }
                });
                btn_cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
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
