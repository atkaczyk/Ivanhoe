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

	public void addCardToHand(Card card) {
		hand.addLast(card);
	}

	public Boolean isEmptyHanded() {
		return hand.isEmpty();
	}

	public ArrayDeque<Card> getHand() {
		return hand;
	}

	public void addCardToDisplay(Card card) {
		// TODO Auto-generated method stub
		
	}

	public ArrayDeque<Card> getDisplayCards() {
		// TODO Auto-generated method stub
		return null;
	}

}
