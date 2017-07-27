package ar.edu.unrn.lia.model;

import java.io.Serializable;

/**
 * Created by Federico on 17/06/2017.
 */
public enum FormaPagoODS implements Serializable {

    TRANSFERENCIA("Transferencia"), DEPOSITO("Deposito");

    private String descripcion;

    FormaPagoODS(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
