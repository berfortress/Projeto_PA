/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webCrawler;

import adtgraph.InvalidVertexException;
import adtgraph.Vertex;
import exceptions.LinkException;
import exceptions.WebsiteException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javaFX_mainScreens.AutomaticFX;
import javaFX_mainScreens.Standards;
import logger.Logger;
import models.Link;
import models.Website;

public class Automatic extends WebCrawler {

    public Automatic(int maxPagesToSearch) {
        super(maxPagesToSearch);
    }

    public Automatic() {
        super();
    }

    @Override
    public void search(String url) throws IOException, WebsiteException, LinkException {
        openUrlAndGetTitleAndLinks(url);
        if (super.getLinksVisited().isEmpty()) {
            System.out.println("\n**** SORRY BUT THE PAGE " + super.webSitesVisited().get(0).getWebsiteName() + " DONT HAVE ANY URL. **** \n");
            AutomaticFX.setResults(String.format(" SORRY BUT THE PAGE \"%s\" DONT HAVE ANY LINKS.  \n", super.webSitesVisited().get(0).getWebsiteName().toUpperCase()));
        } else {
            if (getMaxPagesToSearch() == 0) {
                firstSearch();
            } else if (getMaxPagesToSearch() > 0) {
                firstSearch();
                int count1 = 1;
                try {
                    while (super.webSitesVisited().size() < getMaxPagesToSearch()) {
                        recursiveSearch(count1, url);
                        count1++;
                    }
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Cannot search " + getMaxPagesToSearch() + " can only search " + super.webSitesVisited().size());
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException");
                }
            }
        }
        AutomaticFX.setResults(toString());
    }

    private void firstSearch() throws WebsiteException {
        for (int i = 0; i < super.getLinksVisited().size(); i++) {
            openUrlAndGetTitle(super.getLinksVisited().get(i).getLink());
        }
        int i = 1;
        int j = 0;
        while (i < super.webSitesVisited().size() && j < super.getLinksVisited().size()) {
            addEdge(super.webSitesVisited().get(0), super.webSitesVisited().get(i), super.getLinksVisited().get(j));
            j++;
            i++;
        }
    }

    /**
     * Retorna uma String com a informação acerca do webCrawler.
     *
     * @return
     */
    @Override
    public String toString() {
        String str = "\n WEB CRAWLER (" + digraph.numVertices() + " pagesTitles | " + digraph.numEdges() + " links) \n";
        str = toStringGetEdgesBetween(str);
        str = toStringPagesVisited(str);
        str = toStringStats(str);
        return str;
    }

    private String toStringStats(String str) throws InvalidVertexException {
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

    private String toStringPagesVisited(String str) {
        List<String> list = new ArrayList<>();
        str += "\nNº Páginas Totais " + webSitesVisited().size();
        for (int i = 0; i < webSitesVisited().size(); i++) {
            if (i == 0) {
                str += "\nPágina Visitada ";
                str += super.webSitesVisited().get(0) + " [ INICIAL ]";
                list.add(super.webSitesVisited().get(0).getURL());
            } else {
                if (!list.contains(super.webSitesVisited().get(i).getURL())) {
                    str += "\nPágina Visitada ";
                    str += super.webSitesVisited().get(i);
                    list.add(super.webSitesVisited().get(i).getURL());
                }
            }
        }
        return str;
    }

    private String toStringGetEdgesBetween(String str) {
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
                    Logger logger = Logger.getInstance();
                    logger.writeToLog(a1.element(), a2.element(), (WebCrawler) this);
                } catch (WebsiteException ex) {
                    Logger logger = Logger.getInstance();
                    logger.writeToLog(this, Standards.log_Exception);
                }
            }
        }
        return str;
    }
}
