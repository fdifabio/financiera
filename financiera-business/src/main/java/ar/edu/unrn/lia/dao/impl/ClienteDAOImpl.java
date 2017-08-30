package ar.edu.unrn.lia.dao.impl;

import ar.edu.unrn.lia.dao.ClienteDAO;
import ar.edu.unrn.lia.generic.GenericDaoJpaImpl;
import ar.edu.unrn.lia.model.Cliente;

import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by difabioguillermo on 11/8/17.
 */
@Named("clienteDAO")
public class ClienteDAOImpl extends GenericDaoJpaImpl<Cliente, Long> implements
        ClienteDAO, Serializable {


    public Predicate[] getSearchPredicates(Root<Cliente> root,
                                           Map<String, String> filters) {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        List<Predicate> predicatesList = new ArrayList<Predicate>();
        root.alias("entity");

        String id = filters.get("id");
        if (id != null && !"".equals(id)) {
            predicatesList.add(builder.like(root.<String>get("id"),
                    '%' + id + '%'));
        }
        String dni = filters.get("dni");
        if (dni != null && !"".equals(dni)) {
            predicatesList.add(builder.like(root.<String>get("dni"),
                    '%' + dni + '%'));
        }
        String nombre = filters.get("nombre");
        if (id != null && !"".equals(nombre)) {
            predicatesList.add(builder.like(root.<String>get("nombre"),
                    '%' + nombre + '%'));
        }
        String apellido = filters.get("apellido");
        if (dni != null && !"".equals(apellido)) {
            predicatesList.add(builder.like(root.<String>get("apellido"),
                    '%' + apellido + '%'));
        }
        return predicatesList.toArray(new Predicate[predicatesList.size()]);
    }


    @Override
    public List<Cliente> listwithPag(Predicate[] where, Integer page, Integer pagesize, String sortField, Boolean asc, boolean distinct) {

        CriteriaBuilder criteriaBuilder = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Cliente> cq = criteriaBuilder
                .createQuery(Cliente.class);
        //TODO: cq.distinct(true);
        Root<Cliente> fromCliente = cq.from(Cliente.class);
        fromCliente.alias("entity");
        Order order = orderByQuery(sortField, asc, criteriaBuilder, fromCliente);
        cq.select(criteriaBuilder
                .construct(
                        Cliente.class,
                        fromCliente.get("id"),
                        fromCliente.get("dni"),
                        fromCliente.get("nombre"),
                        fromCliente.get("apellido"),
                        fromCliente.get("celular"))
        );

        final TypedQuery<Cliente> query = this.entityManager
                .createQuery(cq.where(where).orderBy(order));

        query.setFirstResult(page).setMaxResults(pagesize);
        List<Cliente> resultList = query.getResultList();

        return resultList;
    }

    @Override
    public List<Cliente> searchByApellidoNombre(String query) {
        return this.entityManager.createQuery("FROM Cliente WHERE  apellido LIKE '%" + query + "%' OR nombre LIKE '%" + query + "%' OR id LIKE '%" + query + "%' OR dni LIKE '%" + query + "%'").getResultList();
    }
}
