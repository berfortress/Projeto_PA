/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bernardo e Fábio
 */
public class PageTitle implements Serializable{
    private int id = 0;
    private static int count = 0;
    private String pageTitleName;
    private String pageAddress;
    private List<Hyperlinks> hyper;

    /**
     * Construtor da Página
     * @param pageTitleName
     * @param pageAddress 
     */
    public PageTitle(String pageTitleName, String pageAddress) {
        this.id = ++count;
        this.pageTitleName = pageTitleName;
        this.pageAddress = pageAddress;
        hyper = new ArrayList<>();
    }  

    /**
     * Construtor da Página
     */
    public PageTitle() {
        this.id = ++count;
        this.pageTitleName = "";
        this.pageAddress = "";
        hyper = new ArrayList<>();
    }
    
    /**
     * Método que adiciona links a uma página específica.
     * @param h 
     */
    public void addHyperlink(Hyperlinks h){
        hyper.add(h);
    }

    /**
     * Método que retorna uma lista de links de uma página.
     * @return 
     */
    public List<Hyperlinks> getHyper() {
        return hyper;
    }

    /**
     * Retorna o nome da página.
     * @return 
     */
    public String getPageTitleName() {
        return pageTitleName;
    }

    /**
     * Retorna o URL da página.
     * @return 
     */
    public String getPageAddress() {
        return pageAddress;
    }

    /**
     * Altera o URL da página.
     * @param pageAddress 
     */
    public void setPageAddress(String pageAddress) {
        this.pageAddress = pageAddress;
    }
    
    /**
     * Altera o nome da página.
     * @param pageTitleName 
     */
    public void setPageTitleName(String pageTitleName) {
        this.pageTitleName = pageTitleName;
    }

    /**
     * Retorna o id da página.
     * @return 
     */
    public int getId() {
        return id;
    }

    /**
     * Descrição do PageTitle.
     * @return 
     */
    @Override
    public String toString() {
        return id + "[" + getPageTitleName() + "] "+ getPageAddress();
    }
    
    /**
     * Equals
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
        final PageTitle other = (PageTitle) obj;
        return this.id == other.getId();
    }
    
    
}
