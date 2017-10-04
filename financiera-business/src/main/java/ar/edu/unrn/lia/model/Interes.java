package ar.edu.unrn.lia.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Created by Lucas on 22/08/2017.
 */
@Entity
@Table(name = "interes")
public class Interes extends BaseEntity {
    private BigDecimal valor;
    private Integer orden;// esto representa el orden en que se muestran en la vista

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }
}
