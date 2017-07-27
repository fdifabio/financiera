package ar.edu.unrn.lia.seguridad;

import javax.inject.Inject;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import ar.edu.unrn.lia.model.User;
import ar.edu.unrn.lia.service.UserService;

public class IncorrectPasswordEventListener implements ApplicationListener {

//	@Inject
//	UserService userService;
	
	public void onApplicationEvent(ApplicationEvent event) {
//		if (event instanceof AuthenticationFailureBadCredentialsEvent) {
//			AuthenticationFailureBadCredentialsEvent badCredentialsEvent = (AuthenticationFailureBadCredentialsEvent) event;
//			String username = badCredentialsEvent.getAuthentication().getName();
//			
//			User user = (User) getUserService().loadUserByUsername(username);
//			 
//			user.increaseFailedLoginAttempts();
//			getUserService().save(user);
//			
//		}
//		if (event instanceof AuthenticationSuccessEvent) {
//			AuthenticationSuccessEvent successEvent = (AuthenticationSuccessEvent) event;
//
//			String username = successEvent.getAuthentication().getName();
//			
//			User user = (User) getUserService().loadUserByUsername(username);
//			
//			user.resetFailedLoginAttempts();
//			getUserService().save(user);
//		}
	}
	
//	public UserService getUserService() {
//		return userService;
//	}
//
//	public void setUserService(UserService userService) {
//		this.userService = userService;
//	}

}
