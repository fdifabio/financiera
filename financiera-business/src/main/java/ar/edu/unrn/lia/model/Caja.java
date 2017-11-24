package ar.edu.unrn.lia.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Lucas on 05/09/2017.
 */
@Entity
@Table(name = "caja")
public class Caja extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private List<Movimiento> movimientos;
    private Date fechaApertura;
    private Date fechaCierre;

    public Caja() {
    }

    public Caja(List<Movimiento> movimientos, Date fechaApertura, Date fechaCierre) {
        this.movimientos = movimientos;
        this.fechaApertura = fechaApertura;
        this.fechaCierre = fechaCierre;
    }

    public Caja(Date fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    @Column(name = "fecha_apertura")
    public Date getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    @Column(name = "fecha_cierre")
    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    @OneToMany(mappedBy = "caja")
    @Fetch(FetchMode.JOIN)
    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }

    @Transient
    public double saldo() {
        BigDecimal monto = BigDecimal.ZERO;
        if(this != null){
        for (Movimiento movimiento : getMovimientos()
                ) {
            if (movimiento.getTipo() == Movimiento.Tipo.INGRESO)
                monto = monto.add(movimiento.getMonto());
            else
                monto = monto.subtract(movimiento.getMonto());

        }}
        return monto.doubleValue();
    }

    @Transient
    public boolean habilitada() {
        return getFechaCierre() == null;
    }
}
