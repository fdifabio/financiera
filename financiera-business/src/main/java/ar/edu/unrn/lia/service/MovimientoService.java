package ar.edu.unrn.lia.service;

import ar.edu.unrn.lia.dto.MonthYearDTO;
import ar.edu.unrn.lia.dto.MovimientoDTO;
import ar.edu.unrn.lia.model.Movimiento;

import java.util.List;
import java.util.Map;

/**
 * Created by Lucas on 22/08/2017.
 */
public interface MovimientoService extends IGenericService<Movimiento> {

    List<Movimiento> findAllByCajaId(Long idCaja);


    List<MovimientoDTO> findByMonthYear(int month, int year, Movimiento.Tipo tipo);

    List<Integer> listAnios();

}
