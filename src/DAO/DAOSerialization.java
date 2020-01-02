/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Website;

/**
 *
 * @author fabio e bernardo
 */
public class DAOSerialization implements DAO {

    public static final String FILENAME = "webCrawlerSerialization.";
    private String basePath;

    private List<Website> inMemory;

    public DAOSerialization(String basePath) {
        this.basePath = basePath;
        inMemory = new ArrayList<>();
        loadFile();
    }

    /*
    Save imMemory list to file
     */
    private void saveFile() {
        try {
            FileOutputStream fileOut = new FileOutputStream(basePath + FILENAME);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(inMemory);
            out.close();

            fileOut.close();
        } catch (IOException e) {
            Logger.getLogger(DAOSerialization.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void loadFile() {
        try {
            File f = new File(FILENAME);
            if (!f.exists()) {
                inMemory = new ArrayList<>();
                return;
            }

            FileInputStream fileIn = new FileInputStream(FILENAME);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            inMemory = (List<Website>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException e) {
            Logger.getLogger(DAOSerialization.class.getName()).log(Level.SEVERE, null, e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAOSerialization.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void saveWC(Website wc) {
        int index = -1;
        for (int i = 0; i < inMemory.size(); i++) {
            if (inMemory.get(i).getId() == wc.getId()) {
                index = i;
            }
        }

        if (index != -1) {
            inMemory.set(index, wc);
        } else {
            inMemory.add(wc);
        }

        saveFile();
    }

    @Override
    public Website loadWC(String pageTitleName) {
        for (Website web : inMemory) {
            if (web.getWebsiteName().compareToIgnoreCase(pageTitleName) == 0) {
                return web;
            }
        }

        return null;
    }

    public List<Website> readAll() {
        return inMemory;
    }

    public boolean delete(String pageTitleName) {

        int index = -1;
        for (int i = 0; i < inMemory.size(); i++) {
            if (inMemory.get(i).getWebsiteName().compareToIgnoreCase(pageTitleName) == 0) {
                index = i;
            }
        }

        if (index != -1) {
            inMemory.remove(index);
            saveFile();
            return true;
        } else {
            return false;
        }
    }

    public List<Website> selectAll() {
        return inMemory;
    }

    public Website select(String pageTitleName) {
        for (Website web : inMemory) {
            if (web.getWebsiteName().equals(pageTitleName)) {
                return web;
            }
        }
        return null;
    }

    public boolean insert(Website web) {
        for (Website w : inMemory) {
            if (w.getWebsiteName().equals(web.getWebsiteName())) {
                return false;
            }
        }

        inMemory.add(web);
        saveFile();
        return true;
    }

    public boolean remove(String pageTitleName) {
        for (Website w : inMemory) {
            if (w.getWebsiteName().equals(pageTitleName)) {
                inMemory.remove(w);
                return true;
            }
        }
        return false;
    }

//    public boolean updateWebsite() {
//        for (Website w : inMemory) {
//            if (w.getStudentCode().equals(studentCode)) {
//                w.setStudentGrade(newGrade);
//                return true;
//            }
//        }
//        return false;
//    }
}
