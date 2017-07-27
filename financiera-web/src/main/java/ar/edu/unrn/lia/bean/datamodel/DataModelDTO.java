package ar.edu.unrn.lia.bean.datamodel;

import ar.edu.unrn.lia.model.GenericEntity;
import ar.edu.unrn.lia.model.User;
import ar.edu.unrn.lia.service.ReadListwithPagService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataModelDTO<T> extends LazyDataModel<T> {

    private static final long serialVersionUID = 1L;

    private String orderDefault = "id";
    private String sorterDefault = SortOrder.ASCENDING.toString();

    private List<T> list;

    ReadListwithPagService service;
    Map<String, String> filtersByBean;


    public DataModelDTO() {

    }

    public DataModelDTO(ReadListwithPagService service) {

        this.service = service;
    }

    public DataModelDTO(ReadListwithPagService service, String order) {

        this.service = service;
        this.orderDefault = order;

    }

    public DataModelDTO(ReadListwithPagService service, String order, String sorter) {

        this.service = service;
        this.orderDefault = order;
        this.sorterDefault = sorter;
    }

    public DataModelDTO(ReadListwithPagService service, String order, String sorter, Map<String, String> filtersByBean) {

        this.service = service;
        this.orderDefault = order;
        this.sorterDefault = sorter;
        this.filtersByBean = filtersByBean;
    }


    @Override
    public List<T> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder,
                        Map<String, Object> filters) {
        try {
            // Filtros Avanzados
            filters.putAll(filtersByBean != null ? filtersByBean : new HashMap<>());

            list = getService().getList(startingAt, maxPerPage, filters,
                    (sortField == null ? orderDefault : sortField),
                    (sortOrder.name().equals(sorterDefault)), true);


        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<T>(0);
        }

        // set the total of list
        if (getRowCount() <= 0) {
            setRowCount(getService().getCount(filters).intValue());
        }

        // set the page dize
        setPageSize(maxPerPage);

        return list;
    }


    public ReadListwithPagService getService() {
        return service;
    }


    @Override
    public Object getRowKey(T entity) {
        return ((GenericEntity) entity).getId();
    }

    @Override
    public T getRowData(String entityId) {
        Integer id = Integer.valueOf(entityId);

        for (T entity : list) {
            if (id.equals(((GenericEntity) entity).getId())) {
                return entity;
            }
        }

        return null;
    }


}