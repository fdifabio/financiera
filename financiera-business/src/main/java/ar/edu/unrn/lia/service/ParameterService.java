package ar.edu.unrn.lia.service;

import ar.edu.unrn.lia.model.Parameter;

public interface ParameterService extends IGenericService<Parameter> {
	Parameter getEntityByKey(String key);
}