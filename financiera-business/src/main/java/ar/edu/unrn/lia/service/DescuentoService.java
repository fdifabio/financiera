package ar.edu.unrn.lia.service;

import ar.edu.unrn.lia.model.Descuento;
import ar.edu.unrn.lia.model.Interes;

import java.util.List;

/**
 * Created by Lucas on 22/08/2017.
 */
public interface DescuentoService extends IGenericService<Descuento> {

    void updateOrden(List<Descuento> list);
}
