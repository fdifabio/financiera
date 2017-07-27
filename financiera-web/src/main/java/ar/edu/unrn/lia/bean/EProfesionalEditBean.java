package ar.edu.unrn.lia.bean;

import ar.edu.unrn.lia.bean.datamodel.DataModelDTO;
import ar.edu.unrn.lia.dao.ComitenteDAO;
import ar.edu.unrn.lia.dao.TasaDAO;
import ar.edu.unrn.lia.dto.EProfesionalDTO;
import ar.edu.unrn.lia.model.Comitente;
import ar.edu.unrn.lia.model.Estado;
import ar.edu.unrn.lia.model.Role;
import ar.edu.unrn.lia.model.Tasa;
import ar.edu.unrn.lia.security.AuthenticationBean;
import ar.edu.unrn.lia.service.EProfesionalReadService;
import ar.edu.unrn.lia.service.ReadOnlyService;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("eProfesionalEditBean")
@Scope("view")
public class EProfesionalEditBean extends GenericBeanList<EProfesionalDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private EProfesionalReadService eProfesionalReadService;

    @Inject
    private AuthenticationBean authenticationBean;

    @Inject
    private ComitenteDAO comitenteDAO;

    @Inject
    private TasaDAO tasaDAO;

    private List<SelectItem> comitenteItems;

    private Comitente seleccionado;

    List<Comitente> coList;

    List<Tasa> listTasas;

    public Comitente getSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(Comitente seleccionado) {
        this.seleccionado = seleccionado;
    }

    public List<SelectItem> getComitenteItems() {
        if (comitenteItems == null) {
            comitenteItems = new ArrayList<SelectItem>();
            //List<Comitente> coList = comitenteDAO.findAll();
            for (Comitente c : coList) {
                comitenteItems.add(new SelectItem(c,c.getNombre()));
            }
        }
        return comitenteItems;
    }

    public Comitente getComitente(Long id) {
        if (id == null){
            throw new IllegalArgumentException("no id provided");
        }
        for (Comitente comitente : coList){
            if (id.equals(comitente.getId())){
                return comitente;
            }
        }
        return null;
    }

    public Tasa getTasa(Long id) {
        if (id == null){
            throw new IllegalArgumentException("no id provided");
        }
        for (Tasa tasa : listTasas){
            if (id.equals(tasa.getId())){
                return tasa;
            }
        }
        return null;
    }

    public void estadoValueChange(ValueChangeEvent e){
        seleccionado = (Comitente) e.getNewValue();
    }

    @PostConstruct
    public void init() {
        setModelLazy(new DataModelDTO<EProfesionalDTO>(eProfesionalReadService));
        coList = comitenteDAO.findAll();
        listTasas = tasaDAO.findAll();

    }
    public void update() {
      System.out.print("Sel: " + this.seleccionado);

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

    public String validate() {
        try {
            authenticationBean.getPerfilAGR().validar();
            return UtilsBean.REDIRECT_CREATE;
        } catch (Exception e) {

            agregarMensaje(FacesMessage.SEVERITY_ERROR, bundleMessage("error"),
                    e.getMessage());
            return null;
        }
    }

    public boolean crearEP() {
        return getUserLogged().getRole().crearEProfesional();
    }

    public boolean mostrarColumna() {
        return (getUserLogged().getRole().equals(Role.ROLE_ADMIN) || getUserLogged().getRole().equals(Role.ROLE_SECRETARIO));
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

    public List<Tasa> getListTasas() {
        return listTasas;
    }

    public void setListTasas(List<Tasa> listTasas) {
        this.listTasas = listTasas;
    }
}