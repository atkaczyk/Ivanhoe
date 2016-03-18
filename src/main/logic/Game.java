package logic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import utils.Config;

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
		// TODO: DELETE THIS
		players[0].addCardToHand(new ActionCard("Shield"));


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
		do {
			goToNextPlayer();
		} while (!playerCanStart(currentPlayer));

		startTournament();
	}

	public boolean playerCanStart(Player p) {
		for (Card c : p.getHandCards()) {
			if (c instanceof SimpleCard) {
				return true;
			}
		}

		return false;
	}

	// Change the current player to be the next player
	public void goToNextPlayer() {
		// If the current player is the last one in the deque set to first
		// player
		// Make sure that the player is not withdrawn

		do {
			if (players[numOfPlayers - 1].getName().equals(
					currentPlayer.getName())) {
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
		} while (currentPlayer.isWithdrawn());

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
				if (!moreThanOneCardInOtherDisplays(playerNum)) {
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
				if (!moreThanOneCardInOtherDisplays(playerNum)) {
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
				if (!moreThanOneCardInOtherDisplays(playerNum)) {
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
						if (players[i].getDisplayCards().size() > 1
								&& players[i].hasSupporterCardInDisplay()
								&& !players[i].hasSpecialCard("Shield")) {
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
			} else if (name.equals("Riposte")) {
				if (moreThanOneCardInOtherDisplays(playerNum)) {
					return "moreInformationNeeded~Riposte@"
							+ getAllPlayersNamesAndLastDisplayCard(playerNum);
				} else {
					return "false:You cannot play a riposte card when there are no cards you can remove from other player displays!";
				}
			} else if (name.equals("Unhorse")) {
				if (tournamentColour == Config.PURPLE) {
					return "moreInformationNeeded~Unhorse@" + Config.RED
							+ Config.BLUE + Config.YELLOW;
				} else {
					return "false:You cannot play an unhorse card when the tournament colour is not purple!";
				}
			} else if (name.equals("Change Weapon")) {
				if (tournamentColour == Config.RED
						|| tournamentColour == Config.BLUE
						|| tournamentColour == Config.YELLOW) {
					return "moreInformationNeeded~Change Weapon@" + Config.RED
							+ Config.BLUE + Config.YELLOW;
				} else {
					return "false:You cannot play a change weapon card when the tournament colour is not red, blue or yellow!";
				}
			} else if (name.equals("Break Lance")) {
				String playersToChoose = getPlayersWithPurpleToRemove(playerNum);
				if (!playersToChoose.equals("")) {
					return "moreInformationNeeded~Break Lance@"
							+ playersToChoose;
				} else {
					return "false:You cannot play a break lance card when there are no purple cards to remove from other players!";
				}
			} else if (name.equals("Dodge")) {
				if (moreThanOneCardInOtherDisplays(playerNum)) {
					return "moreInformationNeeded~Dodge@"
							+ getDodgeInfo(playerNum);
				} else {
					return "false:You cannot play a dodge card when there are no cards to remove from other opponent's displays!";
				}
			} else if (name.equals("Retreat")) {
				if (!players[playerNum].hasSpecialCard("Shield")
						&& players[playerNum].getDisplayCards().size() > 1) {
					return "moreInformationNeeded~Retreat@"
							+ players[playerNum]
									.getDisplayAsStringNoDuplicates();
				} else {
					return "false:You cannot play a retreat card when you don't have more than one card in your display!";
				}
			} else if (name.equals("Knock Down")) {
				if (playersWithHandCards(playerNum)) {
					return "moreInformationNeeded~Knock Down@"
							+ playersWithHandToChoose(playerNum);
				} else {
					return "false:You cannot play a knock down card when there are no players to take a card form their hand!";
				}
			} else if (name.equals("Outwit")) {
				if (players[playerNum].getNumFaceupCards() > 0
						&& otherPlayersHaveFaceupCards(playerNum)) {
					return "moreInformationNeeded~Outwit@"
							+ getOutwitInfo(playerNum);
				} else {
					return "false:You cannot play an outwit card when there are no faceup cards you can switch!";
				}
			} else if (name.equals("Shield")) {
				// Take card from the player's hand and apply it to the player
				players[playerNum].addSpecialCard(players[playerNum]
						.removeCardFromHand(name));

				return "actionCardPlayedMessage~" + name + ","
						+ getPlayer(playerNum).getName();
			} else if (name.equals("Adapt")) {
				if (allowedToPlayAdapt()) {
					moveCardFromHandToDiscardPile(playerNum, name);
					return "adaptNeedMoreInfo~"+getAdaptInfo();
				} else {
					return "false:You cannot play an adapt card when there are no cards to remove from any players!";
				}
			}
		}

		return "false:This action card has not been implemented yet!";
	}

	private boolean allowedToPlayAdapt() {
		for (Player p: players) {
			if (p.allowedToPlayAdapt()) {
				return true;
			}
		}
		return false;
	}

	private String getAdaptInfo() {
		String result = "";
		for (int i = 0; i < numOfPlayers; i++) {
			if (!players[i].isWithdrawn() && players[i].allowedToPlayAdapt()) {
				result += i + "-";
				result += players[i].getValuesAndCardsAsStringNoDuplicates();
				result += "#";
			}
		}
		
		if (result.endsWith("#")) {
			result = result.substring(0, result.length() - 1);
		}
		
		return result;
	}

	private String getOutwitInfo(int playerNum) {
		String result = "";

		result += players[playerNum].getFaceupCardsAsStringNoDuplicates();

		result += "|";

		for (int i = 0; i < numOfPlayers; i++) {
			if (playerNum != i && !players[i].isWithdrawn()) {
				if (players[i].getNumFaceupCards() > 0) {
					result += players[i].getName();
					result += "-"
							+ players[i].getFaceupCardsAsStringNoDuplicates()
							+ "-";
					result += "#";
				}
			}
		}

		if (result.endsWith("#")) {
			result = result.substring(0, result.length() - 1);
		}

		return result;
	}

	private boolean otherPlayersHaveFaceupCards(int playerNum) {
		for (int i = 0; i < numOfPlayers; i++) {
			if (playerNum != i && !players[i].isWithdrawn()) {
				if (players[i].getNumFaceupCards() > 0) {
					return true;
				}
			}
		}
		return false;
	}

	private String playersWithHandToChoose(int playerNum) {
		String result = "";
		for (int i = 0; i < numOfPlayers; i++) {
			if (!players[i].hasSpecialCard("Shield") && playerNum != i
					&& !players[i].isWithdrawn()) {
				if (players[i].getHandCards().size() > 0) {
					result += players[i].getName();
					result += ",";
				}
			}
		}

		if (result.endsWith(",")) {
			result = result.substring(0, result.length() - 1);
		}

		return result;
	}

	private boolean playersWithHandCards(int playerNum) {
		for (int i = 0; i < numOfPlayers; i++) {
			if (!players[i].hasSpecialCard("Shield") && playerNum != i
					&& !players[i].isWithdrawn()) {
				if (players[i].getHandCards().size() > 0) {
					return true;
				}
			}
		}
		return false;
	}

	private String getDodgeInfo(int playerNum) {
		String result = "";
		for (int i = 0; i < numOfPlayers; i++) {
			if (playerNum != i && !players[i].isWithdrawn()) {
				if (players[i].getDisplayCards().size() > 1) {
					result += players[i].getName();
					result += "-";
					result += players[i].getDisplayAsStringNoDuplicates();
					result += "-";
					result += "#";
				}
			}
		}

		if (result.endsWith("#")) {
			result = result.substring(0, result.length() - 1);
		}

		return result;
	}

	private String getPlayersWithPurpleToRemove(int playerNum) {
		String result = "";

		for (int i = 0; i < numOfPlayers; i++) {
			Player p = players[i];
			if (i != playerNum) {
				if (!p.isWithdrawn() && p.getDisplayCards().size() > 1
						&& p.hasPurpleCardInDisplay()
						&& !p.hasSpecialCard("Shield")) {
					result += p.getName() + ",";
				}
			}
		}

		if (result.endsWith(",")) {
			result = result.substring(0, result.length() - 1);
		}

		return result;
	}

	private boolean moreThanOneCardInOtherDisplays(int playerNum) {
		for (int i = 0; i < numOfPlayers; i++) {
			if (playerNum != i && !players[i].isWithdrawn()
					&& !players[i].hasSpecialCard("Shield")) {
				if (players[i].getDisplayCards().size() > 1) {
					return true;
				}
			}
		}

		return false;
	}

	// excluding the given player
	private String getAllPlayersNamesAndLastDisplayCard(int playerNum) {
		String result = "";

		for (int i = 0; i < numOfPlayers; i++) {
			// First we need to check to see if they have more than one card in
			// their display
			if (i != playerNum && players[i].getDisplayCards().size() > 1) {
				if (result.endsWith("]")) {
					result += ",";
				}
				result += players[i].getName();
				result += "-"
						+ players[i].getDisplayCards().getLast().getName()
						+ "-";
			}
		}

		return result;
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

			while (!playerCanStart(currentPlayer)) {
				goToNextPlayer();
			}

			return winningPlayer + "," + (tournamentNumber - 1) + ","
					+ tournamentColour;
		}

		goToNextPlayer();

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
		if (players[playerNum].addToken(colour)) {
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

	public String playActionCard(int playerNum, String info) {
		String cardName = info.split("@")[0];
		String extraInfo = info.split("@")[1];
		if (info.contains("Riposte")) {
			// Take the last card played on the given opponent's display and add
			// it to the given player
			Card cardToMove = null;
			for (Player p : players) {
				if (p.getName().equals(extraInfo)) {
					cardToMove = p.getDisplayCards().removeLast();
				}
			}
			players[playerNum].addCardToDisplay(cardToMove, tournamentColour);
		} else if (info.contains("Unhorse")) {
			int newColour = Integer.parseInt(extraInfo);
			tournamentColour = newColour;
		} else if (info.contains("Change Weapon")) {
			int newColour = Integer.parseInt(extraInfo);
			tournamentColour = newColour;
		} else if (info.contains("Break Lance")) {
			List<Card> cardsToDiscard = null;
			for (Player p : players) {
				if (p.getName().equals(extraInfo)) {
					cardsToDiscard = p.removeAllPurpleCards();
				}
			}
			for (Card c : cardsToDiscard) {
				discardPile.add(c);
			}
		} else if (info.contains("Dodge")) {
			String playerName = extraInfo.split(",")[0];
			String chosenCard = extraInfo.split(",")[1];

			for (Player p : players) {
				if (p.getName().equals(playerName)) {
					discardPile.add(p.removeFromDisplay(chosenCard));
				}
			}
		} else if (info.contains("Retreat")) {
			players[playerNum].addCardToHand(players[playerNum]
					.removeFromDisplay(extraInfo));
		} else if (info.contains("Knock Down")) {
			for (Player p : players) {
				if (p.getName().equals(extraInfo)) {
					players[playerNum].addCardToHand(p.getRandomCardFromHand());
				}
			}
		} else if (info.contains("Outwit")) {
			String playerCard = extraInfo.split(",")[0];
			String targetPlayer = extraInfo.split(",")[1];
			String targetCard = extraInfo.split(",")[2];

			Card tempPlayerCard = players[playerNum]
					.removeFaceupCard(playerCard);
			Card tempTargetCard = null;

			Player targetPlayerObject = null;

			for (Player p : players) {
				if (p.getName().equals(targetPlayer)) {
					targetPlayerObject = p;
					tempTargetCard = p.removeFaceupCard(targetCard);
				}
			}

			players[playerNum].addFaceupCard(tempTargetCard);
			targetPlayerObject.addFaceupCard(tempPlayerCard);
		}

		moveCardFromHandToDiscardPile(playerNum, cardName);

		return "actionCardPlayedMessage~" + cardName + ","
				+ getPlayer(playerNum).getName();
	}

	public void adaptCardsChosen(int playerNum, String valuesAndNames) {
		Player p = players[playerNum];
		
		String[] valuesInfo = valuesAndNames.split(",");
		for (String info: valuesInfo) {
			int value = Integer.parseInt(info.split("-")[0]);
			String cardToKeep = info.split("-")[1];
			
			List<Card> cardsToDiscard = p.keepOnlyCard(value, cardToKeep);
			for (Card c : cardsToDiscard) {
				discardPile.add(c);
			}
		}
		
		List<Card> cardsToDiscard = p.removeDuplicatesInDisplay();;
		for (Card c : cardsToDiscard) {
			discardPile.add(c);
		}
	}
}
