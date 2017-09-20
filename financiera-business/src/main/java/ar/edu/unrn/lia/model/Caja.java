package ar.edu.unrn.lia.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
}
