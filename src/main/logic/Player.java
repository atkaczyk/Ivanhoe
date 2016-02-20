package logic;

import java.util.ArrayDeque;

public class Player {
	private Boolean active = true;
	private String name = "";
	private ArrayDeque<Card> hand = new ArrayDeque<Card>();
	private ArrayDeque<Card> display = new ArrayDeque<Card>();
	
	public void setActive(Boolean status) {
		active = status;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void addCardToHand(Card card) {
		hand.addLast(card);
	}

	public ArrayDeque<Card> getHandCards() {
		return hand;
	}

	public void addCardToDisplay(Card card) {
		display.addLast(card);
	}

	public ArrayDeque<Card> getDisplayCards() {
		return display;
	}

	public Object getActive() {
		// TODO Auto-generated method stub
		return null;
	}

}
