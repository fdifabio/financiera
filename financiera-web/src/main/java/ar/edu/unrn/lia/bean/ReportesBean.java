package ar.edu.unrn.lia.bean;

import ar.edu.unrn.lia.service.CuotaService;
import org.primefaces.model.chart.*;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Named
@Scope("view")
public class ReportesBean implements Serializable {

    private List<Integer> anios = new ArrayList<Integer>(0);
    @Inject
    private CuotaService cuotaService;
    private BarChartModel barModel;
    private HorizontalBarChartModel horizontalBarModel;
    private int year = Calendar.getInstance().get(Calendar.YEAR);
    private int anioSelecionado = 0;

    @PostConstruct
    public void init() {
        createBarModels();
        setAnios(cuotaService.listAniosAdeudadas());
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    public HorizontalBarChartModel getHorizontalBarModel() {
        return horizontalBarModel;
    }

    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();

        ChartSeries adeudado = new ChartSeries();
        adeudado.setLabel("Total Adeudado");

        cuotaService.listAdeudadas(year).forEach(c -> adeudado.set(c.getMes() + "/" + c.getAnio(), c.getMonto()));

/*
        ChartSeries percibido = new ChartSeries();

        percibido.setLabel("Total Percibido");
        cuotaService.listAdeudadas().stream().forEach(c -> percibido.set(c.getAnio() + c.getMes(), c.getMonto()));
        model.addSeries(percibido);
*/

        model.addSeries(adeudado);


        return model;
    }

    private void createBarModels() {
        createBarModel();
        createHorizontalBarModel();
    }

    private void createBarModel() {
        barModel = new BarChartModel();
        barModel = initBarModel();

        barModel.setTitle("Proyecciones");
        barModel.setAnimate(true);
        barModel.setLegendPosition("se");

        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Año");
        xAxis.setMin(2000);


        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Monto");
        yAxis.setMin(0);
       // yAxis.setMax(2000);
    }

    private void createHorizontalBarModel() {
        horizontalBarModel = new HorizontalBarChartModel();

        ChartSeries boys = new ChartSeries();
        boys.setLabel("Boys");
        boys.set("2004", 50);
        boys.set("2005", 96);
        boys.set("2006", 44);
        boys.set("2007", 55);
        boys.set("2008", 25);

        ChartSeries girls = new ChartSeries();
        girls.setLabel("Girls");
        girls.set("2004", 52);
        girls.set("2005", 60);
        girls.set("2006", 82);
        girls.set("2007", 35);
        girls.set("2008", 120);

        horizontalBarModel.addSeries(boys);
        horizontalBarModel.addSeries(girls);

        horizontalBarModel.setTitle("Horizontal and Stacked");
        horizontalBarModel.setLegendPosition("e");
        horizontalBarModel.setStacked(true);

        Axis xAxis = horizontalBarModel.getAxis(AxisType.X);
        xAxis.setLabel("Births");
        xAxis.setMin(0);
        xAxis.setMax(200000);

        Axis yAxis = horizontalBarModel.getAxis(AxisType.Y);
        yAxis.setLabel("Gender");
    }

    public List<Integer> getAnios() {
        return anios;
    }

    public void setAnios(List<Integer> anios) {
        this.anios = anios;
    }

    public int getAnioSelecionado() {
        return anioSelecionado;
    }

    public void setAnioSelecionado(int anioSelecionado) {
        this.anioSelecionado = anioSelecionado;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void onProyeccionesChangeAnio() {
        setYear(getAnioSelecionado());
        createBarModels();
    }
}
