package ar.edu.unrn.lia.bean;

import ar.edu.unrn.lia.bean.datamodel.DataModel;
import ar.edu.unrn.lia.bean.util.ParameterSendMail;
import ar.edu.unrn.lia.model.Role;
import ar.edu.unrn.lia.model.User;
import ar.edu.unrn.lia.service.EmailService;
import ar.edu.unrn.lia.service.EncriptaService;
import ar.edu.unrn.lia.service.UserService;
import ar.edu.unrn.lia.util.Constantes;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Named
@Scope("view")
public class UserBean extends GenericBean<User> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private EmailService mailService;

    @NotNull(message = "{name.notnull}")
    @Size(min = 4, max = 30, message = "{name.size}")
    private String nuevaClave;

    @Inject
    UserService entityService;

    @Inject
    private EncriptaService encriptaService;

    @PostConstruct
    public void init() {
        setModelLazy(new DataModel<User>(entityService));
        setServices(entityService);
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

    //TODO REVISAR!!!!!!!!!!!!!!!!MODIFICAR EL PROCESO DE ALTA/MODIF desde el Administrador
    //TODO: crear el metodo saveAndNotified para crear el usuario y enviar el mail desde el UserServiceImpl.
    @Override
    public String update() {
        try {
            if (getEntity().getId() == null) {
                String password = Constantes.generate_password();
                getEntity().setPassword(password);
                getEntity().setUsername(getEntity().getEmail());
                super.update();
                String path = urlContextPathApp();
                mailService.sendMail(ParameterSendMail.from, getEntity().getEmail(), ParameterSendMail.subjectNew,
                        ParameterSendMail.body + ParameterSendMail.msjActivar
                                + path + bundleMessage("mail.url") + encriptaService.encryptURL(getEntity().getEmail())
                                + "<br/>"
                                + bundleMessage("formUsuario") + " " + getEntity().getUsername()
                                + "<br/>" + bundleMessage("password") + password);
                super.agregarMensaje(FacesMessage.SEVERITY_INFO,
                        bundleMessage("mail.summary"), bundleMessage("mail.detail") + getEntity().getEmail());
            } else
                super.update();
        } catch (Exception e) {
            super.agregarMensaje(FacesMessage.SEVERITY_ERROR, e.getMessage(), bundleMessage("mail.error"));
        }
        return UtilsBean.REDIRECT_SEARCH;
    }



    public void validateUsuario(FacesContext context, UIComponent component,
                                Object value) {
        if (!getEntityService().validarUnicidadUserName((String) value)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    bundleMessage("error"), bundleMessage("formUsuario") + " " + value + " " + bundleMessage("yaexiste"));
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void validateMail(FacesContext context, UIComponent component,
                             Object value) {
        if (!getEntityService().validarUnicidadMail((String) value)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    bundleMessage("error"), bundleMessage("formMail") + " " + value + " " + bundleMessage("yaexiste"));
            FacesContext.getCurrentInstance().addMessage(null, msg);
            throw new ValidatorException(msg);
        }
    }

    public void initCambioClave(User usuario) {
        setEntity(usuario);
    }

    public void cambioClave() {
        try {
            getEntity().setPassword(Constantes.generate_password());
            mailService.sendMail(ParameterSendMail.from, getEntity().getEmail(), ParameterSendMail.subjectNewPass, ParameterSendMail.body + ParameterSendMail.bodyAcceso
                    + getEntity().getPassword());
            getEntityService().cambiarClave(getEntity());
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
        getEntityService().save(user);
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

    public UserService getEntityService() {
        return entityService;
    }

    public void setEntityService(UserService entityService) {
        this.entityService = entityService;
    }

    public Role[] roles() {
        return Role.values();
    }

    public EmailService getMailService() {
        return mailService;
    }

    public void setMailService(EmailService mailService) {
        this.mailService = mailService;
    }

    public EncriptaService getEncriptaService() {
        return encriptaService;
    }

    public void setEncriptaService(EncriptaService encriptaService) {
        this.encriptaService = encriptaService;
    }
}