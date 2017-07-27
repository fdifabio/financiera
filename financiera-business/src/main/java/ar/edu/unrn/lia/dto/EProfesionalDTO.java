package ar.edu.unrn.lia.dto;

import ar.edu.unrn.lia.model.Estado;
import ar.edu.unrn.lia.model.InmuebleTipo;
import ar.edu.unrn.lia.model.OrdenDeServicio;

import java.util.Date;


public class EProfesionalDTO extends AbstractDTO<Long> implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private String nombre;
    private Date fecha;
    private PerfilAGRDTO perfilAGR;
    private ComitenteDTO comitente;
    private InmuebleDTO inmueble;
    private OrdenDeServicio.EstadoODS estadoODS;
    private Estado estadoEP;
    private String tasaDescripcion;

    private int tasaUnidades;
    private double tasaCosto;
    private double tasaCostoAdicional;


    public EProfesionalDTO() {
    }

    public EProfesionalDTO(Long id) {
        this.id = id;
    }


    public EProfesionalDTO(Long id, String nombre, Date fecha, Long idAGR,
                           String nombreAGR, String apellidoAGR, String comitente,
                           String responsable, String email, InmuebleTipo InmuebleTipo,
                           Estado estadoEP, OrdenDeServicio.EstadoODS estado, String tasaDescripcion,
                           double tasaCosto, int tasaUnidades, double tasaCostoAdicional) {

        //     fromEProfesional.get("tasaDescripcion"),

        this(id);
        this.nombre = nombre;
        this.fecha = fecha;
        this.perfilAGR = new PerfilAGRDTO(idAGR, nombreAGR, apellidoAGR);
        ResponsableDTO responsableDTO = new ResponsableDTO(responsable, email);
        this.comitente = new ComitenteDTO(comitente, responsableDTO);
        this.inmueble = new InmuebleDTO(InmuebleTipo);
        this.estadoODS = estado;
        this.estadoEP = estadoEP;
        this.tasaDescripcion = tasaDescripcion;
        this.tasaCosto = tasaCosto;
        this.tasaCostoAdicional = tasaCostoAdicional;
        this.tasaUnidades = tasaUnidades;
    }

    /**
     * Indica si la EP aun esta pendiente de ser generada
     * @return
     */
    public boolean isPendiente() {

        return this.estadoEP.equals(Estado.ESTADO_PENDIENTE);
    }

    /**
     * Indica si la EP fue generada
     * @return
     */
    public boolean isGenerada() {

        return this.estadoEP.equals(Estado.ESTADO_GENERADO);
    }

    /**
     * Indica si la EP esta pendiente de pago
     * @return
     */
    public boolean isPendienteDePago() {

        return this.estadoODS.equals( OrdenDeServicio.EstadoODS.PENDIENTE);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public InmuebleDTO getInmueble() {
        return inmueble;
    }

    public String getStringInmuebleTipo(){
        return inmueble.getTipo();
    }

    public String getEstadoEP() {
        return estadoEP.getDescripcion();
    }


    public String getEstadoODS() {
        return estadoODS.getDescripcion();
    }

    public String getTasaDescripcion() {
        return tasaDescripcion;
    }

    public int getTasaUnidades() {
        return tasaUnidades;
    }

    public double getTasaCosto() {
        return tasaCosto;
    }

    public double getTasaCostoAdicional() {
        return tasaCostoAdicional;
    }

    public double getCalcularMonto() {
        return this.getTasaCosto() + (this.getTasaUnidades() * this.getTasaCostoAdicional());
    }

}