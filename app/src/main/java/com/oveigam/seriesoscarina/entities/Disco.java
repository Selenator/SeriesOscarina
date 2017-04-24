package com.oveigam.seriesoscarina.entities;

/**
 * Created by Oscarina on 13/04/2017.
 */
public class Disco {
    private int id;
    private String nombre;
    private float capacidad;

    public Disco() {
    }

    public Disco(int id, String nombre, float capacidad) {
        this.id = id;
        this.nombre = nombre;
        this.capacidad = capacidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(float capacidad) {
        this.capacidad = capacidad;
    }
}
