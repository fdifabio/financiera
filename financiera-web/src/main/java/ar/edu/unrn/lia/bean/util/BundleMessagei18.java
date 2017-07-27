package ar.edu.unrn.lia.bean.util;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.slf4j.Logger;
import org.springframework.context.annotation.Scope;

import ar.edu.unrn.lia.logger.Log;

@Named
@Scope("prototype")
public class BundleMessagei18 {

	private static @Log Logger LOG;

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("messages",
			FacesContext.getCurrentInstance().getViewRoot().getLocale());

	private BundleMessagei18() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			LOG.error("No se pudo recuperar la clave");
		}
		return "";
	}

	public static String getString(String key, Object... params) {
		try {
			return MessageFormat.format(RESOURCE_BUNDLE.getString(key), params);
		} catch (MissingResourceException e) {
			LOG.error("No se pudo recuperar la clave");
		}
		return "";
	}
}
