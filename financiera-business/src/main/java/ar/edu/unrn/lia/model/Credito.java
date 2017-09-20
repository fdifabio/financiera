package ar.edu.unrn.lia.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Lucas on 22/08/2017.
 */
@Entity
@Table(name = "credito")
public class Credito extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private BigDecimal capital;
    private Integer cuotas;
    private BigDecimal interes;
    private Date fecha = new Date();
    private Date fechaVencimiento;
    private Estado estado = Estado.ACTIVO;
    private Cliente cliente;
    private BigDecimal montoCutoas;
    private List<Cuota> listCuotas = new ArrayList<>(0);
    private List<Cuota> coutas;

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public Integer getCuotas() {
        return cuotas;
    }

    public void setCuotas(Integer cuotas) {
        this.cuotas = cuotas;
    }

    public BigDecimal getInteres() {
        return interes;
    }

    public void setInteres(BigDecimal interes) {
        this.interes = interes;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Column(name = "fecha_vencimiento")
    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    @OneToMany
    @Fetch(FetchMode.JOIN)
    public List<Cuota> getCoutas() {
        return coutas;
    }

    public void setCoutas(List<Cuota> coutas) {
        this.coutas = coutas;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Transient
    public BigDecimal getMontoCutoas() {
        return montoCutoas;
    }

    public void setMontoCutoas(BigDecimal montoCutoas) {
        this.montoCutoas = montoCutoas;
    }

    @Transient
    public List<Cuota> getListCuotas() {
        return listCuotas;
    }

    public void setListCuotas(List<Cuota> listCuotas) {
        this.listCuotas = listCuotas;
    }

    @Transient
    public void calcularMontoCuotas() {
       /* Formula Matematica de PAGO
        Si en A1 pones Tasa (tipo de interes del periodo)
        en B1 el nPer (número de Periodos)
        en C1 el Va (Capital inicial)
        Esta fórmula: =(A1(1+A1)^B1)C1/(((1+A1)^B1)-1)
        te dará el mismo resultado que esta otra: =PAGO(A1;B1;-C1)*/
        listCuotas.clear();
//        BigDecimal monto_cuota = (BigDecimal) ((interes * Math.pow(((1 + interes)), cuotas)) * capital / ((Math.pow((1 + interes), cuotas)) - 1));
//        BigDecimal monto_cuota = 1112.57320662644F;
        BigDecimal monto_cuota = (BigDecimal) ((interes.multiply(potencia(((BigDecimal.ONE.add(interes))), BigDecimal.valueOf(cuotas.doubleValue())))).multiply(capital).divide(((potencia((BigDecimal.ONE.add(interes)), BigDecimal.valueOf(cuotas.doubleValue()))).subtract(BigDecimal.ONE)), 4, RoundingMode.HALF_UP));
        montoCutoas = monto_cuota;
        BigDecimal cuotaInteres = BigDecimal.ZERO;
        BigDecimal cuotaCapital = BigDecimal.ZERO;
        BigDecimal saldo = capital;
        for (int i = 1; i <= cuotas; i++) {
            cuotaInteres = saldo.multiply(interes);
            cuotaCapital = montoCutoas.subtract(cuotaInteres);
            saldo = saldo.subtract(cuotaCapital);
            listCuotas.add(new Cuota(redondear(cuotaCapital), redondear(cuotaInteres), redondear(saldo)));
        }
    }

    @Transient
    public BigDecimal totalInteres() {
//        return redondear((BigDecimal) listCuotas.stream().mapToDouble(Cuota::getCuotaInteres).sum());
        return BigDecimal.ZERO;
    }

    @Transient
    public BigDecimal totalCapital() {
//        return redondear((BigDecimal) listCuotas.stream().mapToDouble(Cuota::getCuotaCapital).sum());
        return BigDecimal.ZERO;
    }

    @Transient
    public BigDecimal totalSaldo() {
//        return redondear((BigDecimal) listCuotas.stream().mapToDouble(Cuota::getSaldo).sum());
        return BigDecimal.ZERO;
    }

    private BigDecimal redondear(BigDecimal value) {
//        BigDecimal big = new BigDecimal(value);
//        big = big.setScale(2, RoundingMode.HALF_UP);
//        return big.BigDecimalValue();
        return value.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal potencia(BigDecimal base, BigDecimal potencia) {
        BigDecimal acumulador = base;
        for (int i = 1; i < potencia.intValue(); i++) {
            acumulador = acumulador.multiply(base);
        }
        return acumulador;
    }
}
