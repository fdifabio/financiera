package ar.edu.unrn.lia.config;

import ar.edu.unrn.lia.service.ParameterService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import javax.inject.Named;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

@Named
public class ParameterInjector implements BeanPostProcessor {

    @Autowired
    ParameterService parameterService;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(final Object bean, String name) throws BeansException {
        ReflectionUtils.doWithFields(bean.getClass(), new ReflectionUtils.FieldCallback() {
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                ReflectionUtils.makeAccessible(field);
                if (field.getAnnotation(ParamValue.class) != null) {
                    ReflectionUtils.makeAccessible(field);
                    Type fieldGenericType = field.getGenericType();
                    String key = field.getDeclaredAnnotation(ParamValue.class).key();

                    String valor = parameterService.getEntityByKey(key).getValue();

                    Class<?> targetType = field.getType(); // clase del campo
                    try {
//						String instancia = (String) targetType.getConstructor(String.class).newInstance(valor);
//						Class.forName(targetType.getName()).getConstructor(String.class).newInstance(valor);

                        field.set(bean,
                                Class.forName(targetType.getName()).getConstructor(String.class).newInstance(valor));
                    } catch (InstantiationException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (SecurityException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
            }
        });
        return bean;
    }

}
