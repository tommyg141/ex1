package ex1.tests;


import ex1.src.WGraph_Algo;
import ex1.src.WGraph_DS;
import ex1.src.weighted_graph;
import ex1.src.weighted_graph_algorithms;

import static org.junit.jupiter.api.Assertions.*;

public class Tests {

    weighted_graph g = new WGraph_DS();

    @org.junit.jupiter.api.Test
    void firstest (){
        weighted_graph graph = new WGraph_DS();
        weighted_graph_algorithms graph1 = new WGraph_Algo();
        for (int i = 0 ; i<8 ; i++){
            graph.addNode(i);
        }
        graph.connect(0,1,1);
        graph.connect(1,2,1);
        graph.connect(2,3,1);
        graph.connect(3,4,1);
        graph.connect(4,5,1);
        graph.connect(5,6,1);
        graph.connect(6,7,1);
        graph.connect(7,8,1);
        graph.connect(8,9,1);
        graph.getNode(8);
        graph.getNode(9);
        graph.hasEdge(8,9);
        assertFalse(graph.hasEdge(8,9));
        graph.getV();
        System.out.println(graph.getV());
        //graph.removeNode(1);
        graph1.init(graph);
        //graph1.isConnected();
        //assertFalse(graph1.isConnected());
        String str = "graph1.obj";
        graph1.save(str);
    }
    @org.junit.jupiter.api.Test
    void wight() {
        weighted_graph g2 = new WGraph_DS();
        for (int i = 0; i < 6; i++) {
            g2.addNode(i);
        }
        g2.connect(0, 1, 1);
        g2.connect(1, 2, 1);
        g2.connect(2, 3, 1);
        g2.connect(3, 4, 1);
        g2.connect(4, 5, 1);
        g2.connect(4, 6, 1);
        g2.hasEdge(1, 2);
        assertTrue(g2.hasEdge(1, 2));
        g2.removeNode(2);
        assertFalse(g2.hasEdge(2, 3));
        g2.removeEdge(4, 6);
        assertFalse(g2.hasEdge(4, 6));
        g2.connect(0, 8, 1);

    }

    @org.junit.jupiter.api.Test
    void isConnected() {
        weighted_graph g1 = new WGraph_DS();
        weighted_graph_algorithms wsd = new WGraph_Algo();
        for (int i = 0; i < 9; i++) {
            g1.addNode(i);
        }
        g1.connect(0, 1, 1);
        g1.connect(0, 2, 1);
        g1.connect(0, 3, 1);
        g1.connect(0, 4, 1);
        g1.connect(0, 5, 1);
        g1.connect(0, 6, 1);
        g1.connect(0, 7, 1);
        g1.connect(0, 8, 1);
        wsd.init(g1);
        assertTrue(wsd.isConnected());
        g1.removeNode(0);
        assertFalse(wsd.isConnected());
        System.out.println(wsd.shortestPathDist(0, 6));


    }


    @org.junit.jupiter.api.Test
    void test1() {
        g.addNode(0);
        if (g.getMC() != 1) {
            fail("fail mc counter");
        }
        g.addNode(1);
        if (g.getMC() != 2) {
            fail("fail mc counter");
        }
        g.addNode(2);
        if (g.getMC() != 3) {
            fail("fail mc counter");
        }
        if (g.edgeSize() != 0) {
            fail("fail edge counter");
        }
        g.connect(0, 1, 7);
        if (g.edgeSize() != 1) {
            fail("fail edge counter");
        }
        g.removeEdge(1, 0);
        if (g.edgeSize() != 0) {
            fail("fail edge counter");
        }
    }

    @org.junit.jupiter.api.Test
    void test2() {
        for (int i = 0; i < 100; i++) {
            g.addNode(i);
        }
        for (int i = 0; i < 100; i += 2) {
            g.connect(i, i + 1, 15);
        }
        if (g.getMC() != 150) {
            fail("fail mc counter");
        }
        g.addNode(1);
        if (g.getMC() != 150) {
            fail("fail mc counter");
        }
        g.addNode(2);
        if (g.getMC() != 150) {
            fail("fail mc counter");
        }
        if (g.edgeSize() != 50) {
            fail("fail edge counter");
        }
        g.connect(0, 1, 7);
        if (g.edgeSize() != 50) {
            fail("fail edge counter");
        }
        g.removeEdge(1, 0);
        if (g.edgeSize() != 49) {
            fail("fail edge counter");
        }
    }


    @org.junit.jupiter.api.Test
    void test4() {
        weighted_graph g4 = new WGraph_DS();
        weighted_graph_algorithms g4a = new WGraph_Algo();
        g4.addNode(0);
        g4.addNode(1);
        g4.addNode(2);
        g4.connect(0, 1, 1);
        g4.connect(1, 2, 1);
        g4a.init(g4);
        System.out.println(g4a.shortestPathDist(0, 2));
        g4.connect(0, 2, 0);
        System.out.println(g4a.shortestPathDist(0, 2));
    }




}