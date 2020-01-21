/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaFX_mainScreens;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import models.Website;

/**
 *
 * @author fabio e bernardo
 */
public class InformationFX extends AnchorPane{
    private ImageView img;
    private Image i;

    /**
     * Construtor da classe InformationFX
     * @param websites 
     */
    public InformationFX(List<Website> websites) {
        i = new Image("javaFX_images/background.png");
        img = new ImageView();
        img.setImage(i);
        img.setFitWidth(800);
        img.setFitHeight(600);
        img.setOpacity(0.5);

        final ObservableList<Website> list = FXCollections.observableArrayList();
        final ListView<Website> listWebsites = new ListView<>(list);

        listWebsites.setMinWidth(600);
        listWebsites.setMaxHeight(200);
        
        listWebsites.setTranslateX(5);
        listWebsites.setTranslateY(5);

        for (Website web : websites) {
            listWebsites.getItems().add(listWebsites.getItems().size(), web);
            listWebsites.scrollTo(listWebsites.getItems().size() - 1);
        }

        this.getChildren().addAll(img, listWebsites);
    }
}
