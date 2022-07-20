package javafx18;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;
 
class GraphNode extends Node {
	private int id;
	private Pane parent;
	double x;
	double y;
	
	
	public GraphNode(int id,Pane p,double xpos,double ypos) {
		this.id = id;
		this.parent = p;
		x = xpos;
		y = ypos;
	}
	static boolean dragging = false;
	public void add() {
		Circle circle = new Circle();
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setRadius(10);
        //circle.setFill(Color.valueOf("#ff00ff"));
        circle.setStroke(Color.valueOf("#000000"));
        circle.setStrokeWidth(2);
        circle.setFill(Color.valueOf("ff00f0"));
        circle.setOpacity(100);
        
        Text txt = new Text(Integer.toString(id));
		txt.setBoundsType(TextBoundsType.VISUAL);
        
		//double w = txt.getBoundsInLocal().getWidth();
		//double h = txt.getBoundsInLocal().getHeight();
		txt.relocate(x-2.6,y-4);
		
        parent.getChildren().addAll(circle,txt);
        
        
        circle.addEventHandler(MouseEvent.MOUSE_PRESSED, e->{System.out.println("Starting the graph node : "+id);dragging = true; });
        circle.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> { if(dragging)System.out.println("Ending at graph node : "+id); dragging = false;});
	}
	public int getid() {
		return id;
	}
	
}





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
		
		//System.out.println(e.getSource().getClass());
		//System.out.println(e.getEventType());
		// TODO Auto-generated method stub
		if(e.getEventType()==MouseEvent.MOUSE_CLICKED) {
			if(!this.dragging)
			onClickedEventHandler.handle(e);
			this.dragging = false;
		}else if(e.getEventType()==MouseEvent.MOUSE_PRESSED) {
			x1 = e.getX();
			y1 = e.getY();
		}else if(e.getEventType()==MouseEvent.DRAG_DETECTED) {
			this.dragging = true;
		}else if(e.getEventType()==MouseEvent.MOUSE_RELEASED) {
			x2 = e.getX();
			y2 = e.getY();
			
			Line l1 = new Line(x1,y1,x2,y2);
			Pane p= (Pane)e.getSource();
			p.getChildren().add(l1);
			
			
		}
	}
	
}

public class HelloFX extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    /*
    static EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
    	public void handle(MouseEvent e) {
    		if(e.getEventType()==MouseEvent.MOUSE_PRESSED) {
    			mousePressed(e);
    		}
    		if(e.getEventType()==MouseEvent.MOUSE_DRAGGED) {
    			mouseDragged(e);
    		}
    		if(e.getEventType()==MouseEvent.MOUSE_RELEASED) {
    			mouseReleased(e);
    		}
    	}

        public boolean dragging = false;
        public void mousePressed(MouseEvent e) {
        	if(dragging) {
        		return;
        	}
        	
        	startx = e.getX();
        	starty = e.getY();
        	dragging = true;
        }
        public void mouseReleased(MouseEvent e) {
        	dragging = false;
        }
        public void mouseDragged(MouseEvent e) {
        	if(dragging == false) {
        		return;
        	}
        	
        	endx = e.getX();
        	endy = e.getY();
        	
        	Line l1 = new Line(startx,starty,endx,endy);
        	Pane p = (Pane)e.getSource();
        	p.getChildren().add(l1);
        }
        
    };
    */
    static int cur = 0;	
    static double startx = 0.0;
    static double starty = 0.0;
    static double endx = 100.0;
    static double endy = 100.0;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
    	
        Parent root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
        if(root instanceof AnchorPane) {
       // 	System.out.println("True");
       // 	System.out.println(root.getId());
        }
        AnchorPane mainAnchor = (AnchorPane)root;
       // System.out.println(mainAnchor.getId());
       // System.out.println(mainAnchor.getChildren());
        
        SplitPane spane1 = (SplitPane)mainAnchor.getChildren().get(0);
       // System.out.println(spane1.getId());
        AnchorPane apane2 = null;//(AnchorPane)spane1.getItems().get(1);
        for(Node c : spane1.getItems()) {
        	//System.out.println(c.getId());
        	if(c.getId()!=null && c.getId().equals("apane2")) {
        		apane2 = (AnchorPane)c;
        	}
        }
//        System.out.println(apane2.getId());
        if(apane2 != null) {
        SplitPane spane2 = (SplitPane)apane2.getChildren().get(0);
        Pane pane = null;
        for(Node c : spane2.getItems()) {
        	if(c.getId()!=null &&c.getId().equals("nodedisplay")) {
        		pane = (Pane)c;
        	}
        }
        pane.addEventHandler(MouseEvent.ANY, new MyHandler(
        			e->{
        				
        				
        				GraphNode g = new GraphNode(cur++,(Pane)e.getSource(),e.getX(),e.getY());
        				g.add();
        				
        				
        				/*
        				Circle circle = new Circle();
                        circle.setCenterX(e.getX());
                        circle.setCenterY(e.getY());
                        circle.setRadius(10);
                        //circle.setFill(Color.valueOf("#ff00ff"));
                        circle.setStroke(Color.valueOf("#000000"));
                        circle.setStrokeWidth(2);
                        circle.setFill(Color.valueOf("ff00f0"));
                        circle.setOpacity(100);
                        
                        Text txt = new Text(Integer.toString(cur++));
                		txt.setBoundsType(TextBoundsType.VISUAL);
                        
                		//double w = txt.getBoundsInLocal().getWidth();
                		//double h = txt.getBoundsInLocal().getHeight();
                		txt.relocate(e.getX()-2.6,e.getY()-4);
                		
                        Pane p = (Pane)e.getSource();
                        p.getChildren().addAll(circle,txt);

						*/
                        System.out.println("CIRCLE ADDED");
        			}
        		));
        
        /*
        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
        	@Override
        	public void handle(MouseEvent e) {
        		System.out.println(e.getX()+"  "+e.getY());
        		
        		Circle circle = new Circle();
                circle.setCenterX(e.getX());
                circle.setCenterY(e.getY());
                circle.setRadius(10);
                //circle.setFill(Color.valueOf("#ff00ff"));
                circle.setStroke(Color.valueOf("#000000"));
                circle.setStrokeWidth(2);
                circle.setFill(Color.valueOf("ff00f0"));
                circle.setOpacity(100);
                
                Text txt = new Text(Integer.toString(cur++));
        		txt.setBoundsType(TextBoundsType.VISUAL);
                
        		//double w = txt.getBoundsInLocal().getWidth();
        		//double h = txt.getBoundsInLocal().getHeight();
        		txt.relocate(e.getX()-2.6,e.getY()-4);
        		
                Pane p = (Pane)e.getSource();
                p.getChildren().addAll(circle,txt);

                System.out.println("CIRCLE ADDED");
        	}
        });
        	
        
        pane.addEventHandler(MouseEvent.ANY, e-> {
        	
        	if(e.getEventType()== MouseEvent.MOUSE_PRESSED) {
        		startx = e.getX();
        		starty = e.getY();
        		
        		
        	}
        	boolean dragging = false;
        	if(e.getEventType()== MouseEvent.DRAG_DETECTED) {
        		startx = e.getX();
        		starty = e.getY();
        		dragging = true;
        	} if(e.getEventType() == MouseEvent.MOUSE_RELEASED && dragging) {
        		endx = e.getX();
        		endy = e.getY();
        		
        		Line l1 = new Line(startx,starty,endx+10,endy+10);
        		Pane p = (Pane)e.getSource();
        		p.getChildren().add(l1);
        	}
        });
        */	
        	
        }
        
        
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("My Title");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
