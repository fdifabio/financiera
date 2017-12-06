package ar.edu.unrn.lia.model;

import java.io.Serializable;

public enum Estado implements Serializable {

    ACTIVO("Activo", "Blue"), CANCELADO("Cancelado", "Green"), LEGALES("Legales", "Red");

    private String descripcion;
    private String color;

    Estado(String descripcion) {
        this.descripcion = descripcion;
    }

    Estado(String descripcion, String color) {
        this.descripcion = descripcion;
        this.color = color;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getColor() {
        return color;
    }

    public String getBackgroundColor() {
        return color + "Back";
    }
}
