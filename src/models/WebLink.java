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
public class WebLink {
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
}
