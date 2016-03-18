package logic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import utils.Config;

public class Player {
	private Boolean withdrawn = false;
	private String name = "";
	private ArrayDeque<Card> hand = new ArrayDeque<Card>();
	private ArrayDeque<Card> display = new ArrayDeque<Card>();
	private List<Integer> tokens = new ArrayList<Integer>();
	private List<Card> specialCards = new ArrayList<Card>();

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
				hand.remove(card);
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
		specialCards.add(c);
	}

	public Boolean hasSpecialCard(String name) {
		for (Card c : specialCards) {
			if (c.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	public List<Card> withdraw() {
		withdrawn = true;

		List<Card> result = new ArrayList<Card>();
		while (!display.isEmpty()) {
			result.add(display.pop());
		}
		return result;
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

	public String getDisplayAsStringNoDuplicates() {
		if (display.size() == 0) {
			return "";
		}
		String result = "";
		for (Card c : display) {
			if (!result.contains(c.getName())) {
				result += c.getName() + ",";
			}
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
		for (Iterator<Card> itr = displayCopy.descendingIterator(); itr
				.hasNext();) {
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

	/**
	 * Find the largest card value in the display
	 * 
	 * @return The largest value found
	 */
	public int getHighestDisplayValue() {
		int max = 0;
		for (Card c : display) {
			if (c instanceof SupporterCard) {
				if (((SupporterCard) c).getNumber() > max) {
					max = ((SupporterCard) c).getNumber();
				}
			} else if (c instanceof ColourCard) {
				if (((ColourCard) c).getNumber() > max) {
					max = ((ColourCard) c).getNumber();
				}
			}
		}

		return max;
	}

	public boolean hasSupporterCardInDisplay() {
		for (Card c : display) {
			if (c instanceof SupporterCard) {
				return true;
			}
		}
		return false;
	}

	public List<Card> removeAllSupporterCards() {
		List<Card> result = new ArrayList<Card>();

		ArrayDeque<Card> displayCopy = display.clone();
		for (Iterator<Card> itr = displayCopy.descendingIterator(); itr
				.hasNext();) {
			Card c = itr.next();
			if (display.size() > 1) {
				if (c instanceof SupporterCard) {
					result.add(c);
					display.removeLastOccurrence(c);
				}
			}
		}

		return result;
	}

	public boolean hasPurpleCardInDisplay() {
		for (Card c : display) {
			if (c instanceof ColourCard
					&& ((ColourCard) c).getColour() == Config.PURPLE) {
				return true;
			}
		}
		return false;
	}

	public List<Card> removeAllPurpleCards() {
		List<Card> result = new ArrayList<Card>();

		ArrayDeque<Card> displayCopy = display.clone();
		for (Iterator<Card> itr = displayCopy.descendingIterator(); itr
				.hasNext();) {
			Card c = itr.next();
			if (display.size() > 1) {
				if (c instanceof ColourCard
						&& ((ColourCard) c).getColour() == Config.PURPLE) {
					result.add(c);
					display.removeLastOccurrence(c);
				}
			}
		}

		return result;
	}

	public Card removeFromDisplay(String cardName) {
		for (Card c : display) {
			if (c.getName().equals(cardName)) {
				display.remove(c);
				return c;
			}
		}
		return null;
	}

	public Card getRandomCardFromHand() {
		return hand.pop();
	}

	public int getNumFaceupCards() {
		int total = display.size();

		if (specialCards.contains("Shield")) {
			total += 1;
		}
		if (specialCards.contains("Stunned")) {
			total += 1;
		}

		return total;
	}

	public String getFaceupCardsAsStringNoDuplicates() {
		String result = "";

		result += getDisplayAsStringNoDuplicates();

		for (Card c : specialCards) {
			result += "," + c.getName();
		}

		if (result.startsWith(",")) {
			result = result.substring(1, result.length());
		}

		return result;
	}

	public Card removeFaceupCard(String cardName) {
		Card result = null;
		for (Card c: display) {
			if (c.getName().equals(cardName)) {
				result = c;
				display.remove(c);
				break;
			}
		}
		
		for (Card c: specialCards) {
			if (c.getName().equals(cardName)) {
				result = c;
				specialCards.remove(c);
				break;
			}
		}
		
		return result;
	}

	public void addFaceupCard(Card card) {
		if (card.getName().equals("Shield") || card.getName().equals("Stunned")) {
			addSpecialCard(card);
			return;
		}
		
		display.addLast(card);
	}

	public boolean allowedToPlayAdapt() {
		// If they have 
		List<Integer> list = new ArrayList<Integer>();
		
		for (Card c: display) {
			if (!list.contains(((SimpleCard) c).getNumber())) {
				list.add(((SimpleCard) c).getNumber());
			}
			else {
				return true;
			}
		}
		
		return false;
	}

	public String getValuesAndCardsAsStringNoDuplicates() {		
		Map<Integer, Set<String>> displayCards = new HashMap<Integer, Set<String>>();
		
		for (Card c: display) {
			int cardValue = ((SimpleCard) c).getNumber();
			if (displayCards.keySet().contains(cardValue)) {
				displayCards.get(cardValue).add(c.getName());
			}
			else {
				Set<String> cardNames = new HashSet<String>();
				cardNames.add(c.getName());
				displayCards.put(cardValue, cardNames);
			}
		}
		
		String result = "";
		
		for (int num: displayCards.keySet()) {
			result += num + "@";
			for (String name: displayCards.get(num)) {
				result += name + ",";
			}
			if (result.endsWith(",")) {
				result = result.substring(0, result.length() - 1);
			}
			result += "+";
		}
		
		if (result.endsWith("+")) {
			result = result.substring(0, result.length() - 1);
		}
		
		return result;
	}

	public List<Card> keepOnlyCard(int value, String cardName) {
		List<Card> result = new ArrayList<Card>();
		
		// First remove any cards where the name does not match the one to keep
		for (Card c: display) {
			if (((SimpleCard) c).getNumber() == value) {
				if (!c.getName().equals(cardName)) {
					Card cardToRemove = removeFromDisplay(c.getName());
					result.add(cardToRemove);
					System.out.println("I REMOVED "+cardToRemove);
				}
			}
		}
		
		return result;
	}

	public List<Card> removeDuplicatesInDisplay() {
		List<Card> result = new ArrayList<Card>();
		
		Set<Card> noDuplicates = new HashSet<Card>();
		for (Card c: display) {
			if (noDuplicates.contains(c)) {
				System.out.println("I AM REMOVING DUPLICATE "+c);
				Card cardToRemove = removeFromDisplay(c.getName());
				result.add(cardToRemove);
			}
			noDuplicates.add(c);
		}
		
		
		
		return result;
	}
}
