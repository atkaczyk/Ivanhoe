package logic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import utils.Config;

public class Player {
	private Boolean withdrawn = false;
	private String name = "";
	private ArrayDeque<Card> hand = new ArrayDeque<Card>();
	private ArrayDeque<Card> display = new ArrayDeque<Card>();
	private List<Integer> tokens = new ArrayList<Integer>();
	private String specialCard = "";

	public void setWithdrawn(Boolean status) {
		withdrawn = status;
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
			// If the card is a maiden, we must check that there isn't already a
			// maiden
			if (card.getName().equals("Maiden 6") && maidenInDisplay()) {
				return false;
			} else {
				if (tournamentColour != Config.GREEN) {
					hand.remove(card);
				} else {
					hand.remove(card);
				}
			}
		} else if (card instanceof ColourCard) {
			if (((ColourCard) card).getColour() != tournamentColour) {
				return false;
			}
			hand.remove(card);
		}

		display.addLast(card);
		return true;
	}

	private boolean maidenInDisplay() {
		for (Card c : display) {
			if (c.getName().equals("Maiden 6")) {
				return true;
			}
		}
		return false;
	}

	public ArrayDeque<Card> getDisplayCards() {
		return display;
	}

	public Boolean isWithdrawn() {
		return withdrawn;
	}

	public void clearHand() {
		hand.clear();
	}

	public int getDisplayTotal(int tournColour) {
		int total = 0;
		for (Card c : display) {
			if (c instanceof SupporterCard) {
				if (tournColour == Config.GREEN) {
					total += 1;
				} else {
					total += ((SupporterCard) c).getNumber();
				}
			} else if (c instanceof ColourCard) {
				if (tournColour == Config.GREEN) {
					total += 1;
				} else {
					total += ((ColourCard) c).getNumber();
				}
			}
		}
		return total;
	}

	public Boolean addToken(int colour) {
		if (tokens.contains(colour)) {
			return false;
		}
		tokens.add(colour);
		return true;
	}

	public List<Integer> getTokens() {
		return tokens;
	}

	public Boolean removeToken(int colour) {
		if (tokens.contains(colour)) {
			tokens.remove((Object) colour);
			return true;
		}
		return false;
	}

	public void addSpecialCard(Card c) {
		specialCard = c.getName();
	}

	public Boolean hasSpecialCard(String s) {
		return specialCard.contains(s);
	}

	public void withdraw() {
		withdrawn = true;
	}

	public String getHandAsString() {
		if (hand.size() == 0) {
			return "";
		}
		String result = "";
		for (Card c : hand) {
			result += c.getName() + ",";
		}
		result = result.substring(0, result.length() - 1);
		return result;
	}

	public String getDisplayAsString() {
		if (display.size() == 0) {
			return "";
		}
		String result = "";
		for (Card c : display) {
			result += c.getName() + ",";
		}
		result = result.substring(0, result.length() - 1);
		return result;
	}

	public void clearDisplay() {
		display.clear();
	}

	public Card getCardFromHand(String name) {
		for (Card c : hand) {
			if (c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}

	/**
	 * Check to see if the play has the required number of tokens (has won)
	 * 
	 * @param requiredTokens
	 *            The number of different tokens they need to win the game
	 * @return if they are a winner or not
	 */
	public Boolean isWinnerOfGame(int requiredTokens) {
		return requiredTokens == tokens.size();
	}

	/**
	 * Find the smallest card value in the display
	 * 
	 * @return The smallest value found
	 */
	public int getLowestDisplayValue() {
		int min = 1000;
		for (Card c : display) {
			if (c instanceof SupporterCard) {
				if (((SupporterCard) c).getNumber() < min) {
					min = ((SupporterCard) c).getNumber();
				}
			} else if (c instanceof ColourCard) {
				if (((ColourCard) c).getNumber() < min) {
					min = ((ColourCard) c).getNumber();
				}
			}
		}

		return min;
	}

	public List<Card> removeAllCardsWithValue(int value) {
		List<Card> result = new ArrayList<Card>();

		ArrayDeque<Card> displayCopy = display.clone();
		for (Iterator<Card> itr = displayCopy.descendingIterator();itr.hasNext();) {
			Card c = itr.next();
			if (display.size() > 1) {
				if (c instanceof SupporterCard) {
					if (((SupporterCard) c).getNumber() == value) {
						result.add(c);
						display.removeLastOccurrence(c);
					}
				} else if (c instanceof ColourCard) {
					if (((ColourCard) c).getNumber() == value) {
						result.add(c);
						display.removeLastOccurrence(c);
					}
				}
			}
		}
		return result;
	}

}
