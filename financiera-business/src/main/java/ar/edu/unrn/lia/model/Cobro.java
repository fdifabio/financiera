package ar.edu.unrn.lia.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Lucas on 05/09/2017.
 */
@Entity
@Table(name = "cobro")
public class Cobro extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private BigDecimal monto;
    private Date fecha;
    private String descripcion;
    private Cuota cuota;

    public Cobro(BigDecimal monto, Date fecha, String descripcion) {
        this.monto = monto;
        this.fecha = fecha;
        this.descripcion = descripcion;
    }


    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuota_id")
    public Cuota getCuota() {
        return cuota;
    }

    public void setCuota(Cuota cuota) {
        this.cuota = cuota;
    }


}
