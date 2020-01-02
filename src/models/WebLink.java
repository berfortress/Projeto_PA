/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fabio e bernardo
 */

public class WebLink implements Originator {
    private List<Link> links;
    private List<Website> websites;
    private Website website;
    
    public WebLink(Link link, Website website){
        this.links = new ArrayList<>();
        this.websites = new ArrayList<>();
        this.website = website;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
    
    public void setWebsites(List<Website> websites) {
        this.websites = new ArrayList<>(websites);
    }

    public void setWebsite(Website website) {
        this.website = website;
    }

    public List<Link> getLink() {
        return links;
    }

    public Website getWebsite() {
        return website;
    }

    public List<Website> getWebsites() {
        return websites;
    }

    @Override
    public String toString() {
        String str = "[PÁGINA ATUAL] " + website.toString() + "\n--------Páginas a que pode aceder--------\n";
        for(Website w : websites){
            str += w.toString() + "\n";
        }
        return str;
    }

    @Override
    public Memento createMemento() {
        return new MementoWebSite(website);
    }

    @Override
    public void setMemento(Memento savedState) {
        links = ((MementoWebSite)savedState).MementoLinks();
        websites = ((MementoWebSite)savedState).MementoWebsites(); 
        website = ((MementoWebSite)savedState).MementoWebsite();
    }
    
    private class MementoWebSite implements Memento {
        private List<Link> mementoLinks;
        private List<Website> mementoWebsites;
        private Website mementoWebsite;

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
