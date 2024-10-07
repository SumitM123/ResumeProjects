import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import javax.swing.JFileChooser;
public class Board {   
     /**
     *  Sets up the Board and fills the stacks and draw pile from a Deck
     *  consisting of numDecks Decks.  Here are examples:
     *  
     *  # numDecks     #cards in overall Deck
     *      1          13 (all same suit)
     *      2          26 (all same suit)
     *      3          39 (all same suit)
     *      4          52 (all same suit)
     *      
     *  Once the overall Deck is built, it is shuffled and half the cards
     *  are placed as evenly as possible into the stacks.  The other half
     *  of the cards remain in the draw pile.
     */
    int theNumStacks;
    int theNumDecks;
    ArrayList<Deck> totalDecks; 
    static String[] allSymbols;
    static int[] allValues;
    ArrayList<Card> drawPile;
    Deck[] currentD; 
    ArrayList<Deck> loadToGame = new ArrayList<Deck>();;
    public Board(int numStacks, int numDecks) {
        theNumStacks = numStacks;
        theNumDecks = numDecks;
        totalDecks = new ArrayList<Deck>(); 
        String[] symbols2 = {"A","2","3","4","5","6","7","8","9","T","J","Q","K"};
        int[] values2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        allSymbols = symbols2;
        allValues = values2;
        for(int i = 0; i < numDecks; i++) {
            Deck currentDe = new Deck();
            for(int j = 0; j < symbols2.length; j++) {
                Card currentC = new Card(symbols2[j], values2[j]);
                currentDe.add(currentC);
            }
            currentDe.shuffle();
            totalDecks.add(currentDe);
        }

        ArrayList<Card> cardsInTotal = new ArrayList<Card>(); 

        for(int k = 0; k < totalDecks.size(); k++) {
            Deck currentDeck = totalDecks.get(k); 
            for(int l = 0; l < currentDeck.getSize(); l++) {
                cardsInTotal.add(currentDeck.getCard(l));  
            }
        }
        int totalCards = cardsInTotal.size(); 
        int cardsInTotalStacks = totalCards/2; 
        currentD = new Deck[theNumStacks];
        int n = 0; 
        for(int m = 0; m < cardsInTotalStacks; m++) {
            Card currentC = cardsInTotal.get(m);
            Deck currentDec = new Deck();
            //System.out.println(currentDec);
            if(currentD[n] != null) {
                Deck currently = currentD[n];
                //System.out.println(currently);
                //System.out.println(currently.getSize());
                for(int o = 0; o < currently.getSize(); o++) {
                    currentDec.add(currently.getCard(o));
                }
                currentDec.add(currentC);
                currentD[n] = currentDec;
            } else {
                currentDec.add(currentC);
                currentD[n] = currentDec;
            }
            //System.out.println(currentDec);
            //System.out.println(currentD[n]);
            if(n == theNumStacks - 1) {
                n = 0;
                n--;
            }
            n++;
        }
        int numOfCardsInDrawPile = totalCards - cardsInTotalStacks; 
        drawPile = new ArrayList<Card>();
        int p = 0;
        for(int o = cardsInTotalStacks; o < totalCards; o++) {
            drawPile.add(cardsInTotal.get(o));
            p++;
        }
    }

    public static int getElementInArr(String[] arr, String ele) { //P?
        int n = 0;
        for(int i = 0; i < arr.length; i++) {
            if(arr[i].equals(ele)) {
                n = i;
                break;
            }
        }
        return n;
    }

    public static String getSymbol(int z) { //P
        String yoSymbol = allSymbols[z];
        return yoSymbol;
    }

    public static int getValueForSymbol(String[] symbols, String str) {
        int n = getElementInArr(symbols, str);
        int j = allValues[n];
        return j;
    }

    /**
     *  Moves a run of cards from src to dest (if possible) and flips the
     *  next card if one is available.
     */
    public void makeMove(String symbol, int src, int dest) {
        boolean isRow = false;
        Deck getDeck = currentD[src];
        //System.out.println(getDeck);
        //System.out.println(symbol); 
        Deck fromSrcIfTrue = new Deck();
        int k;
        int l;
        int getBack;
        for(int i = getDeck.getSize() - 1; i >= 0; i--) { //
            Card check = getDeck.getCard(i); 
            //System.out.println(check); 
            String currSymbol = check.getSymbol();
            //System.out.println(currSymbol); 
            if(currSymbol.equals(symbol)) { 
                k = i; //NP
                l = getElementInArr(allSymbols, currSymbol); //NP
                //System.out.println(allSymbols[l]); //NP
                for(int j = k; j < getDeck.getSize(); j++) {
                    Card check2 = getDeck.getCard(j);
                    //System.out.println(check2);
                    String currSymbol2 = check2.getSymbol();
                    //System.out.println(currSymbol2);
                    if(check2.getSymbol().equals(allSymbols[l])) {
                        isRow = true;
                        fromSrcIfTrue.add(check2);
                        if(isRow) {
                            getDeck.removeCard(check2);
                            j--;
                        }
                        //i = 0;
                        //break;
                    } else {
                        isRow = false;
                        getBack = j;
                        System.out.println("Invalid: Not a run");
                        for(int y = 0; y < fromSrcIfTrue.getSize(); y++) {
                            Card moveBack = fromSrcIfTrue.getCard(y);
                            getDeck.add(getBack, moveBack);
                            getBack++;
                        }
                        Deck veryTemp = new Deck();
                        fromSrcIfTrue = veryTemp;
                        //System.out.println(fromSrcIfTrue);
                        break;
                    }
                    if(l != 0) {
                        l--;
                    }
                }
                break;
            }
            if(i == 0 && !(isRow)) { //and isRow false
                System.out.println("Need a valid card!");
                break;
            }
        }

        if(isRow) {
            Deck destDeck = currentD[dest];
            //System.out.println(destDeck);
            //int size = 0;
            if(destDeck.getSize() == 0) {
                for(int i = 0; i < fromSrcIfTrue.getSize(); i++) {
                    Card temp = fromSrcIfTrue.getCard(i);
                    destDeck.add(temp);
                }
            } else {
                Card checkToSrc = destDeck.getCard(destDeck.getSize() - 1);
                //System.out.println(checkToSrc);
                Card fromSrcCard = fromSrcIfTrue.getCard(0);
                //System.out.println(fromSrcCard);
                String srcCheckSymbol = checkToSrc.getSymbol();
                //System.out.println(srcCheckSymbol);
                String fromSrcSymbol = fromSrcCard.getSymbol();
                //System.out.println(fromSrcSymbol);
                int m = getElementInArr(allSymbols, srcCheckSymbol);
                int n = getElementInArr(allSymbols, fromSrcSymbol);
                if(m == (n + 1)) {
                    ArrayList<Card> fromSrcToDest = new ArrayList<Card>();
                    for(int i = 0; i < fromSrcIfTrue.getSize(); i++) {
                        Card toDeck = fromSrcIfTrue.getCard(i);
                        fromSrcToDest.add(toDeck);
                    }
                    for(int j = 0; j < fromSrcToDest.size(); j++) {
                        Card toDestDeck = fromSrcToDest.get(j);
                        destDeck.add(toDestDeck);
                    }
                } else {
                    for(int i = 0; i < fromSrcIfTrue.getSize(); i++) {
                        Card moveBack = fromSrcIfTrue.getCard(i);
                        getDeck.add(moveBack);
                    }
                    System.out.println("Not a valid destination!");
                }
            }
        }  
    }

    /** 
     *  Moves one card onto each stack, or as many as are available
     */
    public void drawCards() {
        int j = 0;
        boolean hasCards = false;
        for(int i = 0; i < currentD.length; i++) {
            if(currentD[i].getSize() == 0) {
                hasCards = false;
                System.out.println("Need at least one card in each stack to draw cards"); //each Stack
                break;
            } else {
                hasCards = true;
            }
        }
        //hasCards = true;
        if(hasCards) {
            for(int i = 0; i < theNumStacks; i++) {
                if(drawPile.size() == 0) {
                    System.out.println("No more cards in drawPile");
                    break;
                }
                Card drawMove = drawPile.get(j);
                drawMove.setFaceUp(true);
                currentD[i].add(drawMove);
                drawPile.remove(j);
            }
        }
    }

    /**
     *  Returns true if all stacks and the draw pile are all empty
     */ 
    public boolean isEmpty() {
        boolean isStacksEmpty = false;
        boolean isDrawPileEmpty = false;
        boolean isItEmpty = false;
        for(int i = 0; i < currentD.length; i++) {
            Deck getCurr = currentD[i];
            if(getCurr.getSize() == 0) {
                isStacksEmpty = true;
            } else {
                isStacksEmpty = false;
                break;
            }
        }
        if(drawPile.size() == 0) {
            isDrawPileEmpty = true;
        }
        if(isDrawPileEmpty && isStacksEmpty) {
            isItEmpty = true;
        }
        return isItEmpty;
    }

    /**
     *  If there is a run of A through K starting at the end of sourceStack
     *  then the run is removed from the game or placed into a completed
     *  stacks area.
     *  
     *  If there is not a run of A through K starting at the end of sourceStack
     *  then an invalid move message is displayed and the Board is not changed.
     */
    public void clear(int sourceStack) {
        Deck currSourceStack = currentD[sourceStack];
        boolean ifRightCard = false;
        boolean isCorrectSize = true;
        boolean isUp = false;
        int j = 0;
        int removing;
        boolean notEnoughCards = false;
        if(currSourceStack.getSize() != allSymbols.length) { 
            isCorrectSize = false;
            if(currSourceStack.getSize() < allSymbols.length) {
                System.out.println("Not enough cards in stack to clear");
                notEnoughCards = true;
            }
        } else {
            isCorrectSize = true;
        }
        if(!notEnoughCards) {
            for(int i = currSourceStack.getSize() - 1; i >= 0; i--) {
                Card check = new Card(allSymbols[j], allValues[j]);
                if(currSourceStack.getCard(i).equals(check) && ((currSourceStack.getCard(i).getFaceUp(currSourceStack.getCard(i))) == true)) {
                    ifRightCard = true;
                    if(isCorrectSize && currSourceStack.getIndex(currSourceStack.getCard(i)) == 0 && ifRightCard) {
                        if(ifRightCard) {
                            Deck temp = new Deck();
                            currSourceStack = temp;
                            currentD[sourceStack] = currSourceStack;
                            break;
                        }
                    } else if(!(isCorrectSize) && ifRightCard && currSourceStack.getCard(i).getSymbol() == allSymbols[allSymbols.length - 1]) {
                        removing = i;
                        for(int k = 0; k < allSymbols.length; k++) {
                            currSourceStack.removeCard(currSourceStack.getCard(removing));
                            //System.out.println(currSourceStack);
                        }
                        currentD[sourceStack] = currSourceStack;
                        break;
                    }
                } else {
                    ifRightCard = false; 
                    if(!(ifRightCard)) {
                        System.out.println("Invalid Move");
                        break;
                    } else if(!(currSourceStack.getCard(i).getFaceUp(currSourceStack.getCard(i)))) {
                        System.out.println("Can't clear cards that are face down");
                        break;
                    }
                }
                if(j != allSymbols.length - 1) {
                    j++;
                }
            }
        }
    }

    public void save() throws IOException {
        // Create a JFileChooser that points to the current directory
        JFileChooser chooser = new JFileChooser(".");
        // Tell the JFileChooser to show a Save dialog window
        chooser.showSaveDialog(null);
        // Ask the JFileChooser for the File the user typed in or selected
        File apple = chooser.getSelectedFile();
        // Create a FileWriter that can write to the selected File
        FileWriter banana = new FileWriter(apple);
        for(int i = 0; i < theNumStacks; i++) {
            Deck getCurrentDeck = currentD[i];
            banana.write(getCurrentDeck.getString() + "\n");
        }
        Deck cardsFromDrawPile = new Deck();
        for(Card in: drawPile) {
            //in.setFaceUp(true);
            cardsFromDrawPile.add(in);
        }
        banana.write(cardsFromDrawPile.getString());
        banana.close();
    }

    public void load() throws IOException{
        JFileChooser chooser = new JFileChooser(".");

        // Tell the JFileChooser to show a Save dialog window
        chooser.showOpenDialog(null);

        // Ask the JFileChooser for the File the user typed in or selected
        File apple = chooser.getSelectedFile();

        // Create a FileWriter that can write to the selected File

        Scanner in = new Scanner(apple);
        int i = 0; // 0
        int numberDecks = 1;
        //System.out.println("Stacks: ");
        Deck drawPileFromLoad;
        // IMPORTANT: Make CurrentD and Drawpile empty
        while(in.hasNext()) {
            if(i == theNumStacks) {
                //System.out.println("Drawpile: ");
                String curr = in.nextLine();
                drawPileFromLoad = new Deck(curr);
                //System.out.println(drawPileFromLoad);
                loadToGame.add(drawPileFromLoad);
            } else {
                String curr = in.nextLine();
                Deck movingToLoad = new Deck(curr);
                loadToGame.add(movingToLoad);
            }
            numberDecks++;
            i++;
        }

        for(int j = 0; j < loadToGame.size(); j++) {
            if(j == theNumStacks) {
                drawPile.clear();
                Deck lastStack = loadToGame.get(j);
                //ArrayList<Card> fromLastStack = new ArrayList<Card>();
                for(int k = 0; k < lastStack.getSize(); k++) {
                    Card temp = lastStack.getCard(k);
                    drawPile.add(temp);
                }
            } else {
                currentD[j] = loadToGame.get(j);
            }
        }
    }

    /**
     * Prints the board to the terminal window by displaying the stacks, draw
     * pile, and done stacks (if you chose to have them)
     */
    public void printBoard() {

        //if(!isStacksEmpty && !isDrawPileEmpty) {
        System.out.println("Stacks: ");
        //System.out.println(Arrays.toString(currentD));
        int j = 1;
        for(int i = 0; i < theNumStacks; i++) {
            Deck currD = currentD[i];
            //System.out.println(currD);
            //System.out.println(currD.getSize());
            Card[] currC = new Card[currD.getSize()];
            for(int k = 0; k < currD.getSize(); k++) {
                if(k == currD.getSize() - 1) {
                    Card currCa = currD.getCard(k);
                    currCa.setFaceUp(true);
                    currC[k] = currCa;
                } else {
                    currC[k] = currD.getCard(k);
                }
            }
            System.out.print(j + ": " + Arrays.toString(currC));
            System.out.println();
            j++;
        }
        System.out.println();
        System.out.println("Draw Pile:");
        System.out.println(drawPile);
    }
}