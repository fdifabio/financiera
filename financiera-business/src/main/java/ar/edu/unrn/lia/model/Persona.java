package ar.edu.unrn.lia.model;

import ar.edu.unrn.lia.exception.BusinessException;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class Persona extends BaseEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    protected Boolean fotocopiaDni;
    protected Boolean recibo;
    protected Boolean servicio;
    protected String observacion;
    protected String nombre;
    protected String apellido;
    protected String dni;
    protected Date fechaNacimiento;
    protected String telefono;
    protected String celular;
    protected String domicilio;
    protected Ciudad ciudad;
    protected String trabajoLugar;
    protected String trabajoHorario;
    protected int trabajoDiaCobro;
    protected String trabajoTelefono;

    public Persona() {
        super();
    }



    @Column(name = "nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(name = "apellido")
    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @Column(name = "dni")
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    @Column(name = "domicilio")
    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    @ManyToOne
    @JoinColumn(name = "ciudad_id")
    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    @Column(name = "fecha_nacimiento")
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    @Column(name = "trabajo_lugar")
    public String getTrabajoLugar() {
        return trabajoLugar;
    }

    public void setTrabajoLugar(String trabajoLugar) {
        this.trabajoLugar = trabajoLugar;
    }

    @Column(name = "trabajo_horario")
    public String getTrabajoHorario() {
        return trabajoHorario;
    }

    public void setTrabajoHorario(String trabajoHorario) {
        this.trabajoHorario = trabajoHorario;
    }

    @Column(name = "trabajo_dia_cobro")
    public int getTrabajoDiaCobro() {
        return trabajoDiaCobro;
    }

    public void setTrabajoDiaCobro(int trabajoDiaCobro) {
        this.trabajoDiaCobro = trabajoDiaCobro;
    }

    @Transient
    public String getApellidoNombre() {
        return getApellido() + " " + getNombre();
    }

    @Column(name = "trabajo_telefono")
    public String getTrabajoTelefono() {
        return trabajoTelefono;
    }

    public void setTrabajoTelefono(String trabajoTelefono) {
        this.trabajoTelefono = trabajoTelefono;
    }

    @Column(name = "fotocopia_dni")
    public Boolean getFotocopiaDni() {
        return fotocopiaDni;
    }

    public void setFotocopiaDni(Boolean fotocopiaDni) {
        this.fotocopiaDni = fotocopiaDni;
    }

    public Boolean getRecibo() {
        return recibo;
    }

    public void setRecibo(Boolean recibo) {
        this.recibo = recibo;
    }

    public Boolean getServicio() {
        return servicio;
    }

    public void setServicio(Boolean servicio) {
        this.servicio = servicio;
    }


    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
