package ar.edu.unrn.lia.bean;

import ar.edu.unrn.lia.bean.datamodel.DataModel;
import ar.edu.unrn.lia.model.*;
import ar.edu.unrn.lia.security.AuthenticationBean;
import ar.edu.unrn.lia.service.*;
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
    private BigDecimal descuento = BigDecimal.ZERO;
    private boolean usaSaldoCuenta = false;
    boolean agregarsaldo = false;

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
    @Inject
    private CuotaService cuotaService;

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
        return montoAcumulado().add(saldoCuenta == null ? BigDecimal.ZERO : saldoCuenta).subtract(usaSaldoCuenta ? credito.getSaldoCuenta() : BigDecimal.ZERO).subtract(descuento == null ? BigDecimal.ZERO : descuento);
    }

    public void onCuotaSelect(Cuota c) {
        BigDecimal montoaux = BigDecimal.ZERO;

        if (caja != null && caja.habilitada()) {
            c.setFechaPago(new Date());
            c.setEstadoAnterior(c.getEstado());
            c.setSaldoAPagarAnterior(c.getSaldoAPagar());
            c.setEstado(Cuota.Estado.SALDADO);
            // montoaux=c.getSaldoAPagar();
            if (c.getEstadoAnterior().equals(Cuota.Estado.VENCIDO) || c.getEstadoAnterior().equals(Cuota.Estado.PARCIALMENTE_SALDADO)) {
                c.setInteresVencido(Cuota.INTERES_VENCIDO);
            }
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
        // c.setSaldoAPagar(c.getSaldoAPagarAnterior());
        c.getCobros().remove(new Cobro(c.monto(), new Date(), "", c));
        cuotasPendientes.add(c);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicíon cancelada", "Se cancelo la edición de la cuota Nro. " + ((Cuota) event.getObject()).getNro());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowEdit(RowEditEvent event) {
        Cuota cuota = (Cuota) event.getObject();
        cuota.monto();
        if (cuota.getEstadoAnterior().equals(Cuota.Estado.ADEUDADO))
            cuota.setMontoAPagar(cuota.monto());
        cuota.getCobros().stream().reduce((first, second) -> second)
                .get().setMonto(cuota.getMontoAPagar());
        //Evaluo si la cuota se pagara total o parcial
        if (cuota.getMontoAPagar().equals(cuota.monto()))
            cuota.setEstado(Cuota.Estado.SALDADO);
        else
            cuota.setEstado(Cuota.Estado.PARCIALMENTE_SALDADO);
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

        //Refleja los cobros en movimientos de caja
        credito.setFechaUltimoPago(new Date());
        calcularMovimientos();
        if (usaSaldoCuenta) credito.setSaldoCuenta(BigDecimal.ZERO);
        credito.setSaldoCuenta(credito.getSaldoCuenta().add(this.saldoCuenta));

        try {
//            creditoService.save(credito);
//            cajaService.save(caja);
            entityService.registarCobro(credito, caja);
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
        credito.getListCuotas().stream().forEach(c -> c.getCobros().stream().filter(
                co -> co.getId() == null).forEach(co -> {
            if (usaSaldoCuenta && credito.getSaldoCuenta().compareTo(BigDecimal.ZERO) > 0) {
                if ((descuento.compareTo(BigDecimal.ZERO) > 0))
                    movimientos.add(new Movimiento(co.getMonto().subtract(credito.getSaldoCuenta().add(descuento)), co.getFecha(), "Cobro de cuota " + c.getNro() + "/" + credito.getCuotas() + " a " + credito.getCliente().getApellidoNombre() + ". Usa saldo a cuenta por " + credito.getSaldoCuenta() + ". Se desconto " + descuento + ". Estado: " + c.getEstado().getDescripcion(), Movimiento.Tipo.COBRO, caja));
                else
                    movimientos.add(new Movimiento(co.getMonto().subtract(credito.getSaldoCuenta()), co.getFecha(), "Cobro de cuota " + c.getNro() + "/" + credito.getCuotas() + " a " + credito.getCliente().getApellidoNombre() + ". Usa saldo a cuenta por " + credito.getSaldoCuenta() + ". Estado: " + c.getEstado().getDescripcion(), Movimiento.Tipo.COBRO, caja));

                //   movimientos.add(new Movimiento(credito.getSaldoCuenta(), co.getFecha(), "Usa saldo a cuenta " + credito.getCliente().getApellidoNombre(), Movimiento.Tipo.EGRESO, caja));
                usaSaldoCuenta = false;
                credito.setSaldoCuenta(BigDecimal.ZERO);

            } else if (descuento.compareTo(BigDecimal.ZERO) > 0) {
                movimientos.add(new Movimiento(co.getMonto().subtract(descuento), co.getFecha(), "Cobro de cuota " + c.getNro() + "/" + credito.getCuotas() + " a " + credito.getCliente().getApellidoNombre() + ". Se desconto " + descuento + ". Estado: " + c.getEstado().getDescripcion(), Movimiento.Tipo.COBRO, caja));
                //  movimientos.add(new Movimiento(descuento, co.getFecha(), " Descuento a " + credito.getCliente().getApellidoNombre(), Movimiento.Tipo.EGRESO, caja));
                usaSaldoCuenta = false;
                credito.setSaldoCuenta(BigDecimal.ZERO);
            } else if (saldoCuenta.compareTo(BigDecimal.ZERO) > 0 && !agregarsaldo) {
                movimientos.add(new Movimiento(co.getMonto(), co.getFecha(), "Cobro de cuota " + c.getNro() + "/" + credito.getCuotas() + " a " + credito.getCliente().getApellidoNombre() + ". Estado: " + c.getEstado().getDescripcion(), Movimiento.Tipo.COBRO, caja));
                movimientos.add(new Movimiento(saldoCuenta, co.getFecha(), "Agrega saldo a cuenta " + credito.getCliente().getApellidoNombre(), Movimiento.Tipo.INGRESO, caja));
                agregarsaldo = true;
            } else
                movimientos.add(new Movimiento(co.getMonto(), co.getFecha(), "Cobro de cuota " + c.getNro() + "/" + credito.getCuotas() + " a " + credito.getCliente().getApellidoNombre() + ". Estado: " + c.getEstado().getDescripcion(), Movimiento.Tipo.COBRO, caja));
        }));
        if (selectedCuotas.isEmpty() && !agregarsaldo) {
            //Todo agregar el editar Cuota
            if (credito.existenCuotasVencidas()) {

                Cuota cuotaven;
                credito.setSaldoCuenta(credito.getSaldoCuenta().add(getSaldoCuenta()));

                cuotaven = credito.getListCuotasVencidas().get(0);
                cuotaven.setFechaPago(new Date());
                cuotaven.setEstadoAnterior(cuotaven.getEstado());
                cuotaven.setSaldoAPagarAnterior(cuotaven.getSaldoAPagar());
                cuotaven.setSaldoAPagar(cuotaven.monto());
                cuotaService.save(cuotaven);
                credito.setSaldoCuenta(BigDecimal.ZERO);
                setSaldoCuenta(BigDecimal.ZERO);

            }
            movimientos.add(new Movimiento(saldoCuenta, new Date(), "Agrega saldo a cuenta " + credito.getCliente().getApellidoNombre(), Movimiento.Tipo.INGRESO, caja));
            agregarsaldo = true;
        }
        caja.getMovimientos().addAll(movimientos);
    }

    public void resetSaldoCuenta() {
        saldoCuenta = BigDecimal.ZERO;
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

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public boolean deshabilitarBotonGuardar() {
        return selectedCuotas.isEmpty() && saldoCuenta.equals(BigDecimal.ZERO);
    }
}
