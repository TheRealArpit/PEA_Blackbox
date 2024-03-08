import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;


import java.util.ArrayList;

public class Hexagon extends Polygon {
    private double x; //coordinates
    private double y;
    public double centreX;
    public double centreY;
    public static int atomCount = 0;   //static for the class, not for every instance
    public int BorderingAtoms = 0;
    public boolean hasAtom = false;
    public Atom atom;
    public static Pane parentpane;

    public static final ArrayList<Atom> atomList = new ArrayList<>();//list to keep track of all atoms in hexagons

    public Hexagon() {
        super(); // Call the Polygon constructor
        //event handlers for mouse
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

    public void setPane(Pane pane){//parent pane for the hexagon
        parentpane = pane;
    }
    public void setPosXY(double x, double y) {//set x and y coordinates for the hexagon
        this.x = x;
        this.y = y;
    }

    public void createHex(int row, int column, int[] columns)//method to create hexagon
    {
        centreX=0;
        centreY =0;
        for (int i = 0; i < 6; i++) {//loop through each corner of hexagon
            double angle = 2 * Math.PI * (i + 0.5) / 6;//angle for each corner
            //coordinates for the hexagon
            double z = ConstantValues.HEXAGON_RADIUS * Math.cos(angle) ;
            double a = ConstantValues.HEXAGON_RADIUS * Math.sin(angle) ;
            //update center coordinates
            centreX += z;
            centreY += a;
            //midpoint
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
                //Calculate the midpoint of the line connecting point1 and point2
                double midXArrowLocation = (point1x + point2x) / 2 + x;
                double midYArrowLocation = (point1y + point2y) / 2 + y;

                Arrow arrow = new Arrow(midXArrowLocation, midYArrowLocation,i, parentpane);
                //parentpane.getChildren().add(arrow.ray.line);
                parentpane.getChildren().add(arrow.dot);
                parentpane.getChildren().add(arrow.text);
                arrow.addCount();
            }
            //Add the corner coordinates to the hexagons points
            getPoints().addAll(z, a);
        }
        //calculate final center coordinates
        centreY = (centreY /6) + y;
        centreX = (centreX/ 6) + x;

        setStrokeWidth(ConstantValues.HEXAGON_STROKE_WIDTH);
        setStroke(ConstantValues.HEXAGON_STROKE);
        setFill(ConstantValues.HEXAGON_COLOR);
    }

    private void createAtom() {//method to create atom
        Atom atom = new Atom(x, y);//create atom with given coordinates
        atom.createCircle();//create visual atom
        Pane parentPane = (Pane) getParent();//
        parentPane.getChildren().add(atom.getCOI());//add the coi
        parentPane.getChildren().add(atom.getCircle());

        atomList.add(atom);//add the atom to the list of atoms
        this.atom = atom;
        atomCount++;//increment the count
        hasAtom = true;//indicate that the hexagon has an atom
    }

    public void removeAtom() {//method to remove atoom
        Pane parentPane = (Pane) getParent();//get parent pane
        parentPane.getChildren().remove(atom.getCircle());//remove the atoms circle
        parentPane.getChildren().remove(atom.getCOI());//remove coi of the atom
        atomList.remove(atom);//remove atom from list of atoms
        atomCount--;//decrement count
        hasAtom = false;//indicate that the hexagon doesnt have an atom
        this.atom = null;//set the reference to null
    }

    public String toString() {
        return "(" + centreX + ", " + centreY + ") "+ BorderingAtoms +")\n";
    }
    public void addBorderingAtoms(){//method to increment the count of bordering atoms
        BorderingAtoms++;
    }

    public void removeBorderingAtoms(){//method to decrement the count of bordering atoms
        BorderingAtoms--;
    }
}







