package com.example.grupo_04_tarea_12_ejercicio_01;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.grupo_04_tarea_12_ejercicio_01.db.DBHelper;
import com.example.grupo_04_tarea_12_ejercicio_01.modelo.Articulo;
import com.example.grupo_04_tarea_12_ejercicio_01.modelo.Cliente;
import com.example.grupo_04_tarea_12_ejercicio_01.modelo.Detalle;
import com.example.grupo_04_tarea_12_ejercicio_01.modelo.Direccion;
import com.example.grupo_04_tarea_12_ejercicio_01.modelo.Pedido;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class PedidoFormActivity extends AppCompatActivity implements View.OnClickListener {
    private DBHelper dbHelper;
    private Spinner spn_cliente, spn_direccion, spn_articulo;
    private EditText tie_fechaenvio, tie_cantidad;
    private TextView tv_cantidad_disp;

    private ArrayList<Cliente> list_c;
    private ArrayList<Direccion> list_d;
    private ArrayList<Articulo> list_a;
    private Object[] elemento_dp;

    private int art_stock = 0;
    private int option = 0;
    private String fechaenvio = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pedido_form);
        setTitle("Formulario de registro");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dbHelper = new DBHelper(this);
        list_c = dbHelper.get_All_Clientes();
        list_d = new ArrayList<>();
        list_a = dbHelper.get_All_Articulos();
        elemento_dp = null;

        findViewById(R.id.btn_aceptar).setOnClickListener(this);
        findViewById(R.id.btn_cancelar).setOnClickListener(this);
        findViewById(R.id.btn_add_articulo).setOnClickListener(this);
        findViewById(R.id.btn_add_cantidad).setOnClickListener(this);

        spn_cliente = findViewById(R.id.spn_cliente);
        spn_direccion = findViewById(R.id.spn_direccion);
        spn_articulo = findViewById(R.id.spn_articulo);
        tie_fechaenvio = findViewById(R.id.et_fechaenvio);
        tie_cantidad = findViewById(R.id.et_cantidad);
        tv_cantidad_disp = findViewById(R.id.tv_cantidad_disp);

        list_spn_cliente();
        list_spn_articulos();

        option = (int) getIntent().getExtras().getSerializable("option");
        if (option == 2) {
            elemento_dp = (Object[]) getIntent().getExtras().getSerializable("pedido");
            fechaenvio = elemento_dp[2].toString();
            tie_fechaenvio.setText(fechaenvio);
            tie_cantidad.setText(elemento_dp[4] + "");

            Articulo objArticulo = dbHelper.get_Articulo(Integer.parseInt(elemento_dp[5].toString()));
            dbHelper.Update_Stock_Articulo(Integer.parseInt(elemento_dp[5].toString()), Integer.parseInt(elemento_dp[4].toString()) + objArticulo.getStock());
            list_a = dbHelper.get_All_Articulos();

            spn_cliente.setEnabled(false);
            //spn_cliente.setVisibility(View.GONE);

            Cliente objCliente = dbHelper.get_Cliente(Integer.parseInt(elemento_dp[1].toString()));
            Toast.makeText(this, "cliente " + objCliente.getNombre(), Toast.LENGTH_SHORT).show();
            ((TextView) findViewById(R.id.tv_cliente)).setText(objCliente.getNombre());

            int r = 0;
            for (int i = 0; i < list_a.size(); i++) {
                if (list_a.get(i).getIdarticulo() == Integer.parseInt(elemento_dp[5].toString())) {
                    r = i;
                    break;
                }
            }
            spn_articulo.setSelection(r);
        } else if (option == 1) {
            findViewById(R.id.tv_cliente).setVisibility(View.GONE);
        }


        spn_cliente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                list_d.clear();
                if (elemento_dp != null) {
                    list_spn_direcciones(Integer.parseInt(elemento_dp[1].toString()));
                } else {
                    list_spn_direcciones(list_c.get(position).getIdcliente());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tie_fechaenvio.setOnClickListener(v -> mostrarDateTimePicker(this, tie_fechaenvio));

        spn_articulo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                art_stock = list_a.get(position).getStock();
                tv_cantidad_disp.setText("C. Disp: 0/" + art_stock);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tie_cantidad.addTextChangedListener(new TextWatcher() {
            private boolean isUpdating = false;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdating) return; // Evita la recursividad

                int cantidad = 0;
                if (!s.toString().isEmpty()) {
                    try {
                        cantidad = Integer.parseInt(s.toString());
                    } catch (NumberFormatException e) {
                        cantidad = 0;
                    }
                }

                if (cantidad > art_stock) {
                    isUpdating = true;
                    tie_cantidad.setText(String.valueOf(art_stock));
                    tie_cantidad.setSelection(String.valueOf(art_stock).length());
                    tv_cantidad_disp.setText("C. Disp: " + art_stock + "/" + art_stock);
                    Toast.makeText(PedidoFormActivity.this, "Stock total", Toast.LENGTH_SHORT).show();
                    isUpdating = false;
                } else {
                    tv_cantidad_disp.setText("C. Disp: " + cantidad + "/" + art_stock);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_aceptar) {
            register_pedido(v);
        } else if (v.getId() == R.id.btn_cancelar) {
            if (elemento_dp != null) {
                Articulo objArticulo = dbHelper.get_Articulo(Integer.parseInt(elemento_dp[5].toString()));
                dbHelper.Update_Stock_Articulo(Integer.parseInt(elemento_dp[5].toString()), objArticulo.getStock() - Integer.parseInt(elemento_dp[4].toString()));
            }
            finish();
        } else if (v.getId() == R.id.btn_add_articulo) {
            register_articulo(v);
        } else if (v.getId() == R.id.btn_add_cantidad) {
            add_cantidad();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void register_pedido(View v) {
        // Para la tabla pedido
        int pIdC = spn_cliente.getSelectedItemPosition();
        int pIdD = spn_direccion.getSelectedItemPosition();

        int idcliente = list_c.get(pIdC).getIdcliente();
        int iddireccion = list_d.get(pIdD).getIddireccion();
        String fechaenvio = tie_fechaenvio.getText().toString();
        if (fechaenvio.isEmpty()) {
            new Handler().postDelayed(() -> {
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setTitle("Alerta")
                        .setMessage("Debe seleccionar una fecha y hora")
                        .setCancelable(false)
                        .create();
                alertDialog.show();
                new Handler().postDelayed(alertDialog::dismiss, 2000);
            }, 0);
        } else {
            LocalDateTime f_envio_LDT = null;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            try {
                f_envio_LDT = LocalDateTime.parse(fechaenvio, formatter);
            } catch (DateTimeParseException e) {
                e.printStackTrace();
                tie_fechaenvio.setError("Fecha inválida");
            }
            if (f_envio_LDT != null)  {
                int p_Art = spn_articulo.getSelectedItemPosition();
                int id_articulo = list_a.get(p_Art).getIdarticulo();

                int cantidad = Integer.parseInt(tie_cantidad.getText().toString());
                if (option == 1) {
                    Pedido nuevoPedido = new Pedido(idcliente, f_envio_LDT, iddireccion);
                    int id_pedido = dbHelper.Insert_Pedido(nuevoPedido);

                    Detalle objDetalle = new Detalle(id_articulo, id_pedido, cantidad);
                    dbHelper.Insert_Detalle(objDetalle);

                } else if(option == 2){
                    int idp = Integer.parseInt(elemento_dp[0].toString());
                    int idc = Integer.parseInt(elemento_dp[1].toString());
                    Pedido objPedido = new Pedido(idp, idc, f_envio_LDT, iddireccion);
                    dbHelper.Update_Pedido(objPedido);

                    dbHelper.Delete_Detalle(Integer.parseInt(elemento_dp[5].toString()), Integer.parseInt(elemento_dp[0].toString()));

                    Detalle objDetalle = new Detalle(id_articulo, idp, cantidad);
                    dbHelper.Insert_Detalle(objDetalle);
                }
                Toast.makeText(this, "descripcion: " + list_a.get(p_Art).getDescripcion() + " id_articulo: " + id_articulo, Toast.LENGTH_SHORT).show();

                dbHelper.Update_Stock_Articulo(id_articulo, list_a.get(p_Art).getStock() - cantidad);
                finish();
            } else {
                Toast.makeText(this, "Fecha inválida", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void list_spn_cliente() {
        String[] datos = new String[list_c.size()];
        for (int i = 0; i < list_c.size(); i++) {
            datos[i] = list_c.get(i).getNombre();
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, datos);
        spn_cliente.setAdapter(adapter);
    }

    private void list_spn_direcciones(int idcliente) {
        ArrayList<Direccion> list_d_t = dbHelper.get_All_Direcciones(idcliente);
        ArrayList<String> datos = new ArrayList<>();
        for (int i = 0; i < list_d_t.size(); i++) {
            if (!list_d_t.get(i).getNumero().isEmpty()) {
                list_d.add(list_d_t.get(i));
                datos.add(list_d_t.get(i).getNumero() + ", " + list_d_t.get(i).getCalle() + ", " + list_d_t.get(i).getComuna());
            }
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, datos);
        spn_direccion.setAdapter(adapter);
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

    private void list_spn_articulos() {
        ArrayList<String> datos = new ArrayList<>();
        for (int i = 0; i < list_a.size(); i++) {
            datos.add(list_a.get(i).getDescripcion());
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, datos);
        spn_articulo.setAdapter(adapter);
    }

    private void register_articulo(View v) {
        Dialog dialog = new Dialog(v.getContext());
        dialog.setContentView(R.layout.articulo_form_register);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);

        MaterialButton btn_aceptar = dialog.findViewById(R.id.btn_aceptar);
        MaterialButton btn_cancelar = dialog.findViewById(R.id.btn_cancelar);
        TextInputEditText tie_descripcion = dialog.findViewById(R.id.tie_descripcion);
        TextInputEditText tie_stock = dialog.findViewById(R.id.tie_stock);

        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String descripcion = tie_descripcion.getText().toString();
                int stock = Integer.parseInt(tie_stock.getText().toString());
                Articulo objA = new Articulo(descripcion, stock);
                dbHelper.Insert_Articulo(objA);
                tie_descripcion.setText("");
                tie_stock.setText("");
                list_spn_articulos();
                dialog.dismiss();
            }
        });

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tie_descripcion.setText("");
                tie_stock.setText("");
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void add_cantidad() {
        String cantidadStr = tie_cantidad.getText().toString();
        if (!cantidadStr.isEmpty()) {
            int cantidad = Integer.parseInt(cantidadStr);
            if (cantidad < art_stock) {
                cantidad++;
                tie_cantidad.setText(String.valueOf(cantidad));
            } else {
                Toast.makeText(this, "No se puede exceder el stock disponible", Toast.LENGTH_SHORT).show();
            }
        } else {
            tie_cantidad.setText("1");
        }
    }

}