package ex1.src;

import java.io.*;
import java.util.*;

import java.io.Serializable;

public class WGraph_Algo implements weighted_graph_algorithms, Serializable {
    weighted_graph graph;


    public WGraph_Algo() {
        graph = new WGraph_DS();
    }

    /**
     *get the graph for the Algo
     * @param g
     */
    @Override
    public void init(weighted_graph g) {
        this.graph = g;
    }

    /**
     *get the graph
     * @return
     */
    @Override
    public weighted_graph getGraph() {
        return graph;
    }

    /**
     *making copy of the graph for the Algo
     * @return
     */
    @Override
    public weighted_graph copy() {
        return new WGraph_DS(graph);
    }

    /**
     *check if the graph not have any nodes that not connect to him
     * use bfs function
     * @return
     */
    @Override
    public boolean isConnected() {
        if (graph.nodeSize() == 0 || graph.nodeSize() == 1) return true;
        weighted_graph afterBfs = bfs(graph.getV().iterator().next().getKey());
        for (node_info g : afterBfs.getV()) {
            if (g.getTag() == Double.POSITIVE_INFINITY) return false;
        }
        return true;
    }

    /**
     *get the shortest path by the wight of edge`s ,
     * use dijkstra function
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if (graph.getNode(src) == null || graph.getNode(dest) == null) return -1;
        dijkstra(this.graph.getNode(src));
        if(this.graph.getNode(dest).getTag() == Double.POSITIVE_INFINITY) return -1.0;
        return this.graph.getNode(dest).getTag();

    }

    /**
     *use the dijkstra
     * find the shortest path
     * and start to put in the list
     * from the dest to src
     *until next= null
     * then use revers function
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        if (graph.getNode(src) == null || graph.getNode(dest) == null) return new LinkedList<>();
        LinkedHashMap<node_info, node_info> parents = dijkstra(this.graph.getNode(src));
        List<node_info> ans = new ArrayList<node_info>();
        node_info node = this.graph.getNode(dest);
        while (node != null) {
            ans.add(node);
            node = parents.get(node);
        }
        Collections.reverse(ans);
        return ans;
    }

    /**
     *Assign a distance value to all vertices in the input graph. Initialize all distance values as INFINITE
     * use PriorityQueue for compere between the paths
     * src = 0.0
     * all other change by the wigth they have
     * then compere between the paths and return the shortest by wight
     * @param src
     * @return
     */
    private LinkedHashMap<node_info, node_info> dijkstra(node_info src) {
        inittag();
        PriorityQueue<node_info> q = new PriorityQueue<node_info>(new Comparator<node_info>() {
            @Override
            public int compare(node_info o1, node_info o2) {
                return -Double.compare(o1.getTag(), o1.getTag());
            }
        });
        LinkedHashMap<node_info, node_info> parents = new LinkedHashMap<node_info, node_info>();
        src.setTag(0.0);
        q.add(src);
        while (!q.isEmpty()) {
            node_info vertex_u = q.poll();
            Iterator<node_info> it = this.graph.getV(vertex_u.getKey()).iterator();
            while (it.hasNext()) {
                node_info vertex_v = it.next();
                // if(vertex_v.getInfo().equals("")){
                double dis = this.graph.getEdge(vertex_u.getKey(), vertex_v.getKey()) + vertex_u.getTag();
                if (dis < vertex_v.getTag()) {
                    vertex_v.setTag(dis);
                    parents.put(vertex_v, vertex_u);
                    q.remove(vertex_v);
                    q.add(vertex_v);
                }
            }

        }


        return parents;
    }

    /**
     *set all nodes as POSITIVE_INFINITY
     */
    private void inittag() {
        for (node_info i : graph.getV()) {
            i.setTag(Double.POSITIVE_INFINITY);
        }
    }

    /**
     *saving the graph
     * @param file - the file name (may include a relative path).
     * @return
     */
    @Override
    public boolean save(String file) {
        try {
            FileOutputStream save = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(save);
            out.writeObject(this.graph);
            out.close();
            save.close();
            System.out.println("Object has been serialized");
            return true;
        } catch (IOException ex) {
            System.out.println("IOException is caught");
            return false;
        }
    }

    /**
     *load the graph
     * @param file - file name
     * @return
     */
    @Override
    public boolean load(String file) {
        try {
            FileInputStream load = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(load);
            graph = (weighted_graph) in.readObject();
            in.close();
            load.close();
            System.out.println("Object has been deserialized");
            return true;
        } catch (IOException ex) {
            System.out.println("IOException is caught");
            return false;
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException is caught");
            return false;
        }

    }

    /**bfs getting copy grph
    *making a queue then set the tag of the src as 0
    *then go i while until the queue is empty
     *sets tags until there no any  tags
      */
    private weighted_graph bfs(int src) {
        weighted_graph copyGraph = copy();
        node_info srcNode = copyGraph.getNode(src);
        LinkedList<node_info> queue = new LinkedList<>();
        srcNode.setTag(0);
        queue.push(srcNode);
        while (!queue.isEmpty()) {
            node_info temp = queue.pop();
            for (node_info ni : this.graph.getV(temp.getKey())) {
                if (ni.getTag() == Double.POSITIVE_INFINITY) {
                    ni.setTag(temp.getTag() + 1);
                    queue.push(ni);
                }
            }
        }
        return copyGraph;
    }

    /**
     * java equals function
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WGraph_Algo that = (WGraph_Algo) o;
        return Objects.equals(graph, that.graph);
    }

    @Override
    public int hashCode() {
        return Objects.hash(graph);
    }
}
