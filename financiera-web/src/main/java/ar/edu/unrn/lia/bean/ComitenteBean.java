package ar.edu.unrn.lia.bean;

import ar.edu.unrn.lia.bean.convert.GenericConvert;
import ar.edu.unrn.lia.bean.datamodel.DataModel;
import ar.edu.unrn.lia.model.*;
import ar.edu.unrn.lia.security.AuthenticationBean;
import ar.edu.unrn.lia.service.CiudadService;
import ar.edu.unrn.lia.service.ComitenteService;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Federico on 05/06/2017.
 */
@ManagedBean(name = "comitenteBean")
@ViewScoped
public class ComitenteBean extends GenericBean<Comitente> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{authenticationBean}")
    private AuthenticationBean authenticationBean;

    @ManagedProperty(value = "#{comitenteService}")
    private ComitenteService entityService;

    @ManagedProperty(value = "#{ciudadService}")
    private CiudadService ciudadService;

    private Ciudad ciudadSelecionada = null;

    GenericConvert<Ciudad> ciudadConvert = new GenericConvert<Ciudad>();

    @PostConstruct
    public void init() {
        setModelLazy(new DataModel<Comitente>(entityService));
        setServices(entityService);
        getCiudadConvert().setService(ciudadService);
    }


    public void inicio() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            if (getId() != null) {
                setEntity(entityService.getEntityById(getId()));
                setCiudadSelecionada(getEntity().getCiudad());
            } else {
                setEntity(new Comitente());
                getEntity().setCiudad(new Ciudad());
                getEntity().setPerfilAGR(authenticationBean.getPerfilAGR());
            }
        }
    }

    @Override
    public String update() {
        getEntity().update(ciudadSelecionada);
        return super.update();
    }

    public ComitenteService getEntityService() {
        return entityService;
    }

    public void setEntityService(ComitenteService entityService) {
        this.entityService = entityService;
    }

    public CiudadService getCiudadService() {
        return ciudadService;
    }

    public void setCiudadService(CiudadService ciudadService) {
        this.ciudadService = ciudadService;
    }

    public Ciudad getCiudadSelecionada() {
        return ciudadSelecionada;
    }

    public void setCiudadSelecionada(Ciudad ciudadSelecionada) {
        this.ciudadSelecionada = ciudadSelecionada;
    }

    public GenericConvert<Ciudad> getCiudadConvert() {
        return ciudadConvert;
    }

    public void setCiudadConvert(GenericConvert<Ciudad> ciudadConvert) {
        this.ciudadConvert = ciudadConvert;
    }

    public List<Ciudad> listCiudades() {
        return ciudadService.getAll();
    }

    public AuthenticationBean getAuthenticationBean() {
        return authenticationBean;
    }

    public void setAuthenticationBean(AuthenticationBean authenticationBean) {
        this.authenticationBean = authenticationBean;
    }
}
