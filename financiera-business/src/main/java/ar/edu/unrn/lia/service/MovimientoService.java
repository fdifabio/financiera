package ar.edu.unrn.lia.service;

import ar.edu.unrn.lia.model.Movimiento;

import java.util.List;

/**
 * Created by Lucas on 22/08/2017.
 */
public interface MovimientoService extends IGenericService<Movimiento> {

    List<Movimiento> findAllByCajaId(Long idCaja);

}
