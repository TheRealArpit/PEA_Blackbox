import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;


public class Atom {
    private double x;
    private double y;
    private Circle circle;
    private Circle COI;

    public Atom(double x, double y) {
        this.x = x;
        this.y = y;
        createCircle();
    }

    public void createCircle() {

        circle = new Circle();
        circle.setRadius(ConstantValues.ATOM_RADIUS);
        circle.setFill(ConstantValues.ATOM_COLOR);
        circle.setCenterX(x);
        circle.setCenterY(y);

        // Create a new circle for the border
        COI = new Circle();
        COI.setRadius(ConstantValues.COI_RADIUS); // Same radius as the main circle
        COI.setFill(Color.TRANSPARENT); // Transparent fill
        COI.setStroke(Color.RED); // Red stroke color
        COI.setStrokeWidth(3); // Stroke width
        COI.setStrokeType(StrokeType.OUTSIDE); // Stroke type
        COI.getStrokeDashArray().addAll(10d, 10d); // Dotted line

        // Add the border circle to the same position as the main circle
        COI.setCenterX(x);
        COI.setCenterY(y);

    }

    public Circle getCircle() {
        return circle;
    }
    public Circle getCOI() {
        return COI;
    }


    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
