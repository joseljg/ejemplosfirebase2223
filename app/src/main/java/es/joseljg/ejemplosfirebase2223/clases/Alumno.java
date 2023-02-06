package es.joseljg.ejemplosfirebase2223.clases;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Objects;

public class Alumno implements Serializable {
    private String nombre;
    private String curso;


    public Alumno(String nombre, String curso) {
        this.nombre = nombre;
        this.curso = curso;
    }

    public Alumno() {
        this.nombre = "sin nombre";
        this.curso = "sin curso";
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Alumno)) return false;
        Alumno alumno = (Alumno) o;
        return nombre.equals(alumno.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }

    @Override
    public String toString() {
        return "Alumno{" +
                "nombre='" + nombre + '\'' +
                ", curso='" + curso + '\'' +
                '}';
    }
}
