package javaFX_mainScreens;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Website;
import webCrawler.WebCrawler;

/**
 *
 * @author fabio e bernardo
 */
public class BarChartFX extends AnchorPane {

    private ImageView img = Standards.imagem_Background;
    private Button btnNew, btnGO;

    /**
     * Construtor da classe BarChartFX
     *
     * @param websites
     * @param crawler
     */
    public BarChartFX(List<Website> websites, WebCrawler crawler) {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc = new BarChart<>(xAxis, yAxis);
        bc.setTitle("Web Crawler");
        xAxis.setLabel("Website");
        yAxis.setLabel("Número de ligações");

        XYChart.Series series1 = new XYChart.Series();

        for (Website web : websites) {
            String str = web.getId() + "";
            series1.getData().add(new XYChart.Data(str, crawler.getAdjacentsElem(web).size()));
        }

        bc.getData().addAll(series1);

        btnNew = Standards.buttonFeatures("WEBSITES", 12, 100, 50, 0, 0);
        btnNew.setOnAction((ActionEvent event) -> {
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("Website");
            stage.setScene(new Scene(new InformationFX(websites), 600, 200));
            stage.show();
        });

        btnGO = Standards.buttonFeatures("BACK", 12, 100, 50, 0, 0);
        btnGO.setOnAction((ActionEvent event) -> {
            this.getScene().setRoot(new StatisticsFX(websites,crawler));
        });

        btnGO.setTranslateX(25);
        btnGO.setTranslateY(125);

        VBox vb = new VBox(btnNew, btnGO);
        vb.setSpacing(40);
        vb.setTranslateY(150);
        vb.setTranslateX(10);

        HBox hb = new HBox(bc, vb);
        this.getChildren().addAll(img, hb);
    }
}
