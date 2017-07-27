package ar.edu.unrn.lia.dao.impl;

import ar.edu.unrn.lia.dao.DepartamentoDAO;
import ar.edu.unrn.lia.generic.GenericDaoJpaImpl;
import ar.edu.unrn.lia.model.Departamento;

import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Named("departamentoDao")
public class DepartamentoDAOImpl extends GenericDaoJpaImpl<Departamento, Long> implements
        DepartamentoDAO, Serializable {

    public Predicate[] getSearchPredicates(Root<Departamento> root,
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
    public Departamento getEntityByName(String nombre) throws NoResultException {

        Query query = this.entityManager
                .createQuery("from Departamento c where c.nombre = :nombre");
        query.setParameter("nombre", nombre);

        return (Departamento) query.getSingleResult();
    }

    @Override
    public List<Departamento> getList(Long id) {
        Query query = this.entityManager.createQuery("from Departamento d where d.provincia.id = :id");
        query.setParameter("id", id);

        return (List<Departamento>) query.getResultList();
    }

}
