package javafx18;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

public class GraphNode extends Node {
	private int id;
	private Pane parent;
	double x;
	double y;
	Circle circle;
	Text txt;
	
	public GraphNode(int id,Pane p,double xpos,double ypos) {
		this.id = id;
		this.parent = p;
		x = xpos;
		y = ypos;
		add();
	}
	static boolean dragging = false;
	static int start = -1;
	static int end = -1;
	public void add() {
		circle = new Circle();
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setRadius(10);
        circle.setStroke(Color.valueOf("#000000"));
        circle.setStrokeWidth(2);
        circle.setFill(Color.valueOf("ff00f0"));
        circle.setOpacity(100);
        circle.setId(Integer.toString(id));
        
        txt = new Text(Integer.toString(id));
		txt.setBoundsType(TextBoundsType.VISUAL);
		txt.relocate(x-2.6,y-4);
		
        parent.getChildren().add(circle);
        parent.getChildren().add(txt);
        
        /*
        circle.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
        	dragging = true;
        	System.out.println("ENTERING THE NODE : "+id+"\n");
        });
        
        circle.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
        	if(dragging) {
        		Circle g = (Circle)e.getSource();
        		System.out.println("EXITING THE NODE : "+g.getId()+"\n");
        		dragging = false;
        	}
        });
        */
        /*
        circle.setOnMouseDragReleased(e->{
        	System.out.println("BUENAS NOCHES AMIGOS!!\n");
        });
        */
	}
	public void refill() {
		
		parent.getChildren().remove(circle);
		parent.getChildren().remove(txt);
		circle = new Circle();
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setRadius(10);
        circle.setStroke(Color.valueOf("#000000"));
        circle.setStrokeWidth(2);
        circle.setFill(Color.valueOf("ff00f0"));
        circle.setOpacity(100);
        circle.setId(Integer.toString(id));
        txt = new Text(Integer.toString(id));
		txt.setBoundsType(TextBoundsType.VISUAL);
		txt.relocate(x-2.6,y-4);
		
		parent.getChildren().add(circle);
		parent.getChildren().add(txt);
        
	}
	public int getid() {
		return id;
	}
	
	
	
}

