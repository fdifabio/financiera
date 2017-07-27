package ar.edu.unrn.lia.dto;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class ResponsableDTO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private String nombre;
    private String email;

    public ResponsableDTO() {
    }


    public ResponsableDTO(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
