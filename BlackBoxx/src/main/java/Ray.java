import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.List;

public class Ray {
    public ConstantValues.direction goingTo;//Direction whcih array came from
    double x;
    double y;
    int rowIndex;
    int colIndex;
    Line line;
    Pane parentpane;


    public Ray(ConstantValues.direction goingTo, double x, double y, Pane pane){//constrctor for the ray
        parentpane = pane;
        this.goingTo = goingTo;
        this.x = x;
        this.y = y;

        Point2D targetPoint = new Point2D(x, y); // Example coordinates
        Hexagon hex = findNearestHexagon(targetPoint);//find the nearest hexagon to the target point
        createRay();
    }

    public void createRay(){
        while(isThereNextHex()){
            createLine();
            calculateEndPoint();
            if(isThereNextHex()){
                Hexagon hextocheck = ConstantValues.hexList.get(rowIndex).get(colIndex);
                //Add if statements for the coi reflections
                if(hextocheck.hasBorderingAtom) {
                    if (hextocheck.atomPlacement == ConstantValues.atomPlacement.UPRIGHT) {
                        if (goingTo == ConstantValues.direction.S_WEST) {
                            createLine();
                            System.out.println("hit");
                            break;
                        } else if (goingTo == ConstantValues.direction.S_EAST) {
                            createLine();
                            goingTo = ConstantValues.direction.EAST;
                            createRay();
                        } else if (goingTo == ConstantValues.direction.WEST) {
                            createLine();
                            goingTo = ConstantValues.direction.N_WEST;
                            createRay();
                        }

                    }
                }else if (hextocheck.atomPlacement == ConstantValues.atomPlacement.UPLEFT) {
                    if (goingTo == ConstantValues.direction.S_EAST) {
                        createLine();
                        System.out.println("hit");
                        break;
                    }
                    else if (goingTo == ConstantValues.direction.EAST) {
                        createLine();
                        goingTo = ConstantValues.direction.N_EAST;
                        createRay();
                    }
                    else if (goingTo == ConstantValues.direction.S_WEST) {
                        createLine();
                        goingTo = ConstantValues.direction.WEST;
                        createRay();
                    }
                } else if (hextocheck.atomPlacement == ConstantValues.atomPlacement.LEFT) {
                    if (goingTo == ConstantValues.direction.EAST) {
                        createLine();
                        System.out.println("hit");
                        break;
                    }
                    else if (goingTo == ConstantValues.direction.N_EAST) {
                        createLine();
                        goingTo = ConstantValues.direction.N_WEST;
                        createRay();
                    }
                    else if (goingTo == ConstantValues.direction.S_EAST) {
                        createLine();
                        goingTo = ConstantValues.direction.S_WEST;
                        createRay();
                    }
                }else if (hextocheck.atomPlacement == ConstantValues.atomPlacement.DOWNLEFT) {
                    if (goingTo == ConstantValues.direction.N_EAST) {
                        createLine();
                        System.out.println("hit");
                        break;
                    }
                    else if (goingTo == ConstantValues.direction.EAST) {
                        createLine();
                        goingTo = ConstantValues.direction.S_EAST;
                        createRay();
                    }
                    else if (goingTo == ConstantValues.direction.N_WEST) {
                        createLine();
                        goingTo = ConstantValues.direction.WEST;
                        createRay();
                    }
                } else if (hextocheck.atomPlacement == ConstantValues.atomPlacement.DOWNRIGHT) {
                    if (goingTo == ConstantValues.direction.N_WEST) {
                        createLine();
                        System.out.println("hit");
                        break;
                    }
                    else if (goingTo == ConstantValues.direction.WEST) {
                        createLine();
                        goingTo = ConstantValues.direction.S_WEST;
                        createRay();
                    }
                    else if (goingTo == ConstantValues.direction.N_EAST) {
                        createLine();
                        goingTo = ConstantValues.direction.EAST;
                        createRay();
                    }
                }else if (hextocheck.atomPlacement == ConstantValues.atomPlacement.RIGHT) {
                    if (goingTo == ConstantValues.direction.WEST) {
                        createLine();
                        System.out.println("hit");
                        break;
                    }
                    else if (goingTo == ConstantValues.direction.S_WEST) {
                        createLine();
                        goingTo = ConstantValues.direction.S_EAST;
                        createRay();
                    }
                    else if (goingTo == ConstantValues.direction.N_WEST) {
                        createLine();
                        goingTo = ConstantValues.direction.N_EAST;
                        createRay();
                    }
                }



                }else{
                return;
            }
        }
    }



    //method to find the nearest hexagon from the circle/arrow
    public Hexagon findNearestHexagon(Point2D point) {
        Hexagon nearestHexagon = null;
        double minDistance = Double.MAX_VALUE;
        for(int row = 0; row < ConstantValues.hexList.size(); row++) {
            for (int col = 0; col < ConstantValues.hexList.get(row).size(); col++) {
                Hexagon hexagon = ConstantValues.hexList.get(row).get(col);
                double distance = calculateDistance(point, hexagon.centreX, hexagon.centreY);
                if (distance < minDistance) {
                    minDistance = distance;
                    nearestHexagon = hexagon;
                    rowIndex = row;
                    colIndex = col;
                }
            }
        }
        nearestHexagon.setFill(Color.RED);//set the color of the nearest hexagon to red
        return nearestHexagon;
    } //works

    //Method to calculate the Euclidean distance between two points
    private double calculateDistance(Point2D point, double x, double y) {
        double dx = point.getX() - x;
        double dy = point.getY() - y;
        return Math.sqrt(dx * dx + dy * dy);
    }
    // Method to calculate the next endpoint based on the direction of the ray
    private void calculateEndPoint() {
        // Calculate the endpoint based on the direction of the ray
        //System.out.println(rowIndex + "    " + colIndex);
        //cases for different directions
        switch (goingTo) {
            case EAST:
                colIndex += 1;
                break;
            case WEST:
                colIndex -= 1;
                break;
            case S_EAST: //done
                if(rowIndex>=4) {
                    rowIndex += 1;
                }else{
                    colIndex += 1;
                    rowIndex += 1;
                }
                break;
            case N_EAST: // done
               //System.out.println("here");
                if(rowIndex<=4) {
                    rowIndex -= 1;
                }else{
                    colIndex += 1;
                    rowIndex -= 1;
                }

                break;
            case N_WEST: //done
                if(rowIndex<=4) {
                    rowIndex -= 1;
                    colIndex -= 1;
                }else{
                    rowIndex -= 1;
                }
                break;
            case S_WEST://done
                if(rowIndex>=4) {
                    rowIndex += 1;
                    colIndex -= 1;
                }else{
                    rowIndex += 1;
                }
                break;
            default:
                break;
        }
    }

    private void createLine() {
        if (isThereNextHex()) {
            line = new Line(x, y, ConstantValues.hexList.get(rowIndex).get(colIndex).centreX, ConstantValues.hexList.get(rowIndex).get(colIndex).centreY);
            line.setStrokeWidth(1); // Adjust the thickness as needed
            line.setStroke(Color.BLUE); // Set the color to blue
            parentpane.getChildren().add(line);//Add line to the parent pane
            x = ConstantValues.hexList.get(rowIndex).get(colIndex).centreX;
            y = ConstantValues.hexList.get(rowIndex).get(colIndex).centreY;
        }else{
            return;
        }
    }
//method to check if there is a hexagon in the rays path
    private boolean isThereNextHex() {
        if (rowIndex < 0 || rowIndex >= ConstantValues.hexList.size()) {
            System.out.println("Left the hexagonalboard");
            return false;
        }
        List<Hexagon> row = ConstantValues.hexList.get(rowIndex);
        if (colIndex < 0 || colIndex >= row.size()) {
            System.out.println("Left the hexagonalboard");
            return false;
        }
        return true;
    }

}
