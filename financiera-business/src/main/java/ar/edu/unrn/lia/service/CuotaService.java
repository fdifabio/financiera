package ar.edu.unrn.lia.service;

import ar.edu.unrn.lia.dto.CuotaDTO;
import ar.edu.unrn.lia.model.Cliente;
import ar.edu.unrn.lia.model.Cuota;

import java.util.Date;
import java.util.List;

/**
 * Created by difabioguillermo on 11/8/17.
 */
public interface CuotaService extends IGenericService<Cuota> {

    public List<CuotaDTO> listAdeudadas(int year, int month);

    public List<Integer> listAniosAdeudadas();

    public List<Integer> listAnios();

    public List<CuotaDTO> listSaldadas(int year);

    public List<CuotaDTO> countAdeudadas(int year, int month);
}
