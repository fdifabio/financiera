package ar.edu.unrn.lia.service;

import ar.edu.unrn.lia.model.Tasa;

/**
 * Created by Federico on 14/06/2017.
 */
public interface TasaService extends IGenericService<Tasa> {

    public Tasa findByDescripcion(String descripcion);
}
