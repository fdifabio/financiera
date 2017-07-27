package ar.edu.unrn.lia.bean.convert;

import ar.edu.unrn.lia.bean.PerfilBean;
import ar.edu.unrn.lia.model.GenericEntity;
import ar.edu.unrn.lia.model.Provincia;

import javax.el.ELContext;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "provinciaRealConverter")
public class ProvinciaRealConverter implements Converter {


    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String newValue) {
        Provincia provincia = null;
        try {
            Application application = context.getApplication();
            ELContext elContext = context.getELContext();

            PerfilBean perfilBean = application.evaluateExpressionGet(context, "#{perfilBean}", PerfilBean.class);
            provincia = perfilBean.getProvinciaReal(Long.parseLong(newValue));

        } catch (Throwable ex) {
            throw new ConverterException();
        }
        return provincia;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {

        if (value != null) {
            return value instanceof GenericEntity ? ((GenericEntity) value).getId().toString() : "";
        } else {
            return null;
        }
    }

}
