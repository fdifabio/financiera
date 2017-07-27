package ar.edu.unrn.lia.service.impl;

import ar.edu.unrn.lia.service.EmailService;
import io.reactivex.Observable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private final JavaMailSender javaMailSender;


    @Inject
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    public void sendMail(String from, String to, String subject, String body)
            throws MessagingException, MailException, InterruptedException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
        helper.setText(body, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setFrom(from);
        this.javaMailSender.send(mimeMessage);

    }

    public Observable<String> sendMailAsync(String from, String to, String subject, String body){
        return Observable.create(s -> {
            try {
                this.sendMail(from, to, body, subject);
                s.onNext("Enviado");
            } catch (Exception e) {
                s.onError(e.getCause());
            } finally {
                s.onComplete();
            }
        });
    }


}
