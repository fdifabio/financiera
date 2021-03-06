package ar.edu.unrn.lia.bean;

import ar.edu.unrn.lia.bean.datamodel.DataModel;
import ar.edu.unrn.lia.model.Ciudad;
import ar.edu.unrn.lia.model.Cliente;
import ar.edu.unrn.lia.model.Credito;
import ar.edu.unrn.lia.model.Provincia;
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
public class ClienteBean extends GenericBean<Cliente> implements Serializable {

    private static final long serialVersionUID = 1L;
    private Cliente cliente;


    @Inject
    private ClienteService entityService;

    @Inject
    private CiudadService ciudadService;

    private Ciudad ciudadSelecionada = new Ciudad();

    @Inject
    private ProvinciaService provinciaService;

    private Provincia provinciaSelecionada = new Provincia();

    @Inject
    private CreditoService creditoService;

    List<Provincia> provincias = new ArrayList<>();

    List<Ciudad> ciudades = new ArrayList<>(0);

    @PostConstruct
    public void init() {
        setModelLazy(new DataModel<Cliente>(entityService));
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
                setEntity(new Cliente());
                getEntity().setCiudad(new Ciudad());
                getEntity().getCiudad().setProvincia(new Provincia());
            }
            // getCiudadConvert().setService(ciudadService);
            // getProvinciaConvert().setService(provinciaService);
            super.setUrlDesde(getRequestURL());

        }
    }

    @Override
    public String update() {
        this.ciudadSelecionada.setProvincia(provinciaSelecionada);
        getEntity().setCiudad(ciudadSelecionada);
        return super.update();
    }


    public String updateFromCredito() {
        this.ciudadSelecionada.setProvincia(provinciaSelecionada);
        getEntity().setCiudad(ciudadSelecionada);
        try {
            services.save(getEntity());
            LOG.debug("Guardando " + getEntity());
            if (getIsNew()) {
                mensajeFlash(bundleMessage("INFO.mensaje"),
                        bundleMessage("INFO.mensajeFlash"));
                setIsNew(false);
            } else
                mensajeFlash(bundleMessage("INFO.mensaje"),
                        bundleMessage("INFO.mensajeFlash"));
            return UtilsBean.REDIRECT_CREATE;
        } catch (DataAccessException e) {
            agregarMensaje(FacesMessage.SEVERITY_ERROR, bundleMessage("error"),
                    bundleMessage("error.guardar"));
            LOG.error(" Error al actualizar " + e.getMessage());
            return null;
        } catch (Exception e) {
            agregarMensaje(FacesMessage.SEVERITY_ERROR, bundleMessage("error"),
                    e.getMessage());
            LOG.error("Error al actualizar" + e.getMessage());
            return null;
        }
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


    public ClienteService getEntityService() {
        return entityService;
    }

    public void setEntityService(ClienteService entityService) {
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


    public void deleteCliente(Cliente cliente) {
        try {
            entityService.delete(cliente);
            agregarMensaje(FacesMessage.SEVERITY_INFO, "Exito", "Cliente eliminado con exito");
        } catch (DataAccessException e) {
            agregarMensaje(FacesMessage.SEVERITY_WARN, "Atención!",
                    "No se puede eliminar Cliente, tiene creditos asociados!");
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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
