/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler;

import models.Hyperlinks;
import models.HyperlinksException;
import models.PageTitleException;
import models.PageTitle;
import adtgraph.Digraph;
import java.util.List;
import adtgraph.Edge;
import adtgraph.GraphEdgeList;
import adtgraph.InvalidEdgeException;
import adtgraph.InvalidVertexException;
import adtgraph.Vertex;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bernardo e Fábio
 */
public class WebCrawler {

    private List<Hyperlinks> linksVisited;
    private List<Hyperlinks> linksNotVisited;
    private int maxPagesToSearch;
    private List<PageTitle> pagesVisited;
    private final Digraph<PageTitle, Hyperlinks> digraph;

    /**
     * Construtor da classe WebCrawler
     *
     * @param maxPagesToSearch
     */
    public WebCrawler(int maxPagesToSearch) {
        this.maxPagesToSearch = maxPagesToSearch;
        this.digraph = new GraphEdgeList<>();
        this.linksVisited = new ArrayList<>();
        this.linksNotVisited = new ArrayList<>();
        this.pagesVisited = new ArrayList<>();
    }

    /**
     * Construtor da classe WebCrawler.
     */
    public WebCrawler() {
        this.maxPagesToSearch = 4;
        this.digraph = new GraphEdgeList<>();
        this.linksVisited = new ArrayList<>();
        this.pagesVisited = new ArrayList<>();
    }

    /**
     * Método que retorna uma lista de páginas visitadas.
     *
     * @return
     */
    public List<PageTitle> getPagesVisited() {
        return pagesVisited;
    }

    /**
     * Método que retorna uma lista de links visitados.
     *
     * @return
     */
    public List<Hyperlinks> getLinksVisited() {
        return linksVisited;
    }

    /**
     * Método que retorna uma lista de links não visitados.
     *
     * @return
     */
    public List<Hyperlinks> getLinksNotVisited() {
        return linksNotVisited;
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
     * Método que retorna todos os vértices (PageTitle).
     *
     * @return
     */
    public Iterable<Vertex<PageTitle>> getAllPageTitle() {
        return digraph.vertices();
    }

    /**
     * Método que retorna todos as arestas (Hyperlinks).
     *
     * @return
     */
    public Iterable<Edge<Hyperlinks, PageTitle>> getAllHyperlinks() {
        return digraph.edges();
    }

    /**
     * Método que retorna todos os vértices adjacentes a um vértice específico.
     *
     * @return
     */
    private Iterable<Vertex<PageTitle>> getAdjacents(Vertex<PageTitle> vLook) {
        Set<Vertex<PageTitle>> setAdj = new HashSet<>();
        for (Edge<Hyperlinks, PageTitle> edge : digraph.incidentEdges(vLook)) {
            setAdj.add(digraph.opposite(vLook, edge));
        }
        return setAdj;
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
     * @throws PageTitleException
     * @throws HyperlinksException
     */
    public void search(String url) throws IOException, PageTitleException, HyperlinksException {
        SpiderLeg wc = new SpiderLeg();
        wc.openUrlAndShowTitleAndLinks(url);

        linksNotVisited = wc.getLinksNotVisited();
        linksVisited = wc.getLinksVisited();
        pagesVisited = wc.getPagesVisited();

        if (maxPagesToSearch > linksNotVisited.size()) {
            throw new HyperlinksException("Max Range Exeed! You Can Only Search For " + linksNotVisited.size() + " Pages");
        }

        linksVisited.add(0, linksNotVisited.get(0));
        List<Hyperlinks> hl = new ArrayList<>(linksNotVisited);
        for (int i = 0; i < linksNotVisited.size(); i++) {
            if (linksNotVisited.get(i).getLink().equals(hl.get(0).getLink())) {
                linksNotVisited.remove(i);
            }
        }

        if (linksNotVisited.isEmpty()) {
            System.out.println("**** SORRY BUT THE PAGE " + pagesVisited.get(0).getPageTitleName() + " DONT HAVE ANY URL. **** \n \n");
        } else {
            int i = 1;
            while (pagesVisited.size() <= getMaxPagesToSearch()) {
                if (linksNotVisited.isEmpty()) {
                    wc.openUrlAndShowTitleAndLinks(linksVisited.get(i).getLink());
                    i++;
                } else {
                    wc.openUrlAndShowTitleAndLinks(linksNotVisited.get(0).getLink());
                    Hyperlinks link = linksNotVisited.get(0);
                    pagesVisited.get(0).addHyperlink(link);
                    linksNotVisited.remove(0);
                    linksVisited.add(link);
                }
            }
        }

        for (PageTitle p : pagesVisited) {
            try {
                addPageTitle(p);
            } catch (InvalidVertexException ex) {
                throw new PageTitleException("PageTitle with name does not exist");
            }
        }
        addRelation();
    }

    /**
     * Método que adiciona as relações (arestas) aos vértices.
     *
     * @throws PageTitleException
     * @throws HyperlinksException
     */
    public void addRelation() throws PageTitleException, HyperlinksException {
        try {
            for (int j = 1; j < pagesVisited.size(); j++) {
                addHyperLinks(pagesVisited.get(0), pagesVisited.get(j), linksVisited.get(j));
            }
        } catch (InvalidEdgeException ex) {
            throw new HyperlinksException("Link with the name does not exist");
        }
    }

    /**
     * Método que adiciona vértices.
     *
     * @param page
     * @throws PageTitleException
     */
    public void addPageTitle(PageTitle page) throws PageTitleException {
        try {
            digraph.insertVertex(page);
        } catch (InvalidVertexException e) {
            throw new PageTitleException("Website with name " + page.getPageTitleName() + " does not exist");
        }
    }

    /**
     * Método que liga dois vértices a uma aresta.
     *
     * @param page1
     * @param page2
     * @param link
     * @throws PageTitleException
     */
    public void addHyperLinks(PageTitle page1, PageTitle page2, Hyperlinks link) throws PageTitleException {

        if (link == null) {
            throw new PageTitleException("Hyperlink is null");
        }

        Vertex<PageTitle> a1 = checkPageTitle(page1);
        Vertex<PageTitle> a2 = checkPageTitle(page2);

        try {
            digraph.insertEdge(a1, a2, link);
        } catch (InvalidVertexException e) {
            throw new PageTitleException("The Hyperlink " + link.toString() + " already exists");
        }
    }

    /**
     * Verifica se o vértice já existe.
     *
     * @param page
     * @return
     * @throws PageTitleException
     */
    private Vertex<PageTitle> checkPageTitle(PageTitle page) throws PageTitleException {

        if (page == null) {
            throw new PageTitleException("Page title cannot be null");
        }

        Vertex<PageTitle> find = null;
        for (Vertex<PageTitle> v : digraph.vertices()) {
            if (v.element().equals(page)) { //equals was overriden in PageTitle!!
                find = v;
            }
        }

        if (find == null) {
            throw new PageTitleException("Website with name " + page.getPageTitleName() + " does not exist");
        }

        return find;
    }

    /**
     * Método que retorna uma lista de arestas entre dois vértices.
     *
     * @param page1
     * @param page2
     * @return
     * @throws PageTitleException
     */
    public List<Hyperlinks> getHyperlinksesBetween(PageTitle page1, PageTitle page2) throws PageTitleException {
        Vertex<PageTitle> p1 = checkPageTitle(page1);
        Vertex<PageTitle> p2 = checkPageTitle(page2);
        List<Hyperlinks> list = new ArrayList<>();
        if (!digraph.areAdjacent(p1, p2)) {
            return list;
        }
        Iterable<Edge<Hyperlinks, PageTitle>> it = digraph.incidentEdges(p1);
        for (Edge<Hyperlinks, PageTitle> edge : it) {
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
        for (Vertex<PageTitle> a1 : digraph.vertices()) {
            for (Vertex<PageTitle> a2 : digraph.vertices()) {
                if (a1.equals(a2)) {
                    break;
                }
                try {
                    if (!getHyperlinksesBetween(a1.element(), a2.element()).isEmpty()) {
                        str += "\n [" + a1.element().getPageTitleName() + "]" + " TO " + "[" + a2.element().getPageTitleName() + "]" + "\n";
                        List<Hyperlinks> l = new ArrayList<>();
                        l = getHyperlinksesBetween(a1.element(), a2.element());
                        for (int i = 0; i < l.size(); i++) {
                            str += "\t" + l.get(i).getLink() + "\n";
                        }
                    }
                } catch (PageTitleException ex) {
                    Logger.getLogger(WebCrawler.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }

        str += "\nNº Páginas Totais " + getPagesVisited().size();
        for (int i = 0; i < getPagesVisited().size(); i++) {
            str += "\nPáginas Visitadas ";
            if (i == 0) {
                str += pagesVisited.get(0) + " [ INICIAL ]";
            } else {
                str += pagesVisited.get(i);
            }

        }
        str += "\n\nNº Links Visitados " + getLinksVisited().size() + "\n"
                + "\nLinks Visitados " + getLinksVisited() + "\n"
                + "\nNº Links Não Visitados " + getLinksNotVisited().size() + "\n"
                + "\nLinks Não Visitados " + getLinksNotVisited() + "\n"
                + "\nVertices" + digraph.vertices() + "\n"
                + "\nEdges" + digraph.edges() + "\n"
                + "\nA página mais visitada foi ";
        int max = -1;
        PageTitle pg = new PageTitle();
        for (Vertex<PageTitle> p : digraph.vertices()) {
            if (max < digraph.incidentEdges(p).size()) {
                pg = p.element();
                max = digraph.incidentEdges(p).size();
            }
        }

        str += pg.getPageTitleName() + "\n";

        return str;
    }

    /**
     * Método que retorna o vértice dado um pageTitle.
     *
     * @param pi
     * @return
     */
    private Vertex<PageTitle> getVertex(PageTitle pi) {
        for (Vertex<PageTitle> ap : digraph.vertices()) {
            if (ap.element().equals(pi)) {
                return ap;
            }
        }
        return null;
    }
}
