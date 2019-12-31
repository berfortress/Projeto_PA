/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author fabio
 */
public class WebLink implements Originator {
    private Link link;
    private Website website;
    
    public WebLink(Link link, Website website){
        this.link = link;
        this.website = website;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public void setWebsite(Website website) {
        this.website = website;
    }

    public Link getLink() {
        return link;
    }

    public Website getWebsite() {
        return website;
    }

    @Override
    public String toString() {
        return link.toString() + "\n" + website.toString();
    }

    @Override
    public Memento createMemento() {
        return new MementoWebSite(link, website);
    }

    @Override
    public void setMemento(Memento savedState) {
        link = ((MementoWebSite)savedState).MementoLink();
        website = ((MementoWebSite)savedState).MementoWebsite();
    }
    
    private class MementoWebSite implements Memento {
        private Link mementoLink;
        private Website mementoWebsite;

        public MementoWebSite(Link mementoLink, Website mementoWebsite) {
            this.mementoLink = new Link(mementoLink.getDescription(), mementoLink.getLink());
            this.mementoWebsite = new Website(mementoWebsite.getWebsiteName(), mementoWebsite.getURL());
        }
        
        @Override
        public Link MementoLink() {
            return mementoLink;
        }

        @Override
        public Website MementoWebsite() {
            return mementoWebsite;
        }
    
    }
}
