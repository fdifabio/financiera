package ar.edu.unrn.lia.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import ar.edu.unrn.lia.dao.UserDAO;
import ar.edu.unrn.lia.generic.GenericDaoJpaImpl;
import ar.edu.unrn.lia.model.Role;
import ar.edu.unrn.lia.model.User;

@Named("userDao")
public class UserDAOImpl extends GenericDaoJpaImpl<User, Long> implements
		UserDAO, Serializable {
	
	private static final String QUERY_GET_USER_BY_USERNAME = "select u from User u where u.username = :username";
	private static final String QUERY_GET_USER_BY_EMAIL = "select u from User u where u.email = :email";

	public Predicate[] getSearchPredicates(Root<User> root,
			Map<String, String> filters) {

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();
		root.alias("entity");

		String description = filters.get("username");
		if (description != null && !"".equals(description)) {
			predicatesList.add(builder.like(root.<String> get("username"),
					'%' + description + '%'));
		}
		String email = filters.get("email");
		if (email != null && !"".equals(email)) {
			predicatesList.add(builder.like(root.<String> get("email"),
					'%' + email + '%'));
		}

		return predicatesList.toArray(new Predicate[predicatesList.size()]);
	}
	@Override
	public User getEntityByName(String username) {
		Query query = this.entityManager.createQuery(QUERY_GET_USER_BY_USERNAME);
		query.setParameter("username", username);
		if (query.getResultList().isEmpty())
			return null;
		return (User) query.getSingleResult();
	}
	
	public User getEntityByMail(String mail) {
		Query query = this.entityManager.createQuery(QUERY_GET_USER_BY_EMAIL);
		query.setParameter("email", mail);

		/*  No dispara la exception NoResultException*/
		if(query.getResultList().isEmpty())
			return null;

		return (User) query.getSingleResult();
	}


}