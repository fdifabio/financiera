package ar.edu.unrn.lia.dao.impl;

import ar.edu.unrn.lia.dao.MovimientoDAO;
import ar.edu.unrn.lia.dto.MonthYearDTO;
import ar.edu.unrn.lia.dto.MovimientoDTO;
import ar.edu.unrn.lia.generic.GenericDaoJpaImpl;
import ar.edu.unrn.lia.model.Movimiento;

import javax.inject.Named;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Lucas on 22/08/2017.
 */
@Named("movimientoDAO")
public class MovimientoDAOImpl extends GenericDaoJpaImpl<Movimiento, Long> implements
        MovimientoDAO, Serializable {


    public Predicate[] getSearchPredicates(Root<Movimiento> root,
                                           Map<String, String> filters) {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        List<Predicate> predicatesList = new ArrayList<Predicate>();
        root.alias("entity");

        String id = filters.get("id");
        if (id != null && !"".equals(id)) {
            predicatesList.add(builder.like(root.<String>get("id"),
                    '%' + id + '%'));
        }
        return predicatesList.toArray(new Predicate[predicatesList.size()]);
    }


    @Override
    public List<Movimiento> listwithPag(Predicate[] where, Integer page, Integer pagesize, String sortField, Boolean asc, boolean distinct) {

        CriteriaBuilder criteriaBuilder = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Movimiento> cq = criteriaBuilder
                .createQuery(Movimiento.class);
        Root<Movimiento> fromMovimiento = cq.from(Movimiento.class);
        fromMovimiento.alias("entity");
        Order order = orderByQuery(sortField, asc, criteriaBuilder, fromMovimiento);

        final TypedQuery<Movimiento> query = this.entityManager
                .createQuery(cq.where(where).orderBy(order));

        query.setFirstResult(page).setMaxResults(pagesize);
        List<Movimiento> resultList = query.getResultList();

        return resultList;
    }


    public List<Movimiento> findAll() {
        return this.entityManager.createQuery("FROM Movimiento m ORDER BY m.fecha ASC").getResultList();
    }

    public List<Movimiento> findAllByCajaId(Long idCaja) {
        Query query = this.entityManager.createQuery("FROM Movimiento m where m.caja.id= :idCaja ORDER BY m.fecha ASC");
        query.setParameter("idCaja", idCaja);
        return (List<Movimiento>) query.getResultList();
    }

    public List<MovimientoDTO> findByMonthYear(int month, int year, Movimiento.Tipo tipo) {
        Query query = this.entityManager.createQuery("SELECT new ar.edu.unrn.lia.dto.MovimientoDTO(YEAR( m.fecha ) ,MONTH ( m.fecha ), DAY (m.fecha), SUM( m.monto)) FROM Movimiento m WHERE m.tipo = :tipo and YEAR( m.fecha ) = :year and MONTH( m.fecha ) = :month GROUP BY 1,2,3");
        query.setParameter("tipo", tipo);
        query.setParameter("year", year);
        query.setParameter("month", month);
        return (List<MovimientoDTO>) query.getResultList();
    }

    @Override
    public List<Integer> listAnios() {
        Query query = this.entityManager.createQuery("SELECT MONTH( m.fecha ) FROM Movimiento m  GROUP BY 1");
        return (List<Integer>) query.getResultList();
    }


}
