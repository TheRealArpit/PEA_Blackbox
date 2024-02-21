import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    public static ArrayList<Hexagon> hexagonArrayList;

    public  static void main(String[] args) {
         hexagonArrayList = createBoard();
        Hexagon hexx = findHex(1,0);
        System.out.println(hexx);
    }


    public static ArrayList<Hexagon> createBoard(){
        int[][][] a = new int[9][9][2];
        ArrayList<Hexagon> arr = new ArrayList<>();
        // Fill up the array with the provided values
        for(int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                a[y][x][0] = y;
                a[y][x][1] = x;
            }
        }
        int[] rows = {5, 6, 7, 8, 9, 8, 9, 9, 9}; // number of hexagons in each row
        int[] rowindex = {0, 0, 0, 0, 0, 1, 2, 3, 4};

        for(int y = 0; y < 9; y++) {
            int startIndex = rowindex[y];
            for (int x = startIndex; x < rows[y]; x++) {
                System.out.print(a[x][y][0] + ""+ a[x][y][1] + "  ");
                Hexagon hex = new Hexagon(a[x][y][0], a[x][y][1]);
                arr.add(hex);
            }
            System.out.println();
        }
        System.out.println(arr.toString());
        return arr;
    }

    public static Hexagon findHex(int x, int y){
        for(Hexagon a: hexagonArrayList){
            if (a.x == x && a.y == y){
                return a;
            }
        }
        return null;
    }

}
