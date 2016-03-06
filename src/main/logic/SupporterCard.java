package logic;

/**
 * Represents all supporter cards in the Ivanhoe card game, see
 * {@link SimpleCard}
 * 
 * @author Sophia
 * 
 */
public class SupporterCard extends SimpleCard {

	public SupporterCard(String cardName, int cardNumber) {
		name = cardName;
		number = cardNumber;
	}

	public String toString() {
		return name + " " + number;
	}

}
