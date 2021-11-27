package Reference;

/**
 * Assignment #7
 *
 * Name: Ivan Hornung
 * Student ID: 1222222859
 * Lecture: MWF 11:15am
 * Description: The following class adds three buttons to the top of the window and provides a canvas
 * with user circle-drawing functionality. The three buttons include clear, which wipes the canvas clear
 * of any drawings; undo, which reverts the last action (like a CTRL + Z within the program); color selector,
 * which allows the user to select the color they wish to draw the circle with. This class allows the user to 
 * click on a point in the canvas where they wish the center of the circle to be, and drag to another point in the 
 * canvas with their mouse indicating the size of the radius of the circle. When the user releases their mouse,
 * the circle is placed on the canvas and will remain there until the canvas is cleared.
 *
 */

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Example extends GridPane {

    private CanvasPane canvas;

    //constructor
    //initializes instant variables, sets up the layout, and ties source objects with event handlers
    public Example() {

        // Instantiate
        // Create a pane for drawing circles
        canvas = new CanvasPane();
        canvas.setStyle("-fx-background-color: white;");
        
        
        PointerHandler pHandler = new PointerHandler();
        canvas.setOnMousePressed(pHandler);
        canvas.setOnMouseDragged(pHandler);
        canvas.setOnMouseReleased(pHandler);
        //canvas.setOnAction(new PointerHandler());
        
        //ctrlPane.getChildren().add(hbox);
        //canvas.getChildren().add(ctrlPane);
        this.getChildren().add(canvas);
        
        /*GridPane.setVgrow(canvas, Priority.ALWAYS);
        GridPane.setHgrow(canvas, Priority.ALWAYS);*/
    }

    /**
     * CanvasPane is the panel where Circles will be drawn on.
     */
    private class CanvasPane extends Pane
    {   //instance variables
        private Circle placeholder;
        private boolean isPlaceholderOn;

        
        //constructor creates placeholder with zero size and sets default color to black. initializes instance variables
        public CanvasPane() {
        }

        //draws a circle and has logic behind it to prevent placeholders from bombarding the computer with stacked circles
        public void drawPlaceHolder(int x, int y, int radius)
        {
            // Change the position of the placeholder
            //write your code here
        	
        	placeholder.setCenterX(x);           
        	placeholder.setCenterY(y);           
        	placeholder.setRadius(radius);      
        	//placeholder.setStroke(Color.BLACK);
        	placeholder.setFill(Color.SNOW);
        	
        	// If this is the first time we draw the placeholder, add it to the canvas
            if (!isPlaceholderOn)
            {
                //write your code here
            	this.getChildren().add(placeholder);
            	isPlaceholderOn = true;
            	//placeholder.setStroke(Color.BLACK);            
            }
        	
        }

        //method to erase placeholder to improve efficiency and speed of program
        public void erasePlaceHolder() {
           // Simply remove the placeholder Circle from the canvas
           // write your code here
        	this.getChildren().remove(this.getChildren().size()-1);
        	isPlaceholderOn = false;
        	
        }

        /**
         * Erase and redraw all Circles in the Circle list (not including the
         * placeholder)
         */
        public void repaint() {
            // Redraw all circles in the list
            this.getChildren().clear();
            for (Circle c : circleList)
                this.getChildren().add(c);

            // Make the control panel always visible
        }

    }

    /**
     * Step 2: ButtonListener defines actions to take in case the "Undo" or "Erase"
     * button is clicked
     */
    private class ButtonHandler implements EventHandler<ActionEvent>
    {
    	
    	//performs actions depending on which of the first two buttons on the top are pressed
        @Override
        public void handle(ActionEvent e)
        {
            Object source = e.getSource();

            // Check if source refers to the Erase button
            if (source == btnErase)
            {
                //write your code here
            	tempList = (ArrayList<Circle>)circleList.clone();
            	circleList.clear();
            	canvas.repaint();
            	}
            // Check if source refers to the Undo button
            else if (source == btnUndo)
            {
                // Erase the last Circle in the list
                // write your code here
            	if(circleList.size() != 0) {
            		circleList.remove(circleList.size()-1);
                    canvas.repaint();
                	tempList = (ArrayList<Circle>)circleList.clone();
            	} else if(circleList.size() == 0) {
            		circleList = (ArrayList<Circle>)tempList.clone();
                    canvas.repaint();
            	}
                // Repaint the Canvas

            }
        }
        
        
    }

    /**
     * Step2: A listener class used to set the color chosen by the user via the
     * ComboBox of Colors.
     */
    private class ColorComboBoxHandler implements EventHandler<ActionEvent>
    {

    	//obtains value from the 3rd button (combobox) and sets the next circle drawn by the user as such
        @Override
        public void handle(ActionEvent e)
        {
            //write your code here
        	String colorValue = comboBoxColors.getValue();
        	
        	//The colors are "BLACK (default)", "RED", "BLUE", "GREEN", "ORANGE"
        	
        	if(colorValue.equals("RED")) {
        		circleColor = Color.RED;
        	} else if(colorValue.equals("BLUE")) {
        		circleColor = Color.BLUE;
        	} else if(colorValue.equals("GREEN")) {
        		circleColor = Color.GREEN;
        	} else if(colorValue.equals("ORANGE")) {
        		circleColor = Color.ORANGE;
        	} else if(colorValue.equals("BLACK (default)")) {
        		circleColor = Color.BLACK;
        	}
        }

    }

    /**
     * A listener class that handles any mouse events on the Canvas
     */
    private class PointerHandler implements EventHandler<MouseEvent>
    {
        // 1=pressed, 2=dragged, 3=released
        private int x1, y1, x2, y2, x3, y3;
        private Circle temp;

        
        //gets all the mouse positions and actually draws the circle given them accordingly
        @Override
        public void handle(MouseEvent e)
        {
            //write your code here
        	Circle temp;
        	//logical order since user first presses, then drags, then releases their mouse
        	if(e.getEventType() == MouseEvent.MOUSE_PRESSED) {
        		x1 = (int)e.getX();
        		y1 = (int)e.getY();
        	} else if(e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
        		x2 = (int)e.getX();
        		y2 = (int)e.getY();
            	temp = new Circle(x1, y2, getDistance(x1,y1,x2,y2), circleColor);
            	canvas.getChildren().add(temp);
        	} else if(e.getEventType() == MouseEvent.MOUSE_RELEASED) {
        		//efficiently checks if x2 and y2 are defined to prevent single click creation of circles
        		if(!(x2 + y2 == 0)) {
	            	temp = new Circle(x1, y2, getDistance(x1,y1,x2,y2), circleColor);
	            	canvas.getChildren().add(temp);
	            	circleList.add(temp);
        		}
        	}

        }

        /**
         * A helper method in case you need it. Get the Euclidean distance between (x1,y1) and (x2,y2)
         */
        private double getDistance(int x1, int y1, int x2, int y2)
        {
            return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        }

    }//end of class PointerHandler
}//end of DisplayCirclePane class