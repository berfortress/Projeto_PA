/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler;

import models.Link;
import models.LinkException;
import models.WebsiteException;
import models.Website;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author fabio e bernardo
 */

public class WebCrawlerTest {
    private WebCrawler wc;
    
    @Before
    public void setUp() {
        wc = new WebCrawler(4);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of webSitesVisited method, of class WebCrawler2.
     */
    @Test
    public void testGetPagesVisited() throws IOException, WebsiteException, LinkException {
        assertEquals("A implementação não retorna vazio após a sua criação 01", true, wc.webSitesVisited().isEmpty());
        wc.automatic("https://moodle.ips.pt/1920/course/index.php?categoryid=7");
        assertEquals("A implementação não retorna vazio após a sua criação 02", false, wc.webSitesVisited().isEmpty());
    }

    /**
     * Test of getLinksVisited method, of class WebCrawler2.
     */
    @Test
    public void testGetLinksVisited() throws IOException, WebsiteException, LinkException{
        assertEquals("A implementação não retorna vazio após a sua criação", true, wc.getLinksVisited().isEmpty());
        wc.automatic("https://moodle.ips.pt/1920/course/index.php?categoryid=7");
        assertEquals("A implementação não retorna vazio após a sua criação", false, wc.getLinksVisited().isEmpty());
    }

    /**
     * Test of getMaxPagesToSearch method, of class WebCrawler2.
     */
    @Test
    public void testGetMaxPagesToSearch() {
        assertEquals("O número maximo de paginas não está correto, pois é menor que 0", true, wc.getMaxPagesToSearch() > 0);
    }

    /**
     * Test of getAllWebsites method, of class WebCrawler2.
     */
    @Test
    public void testGetAllPageTitle() {
        assertEquals("O objeto da classe digrafo não pode ser null", true, wc.getAllWebsites() != null);
    }

    /**
     * Test of search method, of class WebCrawler2.
     */
    @Test
    public void testSearch() throws Exception {
        wc.automatic("https://moodle.ips.pt/1920/course/index.php?categoryid=7");
        assertEquals("A implementação não retorna vazio após a sua criação 02", false, wc.webSitesVisited().isEmpty());
        assertEquals("A implementação não retorna vazio após a sua criação", false, wc.getLinksVisited().isEmpty());      
    }

    /**
     * Test of addPageTitle method, of class WebCrawler2.
     */
    @Test
    public void testAddPageTitle() throws Exception {
        Website p = new Website();
        wc.addVertex(p);
        assertEquals("A implementação não está a adicionar o elemento", true, wc.getAllWebsites() != null);
    }

    /**
     * Test of addHyperLinks method, of class WebCrawler2.
     */
    @Test
    public void testAddHyperLinks() throws Exception {
        Website p1 = new Website();
        Website p2 = new Website();
        wc.addVertex(p1);
        wc.addVertex(p2);
        Link h1 = new Link();
        wc.addEdge(p1, p2, h1);
        assertEquals("A implementação não está a adicionar o elemento", true, wc.getAllLinks() != null);
    }

    /**
     * Test of getHyperlinksesBetween method, of class WebCrawler2.
     */
    @Test
    public void testGetHyperlinksesBetween() throws Exception {
        Website p1 = new Website();
        Website p2 = new Website();
        wc.addVertex(p1);
        wc.addVertex(p2);
        Link h1 = new Link();
        wc.addEdge(p1, p2, h1);
        assertEquals("A implementação não está a verificar se o elemento tem arestas entre os vertices", true, wc.getEdgesBetween(p1, p2) != null);
    }    
}
