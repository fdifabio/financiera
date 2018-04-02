package ar.edu.unrn.lia.dao;

import ar.edu.unrn.lia.generic.GenericDao;
import ar.edu.unrn.lia.model.Cliente;
import ar.edu.unrn.lia.model.Garante;

import java.util.List;

/**
 * Created by difabioguillermo on 11/8/17.
 */
public interface GaranteDAO extends GenericDao<Garante, Long> {

    public List<Garante> searchByApellidoNombre(String apellidoNombre);

}

