package SingleThread;

/**
 * This class contains the run function. It will be the one that executes commands and does the
 * calculations.
 *<p>
 *     This class is the one that has an overridden run function. It ensures that the threads
 *     are running into the correct quadrants as well as splitting work evenly.
 *</p>
 */
public class ThreadSingle extends java.lang.Thread implements Runnable {

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

    /**
     * This is the constructor.
     * @param rowStartPoint - an int for rows
     * @param rowEndPoint - an int for rows
     * @param columnStartPoint - an int for columns
     * @param columnEndPoint - an int for columns
     * @param grid - an object for the matrix
     */
    public ThreadSingle(int rowsAndColumns, int rowStartPoint, int rowEndPoint, int columnStartPoint, int columnEndPoint, double[][] grid, double[] averagePerThread, double[] errorPerThread, int threadID, Thread[] threads) {
       this.threads = threads;
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

        // Do-while loop that allows threads to run until error is under 5
        do {
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
            // Print total error
            System.out.println("Total Error = " + errorPerThread[threadID]);
        }

        // While condition that adds all threads error up to ensure it is NOT under five
        // If under five, terminate the program
        while ((errorPerThread[0]) > 5);
    }
}
