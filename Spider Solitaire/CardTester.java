public class CardTester
{
    public static void main(String[] args) {
        Card card1 = new Card("A", 1);
        Card card2 = new Card("Q", 12);
        System.out.println(card1.getSymbol());
        System.out.println(card1.getValue());
        System.out.println(card1.isFaceUp());
        System.out.println(card1);
        System.out.println(card2.getSymbol());
        System.out.println(card2.getValue());
        System.out.println(card2.isFaceUp());
        System.out.println(card2);
        System.out.println(card1.equals(card2));
        System.out.println(card1.compareTo(card2));
        card1.setFaceUp(false);
        System.out.println(card1);
    }
}
