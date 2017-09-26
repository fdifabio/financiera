package ar.edu.unrn.lia.bean;

import ar.edu.unrn.lia.bean.datamodel.DataModel;
import ar.edu.unrn.lia.model.Cliente;
import ar.edu.unrn.lia.model.Cobro;
import ar.edu.unrn.lia.model.Credito;
import ar.edu.unrn.lia.model.Cuota;
import ar.edu.unrn.lia.service.ClienteService;
import ar.edu.unrn.lia.service.CobroService;
import ar.edu.unrn.lia.service.CreditoService;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lucas on 22/08/2017.
 */
@Named
@Scope("view")
public class CobroBean extends GenericBean<Cobro> implements Serializable {

    private static final long serialVersionUID = 1L;
    private Credito credito = new Credito();
    private BigDecimal saldoCuenta;
    private boolean usaSaldoCuenta = false;

    @Inject
    private CobroService entityService;

    @Inject
    private ClienteService clienteService;

    @Inject
    private CreditoService creditoService;

    private List<Cuota> selectedCuotas = new ArrayList<>(0);

    @PostConstruct
    public void init() {
        setModelLazy(new DataModel<Cobro>(entityService));
        setServices(entityService);
        LOG.debug("init.. " + this.getClass().getName());
    }

    public void inicio() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            cargarCredito();
            if (getId() != null) {
                setEntity(entityService.getEntityById(getId()));
            } else {
                setEntity(new Cobro());
            }
            super.setUrlDesde(getRequestURL());
        }
    }

    private void cargarCredito() {
        if (credito.getId() != null)
            credito = creditoService.getEntityById(credito.getId());
    }

    public List<Cliente> completeCliente(String apellidoNombre) {
        return clienteService.searchByApellidoNombre(apellidoNombre);
    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Cuota seleccionada Nro.: ", ((Cuota) event.getObject()).getNro().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("Cuota deseleccionada Nro.: ", ((Cuota) event.getObject()).getNro().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onToggleSelect() {
    }

    public BigDecimal montoAcumulado() {
        return selectedCuotas.stream().map(Cuota::monto).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal total() {
        return montoAcumulado().add(saldoCuenta == null ? BigDecimal.ZERO : saldoCuenta).subtract(usaSaldoCuenta ? credito.getSaldoCuenta() : BigDecimal.ZERO);
    }

    @Override
    public String update() {
        return super.update();
    }

    public CobroService getEntityService() {
        return entityService;
    }

    public void setEntityService(CobroService entityService) {
        this.entityService = entityService;
    }

    public ClienteService getClienteService() {
        return clienteService;
    }

    public void setClienteService(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public Credito getCredito() {
        return credito;
    }

    public void setCredito(Credito credito) {
        this.credito = credito;
    }

    public List<Cuota> getSelectedCuotas() {
        return selectedCuotas;
    }

    public void setSelectedCuotas(List<Cuota> selectedCuotas) {
        this.selectedCuotas = selectedCuotas;
    }

    public BigDecimal getSaldoCuenta() {
        return saldoCuenta;
    }

    public void setSaldoCuenta(BigDecimal saldoCuenta) {
        this.saldoCuenta = saldoCuenta;
    }

    public boolean isUsaSaldoCuenta() {
        return usaSaldoCuenta;
    }

    public void setUsaSaldoCuenta(boolean usaSaldoCuenta) {
        this.usaSaldoCuenta = usaSaldoCuenta;
    }
}
