package ar.edu.unrn.lia.dao.impl;

import java.io.Serializable;

import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ar.edu.unrn.lia.dao.ParameterDAO;
import ar.edu.unrn.lia.generic.GenericDaoJpaImpl;
import ar.edu.unrn.lia.model.Parameter;

@Named("parameterDao")
public class ParameterDAOImpl extends GenericDaoJpaImpl<Parameter, Long> implements ParameterDAO, Serializable {

	@Override
	public Parameter getEntityByKey(String key) throws NoResultException {
		// TODO Auto-generated method stub
		Query query = this.entityManager.createQuery("from Parameter p where p.key = :key");
		query.setParameter("key", key);

		return (Parameter) query.getSingleResult();

	}
}