package ar.edu.unrn.lia.dao;

import ar.edu.unrn.lia.generic.GenericDao;
import ar.edu.unrn.lia.model.Ciudad;
import ar.edu.unrn.lia.model.Provincia;

public interface ProvinciaDAO extends GenericDao<Provincia, Long> {

	Provincia getEntityByName(String nombre);

}
