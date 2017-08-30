package ar.edu.unrn.lia.bean.convert;

import ar.edu.unrn.lia.bean.CreditoBean;
import ar.edu.unrn.lia.model.GenericEntity;
import ar.edu.unrn.lia.service.ClienteService;

import javax.el.ELContext;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(value = "clienteConverter")
public class ClienteConverter implements Converter {

    @Inject
    ClienteService clienteService;


    public Object getAsObject(FacesContext context, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
//                    ThemeService service = (ThemeService) fc.getExternalContext().getApplicationMap().get("themeService");
//                return clienteService.getEntityById(Long.parseLong(value));
                Application application = context.getApplication();
                ELContext elContext = context.getELContext();

                CreditoBean creditoBean = application.evaluateExpressionGet(context, "#{creditoBean}", CreditoBean.class);
                return creditoBean.getClienteService().getEntityById(Long.parseLong(value));
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
