/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler;

import adtgraph.Digraph;
import adtgraph.Edge;
import adtgraph.GraphEdgeList;
import adtgraph.InvalidEdgeException;
import adtgraph.InvalidVertexException;
import adtgraph.Vertex;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
 * @author berna
 */
public class WebCrawler {

    private List<Link> linksVisited;
    private List<Link> linksList;
    private int maxPagesToSearch;
    private List<Website> webSitesVisited;
    private final Digraph<Website, Link> digraph;

    /**
     * Construtor da classe WebCrawler
     *
     * @param maxPagesToSearch
     */
    public WebCrawler(int maxPagesToSearch) {
        this.maxPagesToSearch = maxPagesToSearch;
        this.digraph = new GraphEdgeList<>();
        this.linksVisited = new ArrayList<>();
        this.linksList = new ArrayList<>();
        this.webSitesVisited = new ArrayList<>();
    }

    /**
     * Construtor da classe WebCrawler.
     */
    public WebCrawler() {
        this.maxPagesToSearch = 4;
        this.digraph = new GraphEdgeList<>();
        this.linksVisited = new ArrayList<>();
        this.webSitesVisited = new ArrayList<>();
        this.linksList = new ArrayList<>();
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
        return linksVisited;
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
        String str = "WEB CRAWLER (" + digraph.numVertices() + " pagesTitles | " + digraph.numEdges() + " links) \n";
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

        str += "\nNº Páginas Totais " + webSitesVisited().size();
        for (int i = 0; i < webSitesVisited().size(); i++) {
            str += "\nPáginas Visitadas ";
            if (i == 0) {
                str += webSitesVisited.get(0) + " [ INICIAL ]";
            } else {
                str += webSitesVisited.get(i);
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
    private Vertex<Website> getVertex(Website pi) {
        for (Vertex<Website> ap : digraph.vertices()) {
            if (ap.element().equals(pi)) {
                return ap;
            }
        }
        return null;
    }

    public void removeAfterCharacters(Link link, String url, String characters) {
        int indexOf;
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

    public void openUrlAndShowTitleAndLinks(String urlAddress) throws IOException, WebsiteException, LinkException {
        try {
            Website site = null;
            Document doc = Jsoup.connect(urlAddress).get();
            String title = doc.title();
            Elements links = doc.select("a[href]");
            if (title.isEmpty()) {
                //site.setURL(urlAddress);
                webSitesVisited.add(new Website("", urlAddress));
            } else {
                //site.setWebsiteName(title);
                //site.setURL(urlAddress);
                webSitesVisited.add(new Website(title, urlAddress));
            }
            for (Element l : links) {
                if (l.text().isEmpty()) {
                    linksList.add(new Link("No Description", l.attr("abs:href")));
                } else {
                    linksList.add(new Link(l.text(), l.attr("abs:href")));
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
                linksVisited.add(l);
            }
            addVertex(site);
            linksList.clear();

        } catch (IOException ex) {
            System.out.println("HTTP request error" + ex);
        }
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

    /**
     * Método principal : É dado um URL inicial e a partir dele são adicionados
     * na lista de links não visitados os links que foram encontrados no URL
     * passado por parâmetro, se a lista de links não visitados estiver vazia é
     * enviado um print a informar que a página não contém links,senão ele
     * percorre sempre em medida do número máximo de páginas que definimos. Cria
     * um vértice com informação do link da posição zero dos links não visitados
     * e depois disso é removido fazendo isto até ao critério de paragem ser
     * cumprido.
     *
     * @param url
     * @throws IOException
     * @throws WebsiteException
     * @throws LinkException
     */
    public void search(String url) throws IOException, WebsiteException, LinkException {
        openUrlAndShowTitleAndLinks(url);
        if (linksVisited.isEmpty()) {
            System.out.println("**** SORRY BUT THE PAGE " + webSitesVisited.get(0).getWebsiteName() + " DONT HAVE ANY URL. **** \n \n");
        } else {
            int count = 1;
            int number = 1;
            while (webSitesVisited.size() <= getMaxPagesToSearch()) {
                openUrlAndShowTitleAndLinks(linksVisited.get(count++).getLink());
                Set<Link> newList = new HashSet(linksVisited);
                List<Link> newList1 = new ArrayList(newList);
                linksVisited = newList1;
                bubbleSort(linksVisited);
                addRelation(number++);
            }
        }

    }

    public void addRelation(int number) throws WebsiteException, LinkException {
        try {
            addEdge(webSitesVisited.get(0), webSitesVisited.get(number), linksVisited.get(number));
        } catch (InvalidEdgeException ex) {
            throw new LinkException("Link with the name does not exist");
        }
    }
}
