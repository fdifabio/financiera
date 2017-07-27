package ar.edu.unrn.lia.dto;

/**
 * @author mauroc79
 */

public class ComitenteDTO  extends AbstractDTO<Long> implements java.io.Serializable{

    private static final long serialVersionUID = 1L;
    private Long id;
    private String nombre;
    private boolean empresa;
    private String cuit;
    private String domicilio;
    private CiudadDTO ciudad;
    private ResponsableDTO responsable;
    private PerfilAGRDTO perfilAGR;

    public ComitenteDTO() {
    }

    public ComitenteDTO(Long id, String nombre, boolean empresa, String cuit, String domicilio, String contacto, String email,Long idAGR, String nombreAGR, String apellidoAGR, Long idCiu, String nombreCiu) {
        this.id = id;
        this.nombre = nombre;
        this.empresa = empresa;
        this.cuit = cuit;
        this.domicilio = domicilio;
        this.ciudad = new CiudadDTO(idCiu,nombreCiu);
        this.responsable = new ResponsableDTO(contacto,email);
        this.perfilAGR = new PerfilAGRDTO(idAGR, nombreAGR, apellidoAGR);
    }

    public ComitenteDTO(String nombre, String cuit, boolean isEmpresa) {
        this.nombre = nombre;
        this.cuit = cuit;
        this.empresa = isEmpresa;
    }

    public ComitenteDTO(String nombre, ResponsableDTO responsable) {
        this.nombre = nombre;
        this.responsable = responsable;
    }



    public boolean isEmpresa() {
        return empresa;
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

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public CiudadDTO getCiudad() {
        return ciudad;
    }

    public void setCiudad(CiudadDTO ciudad) {
        this.ciudad = ciudad;
    }

    public ResponsableDTO getResponsable() {
        return responsable;
    }

    public void setResponsable(ResponsableDTO responsable) {
        this.responsable = responsable;
    }

    public void setEmpresa(boolean empresa) {
        this.empresa = empresa;
    }

    public PerfilAGRDTO getPerfilAGR() {
        return perfilAGR;
    }

    public void setPerfilAGR(PerfilAGRDTO perfilAGR) {
        this.perfilAGR = perfilAGR;
    }
}
