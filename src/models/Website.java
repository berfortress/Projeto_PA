/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bernardo e Fábio
 */
public class Website implements Serializable{
    private int id = 0;
    private static int count = 0;
    private String name;
    private String url;
    private List<Link> links;

    /**
     * Construtor da Página
     * @param pageTitleName
     * @param pageAddress 
     */
    public Website(String name, String url) {
        this.id = ++count;
        this.name = name;
        this.url = url;
        links = new ArrayList<>();
    }  

    /**
     * Construtor da Página
     */
    public Website() {
        this.id = ++count;
        this.name = "";
        this.url = "";
        links = new ArrayList<>();
    }
    
    /**
     * Método que adiciona links a uma página específica.
     * @param h 
     */
    public void addLink(Link h){
        links.add(h);
    }

    /**
     * Método que retorna uma lista de links de uma página.
     * @return 
     */
    public List<Link> getLinks() {
        return links;
    }

    /**
     * Retorna o nome da página.
     * @return 
     */
    public String getWebsiteName() {
        return name;
    }

    /**
     * Retorna o URL da página.
     * @return 
     */
    public String getURL() {
        return url;
    }

    /**
     * Altera o URL da página.
     * @param pageAddress 
     */
    public void setURL(String url) {
        this.url = url;
    }
    
    /**
     * Altera o nome da página.
     * @param pageTitleName 
     */
    public void setWebsiteName(String name) {
        this.name = name;
    }

    /**
     * Retorna o id da página.
     * @return 
     */
    public int getId() {
        return id;
    }

    /**
     * Descrição do Website.
     * @return 
     */
    @Override
    public String toString() {
        return id + " [" + getWebsiteName() + "] " + getURL();
    }
    
    /**
     * Equals
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
         if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Website other = (Website) obj;
        return this.id == other.getId();
    }
    
    
}
