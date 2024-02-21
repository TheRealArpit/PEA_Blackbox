import javafx.scene.shape.Polygon;

public class Hexagon extends Polygon{
    public int x;
    public int y;
    public type typeOfHexagon;

    Hexagon east;

    public enum type{
        ATOM,
        COF,
        EMPTY
    };

    public Hexagon(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x+ ", " + y + ")";
    }

}
