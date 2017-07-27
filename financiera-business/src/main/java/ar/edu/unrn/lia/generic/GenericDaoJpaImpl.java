package ar.edu.unrn.lia.generic;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class GenericDaoJpaImpl<T, PK extends Serializable> implements GenericDao<T, PK> {

	protected Class<T> entityClass;
	// TODO
	// http://forum.springsource.org/showthread.php?74277-Hibernate-lazy-initialization-in-JSF-Spring

	@PersistenceContext
	protected EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public GenericDaoJpaImpl() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
	}

	@Override
	public void create(T t) {
		this.entityManager.persist(t);
	}

	@Override
	public T read(PK id) {
		return this.entityManager.find(entityClass, id);
	}

	@Override
	public T update(T t) {

		return this.entityManager.merge(t);
	}

	@Override
	public void delete(T t) {
		this.entityManager.remove(this.entityManager.merge(t));
	}

	@Override
	public void delete(PK id) {
		this.entityManager.remove(this.entityManager.find(entityClass, id));
	}

	@SuppressWarnings("unchecked")
	public List<T> findByQuery(String query, String propertyFilter, String orderDirection) {
		try {
			if (query == null) {
				query = "";
			}
			query = "%" + query.toString().trim().toUpperCase() + "%";

			return entityManager
					.createQuery("from " + entityClass.getName() + " as w " + " where UPPER(w." + propertyFilter
							+ ") like :query " + "order by w." + propertyFilter + " " + orderDirection)
					.setParameter("query", query).setMaxResults(20).setHint("org.hibernate.cacheable", Boolean.TRUE)
					.setHint("org.hibernate.readOnly", Boolean.TRUE).getResultList();
		} catch (Exception e) {
			return new ArrayList<T>();
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll() {
		return this.entityManager.createQuery("from " + this.entityClass.getName()).getResultList();
	}

	public Root<T> rootCount() {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
		return countCriteria.from(entityClass);
	}

	public List<T> listwithPag(Predicate[] where, Integer page, Integer pagesize, String sortField, Boolean asc,
							   boolean distinct) {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(entityClass);
		criteria.distinct(distinct);
		Root<T> root = criteria.from(entityClass);
		root.alias("entity");

		Order order = orderByQuery(sortField, asc, builder, root);

		TypedQuery<T> query = this.entityManager.createQuery(criteria.select(root).where(where).orderBy(order));
		query.setFirstResult(page).setMaxResults(pagesize);

		return query.getResultList();
	}

	protected Order orderByQuery(String sortField, Boolean asc, CriteriaBuilder builder, Root<T> root) {
		Order order;
		// TODO root.get(orders[0]).get(orders[1]) Mejor Solucion
		String[] orders = sortField.split("\\.");

		if (orders.length == 1) {
			if (asc)
				order = builder.asc(root.get(sortField));
			else
				order = builder.desc(root.get(sortField));
		} else if (orders.length == 2) {
			if (asc)
				order = builder.asc(root.get(orders[0]).get(orders[1]));
			else
				order = builder.desc(root.get(orders[0]).get(orders[1]));
		} else {
			if (asc)
				order = builder.asc(root.get(orders[0]).get(orders[1]).get(orders[2]));
			else
				order = builder.desc(root.get(orders[0]).get(orders[1]).get(orders[2]));
		}
		return order;
	}

	public List<T> listwithPag(Predicate[] where, Integer page, Integer pagesize, Map<String, Boolean> sorts) {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(entityClass);
		criteria.distinct(true);
		Root<T> root = criteria.from(entityClass);
		root.alias("entity");
		List<Order> order = new ArrayList<Order>(0);
		Iterator entries = sorts.entrySet().iterator();
		while (entries.hasNext()) {
			Entry<String, Boolean> thisEntry = (Entry) entries.next();
			String sortField = thisEntry.getKey();
			Boolean orden = thisEntry.getValue();

			String[] orders = sortField.split("\\.");

			if (orders.length == 1) {
				if (orden)
					order.add(builder.asc(root.get(sortField)));
				else
					order.add(builder.desc(root.get(sortField)));
			} else if (orders.length == 2) {
				if (orden)
					order.add(builder.asc(root.get(orders[0]).get(orders[1])));
				else
					order.add(builder.desc(root.get(orders[0]).get(orders[1])));
			} else {
				if (orden)
					order.add(builder.asc(root.get(orders[0]).get(orders[1]).get(orders[2])));
				else
					order.add(builder.desc(root.get(orders[0]).get(orders[1]).get(orders[2])));
			}

		}

		TypedQuery<T> query = this.entityManager.createQuery(criteria.select(root).where(where).orderBy(order));
		query.setFirstResult(page).setMaxResults(pagesize);

		return query.getResultList();
	}

	public Long count(Predicate[] where) {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
		countCriteria.distinct(true);
		Root<T> root = countCriteria.from(entityClass);
		root.alias("entity");
		countCriteria = countCriteria.select(builder.count(root)).where(where);
		return this.entityManager.createQuery(countCriteria).getSingleResult();
	}

	@Override
	public Predicate[] getSearchPredicates(Root<T> root, Map<String, String> filters) {
		// TODO Auto-generated method stub
		return null;
	}

	public void filtroLike(String campo, List<Predicate> predicatesList, CriteriaBuilder builder, Root<T> root,
						   Map<String, String> filters) {
		String valor = filters.get(campo);
		if (valor != null && !"".equals(valor)) {
			predicatesList.add(builder.like(root.<String> get(campo), '%' + valor + '%'));
		}
	}

	public void filtroLike(String valor, List<Predicate> predicatesList, CriteriaBuilder builder, Root<T> root,
						   String... campo) {
		if (valor != null && !"".equals(valor)) {
			if (campo.length == 1)
				predicatesList.add(builder.like(root.<String> get(campo[0]), '%' + valor + '%'));
			if (campo.length == 2)
				predicatesList.add(builder.like(root.get(campo[0]).<String> get(campo[1]), '%' + valor + '%'));
			if (campo.length == 3)
				predicatesList
						.add(builder.like(root.get(campo[0]).get(campo[1]).<String> get(campo[2]), '%' + valor + '%'));
		}
	}

	public void filtroEqual(String campo, List<Predicate> predicatesList, CriteriaBuilder builder, Root<T> root,
							Map<String, String> filters) {
		String valor = filters.get(campo);
		if (valor != null && !"".equals(valor)) {
			predicatesList.add(builder.equal(root.get(campo), (Object) valor));
		}
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}