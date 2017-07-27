package ar.edu.unrn.lia.service;

import ar.edu.unrn.lia.model.Ciudad;
import ar.edu.unrn.lia.model.Departamento;

import java.util.List;

public interface DepartamentoService extends IGenericService<Departamento>{

    public List<Departamento> findByQuery(String query, String propertyFilter, String orderDirection);
    public List<Departamento> getList(Long id);

}
