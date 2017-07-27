package ar.edu.unrn.lia.service.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import org.springframework.transaction.annotation.Transactional;

import ar.edu.unrn.lia.dao.ParameterDAO;
import ar.edu.unrn.lia.exception.BusinessException;
import ar.edu.unrn.lia.model.Parameter;
import ar.edu.unrn.lia.model.User;
import ar.edu.unrn.lia.service.ParameterService;

@Named("parameterService")
public class ParameterServiceImpl implements ParameterService {

	@Inject
	ParameterDAO entityDAO;

	public ParameterDAO getEntityDAO() {
		return entityDAO;
	}

	@Transactional(readOnly = true)
	public Long getCount(Map<String, String> filters) {
		return getEntityDAO().count(getEntityDAO().getSearchPredicates(getEntityDAO().rootCount(), filters));
	}


	@Transactional(readOnly = true)
	public List<Parameter> getList(Integer page, Integer pagesize, Map<String, String> filters, String sortField,
								   Boolean asc, boolean distinct) {
		return getEntityDAO().listwithPag(getEntityDAO().getSearchPredicates(getEntityDAO().rootCount(), filters), page,
				pagesize, sortField, asc, false);
	}

	@Transactional
	public void save(Parameter entity) {
		if (entity.getId() == null) {
			getEntityDAO().create(entity);
		} else {
			getEntityDAO().update(entity);
		}
	}

	@Transactional
	public void delete(Parameter entity) {
		getEntityDAO().delete(entity);
	}

	public Parameter getEntityById(Long id) {
		return getEntityDAO().read(id);
	}

	public List<Parameter> getAll() {
		return getEntityDAO().findAll();
	}

	@Override
	public Parameter findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Parameter getEntityByKey(String key) {
		// TODO Auto-generated method stub
		Parameter parameter = null;
		try {
			parameter = entityDAO.getEntityByKey(key);

		} catch (NoResultException e) {

			if (parameter == null) {
				throw new BusinessException(e.getMessage(), e, "parametroNoExiste");
			}
		}

		return parameter;
	}

}