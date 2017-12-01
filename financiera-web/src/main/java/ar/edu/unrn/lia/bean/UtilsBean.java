package ar.edu.unrn.lia.bean;

import org.springframework.context.annotation.Scope;

import javax.inject.Named;

@Named("utilsBean")
@Scope
public class UtilsBean {

	public final static String REDIRECT_CREATE = "create?faces-redirect=true";
	public final static String REDIRECT_SEARCH = "search?faces-redirect=true";
	public final static String MESSAGE_OK = "MESSAGE_OK";
	public final static String REDIRECT_MIPERFIL = "/pages/perfil/miperfil?faces-redirect=true";
	public final static String REDIRECT_HOME = "/pages/home?faces-redirect=true";
	public final static String REDIRECT_DASHBOARD = "/dashboard?faces-redirect=true";
	public final static String REDIRECT_PREFERENCIAS = "/pages/preferencias/search?faces-redirect=true";
	public final static String REDIRECT_LOGIN = "/login?faces-redirect=true";

	public final static String REDIRECT_PERFIL = "/pages/perfil/perfil?faces-redirect=true";
	public final static String REDIRECT_SEARCH_CREDITO = "/pages/credito/search?faces-redirect=true";
	

}
