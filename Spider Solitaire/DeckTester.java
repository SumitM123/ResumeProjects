import java.util.*;
public class DeckTester
{
    public static void main(String[] args) {
        /* *** TO BE IMPLEMENTED IN ACTIVITY 3 *** */
        /* IF GAME IS SLOW, SWITCH TO ARRAYS */
        /*ArrayList<String> symbols = new ArrayList<String>();
        symbols.add("A");
        symbols.add("2");
        symbols.add("3");
        symbols.add("4");
        symbols.add("5");
        symbols.add("6");
        symbols.add("7");
        symbols.add("8");
        symbols.add("9");
        symbols.add("10");
        symbols.add("J");
        symbols.add("Q");
        symbols.add("K");
        ArrayList<Integer> values = new ArrayList<Integer>();
        for(int i = 1; i <= 13; i++) {
            values.add(i);
        }
        Deck deck1 = new Deck();
        System.out.println(deck1);
        deck1.shuffle();*/
        Deck wow = new Deck();
        Card c = new Card("K", 13);
        Card d = new Card("Q", 12);
        Card e = new Card("J", 11);
        Card f = new Card("10", 10);
        Card g = new Card("Nine", 9);
        c.setFaceUp(false);
        d.setFaceUp(false);
        e.setFaceUp(false);
        f.setFaceUp(false);
        g.setFaceUp(true);
        wow.add(c);
        wow.add(d);
        wow.add(e);
        wow.add(f);
        wow.add(g);
        String testing = wow.getString();
        System.out.println(testing);
        Deck check = new Deck("[3 3 D, 9 9 D, 2 2 D, A 1 U]");
        System.out.println(check);
    }
}
