package ar.edu.unrn.lia.dao.impl;

import ar.edu.unrn.lia.dao.CobroDAO;
import ar.edu.unrn.lia.generic.GenericDaoJpaImpl;
import ar.edu.unrn.lia.model.Cobro;

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
@Named("cobroDAO")
public class CobroDAOImpl extends GenericDaoJpaImpl<Cobro, Long> implements
        CobroDAO, Serializable {


    public Predicate[] getSearchPredicates(Root<Cobro> root,
                                           Map<String, String> filters) {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        List<Predicate> predicatesList = new ArrayList<Predicate>();
        root.alias("entity");

        String id = filters.get("id");
        if (id != null && !"".equals(id)) {
            predicatesList.add(builder.like(root.<String>get("id"),
                    '%' + id + '%'));
        }
//        String dni = filters.get("dni");
//        if (dni != null && !"".equals(dni)) {
//            predicatesList.add(builder.like(root.<String>get("dni"),
//                    '%' + dni + '%'));
//        }
//        String nombre = filters.get("nombre");
//        if (id != null && !"".equals(nombre)) {
//            predicatesList.add(builder.like(root.<String>get("nombre"),
//                    '%' + nombre + '%'));
//        }
//        String apellido = filters.get("apellido");
//        if (dni != null && !"".equals(apellido)) {
//            predicatesList.add(builder.like(root.<String>get("apellido"),
//                    '%' + apellido + '%'));
//        }
        return predicatesList.toArray(new Predicate[predicatesList.size()]);
    }


    @Override
    public List<Cobro> listwithPag(Predicate[] where, Integer page, Integer pagesize, String sortField, Boolean asc, boolean distinct) {

        CriteriaBuilder criteriaBuilder = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Cobro> cq = criteriaBuilder
                .createQuery(Cobro.class);
        //TODO: cq.distinct(true);
        Root<Cobro> fromCredito = cq.from(Cobro.class);
        fromCredito.alias("entity");
        Order order = orderByQuery(sortField, asc, criteriaBuilder, fromCredito);
//        cq.select(criteriaBuilder
//                .construct(
//                        Credito.class,
//                        fromCredito.get("id"),
//                        fromCredito.get("dni"),
//                        fromCredito.get("nombre"),
//                        fromCredito.get("apellido"),
//                        fromCredito.get("celular"))
//        );

        final TypedQuery<Cobro> query = this.entityManager
                .createQuery(cq.where(where).orderBy(order));

        query.setFirstResult(page).setMaxResults(pagesize);
        List<Cobro> resultList = query.getResultList();

        return resultList;
    }

}
