package ar.edu.unrn.lia.service.impl;

import ar.edu.unrn.lia.dao.EProfesionalDAO;
import ar.edu.unrn.lia.dto.OrdenDeServicioDTO;
import ar.edu.unrn.lia.model.EProfesional;
import ar.edu.unrn.lia.model.OrdenDeServicio;
import ar.edu.unrn.lia.service.OrdenDeServicioReadService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Named("ordenDeServicioReadService")
public class OrdenDeServicioReadServiceImpl extends AbstractApplyFilterSecurityService implements OrdenDeServicioReadService {

	private static final long serialVersionUID = 1L;

	@Inject
	private EProfesionalDAO entityDAO;


	@Transactional(readOnly = true)
	public Long getCount(Map<String, String> filters) {
		//filters.putAll(getUserLogged().getRole().listODSFilter());

		return getEntityDAO().count(getEntityDAO().getSearchPredicates(getEntityDAO().rootCount(), filters));
	}

	@Transactional(readOnly = true)
	public List<OrdenDeServicioDTO> getList(Integer page, Integer pagesize, Map<String, String> filters, String sortField,
											Boolean asc, boolean distinct) {

		//filters.putAll(getUserLogged().getRole().listODSFilter());

		return getEntityDAO().odslistwithPagDTO(getEntityDAO().getSearchPredicates(getEntityDAO().rootCount(), filters), page, pagesize, sortField, asc);
	}

	public EProfesionalDAO getEntityDAO() {
		return entityDAO;
	}

	@Override
	@Transactional
	public void facturado(OrdenDeServicioDTO ordenDeServicioDTO) {
		EProfesional eProfesional = entityDAO.read(ordenDeServicioDTO.getId());
		eProfesional.facturar();
		getEntityDAO().update(eProfesional);
	}

}
