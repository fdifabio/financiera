package ar.edu.unrn.lia.service;

import ar.edu.unrn.lia.model.Caja;
import ar.edu.unrn.lia.model.Cobro;
import ar.edu.unrn.lia.model.Credito;

/**
 * Created by Lucas on 22/08/2017.
 */
public interface
CobroService extends IGenericService<Cobro> {
    public void registarCobro(Credito credito, Caja caja);
}
