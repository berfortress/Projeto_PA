/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaFX_mainScreens;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import logger.Logger;

/**
 *
 * @author fabio e bernardo
 */
public class SecondWindow extends AnchorPane {

    public int numberPages = 0;
    public static Label results;
    public static String resultsString;

    private Button btnIter, btnAuto;
    private ImageView img = Standards.imagem_Background;

    /**
     * Construtor da classe SecondWindow
     */
    public SecondWindow() {
        btnIter = Standards.buttonFeatures("ITERATIVO", 18, 200, 100, 0, 0);
        btnIter.setOnAction((ActionEvent event) -> {
            Logger logger = Logger.getInstance();
            logger.writeToLog(this, Standards.log_sistema_iterative);
            this.getScene().setRoot(new IterativeFX());
        });

        btnAuto = Standards.buttonFeatures("AUTOMÁTICO", 18, 200, 100, 0, 0);
        btnAuto.setOnAction((ActionEvent event) -> {
            Logger logger = Logger.getInstance();
            logger.writeToLog(this, Standards.log_sistema_automatico);
            this.getScene().setRoot(new AutomaticFX());
        });

        HBox hb = new HBox(btnAuto, btnIter);
        hb.setSpacing(40);
        hb.setTranslateX(115);
        hb.setTranslateY(175);
        this.getChildren().addAll(img, hb);
    }
}
