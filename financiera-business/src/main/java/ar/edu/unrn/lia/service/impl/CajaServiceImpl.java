package ar.edu.unrn.lia.service.impl;

import ar.edu.unrn.lia.dao.CajaDAO;
import ar.edu.unrn.lia.dao.MovimientoDAO;
import ar.edu.unrn.lia.model.Caja;
import ar.edu.unrn.lia.model.Movimiento;
import ar.edu.unrn.lia.service.CajaService;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Lucas on 22/08/2017.
 */
@Named("cajaService")
public class CajaServiceImpl implements CajaService {
    @Inject
    CajaDAO entityDAO;

    @Inject
    MovimientoDAO movimientoDAO;

    public CajaDAO getEntityDAO() {
        return entityDAO;
    }

    public MovimientoDAO getMovimientoDAO() {
        return movimientoDAO;
    }

    public Caja findByName(String nombre) {
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
    public List<Caja> getList(Integer page, Integer pagesize, Map<String, String> filters, String sortField,
                              Boolean asc, boolean distinct) {
        return getEntityDAO().listwithPag(getEntityDAO().getSearchPredicates(getEntityDAO().rootCount(), filters), page,
                pagesize, sortField, asc, false);
    }

    @Transactional
    public void save(Caja entity) {
        if (entity.getId() == null) {
            getEntityDAO().create(entity);
        } else {
            getEntityDAO().update(entity);

        }
    }

    @Transactional
    public Caja habilitarCaja(Caja caja, Movimiento movimiento) {
        this.save(caja);
        movimiento.setCaja(caja);
        getMovimientoDAO().create(movimiento);
        ArrayList<Movimiento> movimientos = new ArrayList<>();
        movimientos.add(movimiento);
        caja.setMovimientos(movimientos);
        return caja;
    }

    @Transactional
    public void cerrarCaja(Caja caja) {
        getEntityDAO().cerrarCaja(caja);
    }

    public Caja getLast() {
        return getEntityDAO().getLast();
    }

    public Caja getLastSaldo() {
        return getEntityDAO().getLastSaldo();
    }

    @Transactional
    public void delete(Caja entity) {
        getEntityDAO().delete(entity);
    }

    public Caja getEntityById(Long id) {
        return getEntityDAO().read(id);
    }

    public List<Caja> getAll() {
        return getEntityDAO().findAll();
    }

}
