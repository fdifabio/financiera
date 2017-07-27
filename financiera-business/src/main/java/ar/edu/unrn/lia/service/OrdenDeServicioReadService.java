package ar.edu.unrn.lia.service;

import ar.edu.unrn.lia.dto.OrdenDeServicioDTO;

public interface OrdenDeServicioReadService extends ReadListwithPagService<OrdenDeServicioDTO> {

    void facturado(OrdenDeServicioDTO ordenDeServicioDTO);
}
