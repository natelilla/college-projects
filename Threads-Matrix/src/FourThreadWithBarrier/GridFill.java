package FourThreadWithBarrier;

/**
 * This is the GridFill class for the multi-thread class with a barrier.
 * <p>
 *     This class is what will be filling the grid with specified values. It
 *     also prints output.
 * </p>
 *
 * @author - Nate Lilla
 * @class - CSCI 370
 * @assignment - 2
 * @bugs - none
 */
public class GridFill {

    /**
     * This is the method that does the actual calculations for filling the grid with correct values.
     * @param grid - array that holds the outline of the grid
     * @param rowsAndColumns - int that specifies the amount of rows and columns the grid has
     * @param topSide - int that holds the top value
     * @param bottomSide - int that holds the bottom value
     * @param rightSide - int that holds the right value
     * @param leftSide - in that holds the left value
     */
    public static void gridInsert(double[][] grid, int rowsAndColumns, int topSide, int bottomSide, int rightSide, int leftSide) {

        // Nested for loop that fills the grid with desired values.
        for (int i = 0; i < rowsAndColumns; i++) {
            for (int j = 0; j < rowsAndColumns; j++) {
                if (i == 0) {
                    grid[i][j] = topSide;
                } else if (i == rowsAndColumns - 1) {
                    grid[i][j] = bottomSide;
                } else if (j == 0) {
                    grid[i][j] = leftSide;
                } else if (j == rowsAndColumns - 1) {
                    grid[i][j] = rightSide;
                } else {
                    grid[i][j] = 0;
                }
            }
        }

        // This portion prints out the initial grid
        System.out.println("================================================================================");
        System.out.println("Initial Grid:");
        for (int i = 0; i < rowsAndColumns; i++) {
            for (int j = 0; j < rowsAndColumns; j++) {

                // Round the output to two decimal places.
                System.out.printf(  " %.2f " , grid[i][j] );
            }
            System.out.println();
        }
        System.out.println("================================================================================");
        System.out.println();
    }
}