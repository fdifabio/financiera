package ar.edu.unrn.lia.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ciudad")
public class Ciudad extends BaseEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private String nombre;
    private Provincia provincia;

    public Ciudad() {
    }

    public Ciudad(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Ciudad(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @ManyToOne
    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }


    @Override
    public String toString() {
        return "Ciudad [id=" + id + ", nombre=" + nombre + "]";
    }
}