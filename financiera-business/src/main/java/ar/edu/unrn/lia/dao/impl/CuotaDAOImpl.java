package ar.edu.unrn.lia.dao.impl;

import ar.edu.unrn.lia.dao.CuotaDAO;
import ar.edu.unrn.lia.dto.CuotaDTO;
import ar.edu.unrn.lia.generic.GenericDaoJpaImpl;
import ar.edu.unrn.lia.model.Cuota;

import javax.inject.Named;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Named("CuotaDAO")

public class CuotaDAOImpl extends GenericDaoJpaImpl<Cuota, Long> implements
        CuotaDAO, Serializable {


    public Predicate[] getSearchPredicates(Root<Cuota> root,
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
    public List<Cuota> listwithPag(Predicate[] where, Integer page, Integer pagesize, String sortField, Boolean asc, boolean distinct) {

        CriteriaBuilder criteriaBuilder = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Cuota> cq = criteriaBuilder
                .createQuery(Cuota.class);
        //TODO: cq.distinct(true);
        Root<Cuota> fromCuota = cq.from(Cuota.class);
        fromCuota.alias("entity");
        Order order = orderByQuery(sortField, asc, criteriaBuilder, fromCuota);
//        cq.select(criteriaBuilder
//                .construct(
//                        Credito.class,
//                        fromCredito.get("id"),
//                        fromCredito.get("dni"),
//                        fromCredito.get("nombre"),
//                        fromCredito.get("apellido"),
//                        fromCredito.get("celular"))
//        );

        final TypedQuery<Cuota> query = this.entityManager
                .createQuery(cq.where(where).orderBy(order));

        query.setFirstResult(page).setMaxResults(pagesize);
        List<Cuota> resultList = query.getResultList();

        return resultList;
    }

    public List<CuotaDTO> listAdeudadas(int year, int month) {

        Query query = this.entityManager.createQuery("SELECT new ar.edu.unrn.lia.dto.CuotaDTO(YEAR( c.fechaVencimiento ) ,MONTH ( c.fechaVencimiento ), SUM( c.cuotaCapital + c.cuotaInteres)) FROM Cuota c WHERE c.estado= :estado and YEAR( c.fechaVencimiento ) = :year and MONTH( c.fechaVencimiento ) > :month GROUP BY 1,2");
        //SELECT YEAR( c.fecha_vencimiento ) ,MONTH ( c.fecha_vencimiento ), SUM( c.cuota_capital + c.cuota_interes) FROM Cuota c WHERE c.estado= 'ADEUDADO' and YEAR( c.fecha_vencimiento ) = 2017 GROUP BY 1,2
        query.setParameter("estado", Cuota.Estado.ADEUDADO);
        query.setParameter("year", year);
        query.setParameter("month", month);
        return (List<CuotaDTO>) query.getResultList();
    }

    public List<CuotaDTO> countAdeudadas(int year, int month) {

        Query query = this.entityManager.createQuery("SELECT new ar.edu.unrn.lia.dto.CuotaDTO(YEAR( c.fechaVencimiento ) ,MONTH ( c.fechaVencimiento ), COUNT( c.cuotaCapital )) FROM Cuota c WHERE c.estado= :estado and YEAR( c.fechaVencimiento ) = :year and MONTH( c.fechaVencimiento ) > :month GROUP BY 1,2");
        //SELECT YEAR( c.fecha_vencimiento ) ,MONTH ( c.fecha_vencimiento ), COUNT( c.cuota_capital + c.cuota_interes) FROM Cuota c WHERE c.estado= 'ADEUDADO' and YEAR( c.fecha_vencimiento ) = 2017 GROUP BY 1,2
        query.setParameter("estado", Cuota.Estado.ADEUDADO);
        query.setParameter("year", year);
        query.setParameter("month", month);
        return (List<CuotaDTO>) query.getResultList();
    }

    public List<CuotaDTO> listSaldadas(int year) {

        Query query = this.entityManager.createQuery("SELECT new ar.edu.unrn.lia.dto.CuotaDTO(YEAR( c.fechaVencimiento ) ,MONTH ( c.fechaVencimiento ), SUM( c.cuotaCapital + c.cuotaInteres)) FROM Cuota c WHERE c.estado= :estado and YEAR( c.fechaVencimiento ) = :year GROUP BY 1,2");
        //SELECT YEAR( c.fecha_vencimiento ) ,MONTH ( c.fecha_vencimiento ), SUM( c.cuota_capital + c.cuota_interes) FROM Cuota c WHERE c.estado= 'ADEUDADO' and YEAR( c.fecha_vencimiento ) = 2017 GROUP BY 1,2
        query.setParameter("estado", Cuota.Estado.SALDADO);
        query.setParameter("year", year);
        return (List<CuotaDTO>) query.getResultList();
    }

    @Override
    public List<Integer> listAniosAdeudadas() {
        Query query = this.entityManager.createQuery("SELECT DISTINCT (YEAR( c.fechaVencimiento )) FROM Cuota c WHERE c.estado= :estado and YEAR( c.fechaVencimiento ) >= YEAR(:anio) ");
//SELECT DISTINCT YEAR( c.fecha_vencimiento ) FROM Cuota c WHERE c.estado= 'ADEUDADO' AND YEAR(c.fecha_vencimiento)>=2018
        query.setParameter("estado", Cuota.Estado.ADEUDADO);
        Date fecha = new Date();
        query.setParameter("anio", fecha);
        return (List<Integer>) query.getResultList();


    }

    @Override
    public List<Integer> listAnios() {
        Query query = this.entityManager.createQuery("SELECT DISTINCT (YEAR( c.fechaVencimiento )) FROM Cuota c   ");
        return (List<Integer>) query.getResultList();

    }

    @Override
    public void actualizarEstados() {
        Query query = this.entityManager.createQuery("UPDATE Cuota c SET c.estado=:estado1 where :today > c.fechaVencimiento and c.estado= :estado3");
        query.setParameter("estado1", Cuota.Estado.VENCIDO);
        query.setParameter("today", new Date());
//        query.setParameter("estado2", Cuota.Estado.PARCIALMENTE_SALDADO);
        query.setParameter("estado3", Cuota.Estado.ADEUDADO);
        query.executeUpdate();
    }
}
