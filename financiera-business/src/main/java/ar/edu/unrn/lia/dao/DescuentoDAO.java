package ar.edu.unrn.lia.dao;

import ar.edu.unrn.lia.generic.GenericDao;
import ar.edu.unrn.lia.model.Descuento;
import ar.edu.unrn.lia.model.Interes;

import java.util.List;

public interface DescuentoDAO extends GenericDao<Descuento, Long> {

    List<Descuento> findAll();

    void setOrden(Descuento entity);
}
