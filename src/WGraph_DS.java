package ex1.src;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;


public class WGraph_DS implements weighted_graph, Serializable {
    private int mc;
    private int size_e;
    private HashMap<Integer, node_info> graph;
    private HashMap<Integer, HashMap<Integer, Double>> weight;
	/**
	 * Default constructor
	 */
    public WGraph_DS() {
        mc = 0;
        size_e = 0;
        graph = new HashMap<Integer, node_info>();
        weight = new HashMap<>();
    }

	/**
	 * Copy constructor
	 */
    public WGraph_DS(weighted_graph other) {
        this.mc = other.getMC();
        this.size_e = other.edgeSize();
        this.graph = new HashMap<Integer, node_info>();
        this.weight = new HashMap<>();
        for (node_info g : other.getV()) {
            this.graph.put(g.getKey(), g);
            g.setTag(Double.POSITIVE_INFINITY);
            this.weight.put(g.getKey(), new HashMap<Integer, Double>());
            Collection<node_info> neighbors = getV(g.getKey());
            for (node_info neighbor : neighbors) {
                this.connect(neighbor.getKey(), g.getKey(), getEdge(neighbor.getKey(), g.getKey()));
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WGraph_DS wGraph_ds = (WGraph_DS) o;
        return this.toString().equals(o.toString());

    }

    @Override
    public int hashCode() {
        return Objects.hash(size_e);
    }

	/**
	 *get Unique key that give the node data info
	 * @param key - the node_id
	 * @return the node_data by the node_id
	 */
    @Override
    public node_info getNode(int key) {
        return this.graph.get(key);
    }

	/**
	 * get two node`s and check if there edge between them
	 *if there not edge its return false
	 * @param node1
	 * @param node2
	 * @return
	 */
    @Override
    public boolean hasEdge(int node1, int node2) {
        if (!graph.containsKey(node1) || !graph.containsKey(node2)) return false;
        return this.weight.get(node1).containsKey(node2);
    }

	/**
	 * this function get two nodes and retrun if there a edge between them
	 * @param node1
	 * @param node2
	 * @return
	 */
    @Override
    public double getEdge(int node1, int node2) {
        if (!hasEdge(node1, node2)) return -1;
        return this.weight.get(node1).get(node2);
    }

	/**
	 * add node to the collection
	 * @param key
	 */
    @Override
    public void addNode(int key) {
        if (!graph.containsKey(key)) {
            this.graph.put(key, new NodeInfo(key));
            this.weight.put(key, new HashMap<Integer, Double>());
            mc++;
        }


    }

	/**
	 * connect between 2 nodes
	 * @param node1
	 * @param node2
	 * @param w
	 */
    @Override
    public void connect(int node1, int node2, double w) {
        if (graph.get(node1) == null || graph.get(node2) == null || w < 0 || node1 == node2) return;
        if (this.weight.get(node1).containsKey(node2)) {
            this.weight.get(node1).replace(node2, w);
            this.weight.get(node2).replace(node1, w);
            mc++;
            return;
        }
        this.weight.get(node1).put(node2, w);
        this.weight.get(node2).put(node1, w);


        size_e++;
        mc++;
    }

	/**
	 * getV return the collection of nodes
	 * @return
	 */
    @Override
    public Collection<node_info> getV() {
        return this.graph.values();
    }

	/**
	 *make a collection from node_id fill in the end by node_info
	 * @param node_id
	 * @return
	 */
    @Override
    public Collection<node_info> getV(int node_id) {
        Collection<node_info> neighbors = new ArrayList<node_info>();
        Set<Integer> keys = this.weight.get(node_id).keySet();
        for (int k : keys) {
            neighbors.add(this.getNode(k));
        }
        return neighbors;
    }

	/**
	 * remove the node by key
	 * go on all the Neighbors and Deletes from them the key
	 * @param key
	 * @return
	 */
    @Override
    public node_info removeNode(int key) {
        if (graph.containsKey(key)) {
            node_info t = getNode(key);
            Iterator<node_info> it = getV(key).iterator();
            while (it.hasNext()) {
                node_info curr = it.next();
                removeEdge(key, curr.getKey());
                mc++;
            }
            graph.remove(key);
            return t;
        } else {

            return null;
        }


    }

	/**
	 * getting two node`s and remove the edge
	 * remove the data from each other
	 * @param node1
	 * @param node2
	 */
    @Override
    public void removeEdge(int node1, int node2) {
        if (this.graph.containsKey(node1) && this.graph.containsKey(node2) || this.weight.get(node1).containsKey(node2)) {
            this.weight.get(node1).remove(node2);
            this.weight.get(node2).remove(node1);
            size_e--;
            mc++;
        }

    }

	/**
	 * get the graph size
	 * @return
	 */
    @Override
    public int nodeSize() {

        return graph.size();
    }

	/**
	 * get the E size of the graph
	 * @return
	 */
    @Override
    public int edgeSize() {
        return size_e;
    }

	/**
	 * get the number of actions that take to make the graph
	 * @return
	 */
    @Override
    public int getMC() {
        return mc;
    }

	/**
	 * print all the pram of the graph
	 * @return
	 */
    @Override
    public String toString() {
        return "WGraph_DS{" +
                "size_e=" + size_e +
                ", graph=" + graph +
                ", weight=" + weight +
                '}';
    }

    public class NodeInfo implements node_info, Serializable {
        private String info;
        private int key;
        private double tag;

        /**
         * THIS is the Default constructor
         **/
        public NodeInfo(int key) {
            info = "";
            this.key = key;
            tag = Double.POSITIVE_INFINITY;
        }

        /**
         * copy constructor
         **/
        public NodeInfo(node_info other) {
            this.key = other.getKey();
            this.info = other.getInfo();
            this.tag = other.getTag();
        }


        /**
         * each node_data should have a unique key
         * return the unique key
         */
        @Override
        public int getKey() {
            return key;
        }

        /**
         * each node have INFO , this function return the info
         */
        @Override
        public String getInfo() {
            return info;
        }

        /**
         *  sets the info
         */
        @Override
        public void setInfo(String s) {
            this.info = s;
        }

		/**
		 * return the tag in the algo ,
		 * @return
		 */
        @Override
        public double getTag() {
            return tag;
        }

		/**
		 * sets wight in the graph
		 * @param t - the new value of the tag
		 */
		@Override
        public void setTag(double t) {
            tag = t;
        }

		/**
		 * print the info of each node in the grpah
		 * @return
		 */
        @Override
        public String toString() {
            return "NodeInfo{" +
                    "info='" + info + '\'' +
                    ", key=" + key +
                    ", tag=" + tag +
                    '}';
        }
    }

}
