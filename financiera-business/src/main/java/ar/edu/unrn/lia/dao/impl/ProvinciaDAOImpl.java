package ar.edu.unrn.lia.dao.impl;

import ar.edu.unrn.lia.dao.ProvinciaDAO;
import ar.edu.unrn.lia.generic.GenericDaoJpaImpl;
import ar.edu.unrn.lia.model.Provincia;

import javax.inject.Named;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Named("provinciaDao")
public class ProvinciaDAOImpl extends GenericDaoJpaImpl<Provincia, Long> implements
        ProvinciaDAO, Serializable {

    public Predicate[] getSearchPredicates(Root<Provincia> root,
                                           Map<String, String> filters) {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        List<Predicate> predicatesList = new ArrayList<Predicate>();
        root.alias("entity");

        String description = filters.get("nombre");
        if (description != null && !"".equals(description)) {
            predicatesList.add(builder.like(root.<String>get("nombre"),
                    '%' + description + '%'));
        }

        return predicatesList.toArray(new Predicate[predicatesList.size()]);
    }

    @Override
    public Provincia getEntityByName(String nombre) {

        Query query = this.entityManager
                .createQuery("from Provincia p where p.nombre = :nombre");
        query.setParameter("nombre", nombre);

        return (Provincia) query.getSingleResult();
    }

}
