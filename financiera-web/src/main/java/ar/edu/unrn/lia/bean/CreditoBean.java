package ar.edu.unrn.lia.bean;

import ar.edu.unrn.lia.bean.datamodel.DataModel;
import ar.edu.unrn.lia.model.*;
import ar.edu.unrn.lia.security.AuthenticationBean;
import ar.edu.unrn.lia.service.CajaService;
import ar.edu.unrn.lia.service.ClienteService;
import ar.edu.unrn.lia.service.CreditoService;
import ar.edu.unrn.lia.service.InteresService;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Lucas on 22/08/2017.
 */
@Named
@Scope("view")
public class CreditoBean extends GenericBean<Credito> implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Cliente> clientes = new ArrayList<Cliente>(0);
    private double montoCuota;
    private Credito creditoSeleccionado;
    private BigDecimal saldoAdeudado;

    private Caja caja;

    @Inject
    private AuthenticationBean authenticationBean;

    @Inject
    private CreditoService entityService;

    @Inject
    private ClienteService clienteService;

    @Inject
    private InteresService interesService;


    @Inject
    CajaService cajaService;


    List<Interes> intereses = new ArrayList<>();

    @PostConstruct
    public void init() {
        setModelLazy(new DataModel<Credito>(entityService));
        setServices(entityService);
        this.intereses = interesService.getAll();
        LOG.debug("init.. " + this.getClass().getName());
    }

    public void inicio() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            setCaja(getCajaService().getLast());
            if (getId() != null) {
                setEntity(entityService.getEntityById(getId()));
                setIsNew(false);
//                calcularCuotas();
            } else {
                setEntity(new Credito());
                setIsNew(true);
//                clientes.addAll(clienteService.getAll());
            }
            super.setUrlDesde(getRequestURL());
        }
    }

    public List<Cliente> completeCliente(String apellidoNombre) {
        return clienteService.searchByApellidoNombre(apellidoNombre);
    }

    public void calcularCuotas() {
//    montoCuota = entity.calcularMontoCuotas();
        entity.calcularMontoCuotas();
    }

    public void calcularMontoCuotas(ActionEvent actionEvent) {
        calcularCuotas();
    }

    public void cargarCredito(Credito credito) {
        this.setCreditoSeleccionado(getEntityService().getEntityById(credito.getId()));
    }

    public void cargarCreditoMoroso(Credito credito, BigDecimal salgoAdeudado) {
        this.setCreditoSeleccionado(getEntityService().getEntityById(credito.getId()));
        this.setSaldoAdeudado(salgoAdeudado);
    }


    public void deleteCredito(Credito credito) {
        try {
            entityService.delete(credito);
            agregarMensaje(FacesMessage.SEVERITY_INFO, "Exito", "Credito eliminado con exito");
        } catch (Exception e) {
            agregarMensaje(FacesMessage.SEVERITY_ERROR, "Error!",
                    e.getMessage());
        }
    }

    public void agregarMensaje(FacesMessage.Severity severity, String summary, String detail) {
        FacesMessage msg = new FacesMessage(severity, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    private void addMovimientos() {
        List<Movimiento> movimientos = new ArrayList<>(0);
        movimientos.add(new Movimiento(getEntity().getCapital(), getEntity().getFechaCreacion(), Movimiento.Tipo.CREDITO.getDescripcion() + " a " + getEntity().getCliente().getApellidoNombre(), Movimiento.Tipo.CREDITO, caja));
        caja.getMovimientos().addAll(movimientos);
    }
    @Override
    public String update() {
        calcularCuotas();
        addMovimientos();
        cajaService.save(caja);
        authenticationBean.updateMovimientos();
        return super.update();
    }

    public List<String> getEstados() {
        return Arrays.asList(Estado.values()).stream().map(Estado::toString).collect(Collectors.toList());
    }

    public BigDecimal interesVencido() {
        return Cuota.INTERES_VENCIDO;
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

    public Credito getCreditoSeleccionado() {
        return creditoSeleccionado;
    }

    public void setCreditoSeleccionado(Credito creditoSeleccionado) {
        this.creditoSeleccionado = creditoSeleccionado;
    }

    public InteresService getInteresService() {
        return interesService;
    }

    public void setInteresService(InteresService interesService) {
        this.interesService = interesService;
    }

    public List<Interes> getIntereses() {
        return intereses;
    }

    public void setIntereses(List<Interes> intereses) {
        this.intereses = intereses;
    }

    public BigDecimal getSaldoAdeudado() {
        return saldoAdeudado;
    }

    public void setSaldoAdeudado(BigDecimal saldoAdeudado) {
        this.saldoAdeudado = saldoAdeudado;
    }

    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
    }

    public CajaService getCajaService() {
        return cajaService;
    }

    public void setCajaService(CajaService cajaService) {
        this.cajaService = cajaService;
    }
}
