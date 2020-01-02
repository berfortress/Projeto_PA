/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler;

import models.Link;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author fabio e bernardo
 */

public class LinkTest {
    private Link hyper;
    
    @Before
    public void setUp() {
        hyper = new Link();
    }

    /**
     * Test of getDescription method, of class Link.
     */
    @Test
    public void testGetName() {
        assertEquals("A implementação não está correta", true, hyper.getDescription().equals(""));
    }

    /**
     * Test of getLink method, of class Link.
     */
    @Test
    public void testGetLink() {
        assertEquals("A implementação não está correta", true, hyper.getLink().equals(""));
    }

    /**
     * Test of setLink method, of class Link.
     */
    @Test
    public void testSetLink() {
        hyper.setLink("hello");
        assertEquals("A implementação não está correta", true, hyper.getLink().equals("hello"));
    }

    /**
     * Test of setDescription method, of class Link.
     */
    @Test
    public void testSetName() {
        hyper.setDescription("hello");
        assertEquals("A implementação não está correta", true, hyper.getDescription().equals("hello"));
    }

    /**
     * Test of getDistance method, of class Link.
     */
    @Test
    public void testGetDistance() {
        assertEquals("A implementação não está correta", true, hyper.getDistance() == 1);
    }

    /**
     * Test of getId method, of class Link.
     */
    @Test
    public void testGetId() {
        assertEquals("A implementação não está correta", true, hyper.getId() > 0);
    }
}
