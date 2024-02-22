import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;

public class Hexagon extends Polygon {
    private double x; //coordinates
    private double y;
    private static int atomCount = 0;   //static for the class, not for every instance
    private boolean hasAtom = false;
    private Atom atom;

    private static final ArrayList<Atom> atomList = new ArrayList<>();

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

    public static void main(String[] args) {
        //trying to check if AtomList is working correctly
        for(Atom at: atomList){
            System.out.println(at.toString());
        }
    }

    public void setPosXY(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void createHex()
    {
        for (int i = 0; i < 6; i++) {
            double angle = 2 * Math.PI * (i + 0.5) / 6;
            double z = ConstantValues.HEXAGON_RADIUS * Math.cos(angle);
            double a = ConstantValues.HEXAGON_RADIUS * Math.sin(angle);
            getPoints().addAll(z, a);
        }
        setStrokeWidth(ConstantValues.HEXAGON_STROKE_WIDTH);
        setStroke(ConstantValues.HEXAGON_STROKE); // set the outline color to yellow
        setFill(ConstantValues.HEXAGON_COLOR);
    }

    private void createAtom() {
        Atom atom = new Atom(x, y);
        atom.createCircle();
        Pane parentPane = (Pane) getParent();
        parentPane.getChildren().add(atom.getCircle());
        parentPane.getChildren().add(atom.getCOI());
        atomList.add(atom);
        this.atom = atom;
        atomCount++;
        hasAtom = true;
    }

    private void removeAtom() {
        Pane parentPane = (Pane) getParent();
        parentPane.getChildren().remove(atom.getCircle());
        atomList.remove(atom);
        atomCount--;
        hasAtom = false;
        this.atom = null;
    }

    public String toString() {
        if (!hasAtom) {
            return "There is no Atom here";
        } else if (atom != null) {
            return atom.toString();
        } else {
            return "(" + x + ", " + y + ")";
        }
    }
}







