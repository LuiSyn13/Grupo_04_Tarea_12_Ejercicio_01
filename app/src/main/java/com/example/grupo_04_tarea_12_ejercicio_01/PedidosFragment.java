package com.example.grupo_04_tarea_12_ejercicio_01;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grupo_04_tarea_12_ejercicio_01.adapter.PedidoAdapter;
import com.example.grupo_04_tarea_12_ejercicio_01.db.DBHelper;
import com.example.grupo_04_tarea_12_ejercicio_01.db.Tables.DireccionTable;
import com.example.grupo_04_tarea_12_ejercicio_01.modelo.Articulo;
import com.example.grupo_04_tarea_12_ejercicio_01.modelo.Cliente;
import com.example.grupo_04_tarea_12_ejercicio_01.modelo.Detalle;
import com.example.grupo_04_tarea_12_ejercicio_01.modelo.Direccion;
import com.example.grupo_04_tarea_12_ejercicio_01.modelo.Pedido;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PedidosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PedidosFragment extends Fragment implements View.OnClickListener {
    private DBHelper dbHelper;
    private RecyclerView recyclerView;
    private PedidoAdapter pedidoAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PedidosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PedidosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PedidosFragment newInstance(String param1, String param2) {
        PedidosFragment fragment = new PedidosFragment();
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
        final View view = inflater.inflate(R.layout.fragment_pedidos, container, false);
        view.findViewById(R.id.btn_registrar).setOnClickListener(this);
        dbHelper = new DBHelper(getContext());
        recyclerView = view.findViewById(R.id.rv_pedidos);
        listarPedidos();
        return view;
    }

    private void listarPedidos() {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        ArrayList<Object[]> lista_detalle_pedido = dbHelper.get_All_DetallesTOTAL();


        if (dbHelper != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            pedidos = dbHelper.get_All_Pedidos();
        } else {
            Log.e("Pedidos", "Error: dbHelper es null o SDK no compatible.");
        }
        pedidoAdapter = new PedidoAdapter(getContext(),lista_detalle_pedido);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(pedidoAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        listarPedidos();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_registrar) {
            //register_Pedidos(getContext());
            Intent intent = new Intent(getContext(), PedidoFormActivity.class);
            Bundle contenedor = new Bundle();
            contenedor.putSerializable("option", 1);
            intent.putExtras(contenedor);
            startActivity(intent);
        }
    }
}