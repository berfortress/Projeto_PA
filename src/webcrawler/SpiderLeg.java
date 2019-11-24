/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author bernardo
 */
public class SpiderLeg {

    public List<Hyperlinks> openUrlAndShowTitleAndLinks(String urlAddress, List<PageTitle> pageTitle, List<Hyperlinks> link, List<Hyperlinks> visitedLinks) throws IOException {
        PageTitle page = null;
        try {
            Document doc = Jsoup.connect(urlAddress).get();
            String title = doc.title();
            Elements links = doc.select("a[href]");
            if (title.isEmpty()) {
                page = new PageTitle("404 - Not Found", urlAddress);
                pageTitle.add(page);
            } else {
                page = new PageTitle(title, urlAddress);
                pageTitle.add(page);
            }
            for (Element l : links) {
                if (l.text().isEmpty()) {
                    link.add(new Hyperlinks("No Description", l.attr("abs:href")));
                } else {
                    link.add(new Hyperlinks(l.text(), l.attr("abs:href")));
                }
            }
            List<Hyperlinks> newList = removeRepeatedLinks(link);
            link = newList;       

        } catch (IOException ex) {
            System.out.println("HTTP request error" + ex);
        }
        
        for (int i = 0; i < link.size(); i++) {
                if (visitedLinks.contains(link.get(i))) {
                    link.remove(i);
                }
            }
        return link;
    }

    public void openUrlAndShowTitle(String urlAddress, List<PageTitle> pageTitle) throws IOException {
        try {
            Document doc = Jsoup.connect(urlAddress).get();
            String title = doc.title();

            if (title.isEmpty()) {
                pageTitle.add(new PageTitle("404 - Not Found", urlAddress));
            } else {
                pageTitle.add(new PageTitle(title, urlAddress));
            }
        } catch (IOException ex) {
            System.out.println("HTTP request error" + ex);
        }
    }

    private void removeAfterCharacters(Hyperlinks link, String url, String characters) {
        int indexOf;
        int urlSize;
        if (characters.equalsIgnoreCase("#")) {
            indexOf = url.indexOf(characters);
            if (indexOf > -1) {
                url = url.substring(0, indexOf);
                link.setLink(url);
            }
        }

        if (characters.equalsIgnoreCase("/")) {
            indexOf = url.lastIndexOf(characters);
            urlSize = url.length();
            if (urlSize == indexOf + 1) {
                url = url.substring(0, indexOf);
                link.setLink(url);
            }
        }

        if (characters.equalsIgnoreCase("&")) {
            indexOf = url.indexOf(characters);
            if (indexOf > -1) {
                url = url.substring(0, indexOf);
                link.setLink(url);
            }
        }

    }

    private List<Hyperlinks> removeRepeatedLinks(List<Hyperlinks> links) {
        for (int i = 0; i < links.size(); i++) {
            String s1 = links.get(i).getLink();
            removeAfterCharacters(links.get(i), s1, "#");
            removeAfterCharacters(links.get(i), s1, "/");
            removeAfterCharacters(links.get(i), s1, "&");

            for (int j = i + 1; j < links.size(); j++) {
                if (links.get(i).getLink().equals(links.get(j).getLink())) {
                    links.remove(j);
                }
            }
        }
        Set<Hyperlinks> newList = new HashSet<>(links);
        List<Hyperlinks> list = new ArrayList<>(newList);
        bubbleSort(list);
        
        return list;
    }

    public List<Hyperlinks> bubbleSort(List<Hyperlinks> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - i - 1; j++) {
                if (list.get(j).getId() > list.get(j + 1).getId()) {
                    Hyperlinks temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
        return list;
    }
}
