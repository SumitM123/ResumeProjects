import gpdraw.*;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
public class HangmanWordsLines {
    HangmanWords word = new HangmanWords();
    String wordsForLines = word.getRandomWord().toLowerCase();
    //String wordsForLines = "declaration";
    char[] lettersToSplit = new char[wordsForLines.length()];
    char[] userAnswer = new char[wordsForLines.length()];
    static ArrayList<Character> numberOfWrongCharacters = new ArrayList<Character>();
    static ArrayList<String> numberOfWrongWords = new ArrayList<String>();
    HangingManDrawing draw = new HangingManDrawing();
    static int numTimesWrong = 0;
    static boolean[] drawingParts = new boolean[6];
    public HangmanWordsLines() throws IOException {
        for(int i = 0; i < wordsForLines.length(); i++) {
            lettersToSplit[i] = wordsForLines.charAt(i);
        }
        for(int j = 0; j < userAnswer.length; j++) {
            userAnswer[j] = '_';
        }
    }

    public void rightLettersGuessed(String str) {
        if(checkWrongCharactersGuessed(str)) {
            System.out.println("There is already a character in the wrongly guessed pile, guess another character");
        } else {
            boolean fullRightLetters = false;
            boolean first = false;
            for(int j = 0; j <= lettersToSplit.length - str.length(); j++) {
                if(str.charAt(0) == lettersToSplit[j]) {
                    int forFullWords = j;
                    int ifNotTrue = j;
                    for(int k = 0; k < str.length(); k++) {
                        if(lettersToSplit[forFullWords] == str.charAt(k)) {
                            forFullWords++;
                            if(k == str.length() - 1) {
                                fullRightLetters = true;
                                first = true;
                            }
                        } else {
                            fullRightLetters = false;
                            if(!first && !fullRightLetters) {
                                /*for(int m = 0; m < str.length(); m++) {
                                numberOfWrongCharacters.add(str.charAt(m));
                                }*/
                                for(int n = ifNotTrue; n < str.length(); n++) {
                                    userAnswer[n] = '_';
                                }
                                numberOfWrongWords.add(str);
                                numberOfTimesWrong();
                                drawTheHangingMan();
                                //draw.wrongLettersGuessed();
                            }
                            break;
                        }
                    }
                    //forFullWords = j;
                    if(fullRightLetters && first) {
                        int fromJ = j;
                        for(int l = 0; l < str.length(); l++) {
                            userAnswer[fromJ] = str.charAt(l);
                            fromJ++;
                        }
                    }
                    fullRightLetters = false;
                } else if(j == lettersToSplit.length - str.length() && !first && !fullRightLetters) {
                    numberOfWrongWords.add(str);
                    numberOfTimesWrong();
                    drawTheHangingMan();
                } else {
                    if(userAnswer[j] != '_') {
                        if(userAnswer[j] == ' ') {
                            userAnswer[j] = '_';
                        } else {
                            userAnswer[j] = userAnswer[j]; 
                        }
                    } else {
                        userAnswer[j] = '_';
                    }
                }
            }
        }
    }

    public void rightLettersGuessed(char c) {
        if(checkWrongCharactersGuessed(c)) {
            System.out.println("There is already a character in the wrongly guessed pile, guess another character");
        } else {
            boolean first = false;
            int temp = (int) c;
            if(temp >= 65 && temp <= 90) {
                temp = temp + 32;
            } else {
                temp = temp;
            }
            for(int i = 0; i < wordsForLines.length(); i++) {
                if(lettersToSplit[i] == temp) {//add character to user
                    first = true;
                    userAnswer[i] = c;
                } else {
                    if(i == wordsForLines.length() - 1 && !first) {//add _ to userAnswer
                        if(userAnswer[i] != '_') {
                            if(userAnswer[i] == ' ') {
                                userAnswer[i] = '_';
                            } else {
                                userAnswer[i] = userAnswer[i]; 
                            }
                        } else {
                            userAnswer[i] = '_';
                        }
                        numberOfWrongCharacters.add(c);
                        numberOfTimesWrong();
                        drawTheHangingMan();
                        //draw.wrongLettersGuessed(); 
                    } else {
                        if(userAnswer[i] != '_') {
                            if(userAnswer[i] == ' ') {
                                userAnswer[i] = '_';
                            } else {
                                userAnswer[i] = userAnswer[i]; 
                            }
                        } else {
                            userAnswer[i] = '_';
                        }
                    }
                }
            }
        }
    }

    public boolean didWin() {
        boolean ifWon = false;
        for(int i = 0; i < userAnswer.length; i++) {
            if(userAnswer[i] == lettersToSplit[i]) {
                ifWon = true;
            } else {
                ifWon = false;
                break;
            }
        }
        return ifWon;
    }

    public static void numberOfTimesWrong() {
        numTimesWrong++;
    }

    public static void drawTheHangingMan() {
        for(int i = 0; i < numTimesWrong; i++) {//change numTimesWrong to 0
            drawingParts[i] = true;
        }
        for(int j = numTimesWrong; j < drawingParts.length; j++) {
            drawingParts[j] = false;
        }
    }

    public static boolean checkWrongCharactersGuessed(String str) {
        boolean answer = false;
        str = str.toLowerCase();
        for(int i = 0; i < numberOfWrongWords.size(); i++) {
            if(numberOfWrongWords.get(i).equals(str)) {
                answer = true;
                break;
            }
        }
        return answer;
    }

    public static boolean checkWrongCharactersGuessed(char c) {
        boolean answer = false;
        int temp = (int) c;
        if(temp >= 65 && temp <= 90) {
            temp = temp + 32;
        } else {
            temp = temp;
        }
        for(int i = 0; i < numberOfWrongCharacters.size(); i++) {
            if(temp == numberOfWrongCharacters.get(i)) {
                answer = true;
                break;
            }
        }
        return answer;
    }

    public boolean gameOver() {
        boolean isGameOver = false;;
        for(int i = 0; i < drawingParts.length; i++) {
            if(drawingParts[i] == true) {
                isGameOver = true;
            } else {
                isGameOver = false;
                break;
            }
        }
        if(isGameOver) {
            System.out.println("The word was: " + wordsForLines);
        }
        return isGameOver;
    }

    public void printRightAndWrong() {
        draw.hangManPole();
        draw.drawFace(drawingParts[0]);
        draw.drawBody(drawingParts[1]);
        draw.drawLeftArm(drawingParts[2]);
        draw.drawRightArm(drawingParts[3]);
        draw.drawLeftLeg(drawingParts[4]);
        draw.drawRightLeg(drawingParts[5]);
        System.out.println("Current progress:");
        System.out.println(userAnswer);
        System.out.println("Wrongly guessed characters: ");
        System.out.println(numberOfWrongCharacters);
        System.out.println("Wrongly guessed words: ");
        System.out.println(numberOfWrongWords);
    }
    /*public static void main(String[] args) {
    try {
    HangmanWordsLines word = new HangmanWordsLines();
    } catch(Exception e) {
    System.out.println("Error: " + e.getMessage());
    }
    }*/
}
