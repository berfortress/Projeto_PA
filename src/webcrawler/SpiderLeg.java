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

    private List<Hyperlinks> linksVisited;
    private List<Hyperlinks> linksNotVisit;
    private List<PageTitle> pagesVisited;

    public SpiderLeg() {
        linksVisited = new ArrayList<>();
        linksNotVisit = new ArrayList<>();
        pagesVisited = new ArrayList<>();
    }

    public void openUrlAndShowTitleAndLinks(String urlAddress) throws IOException {
        try {
            Document doc = Jsoup.connect(urlAddress).get();
            String title = doc.title();
            Elements links = doc.select("a[href]");
            if (title.isEmpty()) {
                pagesVisited.add(new PageTitle("404 - Not Found", urlAddress));
            } else {
                pagesVisited.add(new PageTitle(title, urlAddress));
            }
            for (Element l : links) {
                if (l.text().isEmpty()) {
                    linksNotVisit.add(new Hyperlinks("No Description", l.attr("abs:href")));
                } else {
                    linksNotVisit.add(new Hyperlinks(l.text(), l.attr("abs:href")));
                }
            }
            List<Hyperlinks> newList = removeRepeatedLinks(linksNotVisit);
            linksNotVisit = newList;

        } catch (IOException ex) {
            System.out.println("HTTP request error" + ex);
        }

        for (int i = 0; i < linksVisited.size(); i++) {
            if (linksVisited.get(i).getLink().equals(linksNotVisit.get(i).getLink())) {
                linksNotVisit.remove(i);
            }
        }
    }

    public PageTitle getPageTitle(String urlAddress) {
        PageTitle page = null;
        try {
            Document doc = Jsoup.connect(urlAddress).get();
            String title = doc.title();
            Elements links = doc.select("a[href]");
            if (title.isEmpty()) {
                page = new PageTitle("404 - Not Found", urlAddress);
                pagesVisited.add(page);
                doc.title("404 - Not Found");
                return page;
            } else {
                page = new PageTitle(title, urlAddress);
                pagesVisited.add(page);
                doc.title(title);
                return page;
            }
        } catch (IOException ex) {
            System.out.println("HTTP request error" + ex);
        }
        return null;
    }

    public List<Hyperlinks> getAllLinks(PageTitle page) throws IOException {
        List<Hyperlinks> hype = new ArrayList<>();
        Document doc = Jsoup.connect(page.getPageAddress()).get();
        Elements links = doc.select("a[href]");
        Hyperlinks p = null;

            for (Element l : links) {
                if (l.text().isEmpty()) {
                    p = new Hyperlinks("No Description", l.attr("abs:href"));
                } else {
                    p = new Hyperlinks(l.text(), l.attr("abs:href"));
                }
                if (l != null) {
                    if (!linksVisited.contains(p)) {
                        if (!linksNotVisit.contains(p)) {
                            linksNotVisit.add(p);
                            hype.add(p);
                        }
                    }
                    removeRepeatedLinks(linksNotVisit);
                    List<Hyperlinks> newList = removeRepeatedLinks(hype);
                    hype = newList;
                } else {
                    break;
                }
            }
        return hype;
    }

    public void removeAfterCharacters(Hyperlinks link, String url, String characters) {
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

    public List<Hyperlinks> removeRepeatedLinks(List<Hyperlinks> links) {
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

    public List<Hyperlinks> getLinksVisited() {
        return linksVisited;
    }

    public List<Hyperlinks> getLinksNotVisited() {
        return linksNotVisit;
    }

    public List<PageTitle> getPagesVisited() {
        return pagesVisited;
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
