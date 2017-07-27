package ar.edu.unrn.lia.service;

import ar.edu.unrn.lia.model.GenericEntity;

public interface IGenericService<T extends GenericEntity> extends ReadOnlyService<T>{

	void save(T t);

	void delete(T t);
}
