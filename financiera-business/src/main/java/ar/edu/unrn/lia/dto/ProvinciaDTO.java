package ar.edu.unrn.lia.dto;

import java.io.Serializable;


public class ProvinciaDTO implements Serializable {

    private Long id;
    private String nombre;

    public ProvinciaDTO(String nombre, Long id) {
        this.id = id;
        this.nombre = nombre;
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
}
