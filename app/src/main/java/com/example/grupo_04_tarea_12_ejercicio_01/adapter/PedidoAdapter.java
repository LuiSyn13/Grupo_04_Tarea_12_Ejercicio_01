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
        });

        dialog.show();
    }

    private void editarPedido(Context context, Pedido pedido) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.pedido_form_register);
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        MaterialButton btn_aceptar = dialog.findViewById(R.id.btn_aceptar);
        MaterialButton btn_cancelar = dialog.findViewById(R.id.btn_cancelar);
        EditText et_fechaenvio = dialog.findViewById(R.id.et_fechaenvio);
        Spinner spnCliente = dialog.findViewById(R.id.spn_cliente);
        Spinner spnDireccion = dialog.findViewById(R.id.spn_direccion);

        ArrayList<Cliente> clientes = dbHelper.get_All_Clientes();

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

        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getIdcliente() == pedido.getIdcliente()) {
                spnCliente.setSelection(i);
                break;
            }
        }

        spnCliente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cliente selectedCliente = (Cliente) parent.getItemAtPosition(position);
                int clienteId = selectedCliente.getIdcliente();

                ArrayList<Direccion> direcciones = dbHelper.get_All_Direcciones(clienteId);

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

                for (int i = 0; i < direcciones.size(); i++) {
                    if (direcciones.get(i).getIddireccion() == pedido.getIddireccion()) {
                        spnDireccion.setSelection(i);
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spnDireccion.setAdapter(null);
            }
        });

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            et_fechaenvio.setText(pedido.getFecha_envio().format(formatter));
        }

        et_fechaenvio.setOnClickListener(v -> mostrarDateTimePicker(context, et_fechaenvio));

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            btn_aceptar.setOnClickListener(v -> {
                String fechaEnvio = et_fechaenvio.getText().toString();
                Cliente clienteSeleccionado = (Cliente) spnCliente.getSelectedItem();
                Direccion direccionSeleccionada = (Direccion) spnDireccion.getSelectedItem();

                if (fechaEnvio.isEmpty()) {
                    et_fechaenvio.setError("Debe seleccionar una fecha y hora");
                } else if (clienteSeleccionado == null || direccionSeleccionada == null) {
                    Toast.makeText(context, "Debe seleccionar cliente y dirección", Toast.LENGTH_SHORT).show();
                } else {
                    // Convertir la fecha de envío a LocalDateTime
                    LocalDateTime fechaEnvioLocalDateTime;
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        fechaEnvioLocalDateTime = LocalDateTime.parse(fechaEnvio, formatter);
                    } catch (DateTimeParseException e) {
                        e.printStackTrace();
                        et_fechaenvio.setError("Fecha inválida");
                        return;
                    }

                    // Actualizar pedido
                    pedido.setFecha_envio(fechaEnvioLocalDateTime);
                    pedido.setIdcliente(clienteSeleccionado.getIdcliente());
                    pedido.setIddireccion(direccionSeleccionada.getIddireccion());

                    dbHelper.Update_Pedido(pedido); // Asegúrate de tener este método en DBHelper
                    int position = pedidos.indexOf(pedido);
                    notifyItemChanged(position);
                    Toast.makeText(context, "Se actualizo correctamente", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
        }

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
                    pedidos.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, pedidos.size());
                })
                .setNegativeButton("Cancelar", null)
                .show();
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
