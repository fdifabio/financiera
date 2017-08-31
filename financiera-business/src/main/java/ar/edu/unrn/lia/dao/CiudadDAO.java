package ar.edu.unrn.lia.dao;

import ar.edu.unrn.lia.generic.GenericDao;
import ar.edu.unrn.lia.model.Ciudad;

import javax.persistence.NoResultException;
import java.util.List;

public interface CiudadDAO extends GenericDao<Ciudad, Long> {

	Ciudad getEntityByName(String nombre) throws NoResultException;

	List<Ciudad> getList(Long id);
}
