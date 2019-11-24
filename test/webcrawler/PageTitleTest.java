/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler;

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
public class PageTitleTest {
    private PageTitle page;
    
    @Before
    public void setUp() {
        page = new PageTitle("MoodleIPS: Todas as disciplinas", "https://moodle.ips.pt/1920/course/index.php?categoryid=7");
    }

    /**
     * Test of getPageTitleName method, of class PageTitle.
     */
    @Test
    public void testGetPageTitleName() {
        assertEquals("A implementação não está a atribuir o titulo da página correto", true, page.getPageTitleName().equals("MoodleIPS: Todas as disciplinas"));
    }

    /**
     * Test of getPageAddress method, of class PageTitle.
     */
    @Test
    public void testGetPageAddress() {
        assertEquals("A implementação não está a atribuir o adereço da página correto", true, page.getPageAddress().equals("https://moodle.ips.pt/1920/course/index.php?categoryid=7"));
    }

    /**
     * Test of setPageAddress method, of class PageTitle.
     */
    @Test
    public void testSetPageAddress() {
        page.setPageAddress("hello");
        assertEquals("A implementação não está a atribuir o adereço da página correto", true, page.getPageAddress().equals("hello"));
    }

    /**
     * Test of setPageTitleName method, of class PageTitle.
     */
    @Test
    public void testSetPageTitleName() {
        page.setPageTitleName("hello");
        assertEquals("A implementação não está a atribuir o titulo da página correto", true, page.getPageTitleName().equals("hello"));
    }

    /**
     * Test of getId method, of class PageTitle.
     */
    @Test
    public void testGetId() {
        assertEquals("A implementação não está a atribuir o id correto", true, page.getId() > 0);
    }    
}
