package ar.edu.unrn.lia.dao;

import ar.edu.unrn.lia.generic.GenericDao;
import ar.edu.unrn.lia.model.Ciudad;
import ar.edu.unrn.lia.model.Departamento;

import javax.persistence.NoResultException;
import java.util.List;

public interface DepartamentoDAO extends GenericDao<Departamento, Long> {

	Departamento getEntityByName(String nombre) throws NoResultException;

	List<Departamento> getList(Long id);
}
