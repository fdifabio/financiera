package ar.edu.unrn.lia.dao.impl;

import ar.edu.unrn.lia.dao.ClienteDAO;
import ar.edu.unrn.lia.dao.GaranteDAO;
import ar.edu.unrn.lia.generic.GenericDaoJpaImpl;
import ar.edu.unrn.lia.model.Cliente;
import ar.edu.unrn.lia.model.Cuota;
import ar.edu.unrn.lia.model.Estado;
import ar.edu.unrn.lia.model.Garante;

import javax.inject.Named;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by difabioguillermo on 11/8/17.
 */
@Named("garanteDAO")
public class GaranteDAOImpl extends GenericDaoJpaImpl<Garante, Long> implements
        GaranteDAO, Serializable {

    public Predicate[] getSearchPredicates(Root<Garante> root,
                                           Map<String, String> filters) {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        List<Predicate> predicatesList = new ArrayList<Predicate>();
        root.alias("entity");

        String id = filters.get("id");
        if (id != null && !"".equals(id)) {
            Long idaux = Long.valueOf(id);
            predicatesList.add(builder.equal(root.<Long>get("id"),
                    idaux));
        }
        String dni = filters.get("dni");
        if (dni != null && !"".equals(dni)) {
            predicatesList.add(builder.like(root.<String>get("dni"),
                    '%' + dni + '%'));
        }
        String nombre = filters.get("nombre");
        if (nombre != null && !"".equals(nombre)) {
            predicatesList.add(builder.like(root.<String>get("nombre"),
                    '%' + nombre + '%'));
        }
        String apellido = filters.get("apellido");
        if (apellido != null && !"".equals(apellido)) {
            predicatesList.add(builder.like(root.<String>get("apellido"),
                    '%' + apellido + '%'));
        }
        return predicatesList.toArray(new Predicate[predicatesList.size()]);
    }

    @Override
    public List<Garante> listwithPag(Predicate[] where, Integer page, Integer pagesize, String sortField, Boolean asc, boolean distinct) {

        CriteriaBuilder criteriaBuilder = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Garante> cq = criteriaBuilder
                .createQuery(Garante.class);
        //TODO: cq.distinct(true);
        Root<Garante> fromGarante = cq.from(Garante.class);
        fromGarante.alias("entity");
        Order order = orderByQuery(sortField, asc, criteriaBuilder, fromGarante);
        cq.select(criteriaBuilder
                .construct(
                        Garante.class,
                        fromGarante.get("id"),
                        fromGarante.get("dni"),
                        fromGarante.get("nombre"),
                        fromGarante.get("apellido"),
                        fromGarante.get("celular"))
        );

        final TypedQuery<Garante> query = this.entityManager
                .createQuery(cq.where(where).orderBy(order));

        query.setFirstResult(page).setMaxResults(pagesize);
        List<Garante> resultList = query.getResultList();

        return resultList;
    }

    @Override
    public List<Garante> searchByApellidoNombre(String query) {
        return this.entityManager.createQuery("FROM Garante WHERE  apellido LIKE '%" + query + "%' OR nombre LIKE '%" + query + "%' OR id LIKE '%" + query + "%' OR dni LIKE '%" + query + "%'").getResultList();
    }

}
