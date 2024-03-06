import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Arrow {
    public static int count = 0;
    public Circle dot;
    public int number;
    public Text text;

    direction goingto;

    public enum direction{
        N_EAST,
        EAST,
        S_EAST,
        S_WEST,
        WEST,
        N_WEST
    }




    public Arrow(double xLoc, double yloc, int i) {
        //making circle
        dot = new Circle(xLoc, yloc, 5);
        dot.setFill(Color.PINK);

        number = count;
        //making the text
        text = new Text(String.valueOf(count));
        //text.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        text.setFill(Color.RED);
        text.setLayoutX(xLoc);
        text.setLayoutY(yloc);

        if (i==2){ //2 is going to the right
            goingto = direction.EAST;
        }else if (i==3){ //3 is going to the diagonally down right
            goingto = direction.S_EAST;
        }else if (i==4){ //4 is going diagonally down left
            goingto = direction.S_WEST;
        }else if (i==5){ //5 is going to the left
            goingto = direction.S_WEST;
        }else if (i==0){ //0 is going diagonally up left
            goingto = direction.N_WEST;
        }else if (i==1) { //1 is going diagonally up right
            goingto = direction.N_EAST;
        }
    }

    public void addCount(){
        count++;
    }
}
