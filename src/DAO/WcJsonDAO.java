/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Website;

/**
 *
 * @author fabio
 */
public class WcJsonDAO implements WcDAO{
    
    private String basePath;
    private static final String fileName = "webCrawlerJson.json";

    public WcJsonDAO(String basePath) {
        this.basePath = basePath;
    }

    private HashSet<Website> selectAll() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(basePath + fileName));
            Gson gson = new GsonBuilder().create();

            HashSet<Website> list = gson.fromJson(br,
                    new TypeToken<HashSet<Website>>() {
                    }.getType());
            return list;

        } catch (IOException ex) {
            return new HashSet<>(); 
        }
      
    }

    public void saveWC(Website wc) {
        FileWriter writer = null;
        try {
            Gson gson = new GsonBuilder().create();
            HashSet<Website> list = selectAll();
            list.add(wc);
            writer = new FileWriter(basePath + fileName);
            gson.toJson(list, writer);
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(WcJsonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Website loadWC(String pageTitleName) {
        HashSet<Website> list = selectAll();
        for (Website p : list) {   
            if (p.getWebsiteName().equalsIgnoreCase(pageTitleName)) {
                return p;
            }
        }
        return null;
    }
}
