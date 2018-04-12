package ar.edu.unrn.lia.model;

import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    private Date fechaCreacion = new Date();
    private Date fechaInicio = new Date();
    private Date fechaVencimiento;
    private Estado estado = Estado.ACTIVO;
    private Cliente cliente;
    private BigDecimal montoCutoas;
    private BigDecimal saldoCuenta = BigDecimal.ZERO;
    private List<Cuota> listCuotas = new ArrayList<>(0);

    private Garante garante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garante_id")
    public Garante getGarante() {
        return garante;
    }

    public void setGarante(Garante garante) {
        this.garante = garante;
    }

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

    @Column(name = "fecha_creacion")
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Column(name = "fecha_inicio")
    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    @Column(name = "fecha_vencimiento")
    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
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

    @Column(name = "monto_cuotas")
    public BigDecimal getMontoCutoas() {
        return montoCutoas;
    }

    public void setMontoCutoas(BigDecimal montoCutoas) {
        this.montoCutoas = montoCutoas;
    }

    @Column(name = "saldo_cuenta")
    public BigDecimal getSaldoCuenta() {
        return saldoCuenta;
    }

    public void setSaldoCuenta(BigDecimal saldoCuenta) {
        this.saldoCuenta = saldoCuenta;
    }

    @Transient
    public List<Cuota> getListCuotasVencidas() {
        return getListCuotas().stream().filter(c -> c.isVencido()).collect(Collectors.toList());
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "credito", orphanRemoval = true)
    @Fetch(org.hibernate.annotations.FetchMode.JOIN)
    public List<Cuota> getListCuotas() {
        return listCuotas;
    }

    public void setListCuotas(List<Cuota> listCuotas) {
        this.listCuotas = listCuotas;
    }

    @Transient
    public void calcularMontoCuotas() {
        BigDecimal interes = this.interes.divide(BigDecimal.valueOf(100));
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
            Date fechaVencimiento =
                    Date.from(fechaInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusMonths(i).atStartOfDay(ZoneId.systemDefault()).toInstant());
            listCuotas.add(new Cuota(i, this, redondear(cuotaCapital), redondear(cuotaInteres), redondear(saldo), fechaVencimiento));
        }
        fechaVencimiento = listCuotas.stream().reduce((first, second) -> second).map(Cuota::getFechaVencimiento)
                .orElse(null);
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

    @Transient
    public Long totalCuotasPagas() {
        return listCuotas.stream().filter(c -> c.getEstado().equals(Cuota.Estado.SALDADO)).count();
    }

    @Transient
    public Long porcentajeCuotasPagas() {
        //totalCuotasPagas*100/capital
        return totalCuotasPagas() * 100 / cuotas;
    }

    @Transient
    public Long totalCuotasPorPagar() {
        return cuotas - totalCuotasPagas();
    }

    @Transient
    private BigDecimal redondear(BigDecimal value) {
//        BigDecimal big = new BigDecimal(value);
//        big = big.setScale(2, RoundingMode.HALF_UP);
//        return big.BigDecimalValue();
        return value.setScale(2, RoundingMode.HALF_UP);
    }

    @Transient
    private BigDecimal potencia(BigDecimal base, BigDecimal potencia) {
        BigDecimal acumulador = base;
        for (int i = 1; i < potencia.intValue(); i++) {
            acumulador = acumulador.multiply(base);
        }
        return acumulador;
    }

    @Transient
    public boolean isCuotasSaldadas() {
        return getListCuotas().stream().count() == getListCuotas().stream().filter(Cuota::isSaldado).count();
    }

    @Transient
    public boolean existenCuotasVencidas() {
        return getListCuotas().stream().filter(Cuota::isVencido).findAny().isPresent();
    }

    @Transient
    public boolean existenCuotasAdeudadas() {
        return getListCuotas().stream().filter(Cuota::isAdeudado).findAny().isPresent();
    }

    @Transient
    public long cuotasVencidas() {
        return getListCuotas().stream().filter(c -> c.isParcialmenteSaldado() || c.isVencido()).count();
    }

    @Transient
    public void actualizarEstado() {
        if (this.isCuotasSaldadas())
            this.setEstado(Estado.CANCELADO);
        else if (existenCuotasVencidas())
            this.setEstado(Estado.LEGALES);
        else if (existenCuotasAdeudadas())
            this.setEstado(Estado.ACTIVO);

    }

    @Transient
    public boolean isActivo() {
        return estado.equals(Estado.ACTIVO);
    }

    @Transient
    public String listCuotasString() {
        StringBuilder string = new StringBuilder();
        this.listCuotas.stream().forEach(cuota ->
                string.append("Nro: ").append(cuota.getNro().toString()).append(" Monto: ").append(cuota.getSaldoAPagar().setScale(2, BigDecimal.ROUND_CEILING).toString()).append(" - "));
        return string.toString();
    }

    @Transient
    public boolean isCancelado() {
        return estado.equals(Estado.CANCELADO);
    }

    public Credito() {
        super();
    }

    public Credito(Long id) {
        this.setId(id);
    }
}
