import java.io.IOException;
import java.util.Scanner;
public class Game {
    private Scanner checkUser;
    private HangmanWordsLines hangMan;
    //private HangingManDrawing draw;
    public Game() throws IOException{
        checkUser = new Scanner(System.in);
        hangMan = new HangmanWordsLines();
        //draw = new HangingManDrawing();
    }

    public void play() {
        hangMan.printRightAndWrong();
        boolean gameOver = false;
        while(!gameOver) {
            System.out.println("\nCommands:");
            System.out.println("   character (enter character)");
            System.out.println("   word (enter word)");
            System.out.println("   quit");
            String word = checkUser.next();
            if(word.equals("character")) {
                try {
                    String fullForm = checkUser.next();
                    if(fullForm.length() > 1) {
                        System.out.println("Too many characters!");
                    } else {
                        char character = fullForm.charAt(0);
                        hangMan.rightLettersGuessed(character);
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    return;
                }
            } else if(word.equals("word")) {
                try {
                    String input = checkUser.next();
                    hangMan.rightLettersGuessed(input);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    return;
                }
            } else if(word.equals("quit")) {
                System.out.println("Goodbye!");
                checkUser.close();
                break;
            } else {
                System.out.println("Invalid Command");
            }
            hangMan.printRightAndWrong();
            if(hangMan.didWin()) {
                System.out.println("Congradulations, you've won!");
                break;
            }
            if(hangMan.gameOver()) {
                gameOver = true;
                break;
            }
        }
        if(gameOver) {
            System.out.println("Oh no, you lost the game, please try again!");
        }
    }
    public static void main(String[] args) {
        try {
            Game hangPlay = new Game();
            hangPlay.play();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}