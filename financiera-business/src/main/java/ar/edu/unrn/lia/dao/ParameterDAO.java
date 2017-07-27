package ar.edu.unrn.lia.dao;

import javax.persistence.NoResultException;

import ar.edu.unrn.lia.generic.GenericDao;
import ar.edu.unrn.lia.model.Parameter;

public interface ParameterDAO extends GenericDao<Parameter, Long> {
	Parameter getEntityByKey(String key) throws NoResultException;

}
