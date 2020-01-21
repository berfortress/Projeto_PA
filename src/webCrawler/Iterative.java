/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webCrawler;

import memento.WebLink;
import memento.CareTaker;
import exceptions.LinkException;
import exceptions.WebsiteException;
import java.io.IOException;
import java.util.List;
import models.Link;
import models.Website;

/**
 *
 * @author fabio e bernardo
 */
public class Iterative extends WebCrawler {

    private final WebLink webLink; //Receiver e Originator
    private final CareTaker care;

    /**
     * Construtor da classe Iterative
     */
    public Iterative() {
        super();
        this.webLink = new WebLink(null, null);
        this.care = new CareTaker();
    }

    /**
     * Método que executa o modelo iterativo
     *
     * @param url
     * @throws IOException
     * @throws WebsiteException
     * @throws LinkException
     */
    @Override
    public void search(String url) throws IOException, WebsiteException, LinkException {
        openUrlAndGetTitleAndLinks(url);
        firstSearch();
    }

    private void firstSearch() throws WebsiteException {
        for (int i = 0; i < super.getLinksVisited().size(); i++) {
            openUrlAndGetTitle(super.getLinksVisited().get(i).getLink());
        }
        int i = 1;
        int j = 0;
        while (i < super.webSitesVisited().size() && j < super.getLinksVisited().size()) {
            addEdge(super.webSitesVisited().get(0), super.webSitesVisited().get(i), super.getLinksVisited().get(j));
            j++;
            i++;
        }
    }

    public Website getWebsite(String url) {
        Website website = null;
        for (Website web : webSitesVisited()) {
            if (web.getURL().equals(url)) {
                website = web;
            }
        }
        return website;
    }

    public Website getWebsite(List<Website> list, String url) {
        Website website = null;
        for (Website web : list) {
            if (web.getURL().equals(url)) {
                website = web;
            }
        }
        return website;
    }

    public int getWebsitePosition(Website site) {
        int value = 0;
        for (int i = 0; i < webSitesVisited().size(); i++) {
            if (webSitesVisited().get(i).getURL().equals(site.getURL())) {
                value = i;
            }
        }
        return value;
    }

    public int getWebsitePosition(List<Website> list,Website site) {
        int value = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getURL().equals(site.getURL())) {
                value = i;
            }
        }
        return value;
    }

    /**
     * Método que permite fazer undo
     */
    public void undo() {
        care.restoreState(webLink);
    }

    /**
     * Método que permite obter o website do WebLink
     *
     * @return
     */
    public Website getWebLink() {
        return webLink.getWebsite();
    }

    /**
     * Método que retorna se o memento está vazio
     *
     * @return
     */
    public boolean mementoIsEmpty() {
        return care.objMementos.isEmpty();
    }

    /**
     * Método que guarda o estado
     *
     * @param linkList
     * @param web
     */
    public void saveState(List<Link> linkList, Website web) {
        webLink.setLinks(linkList);
        webLink.setWebsite(web);
        care.saveState(webLink);
    }
}
