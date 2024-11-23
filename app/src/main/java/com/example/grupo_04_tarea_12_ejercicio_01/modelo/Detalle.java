package com.example.grupo_04_tarea_12_ejercicio_01.modelo;

public class Detalle {
    private int idarticulo;
    private int idpedido;
    private int cantidad;

    public Detalle() {}

    public Detalle(int idarticulo, int idpedido, int cantidad) {
        this.idarticulo = idarticulo;
        this.idpedido = idpedido;
        this.cantidad = cantidad;
    }

    public int getIdarticulo() {
        return idarticulo;
    }

    public void setIdarticulo(int idarticulo) {
        this.idarticulo = idarticulo;
    }

    public int getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(int idpedido) {
        this.idpedido = idpedido;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
