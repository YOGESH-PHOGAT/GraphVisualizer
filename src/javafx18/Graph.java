package javafx18;

import java.util.*;

public class Graph {
	class Node{
		GraphNode g;
		Edge e;
		public Node(GraphNode g,Edge e) {
			this.g = g;
			this.e = e;
		}
	}
	private HashMap<GraphNode,List<Node>> adj;
	
	public void addNode(GraphNode g) {
		adj.put(g, new ArrayList<>());
	}
	public void addEdge(GraphNode g1,GraphNode g2,Edge e) {
		Node node1 = new Node(g2,e);
		Node node2 = new Node(g1,e);
		adj.get(g1).add(node1);
		adj.get(g2).add(node2);
	}
	
	
}
