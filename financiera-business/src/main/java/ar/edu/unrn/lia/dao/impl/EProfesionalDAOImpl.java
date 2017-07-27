package ar.edu.unrn.lia.dao.impl;

import ar.edu.unrn.lia.dao.EProfesionalDAO;
import ar.edu.unrn.lia.dto.EProfesionalDTO;
import ar.edu.unrn.lia.dto.OrdenDeServicioDTO;
import ar.edu.unrn.lia.generic.GenericDaoJpaImpl;
import ar.edu.unrn.lia.model.EProfesional;
import ar.edu.unrn.lia.model.OrdenDeServicio;

import javax.inject.Named;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Named("eProfesionalDAO")
public class EProfesionalDAOImpl extends GenericDaoJpaImpl<EProfesional, Long> implements EProfesionalDAO, Serializable {

    public Predicate[] getSearchPredicates(Root<EProfesional> root,
                                           Map<String, String> filters) {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        List<Predicate> predicatesList = new ArrayList<Predicate>();
        root.alias("entity");

        String perfilAGRId = filters.get("perfilAGR.id");
        if (perfilAGRId != null) {
            predicatesList.add(builder
                    .equal(root.get("perfilAGR").<Long> get("id"), perfilAGRId));
        }

        String agrimensorNombre = filters.get("perfilAGR.nombre");
        if (agrimensorNombre != null) {
            predicatesList.add(builder.like(root.get("perfilAGR").<String>get("nombre"), agrimensorNombre));
        }

        String nombre = filters.get("nombre");
        if (nombre != null && !"".equals(nombre)) {
            predicatesList.add(builder.like(root.<String>get("nombre"),
                    '%' + nombre + '%'));
        }

        String estadoODS = filters.get("ordenDeServicio.ODSestado");
        if (estadoODS != null && !"".equals(estadoODS)) {
              predicatesList.add(builder.equal(root.get("ordenDeServicio").get("ODSestado"), OrdenDeServicio.EstadoODS.valueOf(estadoODS.toUpperCase())));
        }

        String  ODScodigoTX = filters.get("ordenDeServicio.ODScodigoTX");
        if (ODScodigoTX != null) {
            predicatesList.add(builder.like(root.get("ordenDeServicio").<String>get("ODScodigoTX"), ODScodigoTX));
        }


        return predicatesList.toArray(new Predicate[predicatesList.size()]);
    }


    @Override
    public List<EProfesionalDTO> listwithPagDTO(Predicate[] where, Integer page, Integer pagesize, String sortField, Boolean asc) {

        CriteriaBuilder criteriaBuilder = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<EProfesionalDTO> cq = criteriaBuilder
                .createQuery(EProfesionalDTO.class);
       //TODO: cq.distinct(true);
        Root<EProfesional> fromEProfesional = cq.from(EProfesional.class);
        fromEProfesional.alias("entity");
        Order order = orderByQuery(sortField, asc, criteriaBuilder, fromEProfesional);
        cq.select(criteriaBuilder
                .construct(
                        EProfesionalDTO.class,
                        fromEProfesional.get("id"),
                        fromEProfesional.get("nombre"),
                        fromEProfesional.get("fecha"),
                        fromEProfesional.get("perfilAGR").get("id"),
                        fromEProfesional.get("perfilAGR").get("nombre"),
                        fromEProfesional.get("perfilAGR").get("apellido"),
                        fromEProfesional.get("comitente").get("nombre"),
                        fromEProfesional.get("comitente").get("responsable").get("nombre"),
                        fromEProfesional.get("comitente").get("responsable").get("email"),
                        fromEProfesional.get("inmueble").get("tipo"),
                        fromEProfesional.get("estado"),
                        fromEProfesional.get("ordenDeServicio").get("ODSestado"),
                        fromEProfesional.get("tasaDescripcion"),
                        fromEProfesional.get("tasaCosto"),
                        fromEProfesional.get("tasaUnidades"),
                        fromEProfesional.get("tasaCostoAdicional")

                )
        );

       final TypedQuery<EProfesionalDTO> query = this.entityManager
                .createQuery(cq.where(where).orderBy(order));

        query.setFirstResult(page).setMaxResults(pagesize);
        List<EProfesionalDTO> resultList = query.getResultList();

        return resultList;
        /** final CriteriaBuilder criteriaBuilder = this.getEntityManager().getCriteriaBuilder();

         final CriteriaQuery<EProfesionalDTO> criteriaQuery = criteriaBuilder.createQuery(EProfesionalDTO.class);

         final Root<EProfesional> rootProyecto = criteriaQuery.from(EProfesional.class);
         final Join<EProfesional, PerfilAGR> joinProyecto = rootProyecto.join("perfilAGR",
         JoinType.LEFT);
         final Join<PerfilAGR, User> joinPerfil = joinProyecto.join("user",
         JoinType.LEFT);
         final Join<EProfesional, Empresa> joinEmpresa = rootProyecto.join("empresa",
         JoinType.LEFT);
         final Join<EProfesional, Inmueble> joinInmueble = rootProyecto.join("inmueble",
         JoinType.LEFT);

         Order order = orderByQuery(sortField, asc, criteriaBuilder, rootProyecto);

         rootProyecto.alias("entity");

         criteriaQuery.distinct(true)
         .select(criteriaBuilder.construct(EProfesionalDTO.class, rootProyecto.get("id"),
         rootProyecto.get("perfilAGR").get("nombre"),
         rootProyecto.get("perfilAGR").get("apellido"),
         rootProyecto.get("perfilAGR").get("matricula"),

         // rootProyecto.get("proyectoDetalleSigeva").get("titulo"),
         rootProyecto.get("proyectoDetalleSigeva").get("directorSigeva").get("nombre"),
         rootProyecto.get("proyectoDetalleSigeva").get("directorSigeva").get("apellido"),
         rootProyecto.get("formularioAdmision").get("sede").get("nombre"),
         rootProyecto.get("proyectoDetalleSigeva").get("id"),
         rootProyecto.get("proyectoDetalleSigeva").get("codigoTramite"), rootProyecto.get("idInterno"),
         rootProyecto.get("proyectoDetalleSigeva").get("fechaInicio"));

         criteriaQuery.groupBy(rootProyecto.get("id"),
         rootProyecto.get("proyectoDetalleSigeva").get("estado").get("estado"),
         // rootProyecto.get("proyectoDetalleSigeva").get("titulo"),
         rootProyecto.get("proyectoDetalleSigeva").get("directorSigeva").get("nombre"),
         rootProyecto.get("proyectoDetalleSigeva").get("directorSigeva").get("apellido"),
         rootProyecto.get("formularioAdmision").get("sede").get("nombre"),
         rootProyecto.get("proyectoDetalleSigeva").get("id"),
         rootProyecto.get("proyectoDetalleSigeva").get("codigoTramite"), rootProyecto.get("idInterno"),
         rootProyecto.get("proyectoDetalleSigeva").get("fechaInicio"),
         rootProyecto.get("proyectoDetalleSigeva").get("fechaFin"));

         final TypedQuery<EProfesionalDTO> query = this.entityManager
         .createQuery(criteriaQuery.where(where).orderBy(order));

         query.setFirstResult(page).setMaxResults(pagesize);
         List<EProfesionalDTO> results = query.getResultList();

         return results;**/
    }

    @Override
    public List<OrdenDeServicioDTO> odslistwithPagDTO(Predicate[] where, Integer page, Integer pagesize, String sortField, Boolean asc) {

        CriteriaBuilder criteriaBuilder = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<OrdenDeServicioDTO> cq = criteriaBuilder
                .createQuery(OrdenDeServicioDTO.class);
        //TODO: cq.distinct(true);
        Root<EProfesional> fromEProfesional = cq.from(EProfesional.class);
        fromEProfesional.alias("entity");

        Order order = orderByQuery(sortField, asc, criteriaBuilder, fromEProfesional);
        cq.select(criteriaBuilder
                .construct(
                        OrdenDeServicioDTO.class,
                        fromEProfesional.get("id"),
                        fromEProfesional.get("ordenDeServicio").get("ODSfecha"),
                        fromEProfesional.get("perfilAGR").get("id"),
                        fromEProfesional.get("perfilAGR").get("nombre"),
                        fromEProfesional.get("perfilAGR").get("apellido"),
                        fromEProfesional.get("perfilAGR").get("matricula"),
                        fromEProfesional.get("perfilAGR").get("cuit"),
                        fromEProfesional.get("comitente").get("nombre"),
                        fromEProfesional.get("comitente").get("empresa"),
                        fromEProfesional.get("comitente").get("cuit"),
                        fromEProfesional.get("ordenDeServicio").get("monto"),
                        fromEProfesional.get("ordenDeServicio").get("formapago"),
                        fromEProfesional.get("ordenDeServicio").get("ODScodigoTX"),
                        fromEProfesional.get("ordenDeServicio").get("ODSestado"),
                        fromEProfesional.get("tasaDescripcion"))
        );

        final TypedQuery<OrdenDeServicioDTO> query = this.entityManager
                .createQuery(cq.where(where).orderBy(order));

        query.setFirstResult(page).setMaxResults(pagesize);
        List<OrdenDeServicioDTO> resultList = query.getResultList();

        return resultList;
    }

   @Override
    public EProfesional read(Long id) {

       String queryString = "select e from EProfesional e  " +
               "LEFT JOIN FETCH e.inmueble i " +
               "LEFT JOIN FETCH i.ciudad ciudad " +
               "LEFT JOIN FETCH i.departamento depto " +
               "LEFT JOIN FETCH depto.provincia prov " +
               "LEFT JOIN FETCH e.comitente c " +
               "LEFT JOIN FETCH e.perfilAGR a " +
               "LEFT JOIN FETCH a.user u " +
               "LEFT JOIN FETCH a.ciudadReal cAGR " +
               "LEFT JOIN FETCH cAGR.departamento dAGR " +
               "LEFT JOIN FETCH dAGR.provincia pAGR " +
               "where e.id = :id";
       Query query = this.entityManager.createQuery(queryString);
       query.setParameter("id", id);
       return (EProfesional) query.getSingleResult();
    }
}
