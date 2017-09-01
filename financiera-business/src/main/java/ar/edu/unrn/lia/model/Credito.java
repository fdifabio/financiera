package ar.edu.unrn.lia.model;

import javax.persistence.*;
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
    private Date fecha=new Date();
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
}
