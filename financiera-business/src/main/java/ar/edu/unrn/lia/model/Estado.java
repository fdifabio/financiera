package ar.edu.unrn.lia.model;

import java.io.Serializable;

public enum Estado implements Serializable {

    ACTIVO("Activo", "BlueBack"), CANCELADO("Cancelado", "GreenBack"), LEGALES("Legales", "RedBack");

    private String descripcion;
    private String color;

    Estado(String descripcion, String color) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return descripcion;
    }

    public void setEstado(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getColor() {
        return color;
    }


    @Override
    public String toString() {
        return "Estado{" +
                "descripcion='" + descripcion + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
