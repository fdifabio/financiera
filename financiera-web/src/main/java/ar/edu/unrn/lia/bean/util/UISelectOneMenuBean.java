package ar.edu.unrn.lia.bean.util;

import ar.edu.unrn.lia.model.Ciudad;
import ar.edu.unrn.lia.model.Provincia;
import ar.edu.unrn.lia.service.CiudadService;
import ar.edu.unrn.lia.service.ProvinciaService;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named(value = "uiSelectOneMenuBean")
@Scope("session")
public class UISelectOneMenuBean implements Serializable {


    @Inject
    private ProvinciaService provinciaService;

    @Inject
    private CiudadService ciudadService;


    List<Provincia> listProvincias = new ArrayList<>();

    List<Ciudad> listCiudades = new ArrayList<Ciudad>();

    @PostConstruct
    public void init() {
        List<Provincia> provincias = provinciaService.getAll();
        setListProvincias(provincias);


        List<Ciudad> ciudades = ciudadService.getAll();
        setListCiudades(ciudades);

    }

    public Provincia getProvincia(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("no id provided");
        }
        for (Provincia provincia : getListProvincias()) {
            if (id.equals(provincia.getId())) {
                return provincia;
            }
        }
        return new Provincia();
    }


    public Ciudad getCiudad(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("no id provided");
        }

        if (id.equals(-1l)) {
            return new Ciudad();
        }
        for (Ciudad ciudad : getListCiudades()) {
            if (id.equals(ciudad.getId())) {
                return ciudad;
            }
        }
        return null;
    }


    //---------------G/S----------------------------------


    public ProvinciaService getProvinciaService() {
        return provinciaService;
    }

    public void setProvinciaService(ProvinciaService provinciaService) {
        this.provinciaService = provinciaService;
    }

    public CiudadService getCiudadService() {
        return ciudadService;
    }

    public void setCiudadService(CiudadService ciudadService) {
        this.ciudadService = ciudadService;
    }

    public List<Provincia> getListProvincias() {
        return listProvincias;
    }

    private void setListProvincias(List<Provincia> listProvincias) {
        this.listProvincias = listProvincias;
    }

    public List<Ciudad> getListCiudades() {
        return listCiudades;
    }

    private void setListCiudades(List<Ciudad> lisCiudades) {
        this.listCiudades = lisCiudades;
    }
}
