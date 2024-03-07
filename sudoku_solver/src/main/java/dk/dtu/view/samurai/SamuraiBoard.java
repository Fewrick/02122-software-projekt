package dk.dtu.view.samurai;


public class SamuraiBoard {
    // Definerer fem separate 9x9 grids til Samurai Sudoku
    public static int[][] topLeftGrid = new int[9][9];
    public static int[][] topRightGrid = new int[9][9];
    public static int[][] bottomLeftGrid = new int[9][9];
    public static int[][] bottomRightGrid = new int[9][9];
    public static int[][] centerGrid = new int[9][9];

    // Eksempel på initialisering af et af de 9x9 grids med nogle forudbestemte værdier
    // Dette er kun et eksempel. I en reel implementering vil du initialisere grids med værdier, der former et gyldigt Samurai Sudoku puzzle
    static {
        // Initialiserer topLeftGrid (som eksempel)
        for (int i = 0; i < topLeftGrid.length; i++) {
            for (int j = 0; j < topLeftGrid[i].length; j++) {
                topLeftGrid[i][j] = 0;
            }
        }
        topLeftGrid[0][0] = 1;
        topLeftGrid[8][8] = 9;

    }


}

