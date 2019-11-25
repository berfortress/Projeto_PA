/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bernardo
 */
public class PageTitle implements Serializable{
    private int id = 0;
    private static int count = 0;
    private String pageTitleName;
    private String pageAddress;
    private List<Hyperlinks> hyper;

    public PageTitle(String pageTitleName, String pageAddress) {
        this.id = ++count;
        this.pageTitleName = pageTitleName;
        this.pageAddress = pageAddress;
        hyper = new ArrayList<>();
    }  

    public PageTitle() {
        this.id = ++count;
        this.pageTitleName = "";
        this.pageAddress = "";
        hyper = new ArrayList<>();
    }
    
    public void addHyperlink(Hyperlinks h){
        hyper.add(h);
    }

    public List<Hyperlinks> getHyper() {
        return hyper;
    }

    public String getPageTitleName() {
        return pageTitleName;
    }

    public String getPageAddress() {
        return pageAddress;
    }

    public void setPageAddress(String pageAddress) {
        this.pageAddress = pageAddress;
    }
    

    public void setPageTitleName(String pageTitleName) {
        this.pageTitleName = pageTitleName;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return id + "[" + getPageTitleName() + "] "+ getPageAddress();
    }
    
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
        final PageTitle other = (PageTitle) obj;
        return this.id == other.getId();
    }
    
    
}
