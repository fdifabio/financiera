package ar.edu.unrn.lia.service.impl;

import ar.edu.unrn.lia.dao.UserDAO;
import ar.edu.unrn.lia.exception.BusinessException;
import ar.edu.unrn.lia.model.User;
import ar.edu.unrn.lia.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Map;

@Named("userService")
public class UserServiceImpl implements UserDetailsService, UserService {

    @Inject
    UserDAO entityDAO;

    public UserDAO getEntityDAO() {
        return entityDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        UserDetails userReturn = findByName(username);
        return userReturn;
    }

    public User findByName(String username) {
        User usuario = null;
        try {
            usuario = entityDAO.getEntityByName(username);
        } catch (NoResultException e) {
            if (usuario == null) {
                throw new BusinessException(e.getMessage(), e, "usuarioNoExiste");
            }
        }
        return usuario;
    }

    public User findByMail(String mail) {
        User usuario = null;
        try {
            usuario = entityDAO.getEntityByMail(mail);
        } catch (NoResultException e) {
            if (usuario == null) {
                throw new BusinessException(e.getMessage(), e, "usuarioNoExiste");
            }
        }

        return usuario;
    }

    @Transactional(readOnly = true)
    public Long getCount(Map<String, String> filters) {
        return getEntityDAO().count(
                getEntityDAO().getSearchPredicates(getEntityDAO().rootCount(),
                        filters));
    }

    @Transactional(readOnly = true)
    public List<User> getList(Integer page, Integer pagesize, Map<String, String> filters, String sortField,
                              Boolean asc, boolean distinct) {
        return getEntityDAO().listwithPag(getEntityDAO().getSearchPredicates(getEntityDAO().rootCount(), filters), page,
                pagesize, sortField, asc, false);
    }

    @Transactional
    public void save(User entity) {
        if (entity.getId() == null) {
            entity.setPassword(encriptarClave(entity.getPassword()));
            getEntityDAO().create(entity);
        } else {
            getEntityDAO().update(entity);
        }
    }

    @Transactional
    public void delete(User entity) {
        getEntityDAO().delete(entity);
    }

    public User getEntityById(Long id) {
        return getEntityDAO().read(id);
    }

    public List<User> getAll() {
        return getEntityDAO().findAll();
    }

    //TODO: REVISAR ESTE METODO, DEBERIA TIRAR UNA EXCEPCION!!! RUNTIME
    public boolean validarUnicidadMail(String mail) {
        try {
            if (findByMail(mail) == null) {
                return true;
            } else {
                return false;
            }
        } catch (UsernameNotFoundException e) {
            return true;
        }
    }

    public boolean validarUnicidadUserName(String username) {
        try {
            if (findByName(username) == null) {
                return true;
            } else {
                return false;
            }
        } catch (UsernameNotFoundException e) {
            return true;
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void cambiarClave(User user) {
        user.setPassword(encriptarClave(user.getPassword()));
        getEntityDAO().cambiarClave(user);
    }

    String encriptarClave(String password) {
        return encoder().encode(password);
    }

    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public User confirm(String email)throws UsernameNotFoundException {
        User u = null;
        try {
            u = findByMail(email);
            u.setActive(true);
            entityDAO.update(u);
            return u;

        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("no se encuentra el mail");
        }

    }

    @Override
    public void saveAndNotified(User user) {
        this.save(user);
        // enviar el mail.

    }
}