/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaFX_mainScreens;

import DAO.DAO;
import DAO.DAOFactory;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
public class DAOFX extends AnchorPane {

    public int numberPages = 0;
    public static Label results;
    public static String resultsString;
    private Button btnIter, btnAuto;
    private ImageView img = Standards.imagem_Background;

    public DAOFX(List<Website> websites, WebCrawler crawler) {
        Text inf = Standards.defaultText("Escolha em que tipo de ficheiro pretende guardar o modelo gerado \npelo modo de crawling escolhido.", 18, 0, 0);
        inf.setStyle("-fx-font-weight: bold");
        inf.setTranslateY(125);
        inf.setTranslateX(50);

        btnIter = Standards.buttonFeatures("DAO Serialization", 18, 200, 100, 0, 0);
        btnIter.setOnAction((ActionEvent event) -> {
            DAO dao = DAOFactory.createDAO("serialization", "./");
            for (Website web : websites) {
                dao.saveWC(web);
            }
            this.getScene().setRoot(new ShowGraphFX(websites, crawler));
        });

        btnAuto = Standards.buttonFeatures("DAO JSON", 18, 200, 100, 0, 0);
        btnAuto.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DAO dao = DAOFactory.createDAO("onejson", "./");
                for (Website web : crawler.webSitesVisited()) {
                    dao.saveWC(web);
                    websites.add(web);
                }
                DAOFX.this.getScene().setRoot(new ShowGraphFX(websites, crawler));
            }
        });

        HBox hb = new HBox(btnAuto, btnIter);
        hb.setSpacing(40);
        hb.setTranslateX(115);
        hb.setTranslateY(175);

        VBox vb = new VBox(inf, hb);
        this.getChildren().addAll(img, vb);
    }
}
