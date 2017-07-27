package ar.edu.unrn.lia.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Federico on 17/05/2017.
 */
@Entity
@Table(name = "inmueble")
public class Inmueble extends BaseEntity implements Serializable {


    private String parcela;
    private String mzaQta;
    private String seccion;
    private Departamento departamento;
    private Ciudad ciudad;
    private InmuebleTipo tipo;

    public Inmueble() {
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

    @ManyToOne
    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

   @ManyToOne
    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = true)
    public InmuebleTipo getTipo() {
        return tipo;
    }

    public void setTipo(InmuebleTipo tipo) {
        this.tipo = tipo;
    }

    @Transient
    public boolean isRural() {
        if (tipo == null)
            return false;
        return tipo.equals(InmuebleTipo.INMUEBLE_TIPO_RURAL);
    }

    @Transient
    public boolean isUrbanoSubUrbano() {
        if (tipo == null)
            return false;
        return tipo.equals(InmuebleTipo.TINMUEBLE_TIPO_URBANO) || tipo.equals(InmuebleTipo.INMUEBLE_TIPO_SUB_URBANO);
    }


    @Transient
    public boolean isOtro() {
        if (tipo == null)
            return false;
        return tipo.equals(InmuebleTipo.INMUEBLE_TIPO_OTRO) || tipo.equals(InmuebleTipo.INMUEBLE_TIPO_OTRO);
    }

}
