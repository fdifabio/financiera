package ar.edu.unrn.lia.bean;

import ar.edu.unrn.lia.bean.datamodel.DataModel;
import ar.edu.unrn.lia.model.Caja;
import ar.edu.unrn.lia.model.Movimiento;
import ar.edu.unrn.lia.service.CajaService;
import ar.edu.unrn.lia.service.ClienteService;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Lucas on 22/08/2017.
 */
@Named
@Scope("view")
public class CajaBean extends GenericBean<Caja> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private CajaService entityService;

    @Inject
    private ClienteService clienteService;

    private Movimiento movimiento;

    @PostConstruct
    public void init() {
        setModelLazy(new DataModel<Caja>(entityService));
        setServices(entityService);
        LOG.debug("init.. " + this.getClass().getName());
    }

    public void inicio() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            if (getId() != null) {
                setEntity(entityService.getEntityById(getId()));
            } else {
                setEntity(new Caja(new Date()));
            }
            super.setUrlDesde(getRequestURL());
        }
    }

    public void habilitar() {
        getEntityService().habilitarCaja(getEntity(), new Movimiento(movimiento.getMonto(), new Date(), movimiento.getDescripcion(), Movimiento.Tipo.INGRESO));
    }

    public void cerrar() {
        getEntityService().cerrarCaja(getEntity());
    }

    @Override
    public String update() {
        return super.update();
    }

    public CajaService getEntityService() {
        return entityService;
    }

    public void setEntityService(CajaService entityService) {
        this.entityService = entityService;
    }
}
