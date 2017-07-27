package ar.edu.unrn.lia.dto;

import ar.edu.unrn.lia.model.GenericEntity;

public abstract class AbstractDTO<T> implements GenericEntity<T> {

    protected T id;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

}
