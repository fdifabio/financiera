package ar.edu.unrn.lia.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Lucas on 05/09/2017.
 */
@Entity
@Table(name = "movimiento")
public class Movimiento extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private BigDecimal monto;
    private Date fecha;
    private String descripcion;
    private Tipo tipo;
    private Caja caja;

    public Movimiento() {
    }

    public Movimiento(BigDecimal monto, Date fecha, String descripcion, Tipo tipo) {
        this.monto = monto;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.tipo = tipo;
    }

    public Movimiento(BigDecimal monto, Date fecha, String descripcion, Tipo tipo, Caja caja) {
        this.monto = monto;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.caja = caja;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caja_id")
    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
    }

    public enum Tipo implements Serializable {

        EGRESO("Egreso", "Red", "fa fa-arrow-down"), INGRESO("Ingreso", "Green", "fa fa-arrow-up"),
        CREDITO("Credito Otorgado", "Red", "fa fa-arrow-down"), COBRO("Cobro", "Green", "fa fa-arrow-up");

        private String descripcion;
        private String color;
        private String icon;

        Tipo(String descripcion, String color, String icon) {
            this.descripcion = descripcion;
            this.color = color;
            this.icon = icon;
        }

        public String getTipo() {
            return descripcion;
        }

        public void setTipo(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public String getColor() {
            return color;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getBackgroundColor() {
            return color + "Back";
        }

        @Override
        public String toString() {
            return "Tipo{" +
                    "descripcion='" + descripcion + '\'' +
                    ", color='" + color + '\'' +
                    '}';
        }

    }
}
