package logic;

import java.util.ArrayDeque;
import java.util.List;

import utils.Config;

public class Player {
	private Boolean active = true;
	private String name = "";
	private ArrayDeque<Card> hand = new ArrayDeque<Card>();
	private ArrayDeque<Card> display = new ArrayDeque<Card>();
	private int displayTotal = 0;
	
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

	public Boolean addCardToDisplay(Card card, int tournamentColour) {
		// If card is a supporter card, increase by number
		if (card instanceof SupporterCard) {
			// If the card is a maiden, we must check that there isn't already a maiden
			if (card.getName().equals("Maiden") && maidenInDisplay()) {
				return false;
			}
			else {
				if (tournamentColour != Config.GREEN) {
					displayTotal += ((SupporterCard) card).getNumber();
				}
				else {
					displayTotal += 1;
				}
			}
		}
		
		display.addLast(card);
		return true;
	}

	private boolean maidenInDisplay() {
		for (Card c: display) {
			if (c.getName().equals("Maiden")) {
				return true;
			}
		}
		return false;
	}

	public ArrayDeque<Card> getDisplayCards() {
		return display;
	}

	public Boolean getActive() {
		return active;
	}

	public void clearHand() {
		hand.clear();
	}

	public int getDisplayTotal() {
		return displayTotal;
	}

	public Boolean addToken(int colour) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Integer> getTokens() {
		// TODO Auto-generated method stub
		return null;
	}

}
