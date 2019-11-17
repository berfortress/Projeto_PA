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
import adtgraph.InvalidEdgeException;
import adtgraph.InvalidVertexException;
import adtgraph.Vertex;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import enums.TypeModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import enums.Criteria;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author berna
 */
public class WebCrawler {
        private Set<PageTitle> pageTitle = new HashSet<PageTitle>();
	private List<Hyperlinks> links = new ArrayList<Hyperlinks>();
        public int maxPagesToSearch;
        private final Graph<PageTitle, Hyperlinks> graph;

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
        this.maxPagesToSearch = 4;
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
                nextUrl = links.get(0).getName();
                links.remove(0);

        } while (links.contains(nextUrl));
            //links.add(new Hyperlinks(nextUrl));
        return nextUrl;
    }
    
    public List<Hyperlinks> getLinks(){
        return links;
    }
	
    public void search(String url) throws IOException, PageTitleException, HyperlinksException{
        while (pageTitle.size() < getMaxPagesToSearch()) {
                String currentUrl;
                SpiderLeg wc = new SpiderLeg();		
                if (this.links.isEmpty()) {
                        currentUrl = url;
                        links.add(new Hyperlinks("Link Página Inicial",url));
                } else {
                        links.remove(0);
                        wc.openUrlAndShowTitleAndLinks(url, pageTitle, links);              
                }
        }
       
 // Método para remover links repetidos
        for (int i = 0; i < links.size(); i++) {
            String s1 = links.get(i).getName();
                for (int j = i+1; j < links.size(); j++) {
                        String s2 = links.get(j).getName();
                        if (s1.compareTo(s2)==0) {
                                links.remove(j);
                                j--;
                        }
                }
        }
        
        System.out.println("\t Links: (" + links.size() + ")");
        
        for (int i = 0; i < links.size(); i++) {
            System.out.println("\t \t " + "[" + links.get(i).getName().toUpperCase() + "]" + " " + links.get(i).getLink());
        }
        
        for(PageTitle p : pageTitle){
            try {
                addPageTitle(p);
            } catch (InvalidVertexException ex) {
                throw new PageTitleException("Website with name does not exist");
            }
        }
        
        List<PageTitle> list = new ArrayList<PageTitle>(pageTitle);
        
        for(Hyperlinks l : links){
            try {
                for (int i = 0; i < links.size(); i++) {
                     System.out.println(list.get(0) + " " + links.get(i).getName() + " " + links.get(i));
                     //addHyperLinks(list.get(0), new PageTitle(links.get(i).getName()),links.get(i));
                }
            } catch (InvalidEdgeException ex) {
                throw new HyperlinksException("Link with the name does not exist");
            }
        }
        System.out.println(graph.numEdges());
    }
    
//    public void test() throws PageTitleException{
//        List<Hyperlinks> linksList = new ArrayList<Hyperlinks>(links);
//        List<PageTitle> pageList = new ArrayList<PageTitle>(pageTitle);
//        for(int i = pageTitle.size() - 1; i >= 0; i--){
//            for(int j = 0; j < pageTitle.size() - 1 - i; j++){
//                for(int n = 0; n < links.size() - i; n++)
//                    addHyperLinks(pageList.get(i), pageList.get(j), linksList.get(n));
//            }
//        }
//    }
        
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
            throw new PageTitleException("The Hyperlink " + link.toString() + " already exists");
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
//
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