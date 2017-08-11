package ar.edu.unrn.lia.dao.impl;

import ar.edu.unrn.lia.dao.UserDAO;
import ar.edu.unrn.lia.dto.UserDTO;
import ar.edu.unrn.lia.generic.GenericDaoJpaImpl;
import ar.edu.unrn.lia.model.User;

import javax.inject.Named;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Named("userDao")
public class UserDAOImpl extends GenericDaoJpaImpl<User, Long> implements
        UserDAO, Serializable {

    private static final String QUERY_GET_USER_BY_USERNAME = "select u from User u where u.username = :username";
    private static final String QUERY_GET_USER_BY_EMAIL = "select u from User u where u.email = :email";
    private static final String QUERY_CAMBIAR_CLAVE = "UPDATE User SET password = :password where id = :id";

    public Predicate[] getSearchPredicates(Root<User> root,
                                           Map<String, String> filters) {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        List<Predicate> predicatesList = new ArrayList<Predicate>();
        root.alias("entity");

        String description = filters.get("username");
        if (description != null && !"".equals(description)) {
            predicatesList.add(builder.like(root.<String>get("username"),
                    '%' + description + '%'));
        }
        String email = filters.get("email");
        if (email != null && !"".equals(email)) {
            predicatesList.add(builder.like(root.<String>get("email"),
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
        if (query.getResultList().isEmpty())
            return null;

        return (User) query.getSingleResult();
    }

    @Override
    public List<User> listwithPag(Predicate[] where, Integer page, Integer pagesize, String sortField, Boolean asc, boolean distinct ) {

        CriteriaBuilder criteriaBuilder = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<User> cq = criteriaBuilder
                .createQuery(User.class);
        //TODO: cq.distinct(true);
        Root<User> fromUser = cq.from(User.class);
        fromUser.alias("entity");
        Order order = orderByQuery(sortField, asc, criteriaBuilder, fromUser);
        cq.select(criteriaBuilder
                .construct(
                        User.class,
                        fromUser.get("id"),
                        fromUser.get("username"),
                        fromUser.get("email"),
                        fromUser.get("active"),
                        fromUser.get("role"))
        );

        final TypedQuery<User> query = this.entityManager
                .createQuery(cq.where(where).orderBy(order));

        query.setFirstResult(page).setMaxResults(pagesize);
        List<User> resultList = query.getResultList();

        return resultList;
    }

}