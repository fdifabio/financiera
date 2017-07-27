package ar.edu.unrn.lia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Federico on 14/06/2017.
 */
@Entity
@Table(name = "tasa")
public class Tasa extends BaseEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private String descripcion;
    private Double costo;
    private Double costoAdicional;

    public Tasa() {
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    @Column(name = "costo_adicional")
    public Double getCostoAdicional() {
        return costoAdicional;
    }

    public void setCostoAdicional(Double costoAdicional) {
        this.costoAdicional = costoAdicional;
    }
}
