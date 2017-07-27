package ar.edu.unrn.lia.bean.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ar.edu.unrn.lia.model.User;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import ar.edu.unrn.lia.model.BaseEntity;
import ar.edu.unrn.lia.service.IGenericService;
import org.springframework.security.core.context.SecurityContextHolder;

public class DataModel<T> extends LazyDataModel<T> {

	private static final long serialVersionUID = 1L;

	private static final String ORDER_DEFAULT = "id";

	private List<T> list;

	IGenericService entityService;

	protected Class<T> entityClass;

	public DataModel() {

	}

	public DataModel(IGenericService entityService) {

		this.entityService = entityService;
	}

	public IGenericService getEntityService() {
		return entityService;
	}

	private User getUserLogged() {
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	@Override
	public List<T> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		try {
			try {
				// Filtros Avanzados
				filters.putAll(getUserLogged().getRole().eprofesionalFilter(getUserLogged()));

				list = getEntityService().getList(startingAt, maxPerPage, filters,
						(sortField == null ? ORDER_DEFAULT : sortField),
						(sortOrder.name().equals(SortOrder.ASCENDING.toString())), true);
			} finally {

			}
		} catch (Exception e) {
			e.printStackTrace();
			list = new ArrayList<T>(0);
		}

		// set the total of list
		if (getRowCount() <= 0) {
			setRowCount(getEntityService().getCount(filters).intValue());
		}

		// set the page dize
		setPageSize(maxPerPage);

		return list;
	}

	@Override
	public Object getRowKey(T entity) {
		return ((BaseEntity) entity).getId();
	}

	@Override
	public T getRowData(String entityId) {
		Integer id = Integer.valueOf(entityId);

		for (T entity : list) {
			if (id.equals(((BaseEntity) entity).getId())) {
				return entity;
			}
		}

		return null;
	}

}