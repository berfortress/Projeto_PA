/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler;

import adtgraph.Digraph;
import java.util.List;
import adtgraph.Edge;
import adtgraph.GraphEdgeList;
import adtgraph.InvalidEdgeException;
import adtgraph.InvalidVertexException;
import adtgraph.Vertex;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import enums.Criteria;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bernardo
 */
public class WebCrawler {

    private List<Hyperlinks> linksVisited;
    private List<Hyperlinks> linksNotVisited;
    private int maxPagesToSearch;
    private List<PageTitle> pagesVisited;
    private final Digraph<PageTitle, Hyperlinks> digraph;

    public WebCrawler(int maxPagesToSearch) {
        this.maxPagesToSearch = maxPagesToSearch;
        this.digraph = new GraphEdgeList<>();
        this.linksVisited = new ArrayList<>();
        this.linksNotVisited = new ArrayList<>();
        this.pagesVisited = new ArrayList<>();
    }

    public WebCrawler() {
        this.maxPagesToSearch = 4;
        this.digraph = new GraphEdgeList<>();
        this.linksVisited = new ArrayList<>();
        this.linksNotVisited = new ArrayList<>();
        this.pagesVisited = new ArrayList<>();
    }

    public List<PageTitle> getPagesVisited() {
        return pagesVisited;
    }

    public List<Hyperlinks> getLinksVisited() {
        return linksVisited;
    }

    public List<Hyperlinks> getLinksNotVisited() {
        return linksNotVisited;
    }

    public int getMaxPagesToSearch() {
        return maxPagesToSearch;
    }

    public void setMaxPagesToSearch(int maxPagesToSearch) {
        this.maxPagesToSearch = maxPagesToSearch;
    }

    public Iterable<Vertex<PageTitle>> getAllPageTitle() {
        return digraph.vertices();
    }

    public Iterable<Vertex<PageTitle>> getAdjacents(Vertex<PageTitle> page) throws PageTitleException {

        checkPageTitle(page.element());
        List<Vertex<PageTitle>> pa = new ArrayList<>();

        for (Edge<Hyperlinks, PageTitle> p : digraph.incidentEdges(page)) {
            System.out.println("---------------------------");
            pa.add(digraph.opposite(page, p));
        }

        return pa;
    }

    private Iterable<PageTitle> BFS(Vertex<PageTitle> v) throws PageTitleException {
        List<PageTitle> path = new ArrayList<>();
        Set<Vertex<PageTitle>> visited = new HashSet<>();
        Queue<Vertex<PageTitle>> queue = new LinkedList<>();

        visited.add(v);
        queue.add(v);

        while (!queue.isEmpty()) {
            Vertex<PageTitle> vLook = queue.remove();
            path.add(vLook.element());
            for (Vertex<PageTitle> vAdj : getAdjacents(vLook)) {
                if (!visited.add(v)) {
                    visited.add(vAdj);
                    queue.add(vAdj);
                }
            }
        }
        return path;
    }

    public void search(String url) throws IOException, PageTitleException, HyperlinksException {
        SpiderLeg wc = new SpiderLeg();
        List<PageTitle> pages = new ArrayList<>();

        linksVisited.add(new Hyperlinks("Initial URL ", url));
        linksNotVisited = wc.openUrlAndShowTitleAndLinks(url, pagesVisited, linksNotVisited, linksVisited);

        if (linksNotVisited.isEmpty()) {
            System.out.println("**** SORRY BUT THE PAGE " + pagesVisited.get(0).getPageTitleName() + " DONT HAVE ANY URL. **** \n \n");
        } else {
            int i = 1;
            while (pagesVisited.size() <= getMaxPagesToSearch()) {
                if (linksNotVisited.isEmpty()) {
                    //wc.openUrlAndShowTitleAndLinks(linksVisited.get(i).getLink(), pagesVisited, linksNotVisited, linksVisited);
                    linksNotVisited = wc.openUrlAndShowTitleAndLinks(linksVisited.get(i).getLink(), pagesVisited, linksNotVisited, linksVisited);
                    i++;
                } else {
                    wc.openUrlAndShowTitle(linksNotVisited.get(0).getLink(), pagesVisited);
                    pages.add(pagesVisited.get(i));
                    Hyperlinks link = linksNotVisited.get(0);
                    linksNotVisited.remove(0);
                    linksVisited.add(link); 
//                wc.openUrlAndShowTitle(notVisitedLinks.get(0).getLink(), pages);
//                Hyperlinks link = notVisitedLinks.get(0);
//                notVisitedLinks.remove(0);
//                linksVisited.add(link);
                }
            }

            for (PageTitle p : pagesVisited) {
                try {
                    addPageTitle(p);
                } catch (InvalidVertexException ex) {
                    throw new PageTitleException("PageTitle with name does not exist");
                }
            }
            //addRelation(pages, visitedLinks);
        }
<<<<<<< HEAD

=======
        
        for(Hyperlinks l: linksNotVisited){
            System.out.println(l.getId() + " " + l.getName() + " " + l.getLink());
        }

        addRelation();
>>>>>>> parent of 416c778... Alterações GOD_FABI
    }

    public void addRelation(List<PageTitle> pagesVisited, List<Hyperlinks> linksVisited) throws PageTitleException, HyperlinksException {
        try {
            for (int j = 1; j < pagesVisited.size(); j++) {
                for (int k = 0; k < linksVisited.size(); k++) {
                    addHyperLinks(pagesVisited.get(0), pagesVisited.get(j), linksVisited.get(k));
                }
            }
        } catch (InvalidEdgeException ex) {
            throw new HyperlinksException("Link with the name does not exist");
        }
    }

    public void addPageTitle(PageTitle page) throws PageTitleException {
        try {
            digraph.insertVertex(page);
        } catch (InvalidVertexException e) {
            throw new PageTitleException("Website with name " + page.getPageTitleName() + " does not exist");
        }
    }

    public void addHyperLinks(PageTitle page1, PageTitle page2, Hyperlinks link) throws PageTitleException {

        if (link == null) {
            throw new PageTitleException("Hyper link is null");
        }

        Vertex<PageTitle> a1 = checkPageTitle(page1);
        Vertex<PageTitle> a2 = checkPageTitle(page2);

        try {
            digraph.insertEdge(a1, a2, link);
        } catch (InvalidVertexException e) {
            throw new PageTitleException("The Hyperlink " + link.toString() + " already exists");
        }
    }

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
                        str += "\n \t" + "[" + a1.element().getPageTitleName() + "]" + " TO " + "[" + a2.element().getPageTitleName() + "]" + "\n";
                        List<Hyperlinks> l = new ArrayList<>();
                        l = getHyperlinksesBetween(a1.element(), a2.element());
                        for (int i = 0; i < l.size(); i++) {
                            str += "\t [" + l.get(i).getName() + "] " + l.get(i).getLink() + "\n";
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
        str += "\nNº Links Visitados " + getLinksVisited().size() + "\nLinks Visitados " + getLinksVisited() + "\nVertices" + digraph.vertices() + "\nEdges" + digraph.edges();
        return str;
    }

    private Vertex<PageTitle> getVertex(PageTitle pi) {
        for (Vertex<PageTitle> ap : digraph.vertices()) {
            if (ap.element().equals(pi)) {
                return ap;
            }
        }
        return null;
    }

    public int minimumCostPath(Criteria criteria, PageTitle orig, PageTitle dst, List<PageTitle> pontos, List<Hyperlinks> path) throws PageTitleException {
        if (orig == null || dst == null) {
            throw new PageTitleException("Invalid - Ponto de interesse");
        }

        HashMap<Vertex<PageTitle>, Double> costs = new HashMap();
        HashMap<Vertex<PageTitle>, Vertex<PageTitle>> predecessors = new HashMap();
        HashMap<Vertex<PageTitle>, Edge<Hyperlinks, PageTitle>> edgesP = new HashMap();

        Vertex<PageTitle> iVertex = getVertex(orig);

        if (iVertex != null) {
            dijkstra(criteria, iVertex, costs, predecessors, edgesP);
        }

        double cost = costs.get(getVertex(dst));

        Vertex<PageTitle> fVertex = getVertex(dst);
        do {
            pontos.add(0, fVertex.element());
            path.add(0, edgesP.get(fVertex).element());
            fVertex = predecessors.get(fVertex);

        } while (fVertex != iVertex);

        pontos.add(0, orig);

        return (int) cost;
    }

    private void dijkstra(Criteria criteria, Vertex<PageTitle> orig,
            Map<Vertex<PageTitle>, Double> costs,
            Map<Vertex<PageTitle>, Vertex<PageTitle>> predecessors,
            Map<Vertex<PageTitle>, Edge<Hyperlinks, PageTitle>> edgesP) {

        List<Vertex<PageTitle>> unvisited = new ArrayList();

        for (Vertex<PageTitle> page2 : digraph.vertices()) {
            unvisited.add(page2);
            costs.put(page2, Double.MAX_VALUE);
            predecessors.put(page2, null);
        }

        costs.put(orig, 0.0);

        while (!unvisited.isEmpty()) {
            Vertex<PageTitle> lowCostVertex = findLowerCostVertex(unvisited, costs);
            unvisited.remove(lowCostVertex);
            for (Edge<Hyperlinks, PageTitle> conexao : digraph.incidentEdges(lowCostVertex)) {
                Vertex<PageTitle> opposite = digraph.opposite(lowCostVertex, conexao);
                if (unvisited.contains(opposite)) {

                    double rotaCusto = 0;
                    switch (criteria) {
                        case DISTANCE:
                            rotaCusto = conexao.element().getDistance() + costs.get(lowCostVertex);
                            break;
                    }
                    if (costs.get(opposite) > rotaCusto) {
                        costs.put(opposite, rotaCusto);
                        predecessors.put(opposite, lowCostVertex);
                        edgesP.put(opposite, conexao);
                    }
                }
            }
        }
    }

    private Vertex<PageTitle> findLowerCostVertex(List<Vertex<PageTitle>> unvisited, Map<Vertex<PageTitle>, Double> costs) {
        double min = Double.MAX_VALUE;
        Vertex<PageTitle> minCostVertex = null;
        for (Vertex<PageTitle> vertex : unvisited) {
            if (costs.get(vertex) <= min) {
                minCostVertex = vertex;
                min = costs.get(vertex);
            }
        }
        return minCostVertex;
    }
}
