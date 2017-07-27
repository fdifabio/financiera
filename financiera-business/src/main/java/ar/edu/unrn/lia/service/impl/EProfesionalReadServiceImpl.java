package ar.edu.unrn.lia.service.impl;

import ar.edu.unrn.lia.dao.EProfesionalDAO;
import ar.edu.unrn.lia.dto.EProfesionalDTO;
import ar.edu.unrn.lia.model.EProfesional;
import ar.edu.unrn.lia.service.EProfesionalReadService;
import ar.edu.unrn.lia.service.EProfesionalService;
import ar.edu.unrn.lia.service.ReadOnlyService;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.criteria.Predicate;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Named("eProfesionalReadService")
public class EProfesionalReadServiceImpl extends AbstractApplyFilterSecurityService  implements EProfesionalReadService {

	private static final long serialVersionUID = 1L;

	@Inject
	private EProfesionalDAO entityDAO;

	@Override
	public List<EProfesionalDTO> getAll() {
		return null;
	}

	@Override
	public EProfesionalDTO getEntityById(Long id) {
		return null;
	}

	@Override
	public EProfesionalDTO findByName(String name) {
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public Long getCount(Map<String, String> filters) {

		filters.putAll(getUserLogged().getRole().eprofesionalFilter(getUserLogged()));

		return getEntityDAO().count(getEntityDAO().getSearchPredicates(getEntityDAO().rootCount(), filters));
	}
	@Override
	@Transactional(readOnly = true)
	public List<EProfesionalDTO> getList(Integer page, Integer pagesize, Map<String, String> filters, String sortField,
									  Boolean asc, boolean distinct) {
		filters.putAll(getUserLogged().getRole().eprofesionalFilter(getUserLogged()));

		return getEntityDAO().listwithPagDTO(getEntityDAO().getSearchPredicates(getEntityDAO().rootCount(), filters), page, pagesize, sortField, asc);
	}

	public EProfesionalDAO getEntityDAO() {
		return entityDAO;
	}


	@Override
	public void save(EProfesionalDTO eProfesionalDTO) {

	}

	@Override
	@Transactional
	public void delete(EProfesionalDTO eProfesionalDTO) {
		EProfesional entity = new EProfesional(eProfesionalDTO.getId());
		getEntityDAO().delete(entity);
	}
}
