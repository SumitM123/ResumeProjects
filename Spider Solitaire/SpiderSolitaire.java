/* Reflection Activity 6
 * Sumit Mantri; seventh period APCS; December 12, 2020 
 * Activity 6 took me around an hour to finish.
 * I would say activity 6 wasn't too hard to do. It was essentially just using try-catch inside the if-else-if statements of the commands to catch the input
 * errors of the user, print out the error, and then continue on with the game. The only hard part was figuring out where to use the try-catch statements. At
 * first, I was using these statements inside the Board class on the methods that I've worked on. Then, I realized that the errors that are going to occur are
 * user input errors where the type in an invalid command, or an invalid card, etc. After realizing this, I used the try-catch statements in Spider Solitaire
 * where user input is present. 
 */
/* Reflection Activity 7
 * Sumit Mantri; seventh period APCS; December 18, 2020 
 * Activity 7 took me around two and half hours to finish.
 * This activity was pretty difficult, especially the loading part of the activity. When I was converting the decks to String, it wasn't all too hard, but I
 * made it quite sofisticated has all the decks are in one line, and the drawpile in another, so I wouldn't know where each deck starts and ends. I didn't
 * realize it was complicated, until trying to do the loading part of the activity. After thinking about it for a while to make it simplier for me to load 
 * the game, I made it so that each deck takes one line with the drawpile being the last deck of them all. After doing so, everything came quite easy for me 
 * has I just need to read each line for a new deck. 
 */
import java.util.*;
import java.io.IOException;
public class SpiderSolitaire
{
    /** Number of stacks on the board **/
    public final int NUM_STACKS = 5;

    /** Number of complete decks used in this game.  A 1-suit deck, which is the
     *  default for this lab, consists of 13 cards (Ace through King).
     */
    public final int NUM_DECKS = 3;

    /** A Board contains stacks and a draw pile **/
    private Board board;

    /** Used for keyboard input **/
    private Scanner input;
    public SpiderSolitaire() {
        // Start a new game with NUM_STACKS stacks and NUM_DECKS of cards
        board = new Board(NUM_STACKS, NUM_DECKS);
        input = new Scanner(System.in);
    }

    /** Main game loop that plays games until user wins or quits **/
    public void play() {
        //try{
        board.printBoard();
        boolean gameOver = false;
        while(!gameOver) {
            System.out.println("\nCommands:");
            System.out.println("   move [card] [source_stack] [destination_stack]");
            System.out.println("   draw");
            System.out.println("   clear [source_stack]");
            System.out.println("   restart");
            System.out.println("   quit");
            System.out.println("   save");
            System.out.println("   load");
            System.out.print(">");
            String command = input.next();

            if (command.equals("move")) {
                /* *** TO BE MODIFIED IN ACTIVITY 5 *** */
                try {
                    String symbol = input.next();
                    int sourceStack = input.nextInt();
                    int destinationStack = input.nextInt();
                    board.makeMove(symbol, sourceStack - 1, destinationStack - 1);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            else if (command.equals("draw")) {
                board.drawCards();
            }
            else if (command.equals("clear")) {
                /* *** TO BE MODIFIED IN ACTIVITY 5 *** */
                try {
                    int sourceStack = input.nextInt();
                    board.clear(sourceStack - 1);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            else if (command.equals("restart")) {
                board = new Board(NUM_STACKS, NUM_DECKS);
            }
            else if (command.equals("quit")) {
                System.out.println("Goodbye!");
                System.exit(0);
            } else if(command.equals("save")) {
                try {
                    board.save();
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    return;
                }
            } else if(command.equals("load")) {
                try {
                    board.load();
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    return;
                }
            }
            else {
                System.out.println("Invalid command.");
            }
            board.printBoard();
            // If all stacks and the draw pile are clear, you win!
            if (board.isEmpty()) {
                gameOver = true;
            }
            if(gameOver) {
                System.out.println("Congratulations!  You win!");
                break;
            }
        }
        //System.out.println("Congratulations!  You win!");
        /*} catch(InputMismatchException e) {
        System.out.println("Error: " + e.getMessage());
        return;
        } catch (ArrayIndexOutOfBoundsException e) {
        System.out.println("Error: " + e.getMessage());
        return;
        } catch (NullPointerException e) {
        System.out.println("Error: " + e.getMessage());
        return;
        }*/
    }
}
