package ar.edu.unrn.lia.bean.convert;

import ar.edu.unrn.lia.bean.EProfesionalEditBean;
import ar.edu.unrn.lia.bean.EProfesionalListBean;
import ar.edu.unrn.lia.model.GenericEntity;
import ar.edu.unrn.lia.model.Tasa;

import javax.el.ELContext;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "tasasConverter")
public class TasasConverter implements Converter {


    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String newValue) {
        Tasa tasa = null;
        try {
            Application application = context.getApplication();
            ELContext elContext = context.getELContext();

            EProfesionalEditBean eProfesionalEditBean = application.evaluateExpressionGet(context, "#{eProfesionalEditBean}", EProfesionalEditBean.class);
            tasa = eProfesionalEditBean.getTasa(Long.parseLong(newValue));

        } catch (Throwable ex) {
            throw new ConverterException();
        }
        return tasa;
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
