package ar.edu.unrn.lia.model;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

public enum InmuebleTipo implements Serializable {

    INMUEBLE_TIPO_RURAL("Rural"), TINMUEBLE_TIPO_URBANO("Urbano"), INMUEBLE_TIPO_SUB_URBANO("Sub Urbano"),INMUEBLE_TIPO_OTRO("Otro");

    private String descripcion;

    InmuebleTipo(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return descripcion;
    }

    public void setTipo(String descripcion) {
        this.descripcion = descripcion;
    }


    @Override
    public String toString() {
        return this.name();
    }

}
