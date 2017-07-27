package ar.edu.unrn.lia.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import ar.edu.unrn.lia.dao.CiudadDAO;
import ar.edu.unrn.lia.generic.GenericDaoJpaImpl;
import ar.edu.unrn.lia.model.Ciudad;
import ar.edu.unrn.lia.model.Departamento;

@Named("ciudadDao")
public class CiudadDAOImpl extends GenericDaoJpaImpl<Ciudad, Long> implements
		CiudadDAO, Serializable {

	public Predicate[] getSearchPredicates(Root<Ciudad> root,
			Map<String, String> filters) {

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();
		root.alias("entity");

		String description = filters.get("nombre");
		if (description != null && !"".equals(description)) {
			predicatesList.add(builder.like(root.<String> get("nombre"),
					'%' + description + '%'));
		}

		return predicatesList.toArray(new Predicate[predicatesList.size()]);
	}

	@Override
	public Ciudad getEntityByName(String nombre) throws NoResultException{
		Query query = this.entityManager
				.createQuery("from Ciudad c where c.nombre = :nombre");
		query.setParameter("nombre", nombre);
		return (Ciudad) query.getSingleResult();
	}

	@Override
	public List<Ciudad> getList(Long id) {

		Query query = this.entityManager.createQuery("from Ciudad c where c.departamento.id = :id");
		query.setParameter("id", id);

		return (List <Ciudad>)query.getResultList();
	}

}
