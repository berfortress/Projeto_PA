/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler;

import adtgraph.Vertex;
import enums.Criteria;
import java.io.IOException;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author fabio
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
     * Test of getPagesVisited method, of class WebCrawler.
     */
    @Test
    public void testGetPagesVisited() throws IOException, PageTitleException, HyperlinksException {
        assertEquals("A implementação não retorna vazio após a sua criação 01", true, wc.getPagesVisited().isEmpty());
        wc.search("https://moodle.ips.pt/1920/course/index.php?categoryid=7");
        assertEquals("A implementação não retorna vazio após a sua criação 02", false, wc.getPagesVisited().isEmpty());
    }

    /**
     * Test of getLinksVisited method, of class WebCrawler.
     */
    @Test
    public void testGetLinksVisited() throws IOException, PageTitleException, HyperlinksException{
        assertEquals("A implementação não retorna vazio após a sua criação", true, wc.getLinksVisited().isEmpty());
        wc.search("https://moodle.ips.pt/1920/course/index.php?categoryid=7");
        assertEquals("A implementação não retorna vazio após a sua criação", false, wc.getLinksVisited().isEmpty());
    }

    /**
     * Test of getLinksNotVisited method, of class WebCrawler.
     */
    @Test
    public void testGetLinksNotVisited() throws IOException, PageTitleException, HyperlinksException {
        assertEquals("A implementação não retorna vazio após a sua criação", true, wc.getLinksNotVisited().isEmpty());
        wc.search("https://moodle.ips.pt/1920/course/index.php?categoryid=7");
        assertEquals("A implementação não retorna vazio após a sua criação", false, wc.getLinksNotVisited().isEmpty());
    }

    /**
     * Test of getMaxPagesToSearch method, of class WebCrawler.
     */
    @Test
    public void testGetMaxPagesToSearch() {
        assertEquals("O número maximo de paginas não está correto, pois é menor que 0", true, wc.getMaxPagesToSearch() > 0);
    }

    /**
     * Test of getAllPageTitle method, of class WebCrawler.
     */
    @Test
    public void testGetAllPageTitle() {
        assertEquals("O objeto da classe digrafo não pode ser null", true, wc.getAllPageTitle() != null);
    }

    /**
     * Test of search method, of class WebCrawler.
     */
    @Test
    public void testSearch() throws Exception {
        wc.search("https://moodle.ips.pt/1920/course/index.php?categoryid=7");
        assertEquals("A implementação não retorna vazio após a sua criação 02", false, wc.getPagesVisited().isEmpty());
        assertEquals("A implementação não retorna vazio após a sua criação", false, wc.getLinksVisited().isEmpty());
        assertEquals("A implementação não retorna vazio após a sua criação", false, wc.getLinksNotVisited().isEmpty());
        
        
    }

    /**
     * Test of addRelation method, of class WebCrawler.
     */
    @Test
    public void testAddRelation() throws Exception {
        wc.addRelation();
        assertEquals("A implementação não retorna vazio após a sua criação", true, wc.getAllHyperlinks() != null);
    }

    /**
     * Test of addPageTitle method, of class WebCrawler.
     */
    @Test
    public void testAddPageTitle() throws Exception {
        PageTitle p = new PageTitle();
        wc.addPageTitle(p);
        assertEquals("A implementação não está a adicionar o elemento", true, wc.getAllPageTitle() != null);
    }

    /**
     * Test of addHyperLinks method, of class WebCrawler.
     */
    @Test
    public void testAddHyperLinks() throws Exception {
        PageTitle p1 = new PageTitle();
        PageTitle p2 = new PageTitle();
        wc.addPageTitle(p1);
        wc.addPageTitle(p2);
        Hyperlinks h1 = new Hyperlinks();
        wc.addHyperLinks(p1, p2, h1);
        assertEquals("A implementação não está a adicionar o elemento", true, wc.getAllHyperlinks() != null);
    }

    /**
     * Test of getHyperlinksesBetween method, of class WebCrawler.
     */
    @Test
    public void testGetHyperlinksesBetween() throws Exception {
        PageTitle p1 = new PageTitle();
        PageTitle p2 = new PageTitle();
        wc.addPageTitle(p1);
        wc.addPageTitle(p2);
        Hyperlinks h1 = new Hyperlinks();
        wc.addHyperLinks(p1, p2, h1);
        assertEquals("A implementação não está a verificar se o elemento tem arestas entre os vertices", true, wc.getHyperlinksesBetween(p1, p2) != null);
    }    
}
