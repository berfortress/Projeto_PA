/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler;

import adtgraph.Digraph;
import adtgraph.Edge;
import adtgraph.GraphEdgeList;
import adtgraph.InvalidVertexException;
import adtgraph.Vertex;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Link;
import models.LinkException;
import models.Website;
import models.WebsiteException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author fabio e bernardo
 */
public class WebCrawler {

    private List<Link> linksToVisit;
    private List<Link> linksList;
    private int maxPagesToSearch;
    private List<Website> webSitesVisited;
    private final Digraph<Website, Link> digraph;
    private Website site;
    private List<String> dupLinks, dupWebsites;

    /**
     * Construtor da classe WebCrawler
     *
     * @param maxPagesToSearch
     */
    public WebCrawler(int maxPagesToSearch) {
        this.maxPagesToSearch = maxPagesToSearch;
        this.digraph = new GraphEdgeList<>();
        this.linksToVisit = new ArrayList<>();
        this.linksList = new ArrayList<>();
        this.webSitesVisited = new ArrayList<>();
        this.site = null;
        this.dupLinks = new ArrayList<>();
        this.dupWebsites = new ArrayList<>();
    }

    /**
     * Construtor da classe WebCrawler.
     */
    public WebCrawler() {
        this.maxPagesToSearch = 4;
        this.digraph = new GraphEdgeList<>();
        this.linksToVisit = new ArrayList<>();
        this.webSitesVisited = new ArrayList<>();
        this.linksList = new ArrayList<>();
        this.site = null;
        this.dupLinks = new ArrayList<>();
        this.dupWebsites = new ArrayList<>();
    }

    /**
     * Método que retorna uma lista de páginas visitadas.
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
        return linksToVisit;
    }

    public List<Link> getLinks() {
        return linksList;
    }

    /**
     * Método que retorna o número máximo de páginas a procurar.
     *
     * @return
     */
    public int getMaxPagesToSearch() {
        return maxPagesToSearch;
    }

    /**
     * Método que altera o número máximo de páginas a procurar.
     *
     * @param maxPagesToSearch
     */
    public void setMaxPagesToSearch(int maxPagesToSearch) {
        this.maxPagesToSearch = maxPagesToSearch;
    }

    /**
     * Método que retorna todos os vértices (Website).
     *
     * @return
     */
    public Iterable<Vertex<Website>> getAllWebsites() {
        return digraph.vertices();
    }

    /**
     * Método que retorna todos as arestas (Link).
     *
     * @return
     */
    public Iterable<Edge<Link, Website>> getAllLinks() {
        return digraph.edges();
    }

    /**
     * Método que adiciona vértices.
     *
     * @param page
     * @throws WebsiteException
     */
    public void addVertex(Website page) throws WebsiteException {
        try {
            digraph.insertVertex(page);
        } catch (InvalidVertexException e) {
            throw new WebsiteException("Website with name " + page.getWebsiteName() + " does not exist");
        }
    }

    /**
     * Método que liga dois vértices a uma aresta.
     *
     * @param page1
     * @param page2
     * @param link
     * @throws WebsiteException
     */
    public void addEdge(Website page1, Website page2, Link link) throws WebsiteException {

        if (link == null) {
            throw new WebsiteException("Hyperlink is null");
        }

        Vertex<Website> a1 = checkVertex(page1);
        Vertex<Website> a2 = checkVertex(page2);

        try {
            digraph.insertEdge(a1, a2, link);
        } catch (InvalidVertexException e) {
            throw new WebsiteException("The Hyperlink " + link.toString() + " already exists");
        }
    }

    /**
     * Verifica se o vértice já existe.
     *
     * @param page
     * @return
     * @throws WebsiteException
     */
    private Vertex<Website> checkVertex(Website page) throws WebsiteException {

        if (page == null) {
            throw new WebsiteException("Page title cannot be null");
        }

        Vertex<Website> find = null;
        for (Vertex<Website> v : digraph.vertices()) {
            if (v.element().equals(page)) { //equals was overriden in Website!!
                find = v;
            }
        }

        if (find == null) {
            throw new WebsiteException("Website with name " + page.getWebsiteName() + " does not exist");
        }

        return find;
    }

    /**
     * Método que retorna uma lista de arestas entre dois vértices.
     *
     * @param page1
     * @param page2
     * @return
     * @throws WebsiteException
     */
    public List<Link> getEdgesBetween(Website page1, Website page2) throws WebsiteException {
        Vertex<Website> p1 = checkVertex(page1);
        Vertex<Website> p2 = checkVertex(page2);
        List<Link> list = new ArrayList<>();
        if (!digraph.areAdjacent(p1, p2)) {
            return list;
        }
        Iterable<Edge<Link, Website>> it = digraph.incidentEdges(p1);
        for (Edge<Link, Website> edge : it) {
            if (digraph.opposite(p1, edge) == p2) {
                list.add(edge.element());
            }
        }
        return list;
    }

    /**
     * Retorna uma String com a informação acerca do webCrawler.
     *
     * @return
     */
    @Override
    public String toString() {
        String str = "\n WEB CRAWLER (" + digraph.numVertices() + " pagesTitles | " + digraph.numEdges() + " links) \n";
        for (Vertex<Website> a1 : digraph.vertices()) {
            for (Vertex<Website> a2 : digraph.vertices()) {
                if (a1.equals(a2)) {
                    break;
                }
                try {
                    if (!getEdgesBetween(a1.element(), a2.element()).isEmpty()) {
                        str += "\n [" + a1.element().getWebsiteName() + "]" + " TO " + "[" + a2.element().getWebsiteName() + "]" + "\n";
                        List<Link> l = new ArrayList<>();
                        l = getEdgesBetween(a1.element(), a2.element());
                        for (int i = 0; i < l.size(); i++) {
                            str += "\t" + l.get(i).getLink() + "\n";
                        }
                    }
                } catch (WebsiteException ex) {
                    Logger.getLogger(WebCrawler.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }

        List<String> list = new ArrayList<>();

        str += "\nNº Páginas Totais " + webSitesVisited().size();
        for (int i = 0; i < webSitesVisited().size(); i++) {
            if (i == 0) {
                str += "\nPágina Visitada ";
                str += webSitesVisited.get(0) + " [ INICIAL ]";
                list.add(webSitesVisited.get(0).getURL());
            } else {
                if (!list.contains(webSitesVisited.get(i).getURL())) {
                    str += "\nPágina Visitada ";
                    str += webSitesVisited.get(i);
                    list.add(webSitesVisited.get(i).getURL());
                }
            }

        }
        str += "\n\nNº Links Visitados " + getLinksVisited().size() + "\n"
                + "\nLinks Visitados " + getLinksVisited() + "\n"
                + "\nVertices" + digraph.vertices() + "\n"
                + "\nEdges" + digraph.edges() + "\n"
                + "\nA página mais visitada foi ";
        int max = -1;
        Website pg = new Website();
        for (Vertex<Website> p : digraph.vertices()) {
            if (max < digraph.incidentEdges(p).size()) {
                pg = p.element();
                max = digraph.incidentEdges(p).size();
            }
        }

        str += pg.getWebsiteName() + "\n";

        return str;
    }

    /**
     * Método que retorna o vértice dado um pageTitle.
     *
     * @param pi
     * @return
     */
    protected Vertex<Website> getVertex(Website pi) {
        for (Vertex<Website> ap : digraph.vertices()) {
            if (ap.element().equals(pi)) {
                return ap;
            }
        }
        return null;
    }

    /**
     * Método que retorna o vértice dado um pageTitle.
     *
     * @param pi
     * @return
     */
    protected Edge<Link, Website> getEdge(Link pi) {
        for (Edge<Link, Website> ap : digraph.edges()) {
            if (ap.element().equals(pi)) {
                return ap;
            }
        }
        return null;
    }

    public void removeAfterCharacters(Link link, String url, String characters) {
        int indexOf = 0;
        if (characters.equalsIgnoreCase("#")) {
            indexOf = url.indexOf(characters);
            if (indexOf > -1) {
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

        if (characters.equalsIgnoreCase("/")) {
            char[] c = url.toCharArray();
            if (url.endsWith("/")) {
                url = url.substring(0, c.length - 1);
                link.setLink(url);
            }
        }
    }

    public Set<Link> removeRepeatedLinks(List<Link> links) {
        for (Link l : links) {
            String s1 = l.getLink();
            removeAfterCharacters(l, s1, "#");
            removeAfterCharacters(l, s1, "/");
            removeAfterCharacters(l, s1, "&");
        }

        Set<Link> newList = new HashSet<>(links);

        return newList;
    }

    public List<Link> openUrlAndShowTitleAndLinks(String urlAddress) throws IOException, WebsiteException, LinkException {
        List<Link> test = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(urlAddress).get();
            String title = doc.title();
            Elements links = doc.select("a[href]");
            Website web = null;

            if (title.isEmpty()) {
                web = new Website("", "");
                if (!dupWebsites.contains("")) {
                    webSitesVisited.add(web);
                    dupWebsites.add("");
                }
            } else {
                web = new Website(title, urlAddress);
                if (!dupWebsites.contains(urlAddress)) {
                    webSitesVisited.add(web);
                    dupWebsites.add(urlAddress);
                }
            }

            for (Element l : links) {
                if (l.text().isEmpty()) {
                    linksList.add(new Link("No Description", l.attr("abs:href")));
                    web.addLink(new Link("No Description", urlAddress));
                } else {
                    linksList.add(new Link(l.text(), l.attr("abs:href")));
                    web.addLink(new Link(l.text(), urlAddress));
                }
            }

            for (int i = 0; i < linksList.size(); i++) {
                if (linksList.get(i).getLink().equals(urlAddress)) {
                    linksList.remove(i);
                }
            }

            Set<Link> newListSet = removeRepeatedLinks(linksList);
            for (Website w : webSitesVisited) {
                if (w.getURL().equals(urlAddress)) {
                    site = w;
                }
            }

            List<Link> newList = new ArrayList<>(newListSet);
            bubbleSort(newList);
            for (Link l : newList) {
                site.addLink(l);
                linksToVisit.add(l);
                test.add(l);
            }

            for (int i = 0; i < linksToVisit.size(); i++) {
                for (int j = i + 1; j < linksToVisit.size(); j++) {
                    if (linksToVisit.get(i).getLink().equalsIgnoreCase(linksToVisit.get(j).getLink())) {
                        linksToVisit.remove(j);
                    }
                }
            }

            if (getVertex(site) == null) {
                addVertex(site);
            }

            linksList.clear();

        } catch (IOException ex) {
            System.out.println("HTTP request error" + ex);
        }
        return test;
    }

    public void bubbleSort(List<Link> list) {
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

    public List<Vertex<Website>> getAdjacents(Vertex<Website> w) {

        List<Vertex<Website>> sites = new ArrayList<>();

        for (Edge<Link, Website> link : digraph.incidentEdges(w)) {
            Vertex<Website> web2 = digraph.opposite(w, link);
            sites.add(web2);
        }
        return sites;
    }

    public List<Website> getAdjacentsElem(Website w) {
        Vertex<Website> vertexWeb = getVertex(w);
        List<Website> sites = new ArrayList<>();

        for (Edge<Link, Website> link : digraph.incidentEdges(vertexWeb)) {
            Vertex<Website> web2 = digraph.opposite(vertexWeb, link);
            sites.add(web2.element());
        }
        return sites;
    }

    public void automatic(String url) throws IOException, WebsiteException, LinkException {
        openUrlAndShowTitleAndLinks(url);

        Set<Vertex<Website>> visited = new HashSet<>();
        Queue<Vertex<Website>> queue = new LinkedList<>();
        Vertex<Website> web = getVertex(site);
        if (linksToVisit.isEmpty()) {
            System.out.println("\n**** SORRY BUT THE PAGE " + webSitesVisited.get(0).getWebsiteName() + " DONT HAVE ANY URL. **** \n");
        } else {
            int count = 1;
            while (webSitesVisited.size() <= getMaxPagesToSearch()) {
                openUrlAndShowTitleAndLinks(linksToVisit.get(count).getLink());
                bubbleSort(linksToVisit);
                if (count < webSitesVisited.size() && count < linksToVisit.size()) {
                    addEdge(web.element(), webSitesVisited.get(count), linksToVisit.get(count));
                }
                count++;
            }
        }

        visited.add(web);
        queue.add(web);
        int c = 0;
        while (!queue.isEmpty() && c <= 2) {
            Vertex<Website> v = queue.remove();

            for (Vertex<Website> w : getAdjacents(v)) {
                if (!visited.contains(w)) {
                    visited.add(w);
                    queue.add(w);

                    for (Vertex<Website> x : visited) {
                        linksList = openUrlAndShowTitleAndLinks(x.element().getURL());

                        for (int i = 0; i < linksList.size(); i++) {
                            if (i < webSitesVisited.size()) {
                                if (x.element() != webSitesVisited.get(i)) {
                                    if (getVertex(webSitesVisited.get(i)) != null && getEdge(linksList.get(i)) == null) {
                                        addEdge(x.element(), webSitesVisited.get(i), linksList.get(i));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            c++;
        }
    }
}
