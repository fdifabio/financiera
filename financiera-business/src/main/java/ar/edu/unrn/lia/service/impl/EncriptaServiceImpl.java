package ar.edu.unrn.lia.service.impl;

import ar.edu.unrn.lia.service.EncriptaService;
import ar.edu.unrn.lia.util.Constantes;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import javax.inject.Named;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Named("encriptaService")
public class EncriptaServiceImpl implements EncriptaService {

    public static final String CLAVE_ENCRYPT = Constantes.GLOBAL_CLAVE;

    public static String encryptsss(String cadena) {
        StandardPBEStringEncryptor s = new StandardPBEStringEncryptor();
        s.setPassword(CLAVE_ENCRYPT);
        return s.encrypt(cadena);
    }

    @Override
    public String encrypt(String cadena) {
        return encryptsss(cadena);
    }

    public static String decryptsss(String cadena) {
        StandardPBEStringEncryptor s = new StandardPBEStringEncryptor();
        s.setPassword(CLAVE_ENCRYPT);
        String devuelve = "";
        try {
            devuelve = s.decrypt(cadena);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return devuelve;
    }

    @Override
    public String decrypt(String cadena) {
        return decryptsss(cadena);
    }


    @Override
    public String encryptURL(String cadena) {
        String encrypt = encrypt(cadena);
        String encode = "";
        try {
            encode = URLEncoder.encode(encrypt, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encode;
    }

}