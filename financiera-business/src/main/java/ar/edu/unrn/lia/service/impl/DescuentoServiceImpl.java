package ar.edu.unrn.lia.service.impl;

import ar.edu.unrn.lia.dao.DescuentoDAO;
import ar.edu.unrn.lia.model.Descuento;
import ar.edu.unrn.lia.service.DescuentoService;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

/**
 * Created by Lucas on 22/08/2017.
 */
@Named("descuentoService")
public class DescuentoServiceImpl implements DescuentoService {
    @Inject
    DescuentoDAO entityDAO;

    public DescuentoDAO getEntityDAO() {
        return entityDAO;
    }

    public Descuento findByName(String nombre) {
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
    public List<Descuento> getList(Integer page, Integer pagesize, Map<String, String> filters, String sortField,
                                   Boolean asc, boolean distinct) {
        return getEntityDAO().listwithPag(getEntityDAO().getSearchPredicates(getEntityDAO().rootCount(), filters), page,
                pagesize, sortField, asc, false);
    }

    @Transactional
    public void save(Descuento entity) {
        if (entity.getId() == null) {

            getEntityDAO().create(entity);

        } else {
            getEntityDAO().update(entity);

        }
    }

    @Transactional
    public void delete(Descuento entity) {
        getEntityDAO().delete(entity);
    }

    public Descuento getEntityById(Long id) {
        return getEntityDAO().read(id);
    }

    public List<Descuento> getAll() {
        return getEntityDAO().findAll();
    }

    @Transactional
    public void updateOrden(List<Descuento> list) {
        int i = 1;
        for (Descuento descuento : list) {
            descuento.setOrden(i);
            getEntityDAO().setOrden(descuento);
            i++;
        }
    }
}
