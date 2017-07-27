package ar.edu.unrn.lia.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ubicacion")
public class Ubicacion extends BaseEntity implements java.io.Serializable {

    private static final long serialVersionUID = 6343798786337774530L;
    private String calle;
    private Ciudad ciudad;

    public Ubicacion() {
    }

    public Ubicacion(Long id, Ciudad ciudad, String calle) {
        this.id = id;
        this.ciudad = ciudad;
        this.calle = calle;
    }

    public Ubicacion(Ciudad ciudad, String calle) {
        this.ciudad = ciudad;
        this.calle = calle;
    }

    @ManyToOne
    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public String getCalle() {
        return this.calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    @Override
    public String toString() {
        return "Ubicacion{" +
                "calle='" + calle + '\'' +
                ", ciudad=" + ciudad +
                '}';
    }
}
