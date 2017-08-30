package ar.edu.unrn.lia.service;

import ar.edu.unrn.lia.model.Cliente;

import java.util.List;

/**
 * Created by difabioguillermo on 11/8/17.
 */
public interface ClienteService extends IGenericService<Cliente> {

    public List<Cliente> searchByApellidoNombre(String apellidoNombre);
}
