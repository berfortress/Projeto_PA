/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaFX_mainScreens;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logger.Logger;

/**
 *
 * @author fabio e bernardo
 */
public class Main extends Application {

    public int numberPages = 0;
    public static Label results;
    public static String resultsString;

    private Scene mainScene;
    
    /**
     * Main
     * @param args 
     */
    public static void main(String[] args) {
        launch(args);
    }
    /**
     * Start
     * @param primaryStage
     * @throws Exception 
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        mainScene = new Scene(new FirstWindow(), 650, 425, Color.BLACK);
        Logger logger = Logger.getInstance();
        logger.writeToLog(this, Standards.log_sistema_iniciado);
        //https://moodle.ips.pt/1920/course/index.php?categoryid=7
        primaryStage.setTitle("Web Crawler");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
}
