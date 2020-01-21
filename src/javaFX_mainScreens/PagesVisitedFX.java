/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaFX_mainScreens;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
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
public class PagesVisitedFX extends AnchorPane{
    private ImageView img = Standards.imagem_Background;
    private Button btnGO;
    
    /**
     * Construtor da classe PagesVisitedFX
     * @param websites
     * @param crawler 
     */
    public PagesVisitedFX(List<Website> websites, WebCrawler crawler) {
        Text txt = Standards.defaultText("Websites Visited", 18, 0, 0);
        
        final ObservableList<Website> list = FXCollections.observableArrayList();
        final ListView<Website> listWebsites = new ListView<>(list);

        listWebsites.setMinWidth(600);
        listWebsites.setMaxHeight(200);
        
        listWebsites.setTranslateY(20);
        
        for (Website web : websites) {
            listWebsites.getItems().add(listWebsites.getItems().size(), web);
            listWebsites.scrollTo(listWebsites.getItems().size() - 1); 
        }
        
        btnGO = Standards.buttonFeatures("BACK", 15, 150, 50, 0, 0);
        btnGO.setOnAction((ActionEvent event) -> {
            this.getScene().setRoot(new StatisticsFX(websites,crawler));
        });
        btnGO.setTranslateX(450);
        
        VBox vb = vBoxDetails(txt, listWebsites);
        
        this.getChildren().addAll(img, vb);
    }

    public VBox vBoxDetails(Text txt, final ListView<Website> listWebsites) {
        VBox vb = new VBox(txt, listWebsites, btnGO);
        vb.setSpacing(60);
        vb.setTranslateX(25);
        vb.setTranslateY(20);
        return vb;
    }
}
