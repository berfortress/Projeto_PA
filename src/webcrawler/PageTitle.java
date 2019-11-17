/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author bernardo
 */
public class PageTitle {
    private int id = 0;
    private static int count = 0;
    private String pageTitleName;


    public PageTitle(String pageTitleName) {
        this.id = ++count;
        this.pageTitleName = pageTitleName;
    }

    public String getPageTitleName() {
        return pageTitleName;
    }

    public void setPageTitleName(String pageTitleName) {
        this.pageTitleName = pageTitleName;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "" + id + ": " + pageTitleName;
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
