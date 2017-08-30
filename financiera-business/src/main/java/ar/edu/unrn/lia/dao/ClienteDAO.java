package ar.edu.unrn.lia.dao;

import ar.edu.unrn.lia.generic.GenericDao;
import ar.edu.unrn.lia.model.Cliente;

import java.util.List;

/**
 * Created by difabioguillermo on 11/8/17.
 */
public interface ClienteDAO extends GenericDao<Cliente, Long> {

    public List<Cliente> searchByApellidoNombre(String apellidoNombre);
}

