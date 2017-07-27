package ar.edu.unrn.lia.bean;

import ar.edu.unrn.lia.bean.convert.GenericConvert;
import ar.edu.unrn.lia.bean.datamodel.DataModel;
import ar.edu.unrn.lia.model.Tasa;
import ar.edu.unrn.lia.service.TasaService;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by Federico on 14/06/2017.
 */
@Named
@Scope("view")
public class TasaBean extends GenericBean<Tasa> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private TasaService entityService;

    @PostConstruct
    public void init() {
        setModelLazy(new DataModel<Tasa>(entityService));
        setServices(entityService);
    }


    public void inicio() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            if (getId() != null) {
                setEntity(entityService.getEntityById(getId()));
            } else {
                setEntity(new Tasa());
            }
        }
    }

    @Override
    public String update() {
        super.update();
        return UtilsBean.REDIRECT_SEARCH;
    }

}
