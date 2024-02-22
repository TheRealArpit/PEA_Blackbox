import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

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
        circle.setRadius(10);
        circle.setFill(Color.RED);
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
