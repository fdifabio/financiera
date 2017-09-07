package ar.edu.unrn.lia.dao;

import ar.edu.unrn.lia.generic.GenericDao;
import ar.edu.unrn.lia.model.Ciudad;
import ar.edu.unrn.lia.model.Interes;

import javax.persistence.NoResultException;
import java.util.List;

public interface InteresDAO extends GenericDao<Interes, Long> {

    List<Interes> findAll();
}
