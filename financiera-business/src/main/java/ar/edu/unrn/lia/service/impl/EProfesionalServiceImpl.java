package ar.edu.unrn.lia.service.impl;

import ar.edu.unrn.lia.config.ParamValue;
import ar.edu.unrn.lia.dao.EProfesionalDAO;
import ar.edu.unrn.lia.model.EProfesional;
import ar.edu.unrn.lia.service.EProfesionalService;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Named("eProfesionalService")
public class EProfesionalServiceImpl implements EProfesionalService, Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    EProfesionalDAO entityDAO;

    @ParamValue(key = "montoGEO")
    private Double montoGEO;

    public EProfesionalDAO getEntityDAO() {
        return entityDAO;
    }

    public EProfesional findByName(String legajo) {
        return null;

    }

    @Override
    @Transactional(readOnly = true)
    public Long getCount(Map<String, String> filters) {
        return getEntityDAO().count(getEntityDAO().getSearchPredicates(getEntityDAO().rootCount(), filters));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EProfesional> getList(Integer page, Integer pagesize, Map<String, String> filters, String sortField,
                                      Boolean asc, boolean distinct) {
        List<EProfesional> results = getEntityDAO().listwithPag(getEntityDAO().getSearchPredicates(getEntityDAO().rootCount(), filters), page,
                pagesize, "fecha", false, false);

        for (EProfesional ep : results
                ) {
            ep.setMontoGEO(montoGEO);

        }


        return results;
    }

    @Override
    @Transactional
    public void save(EProfesional entity) {
        if (entity.getId() == null) {
            getEntityDAO().create(entity);
        } else {
            getEntityDAO().update(entity);

        }
    }

    @Override
    @Transactional
    public void delete(EProfesional entity) {
        getEntityDAO().delete(entity);
    }

    public EProfesional getEntityById(Long id) {
        EProfesional ep = getEntityDAO().read(id);
        ep.setMontoGEO(montoGEO);
        return ep;
    }

    public List<EProfesional> getAll() {

        List<EProfesional> results = getEntityDAO().findAll();

        for (EProfesional ep : results
                ) {
            ep.setMontoGEO(montoGEO);

        }
        return results;
    }


}
