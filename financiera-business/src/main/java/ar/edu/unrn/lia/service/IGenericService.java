package ar.edu.unrn.lia.service;

import java.util.List;
import java.util.Map;

public interface IGenericService<T> {

	public void save(T t);

	public void delete(T t);

	public T getEntityById(Long id);

	public T findByName(String name);

	public List<T> getAll();

	public Long getCount(Map<String, String> filters);

	public List<T> getList(Integer page, Integer pagesize, Map<String, String> filters, String sortField, Boolean asc, boolean distinct);


}
