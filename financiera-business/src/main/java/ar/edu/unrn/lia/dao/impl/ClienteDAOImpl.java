package ar.edu.unrn.lia.dao.impl;

import ar.edu.unrn.lia.dao.ClienteDAO;
import ar.edu.unrn.lia.generic.GenericDaoJpaImpl;
import ar.edu.unrn.lia.model.Cliente;
import ar.edu.unrn.lia.model.Cuota;

import javax.inject.Named;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by difabioguillermo on 11/8/17.
 */
@Named("clienteDAO")
public class ClienteDAOImpl extends GenericDaoJpaImpl<Cliente, Long> implements
        ClienteDAO, Serializable {

    private int year = Calendar.getInstance().get(Calendar.YEAR);
    private int month = Calendar.getInstance().get(Calendar.MONTH)+ 1;
    private int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    public Predicate[] getSearchPredicates(Root<Cliente> root,
                                           Map<String, String> filters) {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        List<Predicate> predicatesList = new ArrayList<Predicate>();
        root.alias("entity");

        String id = filters.get("id");
        if (id != null && !"".equals(id)) {
          Long idaux= Long.valueOf(id);
            predicatesList.add(builder.equal(root.<Long>get("id"),
                    idaux));
        }
        String dni = filters.get("dni");
        if (dni != null && !"".equals(dni)) {
            predicatesList.add(builder.like(root.<String>get("dni"),
                    '%' + dni + '%'));
        }
        String nombre = filters.get("nombre");
        if (nombre != null && !"".equals(nombre)) {
            predicatesList.add(builder.like(root.<String>get("nombre"),
                    '%' + nombre + '%'));
        }
        String apellido = filters.get("apellido");
        if (apellido != null && !"".equals(apellido)) {
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

    @Override
    public List<Cliente> searchMorosos() {
        Query query = this.entityManager.createQuery("SELECT new ar.edu.unrn.lia.model.Cliente(c.id, c.dni, c.nombre, c.apellido, c.celular, sum(cuo.saldoAPagar), cre.id) FROM Cliente c LEFT JOIN c.creditos cre LEFT JOIN cre.listCuotas cuo WHERE  cuo.estado = :estado GROUP BY cre.id");
        query.setParameter("estado", Cuota.Estado.VENCIDO);
        return query.getResultList();
    }

    @Override
    public List<Cliente> searchVencimientosDelDia() {
        Query query = this.entityManager.createQuery("SELECT new ar.edu.unrn.lia.model.Cliente(c.id, c.dni, c.nombre, c.apellido, c.celular, cre.montoCutoas, cre.id) FROM Cliente c LEFT JOIN c.creditos cre LEFT JOIN cre.listCuotas cuo WHERE YEAR(cuo.fechaVencimiento)=:anio AND DAY(cuo.fechaVencimiento)=:dia AND MONTH(cuo.fechaVencimiento)=:mes");
        query.setParameter("dia", day);
        query.setParameter("mes", month);
        query.setParameter("anio", year);
        return query.getResultList();
        /*SELECT c.id, c.dni, c.nombre, c.apellido, c.celular, cre.monto_cuotas
        FROM Cliente c LEFT JOIN Credito cre On c.id=cre.cliente_id LEFT JOIN cuota cu On cu.credito_id=cre.id
        WHERE YEAR(cu.fecha_vencimiento)=2019 AND DAY(cu.fecha_vencimiento)=4 AND MONTH(cu.fecha_vencimiento)=1*/
    }
}
