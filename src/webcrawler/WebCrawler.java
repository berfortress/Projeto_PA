/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import adtgraph.Edge;
import adtgraph.Graph;
import adtgraph.Digraph;
import adtgraph.GraphEdgeList;
import adtgraph.InvalidVertexException;
import adtgraph.Vertex;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import enums.TypeModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author berna
 */
public class WebCrawler {
        private Set<String> pagesVisited = new HashSet<String>();
        private List<PageTitle> pageTitle = new LinkedList<PageTitle>();
	private List<Hyperlinks> links = new LinkedList<Hyperlinks>();
        private List<Hyperlinks> pagesNotFound = new LinkedList<Hyperlinks>();
        public int maxPagesToSearch;
        private final Graph<PageTitle, Hyperlinks> graph;
        
        public enum Criteria {
        DISTANCE, 
        COST;

        public String getUnit() {
            switch(this) {
                case COST: return "€";
                case DISTANCE: return "Links";
            }
            return "Unknown";
        }
    };

    private Vertex<PageTitle> checkPageTitle(PageTitle page) throws PageTitleException {
        
        if( page == null) throw new PageTitleException("Page title cannot be null");

        Vertex<PageTitle> find = null;
        for (Vertex<PageTitle> v : graph.vertices()) {
            if( v.element().equals(page)) { //equals was overriden in PageTitle!!
                find = v;
            }
        }

        if( find == null) 
            throw new PageTitleException("Website with name " + page.getPageTitleName() + " does not exist");

        return find;
    }
    
    public WebCrawler(int maxPagesToSearch) {
        this.maxPagesToSearch = maxPagesToSearch;
        this.graph = new GraphEdgeList<>();
    }

    public WebCrawler() {
        this.maxPagesToSearch = 100;
        this.graph = new GraphEdgeList<>();
    }
    
    public int getMaxPagesToSearch() {
        return maxPagesToSearch;
    }

    public void setMaxPagesToSearch(int maxPagesToSearch) {
        this.maxPagesToSearch = maxPagesToSearch;
    }
    
    public Iterable<Vertex<PageTitle>> getAllPageTitle(){
        return graph.vertices();
    }
    
    private String nextUrl() {
        String nextUrl;
        do {
                nextUrl = this.links.remove(0).getLinkName();
        } while (this.pagesVisited.contains(nextUrl));
        this.pagesVisited.add(nextUrl);
        return nextUrl;
    }
	
    public void search(String url) throws IOException, PageTitleException{
        while (this.pagesVisited.size() < getMaxPagesToSearch()) {
                String currentUrl;
                SpiderLeg wc = new SpiderLeg();		
                if (this.links.isEmpty()) {
                        currentUrl = url;
                        this.pagesVisited.add(url);
                } else {
                        currentUrl = this.nextUrl();
                }
                wc.openUrlAndShowTitleAndLinks(currentUrl, pageTitle, links, pagesNotFound);// Lots of stuff happening here. Look at the crawl method in SpiderLeg
        }
        
        for(PageTitle p : pageTitle){
            try {
                addPageTitle(p);
            } catch (InvalidVertexException ex) {
                throw new PageTitleException("Website with name does not exist");
            }
        }
    }
        
    public void addPageTitle(PageTitle page) throws PageTitleException{
        try {
            graph.insertVertex(page);
        } catch (InvalidVertexException e) {
            throw new PageTitleException("Website with name " + page.getPageTitleName() + " does not exist");
        }
    }
    
    public void addHyperLinks(PageTitle page1, PageTitle page2, Hyperlinks link) 
        throws PageTitleException{
        
        if( link == null) throw new PageTitleException("Hyper link is null");
        
        Vertex<PageTitle> a1 = checkPageTitle(page1);
        Vertex<PageTitle> a2 = checkPageTitle(page2);
        
        try {
            graph.insertEdge(a1, a2, link);
        } catch (InvalidVertexException e) {
            throw new PageTitleException("The Hyper link " + link.toString() + " already exists");
        }
    }
    
    public List<Hyperlinks> getHyperlinksesBetween(PageTitle page1, PageTitle page2) throws PageTitleException {
        Vertex<PageTitle> p1 = checkPageTitle(page1);
        Vertex<PageTitle> p2 = checkPageTitle(page2);
        List<Hyperlinks> list = new ArrayList<>();
        if (!graph.areAdjacent(p1, p2)) {
            return list;
        }
        Iterable<Edge<Hyperlinks, PageTitle>> it = graph.incidentEdges(p1);
        for (Edge<Hyperlinks, PageTitle> edge : it) {
            if (graph.opposite(p1, edge) == p2) {
                list.add(edge.element());
            }
        }
        return list;
    }
    
    @Override
    public String toString() {
        String str = "\nWEB CRAWLER( " + graph.numVertices() + " Pages Title | "+ graph.numEdges() + " Hyperlinks)\n";

//        for (Vertex<PageTitle> p1 : graph.vertices()) {
//            for (Vertex<PageTitle> p2 : graph.vertices()) {
//                if (p1.equals(p2)) {
//                    //System.out.println("\t sem Rotas \n");
//                    break;
//                }
//                str += "\n" + p2.element().toString() + " TO " + p1.element().toString();
//                try {
//                    if(getHyperlinksesBetween(p1.element(), p2.element()).isEmpty()){
//                        str += "\n\t(no flights)\n";
//                    }else{
//                        str += "\n\t" + p2.element().toString() + "\n";
//                        str += "\t" + p1.element().toString() + "\n";
//                    }
//                } catch (PageTitleException ex) {
//                    Logger.getLogger(WebCrawler.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                }
//            }
        return str;
    }
    
    private Vertex<PageTitle> getVertex(PageTitle pi) {
        for (Vertex<PageTitle> ap : graph.vertices()) {
            if (ap.element().equals(pi)) {
                return ap;
            }
        }
        return null;
    }
  
    public int minimumCostPath(Criteria criteria, PageTitle orig, PageTitle dst,
            List<PageTitle> pontos, List<Hyperlinks> path)
            throws PageTitleException {

        if (orig == null || dst == null) {
            throw new PageTitleException("Invalid - Ponto de interesse");
        }

        HashMap<Vertex<PageTitle>, Double> costs = new HashMap();
        HashMap<Vertex<PageTitle>, Vertex<PageTitle>> predecessors = new HashMap();
        HashMap<Vertex<PageTitle>, Edge<Hyperlinks, PageTitle>> edgesP = new HashMap();

        //pontos.clear();
        //path.clear();
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

        for (Vertex<PageTitle> page2 : graph.vertices()) {
            unvisited.add(page2);
            costs.put(page2, Double.MAX_VALUE);
            predecessors.put(page2, null);
        }

        costs.put(orig, 0.0);

        while (!unvisited.isEmpty()) {
            Vertex<PageTitle> lowCostVertex = findLowerCostVertex(unvisited, costs);
            unvisited.remove(lowCostVertex);
            for (Edge<Hyperlinks, PageTitle> conexao : graph.incidentEdges(lowCostVertex)) {
                Vertex<PageTitle> opposite = graph.opposite(lowCostVertex, conexao);
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


    private Vertex<PageTitle> findLowerCostVertex(List<Vertex<PageTitle>> unvisited, 
            Map<Vertex<PageTitle>, Double> costs) {
        
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