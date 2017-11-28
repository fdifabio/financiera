package ar.edu.unrn.lia.bean;

import ar.edu.unrn.lia.bean.datamodel.DataModel;
import ar.edu.unrn.lia.model.Descuento;
import ar.edu.unrn.lia.model.Interes;
import ar.edu.unrn.lia.service.DescuentoService;
import ar.edu.unrn.lia.service.InteresService;
import org.primefaces.event.ReorderEvent;
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
 * Created by Lucas on 22/08/2017.
 */
@Named
@Scope("view")
public class PreferenciasBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private InteresService interesService;

    @Inject
    private DescuentoService descuentoService;

    private List<Interes> interesList = new ArrayList<>(0);

    private Interes selectedInteres;

    private Interes newInteres;

    private Boolean updateInteres = false;

    private List<Descuento> descuentoList = new ArrayList<>(0);

    private Descuento selectedDescuento;

    private Descuento newDescuento;

    private Boolean updateDescuento = false;


    @PostConstruct
    public void init() {
        setInteresService(interesService);
        interesList = interesService.getAll();
        setNewInteres(new Interes());
        setDescuentoService(descuentoService);
        descuentoList = descuentoService.getAll();
        setNewDescuento(new Descuento());
    }

    public void agregarMensaje(FacesMessage.Severity severity, String summary, String detail) {
        FacesMessage msg = new FacesMessage(severity, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    public void inicio() {}

    // Override Update Interes
    public void update(Interes interes) {
        try {
            interesService.save(interes);
        } catch (DataAccessException e) {
            agregarMensaje(FacesMessage.SEVERITY_ERROR, "Error!",
                    "Error al guardar!");
        } catch (Exception e) {
            agregarMensaje(FacesMessage.SEVERITY_ERROR, "Error!",
                    e.getMessage());
        }
    }
    // Override Update Interes
    public void update(Descuento descuento) {
        try {
            descuentoService.save(descuento);
        } catch (DataAccessException e) {
            agregarMensaje(FacesMessage.SEVERITY_ERROR, "Error!",
                    "Error al guardar!");
        } catch (Exception e) {
            agregarMensaje(FacesMessage.SEVERITY_ERROR, "Error!",
                    e.getMessage());
        }
    }

    /*----------------------- INTERESES ---------------------------*/
    public void createInteres() {
        getNewInteres().setOrden(getInteresList().size() + 1);
        this.update(getNewInteres());
        getInteresList().add(getNewInteres());
        setNewInteres(new Interes());
    }

    public void deleteInteres(Interes interes) {
        interesService.delete(interes);
        getInteresList().remove(interes);
    }

    public void updateInteres() {
        this.update(getSelectedInteres());
    }

    public void onRowReorderInteres(ReorderEvent event) {
        this.setUpdateInteres(true);
    }

    public void guardarOrdenInteres() {
        updateInteres = false;
        interesService.updateOrden(interesList);
    }

    /*----------------------- DESCUENTO ---------------------------*/

    public void createDescuento() {
        getNewDescuento().setOrden(getDescuentoList().size() + 1);
        this.update(getNewDescuento());
        getDescuentoList().add(getNewDescuento());
        setNewDescuento(new Descuento());
        agregarMensaje(FacesMessage.SEVERITY_INFO,"Exito","Descuento creado con exito");
    }

    public void deleteDescuento(Descuento descuento) {
        descuentoService.delete(descuento);
        getDescuentoList().remove(descuento);
    }

    public void updateDescuento() {
        this.update(getSelectedDescuento());
    }

    public void onRowReorderDescuento(ReorderEvent event) {
        this.setUpdateDescuento(true);
    }

    public void guardarOrdenDescuento() {
        updateDescuento = false;
        descuentoService.updateOrden(descuentoList);
    }

    public InteresService getInteresService() {
        return interesService;
    }

    public void setInteresService(InteresService interesService) {
        this.interesService = interesService;
    }

    public Boolean getUpdateInteres() {
        return updateInteres;
    }

    public void setUpdateInteres(Boolean updateInteres) {
        this.updateInteres = updateInteres;
    }

    public List<Interes> getInteresList() {
        return interesList;
    }

    public void setInteresList(List<Interes> interesList) {
        this.interesList = interesList;
    }

    public Interes getSelectedInteres() {
        return selectedInteres;
    }

    public void setSelectedInteres(Interes selectedInteres) {
        this.selectedInteres = selectedInteres;
    }

    public Interes getNewInteres() {
        return newInteres;
    }

    public void setNewInteres(Interes newInteres) {
        this.newInteres = newInteres;
    }

    public DescuentoService getDescuentoService() {
        return descuentoService;
    }

    public void setDescuentoService(DescuentoService descuentoService) {
        this.descuentoService = descuentoService;
    }

    public List<Descuento> getDescuentoList() {
        return descuentoList;
    }

    public void setDescuentoList(List<Descuento> descuentoList) {
        this.descuentoList = descuentoList;
    }

    public Descuento getSelectedDescuento() {
        return selectedDescuento;
    }

    public void setSelectedDescuento(Descuento selectedDescuento) {
        this.selectedDescuento = selectedDescuento;
    }

    public Descuento getNewDescuento() {
        return newDescuento;
    }

    public void setNewDescuento(Descuento newDescuento) {
        this.newDescuento = newDescuento;
    }

    public Boolean getUpdateDescuento() {
        return updateDescuento;
    }

    public void setUpdateDescuento(Boolean updateDescuento) {
        this.updateDescuento = updateDescuento;
    }
}
