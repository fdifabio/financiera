package ar.edu.unrn.lia.dao;

import ar.edu.unrn.lia.generic.GenericDao;
import ar.edu.unrn.lia.model.PerfilAGR;

public interface PerfilAGRDAO extends GenericDao<PerfilAGR, Long> {

    PerfilAGR getEntityByCuit(String cuit);

    PerfilAGR getEntityByMail(String mail);

}
