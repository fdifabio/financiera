package ar.edu.unrn.lia.bean.util;

import ar.edu.unrn.lia.model.GenericEntity;
import org.apache.log4j.Logger;
import org.springframework.util.ReflectionUtils;

import javax.faces.model.SelectItem;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class UIUtils<T extends GenericEntity> {

    protected T entity;

    Collection<T> items;
    String labelProperty;

    public UIUtils(Collection<T> items, String labelProperty) {
        this.items = items;
        this.labelProperty = labelProperty;
    }

    public List<SelectItem> createSelectItemCollectionTodos() {
        List<SelectItem> collection = createSelectItemCollection();

        if (!collection.isEmpty()) {

            List<SelectItem> selectItems = new ArrayList<SelectItem>();

            SelectItem selectItem = new SelectItem();
            selectItem.setLabel("<TODOS>");
            selectItem.setValue("");
            selectItems.add(selectItem);
            selectItems.addAll(collection);

            return selectItems;
        }

        return collection;

    }


    public List<SelectItem> createSelectItemCollection() {
        List<SelectItem> selectItems = new ArrayList<SelectItem>();

        if (items == null) {
            return selectItems;
        }

        for (T item : items) {
            SelectItem selectItem = new SelectItem();
            try {

                selectItem.setValue(item);

                Class<? extends GenericEntity> beanClass = item.getClass();
                Field valueField = ReflectionUtils.findField(beanClass, labelProperty);
                valueField.setAccessible(true);
                Object value = valueField.get(item);

                selectItem.setLabel((String) value);

            } catch (Exception ex) {
                Logger.getLogger(UIUtils.class).error("No se puede crear el selectItem", ex);
            }
            selectItems.add(selectItem);
        }

        return selectItems;
    }

    public T getEntity(Long id) {


        return entity;
    }


}
