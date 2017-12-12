package ar.edu.unrn.lia.dao;

import ar.edu.unrn.lia.dto.MonthYearDTO;
import ar.edu.unrn.lia.dto.MovimientoDTO;
import ar.edu.unrn.lia.generic.GenericDao;
import ar.edu.unrn.lia.model.Movimiento;

import java.util.List;
import java.util.Map;

public interface MovimientoDAO extends GenericDao<Movimiento, Long> {

    List<Movimiento> findAll();

    List<Movimiento> findAllByCajaId(Long idCaja);

    List<MovimientoDTO> findByMonthYear(int month, int year, Movimiento.Tipo tipo);

    List<Integer> listAnios();

}
