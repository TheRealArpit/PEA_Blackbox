import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeType;

import java.util.ArrayList;
//private static ArrayList<Atom> atomList = new ArrayList<Atom>();



public class Atom extends Polygon {
    private double x;
    private double y;
    private Circle circle;
    private Circle COI;
    public  boolean visibility = true;

    public Atom(double x, double y) {
        this.x = x;
        this.y = y;
        createCircle();
        //atomList.add(this);
    }

    public void createCircle() {
        // Add the border circle to the same position as the main circle

        circle = new Circle();
        circle.setRadius(ConstantValues.ATOM_RADIUS);
        circle.setFill(ConstantValues.ATOM_COLOR);
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setMouseTransparent(true); // Make the circle transparent to mouse events

        // Create a new circle for the border
        COI = new Circle();
        COI.setRadius(ConstantValues.COI_RADIUS); // Same radius as the main circle
        COI.setFill(Color.TRANSPARENT); // Transparent fill
        COI.setStroke(Color.RED); // Red stroke color
        COI.setStrokeWidth(3); // Stroke width
        COI.setStrokeType(StrokeType.OUTSIDE); // Stroke type
        COI.getStrokeDashArray().addAll(10d, 10d); // Dotted line
        COI.setMouseTransparent(true); // Make the circle transparent to mouse events
        COI.setCenterX(x);
        COI.setCenterY(y);

    }

    public Circle getCircle() {
        return circle;
    }
    public Circle getCOI() {
        return COI;
    }

    public  void hideAtom(){
        getCircle().setVisible(false);
        getCOI().setVisible(false);
    }
    public  void showAtom(){
        getCircle().setVisible(true);
        getCOI().setVisible(true);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
