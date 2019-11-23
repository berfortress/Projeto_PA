/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler;

import java.util.Objects;

/**
 *
 * @author bernardo
 */
public class Hyperlinks {
    private int id = 0;
    private static int count = 0; 
    private final double distance;
    private String name;
    private String link;
    private PageTitle page;

    public Hyperlinks(String name,String link) {
        this.id = ++count;
        this.name = name;
        this.link = link;
        this.distance = 1;
        this.page = null;
    }

    public Hyperlinks() {
        this.id = ++count;
        this.name = "";
        this.link = "";
        this.distance = 1;
        this.page = null;
    }

    public PageTitle getPage() {
        return page;
    }

    public void setPage(PageTitle page) {
        this.page = page;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDistance() {
        return distance;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ID:" + id;
    }

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
    
    public boolean equals1(Object obj) {
       if (obj == this) {
            return true;
        }
        if (!(obj instanceof Hyperlinks)) {
            return false;
        }
        Hyperlinks other = (Hyperlinks) obj;
        return this.name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return link.hashCode();
    }
    
    
}
