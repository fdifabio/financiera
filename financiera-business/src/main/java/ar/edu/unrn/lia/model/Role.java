package ar.edu.unrn.lia.model;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


interface RoleFilter {
    public Map<String, String> eprofesionalFilter(User user);

    public Map<String, String> listODSFilter(OrdenDeServicio.EstadoODS estado);

    public Map<String, String> comitenteFilter(User user);
}

interface RoleAction {
    public boolean crearEProfesional();

}

interface RoleIs {
    public boolean isAdmin();

    public boolean isSecretario();

    public boolean isAGR();

}

public enum Role implements GrantedAuthority, Serializable, RoleFilter, RoleAction, RoleIs {

    ROLE_ADMIN("Administrador") {
        @Override
        public boolean isAdmin() {
            return true;
        }
    }, ROLE_SECRETARIO("Secretario") {
        public Map<String, String> listODSFilter(OrdenDeServicio.EstadoODS estado) {
            Map<String, String> parametros = new HashMap<>();
            parametros.put("ordenDeServicio.ODSestado", estado.toString());
            return parametros;
        }

        @Override
        public boolean isSecretario() {
            return true;
        }
    }, ROLE_AGR("Agrimensor") {
        @Override
        public Map<String, String> eprofesionalFilter(User user) {
            Map<String, String> parametros = new HashMap<>();
            parametros.put("perfilAGR.id", Long.toString(user.getPerfilAGR().getId()));
            return parametros;
        }

        @Override
        public Map<String, String> comitenteFilter(User user) {
            Map<String, String> parametros = new HashMap<>();
            parametros.put("perfilAGR.id", Long.toString(user.getPerfilAGR().getId()));
            return parametros;
        }

        @Override
        public boolean crearEProfesional() {
            return true;
        }

        @Override
        public boolean isAGR() {
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

    public Map<String, String> eprofesionalFilter(User user) {
        return new HashMap<String, String>();
    }

    public Map<String, String> listODSFilter(OrdenDeServicio.EstadoODS estado) {
        return null;
    }


    public Map<String, String> comitenteFilter(User user) {
        return null;
    }

    @Override
    public boolean crearEProfesional() {
        return false;
    }


    public boolean isAdmin() {
        return false;
    }

    public boolean isSecretario() {
        return false;
    }

    public boolean isAGR() {
        return false;
    }
}
