package ar.edu.unrn.lia.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class Coordenada {

    private Double lat = -40.7849607714286;

    private Double lng = -63.01586151123047;

    public Coordenada() {
        super();
    }

    public Coordenada(Double lat, Double lng) {
        super();
        this.lat = lat;
        this.lng = lng;
    }


    public Coordenada lat(Double lat) {
        this.lat = lat;
        return this;
    }

    public Coordenada lng(Double lng) {
        this.lng = lng;
        return this;
    }


    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }


    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

}

