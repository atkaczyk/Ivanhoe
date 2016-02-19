package logic;

public class SupporterCard extends Card {
	
	private int number;
	
	public SupporterCard(String cardName, int cardNumber) {
		name = cardName;
		number = cardNumber;
	}

	public int getNumber() {
		return number;
	}
	
}
