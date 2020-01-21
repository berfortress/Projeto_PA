/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memento;

import java.util.ArrayList;
import java.util.List;
import models.Link;
import models.Website;

/**
 *
 * @author fabio e bernardo
 */

public class WebLink implements Originator {
    private List<Link> links;
    private Website website;
    
    /**
     * Construtor da classe Weblink
     * @param link
     * @param website 
     */
    public WebLink(Link link, Website website){
        this.links = new ArrayList<>();
        this.website = website;
    }

    /**
     * Método que define uma lista de links
     * @param links 
     */
    public void setLinks(List<Link> links) {
        this.links = links;
    }
    
    /**
     * Método que altera o website
     * @param website 
     */
    public void setWebsite(Website website) {
        this.website = website;
    }

    /**
     * Método que retorna uma lista de links
     * @return 
     */
    public List<Link> getLink() {
        return links;
    }

    /**
     * Método que retorna o website
     * @return 
     */
    public Website getWebsite() {
        return website;
    }


    /**
     * Método Descritivo
     * @return 
     */
    @Override
    public String toString() {
        String str = "[PÁGINA ATUAL] " + website.toString() + "\n--------Páginas a que pode aceder--------\n";
        for(Link w : links){
            str += w.toString() + "\n";
        }
        return str;
    }
    
    /**
     * Método que cria uma instância do inner classe MementoWebSite
     * @return 
     */
    @Override
    public Memento createMemento() {
        return new MementoWebSite(website);
    }
    
    /**
     * Método que altera o memento
     * @param savedState 
     */
    @Override
    public void setMemento(Memento savedState) {
        links = ((MementoWebSite)savedState).MementoLinks();
        website = ((MementoWebSite)savedState).MementoWebsite();
    }
    
    private class MementoWebSite implements Memento {
        private final List<Link> mementoLinks;
        private final List<Website> mementoWebsites;
        private final Website mementoWebsite;

        public MementoWebSite(Website mementoWebsite) {
            this.mementoLinks = new ArrayList<>();
            this.mementoWebsites = new ArrayList<>();
            this.mementoWebsite = new Website(mementoWebsite);
        }
        
        @Override
        public List<Link> MementoLinks() {
            return mementoLinks;
        }

        @Override
        public Website MementoWebsite() {
            return mementoWebsite;
        }
        
        @Override
        public List<Website> MementoWebsites() {
            return mementoWebsites;
        } 
    }
}
