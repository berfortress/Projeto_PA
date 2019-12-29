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
public class Link {
    private int id = 0;
    private static int count = 0; 
    private final double distance;
    private String description;
    private String link;
    
    /**
     * Construtor da classe Hyperlinks
     * @param description
     * @param link 
     */

    public Link(String description,String link) {
        this.id = ++count;
        this.description = description;
        this.link = link;
        this.distance = 1;
    }

    
    /**
     * Construtor da classe Hyperlinks
     */
    public Link() {
        this.id = ++count;
        this.description = "";
        this.link = "";
        this.distance = 1;
    }


    /**
     * Retorna a descrição do link
     * @return 
     */
    public String getDescription() {
        return description;
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
    public void setDescription(String description) {
        this.description = description;
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
        //return "ID "  + getId();
        return "ID" + getId() + " " + getLink();
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
        if (!(obj instanceof Link)) {
            return false;
        }
        Link other = (Link) obj;
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
