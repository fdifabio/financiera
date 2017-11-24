package ar.edu.unrn.lia.dao;

import ar.edu.unrn.lia.dto.CuotaDTO;
import ar.edu.unrn.lia.generic.GenericDao;
import ar.edu.unrn.lia.model.Cuota;

import java.util.Date;
import java.util.List;

public interface CuotaDAO extends GenericDao<Cuota, Long> {
    public List<CuotaDTO> listAdeudadas(int year, int month);

    public List<Integer> listAniosAdeudadas();

    public List<CuotaDTO> listSaldadas(int year);

    public List<Integer> listAnios();

    public List<CuotaDTO> countAdeudadas(int year, int month);
}
