package com.example.grupo_04_tarea_12_ejercicio_01.modelo;

import java.time.LocalDateTime;

public class Pedido {
    private int idpedido;
    private int idcliente;
    private LocalDateTime fecha_envio;
    private int iddireccion;

    public Pedido() {
    }

    public Pedido(int idpedido, int idcliente, LocalDateTime fecha_envio, int iddireccion) {
        this.idpedido = idpedido;
        this.idcliente = idcliente;
        this.fecha_envio = fecha_envio;
        this.iddireccion = iddireccion;
    }

    public Pedido(int idcliente, LocalDateTime fecha_envio, int iddireccion) {
        this.idcliente = idcliente;
        this.fecha_envio = fecha_envio;
        this.iddireccion = iddireccion;
    }

    public int getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(int idpedido) {
        this.idpedido = idpedido;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public LocalDateTime getFecha_envio() {
        return fecha_envio;
    }

    public void setFecha_envio(LocalDateTime fecha_envio) {
        this.fecha_envio = fecha_envio;
    }

    public int getIddireccion() {
        return iddireccion;
    }

    public void setIddireccion(int iddireccion) {
        this.iddireccion = iddireccion;
    }
}
