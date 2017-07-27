package ar.edu.unrn.lia.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author mauroc79
 */
@Entity
@Table(name = "comitente")
public class Comitente extends BaseEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private boolean empresa = true;
    private String nombre;
    private String cuit;
    private String domicilio;
    private Ciudad ciudad;
    private PerfilAGR perfilAGR;
    @Embedded
    private Responsable responsable= new Responsable();


    public Comitente() {
    }

    public boolean isEmpresa() {
        return empresa;
    }

    public void setEmpresa(boolean empresa) {
        this.empresa = empresa;
    }

    @NotNull
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @ManyToOne
    @JoinColumn(nullable = true, name = "perfil_agrimensor_id")
    public PerfilAGR getPerfilAGR() {
        return perfilAGR;
    }

    public void setPerfilAGR(PerfilAGR perfilAGR) {
        this.perfilAGR = perfilAGR;
    }

    public Responsable getResponsable() {
        return responsable;
    }

    public void setResponsable(Responsable responsable) {
        this.responsable = responsable;
    }


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

    @NotNull
    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    @Transient
    public boolean isParticular() {
        return empresa == false;
    }

    @Transient
    public void update(Ciudad ciudad) {
        this.setCiudad(ciudad);
        if (!isEmpresa())
            this.responsable.setNombre(this.nombre);
    }

    @Override
    public String toString() {
        return "Comitente{" +
                "empresa=" + empresa +
                ", nombre='" + nombre + '\'' +
                ", cuit='" + cuit + '\'' +
                ", domicilio='" + domicilio + '\'' +
                ", ciudad=" + ciudad +
                ", responsable=" + responsable +
                '}';
    }
}
