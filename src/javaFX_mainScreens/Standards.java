/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaFX_mainScreens;

import java.io.IOException;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author fabio e bernardo
 */
public class Standards {

    public final static ImageView imagem_Background = Standards.background();
    public final static String AMARELO = "#FBA100";
    public final static String BRANCO = "#ffffff";
    public final static String VERDE = "#6bbaa7";
    public final static String VERDE_CLARO = "#C7D8C6";
    public final static String PRETO = "#010000";
    public final static String TEXTO_FONTE = "Verdana";
    public final static Color TEXTO_COR_BRANCA = Color.WHITESMOKE;
    public final static Color TEXTO_COR_PRETA = Color.BLACK;
    public final static String log_sistema_iniciado = "Programa Inicializado";
    public final static String log_sistema_finalizado = "Programa Finalizado";

    public final static String log_sistema_automatico = "Modelo Automático Escolhido";
    public final static String log_sistema_iterative = "Modelo Iterativo Escolhido";

    public final static String log_Exception = "Exception";

    /**
     * Construtor da classe Standards
     */
    public Standards() {
    }

    /**
     * Método para definir imagem de fundo
     *
     * @return
     */
    public static ImageView background() {
        Image i = new Image("javaFX_images/background.png");
        ImageView imagemBackground = new ImageView();
        imagemBackground.setImage(i);
        imagemBackground.setFitWidth(800);
        imagemBackground.setFitHeight(600);
        imagemBackground.setOpacity(0.5);
        return imagemBackground;
    }

    /**
     * Método para definir texto padrao
     *
     * @param texto
     * @param tSize
     * @param hPos
     * @param vPos
     * @return
     */
    public static Text defaultText(String texto, int tSize, double hPos, double vPos) {
        Text t = new Text();
        t.setText(texto);
        t.setFont(Font.font(TEXTO_FONTE, tSize));
        t.setFill(TEXTO_COR_BRANCA);
        t.setLayoutX(hPos);
        t.setLayoutY(vPos);

        return t;
    }

    /**
     * Método para definir características do botão
     *
     * @param texto
     * @param tSize
     * @param hSize
     * @param vSize
     * @param hPos
     * @param vPos
     * @return
     */
    public static Button buttonFeatures(String texto, int tSize, int hSize, int vSize, double hPos, double vPos) {
        Button button = new Button(texto);
        button.setPrefSize(hSize, vSize);
        button.setStyle("-fx-background-color:" + Standards.PRETO);
        button.setTextFill(TEXTO_COR_BRANCA);
        button.setLayoutX(hPos);
        button.setLayoutY(vPos);
        button.setFont(Font.font(TEXTO_FONTE, tSize));
        button.setCursor(Cursor.HAND);
        return button;
    }

    /**
     * Método para criar linhas divisórias
     *
     * @param endX
     * @param endY
     * @param layoutX
     * @param layoutY
     * @param startX
     * @param startY
     * @param stroke
     * @return
     */
    public static Line lineCrt(int endX, int endY, int layoutX, int layoutY, int startX, int startY, double stroke) {
        Line line = new Line();
        line.setEndX(endX);
        line.setEndY(endY);
        line.setLayoutX(layoutX);
        line.setLayoutY(layoutY);
        line.setStartX(startX);
        line.setStartY(startY);
        line.setStroke(TEXTO_COR_BRANCA);
        line.setStrokeWidth(stroke);
        return line;
    }

    /**
     * Método que cria uma HBox
     *
     * @param text
     * @return
     */
    public static HBox addHBox(String text) {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: white;");
        Text scenetitle = new Text(text);
        hbox.getChildren().addAll(scenetitle);
        return hbox;
    }

    /**
     * Método para alterar características do texto
     *
     * @param txt
     * @return
     */
    public static Text txtThings(Text txt) {
        txt.setFill(Color.WHITESMOKE);
        txt.setStyle("-fx-font-weight: bold");
        txt.setFont(Font.font("Verdana", 11));
        return txt;
    }
}
