/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webCrawler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import models.Link;
import models.Website;

/**
 *
 * @author berna
 */
public class WebCrawlerMethods extends DijkstraGraph {
    
    /**
     * Método que dado um url e um caracter remove da url o ultimo caracter dado
     *
     * @param urlAddress
     * @param character
     * @return
     */
    protected String removeLastCharacter(String urlAddress, String character) {
        if (urlAddress.endsWith(character)) {
            int pos = urlAddress.lastIndexOf(character);
            urlAddress = urlAddress.substring(0, pos);
        }
        return urlAddress;
    }

    /**
     * Método para remover caracteres repetidos
     *
     * @param link
     * @param url
     * @return
     */
    public String removeAfterCharacters(Link link, String url) {
        url = removeLastCharacter(url, "#");
        url = removeLastCharacter(url, "?");
        url = removeLastCharacter(url, "&");
        url = removeLastCharacter(url, "/");
        return url;
    }

    /**
     * Método que remove os caracteres dos links
     *
     * @param links
     * @return
     */
    public Set<Link> removeCharacterLinks(List<Link> links) {
        for (Link l : links) {
            String s1 = l.getLink();
            s1 = removeAfterCharacters(l, s1);
            l.setLink(s1);
        }
        Set<Link> newList = new HashSet<>(links);
        return newList;
    }

    /**
     * Método BubbleSort para ordenar links
     *
     * @param list
     */
    public void bubbleSortLink(List<Link> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - i - 1; j++) {
                if (list.get(j).getId() > list.get(j + 1).getId()) {
                    Link temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }

    /**
     * Método BubbleSort para ordenar websites
     *
     * @param web
     */
    public void bubbleSortWeb(List<Website> web) {
        for (int i = 0; i < web.size() - 1; i++) {
            for (int j = 0; j < web.size() - i - 1; j++) {
                if (web.get(j).getId() > web.get(j + 1).getId()) {
                    Website temp = web.get(j);
                    web.set(j, web.get(j + 1));
                    web.set(j + 1, temp);
                }
            }
        }
    }
}
