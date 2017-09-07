package ar.edu.unrn.lia.service;

import ar.edu.unrn.lia.model.Credito;
import ar.edu.unrn.lia.model.Interes;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Lucas on 22/08/2017.
 */
public interface InteresService extends IGenericService<Interes> {

    void updateOrden(List<Interes> list);
}
