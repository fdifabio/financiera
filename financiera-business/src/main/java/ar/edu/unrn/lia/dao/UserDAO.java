package ar.edu.unrn.lia.dao;

import ar.edu.unrn.lia.generic.GenericDao;
import ar.edu.unrn.lia.model.Role;
import ar.edu.unrn.lia.model.User;

public interface UserDAO extends GenericDao<User, Long> {

	User getEntityByName(String username);

	User getEntityByMail(String mail);
	
}
