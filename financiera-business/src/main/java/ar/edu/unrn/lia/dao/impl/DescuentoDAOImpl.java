package ar.edu.unrn.lia.dao.impl;

import ar.edu.unrn.lia.dao.DescuentoDAO;
import ar.edu.unrn.lia.dao.InteresDAO;
import ar.edu.unrn.lia.generic.GenericDaoJpaImpl;
import ar.edu.unrn.lia.model.Descuento;
import ar.edu.unrn.lia.model.Interes;

import javax.inject.Named;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Lucas on 22/08/2017.
 */
@Named("descuentoDAO")
public class DescuentoDAOImpl extends GenericDaoJpaImpl<Descuento, Long> implements
        DescuentoDAO, Serializable {


    public Predicate[] getSearchPredicates(Root<Descuento> root,
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
    public List<Descuento> listwithPag(Predicate[] where, Integer page, Integer pagesize, String sortField, Boolean asc, boolean distinct) {

        CriteriaBuilder criteriaBuilder = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Descuento> cq = criteriaBuilder
                .createQuery(Descuento.class);
        Root<Descuento> fromDescuento = cq.from(Descuento.class);
        fromDescuento.alias("entity");
        Order order = orderByQuery(sortField, asc, criteriaBuilder, fromDescuento);

        final TypedQuery<Descuento> query = this.entityManager
                .createQuery(cq.where(where).orderBy(order));

        query.setFirstResult(page).setMaxResults(pagesize);
        List<Descuento> resultList = query.getResultList();

        return resultList;
    }


    public List<Descuento> findAll() {
        return this.entityManager.createQuery("FROM Descuento d ORDER BY d.orden ASC").getResultList();
    }

    public void setOrden(Descuento descuento){
        Query query = this.entityManager.createQuery("UPDATE Descuento d SET d.orden= :orden where d.id= :id");
        query.setParameter("orden", descuento.getOrden());
        query.setParameter("id", descuento.getId());
        query.executeUpdate();
    }

}
