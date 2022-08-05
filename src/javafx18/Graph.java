package javafx18;

import java.util.*;

import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class Graph {
	class Node{
		GraphNode g;
		Line e;
		public Node(GraphNode g,Line e) {
			this.g = g;
			this.e = e;
		}
	}
	private HashMap<GraphNode,List<Node>> adj;
	Label output;
	
	public Graph() {
		adj = new HashMap<>();
	}
	public void addNode(GraphNode g) {
		adj.put(g, new ArrayList<>());
	}
	public void addEdge(GraphNode g1,GraphNode g2,Line e) {
		Node node1 = new Node(g2,e);
		Node node2 = new Node(g1,e);
		adj.get(g1).add(node1);
		adj.get(g2).add(node2);
	}
	public void connectLabel(Label output) {
		this.output = output;
	}
	
	
	public void bfs(GraphNode src) {
		
		StringBuilder sb = new StringBuilder();
		
		Queue<GraphNode> q = new LinkedList<>();
		
		HashSet<GraphNode> visited = new HashSet<>();
		
		SequentialTransition seqt = new SequentialTransition();
		
		visited.add(src);
		sb.append(Integer.toString(src.getid()));
		sb.append(" ");
		q.offer(src);
		
		FillTransition ft1 = new FillTransition(Duration.millis(5000),src.getCircle(),
				Color.valueOf("ff00f0"),Color.LIGHTCYAN);
		
		seqt.getChildren().add(ft1);
		
		
		while(q.size()!=0) {
			GraphNode rem = q.poll();
			
			
			for(Node nbr : adj.get(rem)) {
				GraphNode nbrNode = nbr.g;
				if(!visited.contains(nbrNode)) {
					sb.append(Integer.toString(nbrNode.getid()));
					sb.append(" ");
					visited.add(nbrNode);
					q.offer(nbrNode);
					
					FillTransition ft2 = new FillTransition(Duration.millis(5000),nbrNode.getCircle(),
							Color.valueOf("ff00f0"),Color.LIGHTCYAN);
					
					seqt.getChildren().add(ft2);
					
				}
			}
			
			
			FillTransition ft3 = new FillTransition(Duration.millis(5000),rem.getCircle(),
					Color.LIGHTCYAN,Color.valueOf("ff00f0"));
			seqt.getChildren().add(ft3);
			
		}
		
		
		output.setText(sb.toString());
		seqt.play();
		
	}
	
	private void dfsHelper(GraphNode cur,HashSet<GraphNode> visited,StringBuilder sb,
			SequentialTransition seqt) {
		
			for(Node nbr : adj.get(cur)) {
				GraphNode nbrNode = nbr.g;
				if(!visited.contains(nbrNode)) {
					sb.append(Integer.toString(nbrNode.getid()));
					sb.append(" ");
					visited.add(nbrNode);
	
					FillTransition ft1 = new FillTransition(Duration.millis(5000),nbrNode.getCircle(),
							Color.valueOf("ff00f0"),Color.LIGHTCYAN);
					
					seqt.getChildren().add(ft1);
					
					dfsHelper(nbrNode,visited,sb,seqt);
					

					FillTransition ft2 = new FillTransition(Duration.millis(5000),nbrNode.getCircle(),
							Color.LIGHTCYAN,Color.valueOf("ff00f0"));
					seqt.getChildren().add(ft2);
				}
			}
		
	}
	public void dfs(GraphNode src) {
		StringBuilder sb = new StringBuilder();
		HashSet<GraphNode> visited = new HashSet<>();
		visited.add(src);
		SequentialTransition seqt = new SequentialTransition();
		FillTransition ft1 = new FillTransition(Duration.millis(5000),src.getCircle(),
				Color.valueOf("ff00f0"),Color.LIGHTCYAN);
		seqt.getChildren().add(ft1);
		sb.append(Integer.toString(src.getid()));
		sb.append(" ");
		dfsHelper(src,visited,sb,seqt);
		
		FillTransition ft2 = new FillTransition(Duration.millis(5000),src.getCircle(),
				Color.LIGHTCYAN,Color.valueOf("ff00f0"));
		seqt.getChildren().add(ft2);
		output.setText(sb.toString());
		
		seqt.play();
		
		
	}
	
	public void topologicalSort() {
		
	}
	
}
