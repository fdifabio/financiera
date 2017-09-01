package ar.edu.unrn.lia.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Lucas on 22/08/2017.
 */
@Entity
@Table(name = "interes")
public class Interes extends BaseEntity {
    private Float valor;
    private Integer orden;// esto representa el orden en que se muestran en la vista

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }
}
