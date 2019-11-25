/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler;

import models.Hyperlinks;
import models.PageTitle;
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
public class HyperlinksTest {
    private Hyperlinks hyper;
    
    @Before
    public void setUp() {
        hyper = new Hyperlinks();
    }

    /**
     * Test of getPage method, of class Hyperlinks.
     */
    @Test
    public void testGetPage() {
        assertEquals("A implementação não está correta", true, hyper.getPage() == null);
    }

    /**
     * Test of setPage method, of class Hyperlinks.
     */
    @Test
    public void testSetPage() {
        PageTitle p1 = new PageTitle();
        hyper.setPage(p1);
        assertEquals("A implementação não está correta", true, hyper.getPage() != null);
    }

    /**
     * Test of getName method, of class Hyperlinks.
     */
    @Test
    public void testGetName() {
        assertEquals("A implementação não está correta", true, hyper.getName().equals(""));
    }

    /**
     * Test of getLink method, of class Hyperlinks.
     */
    @Test
    public void testGetLink() {
        assertEquals("A implementação não está correta", true, hyper.getLink().equals(""));
    }

    /**
     * Test of setLink method, of class Hyperlinks.
     */
    @Test
    public void testSetLink() {
        hyper.setLink("hello");
        assertEquals("A implementação não está correta", true, hyper.getLink().equals("hello"));
    }

    /**
     * Test of setName method, of class Hyperlinks.
     */
    @Test
    public void testSetName() {
        hyper.setName("hello");
        assertEquals("A implementação não está correta", true, hyper.getName().equals("hello"));
    }

    /**
     * Test of getDistance method, of class Hyperlinks.
     */
    @Test
    public void testGetDistance() {
        assertEquals("A implementação não está correta", true, hyper.getDistance() == 1);
    }

    /**
     * Test of getId method, of class Hyperlinks.
     */
    @Test
    public void testGetId() {
        assertEquals("A implementação não está correta", true, hyper.getId() > 0);
    }
}
