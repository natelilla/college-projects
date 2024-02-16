package FourThreadWithBarrier;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.*;

/**
 * This class contains the run function. It will be the one that executes commands and does the
 * calculations.
 *<p>
 *     This class is the one that has an overridden run function. It ensures that the threads
 *     are running into the correct quadrants as well as splitting work evenly.
 *</p>
 */
public class ThreadWBarrier extends java.lang.Thread implements Runnable {

    // Holds the total amount of rows and columns
    int rowsAndColumns;

    // Start point for the rows
    private int rowStartPoint;

    // End point for the rows
    private int rowEndPoint;

    // Start point for columns
    private int columnStartPoint;

    // End point for columns
    private int columnEndPoint;

    // Array that holds threads
    Thread threads[];

    // Array that holds the average per each thread
    double[] averagePerThread;

    // Array that holds the error per thread
    double[] errorPerThread;

    // Array matrix that holds the grid
    double[][] grid;

    // Int that holds the thread ID
    int threadID;

    // Barrier object
    CyclicBarrier barrier;

    /**
     * Constructor for the class. It will change all class level variables to method level/usable variables.
     * @param rowsAndColumns - int for rows and columns
     * @param rowStartPoint - int for start point (row)
     * @param rowEndPoint - int for end point (row)
     * @param columnStartPoint - int for start point (col)
     * @param columnEndPoint - int for end point (col)
     * @param grid - array that holds the grid
     * @param averagePerThread - array for average per thread
     * @param errorPerThread - array for error per thread
     * @param threadID - int for thread ID
     * @param threads - array that holds threads
     * @param barrier - object that has barrier
     */
    public ThreadWBarrier(int rowsAndColumns, int rowStartPoint , int rowEndPoint , int columnStartPoint, int columnEndPoint, double[][] grid, double[] averagePerThread, double[] errorPerThread, int threadID, Thread[] threads, CyclicBarrier barrier) {
       this.threads = threads;
       this.barrier = barrier;
        this.rowsAndColumns = rowsAndColumns;
        this.rowEndPoint = rowEndPoint;
        this.rowStartPoint = rowStartPoint;
        this.columnStartPoint = columnStartPoint;
        this.columnEndPoint = columnEndPoint;
        this.grid = grid;
        this.averagePerThread = averagePerThread;
        this.errorPerThread = errorPerThread;
        this.threadID = threadID;
    }

    /**
     * This is the run() function. It will be the method that runs the calculations. It also contains a barrier
     * that will sync up all threads while running. This method is a part of the extended Runnable in the class
     * definition.
     */
    @Override
    public void run() {

        // Formatting for the output (get the threads ID)
        System.out.println("Thread: " + Thread.currentThread().getId());

        // Barrier that syncs up all threads using try-catch
        try {
            barrier.await();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

        // Do-while loop that allows threads to run until error is under 5
        do {
            try {
                barrier.await();
            }
            catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }

            // Reset the error and average per each thread
            errorPerThread[threadID] = 0;
            averagePerThread[threadID] = 0;

            // Nested for loop that updates the values as the iterations continue
            for (int i = rowStartPoint; i <= rowEndPoint; i++) {
                for (int j = columnStartPoint; j <= columnEndPoint; j++) {
                    double oldAverage = 0;
                    double newAverage = 0;

                    // Set the old average to the old grid
                    oldAverage = grid[i][j];

                    // Calculate average for the entire grid
                    double average = (grid[i - 1][j] + grid[i + 1][j] + grid[i][j - 1] + grid[i][j + 1]);
                    grid[i][j] = ((average / 4 * 10.0) / 10.0);
                    newAverage = grid[i][j];
                    averagePerThread[threadID] += newAverage;

                    // Calculate error for the entire grid
                    double error = newAverage - oldAverage;
                    errorPerThread[threadID] = errorPerThread[threadID] + error;
                }
            }

            // Try-catch to ensure there are no exceptions
            try {
                barrier.await();
            }
            catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            // Print total error
            System.out.println("Total Error = " + errorPerThread[threadID]);
        }

        // While condition that adds all threads error up to ensure it is NOT under five
        // If under five, terminate the program
        while ((errorPerThread[0] + errorPerThread[1] + errorPerThread[2] + errorPerThread[3]) > 5);
    }
}
