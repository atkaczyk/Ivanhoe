package logic;

public class ColourCard extends Card {
	
	private int number;
	private int colour;
	
	public ColourCard(String cardName, int cardNumber, int cardColour) {
		name = cardName;
		number = cardNumber;
		colour = cardColour;
	}

	public int getNumber() {
		return number;
	}

	public int getColour() {
		return colour;
	}
	
	public String toString() {
		return name + " " + number;
	}
	
}
