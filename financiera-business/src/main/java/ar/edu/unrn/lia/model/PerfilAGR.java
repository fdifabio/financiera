package ar.edu.unrn.lia.model;

import ar.edu.unrn.lia.exception.BusinessException;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "perfil_agrimensor")
public class PerfilAGR extends BaseEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private String nombre;
    private String apellido;
    private String titulo;
    private String domicilioLegal;
    private Ciudad ciudadLegal;
    private String domicilioReal;
    private Ciudad ciudadReal;
    private Date fechaNacimiento;
    private String telefono;
    private String celular;
    private Date fechaEgreso;
    private String universidad;
    private String matricula;
    private String cuit;
    private User user;

    public PerfilAGR() {
        super();
    }

    @Column(name = "fecha_egreso")
    public Date getFechaEgreso() {
        return fechaEgreso;
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    @Column(name = "domicilio_legal")
    public String getDomicilioLegal() {
        return domicilioLegal;
    }

    public void setDomicilioLegal(String domicilioLegal) {
        this.domicilioLegal = domicilioLegal;
    }

    @ManyToOne
    @JoinColumn(name = "ciudad_legal_id")
    public Ciudad getCiudadLegal() {
        return ciudadLegal;
    }

    public void setCiudadLegal(Ciudad ciudadLegal) {
        this.ciudadLegal = ciudadLegal;
    }

    @Column(name = "domicilio_real")
    public String getDomicilioReal() {
        return domicilioReal;
    }

    public void setDomicilioReal(String domicilioReal) {
        this.domicilioReal = domicilioReal;
    }

    @ManyToOne
    @JoinColumn(name = "ciudad_real_id")
    public Ciudad getCiudadReal() {
        return ciudadReal;
    }

    public void setCiudadReal(Ciudad ciudadReal) {
        this.ciudadReal = ciudadReal;
    }

    @Column(name = "fecha_nacimiento")
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setFechaEgreso(Date fechaEgreso) {
        this.fechaEgreso = fechaEgreso;
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

    public String getUniversidad() {
        return universidad;
    }

    public void setUniversidad(String universidad) {
        this.universidad = universidad;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "PerfilAGR{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", titulo='" + titulo + '\'' +
                ", domicilioLegal='" + domicilioLegal + '\'' +
                ", ciudadLegal=" + ciudadLegal +
                ", domicilioReal='" + domicilioReal + '\'' +
                ", ciudadReal=" + ciudadReal +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", telefono='" + telefono + '\'' +
                ", celular='" + celular + '\'' +
                ", fechaEgrego='" + fechaEgreso + '\'' +
                ", universidad='" + universidad + '\'' +
                ", matricula='" + matricula + '\'' +
                ", cuit='" + cuit + '\'' +
                '}';
    }

    public void validar() throws BusinessException {
        if ((this.nombre!=null && this.nombre.equals("")) ||
                (this.apellido!=null && this.apellido.equals("")) ||
                (this.titulo!=null && this.titulo.equals("")) ||
                (this.domicilioLegal!=null && this.domicilioLegal.equals("")) ||
                (this.ciudadLegal !=null && this.ciudadLegal.getNombre().equals("")) ||
                (this.domicilioReal != null &&this.domicilioReal.equals("")) ||
                (this.ciudadReal!=null&& this.ciudadReal.getNombre().equals("")) ||
                (this.matricula!=null&& this.matricula.equals("")) ||
                (this.cuit != null && this.cuit.equals("")) ||
                (this.fechaNacimiento !=null && this.fechaNacimiento.equals("")) ||
                (this.celular!=null && this.celular.equals("")) ||
                (this.fechaEgreso!=null && this.fechaEgreso.equals("")) ||
                (this.universidad!=null&& this.universidad.equals("")))
            throw new BusinessException("Faltan completar datos en el perfil de usuario para crear una encomienda profesional");
    }
}
