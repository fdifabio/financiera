package ar.edu.unrn.lia.service.impl;

import ar.edu.unrn.lia.dao.GaranteDAO;
import ar.edu.unrn.lia.model.Garante;
import ar.edu.unrn.lia.service.GaranteService;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

/**
 * Created by difabioguillermo on 11/8/17.
 */
@Named("garanteService")
public class GaranteServiceImpl implements GaranteService {
    @Inject
    GaranteDAO entityDAO;

    public GaranteDAO getEntityDAO() {
        return entityDAO;
    }


    @Transactional(readOnly = true)
    public Long getCount(Map<String, String> filters) {
        return getEntityDAO().count(
                getEntityDAO().getSearchPredicates(getEntityDAO().rootCount(),
                        filters));
    }

    @Transactional(readOnly = true)
    public List<Garante> getList(Integer page, Integer pagesize, Map<String, String> filters, String sortField,
                                 Boolean asc, boolean distinct) {
        return getEntityDAO().listwithPag(getEntityDAO().getSearchPredicates(getEntityDAO().rootCount(), filters), page,
                pagesize, sortField, asc, false);
    }

    @Transactional
    public void save(Garante entity) {
        if (entity.getId() == null) {

            getEntityDAO().create(entity);

        } else {
            getEntityDAO().update(entity);

        }
    }

    @Transactional
    public void delete(Garante entity) {
        getEntityDAO().delete(entity);
    }

    public Garante getEntityById(Long id) {
        return getEntityDAO().read(id);
    }

    @Override
    public Garante findByName(String name) {
        return null;
    }

    public List<Garante> getAll() {
        return getEntityDAO().findAll();
    }

    @Override
    public List<Garante> searchByApellidoNombre(String apellidoNombre) {
        return getEntityDAO().searchByApellidoNombre(apellidoNombre);
    }

}
