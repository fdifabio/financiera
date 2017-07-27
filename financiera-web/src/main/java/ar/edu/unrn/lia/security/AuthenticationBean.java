package ar.edu.unrn.lia.security;

import ar.edu.unrn.lia.bean.GenericBean;
import ar.edu.unrn.lia.bean.UtilsBean;
import ar.edu.unrn.lia.bean.util.BundleMessagei18;
import ar.edu.unrn.lia.bean.util.ParameterBean;
import ar.edu.unrn.lia.bean.util.ParameterSendMail;
import ar.edu.unrn.lia.exception.BusinessException;
import ar.edu.unrn.lia.logger.Log;
import ar.edu.unrn.lia.model.PerfilAGR;
import ar.edu.unrn.lia.model.Role;
import ar.edu.unrn.lia.model.User;
import ar.edu.unrn.lia.seguridad.AuthenticationService;
import ar.edu.unrn.lia.service.EmailService;
import ar.edu.unrn.lia.service.UserService;
import ar.edu.unrn.lia.util.Constantes;
import org.hibernate.validator.constraints.Email;
import org.slf4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;


@Component
@Scope("session")
public class AuthenticationBean extends GenericBean<User> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Log
    protected static Logger LOG;

    @Inject
    EmailService mailService;

    @NotNull(message = "{userNotNull}")
    private String username;

    @NotNull(message = "Clave es Obligatorio")
    private String password;

    @NotNull(message = "Nueva Clave es Obligatorio")
    private String newPassword;

    @NotNull(message = "Confirmar Clave es Obligatorio")
    private String confPassword;

    private String bienvenido;

    private User user;

    private PerfilAGR perfilAGR;

    private String email;

    private boolean register = false;

    @Inject
    AuthenticationService authenticationService;

    @Inject
    UserService userService;


    private String patternDate;

    public AuthenticationBean() {
    }

    public String login() {
        try {
            if (authenticationService.login(username, password)) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                setUser((User) authentication.getPrincipal());
                boolean isUserSecretario = false;
                boolean isAdmin = false;
                boolean isAGR = false;
                boolean active = getUser().isActive();
                if (active == false)
                    super.agregarMensaje(FacesMessage.SEVERITY_INFO, bundleMessage("usuario.no.activo"), bundleMessage("revise.mail"));
                else {
                    final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

                    //TODO: deberiamos apicar un Filter para direccionar segun el role
                    for (final GrantedAuthority grantedAuthority : authorities) {
                        if (grantedAuthority.getAuthority().equals(Role.ROLE_SECRETARIO.name())) {
                            setBienvenido(getUser().getUsername());
                            isUserSecretario = true;
                            break;
                        } else if (grantedAuthority.getAuthority().equals(Role.ROLE_ADMIN.name())) {
                            setBienvenido(getUser().getUsername());
                            isAdmin = true;
                            break;
                        } else if (grantedAuthority.getAuthority().equals(Role.ROLE_AGR.name())) {
                            isAGR = true;
                            setPerfilAGR(getUser().getPerfilAGR());
                            setBienvenido(getPerfilAGR().getNombre() + " " + getPerfilAGR().getApellido());
                            break;
                        }
                    }

                    try {
                        if (isAGR)
                            getPerfilAGR().validar();
                    } catch (BusinessException b) {

                           mensajeFlash(bundleMessage("advertencia"),
                                   bundleMessage("completar.perfil") + ". "+ bundleMessage("verifique.complete"), FacesMessage.SEVERITY_FATAL);

                        return UtilsBean.REDIRECT_PERFIL;
                    }

                    if (isAdmin || isAGR || isUserSecretario)
                        return UtilsBean.REDIRECT_HOME;
                    else
                        super.agregarMensaje(FacesMessage.SEVERITY_INFO,
                                bundleMessage("error"), bundleMessage("permisos"));
                }
            }
        } catch (UsernameNotFoundException e) {
            super.agregarMensaje(FacesMessage.SEVERITY_ERROR, bundleMessage("autenticacion"),
                    bundleMessage("datos.incorrectos"));
        } catch (Exception e) {
            super.agregarMensaje(FacesMessage.SEVERITY_ERROR, bundleMessage("autenticacion"),
                    bundleMessage("user.pass.validacion"));
        }
        return null;
    }


    public String cambiarContrasenia() {
        try {
            if (getNewPassword().equals(getConfPassword())) {
                if (getUserService().encoder().matches(getPassword(), getUser().getPassword())) {
                    getUser().setPassword(getNewPassword());
                    getUserService().cambiarClave(getUser());
                    setUser(getUserService().getEntityById(getUser().getId()));
                    super.agregarMensaje(FacesMessage.SEVERITY_INFO,
                            bundleMessage("cambio.clave"),
                            bundleMessage("cambio.clave.ok"));
                    setNewPassword(null);
                    setConfPassword(null);
                } else
                    super.agregarMensaje(FacesMessage.SEVERITY_INFO,
                            bundleMessage("cambio.clave"),
                            bundleMessage("cambio.clave.invalida"));
            } else
                super.agregarMensaje(FacesMessage.SEVERITY_INFO,
                        bundleMessage("cambio.clave"),
                        bundleMessage("cambio.clave.no.coincide"));

        } catch (UsernameNotFoundException e) {
            super.agregarMensaje(FacesMessage.SEVERITY_ERROR,
                    bundleMessage("error"),
                    e.getMessage());
        }
        return null;
    }

    public void validateMail(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        // try {
        if (userService.validarUnicidadMail((String) value)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    bundleMessage("error"), value + ", email no existe");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            throw new ValidatorException(msg);
        }

        //    userService.validarUnicidadMail((String) value);

       /* }catch (BusinessException e){
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL,"Error",
                    bundleMessage(e.getKeyMessage()));
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }*/
    }

    //TODO: Ver el proceso de envio, deberia estar en el servicio #UserService
    public void enviarMail() {
        try {
            User user = userService.findByMail(this.getEmail().toString());
            String password = Constantes.generate_password();
            user.setPassword(password);
            userService.cambiarClave(user);
            mailService.sendMail(ParameterSendMail.from, this.getEmail(),
                    ParameterSendMail.subjectForgot, ParameterSendMail.body + ParameterSendMail.bodyAcceso + password);
            super.agregarMensaje(FacesMessage.SEVERITY_INFO,
                    bundleMessage("exito"),
                    bundleMessage("mail.detalle") + this.getEmail());
        } catch (Exception e) {
            super.agregarMensaje(FacesMessage.SEVERITY_ERROR,
                    bundleMessage("error"),
                    e.getMessage());
        }

    }

    public String bundleMessage(String nombre) {
        return BundleMessagei18.getString(nombre);
    }

    public String logout() {
        authenticationService.logout();
        return UtilsBean.REDIRECT_LOGIN;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getConfPassword() {
        return confPassword;
    }

    public void setConfPassword(String confPassword) {
        this.confPassword = confPassword;
    }

    @Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }


    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public PerfilAGR getPerfilAGR() {
        return perfilAGR;
    }

    public void setPerfilAGR(PerfilAGR perfilAGR) {
        this.perfilAGR = perfilAGR;
    }

    public void changeRegister() {
        setRegister(true);
    }

    public boolean isRegister() {
        return register;
    }

    public void setRegister(boolean register) {
        this.register = register;
    }

    public String getBienvenido() {
        return bienvenido;
    }

    public void setBienvenido(String bienvenido) {
        this.bienvenido = bienvenido;
    }

    protected final void clearAuthenticationAttributes(
            final HttpServletRequest request) {
        final HttpSession session = request.getSession(false);

        if (session == null) {
            return;
        }

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    public EmailService getMailService() {
        return mailService;
    }

    public void setMailService(EmailService mailService) {
        this.mailService = mailService;
    }

    public String getPatternDate() {
        return ParameterBean.patternDate;
    }
}
