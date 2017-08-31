package ar.edu.unrn.lia.service.impl;

import ar.edu.unrn.lia.dao.CiudadDAO;
import ar.edu.unrn.lia.model.Ciudad;
import ar.edu.unrn.lia.service.CiudadService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Map;

@Named("ciudadService")
public class CiudadServiceImpl implements CiudadService {

	@Inject
	CiudadDAO entityDAO;

	public CiudadDAO getEntityDAO() {
		return entityDAO;
	}

	public Ciudad findByName(String nombre) {
		Ciudad ciudad = null;
		try {
			ciudad = entityDAO.getEntityByName(nombre);

		} catch (NoResultException e) {

		}

		return ciudad;

	}

	@Transactional(readOnly = true)
	@Override
	public Long getCount(Map<String, String> filters) {
		return getEntityDAO().count(
				getEntityDAO().getSearchPredicates(getEntityDAO().rootCount(),
						filters));
	}

	@Transactional(readOnly = true)
	@Override
	public List<Ciudad> getList(Integer page, Integer pagesize, Map<String, String> filters, String sortField,
								Boolean asc, boolean distinct) {
		return getEntityDAO().listwithPag(getEntityDAO().getSearchPredicates(getEntityDAO().rootCount(), filters), page,
				pagesize, sortField, asc, false);
	}

	@Transactional
	public void save(Ciudad entity) {
		if (entity.getId() == null) {

			getEntityDAO().create(entity);

		} else {
			getEntityDAO().update(entity);

		}
	}

	@Transactional
	public void delete(Ciudad entity) {
		getEntityDAO().delete(entity);
	}

	public Ciudad getEntityById(Long id) {
		return getEntityDAO().read(id);
	}

	public List<Ciudad> getAll() {
		return getEntityDAO().findAll();
	}

	@Override
	public List<Ciudad> findByQuery(String query, String propertyFilter,
										  String orderDirection) {
		return getEntityDAO()
				.findByQuery(query, propertyFilter, orderDirection);
	}


	@Override
	public List<Ciudad> getList(Long id) {
		try {
			if ((id != null)) {
				return entityDAO.getList(id);
			}
		} catch (UsernameNotFoundException e) {

			return null;
		}
		return null;


	}

}
