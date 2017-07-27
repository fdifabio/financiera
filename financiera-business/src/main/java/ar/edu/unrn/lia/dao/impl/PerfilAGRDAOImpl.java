package ar.edu.unrn.lia.dao.impl;

import ar.edu.unrn.lia.dao.PerfilAGRDAO;
import ar.edu.unrn.lia.generic.GenericDaoJpaImpl;
import ar.edu.unrn.lia.model.PerfilAGR;

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

@Named("perfilAGRDAO")
public class PerfilAGRDAOImpl extends GenericDaoJpaImpl<PerfilAGR, Long>
        implements PerfilAGRDAO, Serializable {

    public Predicate[] getSearchPredicates(Root<PerfilAGR> root,
                                           Map<String, String> filters) {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        List<Predicate> predicatesList = new ArrayList<Predicate>();
        root.alias("entity");

        String cuit = filters.get("cuit");
        if (cuit != null && !"".equals(cuit)) {
            predicatesList.add(builder.like(root.<String>get("cuit"),
                    '%' + cuit + '%'));
        }

        return predicatesList.toArray(new Predicate[predicatesList.size()]);
    }

    @Override
    public PerfilAGR getEntityByMail(String mail) throws NoResultException {
        Query query = this.entityManager.createQuery("from PerfilAGR p where p.user.email = :mail");
        query.setParameter("mail", mail);
        if (query.getResultList().isEmpty())
            return null;
        return (PerfilAGR) query.getSingleResult();
    }

    @Override
    public PerfilAGR getEntityByCuit(String cuit) {

		/*Query query = this.entityManager
                .createQuery("from Ciudadano c where c.nombre = :twitter");
		query.setParameter("twitter", twitter);
		Ciudadano ciudadano;
		try {
			ciudadano = (Ciudadano) query.getSingleResult();
		} catch (NoResultException e) {
			throw e;
		}*/
        return null;
    }
}
