package ar.edu.unrn.lia.dao.impl;

import ar.edu.unrn.lia.dao.InmuebleDAO;
import ar.edu.unrn.lia.generic.GenericDaoJpaImpl;
import ar.edu.unrn.lia.model.Inmueble;

import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Federico on 19/05/2017.
 */
@Named("inmuebleDAO")
public class InmuebleDAOImpl extends GenericDaoJpaImpl<Inmueble, Long> implements InmuebleDAO, Serializable {

    public Predicate[] getSearchPredicates(Root<Inmueble> root, Map<String, String> filters) {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        List<Predicate> predicatesList = new ArrayList<Predicate>();
        root.alias("entity");
        String description = filters.get("nombre");
        if (description != null && !"".equals(description)) {
            predicatesList.add(builder.like(root.<String>get("nombre"), '%' + description + '%'));
        }

        return predicatesList.toArray(new Predicate[predicatesList.size()]);
    }

}
