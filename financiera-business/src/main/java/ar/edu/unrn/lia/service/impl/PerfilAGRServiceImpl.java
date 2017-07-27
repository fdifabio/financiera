package ar.edu.unrn.lia.service.impl;

import ar.edu.unrn.lia.dao.PerfilAGRDAO;
import ar.edu.unrn.lia.model.PerfilAGR;
import ar.edu.unrn.lia.service.PerfilAGRService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Map;

@Named("perfilAGRService")
public class PerfilAGRServiceImpl implements PerfilAGRService {

    @Inject
    PerfilAGRDAO entityDAO;

    public PerfilAGRDAO getEntityDAO() {
        return entityDAO;
    }

    public PerfilAGR findByName(String cuit) {
        PerfilAGR perfilAGR = null;
        try {
            perfilAGR = entityDAO.getEntityByCuit(cuit);
        } catch (NoResultException e) {

        }

        return perfilAGR;

    }

    @Transactional(readOnly = true)
    public Long getCount(Map<String, String> filters) {
        return getEntityDAO().count(
                getEntityDAO().getSearchPredicates(getEntityDAO().rootCount(),
                        filters));
    }

    @Transactional(readOnly = true)
    public List<PerfilAGR> getList(Integer page, Integer pagesize, Map<String, String> filters, String sortField,
                                   Boolean asc, boolean distinct) {
        return getEntityDAO().listwithPag(getEntityDAO().getSearchPredicates(getEntityDAO().rootCount(), filters), page,
                pagesize, sortField, asc, false);
    }

    @Transactional
    public void save(PerfilAGR entity) {
        if (entity.getId() == null) {
            entity.getUser().setPassword(encriptarClave(entity.getUser().getPassword()));
            getEntityDAO().create(entity);
        } else {
            getEntityDAO().update(entity);

        }
    }

    @Transactional
    public void delete(PerfilAGR entity) {
        getEntityDAO().delete(entity);
    }

    public PerfilAGR getEntityById(Long id) {
        return getEntityDAO().read(id);
    }

    @Transactional
    public void update(PerfilAGR entity) {
        getEntityDAO().update(entity);
    }

    public List<PerfilAGR> getAll() {
        List<PerfilAGR> all = getEntityDAO().findAll();
        return all;
    }

    @Override
    public PerfilAGR findByMail(String mail) {
        PerfilAGR perfil = null;
        try {
            perfil = entityDAO.getEntityByMail(mail);
        } catch (NoResultException e) {
            if (perfil == null) {
                throw new UsernameNotFoundException("Perfil no existe.");
            }
        }
        return perfil;

    }
    String encriptarClave(String password) {
        return encoder().encode(password);
    }
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
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


}
