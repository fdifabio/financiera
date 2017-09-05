package ar.edu.unrn.lia.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * Created by Lucas on 22/08/2017.
 */
@Entity
@Table(name = "credito")
public class Credito extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private Float capital;
    private Integer cuotas;
    private Float interes;
    private Date fecha = new Date();
    private Date fechaVencimiento;
    private Estado estado = Estado.ACTIVO;
    private Cliente cliente;

    public Float getCapital() {
        return capital;
    }

    public void setCapital(Float capital) {
        this.capital = capital;
    }

    public Integer getCuotas() {
        return cuotas;
    }

    public void setCuotas(Integer cuotas) {
        this.cuotas = cuotas;
    }

    public Float getInteres() {
        return interes;
    }

    public void setInteres(Float interes) {
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
    public float calcularMontoCuotas() {
       /* Formula Matematica de PAGO
        Si en A1 pones Tasa (tipo de interes del periodo)
        en B1 el nPer (número de Periodos)
        en C1 el Va (Capital inicial)
        Esta fórmula: =(A1(1+A1)^B1)C1/(((1+A1)^B1)-1)
        te dará el mismo resultado que esta otra: =PAGO(A1;B1;-C1)*/

        float monto_cuota = (float) ((interes * Math.pow(((1 + interes)), cuotas)) * capital / ((Math.pow((1 + interes), cuotas)) - 1));
        BigDecimal big = new BigDecimal(monto_cuota);
        big = big.setScale(2, RoundingMode.HALF_UP);
        return big.floatValue();
    }
}
