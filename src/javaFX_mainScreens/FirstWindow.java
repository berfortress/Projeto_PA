/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaFX_mainScreens;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javafx.event.ActionEvent;
import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

/**
 *
 * @author fabio e bernardo
 */
public class FirstWindow extends AnchorPane {

    ImageView imagem_Background = Standards.imagem_Background;
    Button btn_start;
    Pane pane_header;
    Pane pane_presentation;

    /**
     * Construtor da classe FirstWindow
     */
    public FirstWindow() {
        this.setStyle("-fx-background-color: black;");
        btn_start = Standards.buttonFeatures("COMEÇAR", 18, 150, 50, 485, 370);
        pane_header = header();
        pane_presentation = presentation();

        btn_start.setOnAction((ActionEvent event) -> {
            pane_header.getScene().setRoot(new SecondWindow());
        });

        this.getChildren().addAll(imagem_Background, pane_header, pane_presentation, btn_start);
    }

    /**
     * Metodo de criação do cabecalho
     * @return 
     */
    public Pane header() {
        int fontSize = 18;
        int altTxt = 45;
        Pane pane = new Pane();
        String pattern = "d/MM/yyyy HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        Date today = Calendar.getInstance().getTime();

        Text hour = Standards.defaultText(
                (df.format(today)), fontSize, 15, altTxt);

        Text students = Standards.defaultText(
                ("PROJETO PROGRAMAÇÃO AVANÇADA"), fontSize, 300, altTxt);

        Text version = Standards.defaultText(
                ("2ª Fase "), fontSize, 900, altTxt);

        pane.getChildren().addAll(hour,students,version);
        return pane;

    }
    
    /**
     * Classe que cria o dialogo inicial da apresentação
     * @return 
     */
    public Pane presentation() {
        int sizeTextSe = 20;
        int sizeTxtTit = 35;

        Pane pane = new Pane();
        pane.setPrefWidth(800);
        pane.setPrefHeight(300);
        pane.setLayoutY(150);
        pane.setStyle("-fx-background-color:  " + Standards.imagem_Background + ";");
        pane.setOpacity(0.8);

        Text name = Standards.defaultText("WEB CRAWLER", sizeTxtTit, 700, 200);
        name.setStyle("-fx-font-weight: bold");

        Text proj_nome = Standards.defaultText("BERNARDO FORTALEZA & FÁBIO ESPÍRITO SANTO", sizeTextSe, 700, 200);
        proj_nome.setStyle("-fx-font-weight: bold");

        Text prof_nome = Standards.defaultText("DOCENTE: BRUNO SILVA", sizeTextSe, 700, 200);
        prof_nome.setStyle("-fx-font-weight: bold");

        //Linhas divisorias
        Line line1 = Standards.lineCrt(-158, 40, 400, 50, -32, 40, 5.0);
        Line line2 = Standards.lineCrt(-158, 40, 800, 50, -32, 40, 5.0);

        HBox hBox_nome_projeto = new HBox(5, line1, name, line2);
        hBox_nome_projeto.setPadding(new Insets(100, USE_COMPUTED_SIZE, 10, 10));
        hBox_nome_projeto.setAlignment(Pos.CENTER);

        VBox vBox_apresentacao = new VBox(10, hBox_nome_projeto, proj_nome, prof_nome);
        vBox_apresentacao.setAlignment(Pos.CENTER);
        vBox_apresentacao.setTranslateY(-80);
        vBox_apresentacao.setTranslateX(30);

        pane.getChildren().addAll(vBox_apresentacao);
        return pane;
    }
}
