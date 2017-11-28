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
import java.util.List;

@Named
@Scope("view")
public class ReportesBean implements Serializable {


    @Inject
    private CuotaService cuotaService;
    private BarChartModel barModel;
    private BarChartModel generalBarModel;
    private int year = Calendar.getInstance().get(Calendar.YEAR);
    private int month = Calendar.getInstance().get(Calendar.MONTH);
    private int anioSelecionado = 0;
    private int anioGeneralSelecionado = 0;
    private List<Integer> anios = new ArrayList<Integer>(0);
    private List<Integer> aniosAdeudados = new ArrayList<Integer>(0);
    private Boolean isrender = true;

    @PostConstruct
    public void init() {
        anioGeneralSelecionado = year;
        anioSelecionado = year;
        setAnios(cuotaService.listAnios());
        setAniosAdeudados(cuotaService.listAniosAdeudadas());

        createBarModel();
        createGeneralBarModel();
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
        yAxis.setLabel("Monto");
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
        yAxis.setLabel("Monto");
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

    public Boolean getIsrender() {
        return isrender;
    }

    public void setIsrender(Boolean isrender) {
        this.isrender = isrender;
    }
}
