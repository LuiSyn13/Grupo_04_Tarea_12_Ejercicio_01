package com.example.grupo_04_tarea_12_ejercicio_01.modelo;

public class Cliente {
    private int idcliente;
    private String Nombre;

    public Cliente() {
    }

    public Cliente(int idcliente, String nombre) {
        this.idcliente = idcliente;
        this.Nombre = nombre;
    }

    public Cliente(String nombre) {
        Nombre = nombre;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
