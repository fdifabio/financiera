package ar.edu.unrn.lia.exception;

import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.edu.unrn.lia.bean.util.BundleMessagei18;

@ManagedBean
@RequestScoped
public class CustomExceptionHandler extends ExceptionHandlerWrapper {
	private ExceptionHandler wrapped;

	private static Logger LOG = LoggerFactory.getLogger(CustomExceptionHandler.class);

	@Inject
	BundleMessagei18 bundleMessagei18;

	public CustomExceptionHandler(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return wrapped;
	}

	@SuppressWarnings("static-access")
	@Override
	public void handle() throws FacesException {
		Iterator iterator = getUnhandledExceptionQueuedEvents().iterator();

		while (iterator.hasNext()) {
			ExceptionQueuedEvent event = (ExceptionQueuedEvent) iterator.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

			Throwable throwable = context.getException();

			FacesContext fc = FacesContext.getCurrentInstance();

			try {
				
//				String error = throwable.getMessage() + "(" + throwable.getClass().getName() + ")";
//
//				if (throwable.getClass() == javax.faces.application.ViewExpiredException.class)
//					error = bundleMessagei18.getString("sesion.expirada");
//				
//				
//				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL,
//						bundleMessagei18.getString("error") + ":(", error);
//				RequestContext.getCurrentInstance().showMessageInDialog(message);
				
			
				addMessageNotifcationTweets(throwable, fc);
				
			} finally {
				iterator.remove();
			}
		}

		// Let the parent handle the rest
		getWrapped().handle();
	}

	private void addMessageNotifcationTweets(Throwable throwable, FacesContext fc) {
		Flash flash = fc.getExternalContext().getFlash();
		// Put the exception in the flash scope to be displayed in the error
		// page if necessary ...
//		flash.put("NOtificacion", throwable.getMessage());
//		LOG.info("the error is put in the flash:" + throwable.getMessage());
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, throwable.getMessage(),
				throwable.getMessage());
		RequestContext.getCurrentInstance().showMessageInDialog(message);

		fc.renderResponse();
	}

	private void addMessageFlash(Throwable throwable, FacesContext fc) {
		Flash flash = fc.getExternalContext().getFlash();
		// Put the exception in the flash scope to be displayed in the error
		// page if necessary ...
		flash.put("errorDetails", throwable.getMessage());
		LOG.info("the error is put in the flash:" + throwable.getMessage());
		NavigationHandler navigationHandler = fc.getApplication().getNavigationHandler();
		navigationHandler.handleNavigation(fc, null, "/error/error?faces-redirect=true");

		fc.renderResponse();
	}

	public void handle2() throws FacesException {
		final Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();
		while (i.hasNext()) {
			ExceptionQueuedEvent event = i.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

			// get the exception from context
			Throwable t = context.getException();

			final FacesContext fc = FacesContext.getCurrentInstance();
			final Map<String, Object> requestMap = fc.getExternalContext().getRequestMap();
			final NavigationHandler nav = fc.getApplication().getNavigationHandler();

			// here you do what ever you want with exception
			try {

				// log error ?
				// log.log(Level.SEVERE, "Critical Exception!", t);
				if (t instanceof ViewExpiredException) {
					requestMap.put("javax.servlet.error.message", "Session expired, try again!");
					String errorPageLocation = "/erro.xhtml";
					fc.setViewRoot(fc.getApplication().getViewHandler().createView(fc, errorPageLocation));
					fc.getPartialViewContext().setRenderAll(true);
					fc.renderResponse();
				} else {
					// redirect error page
					requestMap.put("javax.servlet.error.message", t.getMessage());
					nav.handleNavigation(fc, null, "/erro.xhtml");
				}

				fc.renderResponse();
				// remove the comment below if you want to report the error in a
				// jsf error message
				// JsfUtil.addErrorMessage(t.getMessage());
			} finally {
				// remove it from queue
				i.remove();
			}
		}
		// parent hanle
		getWrapped().handle();
	}
}