package ar.edu.unrn.lia.bean;

import ar.edu.unrn.lia.dao.CreditoDAO;
import ar.edu.unrn.lia.model.Cliente;
import ar.edu.unrn.lia.model.Credito;
import ar.edu.unrn.lia.model.Movimiento;
import ar.edu.unrn.lia.service.ClienteService;
import ar.edu.unrn.lia.service.CuotaService;
import ar.edu.unrn.lia.service.MovimientoService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Named
@Scope("view")
public class ReportesBean implements Serializable {


    @Inject
    private CuotaService cuotaService;

    @Inject
    private ClienteService clienteService;

    @Inject
    private CreditoDAO creditoDAO;

    @Inject
    private MovimientoService movimientoService;

    private BarChartModel barModel;
    private BarChartModel generalBarModel;
    private BarChartModel movimientosBarModel;
    private int year = Calendar.getInstance().get(Calendar.YEAR);
    private int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
    private int anioSelecionado = 0;
    private int anioGeneralSelecionado = 0;
    private int anioMovimientoSelecionado = 0;
    private List<Integer> anios = new ArrayList<Integer>(0);
    private List<Integer> aniosAdeudados = new ArrayList<Integer>(0);
    private List<Integer> aniosMeses = new ArrayList<Integer>(0);
    private List<Cliente> morosos = new ArrayList<>(0);
    private List<Cliente> vencimientosDelDia = new ArrayList<>(0);
    private LazyDataModel<Credito> modelCreditos;
    private Boolean isrender = true;

    /*Se utilizan para filtar creditos*/
    private Map<String, Object> filters = new HashMap<>(0);
    private Date fechaInicio;
    private Date fechaFin;

    @PostConstruct
    public void init() {
        anioGeneralSelecionado = year;
        anioSelecionado = year;
        setAnios(cuotaService.listAnios());
        setAniosAdeudados(cuotaService.listAniosAdeudadas());
        setAniosMeses(movimientoService.listAnios());
        setMorosos(clienteService.searchMorosos());
        setVencimientosDelDia(clienteService.searchVencimientosDelDia());
        createBarModel();
        createGeneralBarModel();
        createMovimientosBarModel();

        loadModels();
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    public BarChartModel getGeneralBarModel() {
        return generalBarModel;
    }

    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();

        ChartSeries adeudado = new ChartSeries();
        adeudado.setLabel("Monto Adeudado");
        ChartSeries countadeudado = new ChartSeries();
        countadeudado.setLabel("Cuotas Adeudadas");

        if (anioSelecionado != year) {
            month = 0;
        } else month = Calendar.getInstance().get(Calendar.MONTH);
        cuotaService.listAdeudadas(getAnioSelecionado(), month).forEach(a -> adeudado.set(a.getMes() + "/" + a.getAnio(), a.getMonto()));
        //  cuotaService.countAdeudadas(getAnioSelecionado(), month).forEach(c -> countadeudado.set(c.getMes() + "/" + c.getAnio(), c.getMonto()));

        if (adeudado.getData().size() != 0)
            model.addSeries(adeudado);
        if (countadeudado.getData().size() != 0)
            //    model.addSeries(countadeudado);

            model.setLegendPosition("se");
        return model;
    }


    private void createBarModel() {
        barModel = new BarChartModel();
        barModel = initBarModel();

        barModel.setTitle("Proyecciones");
        barModel.setAnimate(true);
        //barModel.setLegendPosition("se");

        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Año");
        xAxis.setMin(2000);


        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Monto ($)");
        yAxis.setMin(0);
        // yAxis.setMax(2000);
    }

    private void createGeneralBarModel() {
        generalBarModel = new BarChartModel();
        generalBarModel.setAnimate(true);
        ChartSeries saldadas = new ChartSeries();
        saldadas.setLabel("Saldadas");
        cuotaService.listSaldadas(getAnioGeneralSelecionado()).forEach(c -> saldadas.set(c.getMes() + "/" + c.getAnio(), c.getMonto()));


        ChartSeries adeudadas = new ChartSeries();
        adeudadas.setLabel("Adeudadas");
        cuotaService.listAdeudadas(getAnioGeneralSelecionado(), 0).forEach(a -> adeudadas.set(a.getMes() + "/" + a.getAnio(), a.getMonto()));
        if (saldadas.getData().size() == 0 && adeudadas.getData().size() == 0)
            setIsrender(false);
        else {
            if (saldadas.getData().size() != 0)
                generalBarModel.addSeries(saldadas);
            if (adeudadas.getData().size() != 0)
                generalBarModel.addSeries(adeudadas);

        }
        generalBarModel.setTitle("Reporte General");
        generalBarModel.setSeriesColors("58BA27,FFCC33");
        generalBarModel.setLegendPosition("se");
        // generalBarModel.setStacked(true);

        Axis xAxis = generalBarModel.getAxis(AxisType.X);
        xAxis.setLabel("Periodo");
        // xAxis.setMin(0);
        // xAxis.setMax(200000);

        Axis yAxis = generalBarModel.getAxis(AxisType.Y);
        yAxis.setLabel("Monto ($)");
    }

    private void loadModels() {
        modelCreditos = new LazyDataModel<Credito>() {
            private static final long serialVersionUID = -5675337537196030329L;
            static final String ORDER_DEFAULT = "fechaCreacion";

            @Override
            public List<Credito> load(int first, int pageSize, String sortField, SortOrder sortOrder,
                                      Map<String, Object> filters) {

                Map<String, String> filtersMap = filters.entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, e -> (String) e.getValue()));

                modelCreditos.setRowCount(getCount(filtersMap));
                modelCreditos.setPageSize(pageSize);

                return getList(first, pageSize, filtersMap, (sortField == null ? ORDER_DEFAULT : sortField),
                        (sortOrder.name().equals(SortOrder.DESCENDING.toString())));
            }
        };
    }

    public void onSearch() {
        filters.clear();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        filters.put("fecha_inicio", formatter.format(fechaInicio));
        filters.put("fecha_fin", formatter.format(fechaFin));
//        for (String filter : selectedFilters) {
//            filters.put(filter, "");
//        }
//        if (convocatoria != null && convocatoria.getIdConvocatoriaSigeva() != null)
//            filters.put("convocatoria.id", convocatoria.getId());
        loadModels();
    }

    public List<Credito> getList(Integer page, Integer pagesize, Map<String, String> filters, String sortField,
                                 Boolean asc) {
        filters.putAll(this.filters.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> (String) e.getValue())));
        return creditoDAO.listwithPag(creditoDAO.getSearchPredicates(creditoDAO.rootCount(), filters), page,
                pagesize, sortField, asc, true);
    }

    public int getCount(Map<String, String> filters) {
        return creditoDAO.count(creditoDAO.getSearchPredicates(creditoDAO.rootCount(), filters)).intValue();
    }

    private void createMovimientosBarModel() {
        movimientosBarModel = new BarChartModel();
        movimientosBarModel.setAnimate(true);
        ChartSeries egresos = new ChartSeries();
        egresos.setLabel("Egresos");
        if (getAnioMovimientoSelecionado() != null)
            movimientoService.findByMonthYear(getAnioMovimientoSelecionado(), 2017, Movimiento.Tipo.EGRESO).forEach(e -> egresos.set(e.getMes() + "/" + e.getAnio(), e.getMonto()));

        ChartSeries ingresos = new ChartSeries();
        ingresos.setLabel("Ingresos");
        if (getAnioMovimientoSelecionado() != null)
            movimientoService.findByMonthYear(getAnioMovimientoSelecionado(), 2017, Movimiento.Tipo.INGRESO).forEach(e -> egresos.set(e.getMes() + "/" + e.getAnio(), e.getMonto()));


        if (egresos.getData().size() == 0 && ingresos.getData().size() == 0)
            setIsrender(false);
        else {
            if (egresos.getData().size() != 0)
                movimientosBarModel.addSeries(egresos);
            if (ingresos.getData().size() != 0)
                movimientosBarModel.addSeries(ingresos);

        }
        movimientosBarModel.setTitle("Movimientos");
        movimientosBarModel.setSeriesColors("#43A047,#E53935");
        movimientosBarModel.setLegendPosition("se");
        movimientosBarModel.setStacked(true);

        Axis xAxis = movimientosBarModel.getAxis(AxisType.X);
        xAxis.setLabel("Dia");

        Axis yAxis = movimientosBarModel.getAxis(AxisType.Y);
        yAxis.setLabel("Monto ($)");
    }

    public List<Integer> getAnios() {
        return anios;
    }

    public void setAnios(List<Integer> anios) {
        this.anios = anios;
    }

    public List<Integer> getAniosAdeudados() {
        return aniosAdeudados;
    }

    public void setAniosAdeudados(List<Integer> aniosAdeudados) {
        this.aniosAdeudados = aniosAdeudados;
    }

    public int getAnioSelecionado() {
        return anioSelecionado;
    }

    public void setAnioSelecionado(int anioSelecionado) {
        this.anioSelecionado = anioSelecionado;
    }

    public int getAnioGeneralSelecionado() {
        return anioGeneralSelecionado;
    }

    public void setAnioGeneralSelecionado(int anioGeneralSelecionado) {
        this.anioGeneralSelecionado = anioGeneralSelecionado;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void onProyeccionesChangeAnio() {
        createBarModel();
    }

    public void onGeneralChangeAnio() {
        setIsrender(true);

        createGeneralBarModel();

    }

    public void onMovimientoChangeAnio() {
        setIsrender(true);
        createMovimientosBarModel();
    }

    public Boolean getIsrender() {
        return isrender;
    }

    public void setIsrender(Boolean isrender) {
        this.isrender = isrender;
    }


    public List<Cliente> getMorosos() {
        return morosos;
    }

    public void setMorosos(List<Cliente> morosos) {
        this.morosos = morosos;
    }

    public List<Cliente> getVencimientosDelDia() {
        return vencimientosDelDia;
    }

    public void setVencimientosDelDia(List<Cliente> vencimientosDelDia) {
        this.vencimientosDelDia = vencimientosDelDia;
    }

    public LazyDataModel<Credito> getModelCreditos() {
        return modelCreditos;
    }

    public void setModelCreditos(LazyDataModel<Credito> modelCreditos) {
        this.modelCreditos = modelCreditos;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public BarChartModel getMovimientosBarModel() {
        return movimientosBarModel;
    }

    public void setMovimientosBarModel(BarChartModel movimientosBarModel) {
        this.movimientosBarModel = movimientosBarModel;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public Integer getAnioMovimientoSelecionado() {
        return anioMovimientoSelecionado;
    }

    public void setAnioMovimientoSelecionado(Integer anioMovimientoSelecionado) {
        this.anioMovimientoSelecionado = anioMovimientoSelecionado;
    }

    public List<Integer> getAniosMeses() {
        return aniosMeses;
    }

    public void setAniosMeses(List<Integer> aniosMeses) {
        this.aniosMeses = aniosMeses;
    }

    public MovimientoService getMovimientoService() {
        return movimientoService;
    }

    public void setMovimientoService(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    public String date() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return simpleDateFormat.format(new Date());
    }
}
