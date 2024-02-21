import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Hexagon extends Polygon{
    public double x;
    public double y;
    public type typeOfHexagon;
    public enum type{
        ATOM,
        COF,
        EMPTY
    };

    public Hexagon(){
        // Define mouse hover event
        super(); // Call the Polygon constructor

        setOnMouseEntered(event -> {
            //set
            setStroke(Color.RED); // Change stroke color when mouse enters
        });

        // Define mouse exit event
        setOnMouseExited(event -> {
            setStroke(Color.YELLOW); // Revert stroke color when mouse exits
        });

    }
    public void setPosXY(double x, double y){
        this.x = x;
        this.y = y;


    }

    public  void CreateHex(double radius){
        for (int i = 0; i < 6; i++) {
            double angle = 2 * Math.PI * (i + 0.5) / 6; // Calculate the angle for each vertex
            double z = radius * Math.cos(angle); // Calculate the x-coordinate
            double a = radius * Math.sin(angle); // Calculate the y-coordinate
            getPoints().addAll(z, a);
        }
    }

    @Override
    public String toString() {
        return "(" + x+ ", " + y + ")";
    }

}
