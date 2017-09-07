package ar.edu.unrn.lia.service.impl;

import ar.edu.unrn.lia.dao.InteresDAO;
import ar.edu.unrn.lia.model.Credito;
import ar.edu.unrn.lia.model.Interes;
import ar.edu.unrn.lia.service.InteresService;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

/**
 * Created by Lucas on 22/08/2017.
 */
@Named("interesService")
public class InteresServiceImpl implements InteresService {
    @Inject
    InteresDAO entityDAO;

    public InteresDAO getEntityDAO() {
        return entityDAO;
    }

    public Interes findByName(String nombre) {
        return null;
        //TODO:

    }

    @Transactional(readOnly = true)
    public Long getCount(Map<String, String> filters) {
        return getEntityDAO().count(
                getEntityDAO().getSearchPredicates(getEntityDAO().rootCount(),
                        filters));
    }

    @Transactional(readOnly = true)
    public List<Interes> getList(Integer page, Integer pagesize, Map<String, String> filters, String sortField,
                                 Boolean asc, boolean distinct) {
        return getEntityDAO().listwithPag(getEntityDAO().getSearchPredicates(getEntityDAO().rootCount(), filters), page,
                pagesize, sortField, asc, false);
    }

    @Transactional
    public void save(Interes entity) {
        if (entity.getId() == null) {

            getEntityDAO().create(entity);

        } else {
            getEntityDAO().update(entity);

        }
    }

    @Transactional
    public void delete(Interes entity) {
        getEntityDAO().delete(entity);
    }

    public Interes getEntityById(Long id) {
        return getEntityDAO().read(id);
    }

    public List<Interes> getAll() {
        return getEntityDAO().findAll();
    }
}
