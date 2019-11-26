/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler;

import models.Website;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author fabio
 */
public class PageTitleTest {
    private Website page;
    
    @Before
    public void setUp() {
        page = new Website("MoodleIPS: Todas as disciplinas", "https://moodle.ips.pt/1920/course/index.php?categoryid=7");
    }

    /**
     * Test of getWebsiteName method, of class Website.
     */
    @Test
    public void testGetPageTitleName() {
        assertEquals("A implementação não está a atribuir o titulo da página correto", true, page.getWebsiteName().equals("MoodleIPS: Todas as disciplinas"));
    }

    /**
     * Test of getURL method, of class Website.
     */
    @Test
    public void testGetPageAddress() {
        assertEquals("A implementação não está a atribuir o adereço da página correto", true, page.getURL().equals("https://moodle.ips.pt/1920/course/index.php?categoryid=7"));
    }

    /**
     * Test of setURL method, of class Website.
     */
    @Test
    public void testSetPageAddress() {
        page.setURL("hello");
        assertEquals("A implementação não está a atribuir o adereço da página correto", true, page.getURL().equals("hello"));
    }

    /**
     * Test of setWebsiteName method, of class Website.
     */
    @Test
    public void testSetPageTitleName() {
        page.setWebsiteName("hello");
        assertEquals("A implementação não está a atribuir o titulo da página correto", true, page.getWebsiteName().equals("hello"));
    }

    /**
     * Test of getId method, of class Website.
     */
    @Test
    public void testGetId() {
        assertEquals("A implementação não está a atribuir o id correto", true, page.getId() > 0);
    }    
}
