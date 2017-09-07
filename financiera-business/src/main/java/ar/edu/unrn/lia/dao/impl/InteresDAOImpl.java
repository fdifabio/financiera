package ar.edu.unrn.lia.dao.impl;

import ar.edu.unrn.lia.dao.InteresDAO;
import ar.edu.unrn.lia.generic.GenericDaoJpaImpl;
import ar.edu.unrn.lia.model.Interes;

import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Lucas on 22/08/2017.
 */
@Named("interesDAO")
public class InteresDAOImpl extends GenericDaoJpaImpl<Interes, Long> implements
        InteresDAO, Serializable {


    public Predicate[] getSearchPredicates(Root<Interes> root,
                                           Map<String, String> filters) {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        List<Predicate> predicatesList = new ArrayList<Predicate>();
        root.alias("entity");

        String id = filters.get("id");
        if (id != null && !"".equals(id)) {
            predicatesList.add(builder.like(root.<String>get("id"),
                    '%' + id + '%'));
        }
        return predicatesList.toArray(new Predicate[predicatesList.size()]);
    }


    @Override
    public List<Interes> listwithPag(Predicate[] where, Integer page, Integer pagesize, String sortField, Boolean asc, boolean distinct) {

        CriteriaBuilder criteriaBuilder = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Interes> cq = criteriaBuilder
                .createQuery(Interes.class);
        //TODO: cq.distinct(true);
        Root<Interes> fromInteres = cq.from(Interes.class);
        fromInteres.alias("entity");
        Order order = orderByQuery(sortField, asc, criteriaBuilder, fromInteres);

        final TypedQuery<Interes> query = this.entityManager
                .createQuery(cq.where(where).orderBy(order));

        query.setFirstResult(page).setMaxResults(pagesize);
        List<Interes> resultList = query.getResultList();

        return resultList;
    }


    public List<Interes> findAll() {
        return this.entityManager.createQuery("FROM Interes i ORDER BY i.orden ASC").getResultList();
    }

}
