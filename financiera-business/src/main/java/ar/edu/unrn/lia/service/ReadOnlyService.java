package ar.edu.unrn.lia.service;

import ar.edu.unrn.lia.model.GenericEntity;

import java.util.List;

public interface ReadOnlyService<T extends GenericEntity> extends ReadListwithPagService<T> {

    List<T> getAll();

    T getEntityById(Long id);

    T findByName(String name);
}
