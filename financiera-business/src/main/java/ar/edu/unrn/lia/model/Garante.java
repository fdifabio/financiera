package ar.edu.unrn.lia.model;

import ar.edu.unrn.lia.exception.BusinessException;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "garante")
public class Garante extends Persona implements java.io.Serializable {

    private static final long serialVersionUID = 1L;


    private List<Credito> creditos = new ArrayList<Credito>(0);

    public Garante() {
        super();
    }

    public Garante(Long id, String dni, String nombre, String apellido, String celular) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.celular = celular;
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


    @Transient
    @Override
    public String getApellidoNombre() {
        return super.getApellidoNombre();
    }

    @Override
    public String toString() {
        return "Garante{" +
                "fotocopiaDni=" + fotocopiaDni +
                ", recibo=" + recibo +
                ", servicio=" + servicio +
                ", observacion='" + observacion + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni='" + dni + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", telefono='" + telefono + '\'' +
                ", celular='" + celular + '\'' +
                ", domicilio='" + domicilio + '\'' +
                ", ciudad=" + ciudad +
                ", trabajoLugar='" + trabajoLugar + '\'' +
                ", trabajoHorario='" + trabajoHorario + '\'' +
                ", trabajoDiaCobro=" + trabajoDiaCobro +
                ", trabajoTelefono='" + trabajoTelefono + '\'' +
                ", id=" + id +
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
}
