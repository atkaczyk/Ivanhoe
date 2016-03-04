package logic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import utils.Config;
import utils.PrintHelper;

public class Game {
	private int numOfPlayers;
	private Player[] players;
	private int tournamentNumber = 0;
	private DrawPile drawPile = new DrawPile();
	private List<Integer> tokens = new ArrayList<Integer>();
	private int tournamentColour = -1;
	private ArrayDeque<Card> discardPile;

	// To keep track of whose turn it is
	private Player currentPlayer;

	// Needed to start the game
	private int playersRegistered = 0;
	private int tokensPicked = 0;
	private int[] startingGameTokens = Config.ALL_TOKEN_COLOURS;

	public void setNumPlayers(int numOfPlayers) {
		this.numOfPlayers = numOfPlayers;
		players = new Player[numOfPlayers];
	}

	public int getNumPlayers() {
		return numOfPlayers;
	}

	public void addPlayer(String playerName, int tokenColour) {
		for (int i = 0; i < numOfPlayers; i++) {
			if (players[i] == null) {
				players[i] = new Player();
				players[i].setName(playerName);

				if (tokenColour == Config.PURPLE) {
					currentPlayer = players[i];
				}
				break;
			}
		}

		playersRegistered++;
	}

	public Player[] getPlayers() {
		return players;
	}

	public int getNextToken() {
		tokensPicked++;
		return startingGameTokens[tokensPicked - 1];
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public int getPlayersRegistered() {
		return playersRegistered;
	}

	public Boolean isReadyToStart() {
		return numOfPlayers == playersRegistered;
	}

	public int getTournamentNumber() {
		return tournamentNumber;
	}

	public void startGame() {
		discardPile = new ArrayDeque<Card>();
		// Distribute 8 cards to each player
		for (int i = 0; i < numOfPlayers; i++) {
			for (int j = 1; j <= 8; j++) {
				players[i].addCardToHand(drawPile.getCard());
			}
			// System.out.println("\n\nPLAYER " + i);
			// PrintHelper.printCards(players[i].getHandCards());
		}

		// Initialize the 25 tokens (5 of each colour)
		for (int colour : Config.ALL_TOKEN_COLOURS) {
			for (int i = 1; i <= 5; i++) {
				tokens.add(colour);
			}
		}

		// Figure out which player is first
		goToNextPlayer();
		startTournament();
	}

	// Change the current player to be the next player
	private void goToNextPlayer() {
		// If the current player is the last one in the deque set to first
		// player
		if (players[numOfPlayers - 1].getName().equals(currentPlayer.getName())) {
			currentPlayer = players[0];
			return;
		}

		// Otherwise, get position of current player
		int i;
		for (i = 0; i < numOfPlayers; i++) {
			if (players[i].getName().equals(currentPlayer.getName())) {
				currentPlayer = players[i + 1];
				break;
			}
		}

	}

	public List<Integer> getTokens() {
		return tokens;
	}

	public int getTournamentColour() {
		return tournamentColour;
	}

	/** Set the colour of the current tournament **/
	public Boolean setTournamentColour(int colour) {
		int previousColour = tournamentColour;
		if (previousColour == Config.PURPLE && colour == Config.PURPLE) {
			return false;
		}

		Boolean playableCardFound = false;

		// Make sure that the current player that is choosing the token
		// has either that colour in their hand, or a supporter card
		for (Card c : currentPlayer.getHandCards()) {
			if (c instanceof ColourCard
					&& ((ColourCard) c).getColour() == colour) {
				playableCardFound = true;
			} else if (c instanceof SupporterCard) {
				playableCardFound = true;
			}
		}

		if (playableCardFound) {
			tournamentColour = colour;
		}
		return playableCardFound;
	}

	public int getTokensPicked() {
		return tokensPicked;
	}

	public int getCurrentPlayerNumber() {
		int i;
		for (i = 0; i < numOfPlayers; i++) {
			if (players[i].getName().equals(currentPlayer.getName())) {
				break;
			}
		}
		return i;
	}

	public Player getPlayer(int playerNumber) {
		int i;
		for (i = 0; i < numOfPlayers; i++) {
			if (i == playerNumber) {
				break;
			}
		}
		return players[i];
	}

	public void startTournament() {
		// Move all cards from player display to to the discard pile
		for (int i = 0; i < numOfPlayers; i++) {
			ArrayDeque<Card> display = players[i].getDisplayCards();
			for (Card c : display) {
				discardPile.add(display.pop());
			}

			// Set all players as active for this tournament
			players[i].setWithdrawn(false);
		}

		tournamentNumber++;
	}

	/**
	 * Gets the first card from the draw pile and moves it into the players hand
	 **/
	public void drawCard(int playerNum) {
		Player player = players[playerNum];
		Card c = drawPile.getCard();
		player.addCardToHand(c);

		// Check to see is the draw pile is empty
		if (drawPile.getNumCards() == 0) {
			// Shuffle the discard pile and add it to the drawPile
			List<Card> tempList = new ArrayList<Card>();

			while (!discardPile.isEmpty()) {
				tempList.add(discardPile.pop());
			}

			// Shuffle the list
			Collections.shuffle(tempList);

			// Copy back into the deque
			for (Card card : tempList) {
				drawPile.addCard(card);
			}
		}
	}

	public DrawPile getDrawPile() {
		return drawPile;
	}
	
	public String playCard(int playerNum, String name) {
		System.out.println("\n\nBEFORE:");
		System.out.println("DISPLAY: "+players[playerNum].getDisplayAsString());
		System.out.println("HAND: "+players[playerNum].getHandAsString());
		// Get the card name from the file name

		Card c = players[playerNum].getCardFromHand(name);
		
		// Supporter or card being played
		if (c instanceof SupporterCard || c instanceof ColourCard) {
			// Try adding the card to the display
			Boolean result = players[playerNum].addCardToDisplay(c, tournamentColour);
			
			System.out.println("IT WAS "+result);
			// If it succeeded, remove that card from their hand
			if (result == true) {
				System.out.println("AFTER:");
				System.out.println("DISPLAY: "+players[playerNum].getDisplayAsString());
				System.out.println("HAND: "+players[playerNum].getHandAsString());
				
				return "true";
			}
			System.out.println("AFTER:");
			System.out.println("DISPLAY: "+players[playerNum].getDisplayAsString());
			System.out.println("HAND: "+players[playerNum].getHandAsString());
			
			return "false";
			// If it failed, do nothing
		}
		// players[playerNum].addCardToDisplay(card, playerNum)
		
		return null;
	}

	public void overrideTourColour(int colour) {
		tournamentColour = colour;
	}

	public int getDiscardPileSize() {
		return discardPile.size();
	}

	/** 
	 * Withdraw the given player and check for a win
	 * @param playerNum
	 * 			the player we want to withdraw
	 * @return a string containing the winning player number
	 * 			if there is no winner, it returns empty
	 */
	public Object withdrawPlayer(int playerNum) {
		return null;
	}
}
