package ar.edu.unrn.lia.bean;

import ar.edu.unrn.lia.bean.datamodel.DataModel;
import ar.edu.unrn.lia.model.Cliente;
import ar.edu.unrn.lia.model.Credito;
import ar.edu.unrn.lia.service.ClienteService;
import ar.edu.unrn.lia.service.CreditoService;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lucas on 22/08/2017.
 */
@Named
@Scope("view")
public class CreditoBean extends GenericBean<Credito> implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Cliente> clientes = new ArrayList<Cliente>(0);
    private double montoCuota;

    @Inject
    private CreditoService entityService;

    @Inject
    private ClienteService clienteService;

    @PostConstruct
    public void init() {
        setModelLazy(new DataModel<Credito>(entityService));
        setServices(entityService);
        LOG.debug("init.. " + this.getClass().getName());
    }

    public void inicio() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            if (getId() != null)
                setEntity(entityService.getEntityById(getId()));
            else {
                setEntity(new Credito());
//                clientes.addAll(clienteService.getAll());
            }
            super.setUrlDesde(getRequestURL());
        }
    }

    public List<Cliente> completeCliente(String apellidoNombre) {
        return clienteService.searchByApellidoNombre(apellidoNombre);
    }


    public void calcularMontoCuotas(ActionEvent actionEvent){
        montoCuota = entity.calcularMontoCuotas();
    }

    @Override
    public String update() {
        return super.update();
    }

    public CreditoService getEntityService() {
        return entityService;
    }

    public void setEntityService(CreditoService entityService) {
        this.entityService = entityService;
    }

    public ClienteService getClienteService() {
        return clienteService;
    }

    public void setClienteService(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public double getMontoCuota() {
        return montoCuota;
    }

    public void setMontoCuota(double montoCuota) {
        this.montoCuota = montoCuota;
    }
}
