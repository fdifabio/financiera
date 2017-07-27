package ar.edu.unrn.lia.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ar.edu.unrn.lia.model.User;

public interface UserService extends IGenericService<User> {

	public User findByMail(String mail);
	
	public boolean validarUnicidadMail(String value);

	public boolean validarUnicidadUserName(String value);
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
	
	public void cambiarClave(User user);
	
	public BCryptPasswordEncoder encoder();

    public User confirm(String email)throws UsernameNotFoundException;

	void saveAndNotified(User user);
}