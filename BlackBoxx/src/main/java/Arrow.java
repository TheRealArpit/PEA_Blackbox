import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Arrow {
    public static int count = 0;
    public Circle dot;
    public int number;

    public Arrow(double xLoc, double yloc) {
        //making circle
        dot = new Circle(xLoc, yloc, 5);
        dot.setFill(Color.PINK);

        number = count;
        //making the text
        Text text = new Text(String.valueOf(count));
        text.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        text.setFill(Color.RED);
        text.setLayoutX(xLoc);
        text.setLayoutY(yloc);
    }

    public void addCount(){
        count++;
    }
}
