package ar.edu.unrn.lia.dao;

import ar.edu.unrn.lia.dto.EProfesionalDTO;
import ar.edu.unrn.lia.dto.OrdenDeServicioDTO;
import ar.edu.unrn.lia.generic.GenericDao;
import ar.edu.unrn.lia.model.EProfesional;

import javax.persistence.criteria.Predicate;
import java.util.List;

public interface EProfesionalDAO extends GenericDao<EProfesional, Long> {

    public List<EProfesionalDTO> listwithPagDTO(Predicate[] where, Integer page, Integer pagesize, String sortField,
                                                Boolean asc);

    public List<OrdenDeServicioDTO> odslistwithPagDTO(Predicate[] where, Integer page, Integer pagesize, String sortField,
                                                   Boolean asc);
}
