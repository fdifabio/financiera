package ar.edu.unrn.lia.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Federico on 17/05/2017.
 */
@Entity
@Table(name = "departamento")
public class Departamento extends BaseEntity implements Serializable {

    private String nombre;
    private Provincia provincia;


    public String getNombre() {
        return nombre;
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
}
