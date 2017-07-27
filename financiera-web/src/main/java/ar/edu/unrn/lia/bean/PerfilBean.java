package ar.edu.unrn.lia.bean;

import ar.edu.unrn.lia.bean.convert.GenericConvert;
import ar.edu.unrn.lia.bean.util.ParameterBean;
import ar.edu.unrn.lia.bean.util.ParameterSendMail;
import ar.edu.unrn.lia.model.*;
import ar.edu.unrn.lia.security.AuthenticationBean;
import ar.edu.unrn.lia.service.*;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Federico on 17/05/2017.
 */
@Named
@Scope("session")
public class PerfilBean extends GenericBean<PerfilAGR> implements Serializable {


    private static final long serialVersionUID = 1L;

    @Inject
    AuthenticationBean authenticationBean;

    @Inject
    private EmailService mailService;

    @Inject
    private PerfilAGRService entityService;

    @Inject
    private UserService userService;

    @Inject
    private ProvinciaService provinciaService;

    @Inject
    private CiudadService ciudadService;

    @Inject
    private DepartamentoService departamentoService;

    @Inject
    private EncriptaService encriptaService;

    private Ciudad ciudadSeleccionadaReal;

    private Provincia provinciaSeleccionadaReal;

    private Departamento departamentoSeleccionadoReal;

    private Ciudad ciudadSeleccionadaLegal;

    private Provincia provinciaSeleccionadaLegal;

    private Departamento departamentoSeleccionadoLegal;

    List<Provincia> listProvinciasReal = new ArrayList<>();

    List<Provincia> listProvinciasLegal = new ArrayList<>();

    List<Departamento> dtosReal = new ArrayList<Departamento>();

    List<Departamento> dtosLegal = new ArrayList<Departamento>();

    List<Ciudad> ciudadesReal = new ArrayList<Ciudad>();

    List<Ciudad> ciudadesLegal = new ArrayList<Ciudad>();

    GenericConvert<Departamento> deptoConvert = new GenericConvert<Departamento>();

    GenericConvert<Ciudad> ciudadConvert = new GenericConvert<Ciudad>();

    GenericConvert<Provincia> provConvert = new GenericConvert<Provincia>();

    private String empresa;

    @PostConstruct
    public void init() {
        setServices(entityService);
        listProvinciasReal = provinciaService.getAll();
        listProvinciasLegal = listProvinciasReal;

    }

    public List<Provincia> getListProvinciasReal() {
        return listProvinciasReal;
    }

    public void setListProvinciasReal(List<Provincia> listProvinciasReal) {
        this.listProvinciasReal = listProvinciasReal;
    }

    public List<Provincia> getListProvinciasLegal() {
        return listProvinciasLegal;
    }

    public void setListProvinciasLegal(List<Provincia> listProvinciasLegal) {
        this.listProvinciasLegal = listProvinciasLegal;
    }

    public Provincia getProvinciaLegal(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("no id provided");
        }
        for (Provincia provincia : listProvinciasLegal) {
            if (id.equals(provincia.getId())) {
                return provincia;
            }
        }
        return null;
    }

    public Provincia getProvinciaReal(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("no id provided");
        }
        for (Provincia provincia : listProvinciasReal) {
            if (id.equals(provincia.getId())) {
                return provincia;
            }
        }
        return null;
    }

    public void inicioRegister() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            setEntity(new PerfilAGR());
            getEntity().setUser(new User());
            getEntity().getUser().setRole(Role.ROLE_AGR);
        }
    }

    public void inicio() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            if (authenticationBean.getPerfilAGR().getId() != null) {
                setEntity(entityService.getEntityById(authenticationBean.getPerfilAGR().getId()));
                if (entity.getCiudadReal() != null) {
                    Ciudad ciudad = getEntity().getCiudadReal();
                    setCiudadSeleccionadaReal(ciudad);
                    Departamento departamento = ciudad.getDepartamento();
                    Provincia provincia = departamento.getProvincia();
                    setDepartamentoSeleccionadoReal(departamento);
                    setProvinciaSeleccionadaReal(provincia);
                    setDtosReal(listDptosReal());
                    setCiudadesReal(listCiudadesReal());
                }
                if (entity.getCiudadLegal() != null) {
                    Ciudad ciudad = getEntity().getCiudadLegal();
                    setCiudadSeleccionadaLegal(ciudad);
                    Departamento departamento = ciudad.getDepartamento();
                    Provincia provincia = departamento.getProvincia();
                    setDepartamentoSeleccionadoLegal(departamento);
                    setProvinciaSeleccionadaLegal(provincia);
                    setDtosLegal(listDptosLegal());
                    setCiudadesLegal(listCiudadesLegal());
                }

            } else {
                setEntity(new PerfilAGR());
                getEntity().setUser(new User());
            }

            getDeptoConvert().setService(departamentoService);
            getCiudadConvert().setService(ciudadService);
            //getProvConvert().setService(provinciaService);
        }
    }

    //TODO: Parametrizar y en el servicio de perfil crear un metodo de registro que haga todo ahi...
    @Override
    public String update() {
        if (getEntity().getId() == null) {
            try {
                String password = getEntity().getUser().getPassword();
                getEntity().getUser().setUsername(getEntity().getUser().getEmail());
                super.update();
                String path = urlCentro();
                mailService.sendMail(ParameterSendMail.from, getEntity().getUser().getEmail(), ParameterSendMail.subjectNew,
                        ParameterSendMail.body + ParameterSendMail.msjActivar
                                + path + bundleMessage("mail.url") + encriptaService.encryptURL(getEntity().getUser().getEmail())
                                + "<br/>"
                                + bundleMessage("formUsuario") + " " + getEntity().getUser().getUsername()
                                + "<br/>" + bundleMessage("password") + password);
                super.agregarMensaje(FacesMessage.SEVERITY_INFO,
                        bundleMessage("mail.summary"), bundleMessage("mail.detail") + getEntity().getUser().getEmail());
            } catch (Exception e) {
                super.agregarMensaje(FacesMessage.SEVERITY_ERROR, e.getMessage(), bundleMessage("mail.error"));
            }
        } else
            super.update();
        authenticationBean.setPerfilAGR(getEntity());
        return UtilsBean.REDIRECT_MIPERFIL;
    }

    public void validateUsuario(FacesContext context, UIComponent component,
                                Object value) throws ValidatorException {
        if (!getUserService().validarUnicidadUserName((String) value)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    bundleMessage("error"), bundleMessage("formUsuario") + " " + value + " " + bundleMessage("yaexiste"));
            FacesContext.getCurrentInstance().addMessage(null, msg);
            throw new ValidatorException(msg);
        }
    }

    //TODO:REVISAR EL PROCESO DE VALIDAR UNICIDAD
    public void validateMail(FacesContext context, UIComponent component,
                             Object value) throws ValidatorException {
        if (!getUserService().validarUnicidadMail((String) value)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    bundleMessage("error"), bundleMessage("formMail") + " " + value + " " + bundleMessage("yaexiste"));
            FacesContext.getCurrentInstance().addMessage(null, msg);
            throw new ValidatorException(msg);
        }
    }


    public String urlCentro() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return bundleMessage("http") + request.getServerName() + ":" + request.getLocalPort() + request.getContextPath();
    }

    public PerfilAGRService getEntityService() {
        return entityService;
    }

    public void setEntityService(PerfilAGRService entityService) {
        this.entityService = entityService;
    }

    public Departamento getDepartamentoSeleccionadoReal() {
        return departamentoSeleccionadoReal;
    }

    public void setDepartamentoSeleccionadoReal(Departamento departamentoSeleccionadoReal) {
        this.departamentoSeleccionadoReal = departamentoSeleccionadoReal;
    }

    public Departamento getDepartamentoSeleccionadoLegal() {
        return departamentoSeleccionadoLegal;
    }

    public void setDepartamentoSeleccionadoLegal(Departamento departamentoSeleccionadoLegal) {
        this.departamentoSeleccionadoLegal = departamentoSeleccionadoLegal;
    }

    public ProvinciaService getProvinciaService() {
        return provinciaService;
    }

    public void setProvinciaService(ProvinciaService provinciaService) {
        this.provinciaService = provinciaService;
    }

    public CiudadService getCiudadService() {
        return ciudadService;
    }

    public void setCiudadService(CiudadService ciudadService) {
        this.ciudadService = ciudadService;
    }

    public DepartamentoService getDepartamentoService() {
        return departamentoService;
    }

    public void setDepartamentoService(DepartamentoService departamentoService) {
        this.departamentoService = departamentoService;
    }

    public Ciudad getCiudadSeleccionadaReal() {
        return ciudadSeleccionadaReal;
    }

    public void setCiudadSeleccionadaReal(Ciudad ciudadSeleccionadaReal) {
        this.ciudadSeleccionadaReal = ciudadSeleccionadaReal;
        getEntity().setCiudadReal(ciudadSeleccionadaReal);
    }

    public Ciudad getCiudadSeleccionadaLegal() {
        return ciudadSeleccionadaLegal;
    }

    public void setCiudadSeleccionadaLegal(Ciudad ciudadSeleccionadaLegal) {
        this.ciudadSeleccionadaLegal = ciudadSeleccionadaLegal;
        getEntity().setCiudadLegal(ciudadSeleccionadaLegal);
    }

    public List<Departamento> getDtosReal() {
        return dtosReal;
    }

    public void setDtosReal(List<Departamento> dtosReal) {
        this.dtosReal = dtosReal;
    }

    public List<Departamento> getDtosLegal() {
        return dtosLegal;
    }

    public void setDtosLegal(List<Departamento> dtosLegal) {
        this.dtosLegal = dtosLegal;
    }

    public GenericConvert<Departamento> getDeptoConvert() {
        return deptoConvert;
    }

    public void setDeptoConvert(GenericConvert<Departamento> deptoConvert) {
        this.deptoConvert = deptoConvert;
    }

    public Provincia getProvinciaSeleccionadaReal() {
        return provinciaSeleccionadaReal;
    }

    public void setProvinciaSeleccionadaReal(Provincia provinciaSeleccionadaReal) {
        this.provinciaSeleccionadaReal = provinciaSeleccionadaReal;
    }

    public Provincia getProvinciaSeleccionadaLegal() {
        return provinciaSeleccionadaLegal;
    }

    public void setProvinciaSeleccionadaLegal(Provincia provinciaSeleccionadaLegal) {
        this.provinciaSeleccionadaLegal = provinciaSeleccionadaLegal;
    }

    public List<Ciudad> listCiudadesReal() {
        return ciudadService.getList(departamentoSeleccionadoReal.getId());
    }

    public List<Departamento> listDptosReal() {
        return departamentoService.getList(provinciaSeleccionadaReal.getId());
    }

    public List<Ciudad> listCiudadesLegal() {
        return ciudadService.getList(departamentoSeleccionadoLegal.getId());
    }

    public List<Departamento> listDptosLegal() {
        return departamentoService.getList(provinciaSeleccionadaLegal.getId());
    }


    public void cargaListaDeptosReal(AjaxBehaviorEvent event) {
        this.dtosReal = listDptosReal();
    }

    public void cargaListaCiudadesReal(AjaxBehaviorEvent event) {
        this.ciudadesReal = listCiudadesReal();
    }

    public void cargaListaDeptosLegal(AjaxBehaviorEvent event) {
        this.dtosLegal = listDptosLegal();
    }

    public void cargaListaCiudadesLegal(AjaxBehaviorEvent event) {
        this.ciudadesLegal = listCiudadesLegal();
    }

    public List<Ciudad> getCiudadesReal() {
        return ciudadesReal;
    }

    public void setCiudadesReal(List<Ciudad> ciudadesReal) {
        this.ciudadesReal = ciudadesReal;
    }

    public List<Ciudad> getCiudadesLegal() {
        return ciudadesLegal;
    }

    public void setCiudadesLegal(List<Ciudad> ciudadesLegal) {
        this.ciudadesLegal = ciudadesLegal;
    }

    public GenericConvert<Ciudad> getCiudadConvert() {
        return ciudadConvert;
    }

    public void setCiudadConvert(GenericConvert<Ciudad> ciudadConvert) {
        this.ciudadConvert = ciudadConvert;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    public EmailService getMailService() {
        return mailService;
    }

    public void setMailService(EmailService mailService) {
        this.mailService = mailService;
    }

    public AuthenticationBean getAuthenticationBean() {
        return authenticationBean;
    }

    public void setAuthenticationBean(AuthenticationBean authenticationBean) {
        this.authenticationBean = authenticationBean;
    }

    public String getLeyenda() {
        return ParameterBean.leyenda;
    }
}
