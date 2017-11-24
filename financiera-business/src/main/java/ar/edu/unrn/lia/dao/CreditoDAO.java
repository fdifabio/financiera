package ar.edu.unrn.lia.dao;

import ar.edu.unrn.lia.generic.GenericDao;
import ar.edu.unrn.lia.model.Credito;

import java.util.List;

/**
 * Created by Lucas on 22/08/2017.
 */
public interface CreditoDAO extends GenericDao<Credito, Long> {

    List<Credito> listByIdCliente(Long idcliente);

    public void actualizarEstados();
}

