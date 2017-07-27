package ar.edu.unrn.lia.dto;

import ar.edu.unrn.lia.model.Estado;
import ar.edu.unrn.lia.model.FormaPagoODS;
import ar.edu.unrn.lia.model.InmuebleTipo;
import ar.edu.unrn.lia.model.OrdenDeServicio;

import java.util.Date;


public class OrdenDeServicioDTO extends AbstractDTO<Long> implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private String nombre;
    //fecha de Pago
    private Date ODSfechaTX;
    //codigo de Pago tx
    private String ODScodigoTX;
    private String formaDePago;
    private PerfilAGRDTO perfilAGR;
    private ComitenteDTO comitente;

    //estado y monto de la ODS
    private String ODSestado;
    private double monto;
    private String tasaDescripcion;

    public OrdenDeServicioDTO() {
    }

    public OrdenDeServicioDTO(Long id) {
        this.id = id;
    }

    public OrdenDeServicioDTO(Long id,
                              Date ODSfechaTX,
                              Long idAGR, String nombreAGR,
                              String apellidoAGR,
                              String matriculaAGR,
                              String nombreComitente,
                              boolean isEmpresa,
                              String cuitComitente,
                              double montoODS,
                              FormaPagoODS formaDePago,
                              String ODScodigoTX,
                              OrdenDeServicio.EstadoODS estado,
                              String tasaDescripcion) {
        this.id = id;
        this.nombre = nombre;
        this.ODSfechaTX = ODSfechaTX;
        this.perfilAGR = new PerfilAGRDTO(idAGR, nombreAGR, apellidoAGR, matriculaAGR);
        this.comitente = new ComitenteDTO(nombreComitente, cuitComitente, isEmpresa);
        this.monto = montoODS;
        this.formaDePago = formaDePago.getDescripcion();
        this.ODScodigoTX = ODScodigoTX;
        this.ODSestado = estado.getDescripcion();
        this.tasaDescripcion = tasaDescripcion;
    }

    public OrdenDeServicioDTO(Long id,
                              Date ODSfechaTX,
                              Long idAGR, String nombreAGR,
                              String apellidoAGR,
                              String matriculaAGR,
                              String cuitAGR,
                              String nombreComitente,
                              boolean isEmpresa,
                              String cuitComitente,
                              double montoODS,
                              FormaPagoODS formaDePago,
                              String ODScodigoTX,
                              OrdenDeServicio.EstadoODS estado,
                              String tasaDescripcion) {
        this.id = id;
        this.nombre = nombre;
        this.ODSfechaTX = ODSfechaTX;
        this.perfilAGR = new PerfilAGRDTO(idAGR, nombreAGR, apellidoAGR, matriculaAGR, cuitAGR);
        this.comitente = new ComitenteDTO(nombreComitente, cuitComitente, isEmpresa);
        this.monto = montoODS;
        this.formaDePago = formaDePago!=null? formaDePago.getDescripcion(): null;
        this.ODScodigoTX = ODScodigoTX;
        this.ODSestado = estado.getDescripcion();
        this.tasaDescripcion = tasaDescripcion;
    }


    public boolean isFacturada() {
        return this.getODSestado().equalsIgnoreCase(OrdenDeServicio.EstadoODS.FACTURADO.getDescripcion());
    }

    public boolean isPagada() {
        return this.getODSestado().equalsIgnoreCase(OrdenDeServicio.EstadoODS.PAGADO.getDescripcion());
    }

    /**
     * Retorne si el comitente es empresa, caso contrario es Particular.
     *
     * @return
     */
    public boolean isEmpresa() {
        return this.getComitente().isEmpresa();
    }

    /**
     * Retorne si el comitente es particular, caso contrario es Empresa.
     *
     * @return
     */
    public boolean isParticular() {
        return !this.getComitente().isEmpresa();
    }

    public String getODScodigoTX() {
        return ODScodigoTX;
    }

    public void setODScodigoTX(String ODScodigoTX) {
        this.ODScodigoTX = ODScodigoTX;
    }

    public String getFormaDePago() {
        return formaDePago;
    }

    public void setFormaDePago(String formaDePago) {
        this.formaDePago = formaDePago;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public PerfilAGRDTO getPerfilAGR() {
        return perfilAGR;
    }

    public void setPerfilAGR(PerfilAGRDTO perfilAGR) {
        this.perfilAGR = perfilAGR;
    }

    public ComitenteDTO getComitente() {
        return comitente;
    }

    public void setComitente(ComitenteDTO comitente) {
        this.comitente = comitente;
    }

    public String getODSestado() {
        return ODSestado;
    }

    public void setODSestado(String ODSestado) {
        this.ODSestado = ODSestado;
    }

    public Date getODSfechaTX() {
        return ODSfechaTX;
    }

    public void setODSfechaTX(Date ODSfechaTX) {
        this.ODSfechaTX = ODSfechaTX;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getTasaDescripcion() {
        return tasaDescripcion;
    }

    public void setTasaDescripcion(String tasaDescripcion) {
        this.tasaDescripcion = tasaDescripcion;
    }
}