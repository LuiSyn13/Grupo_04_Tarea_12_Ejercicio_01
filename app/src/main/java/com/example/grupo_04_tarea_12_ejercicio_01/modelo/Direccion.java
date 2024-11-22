package com.example.grupo_04_tarea_12_ejercicio_01.modelo;

public class Direccion {
    private int iddireccion;
    private String numero;
    private String calle;
    private String comuna;
    private String ciudad;
    private int idcliente;

    public Direccion() {
    }

    public Direccion(int iddireccion, String numero, String calle, String comuna, String ciudad) {
        this.iddireccion = iddireccion;
        this.numero = numero;
        this.calle = calle;
        this.comuna = comuna;
        this.ciudad = ciudad;
    }

    public Direccion(String numero, String calle, String comuna, String ciudad, int idcliente) {
        this.numero = numero;
        this.calle = calle;
        this.comuna = comuna;
        this.ciudad = ciudad;
        this.idcliente = idcliente;
    }

    public String getDireccionCompleta() {
        return numero + " - " +  calle + " - " + comuna;  // Ejemplo de dirección completa
    }

    public int getIddireccion() {
        return iddireccion;
    }

    public void setIddireccion(int iddireccion) {
        this.iddireccion = iddireccion;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }
}
