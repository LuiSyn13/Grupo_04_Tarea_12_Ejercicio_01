package com.example.grupo_04_tarea_12_ejercicio_01;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grupo_04_tarea_12_ejercicio_01.adapter.PedidoAdapter;
import com.example.grupo_04_tarea_12_ejercicio_01.db.DBHelper;
import com.example.grupo_04_tarea_12_ejercicio_01.db.Tables.DireccionTable;
import com.example.grupo_04_tarea_12_ejercicio_01.modelo.Cliente;
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Toast.makeText(getContext(), "HAY " + dbHelper.get_All_Pedidos().size() + " PEDIDOS", Toast.LENGTH_SHORT).show();
        }
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
        Spinner spnDireccion = dialog.findViewById(R.id.spn_direccion);

        // Obtener lista de clientes
        ArrayList<Cliente> clientes = dbHelper.get_All_Clientes();

        // Adaptador para el Spinner de Clientes
        ArrayAdapter<Cliente> clienteAdapter = new ArrayAdapter<Cliente>(context, android.R.layout.simple_spinner_item, clientes) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_item, parent, false);
                }
                TextView textView = convertView.findViewById(android.R.id.text1);
                Cliente cliente = getItem(position);
                textView.setText(cliente.getIdcliente() + " - " + cliente.getNombre());
                return convertView;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
                }
                TextView textView = convertView.findViewById(android.R.id.text1);
                Cliente cliente = getItem(position);
                textView.setText(cliente.getIdcliente() + " - " + cliente.getNombre());
                return convertView;
            }
        };
        clienteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCliente.setAdapter(clienteAdapter);

        // Configurar listener para cargar direcciones según el cliente seleccionado
        spnCliente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cliente selectedCliente = (Cliente) parent.getItemAtPosition(position);
                int clienteId = selectedCliente.getIdcliente();

                // Obtener las direcciones del cliente seleccionado
                ArrayList<Direccion> direcciones = dbHelper.get_All_Direcciones(clienteId);

                // Crear y configurar adaptador para Spinner de Direcciones
                ArrayAdapter<Direccion> direccionAdapter = new ArrayAdapter<Direccion>(context, android.R.layout.simple_spinner_item, direcciones) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        if (convertView == null) {
                            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_item, parent, false);
                        }
                        TextView textView = convertView.findViewById(android.R.id.text1);
                        Direccion direccion = getItem(position);
                        textView.setText(direccion.getCalle() + ", " + direccion.getComuna() + ", " + direccion.getCiudad());
                        return convertView;
                    }

                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        if (convertView == null) {
                            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
                        }
                        TextView textView = convertView.findViewById(android.R.id.text1);
                        Direccion direccion = getItem(position);
                        textView.setText(direccion.getCalle() + ", " + direccion.getComuna() + ", " + direccion.getCiudad());
                        return convertView;
                    }
                };
                direccionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnDireccion.setAdapter(direccionAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Limpiar el Spinner de Direcciones si no hay cliente seleccionado
                spnDireccion.setAdapter(null);
            }
        });

        et_fechaenvio.setOnClickListener(v -> mostrarDateTimePicker(context, et_fechaenvio));

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            btn_aceptar.setOnClickListener(v -> {
                String fechaEnvio = et_fechaenvio.getText().toString();
                Cliente clienteSeleccionado = (Cliente) spnCliente.getSelectedItem();
                Direccion direccionSeleccionada = (Direccion) spnDireccion.getSelectedItem();

                if (fechaEnvio.isEmpty()) {
                    et_fechaenvio.setError("Debe seleccionar una fecha y hora");
                } else if (clienteSeleccionado == null) {
                    // Validación adicional: cliente no seleccionado
                } else if (direccionSeleccionada == null) {
                    // Validación adicional: dirección no seleccionada
                } else {
                    // Convertir String a LocalDateTime
                    LocalDateTime fechaEnvioLocalDateTime = null;
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    try {
                        fechaEnvioLocalDateTime = LocalDateTime.parse(fechaEnvio, formatter);
                    } catch (DateTimeParseException e) {
                        e.printStackTrace();
                        et_fechaenvio.setError("Fecha inválida");
                    }

                    if (fechaEnvioLocalDateTime != null) {
                        // Crear el objeto Pedido y agregarlo a la base de datos
                        Pedido nuevoPedido = new Pedido(clienteSeleccionado.getIdcliente(), fechaEnvioLocalDateTime, direccionSeleccionada.getIddireccion());
                        dbHelper.Insert_Pedido(nuevoPedido);
                        dialog.dismiss();
                        listarPedidos();
                    }
                }
            });
        }

        btn_cancelar.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }


    private void listarPedidos() {
        ArrayList<Pedido> pedidos = new ArrayList<>(); // Asegúrate de inicializarlo como una lista vacía

        // Verificar que dbHelper no es null antes de llamar al método
        if (dbHelper != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            pedidos = dbHelper.get_All_Pedidos();
        } else {
            Log.e("Pedidos", "Error: dbHelper es null o SDK no compatible.");
        }

        Log.d("Pedidos", "Número de pedidos: " + pedidos.size()); // Aquí no hace falta usar el operador ternario

        // Inicialización del adaptador de pedidos
        pedidoAdapter = new PedidoAdapter(getContext(),pedidos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(pedidoAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        listarPedidos();
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