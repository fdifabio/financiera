package ar.edu.unrn.lia.bean.convert;

import ar.edu.unrn.lia.bean.EProfesionalEditBean;
import ar.edu.unrn.lia.bean.EProfesionalListBean;
import ar.edu.unrn.lia.model.Comitente;

import javax.el.ELContext;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "comitenteConverter")
public class ComitenteConverter implements Converter {


    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String newValue) {
        Comitente comitente = null;
        try {
            Application application = context.getApplication();
            ELContext elContext = context.getELContext();

            EProfesionalEditBean eProfesionalEditBean = application.evaluateExpressionGet(context, "#{eProfesionalEditBean}", EProfesionalEditBean.class);
            comitente = eProfesionalEditBean.getComitente(Long.parseLong(newValue));

        } catch (Throwable ex) {
            throw new ConverterException();
        }
        return comitente;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String val = null;
        try {
            Comitente fac = (Comitente) value;
            val = Long.toString(fac.getId());
        } catch (Throwable ex) {
            throw new ConverterException();
        }
        return val;
    }

}
