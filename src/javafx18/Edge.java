package javafx18;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

class Edge extends Node {
	
	private Pane parent;
	Line line;
	
	public Edge(Pane p) {
		this.parent = p;
	}
	
	public void addEdge(GraphNode g1,GraphNode g2) {
		double startx = g1.circle.getCenterX();
		double starty = g1.circle.getCenterY();
		double endx = g2.circle.getCenterX();
		double endy = g2.circle.getCenterY();
		
		line = new Line(startx,starty,endx,endy);
		parent.getChildren().add(line);
		
		g1.refill();
		g2.refill();
		
	}
}
