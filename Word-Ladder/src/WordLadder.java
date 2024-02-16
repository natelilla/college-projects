import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


/**
 * This program is a word ladder, meaning it will find the shortest word ladder between two words.
 * It implements preset words ranging from lengths of 3 all the way to 9. The program will take two
 * inputs, one for the beginning word, and one for the end word. It will then find the shortest path
 * from each of them.
 *
 * @author Nate Lilla
 * Class- CSCI 340-001
 * Assignment- 5
 *
 * @bugs- none
 */

public class WordLadder {
    /**
     * This method will find the shortest path between the start and the end of the word
     * @param dictionary
     * @param start
     * @param end
     * @return word object
     */
    private static Word getShortest(Set<String> dictionary, String start, String end) {

        if (dictionary.contains(start) && dictionary.contains(end)) {
            List<String> path = new LinkedList<String>();
            path.add(start);

            // Store the paths in a queue
            Queue<Word> queue = new LinkedList<Word>();
            queue.add(new Word(path, 1, start));

            // Remove the word so it is not reused
            dictionary.remove(start);

            // Iterate until queue is not empty (or end word is found)
            while (!queue.isEmpty() && !queue.peek().equals(end)) {
                Word word = queue.remove();

                // Conditional statements to get the last word or iterate
                if (end.equals(word.getLastWord())) {
                    return word;
                }

                Iterator<String> i = dictionary.iterator();

                while (i.hasNext()) {
                    String string = i.next();

                    if (differ(string, word.getLastWord())) {

                        List<String> list = new LinkedList<String>(word.getPath());
                        list.add(string);

                        // Store words in the queue if they differ by one
                        queue.add(new Word(list, word.getLength() + 1, string));

                        // Remove from the dictionary once the work is picked
                        i.remove();
                    }
                }
            }

            if (!queue.isEmpty()) {
                return queue.peek();
            }
        }
        return null;
    }

    /**
     * This method is used to compare two words to see how they differ
     * @param word1
     * @param word2
     * @return the count (the number of letters they differ by)
     */
    public static boolean differ(String word1, String word2) {

        // Conditional statements to compare the words
        if (word1.length() != word2.length()) {
            return false;
        }
        int count = 0;

        // For loop to increment count for output
        for (int i = 0; i < word1.length(); i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                count++;
            }
        }
        return (count == 1);
    }

    /**
     * Main method: all printouts and method calls happen here. It will
     * take two inputs; one for the starting word and one for the ending word.
     * It will also have the output coded in to give out the length of the
     * ladder as well as the ladder printed out. Lastly, it includes a lot
     * of exceptions and conditionals to ensure that the word ladders are valid.
     */
    public static void main(String[] args) {

        // Create Scanner object to receive the input of the user
        Scanner input = new Scanner(System.in);

        Set<String> dictionary = new HashSet<>();

        // Input for starting word
        System.out.println("Enter Beginning Word:");

        // Stores the starting word
        String start = input.nextLine();

        // Input for the ending word
        System.out.println("Enter End Word:");

        // Stores the ending word
        String end = input.nextLine();

        int length = start.length();

        // Error for when the words have differing lengths
        if (length != end.length()) {
            System.err.println("ERROR! Words not of the same length.");
            System.exit(1);
        }

        /*
        The following code will check the inputted words for their length, then
        search the text documents for the appropriate word. It includes conditionals
        to find the correct length. It also includes the exceptions to keep the program
        from stopping.
         */

        // Check for length 3
        if (start.length() == 3) {

            try (BufferedReader br = new BufferedReader(new FileReader("words.3"))) {
                String currentLine;

                while ((currentLine = br.readLine()) != null) {
                    dictionary.add(currentLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Check for length 4
        } else if (start.length() == 4) {

            try (BufferedReader br = new BufferedReader(new FileReader("words.4"))) {
                String currentLine;

                while ((currentLine = br.readLine()) != null) {
                    dictionary.add(currentLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Check for length 5
        } else if (start.length() == 5) {

            try (BufferedReader br = new BufferedReader(new FileReader("words.5"))) {
                String currentLine;

                while ((currentLine = br.readLine()) != null) {
                    dictionary.add(currentLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Check for length 6
        } else if (start.length() == 6) {

            try (BufferedReader br = new BufferedReader(new FileReader("words.6"))) {
                String currentLine;

                while ((currentLine = br.readLine()) != null) {
                    dictionary.add(currentLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Check for length 7
        } else if (start.length() == 7) {

            try (BufferedReader br = new BufferedReader(new FileReader("words.7"))) {
                String currentLine;

                while ((currentLine = br.readLine()) != null) {
                    dictionary.add(currentLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Check for length 8
        } else if (start.length() == 8) {

            try (BufferedReader br = new BufferedReader(new FileReader("words.8.8"))) {
                String currentLine;

                while ((currentLine = br.readLine()) != null) {
                    dictionary.add(currentLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Check for length 9
        } else if (start.length() == 9) {

            try (BufferedReader br = new BufferedReader(new FileReader("words.9.9"))) {
                String currentLine;

                while ((currentLine = br.readLine()) != null) {
                    dictionary.add(currentLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Searches the general word file will many words of differing lengths
        } else {

            try (BufferedReader br = new BufferedReader(new FileReader("words"))) {
                String currentLine;

                while ((currentLine = br.readLine()) != null) {
                    dictionary.add(currentLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Word result = getShortest(dictionary, start, end);
        int counter = 0;

        if (result != null) {

            // Printout for the length of the ladder
            System.out.println("Length is " + result.getLength());

            for (String str : result.getPath()) {
                counter++;
                System.out.print(str);

                if (counter == result.getLength())
                    break;
                System.out.print(" -> ");
            }
        }

        // Printout for when no ladder is found
        else {
            System.out.println("No word ladder found");

        }
    }
}