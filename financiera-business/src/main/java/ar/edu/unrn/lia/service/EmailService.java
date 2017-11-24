package ar.edu.unrn.lia.service;

import io.reactivex.Observable;
import org.springframework.mail.MailException;

import javax.mail.MessagingException;

public interface EmailService {
    void sendMail(final String from, final String to, String subject, final String body)
            throws MessagingException, MailException, InterruptedException;

    Observable<String> sendMailAsync(final String from, final String to, final String subject, final String body);
}
