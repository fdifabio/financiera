package ar.edu.unrn.lia.dao.impl;

import ar.edu.unrn.lia.dao.TasaDAO;
import ar.edu.unrn.lia.generic.GenericDaoJpaImpl;
import ar.edu.unrn.lia.model.Tasa;

import javax.inject.Named;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Federico on 14/06/2017.
 */
@Named("tasaDao")
public class TasaDAOImpl extends GenericDaoJpaImpl<Tasa, Long> implements TasaDAO, Serializable {

    public Predicate[] getSearchPredicates(Root<Tasa> root, Map<String, String> filters) {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        List<Predicate> predicatesList = new ArrayList<Predicate>();
        root.alias("entity");
        String description = filters.get("descripcion");
        if (description != null && !"".equals(description)) {
            predicatesList.add(builder.like(root.<String>get("descripcion"), '%' + description + '%'));
        }
        return predicatesList.toArray(new Predicate[predicatesList.size()]);
    }

    @Override
    public Tasa getEntityByDescripcion(String descripcion) {
        Query query = this.entityManager.createQuery("from Tasa t where t.descripcion = :descripcion");
        query.setParameter("descripcion", descripcion);
        if (query.getResultList().isEmpty())
            return null;
        return (Tasa) query.getSingleResult();
    }
}
