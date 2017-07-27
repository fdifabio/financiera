package ar.edu.unrn.lia.bean;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import ar.edu.unrn.lia.model.GenericEntity;
import org.primefaces.component.api.UIColumn;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;

import com.sun.faces.context.flash.ELFlash;

import ar.edu.unrn.lia.bean.util.BundleMessagei18;
import ar.edu.unrn.lia.logger.Log;
import ar.edu.unrn.lia.service.IGenericService;

public class GenericBean<T extends GenericEntity> extends Bean  {

    protected LazyDataModel<T> modelLazy;

    protected T entity;

    protected IGenericService<T> services;

    protected Long id;

    protected List<SortMeta> sortDefault;

    protected String tituloMensaje;
    protected String mensaje;

    protected boolean isNew;

    public String update() {
        try {
            services.save(getEntity());
            LOG.debug("Guardando " + getEntity());
            if (getIsNew()) {
                mensajeFlash(bundleMessage("INFO.mensaje"),
                        bundleMessage("INFO.mensajeFlash"));
                setIsNew(false);
            } else
                mensajeFlash(bundleMessage("INFO.mensaje"),
                        bundleMessage("INFO.mensajeFlash"));
            return UtilsBean.REDIRECT_SEARCH;
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

    public String delete(Long id) {
        try {
            services.delete(entity);
            mensajeFlash(bundleMessage("INFO.mensaje"),
                    bundleMessage("INFO.mensajeFlash"));

            return UtilsBean.REDIRECT_SEARCH;

        } catch (DataAccessException e) {
            agregarMensaje(FacesMessage.SEVERITY_ERROR, bundleMessage("error"),
                    e.getMessage());

            return null;

        } catch (Exception e) {

            agregarMensaje(FacesMessage.SEVERITY_ERROR, bundleMessage("error"),
                    e.getMessage());

            return null;
        }
    }

    public String delete(T entity) {
        try {
            services.delete(entity);
            mensajeFlash(bundleMessage("INFO.mensaje"),
                    bundleMessage("INFO.mensajeFlash"));

            return UtilsBean.REDIRECT_SEARCH;

        } catch (DataAccessException e) {
            agregarMensaje(FacesMessage.SEVERITY_ERROR, bundleMessage("error"),
                    e.getMessage());

            return null;

        } catch (Exception e) {

            agregarMensaje(FacesMessage.SEVERITY_ERROR, bundleMessage("error"),
                    e.getMessage());

            return null;
        }
    }

    public LazyDataModel<T> getList() {
        return getModelLazy();
    }

    public SortMeta defaultFiltro(String idColumn, String campo, SortOrder order) {
        UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();

        SortMeta filtro = new SortMeta();
        filtro.setSortBy((UIColumn) viewRoot.findComponent(idColumn));
        filtro.setSortField(campo);
        filtro.setSortOrder(order);
        return filtro;
    }

    public void filterListener(org.primefaces.event.data.FilterEvent e) {
        Iterator entries = e.getFilters().entrySet().iterator();
        while (entries.hasNext()) {
            Entry<String, String> filtro = (Entry) entries.next();
            agregarMensaje(FacesMessage.SEVERITY_INFO,
                    bundleMessage("filtro") + filtro.getKey(), filtro.getValue());
        }
    }

    public IGenericService<T> getServices() {
        return services;
    }

    public void setServices(IGenericService<T> services) {
        this.services = services;
    }

    public List<SortMeta> getSortDefault() {
        return sortDefault;
    }

    public void setSortDefault(List<SortMeta> sortDefault) {
        this.sortDefault = sortDefault;
    }

    public LazyDataModel<T> getModelLazy() {
        return modelLazy;
    }

    public void setModelLazy(LazyDataModel<T> modelLazy) {
        this.modelLazy = modelLazy;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }


}
