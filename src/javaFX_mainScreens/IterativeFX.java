/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaFX_mainScreens;

import exceptions.LinkException;
import exceptions.WebsiteException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.Link;
import models.Website;
import webCrawler.Iterative;

/**
 *
 * @author fabio e bernardo
 */
public class IterativeFX extends AnchorPane {

    public static Label results;
    public static String resultsString;
    private ImageView img = Standards.imagem_Background;
    private List<Website> controll;

    /**
     * Construtor da Classe IterateFX
     */
    public IterativeFX() {

        VBox vbox = new VBox();

        HBox hbox = Standards.addHBox("MODELO ITERATIVO");
        vbox.getChildren().addAll(hbox, addGridPane());
        vbox.setSpacing(20);
        vbox.setTranslateY(20);
        vbox.setTranslateX(20);
        controll = new ArrayList<>();

        this.getChildren().addAll(img, vbox);
    }

    /**
     * Método que retorna uma VBox
     *
     * @return
     */
    private VBox addGridPane() {
        Text txt = new Text();
        Text websiteActionTarget = new Text();
        txt.equals(Standards.txtThings(txt));
        VBox vbox = new VBox();
        Iterative it = new Iterative();
        final ObservableList<Website> list = FXCollections.observableArrayList();
        final ListView<Website> listWebsites = new ListView<>(list);

        setMinAndMax(listWebsites);

        Label website = new Label("Enter Website:");
        TextField websiteTextField = new TextField();

        websiteActionTarget.setFill(Color.FIREBRICK);
        websiteTextField.textProperty().addListener((observable) -> {
            if (websiteTextField.getText().isEmpty()) {
                websiteActionTarget.setText("You need to fill this field !");
            } else {
                websiteActionTarget.setText("");
            }
        });

        Button undoButton = UndoButton(it, listWebsites, websiteActionTarget, txt);
        Button searchButton = searchButton(it, websiteTextField, websiteActionTarget, txt, listWebsites);

        Button statsButton = statsButton(it, listWebsites);
        HBox hbox = new HBox(undoButton, searchButton, statsButton);
        HBoxVBoxDetails(website, websiteTextField, websiteActionTarget, txt, listWebsites, vbox, hbox);

        return vbox;
    }

    private Button statsButton(Iterative it, final ListView<Website> listWebsites) {
        Button statsButton = new Button("Estatísticas");
        statsButton.setBackground(new Background(new BackgroundFill(Color.FIREBRICK, CornerRadii.EMPTY, Insets.EMPTY)));
        statsButton.setTextFill(Color.WHITE);
        statsButton.setOnAction(e -> {
            if (!it.mementoIsEmpty()) {
                listWebsites.getScene().setRoot(new StatisticsFX(controll, it));
            }
        });
        return statsButton;
    }

    public Button searchButton(Iterative it, TextField websiteTextField, final Text websiteActionTarget, Text txt, final ListView<Website> listWebsites) {
        Button searchButton = new Button("Pesquisar");
        searchButton.setOnAction(e -> {
            if (it.mementoIsEmpty()) {
                resultsString = null;
                if (websiteTextField.getText().isEmpty()) {
                    websiteActionTarget.setText("Please enter a website!");
                } else {
                    firstSearch(it, websiteTextField, txt, listWebsites);
                }
            } else {
                Website w = listWebsites.getSelectionModel().getSelectedItem();
                textOptions(w, txt);
                iterativeSearch(w, it, websiteTextField, txt, listWebsites);
            }
        });
        return searchButton;
    }

    private void iterativeSearch(Website w, Iterative it, TextField websiteTextField, Text txt, final ListView<Website> listWebsites) {
        try {
            if (w != null) {
                listWebsites.getItems().clear();
                txt.equals(Standards.txtThings(txt));
                List<Website> webList = new ArrayList<>();
                for (Website web : controll) {
                    webList.add(web);
                }

                int size = webList.size();
                Website website = it.getWebsite(controll, w.getURL());
                int position = it.getWebsitePosition(website);
                System.out.println(controll);
                try {
                    it.recursiveSearch(position, website.getURL());
                    webList = it.webSitesVisited();
                    controll = controll;
                    int i = 0;
                    while (i < size) {
                        webList.remove(0);
                        i++;
                    }
                    for (Website web : webList) {
                        if (!web.getURL().equals(w.getURL())) {
                            //controll.add(web);
                            listWebsites.getItems().add(listWebsites.getItems().size(), web);
                            listWebsites.scrollTo(listWebsites.getItems().size() - 1);
                        }
                    }
                    webList.clear();
                } catch (IOException | WebsiteException ex) {
                    java.util.logging.Logger.getLogger(IterativeFX.class.getName()).log(Level.SEVERE, null, ex);
                }
                it.saveState(w.getLinks(), w);
            }
        } catch (NullPointerException e) {
            System.out.println("Não selecionou um website");
        }
    }

    private void firstSearch(Iterative it, TextField websiteTextField, Text txt, final ListView<Website> listWebsites) {
        try {
            it.search(websiteTextField.getText());
        } catch (IOException | WebsiteException | LinkException ex) {
            java.util.logging.Logger.getLogger(IterativeFX.class.getName()).log(Level.SEVERE, null, ex);
        }
        Website webSite = it.webSitesVisited().get(0);
        List<Link> linksList = webSite.getLinks();
        txt.setText(webSite.toString());
        txt.equals(Standards.txtThings(txt));
        for (Website w : it.webSitesVisited()) {
            if (!w.getURL().equals(webSite.getURL())) {
                listWebsites.getItems().add(listWebsites.getItems().size(), w);
                listWebsites.scrollTo(listWebsites.getItems().size() - 1);
            }
        }
        controll = it.webSitesVisited();
        it.saveState(linksList, webSite); // Até aqui está certo
    }

    public Button UndoButton(Iterative it, final ListView<Website> listWebsites, final Text websiteActionTarget, Text txt) {
        Button undoButton = new Button("Undo");
        undoButton.setOnAction(e -> {
            if (it.mementoIsEmpty()) {
                listWebsites.getScene().setRoot(new SecondWindow());
            } else {
                websiteActionTarget.setText("");
                it.undo();
                Website web = it.getWebLink();

                listWebsites.getItems().clear();

                List<Website> webs = it.getAdjacentsElem(web);
                it.bubbleSortWeb(webs);
                txt.setText(web.toString());

                for (Website w : webs) {
                    listWebsites.getItems().add(listWebsites.getItems().size(), w);
                    listWebsites.scrollTo(listWebsites.getItems().size() - 1);
                }
            }
        });
        return undoButton;
    }

    private void HBoxVBoxDetails(Label website, TextField websiteTextField, final Text websiteActionTarget, Text txt, final ListView<Website> listWebsites, VBox vbox, HBox hbox) {
        HBox hb = new HBox(website, websiteTextField);
        hb.setSpacing(10);

        VBox vb1 = new VBox(hb, websiteActionTarget);
        vb1.setSpacing(20);

        VBox vb2 = new VBox(txt, listWebsites);
        vb2.setSpacing(5);

        vbox.getChildren().addAll(vb1, hbox, vb2);
        vbox.setSpacing(10);

        hbox.setSpacing(20);
        hbox.setTranslateY(-10);
    }

    private void setMinAndMax(final ListView<Website> listWebsites) {
        listWebsites.setMinWidth(600);
        listWebsites.setMaxHeight(200);
    }

    private void textOptions(Website w, Text txt) {
        try {
            txt.setText(w.toString());
            txt.setFill(Color.WHITESMOKE);

            txt.setStyle("-fx-font-weight: bold");
            txt.setFont(Font.font("Verdana", 11));
        } catch (NullPointerException e) {
            System.out.println("Não selecionou um website");
        }
    }
}
