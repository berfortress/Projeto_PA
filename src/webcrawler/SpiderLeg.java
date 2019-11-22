/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import static java.util.Comparator.comparingInt;
import java.util.TreeSet;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 *
 * @author bernardo
 */
public class SpiderLeg {

    public void openUrlAndShowTitleAndLinks(String urlAddress, List<PageTitle> pageTitle, List<Hyperlinks> link) throws IOException {
        try {
            Document doc = Jsoup.connect(urlAddress).get();
            String title = doc.title();

            Elements links = doc.select("a[href]");
            if (title.isEmpty()) {
                pageTitle.add(new PageTitle("404 - Not Found", urlAddress));
            } else {
                pageTitle.add(new PageTitle(title, urlAddress));
            }
            for (Element l : links) {
                if (l.text().isEmpty()) {
                    link.add(new Hyperlinks("-----", l.attr("abs:href")));
                } else {
                    link.add(new Hyperlinks(l.text(), l.attr("abs:href")));
                }
            }
            removeRepeatedLinks(link);
//            for (Hyperlinks l : link) {
//                System.out.println(l.getName() + " " + l.getLink());
//            }

        } catch (IOException ex) {
            System.out.println("HTTP request error" + ex);
        }
    }

    public void openUrlAndShowTitle(String urlAddress, List<PageTitle> pageTitle) throws IOException {
        try {
            Document doc = Jsoup.connect(urlAddress).get();
            String title = doc.title();

            Elements links = doc.select("a[href]");
            if (title.isEmpty()) {
                pageTitle.add(new PageTitle("404 - Not Found", urlAddress));
            } else {
                pageTitle.add(new PageTitle(title, urlAddress));
            }
        } catch (IOException ex) {
            System.out.println("HTTP request error" + ex);
        }
    }

    private void removeAfterCharacters(List<Hyperlinks> links, Hyperlinks link, String url, String characters) {
        int indexOf;
        int urlSize;
        if (characters.equalsIgnoreCase("#")) {
            indexOf = url.indexOf(characters);
            if (indexOf > -1) {
                url = url.substring(0, indexOf);
                if (url.compareToIgnoreCase(link.getLink()) == 0) {
                    links.remove((link.getLink() == null ? url == null : link.getLink().equals(url)));
                }
            }
        }
        
        if (characters.equalsIgnoreCase("/")) {
            indexOf = url.lastIndexOf(characters);
            urlSize = url.length();
            if (urlSize >= indexOf + 1) {
                links.remove(link);
            }
        }
        
        if (url.compareToIgnoreCase(link.getLink()) == 0) {
            links.remove((link.getLink().equals(url)));
        }
    }

    private void removeRepeatedLinks(List<Hyperlinks> links) {

        for (int i = 0; i < links.size(); i++) {
            for (int j = i + 1; j < links.size(); j++) {
//                String s1 = links.get(j).getLink();
                removeAfterCharacters(links, links.get(i), links.get(j).getLink(), "#");
                removeAfterCharacters(links, links.get(i), links.get(j).getLink(), "/");

//                if(links.get(i).getLink().equals(links.get(j).getLink()))
//                    links.remove(j);
            }
        }
        
//        for (int i = 0; i < links.size(); i++) {
//            for (Hyperlinks l : links) {
//                if(links.get(i).equals(l))
//                    links.remove(l);
//            }
//        }
    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }
}
