import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Ray {
    public ConstantValues.direction cameFrom;
    double x;
    double y;
    double endX=0;
    double endY=0;
    Line line;
    Pane parentpane;

    public Ray(ConstantValues.direction cameFrom, double x, double y, Pane pane){
        parentpane = pane;
        this.cameFrom = cameFrom;
        this.x = x;
        this.y = y;
        calculateEndPoint();
        createLine();
        parentpane.getChildren().add(line);
    }

    private void calculateEndPoint() {
        // Calculate the endpoint based on the direction of the ray
        switch (cameFrom) {
            case EAST:
                System.out.println(x + " , "+y);
                endX = x + 76*6; // Adjust according to your needs
                //endY = y + 66; // Adjust according to your needs
                break;
            // Add cases for other directions as needed
            default:
                // Handle default case or other directions
                break;
        }
    }

    private void createLine() {
        // Create the line from the start point (x, y) to the end point (endX, endY)
        line = new Line(x, y, endX, y);
        line.setStrokeWidth(3); // Adjust the thickness as needed
        line.setStroke(Color.BLUE); // Set the color to blue
    }

    public Line getLine() {
        return line;
    }
}
