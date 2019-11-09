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
import enums.TypeModel;
import java.io.IOException;

/**
 *
 * @author berna
 */
public class WebCrawler {
        private Set<String> pagesVisited = new HashSet<String>();
	private List<String> pagesToVisit = new LinkedList<String>();
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
            throw new WebCrawlerException("Website with name " + page.getPageTitleName() + " does not exist");

        return find;
    }
        
    public void addPageTitle(PageTitle page) throws PageTitleException{
        Vertex<PageTitle> page1 = checkPageTitle(page);
        try {
            graph.insertVertex(page1.element());
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
    
    private String nextUrl() {
        String nextUrl;
        do {
                nextUrl = this.pagesToVisit.remove(0);
        } while (this.pagesVisited.contains(nextUrl));
        this.pagesVisited.add(nextUrl);
        return nextUrl;
    }
	
    public void search(String url) throws IOException{
		while (this.pagesVisited.size() < getMaxPagesToSearch()) {
			String currentUrl;
			SpiderLeg wc = new SpiderLeg();		
			if (this.pagesToVisit.isEmpty()) {
				currentUrl = url;
				this.pagesVisited.add(url);
			} else {
				currentUrl = this.nextUrl();
			}
			wc.openUrlAndShowTitleAndLinks(currentUrl);// Lots of stuff happening here. Look at the crawl method in SpiderLeg
			this.pagesToVisit.addAll(wc.getLinks());
		}
		System.out.println(String.format("\n **Done** Visited %s web page(s)", this.pagesVisited.size())); 
	}
}