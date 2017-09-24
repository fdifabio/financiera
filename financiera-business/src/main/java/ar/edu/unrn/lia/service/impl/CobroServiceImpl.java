package ar.edu.unrn.lia.service.impl;

import ar.edu.unrn.lia.dao.CobroDAO;
import ar.edu.unrn.lia.model.Cobro;
import ar.edu.unrn.lia.service.CobroService;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

/**
 * Created by Lucas on 22/08/2017.
 */
@Named("cobroService")
public class CobroServiceImpl implements CobroService {
    @Inject
    CobroDAO entityDAO;

    public CobroDAO getEntityDAO() {
        return entityDAO;
    }


    @Transactional(readOnly = true)
    public Long getCount(Map<String, String> filters) {
        return getEntityDAO().count(
                getEntityDAO().getSearchPredicates(getEntityDAO().rootCount(),
                        filters));
    }


    @Transactional(readOnly = true)
    public List<Cobro> getList(Integer page, Integer pagesize, Map<String, String> filters, String sortField,
                               Boolean asc, boolean distinct) {
        return getEntityDAO().listwithPag(getEntityDAO().getSearchPredicates(getEntityDAO().rootCount(), filters), page,
                pagesize, sortField, asc, false);
    }

    @Transactional
    public void save(Cobro entity) {
        if (entity.getId() == null) {

            getEntityDAO().create(entity);

        } else {
            getEntityDAO().update(entity);

        }
    }

    @Transactional
    public void delete(Cobro entity) {
        getEntityDAO().delete(entity);
    }

    public Cobro getEntityById(Long id) {
        return getEntityDAO().read(id);
    }

    @Override
    public Cobro findByName(String name) {
        //TODO:
        return null;
    }

    public List<Cobro> getAll() {
        return getEntityDAO().findAll();
    }
}
