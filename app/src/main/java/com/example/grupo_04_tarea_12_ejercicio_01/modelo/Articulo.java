package com.example.grupo_04_tarea_12_ejercicio_01.modelo;

public class Articulo {
    private int idarticulo;
    private String descripcion;
    private int stock;

    public Articulo() {
    }

    public Articulo(int idarticulo, String descripcion, int stock) {
        this.idarticulo = idarticulo;
        this.descripcion = descripcion;
        this.stock = stock;
    }

    public Articulo(String descripcion, int stock) {
        this.descripcion = descripcion;
        this.stock = stock;
    }

    public int getIdarticulo() {
        return idarticulo;
    }

    public void setIdarticulo(int idarticulo) {
        this.idarticulo = idarticulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
