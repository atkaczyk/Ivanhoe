package logic;

import java.util.ArrayDeque;

public class Deck {
	
	ArrayDeque<Card> cards = new ArrayDeque<Card>();
	
	public Deck() {
		// Add all the squire cards
		for (int i = 2; i <= 3; i++) {
			for (int j = 1; j<= 8; j++) {
				cards.addFirst(new SupporterCard("Squire", i));
			}
		}
		
		// Add the maiden cards
		for (int i = 1; i <=4; i++) {
			cards.addFirst(new SupporterCard("Maiden", 6));
		}
		
		// Add all the purple cards
	}
	
	// Return the card on the top of the deck
	public Card getCard() {
		return cards.getFirst();
	}
	
	public ArrayDeque<Card> getAllCards() {
		return cards;
	}

}
