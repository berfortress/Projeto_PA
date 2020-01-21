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
import java.util.logging.Logger;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import models.Website;
import webCrawler.Automatic;
import webCrawler.WebCrawler;

/**
 *
 * @author fabio e bernardo
 */
public class AutomaticFX extends AnchorPane {

    public int numberPages = 0;
    public static Label results;
    public static String resultsString;

    private Group root;
    private BorderPane outerBorderPane;
    private GridPane tabPane;
    private ImageView img = Standards.imagem_Background;
    private List<Website> websites;
    private int count = 0;

    /**
     * Construtor da classe AutomaticFX
     */
    public AutomaticFX() {
        websites = new ArrayList<>();
        root = new Group();
        outerBorderPane = new BorderPane();
        outerBorderPane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        tabPane = new GridPane();

        tabPane.getChildren().addAll(addGridPane());//All tabs have to be added here to be displayed
        outerBorderPane.setCenter(tabPane);
        root.getChildren().add(outerBorderPane);

        BorderPane border = new BorderPane();

        HBox hbox = Standards.addHBox("MODELO AUTOMÁTICO");
        border.setTop(hbox);
        border.setCenter(addGridPane());
        border.setBottom(addResults(border));

        VBox vb = new VBox(border);
        vb.setTranslateX(22);
        vb.setTranslateY(25);

        this.getChildren().addAll(img, vb);
    }

    /**
     * Método para adicionar resultados
     * @param border
     * @return 
     */
    private Node addResults(BorderPane border) {
        ScrollPane sp = new ScrollPane();
        sp.prefHeightProperty().bind(border.heightProperty());
        sp.prefWidthProperty().bind(border.widthProperty());
        sp.setMaxHeight(150);
        border.setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        results = new Label();
        sp.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        sp.setStyle("-fx-background: white;" + "-fx-padding: 10;" + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;" + "-fx-border-radius: 5;"
                + "-fx-border-color: black;");
        sp.setContent(results);
        return sp;
    }

    /**
     * Método que adiciona uma grelha
     * @return 
     */
    private Node addGridPane() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));
        grid.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));

        Label website = new Label("Enter Website:");
        grid.add(website, 0, 2);

        TextField websiteTextField = new TextField();
        grid.add(websiteTextField, 1, 2);

        final Text websiteActionTarget = new Text();
        grid.add(websiteActionTarget, 1, 3);
        websiteActionTarget.setFill(Color.FIREBRICK);
        websiteTextField.textProperty().addListener((observable) -> {
            if (websiteTextField.getText().isEmpty()) {
                websiteActionTarget.setText("You need to fill this field !");
            } else {
                websiteActionTarget.setText("");
            }
        });

        Label maxNumberOfSitesToVisitLabel = new Label("Number Websites");
        grid.add(maxNumberOfSitesToVisitLabel, 3, 2);

        TextField maxNumberOfSitesToVisit = new TextField();
        grid.add(maxNumberOfSitesToVisit, 4, 2);

        final Text maxWebsitesActionTarget = new Text();
        grid.add(maxWebsitesActionTarget, 4, 3);
        maxWebsitesActionTarget.setFill(Color.FIREBRICK);

        maxNumberOfSitesToVisit.textProperty().addListener((Observable observable) -> {
            if (maxNumberOfSitesToVisit.getText().isEmpty()) {
                maxWebsitesActionTarget.setText("You need to fill this field !");
            } else if (!maxNumberOfSitesToVisit.getText().matches("[0-9]+")) {
                maxWebsitesActionTarget.setText("No strings or negative numbers");
            } else {
                maxWebsitesActionTarget.setText("");
            }
        });
        
        count = 0;

        Button searchButton = new Button("Search");        
        grid.add(searchButton, 4, 4);
        
        Automatic webCrawler = new Automatic();

        searchButton.setOnAction((ActionEvent e) -> {
            resultsString = null;
            if (websiteTextField.getText().isEmpty()) {
                websiteActionTarget.setText("Please enter a website!");
            }
            if (maxNumberOfSitesToVisit.getText().isEmpty()) {
                maxWebsitesActionTarget.setText("Please enter a number!");
            }
            if (!maxNumberOfSitesToVisit.getText().isEmpty() && !websiteTextField.getText().isEmpty()) {
                try {
                    count = 1;
                    numberPages = Integer.parseInt(maxNumberOfSitesToVisit.getText());
                    webCrawler.setMaxPagesToSearch(numberPages);
                    webCrawler.search(websiteTextField.getText());
                    for(Website web : webCrawler.webSitesVisited()){
                        websites.add(web);
                    }
                } catch (IOException | WebsiteException | LinkException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        Button btnStats = new Button("Estatísticas");
        btnStats.setOnAction((ActionEvent event) -> {
            if(count == 1){
                this.getScene().setRoot(new StatisticsFX(websites, webCrawler));
            }
        });
        grid.add(btnStats, 5, 4);
        
        return grid;
    }

    /**
     * Método que retorna os resultados
     * @return 
     */
    public Label getResults() {
        return results;
    }

    /**
     * Método que escreve os resultados
     * @param string 
     */
    public static void setResults(String string) {
        if (resultsString == null) {
            resultsString = string;
        } else {
            resultsString += "\n" + string;
        }
        results.setText(resultsString);
    }

    /**
     * Método que retorna o número de páginas
     * @return 
     */
    public int getMaxPagesToSearch() {
        return numberPages;
    }
}
