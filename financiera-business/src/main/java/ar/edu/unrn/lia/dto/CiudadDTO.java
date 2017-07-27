package ar.edu.unrn.lia.dto;

import ar.edu.unrn.lia.model.BaseEntity;
import ar.edu.unrn.lia.model.Departamento;

import javax.persistence.ManyToOne;

public class CiudadDTO extends AbstractDTO<Long> implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private String nombre;
    private DepartamentoDTO departamento;

    public CiudadDTO() {
    }

    public CiudadDTO(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public CiudadDTO(Long id, String nombre, DepartamentoDTO departamento) {
        this.id = id;
        this.nombre = nombre;
        this.departamento = departamento;
    }


    public CiudadDTO(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public DepartamentoDTO getDepartamento() {
        return departamento;
    }

    public void setDepartamento(DepartamentoDTO departamento) {
        this.departamento = departamento;
    }
}