package logic;

public class SupporterCard extends SimpleCard {
	
	public SupporterCard(String cardName, int cardNumber) {
		name = cardName;
		number = cardNumber;
	}
	
	public String toString() {
		return name + " " + number;
	}
	
}
