/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logger;

import java.io.*;
import java.time.LocalDate;
import java.util.Date;
//import models.Iterative;
import models.Website;
import webCrawler.WebCrawler;

/**
 *
 * @author fabio e bernardo
 */
public final class Logger {

    private static Logger instance = new Logger();
    LocalDate timestamp = LocalDate.now();
    private PrintWriter writer;
    private final String logFile = "logger.txt";

    /**
     * Construtor da classe Logger
     */
    private Logger() {
        try {
            FileWriter fw = new FileWriter(logFile);
            writer = new PrintWriter(fw, true);
        } catch (IOException e) {
        }
    }

    /**
     * Método que retorna uma instância de Logger
     * @return 
     */
    public static Logger getInstance() {
        return instance;
    }

    /**
     * Método que escreve no ficheiro
     * @param atualWeb
     * @param origWeb
     * @param web 
     */
    public void writeToLog(Website atualWeb, Website origWeb, WebCrawler web) {
        writer.println(new Date().toString() + " | <" + atualWeb.getWebsiteName()+ ">" 
                + " | <" + atualWeb.getURL() + ">"
                + " | <" + origWeb.getWebsiteName() + ">"
                + " | <" + web.getAdjacentsElem(atualWeb) + ">");
    }
    
    /**
     * Método que escreve no ficheiro
     * @param object
     * @param s 
     */
    public void writeToLog(Object object, String s) {
        writer.println(new Date().toString() + " | Class: " + object.getClass().getName() + " | action: " + s);
    }

}