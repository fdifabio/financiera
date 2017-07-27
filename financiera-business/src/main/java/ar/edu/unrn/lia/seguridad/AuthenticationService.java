package ar.edu.unrn.lia.seguridad;


public interface AuthenticationService {
	
	boolean login(String username, String password);

	void logout();
}
