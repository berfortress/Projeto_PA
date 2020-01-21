/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author fabio e bernardo
 */
public class Website implements Serializable {

    private int id = 0;
    private static int count = 0;
    private String name;
    private String url;
    private List<Link> links;

    /**
     * Construtor da classe Website
     *
     * @param name
     * @param url
     */
    public Website(String name, String url) {
        this.id = ++count;
        this.name = name;
        this.url = url;
        this.links = new ArrayList<>();
    }

    /**
     * Construtor da classe Website
     *
     * @param web
     */
    public Website(Website web) {
        this.id = web.getId();
        this.name = web.getWebsiteName();
        this.url = web.getURL();
        this.links = web.getLinks();
    }

    /**
     * Construtor da classe Website
     */
    public Website() {
        this.id = ++count;
        this.name = "";
        this.url = "";
        this.links = new ArrayList<>();
    }

    /**
     * Método que coloca link ao website
     *
     * @param h
     */
    public void addLink(Link h) {
        links.add(h);
    }

    public void removeLink(Link h) {
        links.remove(h);
    }

    /**
     * Método que retorna o link do website
     *
     * @return
     */
    public List<Link> getLinks() {
        return links;
    }

    public String getLinkDescription(int id) {
        return links.get(id).getLink();
    }

    public Link getLink(int id) {
        return links.get(id);
    }
    
    public int getLinksSize(){
        return links.size();
    }

    /**
     * Retorna o nome do website.
     *
     * @return
     */
    public String getWebsiteName() {
        return name;
    }

    /**
     * Retorna o URL do website
     *
     * @return
     */
    public String getURL() {
        return url;
    }

    /**
     * Altera o URL do website
     *
     * @param url
     */
    public void setURL(String url) {
        this.url = url;
    }

    /**
     * Altera o nome do website.
     *
     * @param name
     */
    public void setWebsiteName(String name) {
        this.name = name;
    }

    /**
     * Retorna o id do website.
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Descrição do website.
     *
     * @return
     */
    @Override
    public String toString() {
        return id + " [" + getWebsiteName() + "] " + getURL();
    }

    /**
     * Equals
     *
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

    /**
     * HashCode
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.id;
        hash = 89 * hash + Objects.hashCode(this.name);
        hash = 89 * hash + Objects.hashCode(this.url);
        return hash;
    }
}
