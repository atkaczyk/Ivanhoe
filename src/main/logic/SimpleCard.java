package logic;

public class SimpleCard extends Card {
	
	private int number;
	
	public SimpleCard(String cardName, int cardNumber) {
		name = cardName;
		number = cardNumber;
	}
	
	public SimpleCard(String cardName, int cardNumber, int cardColour) {
		// TODO Auto-generated constructor stub
	}

	public int getNumber() {
		return number;
	}

	public Object getColour() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
