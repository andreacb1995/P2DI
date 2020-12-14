package com.example.andreacarballidop2di.core;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


@Entity(tableName = "plantas")
public class Planta implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    protected int id;
    @NonNull
    private int imagen;
    @NonNull
    private String nombre;
    @NonNull
    private String descripcion;

    public Planta( int imagen, @NonNull String nombre, @NonNull String descripcion) {
        this.imagen =imagen;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    @NonNull
    public String getNombre() {
        return nombre;
    }

    public void setNombre(@NonNull String nombre) {
        this.nombre = nombre;
    }

    @NonNull
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(@NonNull String descripcion) {
        this.descripcion = descripcion;
    }
}