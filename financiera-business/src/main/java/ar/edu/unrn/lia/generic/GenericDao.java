package ar.edu.unrn.lia.generic;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface GenericDao<T, PK extends Serializable> {
	public EntityManager getEntityManager();

	void create(T t);

	T read(PK id);

	T update(T t);

	void delete(T t);

	void delete(PK id);

	public List<T> findAll();

	public Long count(Predicate[] where);

	public Root<T> rootCount();

	public Predicate[] getSearchPredicates(Root<T> root,
										   Map<String, String> filters);

	public List<T> listwithPag(Predicate[] where, Integer page,
							   Integer pagesize, String sortField, Boolean asc, boolean distinct);

	public List<T> listwithPag(Predicate[] where, Integer page,
							   Integer pagesize, Map<String, Boolean> sorts);

	public List<T> findByQuery(String query, String propertyFilter,
							   String orderDirection);

}