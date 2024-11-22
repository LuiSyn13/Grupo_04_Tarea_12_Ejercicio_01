package com.example.grupo_04_tarea_12_ejercicio_01;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.grupo_04_tarea_12_ejercicio_01.adapter.Lista_Cliente_Adapter;
import com.example.grupo_04_tarea_12_ejercicio_01.db.DBHelper;
import com.example.grupo_04_tarea_12_ejercicio_01.modelo.Cliente;
import com.google.android.material.button.MaterialButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClienteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClienteFragment extends Fragment implements View.OnClickListener {
    private DBHelper dbHelper;
    private ListView lv_clientes;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ClienteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClienteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClienteFragment newInstance(String param1, String param2) {
        ClienteFragment fragment = new ClienteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_cliente, container, false);
        view.findViewById(R.id.btn_registrar).setOnClickListener(this);
        lv_clientes = view.findViewById(R.id.lv_clientes);
        dbHelper = new DBHelper(getContext());
        listar_Clientes();
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_registrar) {
            register_Cliente(getContext());
            listar_Clientes();
        }
    }

    private void register_Cliente(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.cliente_form_register);
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        MaterialButton btn_aceptar = dialog.findViewById(R.id.btn_aceptar);
        MaterialButton btn_cancelar = dialog.findViewById(R.id.btn_cancelar);

        btn_aceptar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               TextView tv_nombre = dialog.findViewById(R.id.tv_nombre);
               Cliente objCliente = new Cliente(tv_nombre.getText().toString());
               dbHelper.Insert_Cliente(objCliente);
               tv_nombre.setText("");
               listar_Clientes();
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

    private void listar_Clientes() {
        Lista_Cliente_Adapter adapter = new Lista_Cliente_Adapter(getContext(), dbHelper.get_All_Clientes());
        lv_clientes.setAdapter(adapter);
    }
}