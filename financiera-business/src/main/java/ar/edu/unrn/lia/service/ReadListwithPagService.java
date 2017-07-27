package ar.edu.unrn.lia.service;

import ar.edu.unrn.lia.model.GenericEntity;

import java.util.List;
import java.util.Map;

public interface ReadListwithPagService<T extends GenericEntity>{

    Long getCount(Map<String, String> filters);

    List<T> getList(Integer page, Integer pagesize, Map<String, String> filters, String sortField, Boolean asc, boolean distinct);
}
