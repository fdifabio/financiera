package ar.edu.unrn.lia.service;

import ar.edu.unrn.lia.model.GenericEntity;

public interface IService<T extends GenericEntity> extends ReadOnlyService<T>{


	void save(T t);

	void delete(T t);

}
