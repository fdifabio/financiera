package ar.edu.unrn.lia.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
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
    private Date fechaVencimiento;
    private Date fechaCierre;
    private Estado estado = Estado.ADEUDADO;
    private List<Cobro> cobros;

    //TODO: Ver de parametrizarlos!!
    private BigDecimal interesDescuento = BigDecimal.ZERO;//Se utiliza cuando la cuota se paga por adelantado
    private BigDecimal interesVencido = BigDecimal.ZERO;//Se utiliza cuando la cuota esta vencida


    private Credito credito;

    public Cuota() {
        super();
    }

    public Cuota(Integer nro, Credito credito, BigDecimal cuotaCapital, BigDecimal cuotaInteres, BigDecimal saldo, Date fechaVencimiento) {
        super();
        this.nro = nro;
        this.cuotaCapital = cuotaCapital;
        this.cuotaInteres = cuotaInteres;
        this.saldo = saldo;
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

    @Column(name = "fecha_cierre")
    public Date getFechaCierre() {
        return fechaCierre;
    }


    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credito_id")
    public Credito getCredito() {
        return credito;
    }

    public void setCredito(Credito credito) {
        this.credito = credito;
    }

    @OneToMany(mappedBy = "cuota")
    @Fetch(FetchMode.JOIN)
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

    @Transient
    public BigDecimal monto() {
        //TODO: Ver si dejo esta logica aca
        if (diasVencidos() > 0 && !this.estado.equals(Estado.SALDADO)) {
            this.setEstado(Estado.VENCIDO);
            this.interesVencido = new BigDecimal(0.33);
            return credito.getMontoCutoas().add(calcularCuotaInteresVencido());
        }  //TODO: Ver esta logica
        return credito.getMontoCutoas().subtract(calcularCuotaInteresDescuento());
//        return credito.getMontoCutoas();
    }

    @Transient
    private BigDecimal calcularCuotaInteresVencido() {
        BigDecimal value = credito.getMontoCutoas().multiply(interesVencido).divide(new BigDecimal(100));
        value = value.multiply(new BigDecimal(diasVencidos()));
        return value;
    }

    @Transient
    private BigDecimal calcularCuotaInteresDescuento() {
        BigDecimal value = credito.getMontoCutoas().multiply(interesDescuento).divide(new BigDecimal(100));
        return value;
    }

    @Transient
    public long diasVencidos() {
        LocalDate date = fechaVencimiento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long dias = Duration.between(date.atStartOfDay(), LocalDate.now().atStartOfDay()).toDays();
        return dias > 0 ? dias : 0;
    }

    @Transient
    public boolean isVencido() {
        return this.estado.equals(Estado.VENCIDO);
    }

    @Transient
    public boolean isParcialmenteSaldado() {
        return this.estado.equals(Estado.PARCIALMENTE_SALDADO);
    }

    @Transient
    public boolean isSaldado() {
        return this.estado.equals(Estado.SALDADO);
    }

    public enum Estado implements Serializable {

        ADEUDADO("Adeudado", "Orange"), VENCIDO("Vencido", "Red"), SALDADO("Saldado", "Green"), PARCIALMENTE_SALDADO("Paracialmente saldado", "Orange");

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
