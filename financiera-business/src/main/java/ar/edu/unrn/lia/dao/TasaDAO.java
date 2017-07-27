package ar.edu.unrn.lia.dao;

import ar.edu.unrn.lia.generic.GenericDao;
import ar.edu.unrn.lia.model.Tasa;

/**
 * Created by Federico on 14/06/2017.
 */
public interface TasaDAO extends GenericDao<Tasa, Long> {
    Tasa getEntityByDescripcion(String descripcion);
}
