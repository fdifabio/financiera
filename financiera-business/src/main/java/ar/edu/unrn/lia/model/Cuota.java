package ar.edu.unrn.lia.model;

import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * Created by Lucas on 05/09/2017.
 */
@Entity
@Table(name = "cuota")
public class Cuota extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private Integer nro;
    private BigDecimal cuotaCapital;
    private BigDecimal cuotaInteres;
    private BigDecimal saldo;
    private BigDecimal saldoAPagar;// En un principio va ser igual al monto de la cuota
    private Date fechaVencimiento;
    private Date fechaPago;

    private Estado estado = Estado.ADEUDADO;
    private List<Cobro> cobros;

    //TODO: Ver de parametrizarlos!!
    public static final BigDecimal INTERES_VENCIDO = new BigDecimal(0.33);
    private BigDecimal interesDescuento = BigDecimal.ZERO;//Se utiliza cuando la cuota se paga por adelantado
    private BigDecimal interesVencido = INTERES_VENCIDO;//Se utiliza cuando la cuota esta vencida

    private Credito credito;

    /*Transients*/
    private Estado estadoAnterior;
    private BigDecimal montoAPagar;
    private BigDecimal saldoAPagarAnterior;

    public Cuota() {
        super();
    }

    public Cuota(Integer nro, Credito credito, BigDecimal cuotaCapital, BigDecimal cuotaInteres, BigDecimal saldo, Date fechaVencimiento) {
        super();
        this.nro = nro;
        this.cuotaCapital = cuotaCapital;
        this.cuotaInteres = cuotaInteres;
        this.saldo = saldo;
        this.saldoAPagar = credito.getMontoCutoas();
        this.credito = credito;
        this.fechaVencimiento = fechaVencimiento;
    }

    public Integer getNro() {
        return nro;
    }

    public void setNro(Integer nro) {
        this.nro = nro;
    }

    @Column(name = "cuota_capital")
    public BigDecimal getCuotaCapital() {
        return cuotaCapital;
    }

    public void setCuotaCapital(BigDecimal cuotaCapital) {
        this.cuotaCapital = cuotaCapital;
    }

    @Column(name = "cuota_interes")
    public BigDecimal getCuotaInteres() {
        return cuotaInteres;
    }

    public void setCuotaInteres(BigDecimal cuotaInteres) {
        this.cuotaInteres = cuotaInteres;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    @Column(name = "fecha_vencimiento")
    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    @Column(name = "fecha_pago")
    public Date getFechaPago() {
        return fechaPago;
    }


    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credito_id")
    public Credito getCredito() {
        return credito;
    }

    public void setCredito(Credito credito) {
        this.credito = credito;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cuota", fetch = FetchType.EAGER)
    @Fetch(org.hibernate.annotations.FetchMode.JOIN)
    public List<Cobro> getCobros() {
        return cobros;
    }

    public void setCobros(List<Cobro> cobros) {
        this.cobros = cobros;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    public Estado getEstado() {

        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Column(name = "interes_vencido")
    public BigDecimal getInteresVencido() {
        return interesVencido;
    }

    public void setInteresVencido(BigDecimal interesVencido) {
        this.interesVencido = interesVencido;
    }

    @Column(name = "interes_descuento")
    public BigDecimal getInteresDescuento() {
        return interesDescuento;
    }

    public void setInteresDescuento(BigDecimal interesDescuento) {
        this.interesDescuento = interesDescuento;
    }

    @Column(name = "saldo_pagar")
    public BigDecimal getSaldoAPagar() {
        return saldoAPagar;
    }

    public void setSaldoAPagar(BigDecimal saldoAPagar) {
        this.saldoAPagar = saldoAPagar;
    }

    @Transient
    public BigDecimal montoTotal() {
        if (this.getEstado().equals(Estado.VENCIDO) || this.getEstado().equals(Estado.PARCIALMENTE_SALDADO))
            return saldoAPagar.add(calcularCuotaInteresVencido());
        return saldoAPagar.subtract(calcularCuotaInteresDescuento());
    }

    @Transient
    public BigDecimal monto() {
        BigDecimal monto = null;
        if (this.getEstadoAnterior().equals(Estado.VENCIDO) || this.getEstadoAnterior().equals(Estado.PARCIALMENTE_SALDADO)) {
            if (credito.getSaldoCuenta() != null) {
                monto = saldoAPagarAnterior.add(calcularCuotaInteresVencido());
                monto=monto.subtract(credito.getSaldoCuenta());
                //credito.setSaldoCuenta(BigDecimal.ZERO);
            }
//            monto = saldoAPagarAnterior.add(calcularCuotaInteresVencido());

        } else
            monto = saldoAPagarAnterior.subtract(calcularCuotaInteresDescuento());
        return redondear(monto);
    }


    @Transient
    public BigDecimal calcularCuotaInteresVencido() {
        //TODO: SALDO-> Ver de guardarlo cuando se genera el cobro
        if (saldoAPagarAnterior == null){
            saldoAPagarAnterior = saldoAPagar;}
        BigDecimal value = saldoAPagarAnterior.multiply(getInteresVencido()).divide(new BigDecimal(100));
        value = value.multiply(new BigDecimal(diasVencidos()));
        return value;
    }

    @Transient
    private BigDecimal calcularCuotaInteresDescuento() {
        BigDecimal value = credito.getMontoCutoas().multiply(interesDescuento).divide(new BigDecimal(100));
        return value;
    }

    @Transient
    private BigDecimal calcularCobros() {
        return cobros.stream().map(Cobro::getMonto).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transient
    public long diasVencidos() {
        LocalDate date = null;
        if (fechaPago != null) {
            if (fechaPago.after(fechaVencimiento)) {
                date = fechaPago.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            } else {
                date = fechaVencimiento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }
        } else {
            date = fechaVencimiento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        long dias = Duration.between(date.atStartOfDay(), LocalDate.now().atStartOfDay()).toDays();
        return dias > 0 ? dias : 0;
    }

    @Transient
    public boolean isVencido() {
        return this.estado.equals(Estado.VENCIDO);
    }

    @Transient
    public boolean isAdeudado() {
        return this.estado.equals(Estado.ADEUDADO);
    }

    @Transient
    public boolean isParcialmenteSaldado() {
        return this.estado.equals(Estado.PARCIALMENTE_SALDADO);
    }

    @Transient
    public boolean isEstadoAnteriorVencido() {
        return this.estadoAnterior.equals(Estado.VENCIDO);
    }

    @Transient
    public boolean isEstadoAnteriorParcialmenteSaldado() {
        return this.estadoAnterior.equals(Estado.PARCIALMENTE_SALDADO);
    }

    @Transient
    public boolean isSaldado() {
        return this.estado.equals(Estado.SALDADO);
    }

    @Transient
    public Estado getEstadoAnterior() {
        if (estadoAnterior == null) estadoAnterior = estado;
        return estadoAnterior;
    }

    public void setEstadoAnterior(Estado estadoAnterior) {
        this.estadoAnterior = estadoAnterior;
    }

    @Transient
    public BigDecimal getMontoAPagar() {
        return redondear(montoAPagar);
    }

    public void setMontoAPagar(BigDecimal montoAPagar) {
        this.montoAPagar = montoAPagar;
    }

    @Transient
    public BigDecimal getSaldoAPagarAnterior() {
        return saldoAPagarAnterior;
    }

    @Transient
    private BigDecimal redondear(BigDecimal value) {
//        BigDecimal big = new BigDecimal(value);
//        big = big.setScale(2, RoundingMode.HALF_UP);
//        return big.BigDecimalValue();
        return value.setScale(2, RoundingMode.HALF_UP);
    }

    public void setSaldoAPagarAnterior(BigDecimal saldoAPagarAnterior) {
        this.saldoAPagarAnterior = saldoAPagarAnterior;
    }

    public enum Estado implements Serializable {

        ADEUDADO("Adeudado", "Orange"), VENCIDO("Vencido", "Red"), SALDADO("Saldado", "Green"), PARCIALMENTE_SALDADO("Parcialmente saldado", "SoftRed");

        private String descripcion;
        private String color;

        Estado(String descripcion, String color) {
            this.descripcion = descripcion;
            this.color = color;
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

        public String getColor() {
            return color;
        }

        public String getBackgroundColor() {
            return color + "Back";
        }

        @Override
        public String toString() {
            return "Estado{" +
                    "descripcion='" + descripcion + '\'' +
                    ", color='" + color + '\'' +
                    '}';
        }

    }
}
