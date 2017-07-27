package ar.edu.unrn.lia.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class Responsable implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private String nombre;
    private String email;

    public Responsable() {
    }

    public Responsable(String nombre, String email) {
        super();
        this.nombre = nombre;
        this.email = email;
    }


    @Column(name = "contacto")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    @Column(name = "resp_email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Responsable{" +
                "nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
