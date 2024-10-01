/**
 * Card.java
 *
 * <code>Card</code> represents a basic playing card.
 */
public class Card implements Comparable<Card>
{
    /** String value that holds the symbol of the card.
    Examples: "A", "Ace", "10", "Ten", "Wild", "Pikachu"
     */
    private String symbol;

    /** int value that holds the value this card is worth */
    private int value;

    /** boolean value that determines whether this card is face up or down */
    private boolean isFaceUp;

    /**
     * Creates a new <code>Card</code> instance.
     *
     * @param symbol  a <code>String</code> value representing the symbol of the card
     * @param value an <code>int</code> value containing the point value of the card
     */    
    public Card(String symbol, int value) {
        /* *** TO BE IMPLEMENTED IN ACTIVITY 2 *** */
        this.symbol = symbol;
        this.value = value;
        //isFaceUp = true;
    }
    /**
     * Creates a new <code>Card</code> instance.
     * 
     * @param symbol a <code>String</code> value representing the symbol of the card
     */
    public Card(String symbol) {
        this.symbol = symbol;
    }
    /**
     * Getter method to access this <code>Card</code>'s symbol.
     * 
     * @return this <code>Card</code>'s symbol.
     */
    public String getSymbol() {
        /* *** TO BE IMPLEMENTED IN ACTIVITY 2 *** */
        return this.symbol;
    }

    /**
     * Getter method to access this <code>Card</code>'s value.
     * 
     * @return this <code>Card</code>'s value.
     */
    public int getValue() {
        /* *** TO BE IMPLEMENTED IN ACTIVITY 2 *** */
        return this.value;
    }

    /**
     * Boolean method to show if the <code>Card</code> is facing up or not.
     * 
     * @return if this <code>Card</code> is facing up
     */
    public boolean isFaceUp() {
        return isFaceUp;
    }

    /**
     * Setter method to set the state of the <code>Card</code> facing up or down.
     * 
     * @param state is a boolean value that sets the card facing up to true or false.
     */
    public void setFaceUp(boolean state) {
        isFaceUp = state;
    }

    /**
     * Returns whether or not this <code>Card</code> is equal to another
     *  
     *  @return whether or not this Card is equal to other.
     *  @param <code>Card</code> is to check if the value and symbol of the card are equal
     */
    public boolean equals(Card other) {
        /* *** TO BE IMPLEMENTED IN ACTIVITY 2 *** */
        return this.value == other.getValue() && this.symbol.equals(other.getSymbol());
    }
    /**
     * Getter method to get the if the <code>Card</code> is facing up or not
     * 
     * @param Takes a card to see if the card is facing up or not
     */
    public boolean getFaceUp(Card c) {
        return isFaceUp;
    }
    /**
     * Method to get the difference of value between the value for the symbol and the <code>Card</code>'s value being passed in
     * 
     * @param Takes a card to compare values
     */
    public int compareTo(Card o) {
        return this.value - o.getValue();
    }
    /**
     * Returns this card as a String.  If the card is face down, "X"
     * is returned.  Otherwise the symbol of the card is returned.
     *
     * @return a <code>String</code> containing the symbol and point
     *         value of the card.
     */
    @Override
    public String toString() {
        /* *** TO BE IMPLEMENTED IN ACTIVITY 2 *** */
        if(!(isFaceUp)) {
            return "X";
        } else {
            return /*"Symbol: " + */this.symbol /*+ " Value: " + this.value*/;
        }
    }
}
