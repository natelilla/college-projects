package FourThreadWithBarrier;
import java.util.concurrent.CyclicBarrier;

/**
 * This is the main runner for the 4-thread with barrier program.
 *
 * <p>
 *     This is the class that will be calling all of the methods in the GridFill and ThreadWBarrier class.
 *     This main runners goal is to get the formatting for everything in the program.
 *     It does not do any major calculations, just necessary ones like finding out where on the grid each
 *     quadrant is. It also creates threads and assigns them to a quadrant to run on.
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
        These are all class variables that are used throughout the entire program
         */
        // Array that holds the average of the entire grid
        double[] avg = new double[4];

        // Array that holds error of the entire grid
        double[] error = new double[4];

        // Creates the value that is held on the left column of the table
        int leftSide = 10;

        // Creates the value that is held on the top row of the table
        int topSide = 90;

        // Creates the value that is held on the right column of the table
        int rightSide = 80;

        // Creates the value that is held on the bottom row of the table
        int bottomSide = 20;

        // This holds the desired amount of rows and columns
        int rowsAndColumns = 1000;

        // This holds the amount if desired threads
        int threadCount = 4;

        // Array that specifies the grid dimensions
        double[][] grid = new double[rowsAndColumns][rowsAndColumns];

        // Sends the appropriate variables to the GridFill class to ensure the grid is filled with correct values
        GridFill.gridInsert(grid, rowsAndColumns, topSide, bottomSide, rightSide, leftSide);

        // Formatting
        System.out.println("4-Thread Solution Without Barrier:\n");

        // Start the timer
        long startTimer = System.currentTimeMillis();

        // Find the middle value (used to split the grid into four equal quadrants)
        int findMiddle = rowsAndColumns - 2;

        // Create the variables that tell the threads where to run
        int rowStartPoint = 0 , columnStartPoint = 0 , rowEndPoint = 0 , colEndPoint = 0;

        // This is the barrier variable being created
        CyclicBarrier barrier = new CyclicBarrier(4);

        // Create all four threads
        Thread[] threads = new Thread[threadCount];
        for(int i = 0; i < threadCount  ; i++ ) {
			/*
			The following section splits up the work for all the threads
			 */
            // Top left quadrant
            if ( i == 0 ) {
                rowStartPoint = 1;
                columnStartPoint = 1;
                rowEndPoint = findMiddle / 2;
                colEndPoint = findMiddle / 2;
            }

            // Top right quadrant
            if ( i == 1 ) {
                rowStartPoint = 1;
                columnStartPoint = findMiddle / 2 + 1;
                rowEndPoint = (findMiddle / 2);
                colEndPoint = (findMiddle / 2 + 1) + ((findMiddle / 2) - 1);
            }

            // Bottom left quadrant
            if ( i == 2 ) {
                rowStartPoint = findMiddle / 2 + 1;
                columnStartPoint = 1;
                rowEndPoint = (findMiddle / 2 + 1) + ((findMiddle / 2) - 1);
                colEndPoint = findMiddle / 2;
            }

            // Bottom right quadrant
            if ( i == 3 ) {
                rowStartPoint = findMiddle / 2 + 1;
                columnStartPoint = (findMiddle / 2 + 1);
                rowEndPoint = (findMiddle / 2 + 1) + ((findMiddle / 2) - 1);
                colEndPoint = (findMiddle / 2 + 1) + ((findMiddle / 2) - 1);
            }

            // Send these to the override function to ensure the work is split
            threads[i] = new ThreadWBarrier(rowsAndColumns, rowStartPoint , rowEndPoint , columnStartPoint , colEndPoint , grid, avg, error, i, threads, barrier);
        }

        // Start threads allowing them to run
        threads[0].start();
        threads[1].start();
        threads[2].start();
        threads[3].start();

        // For loop to join all threads back together when they are done running
        for(int i = 0 ; i < threadCount  ; i++ ) {
            try {
                threads[i].join();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Output for each array values (error and average at this iteration)
        for(int i = 0; i < threadCount; i++) {
            System.out.println("Thread " + (i+1) + " Average: " + avg[i]);
            System.out.println("Thread " + (i+1) + " Error: " + error[i]);
        }

        // Stop the timer
        long stopTimer = System.currentTimeMillis();

        // Calculate the time take for the entire program to run
        long timeTaken = stopTimer - startTimer;

        // Formatting for results section
        System.out.println();
        System.out.println("================================================================================");
        System.out.println("Final Grid:");
        for (int i = 0; i < rowsAndColumns; i++) {
            for (int j = 0; j < rowsAndColumns; j++) {
                System.out.printf(  " %.2f " , grid[i][j] );
            }
            System.out.println();
        }
        System.out.println("================================================================================");
        System.out.println();

        // Formatting for the final thread results
        for(int i = 0; i < threadCount; i++) {
            System.out.println("Thread " + (i+1) + " Average: " + avg[i]);
            System.out.println("Thread " + (i+1) + " Error: " + error[i]);
            System.out.println();
        }

        // Add up the entire grid's values
        double averageTotal = 0;
        for (int i = 0; i < threadCount; i++) {
            averageTotal += avg[i];
        }

        // Calculate the total average dividing the previous value from the number of rows and columns
        averageTotal = averageTotal / ((rowsAndColumns - 2) * (rowsAndColumns - 2));

        // Sum of the entire grid's error
        double errorTotal = 0;
        for (int i = 0; i < threadCount; i++) {
            errorTotal += error[i];
        }

        // Formatting for the output
        System.out.println("================================================================================");
        System.out.println("Error has reached under a total of 5. These are the results of the test: ");
        System.out.println();
        System.out.println("Time Taken: " +  timeTaken + "ms" );
        System.out.printf("Middle Plate Value: %.2f \n", grid[rowsAndColumns/2][rowsAndColumns/2] );
        System.out.printf("Grid Average After Running: %.2f \n", averageTotal);
        System.out.printf("Total Error After Running: %.2f \n", errorTotal);
        System.out.println("================================================================================");
    }
}
