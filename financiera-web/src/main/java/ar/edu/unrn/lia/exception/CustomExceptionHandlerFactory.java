package ar.edu.unrn.lia.exception;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

@ManagedBean
@RequestScoped
public class CustomExceptionHandlerFactory extends javax.faces.context.ExceptionHandlerFactory {

    private javax.faces.context.ExceptionHandlerFactory parent;

    public CustomExceptionHandlerFactory(ExceptionHandlerFactory parent) {
        this.parent = parent;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        ExceptionHandler result = parent.getExceptionHandler();
        result = new CustomExceptionHandler(result);
        return result;
    }
}