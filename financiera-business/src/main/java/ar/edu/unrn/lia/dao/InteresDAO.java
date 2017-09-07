package ar.edu.unrn.lia.dao;

import ar.edu.unrn.lia.generic.GenericDao;
import ar.edu.unrn.lia.model.Interes;

import java.util.List;

public interface InteresDAO extends GenericDao<Interes, Long> {

    List<Interes> findAll();

    void setOrden(Interes entity);
}
