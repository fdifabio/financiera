package ar.edu.unrn.lia.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Embeddable
public class OrdenDeServicio implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private Date ODSfecha = new Date();
    private FormaPagoODS formapago;
    private double monto;
    private EstadoODS ODSestado = EstadoODS.PENDIENTE;
    private String ODScodigoTX;
    private Date ODSfechaTX;

    public OrdenDeServicio() {
        super();
    }

    public OrdenDeServicio(EstadoODS ODSestado) {
        this.ODSestado = ODSestado;
    }

    public OrdenDeServicio(Date ODSfecha, EstadoODS ODSestado) {
        this.ODSfecha = ODSfecha;
        this.ODSestado = ODSestado;
    }

    public OrdenDeServicio(Date ODSfecha, EstadoODS ODSestado, String ODScodigoTX, Date ODSfechaTX) {
        this.ODSfecha = ODSfecha;
        this.ODSestado = ODSestado;
        this.ODScodigoTX = ODScodigoTX;
        this.ODSfechaTX = ODSfechaTX;
    }

    @Column(name = "ods_fecha_creacion", nullable = false)
    @NotNull
    public Date getODSfecha() {
        return ODSfecha;
    }

    public void setODSfecha(Date ODSfecha) {
        this.ODSfecha = ODSfecha;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "ods_estado", nullable = false)
    @NotNull
    public EstadoODS getODSestado() {
        return ODSestado;
    }

    public void setODSestado(EstadoODS ODSestado) {
        this.ODSestado = ODSestado;
    }

    @Column(name = "ods_codigo_transaccion")
    public String getODScodigoTX() {
        return ODScodigoTX;
    }

    public void setODScodigoTX(String ODScodigoTX) {
        this.ODScodigoTX = ODScodigoTX;
    }

    @Column(name = "ods_fecha_transaccion")
    public Date getODSfechaTX() {
        return ODSfechaTX;
    }

    public void setODSfechaTX(Date ODSfechaTX) {
        this.ODSfechaTX = ODSfechaTX;
    }

    @Column(name = "ods_monto_total_pagado")
    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "ods_forma_pago", nullable = false)
    @NotNull
    public FormaPagoODS getFormapago() {
        return formapago;
    }

    public void setFormapago(FormaPagoODS formapago) {
        this.formapago = formapago;
    }

    @Transient
    public boolean isTransferencia() {
        if (formapago == null)
            return false;
        return formapago.equals(FormaPagoODS.TRANSFERENCIA);
    }

    @Transient
    public boolean isDeposito() {
        if (formapago == null)
            return false;
        return formapago.equals(FormaPagoODS.DEPOSITO);
    }

    @Transient
    public boolean isFacturada() {
        return getODSestado().equals(EstadoODS.FACTURADO);
    }

    @Transient
    public boolean isPagada() {
        return getODSestado().equals(EstadoODS.PAGADO);
    }

    @Transient
    public boolean isPendiente() {
        return getODSestado().equals(EstadoODS.PENDIENTE);
    }

    @Transient
    public void facturar() {
        setODSestado(EstadoODS.FACTURADO);
    }

    @Transient
    public void pagado() {
        setODSestado(EstadoODS.PAGADO);
    }

    public enum EstadoODS implements Serializable {

        PENDIENTE("Pendiente de Pago"), PAGADO("Pagado"), FACTURADO("Facturado");

        private String descripcion;

        EstadoODS(String descripcion) {
            this.descripcion = descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getDescripcion() {
            return descripcion;
        }

        @Override
        public String toString() {
            return this.name();
        }


    }


}
