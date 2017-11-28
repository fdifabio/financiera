package ar.edu.unrn.lia.model;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;


interface RoleFilter {
   /* public Map<String, String> eprofesionalFilter(User user);

    public Map<String, String> listODSFilter(OrdenDeServicio.EstadoODS estado);

    public Map<String, String> comitenteFilter(User user);*/
}

interface RoleAction {
   /* public boolean crearEProfesional();*/

}

interface RoleIs {
    public boolean isAdmin();

    public boolean isPrestamista();

}

public enum Role implements GrantedAuthority, Serializable, RoleFilter, RoleAction, RoleIs {

    ROLE_ADMIN("Administrador") {
        @Override
        public boolean isAdmin() {
            return true;
        }
    }, ROLE_PRESTAMISTA("Prestamista") {
      /*  public Map<String, String> listFilter(OrdenDeServicio.EstadoODS estado) {
            Map<String, String> parametros = new HashMap<>();
            parametros.put("ordenDeServicio.ODSestado", estado.toString());
            return parametros;
        }*/

        @Override
        public boolean isPrestamista() {
            return true;
        }
    };


    private String descripcion;

    Role(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRol() {
        return descripcion;
    }

    public void setRol(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAuthority() {
        return this.name();
    }

    @Override
    public String toString() {
        return this.name();
    }

    public boolean isAdmin() {
        return false;
    }

    public boolean isPrestamista() {
        return false;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
