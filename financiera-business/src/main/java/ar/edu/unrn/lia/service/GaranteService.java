package ar.edu.unrn.lia.service;

import ar.edu.unrn.lia.model.Cliente;
import ar.edu.unrn.lia.model.Garante;

import java.util.List;

/**
 * Created by difabioguillermo on 11/8/17.
 */
public interface GaranteService extends IGenericService<Garante> {

    public List<Garante> searchByApellidoNombre(String apellidoNombre);

}
