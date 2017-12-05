package ar.edu.unrn.lia.dao.impl;

import ar.edu.unrn.lia.dao.CajaDAO;
import ar.edu.unrn.lia.generic.GenericDaoJpaImpl;
import ar.edu.unrn.lia.model.Caja;

import javax.inject.Named;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Lucas on 22/08/2017.
 */
@Named("cajaDAO")
public class CajaDAOImpl extends GenericDaoJpaImpl<Caja, Long> implements
        CajaDAO, Serializable {


    public Predicate[] getSearchPredicates(Root<Caja> root,
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
    public List<Caja> listwithPag(Predicate[] where, Integer page, Integer pagesize, String sortField, Boolean asc, boolean distinct) {

        CriteriaBuilder criteriaBuilder = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Caja> cq = criteriaBuilder
                .createQuery(Caja.class);
        Root<Caja> fromCaja = cq.from(Caja.class);
        fromCaja.alias("entity");
        Order order = orderByQuery(sortField, false , criteriaBuilder, fromCaja);

        final TypedQuery<Caja> query = this.entityManager
                .createQuery(cq.where(where).orderBy(order));

        query.setFirstResult(page).setMaxResults(pagesize);
        List<Caja> resultList = query.getResultList();

        return resultList;
    }


    public List<Caja> findAll() {
        return this.entityManager.createQuery("FROM Caja c ORDER BY c.fechaApertura ASC").getResultList();
    }

    public void cerrarCaja(Caja caja) {
        Query query = this.entityManager.createQuery("UPDATE Caja c SET c.fechaCierre= :fechaCierre where c.id= :id");
        query.setParameter("fechaCierre", new Date());
        query.setParameter("id", caja.getId());
        query.executeUpdate();
    }

    public Caja getLast() {
        Query query = this.entityManager.createQuery("FROM Caja c where c.fechaCierre is null");
        List<Caja> cajas = (List<Caja>) query.getResultList();
        if (cajas == null || cajas.isEmpty()) {
            return null;
        }
        Caja caja= cajas.get(0);
        return caja;
    }

}
