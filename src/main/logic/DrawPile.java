package logic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import utils.Config;

public class DrawPile {

	private ArrayDeque<Card> cards = new ArrayDeque<Card>();

	public DrawPile() {
		initSupporterCards();

		initColourCards();

		initActionCards();
		
		shuffle();
	}

	private void initActionCards() {
		cards.addFirst(new ActionCard("Unhorse"));
		cards.addFirst(new ActionCard("Change Weapon"));
		cards.addFirst(new ActionCard("Drop Weapon"));
		cards.addFirst(new ActionCard("Shield"));
		cards.addFirst(new ActionCard("Stunned"));
		cards.addFirst(new ActionCard("Ivanhoe"));
		cards.addFirst(new ActionCard("Break Lance"));
		for (int i = 1; i <= 3; i++) {
			cards.addFirst(new ActionCard("Riposte"));
		}
		cards.addFirst(new ActionCard("Dodge"));
		cards.addFirst(new ActionCard("Retreat"));
		cards.addFirst(new ActionCard("Knock Down"));
		cards.addFirst(new ActionCard("Knock Down"));
		cards.addFirst(new ActionCard("Outmaneuver"));
		cards.addFirst(new ActionCard("Charge"));
		cards.addFirst(new ActionCard("Countercharge"));
		cards.addFirst(new ActionCard("Disgrace"));
		cards.addFirst(new ActionCard("Adapt"));
		cards.addFirst(new ActionCard("Outwit"));
	}

	private void initColourCards() {
		/* COLOUR CARDS */
		// Add all the purple cards
		for (int i = 3; i <= 7; i++) {
			if (i == 3 || i == 4 || i == 5) {
				for (int j = 1; j <= 4; j++) {
					cards.addFirst(new ColourCard("Purple (Jousting) "+i, i,
							Config.PURPLE));
				}
			} else if (i == 7) {
				cards.addFirst(new ColourCard("Purple (Jousting) "+i, i,
						Config.PURPLE));
				cards.addFirst(new ColourCard("Purple (Jousting) "+i, i,
						Config.PURPLE));
			}
		}

		// Add all the red cards
		for (int i = 3; i <= 5; i++) {
			if (i == 3 || i == 4) {
				for (int j = 1; j <= 6; j++) {
					cards.addFirst(new ColourCard("Red (Sword) "+i, i, Config.RED));
				}
			} else if (i == 5) {
				cards.addFirst(new ColourCard("Red (Sword) "+i, i, Config.RED));
				cards.addFirst(new ColourCard("Red (Sword) "+i, i, Config.RED));
			}
		}

		// Add all the blue cards
		for (int i = 2; i <= 5; i++) {
			if (i == 2 || i == 3 || i == 4) {
				for (int j = 1; j <= 4; j++) {
					cards.addFirst(new ColourCard("Blue (Axe) "+i, i, Config.BLUE));
				}
			} else if (i == 5) {
				cards.addFirst(new ColourCard("Blue (Axe) "+i, i, Config.BLUE));
				cards.addFirst(new ColourCard("Blue (Axe) "+i, i, Config.BLUE));
			}
		}

		// Add all the yellow cards
		for (int i = 2; i <= 4; i++) {
			if (i == 2) {
				for (int j = 1; j <= 4; j++) {
					cards.addFirst(new ColourCard("Yellow (Morningstar) "+i, i,
							Config.YELLOW));
				}
			} else if (i == 3) {
				for (int j = 1; j <= 8; j++) {
					cards.addFirst(new ColourCard("Yellow (Morningstar) "+i, i,
							Config.YELLOW));
				}
			} else if (i == 4) {
				cards.addFirst(new ColourCard("Yellow (Morningstar) "+i, i,
						Config.YELLOW));
				cards.addFirst(new ColourCard("Yellow (Morningstar) "+i, i,
						Config.YELLOW));
			}
		}

		// Add all the green cards
		for (int i = 1; i <= 14; i++) {
			cards.addFirst(new ColourCard("Green (No Weapon) 1", 1, Config.GREEN));
		}
	}

	private void initSupporterCards() {
		/* SUPPORTER CARDS */
		// Add all the squire cards
		for (int i = 2; i <= 3; i++) {
			for (int j = 1; j <= 8; j++) {
				cards.addFirst(new SupporterCard("Squire "+i, i));
			}
		}

		// Add the maiden cards
		for (int i = 1; i <= 4; i++) {
			cards.addFirst(new SupporterCard("Maiden 6", 6));
		}
	}

	// Return the card on the top of the deck
	public Card getCard() {
		return cards.pop();
	}
	
	// Shuffle all the cards currently in the discard pile
	private void shuffle() {
		List<Card> tempList = new ArrayList<Card>();
		
		while (!cards.isEmpty()) {
			tempList.add(cards.pop());
		}
		
		// Shuffle the list
		Collections.shuffle(tempList);
		
		// Copy back into the deque
		for (Card c: tempList) {
			cards.push(c);
		}
	}

	public int getNumCards() {
		return cards.size();
	}

	public void addCard(Card card) {
		cards.add(card);
	}

	public void clearCards() {
		cards.clear();
	}

}
