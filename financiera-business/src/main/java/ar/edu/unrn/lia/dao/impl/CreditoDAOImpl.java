package ar.edu.unrn.lia.dao.impl;

import ar.edu.unrn.lia.dao.CreditoDAO;
import ar.edu.unrn.lia.generic.GenericDaoJpaImpl;
import ar.edu.unrn.lia.model.Credito;
import ar.edu.unrn.lia.model.Cuota;
import ar.edu.unrn.lia.model.Estado;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Lucas on 22/08/2017.
 */
@Named("creditoDAO")
public class CreditoDAOImpl extends GenericDaoJpaImpl<Credito, Long> implements
        CreditoDAO, Serializable {


    public Predicate[] getSearchPredicates(Root<Credito> root,
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
        String cliente = filters.get("cliente");
        if (cliente != null && !"".equals(cliente)) {
            predicatesList.add(builder.or(builder.like(root.get("cliente").get("nombre"),
                    '%' + cliente + '%'), builder.like(root.get("cliente").get("apellido"),
                    '%' + cliente + '%')));
        }
//        String apellido = filters.get("apellido");
//        if (dni != null && !"".equals(apellido)) {
//            predicatesList.add(builder.like(root.<String>get("apellido"),
//                    '%' + apellido + '%'));
//        }

        String estado = filters.get("estado");
        if (estado != null) {
            predicatesList.add(builder.equal(root.<Estado>get("estado"), Estado.valueOf(estado)));
        }

        String fechaInicio = filters.get("fecha_inicio");
        if (fechaInicio != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = null;
            try {
                date = formatter.parse(fechaInicio);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            predicatesList.add(builder.greaterThanOrEqualTo(root.<Date>get("fechaCreacion"), date));
        }

        String fechaFin = filters.get("fecha_fin");
        if (fechaFin != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = null;
            try {
                date = formatter.parse(fechaFin);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            predicatesList.add(builder.lessThanOrEqualTo(root.<Date>get("fechaCreacion"), date));
        }


        return predicatesList.toArray(new Predicate[predicatesList.size()]);
    }


    @Override
    public List<Credito> listwithPag(Predicate[] where, Integer page, Integer pagesize, String sortField, Boolean asc, boolean distinct) {

        CriteriaBuilder criteriaBuilder = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Credito> cq = criteriaBuilder
                .createQuery(Credito.class);
        //TODO: cq.distinct(true);
        Root<Credito> fromCredito = cq.from(Credito.class);
        fromCredito.alias("entity");
        Order order = orderByQuery(sortField, asc, criteriaBuilder, fromCredito);
//        cq.select(criteriaBuilder
//                .construct(
//                        Credito.class,
//                        fromCredito.get("id"),
//                        fromCredito.get("dni"),
//                        fromCredito.get("nombre"),
//                        fromCredito.get("apellido"),
//                        fromCredito.get("celular"))
//        );

        final TypedQuery<Credito> query = this.entityManager
                .createQuery(cq.where(where).orderBy(order));

        query.setFirstResult(page).setMaxResults(pagesize);
        List<Credito> resultList = query.getResultList();

        return resultList;
    }

    public List<Credito> listByIdCliente(Long idcliente) {

        Query query = this.entityManager.createQuery("FROM Credito c  where c.cliente.id= :idcliente");
        query.setParameter("idcliente", idcliente);
        return (List<Credito>) query.getResultList();
    }

    @Override
    @Transactional
    public void actualizarEstados() {

        /*Actualiacion de creditos a estado Legales*/
      /*  Query query = this.entityManager.createQuery(" Select c FROM Credito c INNER JOIN FETCH c.listCuotas cu  where c.estado = :estado2  AND (cu.estado = :estado3 OR cu.estado = :estado4)");
        query.setParameter("estado2", Estado.ACTIVO);
        query.setParameter("estado3", Cuota.Estado.VENCIDO);
        query.setParameter("estado4", Cuota.Estado.PARCIALMENTE_SALDADO);


        List<Credito> creditosConCuotasVencidas = query.getResultList();

        if (!creditosConCuotasVencidas.isEmpty()) {
            List<Long> ids = creditosConCuotasVencidas.stream().map(Credito::getId).collect(Collectors.toList());

            Query query1 = this.entityManager.createQuery("UPDATE Credito c SET c.estado= :estado1  where c.id IN (:ids)");
            query1.setParameter("estado1", Estado.LEGALES);
            query1.setParameter("ids", ids);
            query1.executeUpdate();
        }*/

        /*Actualiacion de creditos A estado ACTIVO, los cuales estan en estado Legal y que no tengan cuotas Vencidas. */
        Query query3 = this.entityManager.createQuery(" Select c FROM Credito c WHERE c.estado = :estado2  AND not exists (Select c1 FROM Credito c1  JOIN  c1.listCuotas cu  WHERE cu.estado = :estado3 OR cu.estado = :estado4)");
        query3.setParameter("estado2", Estado.LEGALES);
        query3.setParameter("estado3", Cuota.Estado.VENCIDO);
        query3.setParameter("estado4", Cuota.Estado.PARCIALMENTE_SALDADO);


        List<Credito> creditosLegales = query3.getResultList();

        if (!creditosLegales.isEmpty()) {
            List<Long> ids = creditosLegales.stream().map(Credito::getId).collect(Collectors.toList());

            Query query1 = this.entityManager.createQuery("UPDATE Credito c SET c.estado= :estado1  where c.id IN (:ids)");
            query1.setParameter("estado1", Estado.ACTIVO);
            query1.setParameter("ids", ids);
            query1.executeUpdate();
        }

    }
    @Transactional
    public void cambiarEstado(Credito credito, Estado estado) {
        Query query1 = this.entityManager.createQuery("UPDATE Credito c SET c.estado= :estado  where c= :credito");
        query1.setParameter("estado", estado);
        query1.setParameter("credito", credito);
        query1.executeUpdate();
    }
}
