package ar.edu.unrn.lia.service.impl;

import ar.edu.unrn.lia.dao.CreditoDAO;
import ar.edu.unrn.lia.model.Credito;
import ar.edu.unrn.lia.service.CreditoService;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

/**
 * Created by Lucas on 22/08/2017.
 */
@Named("creditoService")
public class CreditoServiceImpl implements CreditoService {
    @Inject
    CreditoDAO entityDAO;

    public CreditoDAO getEntityDAO() {
        return entityDAO;
    }

    public Credito findByName(String nombre) {
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
    public List<Credito> listByClienteId(Long idcliente) {
        return getEntityDAO().listByIdCliente(idcliente);

    }

    @Transactional(readOnly = true)
    public List<Credito> getList(Integer page, Integer pagesize, Map<String, String> filters, String sortField,
                                 Boolean asc, boolean distinct) {
        return getEntityDAO().listwithPag(getEntityDAO().getSearchPredicates(getEntityDAO().rootCount(), filters), page,
                pagesize, sortField, asc, false);
    }

    @Transactional
    public void save(Credito entity) {
        if (entity.getId() == null) {

            getEntityDAO().create(entity);

        } else {
            getEntityDAO().update(entity);

        }
    }

    @Transactional
    public void delete(Credito entity) {
        getEntityDAO().delete(entity);
    }

    public Credito getEntityById(Long id) {
        return getEntityDAO().read(id);
    }

    public List<Credito> getAll() {
        return getEntityDAO().findAll();
    }
}
