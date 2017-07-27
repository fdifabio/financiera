package ar.edu.unrn.lia.seguridad;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named("authenticationService")
public class AuthenticationServiceImpl implements AuthenticationService, Serializable {

    private static final long serialVersionUID = 5371998321050759039L;

    @Inject
    private AuthenticationManager authenticationManager;


    @Override
    public boolean login(String username, String password)
            throws AuthenticationException, UsernameNotFoundException {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username,
                        password));
        if (authenticate.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            return true;
        }
        return false;
    }

    @Override
    public void logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        SecurityContextHolder.clearContext();
    }

}
