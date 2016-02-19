package logic;

public class SimpleCard extends Card {
	
	private int number;
	private int colour;
	
	public SimpleCard(String cardName, int cardNumber, int cardColour) {
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
	
}
