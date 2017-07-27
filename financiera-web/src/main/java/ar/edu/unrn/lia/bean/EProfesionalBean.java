package ar.edu.unrn.lia.bean;

import ar.edu.unrn.lia.bean.convert.GenericConvert;
import ar.edu.unrn.lia.bean.datamodel.DataModel;
import ar.edu.unrn.lia.bean.util.DatosPDF;
import ar.edu.unrn.lia.bean.util.LocalDateAttributeConverter;
import ar.edu.unrn.lia.bean.util.ParameterBean;
import ar.edu.unrn.lia.model.*;
import ar.edu.unrn.lia.security.AuthenticationBean;
import ar.edu.unrn.lia.service.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.primefaces.context.RequestContext;
import org.primefaces.event.map.MarkerDragEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

@ManagedBean(name = "eprofesionalBean")
@ViewScoped
public class EProfesionalBean extends GenericBean<EProfesional> implements Serializable {

    private DefaultStreamedContent download;

    private boolean formAltaComitente = false;

    private String tipo;

    @ManagedProperty(value = "#{authenticationBean}")
    private AuthenticationBean authenticationBean;

    @ManagedProperty(value = "#{eProfesionalService}")
    private EProfesionalService entityService;

    @ManagedProperty(value = "#{comitenteService}")
    private ComitenteService comitenteService;

    private Comitente comitenteSelecionada;

    GenericConvert<Comitente> comitenteConvert = new GenericConvert<Comitente>();

    @ManagedProperty(value = "#{departamentoService}")
    private DepartamentoService departamentoService;

    @ManagedProperty(value = "#{ciudadService}")
    private CiudadService ciudadService;

    private Tasa tasaSeleccionada;

    GenericConvert<Tasa> tasaConvert = new GenericConvert<Tasa>();

    @ManagedProperty(value = "#{tasaService}")
    private TasaService tasaService;

    private Ciudad ciudadSeleccionada;

    GenericConvert<Ciudad> ciudadConvert = new GenericConvert<Ciudad>();

    private Ciudad ciudadComitenteSelecionada;

    GenericConvert<Ciudad> ciudadComitenteConvert = new GenericConvert<Ciudad>();


    private Departamento departamentoSelecionado;

    GenericConvert<Departamento> departamentoConvert = new GenericConvert<Departamento>();

    private String parametro;

    private boolean visiblebtn;

    private PerfilAGR perfilAGR = new PerfilAGR();

    private Comitente comitente = new Comitente();

    private LocalDateAttributeConverter localDateAttributeConverter;

    private String depto;

    private String circ;

    private String secc;

    private String uc;

    private String parc;

    private String uf;

    private static String[] deptos = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12",
            "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25"};

    private static String[] circs = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};

    private static String[] seccs = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "Ñ", "O", "P", "Q",
            "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "*"};


    //para redirijir a crear EP cuando presiona la opcion desde el Home
    private boolean fromHome;

    @PostConstruct
    public void init() {
        setModelLazy(new DataModel<EProfesional>(entityService));
        setServices(entityService);
        getComitenteConvert().setService(comitenteService);
        getDepartamentoConvert().setService(departamentoService);
        getCiudadConvert().setService(ciudadService);
        getCiudadComitenteConvert().setService(ciudadService);
        getTasaConvert().setService(tasaService);
    }

    public void setNomenclatura(String nomenclatura) {
        String[] campos = nomenclatura.split("-");
        setDepto(campos[0]);
        setCirc(campos[1]);
        setSecc(campos[2]);
        setUc(campos[3]);
        setParc(campos[4]);
        setUf(campos[5]);
    }

    public String getNomenclatura() {
        return getDepto() + "-" +
                getCirc() + "-" +
                getSecc() + "-" +
                getUc() + "-" +
                getParc() + "-" +
                getUf();
    }

    public void inicio() throws ParseException {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            if (getId() != null) {
                setEntity(entityService.getEntityById(getId()));
                if (getEntity().getInmueble().isRural())
                    setDepartamentoSelecionado(getEntity().getInmueble().getDepartamento());
                else
                 setCiudadSeleccionada(getEntity().getInmueble().getCiudad());

                setComitenteSelecionada(getEntity().getComitente());
                setTipo(getEntity().getInmueble().getTipo().getTipo());
                setTasaSeleccionada(tasaService.findByDescripcion(getEntity().getTasaDescripcion()));
                setNomenclatura(getEntity().getNomenclatura());
            } else {

                setVisiblebtn(false);
                setEntity(new EProfesional());
                getEntity().setPerfilAGR(authenticationBean.getPerfilAGR());
                getEntity().getInmueble().setTipo(InmuebleTipo.INMUEBLE_TIPO_RURAL);
                setTipo(getEntity().getInmueble().getTipo().getTipo());

                validate();
            }
        }
    }


    public void beforePrepDownload(EProfesional eProfesional) {
        setEntity(eProfesional);
    }


    public void prepDownload(EProfesional eProfesional) throws IOException {
        if (eProfesional != null)
            setEntity(eProfesional);
        Document document = new Document(PageSize.LETTER);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            document.open();
            document = DatosPDF.crearImagen(document, urlContextPathApp() + "/resources/logo.png");
            document = DatosPDF.crearEncabezado(document, getEntity().getId(), getEntity().getNomenclatura());
            document = DatosPDF.crearDatosPerfilComitente(document, getEntity().getPerfilAGR(), getEntity().getComitente());
            document = DatosPDF.crearInmueble(document, getEntity());
            document = DatosPDF.crearFooter(document);
            document = DatosPDF.crearNuevaPagina(document);
            document = DatosPDF.crearCupon(document, getEntity(), urlContextPathApp() + "/resources/logo.png");
            document = DatosPDF.crearDatosPerfilComitenteCupon(document, getEntity().getPerfilAGR(), getEntity().getComitente());
            document = DatosPDF.firmaDepositante(document, getEntity());
            document = DatosPDF.crearQR(document, getEntity());
        } catch (Exception ex) {
            System.out.println("Error " + ex.getMessage());
        }
        document.close();
        FacesContext context = FacesContext.getCurrentInstance();
        Object response = context.getExternalContext().getResponse();
        if (response instanceof HttpServletResponse) {
            HttpServletResponse hsr = (HttpServletResponse) response;
            hsr.setContentType("application/pdf");
            hsr.setHeader("Content-Disposition", "attachment; filename=\"" + getFileNamePDF() + ".pdf");
            hsr.setContentLength(baos.size());
            try {
                ServletOutputStream out = hsr.getOutputStream();
                baos.writeTo(out);
                out.flush();
            } catch (IOException ex) {
                System.out.println("Error:  " + ex.getMessage());
            }
            context.responseComplete();
        }
    }

    public boolean renderActionDownloadODS(EProfesional eProfesional) {

        return (getUserLogged().getRole().equals(Role.ROLE_AGR) && !eProfesional.isPendienteDePago());

    }

    public boolean renderActionDownloadEP(EProfesional eProfesional) {

        return (getUserLogged().getRole().equals(Role.ROLE_AGR) && eProfesional.isGenerada());

    }

    public void prepDownloadODS(EProfesional eProfesional) throws IOException {
        if (eProfesional != null)
            setEntity(eProfesional);
        Document document = new Document(PageSize.LETTER);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            document.open();
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));
            document = DatosPDF.crearCupon(document, getEntity(), urlContextPathApp() + "/resources/logo.png");
            document = DatosPDF.crearDatosPerfilComitenteCupon(document, getEntity().getPerfilAGR(), getEntity().getComitente());
            document = DatosPDF.firmaDepositante(document, getEntity());
            document = DatosPDF.crearQR(document, getEntity());
        } catch (Exception ex) {
            System.out.println("Error " + ex.getMessage());
        }
        document.close();
        FacesContext context = FacesContext.getCurrentInstance();
        Object response = context.getExternalContext().getResponse();
        if (response instanceof HttpServletResponse) {
            HttpServletResponse hsr = (HttpServletResponse) response;
            hsr.setContentType("application/pdf");
            hsr.setHeader("Content-Disposition", "attachment; filename=\"" + getFileNamePDF() + ".pdf");
            hsr.setContentLength(baos.size());
            try {
                ServletOutputStream out = hsr.getOutputStream();
                baos.writeTo(out);
                out.flush();
            } catch (IOException ex) {
                System.out.println("Error:  " + ex.getMessage());
            }
            context.responseComplete();
        }
    }

    public String getFileNamePDF() {
        StringBuffer sb = new StringBuffer();
        sb.append(getEntity().getPerfilAGR().getApellido());
        return sb.toString();
    }

    public void changeInmueble() {
        this.setTipo(getEntity().getInmueble().getTipo().getTipo());
    }

    public void reload() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }


    public void reset() {
        RequestContext.getCurrentInstance().reset("formEditar:panel");
    }


    /**
     * Si el link esta e el home
     */
    public void listenerFromHome() {
        fromHome = true;
    }

    public String validate() {
        perfilAGR = authenticationBean.getPerfilAGR();
        try {
            perfilAGR.validar();
        } catch (Exception e) {
            StringBuffer urlPerfil = new StringBuffer();
            urlPerfil.append(". ").
                    append("Ingrese ").append("a la opción Mi perfil");
                   /*   .append("<a href=" +
                             urlContextPathApp() +
                             "/pages/perfil/perfil.jsf")
            .append("class=\"BoldGray Fs20 FontRobotoLight Fright MarRight20 HoverEffect\">")
            .append("<i class=\"fa fa-male\"></i>")
            .append("Perfil </a>");*/

            /**
             *
             * <a href="/pages/perfil/perfil.jsf" class="BoldGray Fs20 FontRobotoLight Fright MarRight20 HoverEffect"><i class="fa fa-male"></i> Perfil </a>
             */
            agregarMensaje(FacesMessage.SEVERITY_INFO, bundleMessage("advertencia"),
                    bundleMessage("completar.perfil") + urlPerfil);
            return null;
        }
        //TODO: no funciona desde el home. revisar!!
        //FacesContext.getCurrentInstance().getExternalContext().redirect(Url);
        String url = fromHome ? urlContextPathApp() + "/pages/eprofesional/create?faces-redirect=true" : UtilsBean.REDIRECT_CREATE;
        return url;

    }

    public boolean mostrarBotonGenerarPDF(EProfesional eProfesional) {

        return eProfesional.isPendiente() && getUserLogged().getRole().isAGR();

    }

    @Override
    public String update() {
        getEntity().setNomenclatura(getNomenclatura());
        getEntity().update(comitenteSelecionada, tasaSeleccionada, ciudadSeleccionada, departamentoSelecionado);
        return super.update();
    }


    public String updateComprobante() {
        getEntity().updateComprobante();
        return super.update();
    }

    public void vaciarComitente() {
        setComitenteSelecionada(getComitente());
        setFormAltaComitente(true);
        getComitenteSelecionada().setPerfilAGR(authenticationBean.getPerfilAGR());
        getComitenteSelecionada().update(getCiudadComitenteSelecionada());
    }

    public void comitenteValueChange(ValueChangeEvent event) {
        setComitenteSelecionada((Comitente) event.getNewValue());
    }

    public void mostrarFormAltaComitente() {
        formAltaComitente = true;
    }

    public void cancelFormAltaComitente() {
        formAltaComitente = false;
    }

    public List<Comitente> listComitente() {
        //(Integer page, Integer pagesize, Map<String, String> filters, String sortField, Boolean asc, boolean distinct);
        return comitenteService.getList(0, 100, new HashMap<>(), "nombre", true, false);
    }

    public List<Departamento> listDepartamentos() {
        return departamentoService.getAll();
    }

    public List<Ciudad> listCiudades() {
        return ciudadService.getAll();
    }

    public ComitenteService getComitenteService() {
        return comitenteService;
    }

    public void setComitenteService(ComitenteService comitenteService) {
        this.comitenteService = comitenteService;
    }

    public Comitente getComitenteSelecionada() {
        return comitenteSelecionada;
    }

    public void setComitenteSelecionada(Comitente comitenteSelecionada) {
        this.comitenteSelecionada = comitenteSelecionada;
    }

    public GenericConvert<Comitente> getComitenteConvert() {
        return comitenteConvert;
    }

    public void setComitenteConvert(GenericConvert<Comitente> comitenteConvert) {
        this.comitenteConvert = comitenteConvert;
    }

    public EProfesionalService getEntityService() {
        return entityService;
    }

    public void setEntityService(EProfesionalService entityService) {
        this.entityService = entityService;
    }

    public String getParametro() {
        return parametro;
    }

    public void setParametro(String parametro) {
        this.parametro = parametro;
    }

    public boolean isVisiblebtn() {
        return visiblebtn;
    }

    public void setVisiblebtn(boolean visiblebtn) {
        this.visiblebtn = visiblebtn;
    }

    public AuthenticationBean getAuthenticationBean() {
        return authenticationBean;
    }

    public void setAuthenticationBean(AuthenticationBean authenticationBean) {
        this.authenticationBean = authenticationBean;
    }

    public InmuebleTipo[] inmuebleTipos() {
        return InmuebleTipo.values();
    }

    public FormaPagoODS[] formadepagostipos() {
        return FormaPagoODS.values();
    }

    public Estado[] estados() {
        return Estado.values();
    }

    public DepartamentoService getDepartamentoService() {
        return departamentoService;
    }

    public void setDepartamentoService(DepartamentoService departamentoService) {
        this.departamentoService = departamentoService;
    }

    public Departamento getDepartamentoSelecionado() {
        return departamentoSelecionado;
    }

    public void setDepartamentoSelecionado(Departamento departamentoSelecionado) {
        this.departamentoSelecionado = departamentoSelecionado;
    }

    public GenericConvert<Departamento> getDepartamentoConvert() {
        return departamentoConvert;
    }

    public void setDepartamentoConvert(GenericConvert<Departamento> departamentoConvert) {
        this.departamentoConvert = departamentoConvert;
    }

    public DefaultStreamedContent getDownload() {
        if (getEntity().generar())
            super.update();
        return download;
    }


    public void setDownload(DefaultStreamedContent download) {
        this.download = download;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public CiudadService getCiudadService() {
        return ciudadService;
    }

    public void setCiudadService(CiudadService ciudadService) {
        this.ciudadService = ciudadService;
    }

    public Ciudad getCiudadSeleccionada() {
        return ciudadSeleccionada;
    }

    public void setCiudadSeleccionada(Ciudad ciudadSeleccionada) {
        this.ciudadSeleccionada = ciudadSeleccionada;
    }

    public GenericConvert<Ciudad> getCiudadConvert() {
        return ciudadConvert;
    }

    public void setCiudadConvert(GenericConvert<Ciudad> ciudadConvert) {
        this.ciudadConvert = ciudadConvert;
    }

    public Tasa getTasaSeleccionada() {
        return tasaSeleccionada;
    }

    public void setTasaSeleccionada(Tasa tasaSeleccionada) {
        this.tasaSeleccionada = tasaSeleccionada;
    }

    public GenericConvert<Tasa> getTasaConvert() {
        return tasaConvert;
    }

    public void setTasaConvert(GenericConvert<Tasa> tasaConvert) {
        this.tasaConvert = tasaConvert;
    }

    public TasaService getTasaService() {
        return tasaService;
    }

    public void setTasaService(TasaService tasaService) {
        this.tasaService = tasaService;
    }

    public List<Tasa> listTasas() {
        return tasaService.getAll();
    }

    public static String[] getDeptos() {
        return deptos;
    }

    public static void setDeptos(String[] deptos) {
        EProfesionalBean.deptos = deptos;
    }

    public static String[] getCircs() {
        return circs;
    }

    public static void setCircs(String[] circs) {
        EProfesionalBean.circs = circs;
    }

    public static String[] getSeccs() {
        return seccs;
    }

    public static void setSeccs(String[] seccs) {
        EProfesionalBean.seccs = seccs;
    }

    public String getDepto() {
        return depto;
    }

    public void setDepto(String depto) {
        this.depto = depto;
    }

    public String getCirc() {
        return circ;
    }

    public void setCirc(String circ) {
        this.circ = circ;
    }

    public String getSecc() {
        return secc;
    }

    public void setSecc(String secc) {
        this.secc = secc;
    }

    public String getUc() {
        return uc;
    }

    public void setUc(String uc) {
        this.uc = uc;
    }

    public String getParc() {
        return parc;
    }

    public void setParc(String parc) {
        this.parc = parc;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public boolean mostrarRural(Inmueble inmueble) {
        return inmueble.isRural();
    }

    public boolean mostrarUrbanaSubUrbana(Inmueble inmueble) {
        return inmueble.isUrbanoSubUrbano();
    }

    public void changeVisiblebtn() {
        setVisiblebtn(!isVisiblebtn());
    }

    public Comitente getComitente() {
        return comitente;
    }

    public void setComitente(Comitente comitente) {
        this.comitente = comitente;
    }

    public Ciudad getCiudadComitenteSelecionada() {
        return ciudadComitenteSelecionada;
    }

    public void setCiudadComitenteSelecionada(Ciudad ciudadComitenteSelecionada) {
        this.ciudadComitenteSelecionada = ciudadComitenteSelecionada;
    }

    public GenericConvert<Ciudad> getCiudadComitenteConvert() {
        return ciudadComitenteConvert;
    }

    public void setCiudadComitenteConvert(GenericConvert<Ciudad> ciudadComitenteConvert) {
        this.ciudadComitenteConvert = ciudadComitenteConvert;
    }

    public boolean isFormAltaComitente() {
        return formAltaComitente;
    }

    public void setFormAltaComitente(boolean formAltaComitente) {
        this.formAltaComitente = formAltaComitente;
    }

    public String getPatternDate() {
        return ParameterBean.patternDate;
    }

    public String getPatternDateTime() {
        return ParameterBean.patternDateTime;
    }

    public Double getMontoGeo() {
        return ParameterBean.montoGEO;
    }

    public String getPatternMoneda() {
        return ParameterBean.patternMoneda;
    }


    public void onInmuebleTipoChosen(InmuebleTipo inmuebleTipo) {
      /*  getEntity().getInmueble().setTipo(inmuebleTipo);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Tipo inmueble seleccionado", "Tipo Inmueble:" + inmuebleTipo.getTipo());
        FacesContext.getCurrentInstance().addMessage(null, message);*/
    }

    public void selectTipoInmuebleFromDialog(InmuebleTipo inmuebleTipo) {
        getEntity().getInmueble().setTipo(inmuebleTipo);
        RequestContext.getCurrentInstance().execute("PF('dialogInmueble').hide();");
    }

    public void selectComitenteFromDialog(Comitente comitente) {
        //getEntity().setComitente(comitente);
        setComitenteSelecionada(comitente);
        setFormAltaComitente(true);
        RequestContext.getCurrentInstance().execute("PF('dialogComitente').hide();");
    }


}
