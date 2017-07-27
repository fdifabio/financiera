package ar.edu.unrn.lia.service.impl;

import ar.edu.unrn.lia.dao.TasaDAO;
import ar.edu.unrn.lia.exception.BusinessException;
import ar.edu.unrn.lia.model.Tasa;
import ar.edu.unrn.lia.service.TasaService;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Map;

/**
 * Created by Federico on 14/06/2017.
 */
@Named("tasaService")
public class TasaServiceImpl implements TasaService {

    @Inject
    TasaDAO entityDAO;

    public TasaDAO getEntityDAO() {
        return entityDAO;
    }

    @Transactional(readOnly = true)
    public Long getCount(Map<String, String> filters) {
        return getEntityDAO().count(getEntityDAO().getSearchPredicates(getEntityDAO().rootCount(), filters));
    }

    @Transactional(readOnly = true)
    public List<Tasa> getList(Integer page, Integer pagesize, Map<String, String> filters, String sortField,
                              Boolean asc, boolean distinct) {
        return getEntityDAO().listwithPag(getEntityDAO().getSearchPredicates(getEntityDAO().rootCount(), filters), page,
                pagesize, sortField, asc, false);
    }

    @Transactional
    public void save(Tasa entity) {
        if (entity.getId() == null) {
            getEntityDAO().create(entity);
        } else {
            getEntityDAO().update(entity);

        }
    }

    @Transactional
    public void delete(Tasa entity) {
        getEntityDAO().delete(entity);
    }

    public Tasa getEntityById(Long id) {
        return getEntityDAO().read(id);
    }

    public List<Tasa> getAll() {
        return getEntityDAO().findAll();
    }

    @Override
    public Tasa findByName(String name) {
        return null;
    }

    @Override
    public Tasa findByDescripcion(String descripcion) {
        Tasa tasa = null;
        try {
            tasa = entityDAO.getEntityByDescripcion(descripcion);
        } catch (NoResultException e) {
            if (tasa == null) {
                throw new BusinessException(e.getMessage(), e, "tasa no existe");
            }
        }
        return tasa;
    }

}
