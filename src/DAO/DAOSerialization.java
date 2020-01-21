/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Website;

/**
 *
 * @author fabio e bernardo
 */
public class DAOSerialization implements DAO {

    public static final String FILENAME = "webCrawlerSerialization.dat";
    private String basePath;

    private HashSet<Website> inMemory;

    /**
     * Construtor da classe DAOSerialization
     * @param basePath 
     */
    public DAOSerialization(String basePath) {
        this.basePath = basePath;
        inMemory = new HashSet<>();
        loadFile();
    }


    /**
     * Método que guarda o ficheiro em memória
     */
    private void saveFile() {
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(basePath + FILENAME);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(inMemory);
            out.close();

            fileOut.close();
        } catch (FileNotFoundException e) {
            Logger.getLogger(DAOSerialization.class.getName()).log(Level.SEVERE, null, e);

        } catch (IOException e) {
            Logger.getLogger(DAOSerialization.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Método que lê
     */
    private void loadFile() {
        FileInputStream fileIn = null;
        try {
            fileIn = new FileInputStream(basePath + FILENAME);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            this.inMemory = (HashSet<Website>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException e) {
            return ; 
        } catch (ClassNotFoundException ex) {
            return ;
        }
    }

    /**
     * Método guarda website
     * @param wc 
     */
    @Override
    public void saveWC(Website wc) {
        if (inMemory.contains(wc)) {
            return;
        }

        inMemory.add(wc);

        saveFile();
    }

     /**
     * Método que retorna website dado um webSiteName
     * @param webSiteName
     * @return 
     */
    @Override
    public Website loadWC(String webSiteName) {
        for (Website web : inMemory) {
            if (web.getWebsiteName().compareToIgnoreCase(webSiteName) == 0) {
                return web;
            }
        }

        return null;
    }

    /**
     * Método que seleciona todos os websites
     * @return 
     */
    public HashSet<Website> selectAll() {
        return inMemory;
    }

    /**
     * Método que retorna um website dada um webSiteName
     * @param webSiteName
     * @return 
     */
    public Website select(String webSiteName) {
        for (Website web : inMemory) {
            if (web.getWebsiteName().equals(webSiteName)) {
                return web;
            }
        }
        return null;
    }

    /**
     * Méotodo que remove da lista dado um webSiteName
     * @param webSiteName
     * @return 
     */
    public boolean remove(String webSiteName) {
        for (Website w : inMemory) {
            if (w.getWebsiteName().equals(webSiteName)) {
                inMemory.remove(w);
                return true;
            }
        }
        return false;
    }
}
