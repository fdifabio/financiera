package ar.edu.unrn.lia.bean.convert;

import ar.edu.unrn.lia.model.BaseEntity;
import ar.edu.unrn.lia.service.IGenericService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class GenericConvert<T extends BaseEntity> implements Converter {

    IGenericService<T> service;

    @Override
    public Object getAsObject(FacesContext ctx, UIComponent comp, String value) {

        if (value == null) {
            return null;
        }
        // Recupero la lista desde el componente
        return service.getEntityById(Long.parseLong(value));
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
        try {

            return value instanceof BaseEntity ? ((T) value).getId().toString() : "";
        } catch (Exception e) {
            return "";
        }
    }

    public void setService(IGenericService<T> service) {
        this.service = service;
    }


}
