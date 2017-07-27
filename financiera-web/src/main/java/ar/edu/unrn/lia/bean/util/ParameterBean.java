package ar.edu.unrn.lia.bean.util;

import ar.edu.unrn.lia.config.ParamValue;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Federico on 04/07/2017.
 */
@Configuration
public class ParameterBean {

    @ParamValue(key = "leyenda.profesional")
    public static String leyenda;

    @ParamValue(key = "pattern.date")
    public static String patternDate;

    @ParamValue(key = "pattern.moneda")
    public static String patternMoneda;

    @ParamValue(key = "pattern.dateTime")
    public static String patternDateTime;

    @ParamValue(key = "montoGEO")
    public static Double montoGEO;





}
