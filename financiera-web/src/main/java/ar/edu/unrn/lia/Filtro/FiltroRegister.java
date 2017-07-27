package ar.edu.unrn.lia.Filtro;

import ar.edu.unrn.lia.service.EncriptaService;
import ar.edu.unrn.lia.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Federico on 04/06/2017.
 */
public class FiltroRegister implements Filter {

    private EncriptaService encriptaService;
    private String mail;
    private UserService uService;


    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // Do nothing

    }


    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());

        setuService((UserService) ctx.getBean("userService"));

        setEncriptaService((EncriptaService) ctx.getBean("encriptaService"));

        String mail = request.getParameter("mail");
        setMail(encriptaService.decrypt(mail));
        //setMail(request.getParameter("mail"));

        if (getMail() != null) {
            uService.confirm(this.mail);
        }
        chain.doFilter(req, res);

    }

    @Override
    public void destroy() {
        // Do nothing
    }


    public EncriptaService getEncriptaService() {
        return encriptaService;
    }


    public void setEncriptaService(EncriptaService encriptaService) {
        this.encriptaService = encriptaService;
    }


    public String getMail() {
        return mail;
    }


    public void setMail(String mail) {
        this.mail = mail;
    }


    public UserService getuService() {
        return uService;
    }


    public void setuService(UserService uService) {
        this.uService = uService;
    }


}