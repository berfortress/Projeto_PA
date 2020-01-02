/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Website;

/**
 *
 * @author fabio
 */
public class DAOFile implements DAO{
    
    private String basePath;

    public DAOFile(String basePath) {
        this.basePath = basePath;
    }

    @Override
    public void saveWC(Website wc) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(basePath + wc.getWebsiteName());
            fw.write("Nome Da Página: " + wc.getWebsiteName());
            fw.write("\nURL: " + wc.getURL());
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(DAOFile.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(DAOFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public Website loadWC(String pageTitleName) {
        Scanner sc = null;
        try {
            sc = new Scanner(new File(basePath + pageTitleName + ".Webcrawler"));
            Website wc = new Website();
            String rTitle = sc.nextLine();
            String rURL = sc.nextLine();
            if (rTitle.startsWith("Nome Da Página: ")) {
                wc.setWebsiteName(rTitle.substring("Nome Da Página: ".length()));
            } else {
                return null;
            }
            if (rURL.startsWith("URL: ")) {
                wc.setURL(rURL.substring("URL: ".length()));
            } else {
                return null;
            }
            return wc;
        } catch (FileNotFoundException ex) {

            return null;
        }

    }
}
