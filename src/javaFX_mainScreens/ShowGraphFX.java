/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaFX_mainScreens;

import adtgraph.Digraph;
import java.util.List;import javaFX_containers.SmartGraphDemoContainer;
;
import javaFX_graphView.*;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.Link;
import models.Website;
import webCrawler.WebCrawler;

/**
 *
 * @author fabio e bernardo
 */
public class ShowGraphFX extends AnchorPane{
    private Button btnGO, btnExit;
    private ImageView img = Standards.imagem_Background;
    private SmartGraphDemoContainer smartGraph;
    private SmartGraphPanel<Website, Link> graphView;
    private Scene scene;
    
    /**
     * Construtor da classe ShowGraphFX
     * @param websites
     * @param generic 
     */
    public ShowGraphFX(List<Website> websites, WebCrawler generic) {
        btnGO = Standards.buttonFeatures("GRAPH \nVISUALIZATION", 18, 200, 100, 0, 0);
        btnGO.setOnAction((ActionEvent event) -> {
            Stage stage = new Stage();
            Group root = new Group();
            this.scene = new Scene(root, 1200, 800);
            stage.setScene(scene);

            SmartPlacementStrategy strategy = new SmartCircularSortedPlacementStrategy();
            Digraph<Website, Link> g = generic.getDigraph();

            this.graphView = new SmartGraphPanel<>(g, strategy);
            this.smartGraph = new SmartGraphDemoContainer(graphView);

            smartGraph.setMinWidth(1100);
            smartGraph.setMinHeight(800);

            root.getChildren().add(smartGraph);
            stage.setTitle("Web Crawler");
            stage.setScene(scene);
            stage.show();

            this.graphView.init();
            this.graphView.setAutomaticLayout(true);
        });
        
        btnExit = Standards.buttonFeatures("EXIT", 18, 200, 100, 0, 0);
        btnExit.setOnAction((ActionEvent event) -> {
            Stage stage = (Stage) btnGO.getScene().getWindow();
            stage.close();
        });
        
        HBox hb = new HBox(btnGO, btnExit);
        hb.setSpacing(40);
        hb.setTranslateX(115);
        hb.setTranslateY(175);
        
        this.getChildren().addAll(img, hb);
    }
}