package logic;

import java.util.ArrayDeque;

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

	public void addCardToDisplay(Card card, int tournamentColour) {
		// If card is a supporter card, increase by number
		if (card instanceof SupporterCard) {
			if (tournamentColour != Config.GREEN) {
				displayTotal += ((SupporterCard) card).getNumber();
			}
			else {
				displayTotal += 1;
			}
		}
		
		display.addLast(card);
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

}
