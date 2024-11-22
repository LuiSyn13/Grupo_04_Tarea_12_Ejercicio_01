package com.example.grupo_04_tarea_12_ejercicio_01;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.grupo_04_tarea_12_ejercicio_01.db.DBHelper;
import com.example.grupo_04_tarea_12_ejercicio_01.modelo.Cliente;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
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
        return view;
    }

    private void register_Pedidos(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.pedido_form_register);
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        MaterialButton btn_aceptar = dialog.findViewById(R.id.btn_aceptar);
        MaterialButton btn_cancelar = dialog.findViewById(R.id.btn_cancelar);
        EditText et_fechaenvio = dialog.findViewById(R.id.et_fechaenvio);
        Spinner spnCliente = dialog.findViewById(R.id.spn_cliente);

        ArrayList<Cliente> clientes = dbHelper.get_All_Clientes();

        ArrayAdapter<Cliente> adapter = new ArrayAdapter<Cliente>(context, android.R.layout.simple_spinner_item, clientes) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_item, parent, false);
                }
                TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
                Cliente cliente = getItem(position);
                textView.setText(cliente.getIdcliente() + " - " + cliente.getNombre());
                return convertView;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
                }
                TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
                Cliente cliente = getItem(position);
                textView.setText(cliente.getIdcliente() + " - " + cliente.getNombre());
                return convertView;
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCliente.setAdapter(adapter);

        et_fechaenvio.setOnClickListener(v -> mostrarDateTimePicker(context, et_fechaenvio));

        btn_aceptar.setOnClickListener(v -> {
            String fechaEnvio = et_fechaenvio.getText().toString();
            Cliente clienteSeleccionado = (Cliente) spnCliente.getSelectedItem();

            if (fechaEnvio.isEmpty()) {
                et_fechaenvio.setError("Debe seleccionar una fecha y hora");
            } else if (clienteSeleccionado == null) {
            } else {
                dialog.dismiss();
            }
        });

        btn_cancelar.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }



    private void mostrarDateTimePicker(Context context, EditText editText) {
        final Calendar calendar = Calendar.getInstance();

        new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            new TimePickerDialog(context, (timeView, hourOfDay, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                String fechaHora = sdf.format(calendar.getTime());

                editText.setText(fechaHora);
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_registrar) {
            register_Pedidos(getContext());

        }
    }
}