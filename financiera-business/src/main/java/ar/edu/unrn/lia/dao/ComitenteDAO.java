package ar.edu.unrn.lia.dao;

import ar.edu.unrn.lia.dto.ComitenteDTO;
import ar.edu.unrn.lia.generic.GenericDao;
import ar.edu.unrn.lia.model.Comitente;

import javax.persistence.criteria.Predicate;
import java.util.List;

public interface ComitenteDAO extends GenericDao<Comitente, Long> {


    public List<ComitenteDTO> listwithPagDTO(Predicate[] where, Integer page, Integer pagesize, String sortField,
                                             Boolean asc);


    public List<ComitenteDTO> findAllDTO();
}
