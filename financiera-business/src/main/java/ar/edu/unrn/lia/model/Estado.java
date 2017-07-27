package ar.edu.unrn.lia.model;

import java.io.Serializable;

public enum Estado implements Serializable {

    ESTADO_PENDIENTE("Pendiente"), ESTADO_GENERADO("Generada");

    private String descripcion;

    Estado(String descripcion) {
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
    @Override
    public String toString() {
        return this.name();
    }


}
