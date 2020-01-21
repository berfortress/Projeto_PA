/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaFX_mainScreens;

import adtgraph.Vertex;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.Website;
import webCrawler.WebCrawler;

/**
 *
 * @author fabio e bernardo
 */
public class MostVisitedFX extends AnchorPane{
    ImageView img = Standards.imagem_Background;
    Button btnGO;

    /**
     * Construtor da classe MostVisitedFX
     * @param websites
     * @param crawler 
     */
    public MostVisitedFX(List<Website> websites, WebCrawler crawler) {
        btnGO = Standards.buttonFeatures("BACK", 15, 150, 50, 0, 0);
        btnGO.setOnAction((ActionEvent event) -> {
            this.getScene().setRoot(new StatisticsFX(websites, crawler));
        });
        btnGO.setTranslateX(425);
        btnGO.setTranslateY(150);
        
        int max = -1;
        Website pg = new Website();
        for (Vertex<Website> p : crawler.getAllVertices()) {
            if (max < crawler.getAdjacents(p).size()) {
                pg = p.element();
                max = crawler.getAdjacents(p).size();
            }
        }
        
        Text txt = Standards.defaultText("The most visited Website is:", 15, 0, 0);
        Text txt2 = Standards.defaultText(pg.getWebsiteName()+ "\n" + pg.getURL(), 15, 0, 0);
        txt2.setStyle("-fx-font-weight: bold");
        
        VBox vb = new VBox(txt, txt2, btnGO);
        vb.setSpacing(40);
        vb.setTranslateX(60);
        vb.setTranslateY(80);
        
        this.getChildren().addAll(img, vb);
    }
}
