/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webCrawler;

import adtgraph.Digraph;
import adtgraph.Edge;
import adtgraph.GraphEdgeList;
import adtgraph.InvalidVertexException;
import adtgraph.Vertex;
import exceptions.LinkException;
import exceptions.WebsiteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import models.Link;
import models.Website;

/**
 *
 * @author berna
 */
public class DijkstraGraph {
    
    protected final Digraph<Website, Link> digraph;

    public DijkstraGraph() {
        this.digraph = new GraphEdgeList<>();
    }

    /**
     * Método que retorna todos os vértices (Websites).
     *
     * @return
     */
    public Iterable<Vertex<Website>> getAllVertices() {
        return digraph.vertices();
    }

    /**
     * Método que retorna todos as arestas (Links).
     *
     * @return
     */
    public Iterable<Edge<Link, Website>> getAllEdges() {
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
            throw new WebsiteException("Link is null");
        }
        Vertex<Website> a1 = checkVertex(page1);
        Vertex<Website> a2 = checkVertex(page2);
        try {
            digraph.insertEdge(a1, a2, link);
        } catch (InvalidVertexException e) {
            throw new WebsiteException("The Link " + link.toString() + " already exists");
        }
    }

    /**
     * Verifica se o vértice já existe.
     *
     * @param page
     * @return
     * @throws WebsiteException
     */
    protected Vertex<Website> checkVertex(Website page) throws WebsiteException {
        if (page == null) {
            throw new WebsiteException("Website cannot be null");
        }
        Vertex<Website> find = null;
        for (Vertex<Website> v : digraph.vertices()) {
            if (v.element().equals(page)) {
                //equals was overriden in Website!!
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
     * Método que retorna o vértice dado um webSite.
     *
     * @param pi
     * @return
     */
    public Vertex<Website> getVertex(Website pi) {
        for (Vertex<Website> ap : digraph.vertices()) {
            if (ap.element().equals(pi)) {
                return ap;
            }
        }
        return null;
    }

    /**
     * Método que retorna o vértice dado um webSite.
     *
     * @param pi
     * @return
     */
    public Edge<Link, Website> getEdge(Link pi) {
        for (Edge<Link, Website> ap : digraph.edges()) {
            if (ap.element().equals(pi)) {
                return ap;
            }
        }
        return null;
    }

    /**
     * Método que retorna uma lista de websites adjacentes
     * @param w
     * @return
     */
    public List<Vertex<Website>> getAdjacents(Vertex<Website> w) {
        List<Vertex<Website>> sites = new ArrayList<>();
        for (Edge<Link, Website> link : digraph.incidentEdges(w)) {
            Vertex<Website> web2 = digraph.opposite(w, link);
            sites.add(web2);
        }
        return sites;
    }

    /**
     * Método que retorna uma lista de websites adjacentes
     *
     * @param w
     * @return
     */
    public List<Website> getAdjacentsElem(Website w) {
        Vertex<Website> vertexWeb = getVertex(w);
        List<Website> sites = new ArrayList<>();
        for (Edge<Link, Website> link : digraph.incidentEdges(vertexWeb)) {
            Vertex<Website> web2 = digraph.opposite(vertexWeb, link);
            sites.add(web2.element());
        }
        return sites;
    }

    public Digraph<Website, Link> getDigraph() {
        return digraph;
    }

    /**
     * Método principal do Dijsktra
     *
     * @param orig
     * @param dst
     * @param pontos
     * @param path
     * @return
     * @throws LinkException
     */
    public int minimumCostPath(Website orig, Website dst, List<Website> pontos, List<Link> path) throws LinkException {
        if (orig == null || dst == null) {
            throw new LinkException("Invalid - Ponto de interesse");
        }
        HashMap<Vertex<Website>, Double> costs = new HashMap();
        HashMap<Vertex<Website>, Vertex<Website>> predecessors = new HashMap();
        HashMap<Vertex<Website>, Edge<Link, Website>> edgesP = new HashMap();
        Vertex<Website> iVertex = getVertex(orig);
        if (iVertex != null) {
            dijkstra(iVertex, costs, predecessors, edgesP);
        }
        double cost = costs.get(getVertex(dst));
        Vertex<Website> fVertex = getVertex(dst);
        do {
            pontos.add(0, fVertex.element());
            path.add(0, edgesP.get(fVertex).element());
            fVertex = predecessors.get(fVertex);
        } while (fVertex != iVertex);
        pontos.add(0, orig);
        return (int) cost;
    }

    /**
     * Método do Dijsktra
     *
     * @param unvisited
     * @param costs
     * @return
     */
    protected Vertex<Website> findLowerCostVertex(List<Vertex<Website>> unvisited, Map<Vertex<Website>, Double> costs) {
        double min = Double.MAX_VALUE;
        Vertex<Website> minCostVertex = null;
        for (Vertex<Website> vertex : unvisited) {
            if (costs.get(vertex) <= min) {
                minCostVertex = vertex;
                min = costs.get(vertex);
            }
        }
        return minCostVertex;
    }

    /**
     * Método Dijsktra
     *
     * @param orig
     * @param costs
     * @param predecessors
     * @param edgesP
     */
    protected void dijkstra(Vertex<Website> orig, Map<Vertex<Website>, Double> costs, Map<Vertex<Website>, Vertex<Website>> predecessors, Map<Vertex<Website>, Edge<Link, Website>> edgesP) {
        List<Vertex<Website>> unvisited = new ArrayList();
        for (Vertex<Website> webSite : digraph.vertices()) {
            unvisited.add(webSite);
            costs.put(webSite, Double.MAX_VALUE);
            predecessors.put(webSite, null);
        }
        costs.put(orig, 0.0);
        while (!unvisited.isEmpty()) {
            Vertex<Website> lowCostVertex = findLowerCostVertex(unvisited, costs);
            unvisited.remove(lowCostVertex);
            for (Edge<Link, Website> connection : digraph.incidentEdges(lowCostVertex)) {
                Vertex<Website> opposite = digraph.opposite(lowCostVertex, connection);
                if (unvisited.contains(opposite)) {
                    double cost = 0;
                    cost = connection.element().getDistance() + costs.get(lowCostVertex);
                    if (costs.get(opposite) > cost) {
                        costs.put(opposite, cost);
                        predecessors.put(opposite, lowCostVertex);
                        edgesP.put(opposite, connection);
                    }
                }
            }
        }
    }
    
}
