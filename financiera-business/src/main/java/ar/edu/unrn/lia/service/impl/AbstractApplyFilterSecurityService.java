package ar.edu.unrn.lia.service.impl;

import ar.edu.unrn.lia.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class AbstractApplyFilterSecurityService {


    protected User getUserLogged() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }



}
