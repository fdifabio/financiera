package ar.edu.unrn.lia.dao.impl;

import ar.edu.unrn.lia.dao.ComitenteDAO;
import ar.edu.unrn.lia.dto.ComitenteDTO;
import ar.edu.unrn.lia.generic.GenericDaoJpaImpl;
import ar.edu.unrn.lia.model.Ciudad;
import ar.edu.unrn.lia.model.Comitente;
import ar.edu.unrn.lia.model.PerfilAGR;

import javax.inject.Named;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Named("comitenteDao")
public class ComitenteDAOImpl extends GenericDaoJpaImpl<Comitente, Long> implements ComitenteDAO, Serializable {

    public Predicate[] getSearchPredicates(Root<Comitente> root, Map<String, String> filters) {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        List<Predicate> predicatesList = new ArrayList<Predicate>();
        root.alias("entity");

        String perfilAGRId = filters.get("perfilAGR.id");
        if (perfilAGRId != null) {
            predicatesList.add(builder
                    .equal(root.get("perfilAGR").<Long>get("id"), perfilAGRId));
        }

        String nombre = filters.get("nombre");
        if (nombre != null && !"".equals(nombre)) {
            predicatesList.add(builder.like(root.<String>get("nombre"), '%' + nombre + '%'));
        }

        String cuit = filters.get("cuit");
        if (cuit != null && !"".equals(cuit)) {
            predicatesList.add(builder.like(root.<String>get("cuit"), '%' + cuit + '%'));
        }

        String responsable = filters.get(" responsable.nombre");
        if (responsable != null && !"".equals(responsable)) {
            predicatesList.add(builder.like(root.<String>get("responsable").get("nombre"), '%' + responsable + '%'));
        }

        return predicatesList.toArray(new Predicate[predicatesList.size()]);
    }


  /**  @Override
    public List<Comitente> listwithPag(Predicate[] where, Integer page, Integer pagesize, String sortField, Boolean asc, boolean distinct) {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Comitente> criteria = builder.createQuery(entityClass);
        Root<Comitente> root = criteria.from(entityClass);
        root.alias("entity");
        final Join<Comitente, PerfilAGR> joinPerfilAGR = root.join("perfilAGR",
                JoinType.LEFT);
        final Join<Comitente, Ciudad> joinCiudad = root.join("ciudad",
                JoinType.LEFT);

        Order order = orderByQuery(sortField, asc, builder, root);

        TypedQuery<Comitente> query = this.entityManager.createQuery(criteria.select(root).where(where).orderBy(order));
        query.setFirstResult(page).setMaxResults(pagesize);

        return query.getResultList();

    }*/

    @Override
    public List<ComitenteDTO> listwithPagDTO(Predicate[] where, Integer page, Integer pagesize, String sortField, Boolean asc) {

        CriteriaBuilder criteriaBuilder = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<ComitenteDTO> cq = criteriaBuilder
                .createQuery(ComitenteDTO.class);
        Root<Comitente> fromComitente = cq.from(Comitente.class);
        fromComitente.alias("entity");
        Order order = orderByQuery(sortField, asc, criteriaBuilder, fromComitente);
        cq.select(criteriaBuilder
                .construct(
                        ComitenteDTO.class,
                        fromComitente.get("id"),
                        fromComitente.get("nombre"),
                        fromComitente.get("empresa"),
                        fromComitente.get("cuit"),
                        fromComitente.get("domicilio"),
                        fromComitente.get("responsable").get("nombre"),
                        fromComitente.get("responsable").get("email"),
                        fromComitente.get("perfilAGR").get("id"),
                        fromComitente.get("perfilAGR").get("nombre"),
                        fromComitente.get("perfilAGR").get("apellido"),
                        fromComitente.get("ciudad").get("id"),
                        fromComitente.get("ciudad").get("nombre")
                )
        );

        final TypedQuery<ComitenteDTO> query = this.entityManager
                .createQuery(cq.where(where).orderBy(order));

        query.setFirstResult(page).setMaxResults(pagesize);
        List<ComitenteDTO> resultList = query.getResultList();

        return resultList;
    }

    @Override
    public List<ComitenteDTO> findAllDTO() {
        return this.entityManager.createQuery("from " + this.entityClass.getName()).getResultList();
    }

    /*
  @Override
    public Comitente read(Long id) {
        String queryString = "select c from Comitente c " +
                "LEFT JOIN FETCH c.ciudad ciu " +
                "LEFT JOIN FETCH c.perfilAGR P " +
                "where c.id = :id";

        Query query = this.entityManager.createQuery(queryString);
        query.setParameter("id", id);
        return (Comitente) query.getSingleResult();
    }
    */
}
