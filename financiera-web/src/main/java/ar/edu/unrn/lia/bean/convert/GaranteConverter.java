package ar.edu.unrn.lia.bean.convert;

import ar.edu.unrn.lia.bean.CreditoBean;
import ar.edu.unrn.lia.model.Garante;
import ar.edu.unrn.lia.model.GenericEntity;
import ar.edu.unrn.lia.service.GaranteService;

import javax.el.ELContext;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(value = "garanteConverter")
public class GaranteConverter implements Converter {

    @Inject
    GaranteService garanteService;


    public Object getAsObject(FacesContext context, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {

                Application application = context.getApplication();
                ELContext elContext = context.getELContext();

                CreditoBean creditoBean = application.evaluateExpressionGet(context, "#{creditoBean}", CreditoBean.class);
                return creditoBean.getGaranteService().getEntityById(Long.parseLong(value));
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        } else {
            return null;
        }
    }

    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        if (value != null) {
            return value instanceof GenericEntity ? ((GenericEntity) value).getId().toString() : "";
        } else {
            return null;
        }
    }

}
