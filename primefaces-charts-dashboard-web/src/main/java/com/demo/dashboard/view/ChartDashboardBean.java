package com.demo.dashboard.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import org.primefaces.event.ItemSelectEvent;

import com.demo.dashboard.service.ChartDataService;
import com.demo.dashboard.service.ChartSampleData;

import software.xdev.chartjs.model.charts.BarChart;
import software.xdev.chartjs.model.charts.DoughnutChart;
import software.xdev.chartjs.model.charts.LineChart;
import software.xdev.chartjs.model.charts.MixedChart;
import software.xdev.chartjs.model.charts.PieChart;
import software.xdev.chartjs.model.color.RGBAColor;
import software.xdev.chartjs.model.data.BarData;
import software.xdev.chartjs.model.data.DoughnutData;
import software.xdev.chartjs.model.data.LineData;
import software.xdev.chartjs.model.data.MixedData;
import software.xdev.chartjs.model.data.PieData;
import software.xdev.chartjs.model.dataset.BarDataset;
import software.xdev.chartjs.model.dataset.DoughnutDataset;
import software.xdev.chartjs.model.dataset.LineDataset;
import software.xdev.chartjs.model.dataset.PieDataset;
import software.xdev.chartjs.model.enums.IndexAxis;
import software.xdev.chartjs.model.options.BarOptions;
import software.xdev.chartjs.model.options.DoughnutOptions;
import software.xdev.chartjs.model.options.LegendOptions;
import software.xdev.chartjs.model.options.LineOptions;
import software.xdev.chartjs.model.options.Options;
import software.xdev.chartjs.model.options.PieOptions;
import software.xdev.chartjs.model.options.Plugins;
import software.xdev.chartjs.model.options.Title;
import software.xdev.chartjs.model.options.Tooltip;
import software.xdev.chartjs.model.options.elements.Fill;
import software.xdev.chartjs.model.options.scale.Scales;
import software.xdev.chartjs.model.options.scale.cartesian.CartesianScaleOptions;
import software.xdev.chartjs.model.options.scale.cartesian.CartesianTickOptions;

/**
 * Bean Faces que construye JSON Chart.js (XDEV) para {@code p:chart} (PrimeFaces 14).
 * Los datos demográficos vienen del EJB {@link ChartDataService}.
 */
@Named
@ViewScoped
public class ChartDashboardBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private ChartDataService chartDataService;

    private String doughnutModel;
    private String pieModel;
    private String barModel;
    private String lineModel;
    private String mixedModel;

    @PostConstruct
    public void init() {
        ChartSampleData data = chartDataService.loadSampleData();
        createDoughnutModel(data);
        createPieModel(data);
        createBarModel(data);
        createLineModel(data);
        createMixedModel(data);
    }

    /**
     * Doughnut Red / Blue / Yellow (paleta y proporción del Showcase PF).
     */
    private void createDoughnutModel(ChartSampleData data) {
        doughnutModel = new DoughnutChart()
                .setData(new DoughnutData()
                        .addDataset(new DoughnutDataset()
                                .setData(toBigDecimals(data.getDoughnutValues()))
                                .addBackgroundColors(
                                        new RGBAColor(255, 99, 132),
                                        new RGBAColor(54, 162, 235),
                                        new RGBAColor(255, 205, 86))
                                .addBorderColors(
                                        new RGBAColor(255, 255, 255),
                                        new RGBAColor(255, 255, 255),
                                        new RGBAColor(255, 255, 255))
                                .setBorderWidth(2))
                        .setLabels(data.getDoughnutLabels().toArray(String[]::new)))
                .setOptions(new DoughnutOptions()
                        .setResponsive(true)
                        .setMaintainAspectRatio(Boolean.FALSE)
                        .setPlugins(new Plugins()
                                .setLegend(new LegendOptions()
                                        .setDisplay(true)
                                        .setPosition("top"))))
                .toJson();
    }

    private void createPieModel(ChartSampleData data) {
        pieModel = new PieChart()
                .setData(new PieData()
                        .addDataset(new PieDataset()
                                .setData(toBigDecimals(data.getPieValues()))
                                .setLabel("Participación")
                                .addBackgroundColors(
                                        new RGBAColor(255, 99, 132),
                                        new RGBAColor(54, 162, 235),
                                        new RGBAColor(255, 205, 86),
                                        new RGBAColor(75, 192, 192)))
                        .setLabels(data.getPieLabels().toArray(String[]::new)))
                .setOptions(new PieOptions()
                        .setResponsive(true)
                        .setMaintainAspectRatio(Boolean.FALSE)
                        .setPlugins(new Plugins()
                                .setLegend(new LegendOptions()
                                        .setDisplay(true)
                                        .setPosition("top"))
                                .setTitle(new Title()
                                        .setDisplay(true)
                                        .setText("Participación por región"))))
                .toJson();
    }

    private void createBarModel(ChartSampleData data) {
        barModel = new BarChart()
                .setData(new BarData()
                        .addDataset(new BarDataset()
                                .setData(toNumbers(data.getSalesCurrentYear()))
                                .setLabel("Ventas 2025")
                                .setBackgroundColor(new RGBAColor(255, 99, 132, 0.55))
                                .setBorderColor(new RGBAColor(255, 99, 132))
                                .setBorderWidth(1))
                        .addDataset(new BarDataset()
                                .setData(toNumbers(data.getSalesPreviousYear()))
                                .setLabel("Ventas 2024")
                                .setBackgroundColor(new RGBAColor(54, 162, 235, 0.55))
                                .setBorderColor(new RGBAColor(54, 162, 235))
                                .setBorderWidth(1))
                        .setLabels(data.getMonthLabels().toArray(String[]::new)))
                .setOptions(new BarOptions()
                        .setResponsive(true)
                        .setMaintainAspectRatio(false)
                        .setIndexAxis(IndexAxis.X)
                        .setScales(new Scales().addScale(Scales.ScaleAxis.Y, new CartesianScaleOptions()
                                .setStacked(false)
                                .setTicks(new CartesianTickOptions().setAutoSkip(true))))
                        .setPlugins(new Plugins()
                                .setLegend(new LegendOptions().setDisplay(true).setPosition("top"))
                                .setTitle(new Title()
                                        .setDisplay(true)
                                        .setText("Comparativa de ventas mensuales"))))
                .toJson();
    }

    private void createLineModel(ChartSampleData data) {
        lineModel = new LineChart()
                .setData(new LineData()
                        .addDataset(new LineDataset()
                                .setData(toNumbers(data.getActiveUsers()))
                                .setLabel("Usuarios activos")
                                .setBorderColor(new RGBAColor(75, 192, 192))
                                .setBackgroundColor(new RGBAColor(75, 192, 192, 0.2))
                                .setLineTension(0.35f)
                                .setFill(new Fill<>(Boolean.TRUE)))
                        .addDataset(new LineDataset()
                                .setData(toNumbers(data.getNewSessions()))
                                .setLabel("Sesiones nuevas")
                                .setBorderColor(new RGBAColor(255, 159, 64))
                                .setBackgroundColor(new RGBAColor(255, 159, 64, 0.15))
                                .setLineTension(0.35f)
                                .setFill(new Fill<>(Boolean.FALSE)))
                        .setLabels(data.getMonthLabels().toArray(String[]::new)))
                .setOptions(new LineOptions()
                        .setResponsive(true)
                        .setMaintainAspectRatio(false)
                        .setPlugins(new Plugins()
                                .setLegend(new LegendOptions().setDisplay(true).setPosition("top"))
                                .setTitle(new Title()
                                        .setDisplay(true)
                                        .setText("Tendencia de actividad"))))
                .toJson();
    }

    private void createMixedModel(ChartSampleData data) {
        MixedData mixedData = new MixedData();

        mixedData.addDataset(new BarDataset()
                .setType("bar")
                .setData(toNumbers(data.getOrders()))
                .setLabel("Pedidos")
                .setBorderColor(new RGBAColor(255, 99, 132, 1.0))
                .setBackgroundColor(new RGBAColor(255, 99, 132, 0.5))
                .setBorderWidth(1));

        mixedData.addDataset(new LineDataset()
                .setType("line")
                .setData(toNumbers(data.getOrderTarget()))
                .setLabel("Meta")
                .setBorderColor(new RGBAColor(75, 192, 192, 1.0))
                .setBackgroundColor(new RGBAColor(75, 192, 192, 0.35))
                .setLineTension(0.2f)
                .setFill(new Fill<>(Boolean.FALSE)));

        mixedData.setLabels(data.getMonthLabels().toArray(String[]::new));

        mixedModel = new MixedChart()
                .setData(mixedData)
                .setOptions(new Options<>()
                        .setResponsive(true)
                        .setMaintainAspectRatio(false)
                        .setPlugins(new Plugins()
                                .setTooltip(new Tooltip().setMode("index"))
                                .setLegend(new LegendOptions().setDisplay(true).setPosition("top"))
                                .setTitle(new Title()
                                        .setDisplay(true)
                                        .setText("Pedidos vs meta (mixto)"))))
                .toJson();
    }

    private static BigDecimal[] toBigDecimals(List<BigDecimal> values) {
        return values.toArray(BigDecimal[]::new);
    }

    private static Number[] toNumbers(List<Number> values) {
        return values.toArray(Number[]::new);
    }

    public void itemSelect(ItemSelectEvent event) {
        FacesMessage msg = new FacesMessage(
                FacesMessage.SEVERITY_INFO,
                "Elemento seleccionado",
                "Índice: " + event.getItemIndex()
                        + ", dataset: " + event.getDataSetIndex());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public String getDoughnutModel() {
        return doughnutModel;
    }

    public String getPieModel() {
        return pieModel;
    }

    public String getBarModel() {
        return barModel;
    }

    public String getLineModel() {
        return lineModel;
    }

    public String getMixedModel() {
        return mixedModel;
    }
}
