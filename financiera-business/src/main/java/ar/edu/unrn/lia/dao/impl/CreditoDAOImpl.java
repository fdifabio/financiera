package ar.edu.unrn.lia.dao.impl;

import ar.edu.unrn.lia.dao.CreditoDAO;
import ar.edu.unrn.lia.generic.GenericDaoJpaImpl;
import ar.edu.unrn.lia.model.Credito;

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
@Named("creditoDAO")
public class CreditoDAOImpl extends GenericDaoJpaImpl<Credito, Long> implements
        CreditoDAO, Serializable {


    public Predicate[] getSearchPredicates(Root<Credito> root,
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
    public List<Credito> listwithPag(Predicate[] where, Integer page, Integer pagesize, String sortField, Boolean asc, boolean distinct) {

        CriteriaBuilder criteriaBuilder = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Credito> cq = criteriaBuilder
                .createQuery(Credito.class);
        //TODO: cq.distinct(true);
        Root<Credito> fromCredito = cq.from(Credito.class);
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

        final TypedQuery<Credito> query = this.entityManager
                .createQuery(cq.where(where).orderBy(order));

        query.setFirstResult(page).setMaxResults(pagesize);
        List<Credito> resultList = query.getResultList();

        return resultList;
    }

    public List<Credito> listByIdCliente(Long idcliente) {

        Query query = this.entityManager.createQuery("FROM Credito c  where c.cliente.id= :idcliente");
        query.setParameter("idcliente", idcliente);
        return (List<Credito>) query.getResultList();
    }
}
