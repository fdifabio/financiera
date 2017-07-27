package ar.edu.unrn.lia.dto;

import ar.edu.unrn.lia.model.BaseEntity;
import ar.edu.unrn.lia.model.Departamento;
import ar.edu.unrn.lia.model.InmuebleTipo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Federico on 17/05/2017.
 */
public class InmuebleDTO  implements Serializable {

private Long id;
    private String parcela;
    private String mzaQta;
    private String seccion;
    private DepartamentoDTO departamento;
    private String tipo;

    public InmuebleDTO() {
    }

    public InmuebleDTO(InmuebleTipo tipo) {
        this.tipo = tipo.getTipo();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParcela() {
        return parcela;
    }

    public void setParcela(String parcela) {
        this.parcela = parcela;
    }

    public String getMzaQta() {
        return mzaQta;
    }

    public void setMzaQta(String mzaQta) {
        this.mzaQta = mzaQta;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public DepartamentoDTO getDepartamento() {
        return departamento;
    }

    public void setDepartamento(DepartamentoDTO departamento) {
        this.departamento = departamento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "InmuebleDTO{" +
                "id=" + id +
                ", parcela='" + parcela + '\'' +
                ", mzaQta='" + mzaQta + '\'' +
                ", seccion='" + seccion + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
