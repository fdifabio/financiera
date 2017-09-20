package ar.edu.unrn.lia.service.impl;

import ar.edu.unrn.lia.dao.MovimientoDAO;
import ar.edu.unrn.lia.model.Movimiento;
import ar.edu.unrn.lia.service.MovimientoService;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

/**
 * Created by Lucas on 22/08/2017.
 */
@Named("movimientoService")
public class MovimientoServiceImpl implements MovimientoService {
    @Inject
    MovimientoDAO entityDAO;

    public MovimientoDAO getEntityDAO() {
        return entityDAO;
    }

    public Movimiento findByName(String nombre) {
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
    public List<Movimiento> getList(Integer page, Integer pagesize, Map<String, String> filters, String sortField,
                                    Boolean asc, boolean distinct) {
        return getEntityDAO().listwithPag(getEntityDAO().getSearchPredicates(getEntityDAO().rootCount(), filters), page,
                pagesize, sortField, asc, false);
    }

    @Transactional
    public void save(Movimiento entity) {
        if (entity.getId() == null) {

            getEntityDAO().create(entity);

        } else {
            getEntityDAO().update(entity);

        }
    }

    @Transactional
    public void delete(Movimiento entity) {
        getEntityDAO().delete(entity);
    }

    public Movimiento getEntityById(Long id) {
        return getEntityDAO().read(id);
    }

    public List<Movimiento> getAll() {
        return getEntityDAO().findAll();
    }

    public List<Movimiento> findAllByCajaId(Long idCaja) {
        return getEntityDAO().findAllByCajaId(idCaja);
    }

}
