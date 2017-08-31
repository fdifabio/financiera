package ar.edu.unrn.lia.bean;

import ar.edu.unrn.lia.bean.datamodel.DataModel;
import ar.edu.unrn.lia.model.Ciudad;
import ar.edu.unrn.lia.model.Cliente;
import ar.edu.unrn.lia.model.Provincia;
import ar.edu.unrn.lia.service.CiudadService;
import ar.edu.unrn.lia.service.ClienteService;
import ar.edu.unrn.lia.service.ProvinciaService;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
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

    // GenericConvert<Ciudad> ciudadConvert = new GenericConvert<Ciudad>();

    @Inject
    private ProvinciaService provinciaService;

    private Provincia provinciaSelecionada = new Provincia();


    //  GenericConvert<Provincia> provinciaConvert = new GenericConvert<Provincia>();

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
        return super.update();
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
}
