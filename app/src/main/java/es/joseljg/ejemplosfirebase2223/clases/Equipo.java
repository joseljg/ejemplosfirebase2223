package es.joseljg.ejemplosfirebase2223.clases;

import java.io.Serializable;
import java.util.Objects;

public class Equipo implements Serializable {
     private String idEquipo;
     private String nombre;

     //------------------------------------------

    public Equipo(String idEquipo, String nombre) {
        this.idEquipo = idEquipo;
        this.nombre = nombre;
    }
    public Equipo() {
        this.idEquipo = "";
        this.nombre = "";
    }

    public String getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(String idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Equipo)) return false;
        Equipo equipo = (Equipo) o;
        return idEquipo.equals(equipo.idEquipo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEquipo);
    }

    @Override
    public String toString() {
        return "Equipo{" +
                "idEquipo='" + idEquipo + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}

