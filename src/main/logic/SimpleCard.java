package logic;

public class SimpleCard extends Card {
	
	private int number;
	
	public SimpleCard(String cardName, int cardNumber) {
		name = cardName;
		number = cardNumber;
	}
	
	public int getNumber() {
		return number;
	}
	
}
