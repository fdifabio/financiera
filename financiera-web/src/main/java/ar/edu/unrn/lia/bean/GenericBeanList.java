package ar.edu.unrn.lia.bean;

import ar.edu.unrn.lia.model.GenericEntity;
import org.primefaces.component.api.UIColumn;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class GenericBeanList<T extends GenericEntity> extends Bean{

    protected LazyDataModel<T> modelLazy;

    protected T entity;

    protected List<SortMeta> sortDefault;

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
                    "Filtro: " + filtro.getKey(), filtro.getValue());
        }
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


}
