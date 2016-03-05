package logic;

public class ColourCard extends SimpleCard {
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
