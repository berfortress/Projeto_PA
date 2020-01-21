/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaFX_mainScreens;

import exceptions.LinkException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.Link;
import models.Website;
import webCrawler.WebCrawler;

/**
 *
 * @author fabio e bernardo
 */
public class MinPathFX extends AnchorPane {

    private ImageView img = Standards.imagem_Background;
    private Button btnGO, btnOrig, btnDest, btnCalc;
    private Website orig, dest;
    private int cost;

    /**
     * Construtor da classe MinPathFX
     *
     * @param websites
     * @param crawler
     */
    public MinPathFX(List<Website> websites,WebCrawler crawler) {
        Text txt = Standards.defaultText("Minimum Cost Path", 18, 0, 0);
        List<Link> listLink = crawler.getLinksVisited();

        orig = null;
        dest = null;

        final ObservableList<Website> list = FXCollections.observableArrayList();
        final ListView<Website> listWebsites = new ListView<>(list);

        listWebsites.setMinWidth(600);
        listWebsites.setMaxHeight(200);

        listWebsites.setTranslateY(-45);

        for (Website web : websites) {
            listWebsites.getItems().add(listWebsites.getItems().size(), web);
            listWebsites.scrollTo(listWebsites.getItems().size() - 1);
        }

        btnGO = Standards.buttonFeatures("BACK", 15, 125, 25, 0, 0);
        btnGO.setOnAction((ActionEvent event) -> {
            this.getScene().setRoot(new StatisticsFX(websites,crawler));
        });
        btnGO.setTranslateY(-60);

        btnDest = Standards.buttonFeatures("DESTINY", 15, 125, 25, 0, 0);
        btnDest.setOnAction((ActionEvent event) -> {
            dest = listWebsites.getSelectionModel().getSelectedItem();
        });

        btnOrig = Standards.buttonFeatures("ORIGIN", 15, 125, 25, 0, 0);
        btnOrig.setOnAction((ActionEvent event) -> {
            orig = listWebsites.getSelectionModel().getSelectedItem();
        });

        Text txt1 = Standards.defaultText("", 18, 0, 0);
        cost = 0;

        btnCalc = Standards.buttonFeatures("CALCULATE", 15, 125, 25, 0, 0);
        btnCalc.setOnAction((ActionEvent event) -> {
            if (dest != null && orig != null) {
                try {
                    cost = crawler.minimumCostPath(orig, dest, websites, listLink);
                    txt1.setText("The Minimum Cost Path is " + cost);
                } catch (LinkException ex) {
                    Logger.getLogger(MinPathFX.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        HBox hb = new HBox(btnOrig, btnDest, btnCalc, txt1);
        hb.setSpacing(20);
        hb.setTranslateY(-20);

        VBox vb = new VBox(txt, txt1, hb, listWebsites, btnGO);
        vb.setSpacing(40);
        vb.setTranslateX(25);
        vb.setTranslateY(10);

        this.getChildren().addAll(img, vb);
    }
}
