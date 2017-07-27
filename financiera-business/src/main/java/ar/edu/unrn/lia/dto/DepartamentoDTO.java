package ar.edu.unrn.lia.dto;

import ar.edu.unrn.lia.model.BaseEntity;
import ar.edu.unrn.lia.model.Provincia;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Federico on 17/05/2017.
 */

public class DepartamentoDTO  implements Serializable {

    private Long id;
    private String nombre;
    private ProvinciaDTO provincia;


    public DepartamentoDTO(Long id, String nombre, ProvinciaDTO provincia) {
        this.id = id;
        this.nombre = nombre;
        this.provincia = provincia;
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

    public ProvinciaDTO getProvincia() {
        return provincia;
    }

    public void setProvincia(ProvinciaDTO provincia) {
        this.provincia = provincia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DepartamentoDTO that = (DepartamentoDTO) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
