package ar.edu.unrn.lia.service;

import ar.edu.unrn.lia.model.PerfilAGR;

public interface PerfilAGRService extends IGenericService<PerfilAGR> {

    public boolean validarUnicidadMail(String value);

    public PerfilAGR findByMail(String mail);
}
