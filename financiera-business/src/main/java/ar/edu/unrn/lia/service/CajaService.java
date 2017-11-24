package ar.edu.unrn.lia.service;

import ar.edu.unrn.lia.model.Caja;
import ar.edu.unrn.lia.model.Interes;
import ar.edu.unrn.lia.model.Movimiento;

import java.util.List;

/**
 * Created by Lucas on 22/08/2017.
 */
public interface CajaService extends IGenericService<Caja> {

    Caja habilitarCaja(Caja caja, Movimiento movimiento);

    void cerrarCaja(Caja caja);

    Caja getLast();
}
