package logic;

/**
 * Represents all the actions cards in the Ivanhoe card game, see {@link Card}
 * 
 * @author Sophia
 * 
 */
public class ActionCard extends Card {

	public ActionCard(String cardName) {
		name = cardName;
	}

	public String toString() {
		return name;
	}

}
