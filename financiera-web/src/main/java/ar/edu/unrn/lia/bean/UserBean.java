package ar.edu.unrn.lia.bean;

import ar.edu.unrn.lia.bean.datamodel.DataModel;
import ar.edu.unrn.lia.model.Role;
import ar.edu.unrn.lia.model.User;
import ar.edu.unrn.lia.service.UserService;
import ar.edu.unrn.lia.util.Constantes;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by Federico on 11/08/2017.
 */
@Named
@Scope("view")
public class UserBean extends GenericBean<User> implements Serializable {

    private static final long serialVersionUID = 1L;
    private User user;


    @Inject
    private UserService entityService;

    @PostConstruct
    public void init() {
        setModelLazy(new DataModel<User>(entityService));
        setServices(entityService);
        LOG.debug("init.. " + this.getClass().getName());
    }

    public void inicio() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            if (getId() != null)
                setEntity(entityService.getEntityById(getId()));
            else
                setEntity(new User());
            super.setUrlDesde(getRequestURL());
        }
    }

    @Override
    public String update() {
        getEntity().setPassword("1234");
        return super.update();
    }

   /* public void validateUsuario(FacesContext context, UIComponent component,
                                Object value) {
        if (!getEntityService().validarUnicidadUserName((String) value)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    bundleMessage("error"), bundleMessage("formUsuario") + " " + value + " " + bundleMessage("yaexiste"));
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    public void validateMail(FacesContext context, UIComponent component,
                             Object value) throws ValidatorException {
        if (!entityService.validarUnicidadMail((String) value)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "el mail ya existe");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            throw new ValidatorException(msg);
        }
    }*/

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void initCambioClave(User usuario) {
        setEntity(usuario);
    }

    public void cambioClave() {
        try {
            getEntity().setPassword(Constantes.generate_password());
            entityService.cambiarClave(getEntity());
            super.agregarMensaje(FacesMessage.SEVERITY_INFO,
                    "Cambio Clave", "Se envio la clave con exito al mail " + getEntity().getEmail());
            setEntity(null);
        } catch (Exception e) {
            super.agregarMensaje(FacesMessage.SEVERITY_FATAL,
                    bundleMessage("error"), bundleMessage("error.password")
                            + e.getMessage());
        }
    }

    public void activarDesactivar(User user) {
        user.setActive(!user.isActive());
        entityService.save(user);
        FacesMessage msg;
        if (user.isActive()) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, bundleMessage("activo"),
                    bundleMessage("msj.activo"));

        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, bundleMessage("inactivo"),
                    bundleMessage("msj.inactivo"));
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public Role[] roles() {
        return Role.values();
    }

    public UserService getEntityService() {
        return entityService;
    }

    public void setEntityService(UserService entityService) {
        this.entityService = entityService;
    }
}
