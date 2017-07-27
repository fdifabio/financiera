package ar.edu.unrn.lia.bean;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;

@Named("utilsBean")
@Scope
public class UtilsBean {

	public final static String REDIRECT_CREATE = "create?faces-redirect=true";
	public final static String REDIRECT_SEARCH = "search?faces-redirect=true";
	public final static String MESSAGE_OK = "MESSAGE_OK";
	public final static String REDIRECT_MIPERFIL = "/pages/perfil/miperfil?faces-redirect=true";
	public final static String REDIRECT_HOME = "/pages/home?faces-redirect=true";
	public final static String REDIRECT_DASHBOARD = "/dashboard?faces-redirect=true";
	public final static String REDIRECT_LOGIN = "/login?faces-redirect=true";

	public final static String REDIRECT_PERFIL = "/pages/perfil/perfil?faces-redirect=true";
	

}
