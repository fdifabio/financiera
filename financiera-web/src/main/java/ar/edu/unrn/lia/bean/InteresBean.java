package ar.edu.unrn.lia.bean;

import ar.edu.unrn.lia.bean.datamodel.DataModel;
import ar.edu.unrn.lia.model.Interes;
import ar.edu.unrn.lia.service.InteresService;
import org.primefaces.context.RequestContext;
import org.primefaces.event.ReorderEvent;
import org.primefaces.event.SelectEvent;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
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
public class InteresBean extends GenericBean<Interes> implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Interes> interesList = new ArrayList<>(0);

    private Interes selectedInteres;

    private Boolean update = false;

    @Inject
    private InteresService entityService;

    @PostConstruct
    public void init() {
        setModelLazy(new DataModel<Interes>(entityService));
        setServices(entityService);
        interesList = entityService.getAll();
        setEntity(new Interes());
        LOG.debug("init.. " + this.getClass().getName());
    }

    public void inicio() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            if (getId() != null)
                setEntity(entityService.getEntityById(getId()));
            else {
                setEntity(new Interes());
            }
            super.setUrlDesde(getRequestURL());
        }
    }

    public String create() {
        getEntity().setOrden(getInteresList().size() + 1);
        super.update();
        getInteresList().add(getEntity());
        return UtilsBean.REDIRECT_PREFERENCIAS;
    }
    @Override
    public String delete(Interes interes){
        super.delete(interes);
        getInteresList().remove(interes);
        return UtilsBean.REDIRECT_PREFERENCIAS;
    }
    @Override
    public String update() {
        setEntity(this.selectedInteres);
        return super.update();
    }


    public InteresService getEntityService() {
        return entityService;
    }

    public void setEntityService(InteresService entityService) {
        this.entityService = entityService;
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

    public void onSelect(SelectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        this.setSelectedInteres((Interes) event.getObject());
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('dlg').show()");
    }

    public Boolean getUpdate() {
        return update;
    }

    public void setUpdate(Boolean update) {
        this.update = update;
    }

    public void onRowReorder(ReorderEvent event) {
        this.setUpdate(true);
    }

    public void guardarOrden() {
        int i = 1;
        for (Interes interes : this.getInteresList()
                ) {
            interes.setOrden(i);
            setEntity(interes);
            super.update();
            i++;
        }
    }

}
