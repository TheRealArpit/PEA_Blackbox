import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Hexagon extends Polygon {
    private double x; //coordinates
    private double y;
    private static int atomCount = 0;   //static for the class, not for every instance
    public int BorderingAtoms = 0;
    public boolean hasAtom = false;
    public Atom atom;
    //public static int count=0;
    public static Pane parentpane;

    public static final ArrayList<Atom> atomList = new ArrayList<>();

    public Hexagon() {
        super(); // Call the Polygon constructor
        setOnMouseEntered(event -> setStroke(Color.RED));
        setOnMouseExited(event -> setStroke(ConstantValues.HEXAGON_STROKE));
        setOnMouseClicked(event -> {
            if (!hasAtom && atomCount < ConstantValues.MAX_ATOMS) {
                createAtom();
            } else if (hasAtom) {
                removeAtom();
            } else {
                System.out.println("Can't add more atoms. Maximum limit reached.");
            }
        });

    }

    public void setPane(Pane pane){
        parentpane = pane;
    }
    public void setPosXY(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void createHex(int row, int column, int[] columns)
    {
        for (int i = 0; i < 6; i++) {
            double angle = 2 * Math.PI * (i + 0.5) / 6;
            double z = ConstantValues.HEXAGON_RADIUS * Math.cos(angle) ;
            double a = ConstantValues.HEXAGON_RADIUS * Math.sin(angle) ;

            double point1x = 60 * Math.cos(angle) ;
            double point1y = 60 * Math.sin(angle) ;

            // Calculate the next point to create the circle at the midpoint
            int nextIndex = (i + 1) % 6;
            double nextAngle = 2 * Math.PI * (nextIndex + 0.5) / 6;

            double point2x = 60 * Math.cos(nextAngle);
            double point2y = 60 * Math.sin(nextAngle);

            //making the arrows
            if ( ((i == 3 || i == 4 || i == 2) && column == 0 && row == 0 || (i == 0 || i == 1 || i == 2) && column == 0 && row == 8) ||
                    (i == 3 || i == 4 || i == 5) && column == 4 && row == 0 || (i == 5 || i == 0 || i == 1) && column == 4 && row == 8 ||
                    (i == 1 || i == 2 || i == 3) && column == 0 && row == 4 || (i == 4 || i == 5 || i == 0) && column == 8 && row == 4 ||
                    (i == 3 || i == 4) && column >0 && column<4 && row == 0 || (i == 0 || i == 1) && column >0 && column<4 && row == 8 ||
                    (i == 3 || i == 2) && row >0 && row<4 && column == 0|| (i == 1 || i == 2) && row >=4 && row<=8 && column == 0  ||
                    (i == 4 ||i==5 ) && row == 2 && column == 6 || (i == 4 ||i==5 ) && row == 1 && column == 5||
                    (i == 4 ||i==5 ) && row == 3 && column == 7|| (i == 0 ||i==5 ) && row == 5 && column == 7 ||
                    (i == 0 ||i==5 ) && row == 7 && column == 5|| (i == 0 ||i==5 ) && row == 6 && column == 6)
            {
                double midXArrowLocation = (point1x + point2x) / 2 + x;
                double midYArrowLocation = (point1y + point2y) / 2 + y;
                Arrow arrow = new Arrow(midXArrowLocation, midYArrowLocation);
                parentpane.getChildren().add(arrow.dot);
                parentpane.getChildren().add(arrow.text);
                arrow.addCount();
            }
            getPoints().addAll(z, a);
        }
        System.out.println(toString());

        setStrokeWidth(ConstantValues.HEXAGON_STROKE_WIDTH);
        setStroke(ConstantValues.HEXAGON_STROKE); // set the outline color to yellow
        setFill(ConstantValues.HEXAGON_COLOR);
    }

    private void createAtom() {
        Atom atom = new Atom(x, y);
        atom.createCircle();
        Pane parentPane = (Pane) getParent();
        parentPane.getChildren().add(atom.getCOI());
        parentPane.getChildren().add(atom.getCircle());
        atomList.add(atom);
        this.atom = atom;
        atomCount++;
        hasAtom = true;
    }

    public void removeAtom() {
        Pane parentPane = (Pane) getParent();
        parentPane.getChildren().remove(atom.getCircle());
        parentPane.getChildren().remove(atom.getCOI());
        atomList.remove(atom);
        atomCount--;
        hasAtom = false;
        this.atom = null;
    }

    public String toString() {
        return "(" + x + ", " + y + ") "+ BorderingAtoms +")\n";
    }
    public void addBorderingAtoms(){
        BorderingAtoms++;
    }

    public void removeBorderingAtoms(){
        BorderingAtoms--;
    }
/*
    public void createArrow(double midZ, double midA){
        //Pane parentPane = (Pane) getParent();
        Circle dot = new Circle(midZ, midA, 10);
        dot.setLayoutX(x);
        dot.setLayoutY(y);
        dot.setFill(Color.PINK); // Ensure a visible color
        parentpane.getChildren().add(dot);
    }*/

    /*private void createArrow(double midZ, double midA) {
        // Create the circle
        Circle dot = new Circle(midZ+x, midA+y, 5);

        dot.setFill(Color.PINK); // Ensure a visible color
        parentpane.getChildren().add(dot);

        Text text = new Text(String.valueOf(count));
        text.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        text.setFill(Color.RED);

        text.setLayoutX(x+midZ);
        text.setLayoutY(y+midA);
        parentpane.getChildren().add(text);
    }
*/
}







