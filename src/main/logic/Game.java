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
	private List<Integer> tokenPool = new ArrayList<Integer>();
	private int tournamentColour = -1;
	private ArrayDeque<Card> discardPile = new ArrayDeque<Card>();;

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
		// Distribute 8 cards to each player
		for (int i = 0; i < numOfPlayers; i++) {
			for (int j = 1; j <= 8; j++) {
				players[i].addCardToHand(drawPile.getCard());

			}
		}

		// Initialize the 25 tokens (5 of each colour)
		for (int colour : Config.ALL_TOKEN_COLOURS) {
			for (int i = 1; i <= 5; i++) {
				tokenPool.add(colour);
			}
		}

		// Figure out which player is first
		goToNextPlayer();
		startTournament();
	}

	// Change the current player to be the next player
	public void goToNextPlayer() {
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
		// Get the card name from the file name
		Card c = players[playerNum].getCardFromHand(name);

		if (c instanceof SimpleCard) {
			Boolean result = players[playerNum].addCardToDisplay(c,
					tournamentColour);

			if (result == true) {
				return "true";
			}

			if (c instanceof SupporterCard) {
				return "false:You cannot add a second maiden card to your display!";
			}
			return "false:When playing a colour card, the colour must match the current tournament colour!";
		} else {
			if (name.equals("Drop Weapon")) {
				// The tournament color changes from red, blue or yellow to
				// green.
				if (tournamentColour == Config.RED
						|| tournamentColour == Config.BLUE
						|| tournamentColour == Config.YELLOW) {
					tournamentColour = Config.GREEN;
					moveCardFromHandToDiscardPile(playerNum, name);

					return "true";
				} else {
					return "false:Tournament colour must be red, blue or yellow to play a drop weapon card!";
				}
			} else if (name.equals("Outmaneuver")) {
				// All opponents must remove the last card played on their
				// displays.

				// First check to see there is at least one player where you can
				// remove a card
				Boolean playerFound = false;
				for (int i = 0; i < numOfPlayers; i++) {
					if (playerNum != i) {
						if (players[i].getDisplayCards().size() > 1) {
							playerFound = true;
							break;
						}
					}
				}
				if (!playerFound) {
					return "false:You cannot play an outmaneuver card when there are no cards you can remove from other player displays!";
				}

				moveCardFromHandToDiscardPile(playerNum, name);
				for (int i = 0; i < numOfPlayers; i++) {
					if (playerNum != i) {
						if (players[i].getDisplayCards().size() >= 2) {
							Card cardFromDisplay = players[i].getDisplayCards()
									.removeLast();
							discardPile.add(cardFromDisplay);
						}
					}
				}
				return "true";
			} else if (name.equals("Charge")) {
				// Identify the lowest value card throughout all displays. All
				// players must discard all cards of this value from their
				// displays.

				// First check to see there is at least one player where you can
				// remove a card
				Boolean playerFound = false;
				for (int i = 0; i < numOfPlayers; i++) {
					if (playerNum != i) {
						if (players[i].getDisplayCards().size() > 1) {
							playerFound = true;
							break;
						}
					}
				}
				if (!playerFound) {
					return "false:You cannot play a charge card when there are no cards you can remove from other player displays!";
				}

				moveCardFromHandToDiscardPile(playerNum, name);
				for (int i = 0; i < numOfPlayers; i++) {
					if (playerNum != i) {
						int lowestValue = players[i].getLowestDisplayValue();
						List<Card> cardsRemoved = players[i]
								.removeAllCardsWithValue(lowestValue);
						for (Card toDiscard : cardsRemoved) {
							discardPile.add(toDiscard);
						}
					}
				}

				return "actionCardPlayedMessage~" + name + ","
						+ getPlayer(playerNum).getName();
			} else if (name.equals("Countercharge")) {
				// Identify the highest value card throughout all displays. All
				// players must discard all cards of this value from their
				// displays.

				// First check to see there is at least one player where you can
				// remove a card
				Boolean playerFound = false;
				for (int i = 0; i < numOfPlayers; i++) {
					if (playerNum != i) {
						if (players[i].getDisplayCards().size() > 1) {
							playerFound = true;
							break;
						}
					}
				}
				if (!playerFound) {
					return "false:You cannot play a counter charge card when there are no cards you can remove from other player displays!";
				}

				moveCardFromHandToDiscardPile(playerNum, name);
				for (int i = 0; i < numOfPlayers; i++) {
					if (playerNum != i) {
						int highestValue = players[i].getHighestDisplayValue();
						List<Card> cardsRemoved = players[i]
								.removeAllCardsWithValue(highestValue);
						for (Card toDiscard : cardsRemoved) {
							discardPile.add(toDiscard);
						}
					}
				}

				return "actionCardPlayedMessage~" + name + ","
						+ getPlayer(playerNum).getName();
			} else if (name.equals("Disgrace")) {
				// Each player must remove all his supporters from his display

				// First check to see there is at least one player where you can
				// remove a card
				Boolean playerFound = false;
				for (int i = 0; i < numOfPlayers; i++) {
					if (playerNum != i) {
						if (players[i].getDisplayCards().size() > 1 && players[i].hasSupporterCardInDisplay()) {
							playerFound = true;
							break;
						}
					}
				}
				if (!playerFound) {
					return "false:You cannot play a disgrace card when there are no cards you can remove from other player displays!";
				}

				moveCardFromHandToDiscardPile(playerNum, name);
				for (int i = 0; i < numOfPlayers; i++) {
					if (playerNum != i) {
						List<Card> cardsRemoved = players[i]
								.removeAllSupporterCards();
						for (Card toDiscard : cardsRemoved) {
							discardPile.add(toDiscard);
						}
					}
				}
				
				return "actionCardPlayedMessage~" + name + ","
						+ getPlayer(playerNum).getName();
			}

			// System.out.println("\n\nBEFORE:");
			// System.out.println("DISPLAY: "
			// + players[playerNum].getDisplayAsString());
			// System.out.println("HAND: " +
			// players[playerNum].getHandAsString());
			//
			// System.out.println("AFTER:");
			// System.out.println("DISPLAY: "
			// + players[playerNum].getDisplayAsString());
			// System.out.println("HAND: " +
			// players[playerNum].getHandAsString());
			System.out.println(discardPile);

		}

		return "false:This action card has not been implemented yet!";
	}

	/**
	 * Take the card out of the players hand and move to the discard pile
	 * 
	 * @param playerNum
	 *            The player that is having the card moved
	 * @param name
	 *            The name of the card we are moving
	 */
	private void moveCardFromHandToDiscardPile(int playerNum, String name) {
		Card c = players[playerNum].getCardFromHand(name);
		discardPile.add(c);
		players[playerNum].getHandCards().remove(c);
	}

	public void overrideTourColour(int colour) {
		tournamentColour = colour;
	}

	public int getDiscardPileSize() {
		return discardPile.size();
	}

	/**
	 * Withdraw the given player and check for a win
	 * 
	 * @param playerNum
	 *            the player we want to withdraw
	 * @return the <name of winner>,<tournament number>,<tournament colour>
	 */
	public String withdrawPlayer(int playerNum) {
		// Withdraw the given player
		List<Card> cardsToDiscard = getPlayer(playerNum).withdraw();

		for (Card c : cardsToDiscard) {
			discardPile.add(c);
		}
		
		String winningPlayer = "";
		int playersStillActive = 0;

		// See if there is only one player left that isn't withdrawn
		for (int i = 0; i < numOfPlayers; i++) {
			if (!players[i].isWithdrawn()) {
				playersStillActive++;
				currentPlayer = players[i];
				winningPlayer = players[i].getName();
			}
		}

		if (playersStillActive == 1) {
			startTournament();
			return winningPlayer + "," + (tournamentNumber-1) + ","
					+ tournamentColour;
		}

		return "";
	}

	/**
	 * See if any player has won the game
	 * 
	 * @return Name of the winning player (returns empty if no winner
	 */
	public String checkForWinner() {
		int tokensRequiredToWin = 5;

		if (numOfPlayers == 4 || numOfPlayers == 5) {
			tokensRequiredToWin = 4;
		}

		for (int i = 0; i < numOfPlayers; i++) {
			if (players[i].isWinnerOfGame(tokensRequiredToWin)) {
				return players[i].getName();
			}
		}
		return "";
	}

	public ArrayDeque<Card> getDiscardPile() {
		return discardPile;
	}

	public List<Integer> getTokenPool() {
		return tokenPool;
	}

	public void addTokenToPlayer(int playerNum, int colour) {
		if (players[0].addToken(colour)) {
			tokenPool.remove((Object) colour);
		}
	}

	public String getTokensRemainingForPlayer(int playerNum) {
		String result = "";

		for (int colour : Config.ALL_TOKEN_COLOURS) {
			if (!players[playerNum].getTokens().contains(colour)) {
				result += colour;
			}
		}

		return result;
	}
}
