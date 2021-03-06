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

	public void addPlayer(String playerName, int tokenColour, String avatarFile) {
		for (int i = 0; i < numOfPlayers; i++) {
			if (players[i] == null) {
				players[i] = new Player(playerName);
				players[i].setAvatar(avatarFile);

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

		do {
			goToNextPlayer(false);
		} while (!playerCanStart(currentPlayer));

		startTournament();
	}

	private void clearAllCardCounters() {
		for (Player p : players) {
			p.clearCardCounter();
		}
	}

	private boolean playerCanStart(Player p) {
		for (Card c : p.getHandCards()) {
			if (c instanceof SimpleCard) {
				return true;
			}
		}

		return false;
	}

	// Change the current player to be the next player
	public String goToNextPlayer(boolean announcingEndOfTurn) {
		// Make sure that the player is not withdrawn
		String returnValue = "";

		if (announcingEndOfTurn) {
			int maxScore = -1;
			int playerNum = -1;
			for (int i = 0; i < numOfPlayers; i++) {
				Player p = players[i];
				if (p.getDisplayTotal(tournamentColour) > maxScore) {
					maxScore = p.getDisplayTotal(tournamentColour);
				}
				if (p.getName().equals(currentPlayer.getName())) {
					playerNum = i;
				}
			}

			// If the current player's display is not the highest, they must
			// withdraw
			if (maxScore != currentPlayer.getDisplayTotal(tournamentColour)) {
				returnValue = withdrawPlayer(playerNum, false);
			}

			// If the current player has not yet played a card in their turn,
			// they
			// must withdraw
			else if (currentPlayer.getCardsPlayedThisTurn() == 0) {
				returnValue = withdrawPlayer(playerNum, false);
			}
		}

		clearAllCardCounters();

		if (!returnValue.contains("aWinnerWasFound")) {
			do {
				if (players[numOfPlayers - 1].getName().equals(
						currentPlayer.getName())) {
					currentPlayer = players[0];
				} else {
					// Otherwise, get position of current player
					int i;
					for (i = 0; i < numOfPlayers; i++) {
						if (players[i].getName()
								.equals(currentPlayer.getName())) {
							currentPlayer = players[i + 1];
							break;
						}
					}
				}
			} while (currentPlayer.isWithdrawn());
		}
		return returnValue;
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

	private void startTournament() {
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
	public String drawCard(int playerNum) {
		Player player = players[playerNum];

		if (player.getCardsPlayedThisTurn() > 0) {
			return "You have already played a card, you can no longer draw a card!";
		}
		if (player.getCardsDrawnThisTurn() > 0) {
			return "You have already drawn a card, you cannot draw another one!";
		}
		Card c = drawPile.getCard();
		player.addCardToHand(c);
		player.addToCardsDrawnThisTurn();

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

		return "";
	}

	public DrawPile getDrawPile() {
		return drawPile;
	}

	public String playCard(int playerNum, String name) {
		Card c = players[playerNum].getCardFromHand(name);
		if (c instanceof SimpleCard) {
			return playSimpleCard(playerNum, name, c);
		} else {
			if (Config.ACTION_CARDS_NO_INPUT.contains(name)) {
				if (getPlayerWithIvanhoe() != -1) {
					// ask for ivanhoe
					return "askForIvanhoe~Would you like to play your Ivanhoe card? "
							+ getPlayer(playerNum).getName()
							+ " is trying to play the "
							+ name
							+ " action card.---" + name + "=" + playerNum;
				}
				return playActionCardNoExtraInfoRequired(playerNum, name);
			}

			if (name.equals("Riposte")) {
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
				if (checkForPlayersWithHandCards(playerNum)) {
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
					if (getPlayerWithIvanhoe() != -1) {
						// ask for ivanhoe
						return "askForIvanhoe~Would you like to play your Ivanhoe card? "
								+ getPlayer(playerNum).getName()
								+ " is trying to play the "
								+ name
								+ " action card.---" + name + "=" + playerNum;
					}
					moveCardFromHandToDiscardPile(playerNum, name);
					return "adaptNeedMoreInfo~" + getAdaptInfo();
				} else {
					return "false:You cannot play an adapt card when there are no cards to remove from any players!";
				}
			} else if (name.equals("Stunned")) {
				return "moreInformationNeeded~Stunned@" + getStunnedInfo();
			}
		}

		return "false:You will be promted for Ivanhoe, if an action card gets played!";
	}

	public int getPlayerWithIvanhoe() {
		for (int i = 0; i < numOfPlayers; i++) {
			if (players[i].hasIvanhoeCard()) {
				return i;
			}
		}
		return -1;
	}

	private String playActionCardNoExtraInfoRequired(int playerNum, String name) {
		if (name.equals("Drop Weapon")) {
			// The tournament color changes from red, blue or yellow to
			// green.
			if (tournamentColour == Config.RED
					|| tournamentColour == Config.BLUE
					|| tournamentColour == Config.YELLOW) {
				tournamentColour = Config.GREEN;
				moveCardFromHandToDiscardPile(playerNum, name);
				players[playerNum].addActionCardPlayed();
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
			players[playerNum].addActionCardPlayed();
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
			players[playerNum].addActionCardPlayed();
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
			players[playerNum].addActionCardPlayed();
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
			players[playerNum].addActionCardPlayed();
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
		return "";
	}

	private String playSimpleCard(int playerNum, String name, Card c) {
		Boolean result = players[playerNum].addCardToDisplay(c,
				tournamentColour);

		if (result == true) {
			return "true";
		}

		if (players[playerNum].hasSpecialCard("Stunned")) {
			return "false:You cannot add a second card to your display when you have a stunned card on you!";
		}

		if (c instanceof SupporterCard) {
			return "false:You cannot add a second maiden card to your display!";
		}
		return "false:When playing a colour card, the colour must match the current tournament colour!";
	}

	private String getStunnedInfo() {
		String result = "";
		for (Player p : players) {
			if (!p.isWithdrawn()) {
				result += p.getName() + ",";
			}
		}

		if (result.endsWith(",")) {
			result = result.substring(0, result.length() - 1);
		}

		return result;
	}

	private boolean allowedToPlayAdapt() {
		for (Player p : players) {
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

	private boolean checkForPlayersWithHandCards(int playerNum) {
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
	public void moveCardFromHandToDiscardPile(int playerNum, String name) {
		Card c = players[playerNum].getCardFromHand(name);
		discardPile.add(c);
		players[playerNum].getHandCards().remove(c);
	}

	public void overrideTourColour(int colour) {
		tournamentColour = colour;
	}

	/**
	 * Withdraw the given player and check for a win
	 * 
	 * @param playerNum
	 *            the player we want to withdraw
	 * @return the <name of winner>,<tournament number>,<tournament colour>
	 */
	public String withdrawPlayer(int playerNum, Boolean mustGoToNextPlayer) {
		// Withdraw the given player
		List<Card> cardsToDiscard = getPlayer(playerNum).withdraw();

		String maidenInfo = "";

		for (Card c : cardsToDiscard) {
			if (c.getName().contains("Maiden")
					&& players[playerNum].getTokens().size() > 0) {
				maidenInfo = "maidenPickTokenToReturn~"
						+ players[playerNum].getTokensAsString();
			}
			discardPile.add(c);
		}

		String winningPlayer = "";
		int playersStillActive = 0;
		Player winningPlayerObject = null;

		// See if there is only one player left that isn't withdrawn
		for (int i = 0; i < numOfPlayers; i++) {
			if (!players[i].isWithdrawn()) {
				playersStillActive++;
				winningPlayerObject = players[i];
				winningPlayer = players[i].getName();
			}
		}

		if (playersStillActive == 1) {
			startTournament();

			currentPlayer = winningPlayerObject;
			while (!playerCanStart(currentPlayer)) {
				goToNextPlayer(false);
			}

			return winningPlayer + "," + (tournamentNumber - 1) + ","
					+ tournamentColour + "#" + maidenInfo + "#aWinnerWasFound";
		}

		if (mustGoToNextPlayer) {
			goToNextPlayer(false);
		}

		if (!maidenInfo.equals("")) {
			return "#" + maidenInfo;
		}

		return "#";
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

	public String playActionCardWithAdditionalInfo(int playerNum, String info) {
		String cardName = info.split("@")[0];
		String extraInfo = info.split("@")[1];

		players[playerNum].addActionCardPlayed();

		Boolean allowedToDiscard = true;
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
		} else if (info.contains("Stunned")) {
			// Remove the card from the players hand
			Card cardToMove = players[playerNum].removeCardFromHand("Stunned");
			for (Player p : players) {
				if (p.getName().equals(extraInfo)) {
					p.addSpecialCard(cardToMove);
					break;
				}
			}

			allowedToDiscard = false;
		}

		if (allowedToDiscard) {
			moveCardFromHandToDiscardPile(playerNum, cardName);
		}

		return "actionCardPlayedMessage~" + cardName + ","
				+ getPlayer(playerNum).getName();
	}

	public void adaptCardsChosen(int playerNum, String valuesAndNames) {
		Player p = players[playerNum];

		String[] valuesInfo = valuesAndNames.split(",");
		for (String info : valuesInfo) {
			int value = Integer.parseInt(info.split("-")[0]);
			String cardToKeep = info.split("-")[1];

			List<Card> cardsToDiscard = p.keepOnlyCard(value, cardToKeep);
			for (Card c : cardsToDiscard) {
				discardPile.add(c);
			}
		}

		List<Card> cardsToDiscard = p.removeDuplicatesInDisplay();
		;
		for (Card c : cardsToDiscard) {
			discardPile.add(c);
		}
	}

	public String processIvanhoeCard(String info) {
		String[] splitInfo = info.split("=");
		String ivanhoeChoice = splitInfo[0];
		String actionCardName = splitInfo[1];
		int playerNum = Integer.parseInt(splitInfo[2]);

		if (splitInfo.length == 3) {
			if (ivanhoeChoice.equals("No")) {
				// Player decided not to cancel the action card, so play the
				// card as normal
				if (actionCardName.equals("Adapt")) {
					moveCardFromHandToDiscardPile(playerNum, actionCardName);
					return "adaptNeedMoreInfo~" + getAdaptInfo();
				}
				return playActionCardNoExtraInfoRequired(playerNum,
						actionCardName);
			} else {
				// Player decided to cancel the action card, so discard both
				// cards instead
				String ivanhoePlayerName = getPlayer(getPlayerWithIvanhoe())
						.getName();
				moveCardFromHandToDiscardPile(getPlayerWithIvanhoe(), "Ivanhoe");
				moveCardFromHandToDiscardPile(playerNum, actionCardName);
				return "actionCardPlayedMessage~Ivanhoe," + ivanhoePlayerName;
			}
		} else {
			// This means the player was trying to play an action card with
			// additional info
			String extraInfo = splitInfo[3];
			if (ivanhoeChoice.equals("No")) {
				// Player decided not to cancel the action card, so play the
				// card as normal
				return playActionCardWithAdditionalInfo(playerNum,
						actionCardName + "@" + extraInfo);
			} else {
				// Player decided to cancel the action card, so discard both
				// cards instead
				String ivanhoePlayerName = getPlayer(getPlayerWithIvanhoe())
						.getName();
				moveCardFromHandToDiscardPile(getPlayerWithIvanhoe(), "Ivanhoe");
				moveCardFromHandToDiscardPile(playerNum, actionCardName);
				return "actionCardPlayedMessage~" + actionCardName + ","
						+ ivanhoePlayerName;
			}
		}
	}

	public String checkForIvanhoeAdditionalInfoCard(int playerNum, String info) {
		String cardName = info.split("@")[0];
		String extraInfo = info.split("@")[1];

		if (getPlayerWithIvanhoe() != -1) {
			// ask for ivanhoe
			// TODO: change this to give a custom ivanhoe message for each
			// action card.
			return "askForIvanhoe~Would you like to play your Ivanhoe card? "
					+ getPlayer(playerNum).getName()
					+ " is trying to play the " + cardName + " action card.---"
					+ cardName + "=" + playerNum + "=" + extraInfo;
		}
		return playActionCardWithAdditionalInfo(playerNum, info);
	}

	public void processReturnToken(int playerNum, int tokenColour) {
		int tokenToAddToPool = players[playerNum].removeToken(tokenColour);

		if (tokenToAddToPool != -1) {
			tokenPool.add(tokenToAddToPool);
		}
	}
}
