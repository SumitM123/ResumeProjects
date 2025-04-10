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
