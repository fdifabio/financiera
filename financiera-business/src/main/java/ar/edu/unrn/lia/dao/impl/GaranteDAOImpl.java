package ar.edu.unrn.lia.dao.impl;

import ar.edu.unrn.lia.dao.ClienteDAO;
import ar.edu.unrn.lia.dao.GaranteDAO;
import ar.edu.unrn.lia.generic.GenericDaoJpaImpl;
import ar.edu.unrn.lia.model.Cliente;
import ar.edu.unrn.lia.model.Cuota;
import ar.edu.unrn.lia.model.Estado;
import ar.edu.unrn.lia.model.Garante;

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
@Named("garanteDAO")
public class GaranteDAOImpl extends GenericDaoJpaImpl<Garante, Long> implements
        GaranteDAO, Serializable {
    @Override
    public List<Garante> searchByApellidoNombre(String query) {
        return this.entityManager.createQuery("FROM Garante WHERE  apellido LIKE '%" + query + "%' OR nombre LIKE '%" + query + "%' OR id LIKE '%" + query + "%' OR dni LIKE '%" + query + "%'").getResultList();
    }

}
