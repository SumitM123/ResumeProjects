/* Sumit Mantri; seventh period APCS; December 2, 2020 
 * Activities 1-3 took me around 2 hours to finish.
 * This was lab wasn't really challenging, and was quite a breeze, especially the Card class, although I had one realization when I was doing activity
 * 4 and 5. Before, I had an arraylist of symbols and values in the Deck class, and I came to realize that this deck class is just an arraylist of cards. 
 * The Board class was where we have an array or Arraylist of values/symbols, and after noticing this, I made immediate changes to both of the classes. The
 * most challenging part of the Activities, in my opinion, was the shuffle method has I had some trouble debugging in trying to get the correct random 
 * numbers, has in numbers that are not out of bounds, etc. Other than the two conflicts I talked about, I would say this was a pretty easy lab and enjoyable
 * lab. 
 */
import java.util.*;
/**
 * This class is going to have an arraylist of <Code>Card</Code>'s.
 */
public class Deck
{
    /* Javadocs Outline
     * Write class summary at the very top of the class
     * 
     * First line is a description of what the method does
     * Then describe every single parameter: @param nameOfParameter and then description
     * When returning, write @return, and write description of it returns
     */
    /* *** TO BE IMPLEMENTED IN ACTIVITY 3 *** */
    /**
     * It stores the symbols being inputed into constructor.
     */

    /**
     * It stores the values being inputed into constructor.
     */

    /**
     * It stores the <code>Card</code>'s for each symbol and value. 
     */
    ArrayList<Card> deck;
    /**
     * The method is just going to take an Arraylist of symbols and values, and create an ArrayList of <code>Card</code> with one symbol and value being 
     * passed in. 
     * @param symbols Going to take in an ArrayList of symbols of all the cards. 
     * @param values Going to take in an ArrayList of values of all the cards. 
     */
    public Deck() {
        deck = new ArrayList<Card>();
    }

    public Deck(String str) {
        Scanner in = new Scanner(str);
        deck = new ArrayList<Card>();
        String cardSymbol = "";
        boolean hasSymbol = false;
        String cardValue = "";
        int bestCardValue = 0;
        boolean hasValue = false;
        boolean isUp = false;
        boolean hasState = false;
        int correctCardValue = 0;
        for(int i = 0; i < str.length(); i++) {
            //String c = str.charAt(i);
            //System.out.println(str.charAt(i));
            if((!(hasSymbol) && !(hasValue) && !(hasState)) && (((str.charAt(i) >= 48 && str.charAt(i) < 58) || (str.charAt(i) >= 65 && str.charAt(i) <= 87) || 
                    str.charAt(i) >= 89 && str.charAt(i) <= 90) || (str.charAt(i) >= 97 && str.charAt(i) <= 122))) {
                cardSymbol += str.charAt(i);
                if(i < str.length() - 1 && str.charAt(i + 1) != ' ') {
                    hasSymbol = false;
                } else {
                    hasSymbol = true;
                    if(str.charAt(i) >= 48 && str.charAt(i) < 58) {
                        i++;
                    }
                }
            }
            if(hasSymbol && !(hasValue) && !(hasState) && (str.charAt(i) >= 48 && str.charAt(i) < 58)) {
                cardValue += str.charAt(i);
                if(i < str.length() - 1 && str.charAt(i + 1) != ' ') {
                    hasValue = false;
                } else {
                    hasValue = true;
                    bestCardValue = Integer.parseInt(cardValue);
                }
            }
            if(hasSymbol && hasValue && !(hasState)) {
                if(str.charAt(i) == 68) {
                    isUp = false;
                    hasState = true;
                } else if(str.charAt(i) == 85){
                    isUp = true;
                    hasState = true;
                }
            }
            if(hasSymbol && hasValue && hasState) {
                Card c = new Card(cardSymbol, bestCardValue);
                if(isUp) {
                    c.setFaceUp(true);
                } else {
                    c.setFaceUp(false);
                }
                deck.add(c);
                cardSymbol = "";
                hasSymbol = false;
                cardValue = "";
                bestCardValue = 0;
                hasValue = false;
                isUp = false;
                hasState = false;
                correctCardValue = 0;
            }
        }
    }

    /**
     * This method is going to <code>shuffle</code> the ArrayList of cards. 
     */
    public void shuffle() {
        Random randNum = new Random();
        for(int i = 0; i < deck.size(); i++) {
            int range = deck.size() - i;
            int rand = (int) (Math.random() * range) + i;
            Card storage = deck.get(i);
            deck.set(i, deck.get(rand));
            deck.set(rand, storage);
        }
        //System.out.println(deck);
    }
    //drawFirst returning the top card and removes the top card
    public Card drawFirst() {
        Card topCard = deck.get(0);
        deck.remove(0);
        return topCard;
    }
    //size method that returns the number of cards in deck
    public int getSize() {
        return deck.size();
    }
    //public void add() adds a card to the deck
    public void addCard(String symbol, int value) {
        deck.add(new Card(symbol, value));
    }
    //public card getCard(int index) //Returns the card at index but doesn't remove
    public Card getCard(int index) {
        return deck.get(index);
    }

    public void add(Card c) {
        deck.add(c);
    }

    public void removeCard(Card c) {
        deck.remove(c);
    }

    public int getIndex(Card c) {
        return deck.indexOf(c);
    }

    public Card[] makeDeckToArr(Deck c) {
        Card[] answer = new Card[c.getSize() - 1];
        for(int i = 0; i < c.getSize() - 1; i++) {
            Card temp = c.getCard(i);
            answer[i] = temp;
        }
        return answer;
    }

    public int indexOf(Card woo) {
        return deck.indexOf(woo);
    }

    public String getString() {
        String variationDeck = "[";
        if(deck.size() == 0) {
            variationDeck += "]";
        }
        for(int i = 0; i < deck.size(); i++) {
            Card c = deck.get(i);
            String getFaceUp = "";
            if(c.isFaceUp()) {
                getFaceUp = "U";
            } else {
                getFaceUp = "D";
            }
            if(i != deck.size() - 1){
                variationDeck += c.getSymbol() + " " + c.getValue() + " " + getFaceUp + ", ";
            } else {
                variationDeck += c.getSymbol() + " " + c.getValue() + " " + getFaceUp + "]";
            }
        }
        //System.out.println();
        return variationDeck;
    }

    /*public Card getCardFromSymbolsAndValues(String symbol, int value) {
    Card smth = deck.get(symbol, value);
    //deck.get();
    }*/
    public void add(int n, Card c) {
        deck.add(n, c);
    }

    /*public Card getIndexOfCard(String symbol, int value) {

    }*/
    @Override
    /**
     * This method is going return all the symbols and values in the object of the class. 
     * @return A row of all the Symbols and a row of all the values in the object. 
     */
    public String toString() {
        String allSymbols = "";
        String allValues = "";
        for(Card i: deck) {
            if(i.isFaceUp()) {
                String tempSymbol = i.getSymbol();
                int tempValues = i.getValue();
                allSymbols += tempSymbol + " ";
                allValues += tempValues + " ";
            } else {
                allSymbols += "X ";
                allValues += "X ";
            }
        }
        return allSymbols /*+ "\n" + "Values: " + allValues*/;
    }
}