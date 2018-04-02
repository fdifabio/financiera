package ar.edu.unrn.lia.model;

import ar.edu.unrn.lia.exception.BusinessException;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Federico on 27/07/2017.
 */
@Entity
@Table(name = "cliente")
public class Cliente extends Persona implements java.io.Serializable {

    private static final long serialVersionUID = 1L;


    private User user;
    private List<Credito> creditos = new ArrayList<Credito>(0);

    /*TRANSIENT*/
    private BigDecimal saldoAdeudado;
    private Credito creditoAdeudado;

    public Cliente() {
        super();
    }

    public Cliente(Long id, String dni, String nombre, String apellido, String celular, BigDecimal saldoAdeudado, Long idCredito) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.celular = celular;
        this.saldoAdeudado = saldoAdeudado;
        this.creditoAdeudado = new Credito(idCredito);
    }

    public Cliente(Long id, String dni, String nombre, String apellido, String celular) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.celular = celular;
    }


    @ManyToOne(cascade = {CascadeType.ALL}, optional = true)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToMany(mappedBy = "cliente")
    @Fetch(FetchMode.JOIN)
    public List<Credito> getCreditos() {
        return creditos;
    }

    public void setCreditos(List<Credito> creditos) {
        this.creditos = creditos;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


    @Override
    public String toString() {
        return "Cliente{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", id=" + id +
                ", dni='" + dni + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", telefono='" + telefono + '\'' +
                ", celular='" + celular + '\'' +
                ", domicilio='" + domicilio + '\'' +
                ", ciudad=" + ciudad +
                ", trabajoLugar='" + trabajoLugar + '\'' +
                ", trabajoHorario='" + trabajoHorario + '\'' +
                ", user=" + user +
                ", creditos=" + creditos +
                '}';
    }

    public void validar() throws BusinessException {
        if ((this.nombre != null && this.nombre.equals("")) ||
                (this.apellido != null && this.apellido.equals("")) ||
                (this.dni != null && this.dni.equals("")) ||
                (this.domicilio != null && this.domicilio.equals("")) ||
                (this.ciudad != null && this.ciudad.getNombre().equals("")) ||
                (this.fechaNacimiento != null && this.fechaNacimiento.equals("")) ||
                (this.celular != null && this.celular.equals("")) ||
                (this.trabajoLugar != null && this.trabajoLugar.equals("")) ||
                (this.trabajoHorario != null && this.trabajoHorario.equals("")))
            throw new BusinessException("Faltan completar datos del cliente para otorgar un prestamo");
    }

    @Transient
    public BigDecimal getSaldoAdeudado() {
        return saldoAdeudado;
    }

    @Transient
    @Override
    public String getApellidoNombre() {
        return super.getApellidoNombre();
    }
}

