package ar.edu.unrn.lia.dto;

import java.util.Date;


public class PerfilAGRDTO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String nombre;
    private String apellido;
    private String titulo;
    private String domicilioReal;
    private CiudadDTO ciudadReal;
    private String domicilioLegal;
    private CiudadDTO ciudadLegal;
    private String matricula;
    private String cuit;
    private String legajo;
    private Date fechaNacimiento;
    private String telefono;
    private String celular;
    private Date fechaEgreso;
    private String universidad;
    private UserDTO user;

    public PerfilAGRDTO(Long id, String nombre, String apellido) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public PerfilAGRDTO(Long id, String nombre, String apellido, String matricula) {
        this(id, nombre, apellido);
        this.matricula = matricula;
    }

    public PerfilAGRDTO(Long id, String nombre, String apellido, String matricula, String cuit) {
        this(id, nombre, apellido, matricula);
        this.cuit = cuit;
    }

    public PerfilAGRDTO(Long id, String nombre, String apellido, String titulo, String domicilioReal, CiudadDTO ciudadReal, String matricula, String cuit, String legajo, UserDTO user) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.titulo = titulo;
        this.domicilioReal = domicilioReal;
        this.ciudadReal = ciudadReal;
        this.matricula = matricula;
        this.cuit = cuit;
        this.legajo = legajo;
        this.user = user;
    }

    public PerfilAGRDTO(Long id, String nombre, String apellido, String titulo, String domicilioReal, CiudadDTO ciudadReal, String domicilioLegal, CiudadDTO ciudadLegal, String matricula, String cuit, String legajo, UserDTO user,
                        Date fechaNacimiento, String telefono, String celular, Date fechaEgreso, String universidad) {
        this(id, nombre, apellido, titulo, domicilioReal, ciudadReal, matricula, cuit, legajo, user);
        this.domicilioLegal = domicilioLegal;
        this.ciudadLegal = ciudadLegal;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.celular = celular;
        this.fechaEgreso = fechaEgreso;
        this.universidad = universidad;
    }

    public PerfilAGRDTO() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDomicilioReal() {
        return domicilioReal;
    }

    public void setDomicilioReal(String domicilioReal) {
        this.domicilioReal = domicilioReal;
    }

    public CiudadDTO getCiudadReal() {
        return ciudadReal;
    }

    public void setCiudadReal(CiudadDTO ciudadReal) {
        this.ciudadReal = ciudadReal;
    }

    public String getDomicilioLegal() {
        return domicilioLegal;
    }

    public void setDomicilioLegal(String domicilioLegal) {
        this.domicilioLegal = domicilioLegal;
    }

    public CiudadDTO getCiudadLegal() {
        return ciudadLegal;
    }

    public void setCiudadLegal(CiudadDTO ciudadLegal) {
        this.ciudadLegal = ciudadLegal;
    }

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

    public Date getFechaEgreso() {
        return fechaEgreso;
    }

    public void setFechaEgreso(Date fechaEgreso) {
        this.fechaEgreso = fechaEgreso;
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

    public String getLegajo() {
        return legajo;
    }

    public void setLegajo(String legajo) {
        this.legajo = legajo;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "PerfilAGRDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                '}';
    }
}
