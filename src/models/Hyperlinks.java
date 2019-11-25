/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Objects;

/**
 *
 * @author Bernardo e Fábio
 */
public class Hyperlinks {
    private int id = 0;
    private static int count = 0; 
    private final double distance;
    private String name;
    private String link;
    private PageTitle page;
    
    /**
     * Construtor da classe Hyperlinks
     * @param name
     * @param link 
     */

    public Hyperlinks(String name,String link) {
        this.id = ++count;
        this.name = name;
        this.link = link;
        this.distance = 1;
        this.page = null;
    }

    
    /**
     * Construtor da classe Hyperlinks
     */
    public Hyperlinks() {
        this.id = ++count;
        this.name = "";
        this.link = "";
        this.distance = 1;
        this.page = null;
    }

    /**
     * Retorna a página.
     * @return 
     */
    public PageTitle getPage() {
        return page;
    }

    /**
     * Altera a página.
     * @param page 
     */
    public void setPage(PageTitle page) {
        this.page = page;
    }

    /**
     * Retorna a descrição do link
     * @return 
     */
    public String getName() {
        return name;
    }

    /**
     * Retorna o link.
     * @return 
     */
    public String getLink() {
        return link;
    }

    /**
     * Altera o link.
     * @param link 
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Altera a descrição do link
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retorna a distância (Neste caso é 1)
     * @return 
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Retorna o id do link
     * @return 
     */
    public int getId() {
        return id;
    }

    /**
     * Descrição do link
     * @return 
     */
    @Override
    public String toString() {
        return "ID "  + getId();
    }

    /**
     * Equals
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
       if (obj == this) {
            return true;
        }
        if (!(obj instanceof Hyperlinks)) {
            return false;
        }
        Hyperlinks other = (Hyperlinks) obj;
        return this.link.equals(other.link);
    }
    
    /**
     * HashCode
     * @return 
     */
    @Override
    public int hashCode() {
        return link.hashCode();
    } 
}
