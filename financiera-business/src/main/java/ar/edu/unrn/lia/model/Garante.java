package ar.edu.unrn.lia.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "garante")
public class Garante extends Persona implements java.io.Serializable {

    private static final long serialVersionUID = 1L;


    public Garante() {
        super();
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
}
