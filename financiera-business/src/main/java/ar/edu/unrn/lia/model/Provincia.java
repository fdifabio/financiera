package ar.edu.unrn.lia.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Federico on 17/05/2017.
 */
@Entity
@Table(name = "provincia")
public class Provincia extends BaseEntity implements Serializable{

    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
