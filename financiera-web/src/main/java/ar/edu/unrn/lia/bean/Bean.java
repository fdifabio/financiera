package ar.edu.unrn.lia.bean;

import ar.edu.unrn.lia.bean.util.BundleMessagei18;
import ar.edu.unrn.lia.logger.Log;
import ar.edu.unrn.lia.model.User;
import com.sun.faces.context.flash.ELFlash;
import org.slf4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class Bean {

    protected static
    @Log
    Logger LOG;

    protected String urlDesde;

    protected Map<String, String> parametrosByBean = new HashMap<>();

    protected User getUserLogged() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    protected String urlContextPathApp() {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        StringBuffer urlApp = new StringBuffer(bundleMessage("http"))
                .append(request.getServerName())
                .append(request.getLocalPort() != 0 ? ":" + request.getLocalPort() : "")
                .append(request.getContextPath());
        return urlApp.toString();

    }

    public void volver(String defecto) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        String path = ctx.getExternalContext().getRequestContextPath();
        try {
            if (urlDesde != null)
                ctx.getExternalContext().redirect(urlDesde);
            else
                ctx.getExternalContext().redirect(defecto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getRequestURL() {
        Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String url = "";
        if (request instanceof HttpServletRequest) {
            HttpServletRequest req = ((HttpServletRequest) request);
            String q = req.getQueryString();
            url = req.getRequestURI();
            url += ((q != null && !"".equals(q)) ? "?" + q : "");

            return url;
        } else {
            return url;
        }
    }

    /**
     * Utilizada en los listados de DTO
     */
    public void confirmarOperacion() {
        if (ELFlash.getFlash().get(UtilsBean.MESSAGE_OK) != null) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    (FacesMessage) ELFlash.getFlash().get(
                            UtilsBean.MESSAGE_OK));
            ELFlash.getFlash().remove(
                    ELFlash.getFlash().get(UtilsBean.MESSAGE_OK));
        }
    }

    public void vermensaje() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            if (ELFlash.getFlash().get(UtilsBean.MESSAGE_OK) != null) {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        (FacesMessage) ELFlash.getFlash().get(
                                UtilsBean.MESSAGE_OK));
                ELFlash.getFlash().remove(
                        ELFlash.getFlash().get(UtilsBean.MESSAGE_OK));
            }
        }
    }

    public void mensajeFlash(String titulo, String mensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        ELFlash.getFlash().put(UtilsBean.MESSAGE_OK,
                new FacesMessage(FacesMessage.SEVERITY_INFO, titulo, mensaje));

    }

    public void mensajeFlash(String titulo, String mensaje, FacesMessage.Severity severity) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        ELFlash.getFlash().put(UtilsBean.MESSAGE_OK,
                new FacesMessage(severity, titulo, mensaje));

    }

    public void agregarMensaje(Severity severity, String summary, String detail) {
        FacesMessage msg = new FacesMessage(severity, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    protected Map<String, String> getParametrosByBean() {
        return parametrosByBean;
    }

    protected void setParametrosByBean(Map<String, String> parametrosByBean) {
        this.parametrosByBean = parametrosByBean;
    }

    public String bundleMessage(String nombre) {
        return BundleMessagei18.getString(nombre);
    }


    protected String getUrlDesde() {
        return urlDesde;
    }

    protected void setUrlDesde(String urlDesde) {
        this.urlDesde = urlDesde;
    }
}
