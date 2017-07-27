package ar.edu.unrn.lia.service.impl;

import ar.edu.unrn.lia.dao.DepartamentoDAO;
import ar.edu.unrn.lia.model.Departamento;
import ar.edu.unrn.lia.service.DepartamentoService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Map;

@Named("departamentoService")
public class DepartamentoServiceImpl implements DepartamentoService {

    @Inject
    DepartamentoDAO entityDAO;

    public DepartamentoDAO getEntityDAO() {
        return entityDAO;
    }

    public Departamento findByName(String nombre) {
        Departamento departamento = null;
        try {
            departamento = entityDAO.getEntityByName(nombre);

        } catch (NoResultException e) {

        }

        return departamento;

    }

    @Override
    public List getList(Integer page, Integer pagesize, Map<String,String> filters, String sortField, Boolean asc, boolean distinct) {
        return null;
    }

    @Override
    public Long getCount(Map filters) {
        return null;
    }

    @Transactional
    public void save(Departamento entity) {
        if (entity.getId() == null) {
            getEntityDAO().create(entity);
        } else {
            getEntityDAO().update(entity);
        }
    }

    @Transactional
    public void delete(Departamento entity) {
        getEntityDAO().delete(entity);
    }

    public Departamento getEntityById(Long id) {
        return getEntityDAO().read(id);
    }

    public List<Departamento> getAll() {
        return getEntityDAO().findAll();
    }

    @Override
    public List<Departamento> findByQuery(String query, String propertyFilter,
                                          String orderDirection) {
        return getEntityDAO()
                .findByQuery(query, propertyFilter, orderDirection);
    }


    @Override
    public List<Departamento> getList(Long id) {
        try {
            if ((id != null)) {
                return entityDAO.getList(id);
            }
        } catch (UsernameNotFoundException e) {

            return null;
        }
        return null;


    }

}
