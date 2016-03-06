package logic;

/**
 * Represents all colour cards in the Ivanhoe card game, see {@link SimpleCard}
 * 
 * @author Sophia
 * 
 */
public class ColourCard extends SimpleCard {
	// The colour of the card
	private int colour;
	
	public ColourCard(String cardName, int cardNumber, int cardColour) {
		name = cardName;
		number = cardNumber;
		colour = cardColour;
	}

	public int getColour() {
		return colour;
	}
	
	public String toString() {
		return name + " " + number;
	}
	
}
