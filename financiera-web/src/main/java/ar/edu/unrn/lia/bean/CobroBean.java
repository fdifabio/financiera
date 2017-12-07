package ar.edu.unrn.lia.bean;

import ar.edu.unrn.lia.bean.datamodel.DataModel;
import ar.edu.unrn.lia.model.*;
import ar.edu.unrn.lia.security.AuthenticationBean;
import ar.edu.unrn.lia.service.CajaService;
import ar.edu.unrn.lia.service.ClienteService;
import ar.edu.unrn.lia.service.CobroService;
import ar.edu.unrn.lia.service.CreditoService;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Lucas on 22/08/2017.
 */
@Named
@Scope("view")
public class CobroBean extends GenericBean<Cobro> implements Serializable {

    private static final long serialVersionUID = 1L;
    private Credito credito = new Credito();
    private BigDecimal saldoCuenta = BigDecimal.ZERO;
    private boolean usaSaldoCuenta = false;

    @Inject
    private AuthenticationBean authenticationBean;

    private Caja caja;

    @Inject
    private CobroService entityService;

    @Inject
    private ClienteService clienteService;

    @Inject
    private CreditoService creditoService;

    @Inject
    CajaService cajaService;


    private List<Cuota> cuotasPendientes = new ArrayList<>(0);
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
            setCaja(getCajaService().getLast());
            if (getId() != null) {
                setEntity(entityService.getEntityById(getId()));
            } else {
                setEntity(new Cobro());
            }
            super.setUrlDesde(getRequestURL());
        }
    }

    private void cargarCredito() {
        if (credito.getId() != null) {
            credito = creditoService.getEntityById(credito.getId());
            cuotasPendientes = credito.getListCuotas().stream().filter(c -> !c.getEstado().equals(Cuota.Estado.SALDADO)).collect(Collectors.toList());
        }
    }

    public List<Cliente> completeCliente(String apellidoNombre) {
        return clienteService.searchByApellidoNombre(apellidoNombre);
    }

    public void onRowSelect(SelectEvent event) {
       /* FacesMessage msg = new FacesMessage("Cuota seleccionada Nro.: ", ((Cuota) event.getObject()).getNro().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);*/
    }

    public void onRowUnselect(UnselectEvent event) {
       /* FacesMessage msg = new FacesMessage("Cuota deseleccionada Nro.: ", ((Cuota) event.getObject()).getNro().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);*/
    }

    public void onToggleSelect() {
    }

    public BigDecimal montoAcumulado() {
        return selectedCuotas.stream().map(Cuota::getMontoAPagar).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal total() {
        return montoAcumulado().add(saldoCuenta == null ? BigDecimal.ZERO : saldoCuenta).subtract(usaSaldoCuenta ? credito.getSaldoCuenta() : BigDecimal.ZERO);
    }

    public void onCuotaSelect(Cuota c) {
        if (caja != null && caja.habilitada()) {
            c.setEstadoAnterior(c.getEstado());
            c.setSaldoAPagarAnterior(c.getSaldoAPagar());
            c.setEstado(Cuota.Estado.SALDADO);
            if (c.getEstadoAnterior().equals(Cuota.Estado.VENCIDO) || c.getEstadoAnterior().equals(Cuota.Estado.PARCIALMENTE_SALDADO))
                c.setInteresVencido(Cuota.INTERES_VENCIDO);

            c.setMontoAPagar(c.monto());
            c.getCobros().add(new Cobro(c.monto(), new Date(), "", c));
            selectedCuotas.add(c);
            cuotasPendientes.remove(c);
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Caja deshabilitada!", "Para poder registar un cobro deberá habilitar la caja.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public void onCuotaUnSelect(Cuota c) {
        selectedCuotas.remove(c);
        c.setInteresDescuento(BigDecimal.ZERO);
        c.setInteresVencido(BigDecimal.ZERO);
        c.setEstado(c.getEstadoAnterior());
        c.setSaldoAPagar(c.getSaldoAPagarAnterior());
        cuotasPendientes.add(c);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicíon cancelada", "Se cancelo la edición de la cuota Nro. " + ((Cuota) event.getObject()).getNro());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowEdit(RowEditEvent event) {
        Cuota cuota = (Cuota) event.getObject();
        if (cuota.getEstadoAnterior().equals(Cuota.Estado.ADEUDADO))
            cuota.setMontoAPagar(cuota.monto());
        cuota.getCobros().stream().reduce((first, second) -> second)
                .get().setMonto(cuota.getMontoAPagar());
        //Evaluo si la cuota se pagara total o parcial
        if (cuota.getMontoAPagar().equals(cuota.monto()))
            cuota.setEstado(Cuota.Estado.SALDADO);
        else cuota.setEstado(Cuota.Estado.PARCIALMENTE_SALDADO);
        cuota.setSaldoAPagar(cuota.monto().subtract(cuota.getMontoAPagar()));
        FacesMessage msg = new FacesMessage("Cuota editada", "Se editó la cuota Nro. " + cuota.getNro());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void listenerUpdate(int index) {
        selectedCuotas.get(index).setMontoAPagar(selectedCuotas.get(index).monto());
        RequestContext.getCurrentInstance().update("crear:listSelected:" + index + ":monto");
        RequestContext.getCurrentInstance().update("crear:listSelected:" + index + ":montoAPagar");
    }

    @Override
    public String update() {
        //TODO:Deberia tener en cuenta el SaldoCuenta Nuevo y el Anterior!!!!.
        if (usaSaldoCuenta) credito.setSaldoCuenta(BigDecimal.ZERO);
        credito.setSaldoCuenta(credito.getSaldoCuenta().add(this.saldoCuenta));

        //Refleja los cobros en movimientos de caja
        calcularMovimientos();

        try {
            creditoService.save(credito);
            cajaService.save(caja);
            authenticationBean.updateMovimientos();

            LOG.debug("Guardando " + getEntity());
            if (getIsNew()) {
                mensajeFlash(bundleMessage("INFO.mensaje"),
                        bundleMessage("INFO.mensajeFlash"));
                setIsNew(false);
            } else
                mensajeFlash(bundleMessage("INFO.mensaje"),
                        bundleMessage("INFO.mensajeFlash"));
            return UtilsBean.REDIRECT_SEARCH_CREDITO;
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

    private void calcularMovimientos() {
        List<Movimiento> movimientos = new ArrayList<>(0);
        credito.getListCuotas().stream().forEach(c -> c.getCobros().stream().filter(co -> co.getId() == null).forEach(co -> movimientos.add(new Movimiento(co.getMonto(), co.getFecha(), "Cobro", Movimiento.Tipo.INGRESO, caja))));
        caja.getMovimientos().addAll(movimientos);
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

    public List<Cuota> getCuotasPendientes() {
        return cuotasPendientes;
    }

    public void setCuotasPendientes(List<Cuota> cuotasPendientes) {
        this.cuotasPendientes = cuotasPendientes;
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

    public AuthenticationBean getAuthenticationBean() {
        return authenticationBean;
    }

    public void setAuthenticationBean(AuthenticationBean authenticationBean) {
        this.authenticationBean = authenticationBean;
    }
}
