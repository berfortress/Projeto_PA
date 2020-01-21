/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webCrawler;

import exceptions.LinkException;
import exceptions.WebsiteException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import models.Link;
import models.Website;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public abstract class WebCrawler extends WebCrawlerMethods {

    private List<Link> linksVisited;
    private List<Link> linksList;
    private int maxPagesToSearch;
    private List<Website> webSitesVisited;
    private List<String> dupWebsites;

    /**
     * Construtor da classe WebCrawler
     *
     * @param maxPagesToSearch
     */
    public WebCrawler(int maxPagesToSearch) {
        this.maxPagesToSearch = maxPagesToSearch;
        this.linksVisited = new ArrayList<>();
        this.linksList = new ArrayList<>();
        this.webSitesVisited = new ArrayList<>();
        this.dupWebsites = new ArrayList<>();
    }

    /**
     * Construtor da classe WebCrawler.
     */
    public WebCrawler() {
        this.maxPagesToSearch = 4;
        this.linksVisited = new ArrayList<>();
        this.webSitesVisited = new ArrayList<>();
        this.linksList = new ArrayList<>();
        this.dupWebsites = new ArrayList<>();
    }

    /**
     * Método que retorna uma lista de websites visitados.
     *
     * @return
     */
    public List<Website> webSitesVisited() {
        return webSitesVisited;
    }

    /**
     * Método que retorna uma lista de links visitados.
     *
     * @return
     */
    public List<Link> getLinksVisited() {
        return linksVisited;
    }

    /**
     * Método que retorna o número máximo de websites a procurar.
     *
     * @return
     */
    public int getMaxPagesToSearch() {
        return maxPagesToSearch;
    }

    /**
     * Método que altera o número de websites a procurar
     *
     * @param maxPagesToSearch
     */
    public void setMaxPagesToSearch(int maxPagesToSearch) {
        if (maxPagesToSearch >= 0) {
            this.maxPagesToSearch = maxPagesToSearch;
        } else {
            throw new IndexOutOfBoundsException("O número de páginas não pode ser menor que 0.");
        }
    }

    /**
     * Método que recebe uma URL e a partir dessa URL adiciona o número de links
     * e websites associados
     *
     * @param urlAddress
     * @return
     * @throws IOException
     * @throws WebsiteException
     * @throws LinkException
     */
    public List<Link> openUrlAndGetTitleAndLinks(String urlAddress) throws IOException, WebsiteException, LinkException {
        List<Link> list = new ArrayList<>();
        try {
            Website site = null;
            Document doc = Jsoup.connect(urlAddress).get();
            site = openUrlAndGetTitle(urlAddress);
            Elements links = doc.select("a[href]");
            addLinksAndRemoveRepeated(links, site, urlAddress);
            Set<Link> newListSet = removeCharacterLinks(linksList);
            for (Website w : webSitesVisited) {
                if (w.getURL().equals(urlAddress)) {
                    site = w;
                }
            }
            List<Link> newList = new ArrayList<>(newListSet);
            bubbleSortLink(newList);
            aditionalMethod(newList, site);

        } catch (IOException e) {
            if (e instanceof HttpStatusException) {
                HttpStatusException statusException = (HttpStatusException) e;
                if (statusException.getStatusCode() == 404) {
                    System.out.println("Status Exception 404");
                }
                if (statusException.getStatusCode() == 503) {
                    System.out.println("Status Exception 503");
                }
            }
        }
        linksList.clear();
        return list;
    }

    private void aditionalMethod(List<Link> newList, Website site) throws WebsiteException {
        for (Link l : newList) {
            linksVisited.add(l);
        }
        for (int i = 0; i < linksVisited.size(); i++) {
            for (int j = i + 1; j < linksVisited.size(); j++) {
                if (linksVisited.get(i).getLink().equalsIgnoreCase(linksVisited.get(j).getLink())) {
                    linksVisited.remove(j);
                }
            }
        }
        if (getVertex(site) == null) {
            addVertex(site);
        }
        site.getLinks().clear();
        for (int i = 0; i < linksList.size(); i++) {
            site.addLink(linksList.get(i));
        }
        linksList.clear();
    }

    private void addLinksAndRemoveRepeated(Elements links, Website site, String urlAddress) {
        for (Element l : links) {
            if (l.text().isEmpty()) {
                linksList.add(new Link("", l.attr("abs:href")));
                site.addLink(new Link("", urlAddress));
            } else {
                linksList.add(new Link(l.text(), l.attr("abs:href")));
                site.addLink(new Link(l.text(), urlAddress));
            }
        }
        for (int i = 0; i < linksList.size(); i++) {
            if (linksList.get(i).getLink().equals(urlAddress)) {
                linksList.remove(i);
            }
        }
    }

    private Website AddWebSitesVisited(String title, Website site, String urlAddress) {
        if (title.isEmpty()) {
            if (!dupWebsites.contains("")) {
                site = new Website("", urlAddress);
                webSitesVisited.add(site);
                dupWebsites.add("");
            }
        } else {
            if (!dupWebsites.contains(urlAddress)) {
                site = new Website(title, urlAddress);
                webSitesVisited.add(site);
                dupWebsites.add("");
            }
        }
        return site;
    }

    public Website openUrlAndGetTitle(String urlAddress) throws WebsiteException {
        Website site = null;
        try {
            Document doc = Jsoup.connect(urlAddress).get();
            String title = doc.title();
            site = AddWebSitesVisited(title, site, urlAddress);
            addVertex(site);
        } catch (IOException e) {
            if (e instanceof HttpStatusException) {
                HttpStatusException statusException = (HttpStatusException) e;
                if (statusException.getStatusCode() == 404) {
                    System.out.println("Status Exception 404");
                }
                if (statusException.getStatusCode() == 503) {
                    System.out.println("Status Exception 503");
                }
            }
        }
        return site;
    }

    public void openUrlAndGetLinks(String urlAddress, String initialAddress) throws IOException {
        try {
            Document doc = Jsoup.connect(urlAddress).get();
            Elements links = doc.select("a[href]");
            openUrlAndGetLinksAditionalMethod(links, urlAddress);
            Set<Link> newListSet = removeCharacterLinks(linksList);
            List<Link> newList = new ArrayList<>(newListSet);
            bubbleSortLink(newList);
            Website site = null;
            for (int i = 0; i < webSitesVisited.size(); i++) {
                if (webSitesVisited.get(i).getURL().equals(urlAddress)) {
                    site = webSitesVisited.get(i);
                }
            }

            for (int j = 0; j < linksVisited.size(); j++) {
                for (int i = 0; i < newList.size(); i++) {
                    if (linksVisited.get(j).getLink().equals(newList.get(i).getLink()) || newList.get(i).getLink().equals(initialAddress)
                            || linksVisited.get(j) == null) {
                        newList.remove(i);
                    }
                }
            }
            if (!newList.isEmpty()) {
                for (Link l : newList) {
                    site.addLink(l);
                    linksVisited.add(l);
                }
            }
            linksList.clear();
        } catch (IndexOutOfBoundsException error) {
            System.out.println("Erro " + error);
        } catch (IOException e) {
            if (e instanceof HttpStatusException) {
                HttpStatusException statusException = (HttpStatusException) e;
                if (statusException.getStatusCode() == 404) {
                    System.out.println("Status Exception 404");
                }
                if (statusException.getStatusCode() == 503) {
                    System.out.println("Status Exception 503");
                }
            }
        }
    }

    private void openUrlAndGetLinksAditionalMethod(Elements links, String urlAddress) {
        for (Element l : links) {
            if (l.text().isEmpty()) {
                linksList.add(new Link("", l.attr("abs:href")));
            } else {
                linksList.add(new Link(l.text(), l.attr("abs:href")));
            }
        }
        for (int i = 0; i < linksList.size(); i++) {
            if (linksList.get(i).getLink().equals(urlAddress)) {
                linksList.remove(i);
            }
        }
    }

    public abstract void search(String url) throws IOException, WebsiteException, LinkException;

    public void recursiveSearch(int count1, String url) throws WebsiteException, IOException {
        List<Website> list = new ArrayList<>();
        openUrlAndGetLinks(webSitesVisited().get(count1).getURL(), url);
        for (int i = 0; i < webSitesVisited().get(count1).getLinksSize(); i++) {
            list.add(openUrlAndGetTitle(webSitesVisited().get(count1).getLinkDescription(i)));
        }
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) {
                list.remove(i);
            }
        }
        int i = 0;
        int j = 0;
        while (i < list.size() && j < webSitesVisited().get(count1).getLinksSize()) {
            addEdge(webSitesVisited().get(count1), list.get(i), webSitesVisited().get(count1).getLink(j));
            j++;
            i++;
        }
        list.clear();
    }
}
