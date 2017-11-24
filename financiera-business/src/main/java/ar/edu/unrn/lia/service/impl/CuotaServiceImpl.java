package ar.edu.unrn.lia.service.impl;

import ar.edu.unrn.lia.dao.ClienteDAO;
import ar.edu.unrn.lia.dao.CuotaDAO;
import ar.edu.unrn.lia.dto.CuotaDTO;
import ar.edu.unrn.lia.model.Cliente;
import ar.edu.unrn.lia.model.Cuota;
import ar.edu.unrn.lia.service.ClienteService;
import ar.edu.unrn.lia.service.CuotaService;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by difabioguillermo on 11/8/17.
 */
@Named("cuotaService")
public class CuotaServiceImpl implements CuotaService {
    @Inject
    CuotaDAO entityDAO;

    public CuotaDAO getEntityDAO() {
        return entityDAO;
    }

    public Cuota findByName(String nombre) {
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
    public List<Cuota> getList(Integer page, Integer pagesize, Map<String, String> filters, String sortField,
                               Boolean asc, boolean distinct) {
        return getEntityDAO().listwithPag(getEntityDAO().getSearchPredicates(getEntityDAO().rootCount(), filters), page,
                pagesize, sortField, asc, false);
    }

    @Transactional
    public void save(Cuota entity) {
        if (entity.getId() == null) {

            getEntityDAO().create(entity);

        } else {
            getEntityDAO().update(entity);

        }
    }

    @Transactional
    public void delete(Cuota entity) {
        getEntityDAO().delete(entity);
    }

    public Cuota getEntityById(Long id) {
        return getEntityDAO().read(id);
    }

    public List<Cuota> getAll() {
        return getEntityDAO().findAll();
    }


    @Override
    public List<CuotaDTO> listAdeudadas(int year, int month) {
        return getEntityDAO().listAdeudadas(year, month);
    }

    @Override
    public List<Integer> listAniosAdeudadas() {
        return getEntityDAO().listAniosAdeudadas();
    }

    @Override
    public List<Integer> listAnios() {
        return getEntityDAO().listAnios();
    }

    @Override
    public List<CuotaDTO> listSaldadas(int year) {
        return getEntityDAO().listSaldadas(year);
    }

    @Override
    public List<CuotaDTO> countAdeudadas(int year, int month) {
        return getEntityDAO().countAdeudadas(year, month);
    }
}
