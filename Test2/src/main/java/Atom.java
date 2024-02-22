import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class Atom {
    private double x;
    private double y;
    private Circle circle;

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
    }

    public Circle getCircle() {
        return circle;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
