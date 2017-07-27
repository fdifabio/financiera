package ar.edu.unrn.lia.service.impl;

import ar.edu.unrn.lia.dao.ProvinciaDAO;
import ar.edu.unrn.lia.model.Provincia;
import ar.edu.unrn.lia.service.ProvinciaService;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Map;

@Named("provinciaService")
public class ProvinciaServiceImpl implements ProvinciaService {

    @Inject
    ProvinciaDAO entityDAO;

    public ProvinciaDAO getEntityDAO() {
        return entityDAO;
    }

    public Provincia findByName(String nombre) {
        Provincia provincia = null;
        try {
            provincia = entityDAO.getEntityByName(nombre);
        } catch (NoResultException e) {

        }
        return provincia;

    }

    @Transactional(readOnly = true)
    public Long getCount(Map<String, String> filters) {
        return getEntityDAO().count(
                getEntityDAO().getSearchPredicates(getEntityDAO().rootCount(),
                        filters));
    }

    @Transactional(readOnly = true)
    public List<Provincia> getList(Integer page, Integer pagesize, Map<String, String> filters, String sortField,
                                   Boolean asc, boolean distinct) {
        return getEntityDAO().listwithPag(getEntityDAO().getSearchPredicates(getEntityDAO().rootCount(), filters), page,
                pagesize, sortField, asc, false);
    }

    @Transactional
    public void save(Provincia entity) {
        if (entity.getId() == null) {

            getEntityDAO().create(entity);

        } else {
            getEntityDAO().update(entity);

        }
    }

    @Transactional
    public void delete(Provincia entity) {
        getEntityDAO().delete(entity);
    }

    public Provincia getEntityById(Long id) {
        return getEntityDAO().read(id);
    }

    public List<Provincia> getAll() {
        return getEntityDAO().findAll();
    }

}
