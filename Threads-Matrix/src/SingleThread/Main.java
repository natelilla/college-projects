package SingleThread;

/**
 * This is the main runner for the single-thread without barrier program.
 *
 * <p>
 *     This is the class that will be calling all of the methods in the GridFill and ThreadWBarrier class.
 *     This main runners goal is to get the formatting for everything in the program.
 *     It does not do any major calculations, just necessary ones.
 * </p>
 *
 * @author - Nate Lilla
 * @class - CSCI 370
 * @assignment - 2
 * @bugs - none
 */
public class Main {

    /**
     * This is the main method. It ensures other methods are called and creates an output using those methods.
     * It is the one that creates the flow of the program and calls the methods in the right order to get
     * a correct output and calculations.
     * @param args - default setup for a main method in java
     */
    public static void main(String[] args) {

        /*
        These are all class variables that are used throughout the entire program.
         */
        // Array that holds the average of the entire program
        double[] avg = new double[1];

        // Array that holds error of the entire grid
        double[] error = new double[1];

        // Creates the value that is held on the left column of the table
        int leftSide = 20;

        // Creates the value that is held on the top row of the table
        int topSide = 90;

        // Creates the value that is held on the right column of the table
        int rightSide = 80;

        // This holds the desired amount of rows and columns
        int bottomSide = 10;

        // This holds the desired amount of rows and columns
        int rowsAndColumns = 10;

        // This holds the amount if desired threads
        int threadCount = 1;

        // Array that specifies the grid dimensions
        double[][] grid = new double[rowsAndColumns][rowsAndColumns];

        // Sends the appropriate variables to the GridFill class to ensure the grid is filled with correct values
        GridFill.gridInsert(grid, rowsAndColumns, topSide, bottomSide, rightSide, leftSide);

        // Formatting
        System.out.println("Single Thread Solution:\n");

        // Start the timer
        long startTimer = System.currentTimeMillis();

        // Create the variables that tell the threads where to run
        int rowStartPoint = 0 , columnStartPoint = 0 , rowEndPoint = 0 , columnEndPoint = 0;

        // Create the one thread
        Thread[] threads = new Thread[threadCount];

        // Tells the thread where to start running
        if(threadCount == 1) {
            rowStartPoint = 1;
            columnStartPoint = 1;
            rowEndPoint = ((rowsAndColumns - 1) / 2 + 1) + (((rowsAndColumns - 1) / 2) - 1);
            columnEndPoint = ((rowsAndColumns - 1) / 2 + 1) + (((rowsAndColumns - 1) / 2) - 1);
        }

        // Send these to the override function to ensure running
        threads[0] = new ThreadSingle(rowsAndColumns, rowStartPoint, rowEndPoint, columnStartPoint, columnEndPoint, grid, avg, error, 0, threads);

        // Start all threads
        threads[0].start();

        // Allows the thread to keep running
        for(int i = 0 ; i < threadCount ; i++ ) {
            try {
                threads[i].join();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Total Error and Total Average for each thread
        for(int i = 0; i < threadCount; i++) {
            System.out.println("Thread " + (i+1) + " Average: " + avg[i]);
            System.out.println("Thread " + (i+1) + " Error: " + error[i]);
        }

        // Stop the timer
        long stopTimer = System.currentTimeMillis();
        long timeTaken = stopTimer - startTimer;

        // Formatting for results section
        System.out.println();
        System.out.println("================================================================================");
        System.out.println("Final Grid");
        for (int i = 0; i < rowsAndColumns; i++) {
            for (int j = 0; j < rowsAndColumns; j++) {
                System.out.printf(  " %.2f " , grid[i][j] );

            }
            System.out.println();
        }
        System.out.println("================================================================================");
        System.out.println();

//        for(int i = 0; i < threadCount; i++) {
//            System.out.println("Thread " + (i+1) + " Average: " + avg[i]);
//            System.out.println("Thread " + (i+1) + " Error: " + threadError[i]);
//        }

        // Add up the entire grid's values
        double averageTotal = 0;
        for (int i = 0; i < threadCount; i++) {
            averageTotal += avg[i];
        }

        // Calculate the total average dividing the previous value from the number of rows and columns
        averageTotal = averageTotal/ ((rowsAndColumns - 2) * (rowsAndColumns - 2));

        // Sum of the entire grid's error
        double errorTotal = 0;
        for (int i = 0; i < threadCount; i++) {
            errorTotal += error[i];
        }

        // Formatting for the output
        System.out.println("Error has reached under a total of 5. These are the results of the test: ");
        System.out.println();
        System.out.println("Time Taken: " +  timeTaken + "ms" );
        System.out.printf("Middle Plate Value: %.2f \n", grid[rowsAndColumns/2][rowsAndColumns/2] );
        System.out.printf("Grid Average After Running: %.2f \n", averageTotal);
        System.out.printf("Total Error After Running: %.2f \n", errorTotal);
        System.out.println("================================================================================");
    }
}
