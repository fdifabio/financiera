package ar.edu.unrn.lia.dao;

import ar.edu.unrn.lia.generic.GenericDao;
import ar.edu.unrn.lia.model.Caja;
import ar.edu.unrn.lia.model.Movimiento;

import java.util.List;

public interface MovimientoDAO extends GenericDao<Movimiento, Long> {

    List<Movimiento> findAll();

    List<Movimiento> findAllByCajaId(Long idCaja);
}
