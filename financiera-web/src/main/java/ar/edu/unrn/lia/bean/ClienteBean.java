package ar.edu.unrn.lia.bean;

import ar.edu.unrn.lia.bean.datamodel.DataModel;
import ar.edu.unrn.lia.model.Cliente;
import ar.edu.unrn.lia.service.ClienteService;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

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

    @PostConstruct
    public void init() {
        setModelLazy(new DataModel<Cliente>(entityService));
        setServices(entityService);
        LOG.debug("init.. " + this.getClass().getName());
    }

    public void inicio() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            if (getId() != null)
                setEntity(entityService.getEntityById(getId()));
            else
                setEntity(new Cliente());
            super.setUrlDesde(getRequestURL());
        }
    }

    @Override
    public String update() {
        return super.update();
    }

    public ClienteService getEntityService() {
        return entityService;
    }

    public void setEntityService(ClienteService entityService) {
        this.entityService = entityService;
    }
}
