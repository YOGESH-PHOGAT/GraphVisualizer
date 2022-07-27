package javafx18;

import java.util.HashMap;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;




class MyHandler implements EventHandler<MouseEvent> {

	private final EventHandler<MouseEvent> onClickedEventHandler;
	private boolean dragging;
	private double x1;
	private double x2;
	private double y1;
	private double y2;
	
	
	public MyHandler(EventHandler<MouseEvent> eh1) {
		onClickedEventHandler = eh1;
		this.dragging = false;
		x1=x2=y1=y2=0.0;
	}
	
	@Override
	public void handle(MouseEvent e) {
		
		if(e.getEventType()==MouseEvent.MOUSE_CLICKED) {
			if(!this.dragging)
			onClickedEventHandler.handle(e);
			this.dragging = false;
		}
		
		else if(e.getEventType()==MouseEvent.MOUSE_PRESSED) {
			x1 = e.getX();
			y1 = e.getY();
		}
		
		else if(e.getEventType()==MouseEvent.DRAG_DETECTED) {
			this.dragging = true;
			System.out.println("DRAG STARTED \n");
		}
		
		else if(e.getEventType()==MouseEvent.MOUSE_RELEASED) {
			if(dragging) {
				x2 = e.getX();
				y2 = e.getY();
				System.out.println(e.getSource().getClass());
			
				Line l1 = new Line(x1,y1,x2,y2);
				Pane p= (Pane)e.getSource();
				p.getChildren().add(l1);
			}
			
		}
	}
	
}

public class HelloFX extends Application {
	static void disp() {
		System.out.println("CALLING FROM GRAPH NODE");
	}

    static HashMap<Integer,GraphNode> nodeMap;
    
    static HashMap<GraphNode,Edge> edgeMap;
    
    static void addNode(GraphNode g) {
    	nodeMap.put(g.getid(), g);
    	g.add();
    }
    static void connect() {
    	String src = tf1.getText();
    	String dest = tf2.getText();
    	GraphNode g1 = nodeMap.get(Integer.parseInt(src));
    	GraphNode g2 = nodeMap.get(Integer.parseInt(dest));
    	
    	
    	Line l = new Line(g1.x,g1.y,g2.x,g2.y);
    	parent.getChildren().add(l);
    	
    	g1.refill();
    	g2.refill();
    	
    }
    
    
    public static void main(String[] args) {
        nodeMap = new HashMap<>();
        edgeMap = new HashMap<>();
    	launch(args);
        
    }

    static int cur = 0;	
    static TextField tf1;
    static TextField tf2;
    static Pane parent;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
    	
        Parent root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
        AnchorPane mainAnchor = (AnchorPane)root;
        SplitPane spane1 = (SplitPane)mainAnchor.getChildren().get(0);
        AnchorPane apane1 = null;
        AnchorPane apane2 = null;
        for(Node c : spane1.getItems()) {
        	if(c.getId()!=null && c.getId().equals("apane2")) {
        		apane2 = (AnchorPane)c;
        	}
        	
        	if(c.getId()!=null && c.getId().equals("apane1")) {
        		apane1 = (AnchorPane)c;
        	}
        }
        
        Button edgeButton = null;

        for(Node c : apane1.getChildren()) {
        	if(c.getId()!=null && c.getId().equals("EdgeButton")) {
        		edgeButton = (Button)c;
        	}
        	if(c.getId()!=null && c.getId().equals("srcVertex")) {
        		tf1 = (TextField)c;
        	
        	}
        	if(c.getId()!=null && c.getId().equals("destVertex")) {
        		tf2 = (TextField)c;
        	}
        }
        
        
        edgeButton.setOnMouseClicked(e->{
        	connect();
            
        });
        
        if(apane2 != null) {
        SplitPane spane2 = (SplitPane)apane2.getChildren().get(0);
        Pane pane = null;
        for(Node c : spane2.getItems()) {
        	if(c.getId()!=null &&c.getId().equals("nodedisplay")) {
        		pane = (Pane)c;
        	}
        }
        parent = pane;
        /*
        HandlersClass handlers = new HandlersClass (pane);
        */
        pane.addEventHandler(MouseEvent.ANY, new MyHandler(
        			e->{
        				GraphNode g = new GraphNode(cur++,(Pane)e.getSource(),e.getX(),e.getY());
        				addNode(g);
                        System.out.println("CIRCLE ADDED");
        			}
        		));
        }
        
        
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("Graph Visualizer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
