/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaFX_mainScreens;

import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.Website;
import webCrawler.WebCrawler;

/**
 *
 * @author fabio e bernardo
 */
public class StatisticsFX<E> extends AnchorPane{
    private ImageView img = Standards.imagem_Background;
    private Button btnPageVisit, btnMostVisited, btnGraf, btnPath, btnGO;
        
    /**
     * Constutor da classe StatisticsFX
     * @param websites
     * @param crawler 
     */
    public StatisticsFX(List<Website> websites,WebCrawler crawler) {
        Text txt = Standards.defaultText("Clique no botão respetivo aos dados que pretende visualizar", 15, 0, 0);
        txt.setStyle("-fx-font-weight: bold");
        txt.setTranslateX(-20);
        
        btnMostVisited = Standards.buttonFeatures("MAIS VISITADA", 15, 200, 100, 0, 0);
        btnMostVisited.setOnAction((ActionEvent event) -> {
            this.getScene().setRoot(new MostVisitedFX(websites,crawler));
        });

        btnPageVisit = Standards.buttonFeatures("GRÁFICO", 15, 200, 100, 0, 0);
        btnPageVisit.setOnAction((ActionEvent event) -> {
            this.getScene().setRoot(new BarChartFX(websites,crawler));
        });
        
        btnGraf = Standards.buttonFeatures("PÁGINAS VISITADAS", 15, 200, 100, 0, 0);
        btnGraf.setOnAction((ActionEvent event) -> {
            this.getScene().setRoot(new PagesVisitedFX(websites,crawler));
        });
        
        btnPath = Standards.buttonFeatures("CAMINHO MAIS CURTO", 15, 200, 100, 0, 0);
        btnPath.setOnAction((ActionEvent event) -> {
            this.getScene().setRoot(new MinPathFX(websites,crawler));
        });
        
        btnGO = Standards.buttonFeatures("Continue", 15, 150, 50, 0, 0);
        btnGO.setOnAction((ActionEvent event) -> {
            this.getScene().setRoot(new DAOFX(websites,crawler));
        });
        btnGO.setTranslateX(375);

        HBox hb1 = new HBox(btnMostVisited, btnPageVisit);
        hb1.setSpacing(40);
        
        HBox hb2 = new HBox(btnGraf, btnPath);
        hb2.setSpacing(40);
        
        VBox vb = new VBox(txt, hb1, hb2, btnGO);
        vb.setSpacing(40);
        vb.setTranslateX(105);
        vb.setTranslateY(25);
        this.getChildren().addAll(img, vb);
    }
}
