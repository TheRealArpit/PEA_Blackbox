import javafx.scene.shape.Polygon;

import javax.swing.plaf.PanelUI;
import java.lang.reflect.Constructor;

public class Ray {
    public ConstantValues.direction cameFrom;
    double x;
    double y;


    public Ray(ConstantValues.direction cameFrom, double x, double y) {
        this.cameFrom = cameFrom;
        this.x = x;
        this.y = y;
    }
}
