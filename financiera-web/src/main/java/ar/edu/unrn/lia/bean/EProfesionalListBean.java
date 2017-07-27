package ar.edu.unrn.lia.bean;

import ar.edu.unrn.lia.bean.datamodel.DataModelDTO;
import ar.edu.unrn.lia.dto.EProfesionalDTO;
import ar.edu.unrn.lia.model.EProfesional;
import ar.edu.unrn.lia.model.Estado;
import ar.edu.unrn.lia.model.Role;
import ar.edu.unrn.lia.security.AuthenticationBean;
import ar.edu.unrn.lia.service.EProfesionalReadService;
import ar.edu.unrn.lia.service.ReadOnlyService;
import org.primefaces.model.SortOrder;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named("eProfesionalListBean")
@Scope("view")
public class EProfesionalListBean extends GenericBeanList<EProfesionalDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private EProfesionalReadService eProfesionalReadService;

    @Inject
    private AuthenticationBean authenticationBean;

    private String orderDefaultFechaCreacion = "fecha";
    private String sorterDefault = SortOrder.DESCENDING.toString();


    @PostConstruct
    public void init() {
        listEProfesionalDefault();
    }

    private void listEProfesionalDefault() {
        setModelLazy(new DataModelDTO<EProfesionalDTO>(eProfesionalReadService, orderDefaultFechaCreacion, sorterDefault, parametrosByBean));

    }

    public Estado[] getEstados() {
        return Estado.values();
    }

    public ReadOnlyService<EProfesionalDTO> geteProfesionalReadService() {
        return eProfesionalReadService;
    }

    public AuthenticationBean getAuthenticationBean() {
        return authenticationBean;
    }

    public boolean crearEP() {
        return getUserLogged().getRole().crearEProfesional();
    }

    /**
     * Renderiza solo cuando se loggeo un Rol SECRETARIO o ADMIN
     * @return
     */
    public boolean renderColumnSEC_ADMIN() {
        return (getUserLogged().getRole().equals(Role.ROLE_ADMIN) || getUserLogged().getRole().equals(Role.ROLE_SECRETARIO));
    }

    public boolean renderIngresoComprobantePago(EProfesionalDTO eProfesionalDTO) {
        return (getUserLogged().getRole().equals(Role.ROLE_AGR) && eProfesionalDTO.isGenerada());
    }

    public boolean renderActionDelete(EProfesionalDTO eProfesionalDTO) {

        return (getUserLogged().getRole().equals(Role.ROLE_AGR) && eProfesionalDTO.isPendiente());

    }

    public boolean renderActionEdit(EProfesionalDTO eProfesionalDTO) {

        return (getUserLogged().getRole().equals(Role.ROLE_AGR) && eProfesionalDTO.isPendiente());
    }

    public boolean renderActionDownloadEP(EProfesionalDTO eProfesionalDTO) {

        return (getUserLogged().getRole().equals(Role.ROLE_AGR) && eProfesionalDTO.isGenerada());
    }

    public boolean renderActionDownloadODS(EProfesionalDTO eProfesionalDTO){

        return (getUserLogged().getRole().equals(Role.ROLE_AGR) && !eProfesionalDTO.isPendienteDePago());

    }

    public boolean mostrarColumnaParaAGR() {
        return (getUserLogged().getRole().equals(Role.ROLE_AGR));
    }

    public String validate() {
        try {
            authenticationBean.getPerfilAGR().validar();
            return UtilsBean.REDIRECT_CREATE;
        } catch (Exception e) {

            agregarMensaje(FacesMessage.SEVERITY_WARN, bundleMessage("Advertencia"),
                    e.getMessage());
            return null;
        }
    }

    public String delete(EProfesionalDTO entity) {
        try {
            eProfesionalReadService.delete(entity);
            mensajeFlash(bundleMessage("INFO.mensaje"),
                    bundleMessage("INFO.mensajeFlash"));

            return UtilsBean.REDIRECT_SEARCH;

        } catch (Exception e) {

            agregarMensaje(FacesMessage.SEVERITY_ERROR, bundleMessage("error"),
                    e.getMessage());

            return null;
        }
    }

}