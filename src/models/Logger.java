/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import webcrawler.WebCrawler;

/**
 *
 * @author fabio e bernardo
 */

public final class Logger {
    private static Logger instance = new Logger();
    LocalDate timestamp = LocalDate.now();
    private String LOGGERFILE;
    private PrintStream printStream;

    private Logger() {

    }

    public static Logger getInstance() {
        return instance;
    }

    public boolean connect() {
        if (printStream == null) {
            try {
                printStream = new PrintStream(new FileOutputStream(LOGGERFILE), true);
            } catch (FileNotFoundException ex) {
                printStream = null;
                return false;

            }
            return true;
        }
        return true;
    }

    public void writeToLog(Website atualWeb, Website origWeb, WebCrawler web) {
        connect();
        printStream.println(new Date().toString() + " | Título da Página: " + atualWeb.getWebsiteName() + " | Hendereço http: " + atualWeb.getURL() + 
                            "| Título da Página de Origem: " + origWeb.getWebsiteName() + 
                            "| Número de hiperligações da Página: " + web.getAdjacentsElem(atualWeb));
        //System.out.println("Classe: " + object.getClass().getName() + " ação: " + s);
    }

    public void setCliente_id(String cliente_id) {
        LOGGERFILE = "base_loggers/"
                + "logdata" + timestamp.format(DateTimeFormatter.ISO_DATE)
                + "cliente" + cliente_id
                + ".txt";
    }

}
