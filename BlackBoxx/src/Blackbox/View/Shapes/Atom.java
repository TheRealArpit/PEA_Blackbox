package Blackbox.View.Shapes;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

import static Blackbox.Constant.Constants.*;

public class Atom {
    private Pane parantPane;
    private Circle circle;
    private Circle COI;


    public Atom(double x, double y, Pane parentPane){
        this.parantPane = parentPane;
        createAtom(x,y);
    }

    public void createAtom(double x, double y){
        circle = new Circle();
        circle.setRadius(ATOM_RADIUS);
        circle.setFill(ATOM_COLOR);
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setMouseTransparent(true); // Make the circle transparent to mouse events

        COI = new Circle();
        COI.setRadius(COI_RADIUS); // Same radius as the main circle
        COI.setFill(Color.TRANSPARENT); // Transparent fill
        COI.setStroke(Color.RED); // Red stroke color
        COI.setStrokeWidth(3); // Stroke width
        COI.setStrokeType(StrokeType.OUTSIDE); // Stroke type
        COI.getStrokeDashArray().addAll(10d, 10d); // Dotted line
        COI.setMouseTransparent(true); // Make the circle transparent to mouse events
        COI.setCenterX(x);
        COI.setCenterY(y);
    }
    public void addCirCoiPane(){
        parantPane.getChildren().addAll(circle,COI);
    }
    public void removeAtom(){parantPane.getChildren().removeAll(circle,COI);}

    public  void hideAtom(){//hides the atom by setting the visibility of both the main circle and the coi to false
        getCircle().setVisible(false);
        getCOI().setVisible(false);
    }
    public  void showAtom(){//showes the atom by settign the visibility of both the main circle and the coi to true
        getCircle().setVisible(true);
        getCOI().setVisible(true);
    }

    public Circle getCircle() {return circle;}
    public Circle getCOI() {return COI;}

}
