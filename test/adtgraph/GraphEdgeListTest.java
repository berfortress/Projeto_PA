/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adtgraph;

import java.util.Collection;
import javafx.scene.control.Hyperlink;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import models.Link;
import models.Website;

/**
 *
 * @author fabio
 */
public class GraphEdgeListTest {
    private GraphEdgeList digraph;
    
    public GraphEdgeListTest() {
    }
    
    @Before
    public void setUp() {
        digraph = new GraphEdgeList();
    }

    /**
     * Test of numVertices method, of class GraphEdgeList.
     */
    @Test
    public void testNumVertices() {
        assertEquals("O digrafo tem vertices", true, digraph.numVertices() == 0);
    }

    /**
     * Test of numEdges method, of class GraphEdgeList.
     */
    @Test
    public void testNumEdges() {
        assertEquals("O digrafo tem arestas", true, digraph.numEdges() == 0);
    }

    /**
     * Test of vertices method, of class GraphEdgeList.
     */
    @Test
    public void testVertices() {
        assertEquals("O digrafo tem vertices", true, digraph.vertices().isEmpty());
    }

    /**
     * Test of edges method, of class GraphEdgeList.
     */
    @Test
    public void testEdges() {
        assertEquals("O digrafo tem arestas", true, digraph.edges().isEmpty());
    }

//    /**
//     * Test of incidentEdges method, of class GraphEdgeList.
//     */
//    @Test
//    public void testIncidentEdges() {
//        GraphEdgeList.MyVertex x1 = new GraphEdgeList.MyVertex();
//        assertEquals("O digrafo tem arestas", new InvalidVertexException("Not a vertex."), digraph.incidentEdges(null));
//    }

//    /**
//     * Test of opposite method, of class GraphEdgeList.
//     */
//    @Test
//    public void testOpposite() {
//        System.out.println("opposite");
//        GraphEdgeList instance = new GraphEdgeList();
//        Vertex expResult = null;
//        Vertex result = instance.opposite(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of areAdjacent method, of class GraphEdgeList.
//     */
//    @Test
//    public void testAreAdjacent() {
//        System.out.println("areAdjacent");
//        GraphEdgeList instance = new GraphEdgeList();
//        boolean expResult = false;
//        boolean result = instance.areAdjacent(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of insertVertex method, of class GraphEdgeList.
//     */
//    @Test
//    public void testInsertVertex() {
//        System.out.println("insertVertex");
//        Object vElement = null;
//        GraphEdgeList instance = new GraphEdgeList();
//        Vertex expResult = null;
//        Vertex result = instance.insertVertex(vElement);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of insertEdge method, of class GraphEdgeList.
//     */
//    @Test
//    public void testInsertEdge_3args_1() {
//        System.out.println("insertEdge");
//        GraphEdgeList instance = new GraphEdgeList();
//        Edge expResult = null;
//        Edge result = instance.insertEdge(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of insertEdge method, of class GraphEdgeList.
//     */
//    @Test
//    public void testInsertEdge_3args_2() {
//        System.out.println("insertEdge");
//        Object vElement1 = null;
//        Object vElement2 = null;
//        Object edgeElement = null;
//        GraphEdgeList instance = new GraphEdgeList();
//        Edge expResult = null;
//        Edge result = instance.insertEdge(vElement1, vElement2, edgeElement);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of removeVertex method, of class GraphEdgeList.
//     */
//    @Test
//    public void testRemoveVertex() {
//        System.out.println("removeVertex");
//        GraphEdgeList instance = new GraphEdgeList();
//        Object expResult = null;
//        Object result = instance.removeVertex(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of removeEdge method, of class GraphEdgeList.
//     */
//    @Test
//    public void testRemoveEdge() {
//        System.out.println("removeEdge");
//        GraphEdgeList instance = new GraphEdgeList();
//        Object expResult = null;
//        Object result = instance.removeEdge(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of replace method, of class GraphEdgeList.
//     */
//    @Test
//    public void testReplace_Vertex_GenericType() {
//        System.out.println("replace");
//        GraphEdgeList instance = new GraphEdgeList();
//        Object expResult = null;
//        Object result = instance.replace(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of replace method, of class GraphEdgeList.
//     */
//    @Test
//    public void testReplace_Edge_GenericType() {
//        System.out.println("replace");
//        GraphEdgeList instance = new GraphEdgeList();
//        Object expResult = null;
//        Object result = instance.replace(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of toString method, of class GraphEdgeList.
//     */
//    @Test
//    public void testToString() {
//        System.out.println("toString");
//        GraphEdgeList instance = new GraphEdgeList();
//        String expResult = "";
//        String result = instance.toString();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of outboundEdges method, of class GraphEdgeList.
//     */
//    @Test
//    public void testOutboundEdges() {
//        System.out.println("outboundEdges");
//        GraphEdgeList instance = new GraphEdgeList();
//        Collection<Edge<E, V>> expResult = null;
//        Collection<Edge<E, V>> result = instance.outboundEdges(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//    
}
