package ar.edu.unrn.lia.security;

import ar.edu.unrn.lia.bean.GenericBean;
import ar.edu.unrn.lia.bean.UtilsBean;
import ar.edu.unrn.lia.bean.util.BundleMessagei18;
import ar.edu.unrn.lia.bean.util.ParameterBean;
import ar.edu.unrn.lia.bean.util.ParameterSendMail;
import ar.edu.unrn.lia.logger.Log;
import ar.edu.unrn.lia.model.Caja;
import ar.edu.unrn.lia.model.Movimiento;
import ar.edu.unrn.lia.model.Role;
import ar.edu.unrn.lia.model.User;
import ar.edu.unrn.lia.seguridad.AuthenticationService;
import ar.edu.unrn.lia.service.*;
import ar.edu.unrn.lia.util.Constantes;
import org.hibernate.validator.constraints.Email;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;
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
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;


@Component
@Scope("session")
public class AuthenticationBean extends GenericBean<User> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Log
    protected static Logger LOG;

    @Inject
    EmailService mailService;

    @Inject
    CajaService cajaService;

    @Inject
    CreditoService creditoService;

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

    private String email;

    private Caja caja;

    private Double monto;

    private Double ingreso;

    private Double egreso;

    private boolean register = false;

    @Inject
    AuthenticationService authenticationService;

    @Inject
    UserService userService;


    @Inject
    MovimientoService movimientoService;

    private TimelineModel timelineMovimientos;

    private String patternDate;

    public AuthenticationBean() {
    }

    public String login() {
        try {
            if (authenticationService.login(username, password)) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                setUser((User) authentication.getPrincipal());
                boolean isPrestamista = false;
                boolean isAdmin = false;
                boolean active = getUser().isActive();
                if (active == false)
                    super.agregarMensaje(FacesMessage.SEVERITY_INFO, bundleMessage("usuario.no.activo"), bundleMessage("revise.mail"));
                else {
                    final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

                    //TODO: deberiamos apicar un Filter para direccionar segun el role
                    for (final GrantedAuthority grantedAuthority : authorities) {
                        if (grantedAuthority.getAuthority().equals(Role.ROLE_ADMIN.name())) {
                            setBienvenido(getUser().getUsername());
                            isAdmin = true;
                            break;
                        } else if (grantedAuthority.getAuthority().equals(Role.ROLE_PRESTAMISTA.name())) {
                            setBienvenido(getUser().getUsername());
                            isPrestamista = true;
                            break;
                        }
                    }

                    // ACTUALIZACION DE LOS ESTADOS DE CUOTAS Y CREDITOS
                    creditoService.actualizarEstadoCreditoYCuotas();

                    //CAJA
                    setCaja(getCajaService().getLast());
                    timelineMovimientos = new TimelineModel();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    for (Movimiento movimiento : getMovimientoService().getAll()
                            ) {
                        timelineMovimientos.add(new TimelineEvent(new Task(df.format(movimiento.getFecha()) + " $" + movimiento.getMonto().toString(), movimiento.getTipo().getIcon(), movimiento.getTipo().getBackgroundColor(), false), movimiento.getFecha(), false, movimiento.getTipo().getDescripcion(), movimiento.getTipo().getDescripcion()));
                    }
                    setTimelineMovimientos(
                            timelineMovimientos
                    );


                    if (isAdmin || isPrestamista)
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
        creditoService.actualizarEstadoCreditoYCuotas();
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

    public MovimientoService getMovimientoService() {
        return movimientoService;
    }

    public void setMovimientoService(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    public TimelineModel getTimelineMovimientos() {
        return timelineMovimientos;
    }

    public void setTimelineMovimientos(TimelineModel timelineMovimientos) {
        this.timelineMovimientos = timelineMovimientos;
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

    public CajaService getCajaService() {
        return cajaService;
    }

    public void setCajaService(CajaService cajaService) {
        this.cajaService = cajaService;
    }

    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Double getIngreso() {
        return ingreso;
    }

    public void setIngreso(Double ingreso) {
        this.ingreso = ingreso;
    }

    public Double getEgreso() {
        return egreso;
    }

    public void setEgreso(Double egreso) {
        this.egreso = egreso;
    }

    public String habilitarCaja() {
        Caja caja = getCajaService().habilitarCaja(new Caja(new Date()), new Movimiento(BigDecimal.valueOf(monto), new Date(), "", Movimiento.Tipo.INGRESO));
        setCaja(caja);
        agregarMensaje(FacesMessage.SEVERITY_INFO, "Caja habilitada", "Monto habilitado: $" + monto);
        return UtilsBean.REDIRECT_HOME;
    }

    public String deshabilitarCaja() {
        getCajaService().cerrarCaja(getCaja());
        setCaja(null);
        agregarMensaje(FacesMessage.SEVERITY_INFO, "Caja deshabilitada", "Caja deshabilitada con exito!");
        return UtilsBean.REDIRECT_HOME;
    }

    public String ingreso(){
        getCaja().getMovimientos().add(new Movimiento(BigDecimal.valueOf(ingreso), new Date(), "", Movimiento.Tipo.INGRESO));
        getCajaService().save(getCaja());
        agregarMensaje(FacesMessage.SEVERITY_INFO, "Ingreso registrado", "Ingreso registrado con exito!");
        return UtilsBean.REDIRECT_HOME;
    }

    public String egreso(){
        getCaja().getMovimientos().add(new Movimiento(BigDecimal.valueOf(egreso), new Date(), "", Movimiento.Tipo.EGRESO));
        getCajaService().save(getCaja());
        agregarMensaje(FacesMessage.SEVERITY_INFO, "Egreso registrado", "Egreso registrado con exito!");
        return UtilsBean.REDIRECT_HOME;
    }

    public class Task implements Serializable {

        private String monto;
        private String icon;
        private String backgroundColor;
        private boolean period;

        public Task(String monto, String icon, String backgroundColor, boolean period) {
            this.monto = monto;
            this.icon = icon;
            this.backgroundColor = backgroundColor;
            this.period = period;
        }

        public String getMonto() {
            return monto;
        }

        public String getBackgroundColor() {
            return backgroundColor;
        }

        public String getIcon() {
            return icon;
        }

        public boolean isPeriod() {
            return period;
        }
    }



}
