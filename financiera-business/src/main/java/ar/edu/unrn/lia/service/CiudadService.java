package ar.edu.unrn.lia.service;

import ar.edu.unrn.lia.model.Ciudad;

import java.util.List;

public interface CiudadService extends IGenericService<Ciudad>{

    public List<Ciudad> findByQuery(String query, String propertyFilter,	String orderDirection);
    public List<Ciudad> getList(Long id);

}
