package ar.edu.unrn.lia.service;

import ar.edu.unrn.lia.model.Cliente;
import ar.edu.unrn.lia.model.Credito;

import java.util.List;

/**
 * Created by Lucas on 22/08/2017.
 */
public interface
CreditoService extends IGenericService<Credito> {
    List<Credito> listByClienteId(Long idcliente);


}
