import java.io.File;
import java.util.Random;
import java.util.Scanner;
import java.io.IOException;
public class HangmanWords {
    
    public HangmanWords () {
    }

    public String getRandomWord() throws IOException {
        Scanner in = new Scanner(new File("Hangman Words.txt"));
        int i = 0;
        while(in.hasNextLine()) {
            String nextLine = in.nextLine();
            i++;
        }
        Scanner in2 = new Scanner(new File("Hangman Words.txt"));
        Random rand = new Random();
        int randomNum = rand.nextInt(i)+1;
        String word = "";
        for(int k = 1; k <= randomNum; k++) {
            String inTheLine = in2.nextLine();
            if(k == randomNum) {
                word = inTheLine;
                break;
            }
        }
        return word;
    }

    /*public static void main(String[] args) {
        try {
            HangmanWords word = new HangmanWords();
            System.out.println(word.getRandomWord());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
    }*/
}
