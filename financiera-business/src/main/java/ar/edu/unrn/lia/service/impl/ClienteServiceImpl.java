package ar.edu.unrn.lia.service.impl;

import ar.edu.unrn.lia.dao.ClienteDAO;
import ar.edu.unrn.lia.dao.CreditoDAO;
import ar.edu.unrn.lia.dao.CuotaDAO;
import ar.edu.unrn.lia.model.Cliente;
import ar.edu.unrn.lia.model.Credito;
import ar.edu.unrn.lia.model.Cuota;
import ar.edu.unrn.lia.service.ClienteService;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by difabioguillermo on 11/8/17.
 */
@Named("clienteService")
public class ClienteServiceImpl implements ClienteService {
    @Inject
    ClienteDAO entityDAO;
    @Inject
    CreditoDAO creditoDAO;

    public ClienteDAO getEntityDAO() {
        return entityDAO;
    }

    public Cliente findByName(String nombre) {
        return null;
        //TODO:

    }

    @Transactional(readOnly = true)
    public Long getCount(Map<String, String> filters) {
        return getEntityDAO().count(
                getEntityDAO().getSearchPredicates(getEntityDAO().rootCount(),
                        filters));
    }

    @Transactional(readOnly = true)
    public List<Cliente> getList(Integer page, Integer pagesize, Map<String, String> filters, String sortField,
                                 Boolean asc, boolean distinct) {
        return getEntityDAO().listwithPag(getEntityDAO().getSearchPredicates(getEntityDAO().rootCount(), filters), page,
                pagesize, sortField, asc, false);
    }

    @Transactional
    public void save(Cliente entity) {
        if (entity.getId() == null) {

            getEntityDAO().create(entity);

        } else {
            getEntityDAO().update(entity);

        }
    }

    @Transactional
    public void delete(Cliente entity) {
        getEntityDAO().delete(entity);
    }

    public Cliente getEntityById(Long id) {
        return getEntityDAO().read(id);
    }

    public List<Cliente> getAll() {
        return getEntityDAO().findAll();
    }

    @Override
    public List<Cliente> searchByApellidoNombre(String apellidoNombre) {
        return getEntityDAO().searchByApellidoNombre(apellidoNombre);
    }

    @Override
    public List<Cliente> searchMorosos() {
        List<Cliente> clientes = new ArrayList<>();
        clientes = getEntityDAO().searchMorosos();
        clientes.stream().forEach(cliente -> {
            cliente.setCreditoAdeudado(creditoDAO.read(cliente.getCreditoAdeudado().getId()));
            cliente.setGarante(creditoDAO.read(cliente.getCreditoAdeudado().getId()).getGarante());
        });
        return clientes;
    }

    @Override
    public List<Cliente> searchVencimientosDelDia() {
        return getEntityDAO().searchVencimientosDelDia();
    }
}
