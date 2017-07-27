package ar.edu.unrn.lia.service.impl;

import ar.edu.unrn.lia.dao.ComitenteDAO;
import ar.edu.unrn.lia.model.Comitente;
import ar.edu.unrn.lia.service.ComitenteService;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Named("comitenteService")
public class ComitenteServiceImpl extends AbstractApplyFilterSecurityService implements ComitenteService,Serializable {

    @Inject
    ComitenteDAO entityDAO;

    public ComitenteDAO getEntityDAO() {
        return entityDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public Long getCount(Map<String, String> filters) {
        filters.putAll(getUserLogged().getRole().comitenteFilter(getUserLogged()));
        return getEntityDAO().count(getEntityDAO().getSearchPredicates(getEntityDAO().rootCount(), filters));
    }
    @Override
    @Transactional(readOnly = true)
    public List<Comitente> getList(Integer page, Integer pagesize, Map<String, String> filters, String sortField,
                                      Boolean asc, boolean distinct) {
        filters.putAll(getUserLogged().getRole().comitenteFilter(getUserLogged()));
        return getEntityDAO().listwithPag(getEntityDAO().getSearchPredicates(getEntityDAO().rootCount(), filters), page,
                pagesize, "empresa", true, false);
    }

    @Transactional
    public void save(Comitente entity) {
        if (entity.getId() == null) {
            getEntityDAO().create(entity);
        } else {
            getEntityDAO().update(entity);

        }
    }

    @Transactional
    public void delete(Comitente entity) {
        getEntityDAO().delete(entity);
    }

    public Comitente getEntityById(Long id) {
        return getEntityDAO().read(id);
    }

    public List<Comitente> getAll() {
        return getEntityDAO().findAll();
    }

    @Override
    public Comitente findByName(String name) {
        // TODO Auto-generated method stub
        return null;
    }

}
