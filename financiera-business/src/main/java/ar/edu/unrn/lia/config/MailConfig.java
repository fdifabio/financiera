package ar.edu.unrn.lia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Created by Federico on 26/05/2017.
 */
@Configuration
public class MailConfig {

    @ParamValue(key = "spring.mail.host")
    private String host;

    @ParamValue(key = "spring.mail.username")
    private String username;

    @ParamValue(key = "spring.mail.password")
    private String password;

    @ParamValue(key = "spring.mail.port")
    private Integer port;

    @ParamValue(key = "spring.mail.smtp.ssl.enable")
    private String enable;

    @ParamValue(key = "mail.smtp.auth")
    private String auth;

    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        javaMailSender.setPort(port);
        javaMailSender.setJavaMailProperties(getMailProperties());
        return javaMailSender;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.ssl.enable", enable);
        return properties;
    }
}
