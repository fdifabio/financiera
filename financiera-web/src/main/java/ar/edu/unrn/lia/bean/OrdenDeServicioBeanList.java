package ar.edu.unrn.lia.bean;

import ar.edu.unrn.lia.bean.datamodel.DataModelDTO;
import ar.edu.unrn.lia.dto.OrdenDeServicioDTO;
import ar.edu.unrn.lia.model.OrdenDeServicio;
import ar.edu.unrn.lia.service.OrdenDeServicioReadService;
import org.primefaces.model.SortOrder;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@Scope("view")
public class OrdenDeServicioBeanList extends GenericBeanList<OrdenDeServicioDTO> implements Serializable {

    private static final long serialVersionUID = 1L;
    private String orderDefaultFechaPagada = "ordenDeServicio.ODSfechaTX";
    private String sorter = SortOrder.DESCENDING.toString();
    private OrdenDeServicio.EstadoODS estadoSelected;

    @Inject
    private OrdenDeServicioReadService ordenDeServicioReadService;


    @PostConstruct
    public void init() {
        setEstadoSelected(OrdenDeServicio.EstadoODS.PAGADO);
        listOrdenDeServicioByRole();
    }

    /**
     * Consulta por estados dependiendo del Rol. y los estados
     */
    private void listOrdenDeServicioByRole() {
        setParametrosByBean(getUserLogged().getRole().listODSFilter(getEstadoSelected()));
        setModelLazy(new DataModelDTO<OrdenDeServicioDTO>(ordenDeServicioReadService, orderDefaultFechaPagada, sorter, parametrosByBean));

    }


    public OrdenDeServicio.EstadoODS[] getEstados() {

        OrdenDeServicio.EstadoODS[] combo = new OrdenDeServicio.EstadoODS[]{
                OrdenDeServicio.EstadoODS.FACTURADO,
                OrdenDeServicio.EstadoODS.PAGADO
        };

        return combo;
    }

    public OrdenDeServicio.EstadoODS getEstadoSelected() {
        return estadoSelected;
    }

    public void estadoValueChange(ValueChangeEvent e) {


        setEstadoSelected(OrdenDeServicio.EstadoODS.valueOf(e.getNewValue().toString()));
        //consulta por estados.
        listOrdenDeServicioByRole();
    }

    public void setEstadoSelected(OrdenDeServicio.EstadoODS estadoSelected) {
        this.estadoSelected = estadoSelected;
    }

    public void facturada(OrdenDeServicioDTO ordenDeServicioDTO) {
        try {
            ordenDeServicioReadService.facturado(ordenDeServicioDTO);
            mensajeFlash(bundleMessage("INFO.mensaje"),
                    bundleMessage("INFO.mensajeFlash"));

        } catch (Exception e) {

            agregarMensaje(FacesMessage.SEVERITY_ERROR, bundleMessage("error"),
                    e.getMessage());

        }
    }

    /**
     * muestra la opcion  de pasar a estado facturada cuando el estado de la ODS sea PAGADA
     * @param ordenDeServicioDTO
     * @return
     */
    public boolean renderOpcionFacturada(OrdenDeServicioDTO ordenDeServicioDTO){

        return  ordenDeServicioDTO.isPagada();
    }

    public OrdenDeServicioReadService getOrdenDeServicioReadService() {
        return ordenDeServicioReadService;
    }
}