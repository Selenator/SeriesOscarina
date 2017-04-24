package com.oveigam.seriesoscarina.entities;

/**
 * Created by Oscarina on 13/04/2017.
 */
public class Serie {
    private int id;
    private String nombre;
    private String localizacion;
    private String audio, subs;
    private boolean aldia, finalizada;

    public Serie() {
    }

    public Serie(int id, String nombre, String localizacion, String audio, String subs, boolean aldia, boolean finalizada) {
        this.id = id;
        this.nombre = nombre;
        this.localizacion = localizacion;
        this.audio = audio;
        this.subs = subs;
        this.aldia = aldia;
        this.finalizada = finalizada;
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

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getSubs() {
        return subs;
    }

    public void setSubs(String subs) {
        this.subs = subs;
    }

    public boolean isAldia() {
        return aldia;
    }

    public void setAldia(boolean aldia) {
        this.aldia = aldia;
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public void setFinalizada(boolean finalizada) {
        this.finalizada = finalizada;
    }
}
