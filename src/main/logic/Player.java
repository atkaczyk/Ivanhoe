package logic;

import java.util.ArrayDeque;

public class Player {
	private String name = "";
	private ArrayDeque<Card> hand = new ArrayDeque<Card>();
	private ArrayDeque<Card> display = new ArrayDeque<Card>();
	
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

}
