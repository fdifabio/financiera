package ar.edu.unrn.lia.bean;

import org.primefaces.event.map.MarkerDragEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by Federico on 10/06/2017.
 */
@Named
@Scope("view")
public class AddMarkersView implements Serializable {

    private MapModel draggableModel;

    private Marker marker;

    private String centerGeoMap = "-40.058219,-69.0007262";

    @PostConstruct
    public void init() {
        draggableModel = new DefaultMapModel();
        LatLng coordViedma = new LatLng(-40.7849607714286, -63.01586151123047);
        marker = new Marker(coordViedma,"Viedma");
        draggableModel.addOverlay(marker);
        marker.setDraggable(true);
    }


    public String getCenterGeoMap() {
        return centerGeoMap;
    }

    public void setCenterGeoMap(String centerGeoMap) {
        this.centerGeoMap = centerGeoMap;
    }

    public MapModel getDraggableModel() {
        return draggableModel;
    }

    public void setDraggableModel(MapModel draggableModel) {
        this.draggableModel = draggableModel;
    }

    public void onMarkerDrag(MarkerDragEvent event) {
        marker = event.getMarker();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Punto movido!", "Lat:" + marker.getLatlng().getLat() + ", Lng:" + marker.getLatlng().getLng()));
    }
}
