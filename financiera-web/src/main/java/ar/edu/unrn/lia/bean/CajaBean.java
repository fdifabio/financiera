package ar.edu.unrn.lia.bean;

import ar.edu.unrn.lia.bean.datamodel.DataModel;
import ar.edu.unrn.lia.model.Caja;
import ar.edu.unrn.lia.model.Movimiento;
import ar.edu.unrn.lia.security.AuthenticationBean;
import ar.edu.unrn.lia.service.CajaService;
import ar.edu.unrn.lia.service.ClienteService;
import ar.edu.unrn.lia.service.MovimientoService;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
    private MovimientoService movimientoService;

    @Inject
    private AuthenticationBean authenticationBean;

    @Inject
    private ClienteService clienteService;

    private Movimiento movimiento;

    private Double monto;

    private Double ingreso;

    private Double egreso;
    private String descripcion;

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

    public Caja getLast() {
        return entityService.getLast();
    }

    public String habilitarCaja() {
        entityService.habilitarCaja(new Caja(new Date()), new Movimiento(BigDecimal.valueOf(monto), new Date(), "Saldo inicial", Movimiento.Tipo.INGRESO));
        authenticationBean.updateMovimientos();
        agregarMensaje(FacesMessage.SEVERITY_INFO, "Caja habilitada", "Monto habilitado: $" + monto);
        return UtilsBean.REDIRECT_HOME;
    }

    public String ingreso() {
        Movimiento movimiento = new Movimiento(BigDecimal.valueOf(ingreso), new Date(), descripcion, Movimiento.Tipo.INGRESO);
        movimiento.setCaja(getLast());
        movimientoService.save(movimiento);
        authenticationBean.updateMovimientos();
        agregarMensaje(FacesMessage.SEVERITY_INFO, "Ingreso registrado", "Ingreso registrado con exito!");
        return UtilsBean.REDIRECT_HOME;
    }

    public String egreso() {
        Movimiento movimiento = new Movimiento(BigDecimal.valueOf(egreso), new Date(), descripcion, Movimiento.Tipo.EGRESO);
        movimiento.setCaja(getLast());
        movimientoService.save(movimiento);
        authenticationBean.updateMovimientos();
        agregarMensaje(FacesMessage.SEVERITY_INFO, "Egreso registrado", "Egreso registrado con exito!");
        return UtilsBean.REDIRECT_HOME;
    }

    public String deshabilitarCaja() {
        entityService.cerrarCaja(entityService.getLast());
        agregarMensaje(FacesMessage.SEVERITY_INFO, "Caja deshabilitada", "Caja deshabilitada con exito!");
        return UtilsBean.REDIRECT_HOME;
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

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Double getIngreso() {
        return ingreso;
    }

    public void setIngreso(Double ingreso) {
        this.ingreso = ingreso;
    }

    public Double getEgreso() {
        return egreso;
    }

    public void setEgreso(Double egreso) {
        this.egreso = egreso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public MovimientoService getMovimientoService() {
        return movimientoService;
    }

    public void setMovimientoService(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    public AuthenticationBean getAuthenticationBean() {
        return authenticationBean;
    }

    public void setAuthenticationBean(AuthenticationBean authenticationBean) {
        this.authenticationBean = authenticationBean;
    }

    public ClienteService getClienteService() {
        return clienteService;
    }

    public void setClienteService(ClienteService clienteService) {
        this.clienteService = clienteService;
    }
}
