package ar.edu.unrn.lia.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "eprofesional")
public class EProfesional extends BaseEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private String nombre;
    private Date fecha;
    private String nomenclatura;
    private PerfilAGR perfilAGR;
    private Comitente comitente;
    private Inmueble inmueble = new Inmueble();
    private Estado estado = Estado.ESTADO_PENDIENTE;
    private String tasaDescripcion;
    private int tasaUnidades;
    private double tasaCosto;
    private double tasaCostoAdicional;
    @Embedded
    private Coordenada coordenada;
    @Embedded
    private OrdenDeServicio ordenDeServicio = new OrdenDeServicio();

    @Transient
    private double calcularMontoTasasYadicioanles;

    @Transient
    private double montoGEO;

    @PostLoad
    private void onLoad() {
        this.calcularMontoTasasYadicioanles = (this.getTasaCosto() + (this.getTasaUnidades() * this.getTasaCostoAdicional()));
    }

    public EProfesional() {
    }

    public EProfesional(Long id) {
        this.id = id;

    }

    public EProfesional(Long id, OrdenDeServicio ordenDeServicio) {
        this.id = id;
        this.ordenDeServicio = ordenDeServicio;
    }

    @Transient
    public double getMontoGEO() {
        return montoGEO;
    }

    public void setMontoGEO(double montoGEO) {
        this.montoGEO = montoGEO;
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

    public String getNomenclatura() {
        return nomenclatura;
    }

    public void setNomenclatura(String nomenclatura) {
        this.nomenclatura = nomenclatura;
    }

    @ManyToOne
    @JoinColumn(nullable = true, name = "perfil_agrimensor_id")
    public PerfilAGR getPerfilAGR() {
        return perfilAGR;
    }

    public void setPerfilAGR(PerfilAGR perfilAGR) {
        this.perfilAGR = perfilAGR;
    }


    @ManyToOne
    @Cascade({CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "comitente_id")
    public Comitente getComitente() {
        return comitente;
    }

    public void setComitente(Comitente comitente) {
        this.comitente = comitente;
    }


    @Column(name = "tasa_descripcion", nullable = false)
    @NotNull
    public String getTasaDescripcion() {
        return tasaDescripcion;
    }

    public void setTasaDescripcion(String tasaDescripcion) {
        this.tasaDescripcion = tasaDescripcion;
    }

    @Column(name = "tasa_costo_adicional", nullable = false)
    @NotNull
    public double getTasaCostoAdicional() {
        return tasaCostoAdicional;
    }

    public void setTasaCostoAdicional(double tasaCostoAdicional) {
        this.tasaCostoAdicional = tasaCostoAdicional;
    }

    @Column(name = "tasa_costo", nullable = false)
    @NotNull
    public double getTasaCosto() {
        return tasaCosto;
    }

    public void setTasaCosto(double tasaCosto) {
        this.tasaCosto = tasaCosto;
    }

    @Column(name = "tasa_unidades", nullable = false)
    @NotNull
    public int getTasaUnidades() {
        return tasaUnidades;
    }

    public void setTasaUnidades(int tasaUnidades) {
        this.tasaUnidades = tasaUnidades;
    }

    @ManyToOne
    @Cascade({CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "inmueble_id")
    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = true)
    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Coordenada getCoordenada() {
        return coordenada;
    }

    public void setCoordenada(Coordenada coordenada) {
        this.coordenada = coordenada;
    }

    public OrdenDeServicio getOrdenDeServicio() {
        return ordenDeServicio;
    }

    public void setOrdenDeServicio(OrdenDeServicio ordenDeServicio) {
        this.ordenDeServicio = ordenDeServicio;
    }


    @Transient
    public boolean isInmuebleRural() {
       return this.inmueble.isRural();
    }

    @Transient
    public boolean isInmuebleUrbanoSubUrbano() {
        return this.inmueble.isUrbanoSubUrbano();
    }


    @Transient
    public boolean isInmuebleOtro() {
        return this.inmueble.isOtro();
    }

    @Transient
    public boolean isGenerada() {
        return estado.equals(Estado.ESTADO_GENERADO);
    }

    @Transient
    public boolean isPendiente() {
        return estado.equals(Estado.ESTADO_PENDIENTE);
    }

    @Transient
    public void facturar() {
        this.ordenDeServicio.facturar();
    }

    @Transient
    public boolean isTransferencia() {
        return this.ordenDeServicio.isTransferencia();
    }

    @Transient
    public boolean isDeposito() {
        return this.ordenDeServicio.isDeposito();
    }

    @Transient
    public OrdenDeServicio.EstadoODS getEstadoODS() {
        return this.ordenDeServicio.getODSestado();
    }

    @Transient
    public String getEstadoODSDescripcion() {
        return this.ordenDeServicio.getODSestado().getDescripcion();
    }

    @Transient
    public boolean isPendienteDePago() {
        return this.ordenDeServicio.isPendiente();
    }

    @Transient
    public boolean isPagada() {
        return this.ordenDeServicio.isPagada();
    }

    @Transient
    public InmuebleTipo getInmuebleTipo() {
        return this.inmueble.getTipo();
    }

    @Transient
    public String getStringInmuebleTipo() {
        return this.inmueble.getTipo().getTipo();
    }
    @Transient
    public void setTasa(Tasa tasa) {
        this.setTasaDescripcion(tasa.getDescripcion());
        this.setTasaCosto(tasa.getCosto());
        this.setTasaCostoAdicional(tasa.getCostoAdicional());
    }

    @Transient
    public void updateComprobante() {
        this.getOrdenDeServicio().setODSfechaTX(new Date());
        this.getOrdenDeServicio().pagado();
    }

    /**
     * Calcula monto total con tasas, adicionales y Monto GEO
     * @return
     */
    @Transient
    public double getCalcularMonto() {
        return (this.calcularMontoTasasYadicioanles) + this.montoGEO;
    }

    @Transient
    public double getCalcularMontoTasasYadicioanles() {
        return (this.calcularMontoTasasYadicioanles);
    }

    @Transient
    public void update(Comitente comitente, Tasa tasa, Ciudad ciudad, Departamento departamento) {
        setComitente(comitente);
        this.setTasa(tasa);
        this.getOrdenDeServicio().setMonto(getCalcularMonto());
        if (this.getInmueble().isUrbanoSubUrbano()) {
            this.getInmueble().setDepartamento(null);
            this.getInmueble().setCiudad(ciudad);
        } else {
            this.getInmueble().setCiudad(null);
            this.getInmueble().setDepartamento(departamento);
        }

    }

    @Transient
    public boolean generar() {
        if (this.isPendiente()) {
            this.setEstado(Estado.ESTADO_GENERADO);
            this.getOrdenDeServicio().setODSfecha(new Date());
            this.getOrdenDeServicio().setODSestado(OrdenDeServicio.EstadoODS.PENDIENTE);
            //Actualiza el monto total de lo ODS, Tasas + Adic + Fondo GEO
            this.getOrdenDeServicio().setMonto(this.getCalcularMonto());
            return true;
        } else
            return false;
    }

    @Override
    public String toString() {
        return "EProfesional{" +
                "nombre='" + nombre + '\'' +
                ", fecha=" + fecha +
                ", inmueble=" + inmueble +
                ", estado=" + estado +
                ", tasaDescripcion='" + tasaDescripcion + '\'' +
                ", tasaCosto=" + tasaCosto +
                ", coordenada=" + coordenada +
                ", ordenDeServicio=" + ordenDeServicio +
                '}';
    }
}