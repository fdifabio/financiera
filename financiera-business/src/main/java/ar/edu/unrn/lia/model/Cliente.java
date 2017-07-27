package ar.edu.unrn.lia.model;

import ar.edu.unrn.lia.exception.BusinessException;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Federico on 27/07/2017.
 */
@Entity
@Table(name = "cliente")
public class Cliente extends BaseEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private String nombre;
    private String apellido;
    private String dni;
    private Date fechaNacimiento;
    private String telefono;
    private String celular;
    private String domicilio;
    private Ciudad ciudad;
    private String trabajoLugar;
    private String trabajoHorario;
    private Boolean garantia;
    private String observacion;
    private User user;

    public Cliente() {
        super();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

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

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Boolean getGarantia() {
        return garantia;
    }

    public void setGarantia(Boolean garantia) {
        this.garantia = garantia;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


    @Override
    public String toString() {
        return "Cliente{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni='" + dni + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", telefono='" + telefono + '\'' +
                ", celular='" + celular + '\'' +
                ", domicilio='" + domicilio + '\'' +
                ", ciudad=" + ciudad +
                ", trabajoLugar='" + trabajoLugar + '\'' +
                ", trabajoHorario='" + trabajoHorario + '\'' +
                ", garantia=" + garantia +
                ", observacion='" + observacion + '\'' +
                ", user=" + user +
                '}';
    }

    public void validar() throws BusinessException {
        if ((this.nombre != null && this.nombre.equals("")) ||
                (this.apellido != null && this.apellido.equals("")) ||
                (this.dni != null && this.dni.equals("")) ||
                (this.domicilio != null && this.domicilio.equals("")) ||
                (this.ciudad != null && this.ciudad.getNombre().equals("")) ||
                (this.fechaNacimiento != null && this.fechaNacimiento.equals("")) ||
                (this.celular != null && this.celular.equals("")) ||
                (this.trabajoLugar != null && this.trabajoLugar.equals("")) ||
                (this.trabajoHorario != null && this.trabajoHorario.equals("")) ||
                (this.garantia != null) ||
                (this.observacion != null && this.observacion.equals("")))
            throw new BusinessException("Faltan completar datos del cliente para otorgar un prestamo");
    }
}

