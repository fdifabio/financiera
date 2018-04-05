package ar.edu.unrn.lia.bean;

import ar.edu.unrn.lia.bean.datamodel.DataModel;
import ar.edu.unrn.lia.model.*;
import ar.edu.unrn.lia.service.*;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by difabioguillermo on 11/8/17.
 */
@Named
@Scope("view")
public class GaranteBean extends GenericBean<Garante> implements Serializable {

    private static final long serialVersionUID = 1L;
    private Garante garante;


    @Inject
    private GaranteService entityService;

    @Inject
    private CiudadService ciudadService;

    private Ciudad ciudadSelecionada = new Ciudad();

    @Inject
    private ProvinciaService provinciaService;

    private Provincia provinciaSelecionada = new Provincia();

    @Inject
    private CreditoService creditoService;

    //  GenericConvert<Provincia> provinciaConvert = new GenericConvert<Provincia>();

    List<Provincia> provincias = new ArrayList<>();

    List<Ciudad> ciudades = new ArrayList<>(0);

    @PostConstruct
    public void init() {
        setModelLazy(new DataModel<Garante>(entityService));
        setServices(entityService);
        this.provincias = provinciaService.getAll();
        LOG.debug("init.. " + this.getClass().getName());
    }

    public void inicio() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            if (getId() != null) {
                setEntity(entityService.getEntityById(getId()));
                setCiudadSelecionada(getEntity().getCiudad());
                setProvinciaSelecionada(getEntity().getCiudad().getProvincia());
                ciudades = listCiudades();
            } else {
                setEntity(new Garante());
                getEntity().setCiudad(new Ciudad());
                getEntity().getCiudad().setProvincia(new Provincia());
            }
            super.setUrlDesde(getRequestURL());

        }
    }

    @Override
    public String update() {
        this.ciudadSelecionada.setProvincia(provinciaSelecionada);
        getEntity().setCiudad(ciudadSelecionada);
        return super.update();
    }

    public List<Credito> listCreditos() {
        return creditoService.listByClienteId(getId());
    }

    public List<Ciudad> listCiudades() {
        return ciudadService.getList(provinciaSelecionada.getId());
    }

    public List<Provincia> listProvincias() {
        return provinciaService.getAll();
    }


    public GaranteService getEntityService() {
        return entityService;
    }

    public void setEntityService(GaranteService entityService) {
        this.entityService = entityService;
    }

    public Ciudad getCiudadSelecionada() {
        return ciudadSelecionada;
    }

    public void setCiudadSelecionada(Ciudad ciudadSelecionada) {
        this.ciudadSelecionada = ciudadSelecionada;
    }


    public Provincia getProvinciaSelecionada() {
        return provinciaSelecionada;
    }

    public void setProvinciaSelecionada(Provincia provinciaSelecionada) {
        this.provinciaSelecionada = provinciaSelecionada;
    }


    public void deleteGarante(Garante garante) {
        try {
            entityService.delete(garante);
            agregarMensaje(FacesMessage.SEVERITY_INFO, "Exito", "Garante eliminado con exito");
        } catch (DataAccessException e) {
            agregarMensaje(FacesMessage.SEVERITY_WARN, "Atención!",
                    "No se puede eliminar Garante, tiene creditos asociados!");
        } catch (Exception e) {
            agregarMensaje(FacesMessage.SEVERITY_ERROR, "Error!",
                    e.getMessage());
        }
    }

    public void agregarMensaje(FacesMessage.Severity severity, String summary, String detail) {
        FacesMessage msg = new FacesMessage(severity, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    public CiudadService getCiudadService() {
        return ciudadService;
    }

    public void setCiudadService(CiudadService ciudadService) {
        this.ciudadService = ciudadService;
    }

    public ProvinciaService getProvinciaService() {
        return provinciaService;
    }

    public void setProvinciaService(ProvinciaService provinciaService) {
        this.provinciaService = provinciaService;
    }


    public Garante getGarante() {
        return garante;
    }

    public void setGarante(Garante garante) {
        this.garante = garante;
    }

    public List<Provincia> getProvincias() {
        return provincias;
    }

    public void setProvincias(List<Provincia> provincias) {
        this.provincias = provincias;
    }

    public List<Ciudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<Ciudad> ciudades) {
        this.ciudades = ciudades;
    }

    public void onProvinciaChange() {
        this.ciudades = listCiudades();
    }

    public CreditoService getCreditoService() {
        return creditoService;
    }

    public void setCreditoService(CreditoService creditoService) {
        this.creditoService = creditoService;
    }


}
