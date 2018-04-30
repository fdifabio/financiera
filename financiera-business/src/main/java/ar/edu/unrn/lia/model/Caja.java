package ar.edu.unrn.lia.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Lucas on 05/09/2017.
 */
@Entity
@Table(name = "caja")
public class Caja extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private List<Movimiento> movimientos = new ArrayList<>(0);
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

    @OneToMany(mappedBy = "caja", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    @Transient
    public List<Movimiento> getMovimientosOrden() {
        Collections.reverse(movimientos);
        return movimientos;
    }

    @Transient
    public List<Movimiento> getMovimientosOrdenCobro() {
        Collections.reverse(movimientos);
        return movimientos.stream().filter(m -> m.getTipo().equals(Movimiento.Tipo.COBRO)).collect(Collectors.toList());
    }

    @Transient
    public List<Movimiento> getMovimientosOrdenCredito() {
        Collections.reverse(movimientos);
        return movimientos.stream().filter(m -> m.getTipo().equals(Movimiento.Tipo.CREDITO)).collect(Collectors.toList());
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }

    @Transient
    public double saldo() {
        BigDecimal monto = BigDecimal.ZERO;
        if (this != null) {
            for (Movimiento movimiento : getMovimientos()
                    ) {
                if (movimiento.getTipo() == Movimiento.Tipo.INGRESO || movimiento.getTipo() == Movimiento.Tipo.COBRO)
                    monto = monto.add(movimiento.getMonto());
                else
                    monto = monto.subtract(movimiento.getMonto());

            }
        }
        return monto.doubleValue();
    }

    @Transient
    public double saldoInicial() {
        BigDecimal monto = BigDecimal.ZERO;
        if (this != null) {
            for (Movimiento movimiento : getMovimientos()
                    ) {
                if (movimiento.getTipo() == Movimiento.Tipo.INGRESO)
                    monto = monto.add(movimiento.getMonto());

            }
        }
        return monto.doubleValue();
    }
    @Transient
    public double saldoCobros() {
        BigDecimal monto = BigDecimal.ZERO;
        if (this != null) {
            for (Movimiento movimiento : getMovimientos()
                    ) {
                if (movimiento.getTipo() == Movimiento.Tipo.COBRO)
                    monto = monto.add(movimiento.getMonto());

            }
        }
        return monto.doubleValue();
    }
    @Transient
    public double saldoCreditos() {
        BigDecimal monto = BigDecimal.ZERO;
        if (this != null) {
            for (Movimiento movimiento : getMovimientos()
                    ) {
                if (movimiento.getTipo() == Movimiento.Tipo.CREDITO)
                    monto = monto.add(movimiento.getMonto());

            }
        }
        return 0 - monto.doubleValue();
    }

    @Transient
    public double saldoGastos() {
        BigDecimal monto = BigDecimal.ZERO;
        if (this != null) {
            for (Movimiento movimiento : getMovimientos()
                    ) {
                if (movimiento.getTipo() == Movimiento.Tipo.EGRESO)
                    monto = monto.add(movimiento.getMonto());

            }
        }
        return 0 - monto.doubleValue();
    }
    public String date() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return simpleDateFormat.format(this.fechaApertura);
    }

    @Transient
    public boolean habilitada() {
        return getFechaCierre() == null;
    }
}
