package ar.edu.unrn.lia.dao;

import ar.edu.unrn.lia.generic.GenericDao;
import ar.edu.unrn.lia.model.Caja;
import ar.edu.unrn.lia.model.Interes;

import java.util.List;

public interface CajaDAO extends GenericDao<Caja, Long> {

    List<Caja> findAll();

    void cerrarCaja(Caja caja);

    Caja getLast();
}
