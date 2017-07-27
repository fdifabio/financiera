package ar.edu.unrn.lia.bean.util;

import ar.edu.unrn.lia.config.ParamValue;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Federico on 05/07/2017.
 */
@Configuration
public class ParameterSendMail {

    @ParamValue(key = "spring.mail.subject.forgot")
    public static String subjectForgot;

    @ParamValue(key = "spring.mail.subject.new")
    public static String subjectNew;

    @ParamValue(key = "spring.mail.subject.new.pass")
    public static String subjectNewPass;

    @ParamValue(key = "spring.mail.username")
    public static String from;

    @ParamValue(key = "spring.mail.body")
    public static String body;

    @ParamValue(key = "spring.mail.msj.activar")
    public static String msjActivar;

    @ParamValue(key = "spring.mail.body.acceso")
    public static String bodyAcceso;






}

