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
import models.PageTitle;

/**
 *
 * @author fabio
 */
public class wcJsonDAO implements wcDAO{
    
    private String basePath;
    private static final String fileName = "webCrawler.json";

    public wcJsonDAO(String basePath) {
        this.basePath = basePath;
    }

    private HashSet<PageTitle> selectAll() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(basePath + fileName));
            Gson gson = new GsonBuilder().create();

            HashSet<PageTitle> list = gson.fromJson(br,
                    new TypeToken<HashSet<PageTitle>>() {
                    }.getType());
            return list;

        } catch (IOException ex) {
            return new HashSet<>(); 
        }
      
    }

    public void saveWC(PageTitle wc) {
        FileWriter writer = null;
        try {
            Gson gson = new GsonBuilder().create();
            HashSet<PageTitle> list = selectAll();
            list.add(wc);
            writer = new FileWriter(basePath + fileName);
            gson.toJson(list, writer);
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(wcJsonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PageTitle loadWC(String pageTitleName) {
        HashSet<PageTitle> list = selectAll();
        for (PageTitle p : list) {   
            if (p.getPageTitleName().equalsIgnoreCase(pageTitleName)) {
                return p;
            }
        }
        return null;
    }
}
