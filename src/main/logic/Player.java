package logic;

import java.util.ArrayDeque;

public class Player {
	private String name = "";
	private ArrayDeque<Card> hand = new ArrayDeque<Card>();
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void addCard(Card card) {
		hand.addLast(card);
	}

	public Boolean isEmptyHanded() {
		return hand.isEmpty();
	}

	public ArrayDeque<Card> getHand() {
		return hand;
	}

}
