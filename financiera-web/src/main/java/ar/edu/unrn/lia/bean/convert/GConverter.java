package ar.edu.unrn.lia.bean.convert;

import ar.edu.unrn.lia.model.BaseEntity;
import ar.edu.unrn.lia.model.GenericEntity;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mauroc79 on 26/06/2017.
 */
public class GConverter<T> implements Converter {

    @Override
    public Object getAsObject(FacesContext ctx, UIComponent comp, String value) {

        if (value == null) {
            return null;
        }
        // Recupero la lista desde el componente
        List<T> entityes = (ArrayList<T>) ((UISelectItems) comp
                .getChildren().get(0)).getValue();

        for (T r : entityes) {
            if (((GenericEntity) r).getId().toString().equals(value)) {
                return r;
            }

        }

        return null;
    }


    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
        if (value != null) {
            return value instanceof GenericEntity ? ((GenericEntity) value).getId().toString() : "";
        } else {
            return null;
        }


    }

}
