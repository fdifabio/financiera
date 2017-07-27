package ar.edu.unrn.lia.model;

public interface GenericEntity<T> {

    T getId();

    void setId(T id);

}