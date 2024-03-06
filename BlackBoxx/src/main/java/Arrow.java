import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Arrow extends Polygon{
    public static int count = 0;
    public Circle dot;
    public int number;
    public Text text;
    public Ray ray;
    public Pane parentpane;


    public Arrow(double xLoc, double yloc, int i, Pane pane) {
        super();
        parentpane = pane;
        //making circle
        dot = new Circle(xLoc, yloc, 10);
        dot.setFill(Color.PINK);
        number = count;
        //making the text
        text = new Text(String.valueOf(count));
        text.setMouseTransparent(true);
        text.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        text.setFill(Color.RED);
        text.setLayoutX(xLoc);
        text.setLayoutY(yloc);
        ConstantValues.direction goingto;
        if (i==2){ //2 is going to the right
            goingto = ConstantValues.direction.EAST;
        }else if (i==3){ //3 is going to the diagonally down right
            goingto = ConstantValues.direction.S_EAST;
        }else if (i==4){ //4 is going diagonally down left
            goingto = ConstantValues.direction.S_WEST;
        }else if (i==5){ //5 is going to the left
            goingto = ConstantValues.direction.S_WEST;
        }else if (i==0){ //0 is going diagonally up left
            goingto = ConstantValues.direction.WEST;
        }else if (i==1) { //1 is going diagonally up right
            goingto = ConstantValues.direction.N_EAST;
        } else {
            goingto = null;
        }
        dot.setOnMouseEntered(event -> dot.setFill(Color.BLUE));
        dot.setOnMouseExited(event -> dot.setFill(Color.PINK));
        dot.setOnMouseClicked(event -> {
            dot.setFill(Color.BLUE);
            ray = new Ray(goingto, xLoc, yloc, parentpane);
        });

    }

    public void addCount(){
        count++;
    }
}
