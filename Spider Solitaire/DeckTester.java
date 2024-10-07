public class DeckTester
{
    public static void main(String[] args) {
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
