package logicTest;

import static org.junit.Assert.*;

import java.util.ArrayDeque;

import logic.ActionCard;
import logic.Card;
import logic.ColourCard;
import logic.Game;
import logic.Player;
import logic.SupporterCard;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.Config;

public class TestGame {
	private static final int NUM_OF_PLAYERS = 2;
	private static final String PLAYER_ONE_NAME = "Jack";
	private static final String PLAYER_TWO_NAME = "Chloe";
	private static final String PLAYER_THREE_NAME = "Chase";
	private static final String PLAYER_FOUR_NAME = "Tony";
	private static final String PLAYER_FIVE_NAME = "Edward";
	private static final Card PURPLE_CARD_3 = new ColourCard(
			"Purple (Jousting) 3", 3, Config.PURPLE);
	private static final ArrayDeque<Card> HAND_WITH_ALL_COLOURS = new ArrayDeque<Card>();
	static {
		HAND_WITH_ALL_COLOURS.add(new ColourCard("", 2, Config.RED));
		HAND_WITH_ALL_COLOURS.add(new ColourCard("", 2, Config.YELLOW));
		HAND_WITH_ALL_COLOURS.add(new ColourCard("", 2, Config.GREEN));
		HAND_WITH_ALL_COLOURS.add(new ColourCard("", 2, Config.BLUE));
		HAND_WITH_ALL_COLOURS.add(new ColourCard("", 2, Config.PURPLE));
	}
	// Supporter Cards
	private static final Card MAIDEN_CARD = new SupporterCard("Maiden 6", 6);
	private static final Card SQUIRE_CARD_2 = new SupporterCard("Squire 2", 2);
	private static final Card SQUIRE_CARD_3 = new SupporterCard("Squire 3", 3);

	// Colour Card
	private static final Card BLUE_CARD_3 = new ColourCard("Blue (Axe) 3", 3,
			Config.BLUE);
	private static final Card GREEN_CARD_1 = new ColourCard(
			"Green (No Weapon) 1", 1, Config.GREEN);
	private static final Card PURPLE_CARD_7 = new ColourCard(
			"Purple (Jousting) 7", 7, Config.PURPLE);

	// Action Cards
	private static final Card DROP_WEAPON_CARD = new ActionCard("Drop Weapon");
	private static final Card OUTMANEUVER_CARD = new ActionCard("Outmaneuver");
	private static final Card CHARGE_CARD = new ActionCard("Charge");
	private static final Card COUNTER_CHARGE_CARD = new ActionCard(
			"Countercharge");
	private static final Card DISGRACE_CARD = new ActionCard("Disgrace");

	private static final Card RIPOSTE_CARD = new ActionCard("Riposte");
	private static final Card UNHORSE_CARD = new ActionCard("Unhorse");
	private static final Card CHANGE_WEAPON_CARD = new ActionCard(
			"Change Weapon");
	private static final Card BREAK_LANCE_CARD = new ActionCard("Break Lance");
	private static final Card DODGE_CARD = new ActionCard("Dodge");
	private static final Card RETREAT_CARD = new ActionCard("Retreat");
	private static final Card KNOCK_DOWN_CARD = new ActionCard("Knock Down");
	private static final Card OUTWIT_CARD = new ActionCard("Outwit");
	private static final Card ADAPT_CARD = new ActionCard("Adapt");

	private static final Card SHIELD_CARD = new ActionCard("Shield");
	private static final Card STUNNED_CARD = new ActionCard("Stunned");
	private static final Card IVANHOE_CARD = new ActionCard("Ivanhoe");

	Game game;

	@Before
	public void setUp() {
		game = new Game();
		game.setNumPlayers(NUM_OF_PLAYERS);
	}

	@Test
	public void numPlayersSet() {
		game.setNumPlayers(NUM_OF_PLAYERS);

		assertEquals(NUM_OF_PLAYERS, game.getNumPlayers());
	}

	@Test
	public void addPlayer() {
		game.addPlayer(PLAYER_ONE_NAME, Config.PURPLE, "");

		assertNotEquals(null, game.getPlayers()[0]);
	}

	@Test
	public void startingPlayer() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		assertEquals(PLAYER_TWO_NAME, game.getCurrentPlayer().getName());
	}

	@Test
	public void gameReady() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		assertEquals(true, game.isReadyToStart());
	}

	@Test
	public void gameNotReady() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");

		assertEquals(false, game.isReadyToStart());
	}

	@Test
	public void gameStart() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.startGame();

		assertEquals(false, game.getPlayers()[0].getHandCards().isEmpty());
		assertEquals(false, game.getTokenPool().isEmpty());
	}

	@Test
	public void playersActiveAfterStart() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.startGame();

		assertEquals(false, game.getPlayers()[0].isWithdrawn());
		assertEquals(false, game.getPlayers()[1].isWithdrawn());
	}

	@Test
	public void startGameTwoPlayersFirstPlayerStarts() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.startGame();

		assertEquals(PLAYER_ONE_NAME, game.getCurrentPlayer().getName());
	}

	@Test
	public void getCurrentPlayerNumber() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.startGame();

		assertEquals(0, game.getCurrentPlayerNumber());
	}

	@Test
	public void startGameTwoPlayersSecondPlayerStarts() {
		game.addPlayer(PLAYER_ONE_NAME, Config.PURPLE, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.RED, "");

		game.startGame();

		assertEquals(PLAYER_TWO_NAME, game.getCurrentPlayer().getName());
	}

	@Test
	public void pickTournColourColourInHand() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.startGame();
		game.getCurrentPlayer().clearHand();

		for (Card c : HAND_WITH_ALL_COLOURS) {
			game.getCurrentPlayer().addCardToHand(c);
		}

		game.setTournamentColour(Config.RED);
		assertEquals(Config.RED, game.getTournamentColour());
		game.setTournamentColour(Config.YELLOW);
		assertEquals(Config.YELLOW, game.getTournamentColour());
		game.setTournamentColour(Config.GREEN);
		assertEquals(Config.GREEN, game.getTournamentColour());
		game.setTournamentColour(Config.BLUE);
		assertEquals(Config.BLUE, game.getTournamentColour());
		game.setTournamentColour(Config.PURPLE);
		assertEquals(Config.PURPLE, game.getTournamentColour());
	}

	@Test
	public void pickTournColourOneSupporterInHand() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.startGame();
		game.getCurrentPlayer().clearHand();

		game.getCurrentPlayer().addCardToHand(MAIDEN_CARD);

		game.setTournamentColour(Config.BLUE);
		assertEquals(Config.BLUE, game.getTournamentColour());
	}

	@Test
	public void pickTournColourInvalidHand() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.startGame();
		game.getCurrentPlayer().clearHand();

		assertEquals(false, game.setTournamentColour(Config.BLUE));
	}

	@Test
	public void invalidPurpleTokenPreviousPurpleTournament() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.startGame();
		game.getCurrentPlayer().clearHand();

		game.getCurrentPlayer().addCardToHand(MAIDEN_CARD);

		game.setTournamentColour(Config.PURPLE);
		assertEquals(false, game.setTournamentColour(Config.PURPLE));
	}

	@Test
	public void getPlayerNumber() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");

		assertEquals(PLAYER_ONE_NAME, game.getPlayer(0).getName());
	}

	@Test
	public void tournamentStart() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.startGame();

		assertEquals(1, game.getTournamentNumber());
		assertEquals(false, game.getPlayer(0).isWithdrawn());
		assertEquals(false, game.getPlayer(1).isWithdrawn());
	}

	@Test
	public void playerDrawsCard() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.drawCard(0);

		assertEquals(1, game.getPlayer(0).getHandCards().size());
	}

	@Test
	public void twoPlayersStartTournament() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		// player one goes first
		int before = game.getDrawPile().getNumCards();
		game.drawCard(0);
		assertEquals(1, game.getPlayer(0).getHandCards().size());
		assertEquals(before - 1, game.getDrawPile().getNumCards());
	}

	@Test
	public void playingSupporterCard() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.getPlayer(0).addCardToHand(SQUIRE_CARD_2);

		assertEquals("true", game.playCard(0, SQUIRE_CARD_2.getName()));
		assertEquals(1, game.getPlayer(0).getDisplayCards().size());
		assertEquals(0, game.getPlayer(0).getHandCards().size());
	}

	@Test
	public void playingValidColourCard() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.overrideTourColour(Config.PURPLE);
		game.getPlayer(0).addCardToHand(PURPLE_CARD_3);

		assertEquals("true", game.playCard(0, PURPLE_CARD_3.getName()));
		assertEquals(1, game.getPlayer(0).getDisplayCards().size());
		assertEquals(0, game.getPlayer(0).getHandCards().size());
	}

	@Test
	public void playingInvalidColourCard() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.getPlayer(0).addCardToHand(PURPLE_CARD_3);
		game.overrideTourColour(Config.RED);

		String result = game.playCard(0, PURPLE_CARD_3.getName());
		assertEquals(true, result.contains("false"));
		assertEquals(0, game.getPlayer(0).getDisplayCards().size());
		assertEquals(1, game.getPlayer(0).getHandCards().size());
	}

	@Test
	public void startingTournamentClearsDisplay() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.startGame();
		game.getPlayer(0).addCardToDisplay(MAIDEN_CARD, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);

		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);

		game.withdrawPlayer(0, false);
		// Automatically starts the next tournament

		// When a tournament is started it should clear all the player displays
		// and move the cards to the discard pile
		assertEquals(0, game.getPlayer(0).getDisplayCards().size());
		assertEquals(0, game.getPlayer(1).getDisplayCards().size());
		assertEquals(8, game.getDiscardPile().size());
		assertEquals(2, game.getTournamentNumber());
	}

	@Test
	public void twoPlayersWithdrawOne() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.startGame();
		game.overrideTourColour(Config.BLUE);

		String expected = PLAYER_TWO_NAME + "," + game.getTournamentNumber()
				+ "," + game.getTournamentColour();

		assertEquals(expected, game.withdrawPlayer(0, true).split("#")[0]);
	}

	@Test
	public void threePlayersWithdrawTwo() {
		game.setNumPlayers(3);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.YELLOW, "");
		game.addPlayer(PLAYER_THREE_NAME, Config.PURPLE, "");

		game.startGame();

		game.withdrawPlayer(0, true);

		game.overrideTourColour(Config.BLUE);

		String expected = PLAYER_THREE_NAME + "," + game.getTournamentNumber()
				+ "," + game.getTournamentColour();

		assertEquals(expected, game.withdrawPlayer(1, true).split("#")[0]);
	}

	@Test
	public void threePlayersWithdrawOne() {
		game.setNumPlayers(3);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.YELLOW, "");
		game.addPlayer(PLAYER_THREE_NAME, Config.PURPLE, "");

		game.startGame();
		game.overrideTourColour(Config.BLUE);

		assertEquals("#", game.withdrawPlayer(1, true));
	}

	@Test
	public void fivePlayersWithdrawFour() {
		game.setNumPlayers(5);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.YELLOW, "");
		game.addPlayer(PLAYER_THREE_NAME, Config.PURPLE, "");
		game.addPlayer(PLAYER_FOUR_NAME, Config.BLUE, "");
		game.addPlayer(PLAYER_FIVE_NAME, Config.GREEN, "");

		game.startGame();

		game.withdrawPlayer(0, true);
		game.withdrawPlayer(1, true);
		game.withdrawPlayer(4, true);

		game.overrideTourColour(Config.BLUE);

		String expected = PLAYER_THREE_NAME + "," + game.getTournamentNumber()
				+ "," + game.getTournamentColour();

		assertEquals(expected, game.withdrawPlayer(3, true).split("#")[0]);
	}

	@Test
	public void twoPlayersOneWins() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.startGame();

		game.getPlayer(1).addToken(Config.BLUE);
		game.getPlayer(1).addToken(Config.PURPLE);
		game.getPlayer(1).addToken(Config.YELLOW);
		game.getPlayer(1).addToken(Config.RED);
		game.getPlayer(1).addToken(Config.GREEN);

		assertEquals(PLAYER_TWO_NAME, game.checkForWinner());
	}

	@Test
	public void twoPlayersNoWinner() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.startGame();

		game.getPlayer(1).addToken(Config.PURPLE);
		game.getPlayer(1).addToken(Config.YELLOW);
		game.getPlayer(1).addToken(Config.RED);
		game.getPlayer(1).addToken(Config.GREEN);

		assertEquals("", game.checkForWinner());
	}

	@Test
	public void fourPlayersOneWins() {
		game.setNumPlayers(4);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");
		game.addPlayer(PLAYER_THREE_NAME, Config.BLUE, "");
		game.addPlayer(PLAYER_FOUR_NAME, Config.BLUE, "");

		game.startGame();

		// Player four will have the four tokens they need to win
		game.getPlayer(3).addToken(Config.BLUE);
		game.getPlayer(3).addToken(Config.PURPLE);
		game.getPlayer(3).addToken(Config.YELLOW);
		game.getPlayer(3).addToken(Config.RED);

		assertEquals(PLAYER_FOUR_NAME, game.checkForWinner());
	}

	@Test
	public void fourPlayersNoWinner() {
		game.setNumPlayers(4);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");
		game.addPlayer(PLAYER_THREE_NAME, Config.BLUE, "");
		game.addPlayer(PLAYER_FOUR_NAME, Config.BLUE, "");

		game.startGame();

		// Player four will have the four tokens they need to win
		game.getPlayer(3).addToken(Config.BLUE);
		game.getPlayer(3).addToken(Config.PURPLE);
		game.getPlayer(3).addToken(Config.YELLOW);

		assertEquals("", game.checkForWinner());
	}

	@Test
	public void playDropWeaponCard() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.overrideTourColour(Config.RED);
		game.getPlayer(0).addCardToHand(DROP_WEAPON_CARD);

		assertEquals("true", game.playCard(0, DROP_WEAPON_CARD.getName()));
		assertEquals(0, game.getPlayer(0).getHandCards().size());
		assertEquals(Config.GREEN, game.getTournamentColour());
		assertEquals(1, game.getDiscardPile().size());
		assertEquals(true, game.getDiscardPile().contains(DROP_WEAPON_CARD));
	}

	@Test
	public void playOutmaneuverTwoPlayersTwoCardsInDisplayNoShield() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.getPlayer(1).addCardToDisplay(MAIDEN_CARD, Config.BLUE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);

		game.getPlayer(0).addCardToHand(OUTMANEUVER_CARD);

		assertEquals("true", game.playCard(0, OUTMANEUVER_CARD.getName()));
		assertEquals(0, game.getPlayer(0).getHandCards().size());
		assertEquals(2, game.getDiscardPile().size());
		assertEquals(true, game.getDiscardPile().contains(OUTMANEUVER_CARD));
		assertEquals(1, game.getPlayer(1).getDisplayCards().size());

		// It should still contain this card
		assertEquals(true,
				game.getPlayer(1).getDisplayCards().contains(MAIDEN_CARD));
		// This card should have gotten removed by the outmaneuver
		assertEquals(false,
				game.getPlayer(1).getDisplayCards().contains(SQUIRE_CARD_2));
	}

	@Test
	public void playOutmaneuverFivePlayersManyCardsInDisplayNoShield() {
		game.setNumPlayers(5);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.YELLOW, "");
		game.addPlayer(PLAYER_THREE_NAME, Config.GREEN, "");
		game.addPlayer(PLAYER_FOUR_NAME, Config.BLUE, "");
		game.addPlayer(PLAYER_FIVE_NAME, Config.PURPLE, "");

		game.getPlayer(1).addCardToDisplay(MAIDEN_CARD, Config.BLUE);

		game.getPlayer(2).addCardToDisplay(MAIDEN_CARD, Config.BLUE);
		game.getPlayer(2).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);

		game.getPlayer(3).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);
		game.getPlayer(3).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);
		game.getPlayer(3).addCardToDisplay(MAIDEN_CARD, Config.BLUE);

		game.getPlayer(4).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);
		game.getPlayer(4).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);
		game.getPlayer(4).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);
		game.getPlayer(4).addCardToDisplay(MAIDEN_CARD, Config.BLUE);

		game.getPlayer(2).addCardToHand(OUTMANEUVER_CARD);

		assertEquals("true", game.playCard(2, OUTMANEUVER_CARD.getName()));
		assertEquals(0, game.getPlayer(0).getHandCards().size());
		assertEquals(3, game.getDiscardPile().size());
		assertEquals(true, game.getDiscardPile().contains(OUTMANEUVER_CARD));

		assertEquals(0, game.getPlayer(0).getDisplayCards().size());
		assertEquals(1, game.getPlayer(1).getDisplayCards().size());
		assertEquals(2, game.getPlayer(2).getDisplayCards().size());
		assertEquals(2, game.getPlayer(3).getDisplayCards().size());
		assertEquals(3, game.getPlayer(4).getDisplayCards().size());

		// It should still contain this card
		assertEquals(true,
				game.getPlayer(3).getDisplayCards().contains(SQUIRE_CARD_2));
		// This card should have gotten removed by the outmaneuver
		assertEquals(false,
				game.getPlayer(3).getDisplayCards().contains(MAIDEN_CARD));
	}

	@Test
	public void playOutmaneuverTwoPlayersOneCardInDisplay() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.getPlayer(1).addCardToDisplay(MAIDEN_CARD, Config.BLUE);

		game.getPlayer(0).addCardToHand(OUTMANEUVER_CARD);

		String result = game.playCard(0, OUTMANEUVER_CARD.getName());
		assertEquals(true, result.contains("false"));
		assertEquals(1, game.getPlayer(0).getHandCards().size());
		assertEquals(0, game.getDiscardPile().size());
		assertEquals(1, game.getPlayer(1).getDisplayCards().size());

		// It should still contain this card
		assertEquals(true,
				game.getPlayer(1).getDisplayCards().contains(MAIDEN_CARD));
	}

	@Test
	public void notAllowedToPlayOutmaneuverWithdrawn() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.getPlayer(1).withdraw();

		game.getPlayer(0).addCardToHand(OUTMANEUVER_CARD);

		String result = game.playCard(0, OUTMANEUVER_CARD.getName());
		assertEquals(true, result.contains("false"));
		assertEquals(1, game.getPlayer(0).getHandCards().size());
		assertEquals(0, game.getDiscardPile().size());
		assertEquals(0, game.getPlayer(1).getDisplayCards().size());
	}

	@Test
	public void playChargeCardTwoPlayersTwoOfLowestValueToRemoveNoShield() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.getPlayer(0).addCardToDisplay(MAIDEN_CARD, Config.BLUE);
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_3, Config.BLUE);
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);

		game.getPlayer(1).addCardToDisplay(MAIDEN_CARD, Config.BLUE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);

		game.getPlayer(1).addCardToHand(CHARGE_CARD);
		String result = game.playCard(1, CHARGE_CARD.getName());

		assertEquals(true, result.contains("actionCardPlayedMessage"));
		assertEquals(0, game.getPlayer(1).getHandCards().size());
		assertEquals(3, game.getDiscardPile().size());
		assertEquals(true, game.getDiscardPile().contains(CHARGE_CARD));
		assertEquals(2, game.getPlayer(0).getDisplayCards().size());
		assertEquals(4, game.getPlayer(1).getDisplayCards().size());

		// It should still contain this card
		assertEquals(true,
				game.getPlayer(0).getDisplayCards().contains(MAIDEN_CARD));
		assertEquals(false,
				game.getPlayer(0).getDisplayCards().contains(SQUIRE_CARD_2));
	}

	@Test
	public void playChargeCardTwoPlayersOnlyOneCardNumber() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.getPlayer(0).addCardToDisplay(BLUE_CARD_3, Config.BLUE);
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_3, Config.BLUE);
		game.getPlayer(0).addCardToDisplay(BLUE_CARD_3, Config.BLUE);
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_3, Config.BLUE);
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_3, Config.BLUE);

		game.getPlayer(1).addCardToDisplay(MAIDEN_CARD, Config.BLUE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);

		game.getPlayer(1).addCardToHand(CHARGE_CARD);
		String result = game.playCard(1, CHARGE_CARD.getName());

		assertEquals(true, result.contains("actionCardPlayedMessage"));
		assertEquals(0, game.getPlayer(1).getHandCards().size());
		assertEquals(5, game.getDiscardPile().size());
		assertEquals(1, game.getPlayer(0).getDisplayCards().size());
		assertEquals(4, game.getPlayer(1).getDisplayCards().size());

		// It should still contain this card
		assertEquals(true,
				game.getPlayer(0).getDisplayCards().contains(BLUE_CARD_3));
		assertEquals(false,
				game.getPlayer(0).getDisplayCards().contains(SQUIRE_CARD_3));
	}

	@Test
	public void tryDropWeaponCardNotAllowed() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.overrideTourColour(Config.PURPLE);
		game.getPlayer(0).addCardToHand(DROP_WEAPON_CARD);

		String result = game.playCard(0, DROP_WEAPON_CARD.getName());
		assertEquals(true, result.contains(result));
		assertEquals(1, game.getPlayer(0).getHandCards().size());
		assertEquals(Config.PURPLE, game.getTournamentColour());
		assertEquals(0, game.getDiscardPile().size());
	}

	@Test
	public void notAllowedToPlayOutmaneuver() {
		game.setNumPlayers(5);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.YELLOW, "");
		game.addPlayer(PLAYER_THREE_NAME, Config.GREEN, "");
		game.addPlayer(PLAYER_FOUR_NAME, Config.BLUE, "");
		game.addPlayer(PLAYER_FIVE_NAME, Config.PURPLE, "");

		game.getPlayer(1).addCardToDisplay(MAIDEN_CARD, Config.BLUE);
		game.getPlayer(2).addCardToDisplay(MAIDEN_CARD, Config.BLUE);
		game.getPlayer(2).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);
		game.getPlayer(4).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);

		game.getPlayer(2).addCardToHand(OUTMANEUVER_CARD);

		String result = game.playCard(2, OUTMANEUVER_CARD.getName());
		assertEquals(true, result.contains("false"));
		assertEquals(0, game.getPlayer(0).getHandCards().size());
		assertEquals(0, game.getDiscardPile().size());

		assertEquals(0, game.getPlayer(0).getDisplayCards().size());
		assertEquals(1, game.getPlayer(1).getDisplayCards().size());
		assertEquals(2, game.getPlayer(2).getDisplayCards().size());
		assertEquals(0, game.getPlayer(3).getDisplayCards().size());
		assertEquals(1, game.getPlayer(4).getDisplayCards().size());
	}

	@Test
	public void notAllowedToPlayCharge() {
		game.setNumPlayers(3);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");
		game.addPlayer(PLAYER_THREE_NAME, Config.PURPLE, "");

		game.getPlayer(0).addCardToDisplay(BLUE_CARD_3, Config.BLUE);

		game.getPlayer(1).addCardToHand(CHARGE_CARD);
		String result = game.playCard(1, CHARGE_CARD.getName());

		assertEquals(true, result.contains("false"));
		assertEquals(1, game.getPlayer(1).getHandCards().size());
		assertEquals(0, game.getDiscardPile().size());
		assertEquals(1, game.getPlayer(0).getDisplayCards().size());
		assertEquals(0, game.getPlayer(2).getDisplayCards().size());
	}

	@Test
	public void notAllowedToPlayChargeBecauseWithdrawn() {
		game.setNumPlayers(3);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");
		game.addPlayer(PLAYER_THREE_NAME, Config.PURPLE, "");

		game.getPlayer(0).addCardToDisplay(BLUE_CARD_3, Config.BLUE);
		game.getPlayer(0).addCardToDisplay(BLUE_CARD_3, Config.BLUE);
		game.getPlayer(0).withdraw();

		game.getPlayer(1).addCardToHand(CHARGE_CARD);
		String result = game.playCard(1, CHARGE_CARD.getName());

		assertEquals(true, result.contains("false"));
		assertEquals(1, game.getPlayer(1).getHandCards().size());
		assertEquals(0, game.getDiscardPile().size());
		assertEquals(0, game.getPlayer(0).getDisplayCards().size());
		assertEquals(0, game.getPlayer(2).getDisplayCards().size());
	}

	@Test
	public void withdrawAPlayerClearsTheirDisplay() {
		game.setNumPlayers(3);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.startGame();
		game.overrideTourColour(Config.BLUE);

		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);

		assertEquals("#", game.withdrawPlayer(0, true));
		assertEquals(true, game.getPlayer(0).getDisplayCards().isEmpty());
		assertEquals(3, game.getDiscardPile().size());
	}

	@Test
	public void startGameWithFullTokenPool() {
		game.setNumPlayers(3);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.startGame();

		assertEquals(25, game.getTokenPool().size());
	}

	@Test
	public void addTokenToPlayer() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.startGame();

		game.addTokenToPlayer(0, Config.BLUE);
		assertEquals(true, game.getPlayer(0).getTokens().contains(Config.BLUE));
		assertEquals(24, game.getTokenPool().size());
	}

	@Test
	public void addTwoOfSameColourTokensToPlayer() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.startGame();

		game.addTokenToPlayer(0, Config.BLUE);
		game.addTokenToPlayer(0, Config.BLUE);
		assertEquals(1, game.getPlayer(0).getTokens().size());
		assertEquals(24, game.getTokenPool().size());
	}

	@Test
	public void addThreeTokensToPlayer() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.startGame();

		game.addTokenToPlayer(0, Config.BLUE);
		game.addTokenToPlayer(0, Config.YELLOW);
		game.addTokenToPlayer(0, Config.GREEN);
		assertEquals(3, game.getPlayer(0).getTokens().size());
		assertEquals(22, game.getTokenPool().size());
	}

	@Test
	public void playCounterChargeCardTwoPlayersTwoOfHighestValueToRemoveNoShield() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.getPlayer(0).addCardToDisplay(MAIDEN_CARD, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_7, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_7, Config.PURPLE);

		game.getPlayer(1).addCardToDisplay(MAIDEN_CARD, Config.BLUE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);

		game.getPlayer(1).addCardToHand(COUNTER_CHARGE_CARD);
		String result = game.playCard(1, COUNTER_CHARGE_CARD.getName());

		assertEquals(true, result.contains("actionCardPlayedMessage"));
		assertEquals(0, game.getPlayer(1).getHandCards().size());
		assertEquals(3, game.getDiscardPile().size());
		assertEquals(true, game.getDiscardPile().contains(COUNTER_CHARGE_CARD));
		assertEquals(1, game.getPlayer(0).getDisplayCards().size());
		assertEquals(3, game.getPlayer(1).getDisplayCards().size());

		// It should still contain this card
		assertEquals(true,
				game.getPlayer(0).getDisplayCards().contains(MAIDEN_CARD));
		assertEquals(false,
				game.getPlayer(0).getDisplayCards().contains(PURPLE_CARD_7));
	}

	@Test
	public void playCounterchargeCardTwoPlayersOnlyOneCardNumber() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_7, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_7, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_7, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_7, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_7, Config.PURPLE);

		game.getPlayer(1).addCardToDisplay(MAIDEN_CARD, Config.BLUE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);

		game.getPlayer(1).addCardToHand(COUNTER_CHARGE_CARD);
		String result = game.playCard(1, COUNTER_CHARGE_CARD.getName());

		assertEquals(true, result.contains("actionCardPlayedMessage"));
		assertEquals(0, game.getPlayer(1).getHandCards().size());
		assertEquals(5, game.getDiscardPile().size());
		assertEquals(1, game.getPlayer(0).getDisplayCards().size());
		assertEquals(4, game.getPlayer(1).getDisplayCards().size());

		// It should still contain this card
		assertEquals(true,
				game.getPlayer(0).getDisplayCards().contains(PURPLE_CARD_7));
	}

	@Test
	public void notAllowedToPlayCountercharge() {
		game.setNumPlayers(3);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");
		game.addPlayer(PLAYER_THREE_NAME, Config.PURPLE, "");

		game.getPlayer(0).addCardToDisplay(BLUE_CARD_3, Config.BLUE);

		game.getPlayer(1).addCardToHand(COUNTER_CHARGE_CARD);
		String result = game.playCard(1, COUNTER_CHARGE_CARD.getName());

		assertEquals(true, result.contains("false"));
		assertEquals(1, game.getPlayer(1).getHandCards().size());
		assertEquals(0, game.getDiscardPile().size());
		assertEquals(1, game.getPlayer(0).getDisplayCards().size());
		assertEquals(0, game.getPlayer(2).getDisplayCards().size());
	}

	@Test
	public void playDisgraceCardNoShield() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.getPlayer(0).addCardToDisplay(MAIDEN_CARD, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_7, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_7, Config.PURPLE);

		game.getPlayer(1).addCardToDisplay(MAIDEN_CARD, Config.BLUE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);

		game.getPlayer(1).addCardToHand(DISGRACE_CARD);
		String result = game.playCard(1, DISGRACE_CARD.getName());

		assertEquals(true, result.contains("actionCardPlayedMessage"));
		assertEquals(0, game.getPlayer(1).getHandCards().size());
		assertEquals(2, game.getDiscardPile().size());
		assertEquals(true, game.getDiscardPile().contains(DISGRACE_CARD));
		assertEquals(2, game.getPlayer(0).getDisplayCards().size());
		assertEquals(3, game.getPlayer(1).getDisplayCards().size());

		// It should still contain this card
		assertEquals(true,
				game.getPlayer(0).getDisplayCards().contains(PURPLE_CARD_7));
		assertEquals(false,
				game.getPlayer(0).getDisplayCards().contains(MAIDEN_CARD));
	}

	@Test
	public void notAllowedToPlayDisgraceCardTheyHaveNoSupporters() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_7, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_7, Config.PURPLE);

		game.getPlayer(1).addCardToDisplay(MAIDEN_CARD, Config.BLUE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);

		game.getPlayer(1).addCardToHand(DISGRACE_CARD);
		String result = game.playCard(1, DISGRACE_CARD.getName());

		assertEquals(true, result.contains("false"));
		assertEquals(1, game.getPlayer(1).getHandCards().size());
		assertEquals(0, game.getDiscardPile().size());
		assertEquals(2, game.getPlayer(0).getDisplayCards().size());
		assertEquals(3, game.getPlayer(1).getDisplayCards().size());

		// It should still contain this card
		assertEquals(true,
				game.getPlayer(0).getDisplayCards().contains(PURPLE_CARD_7));
	}

	@Test
	public void playDisgraceCardAllSupportersNoShield() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.getPlayer(0).addCardToDisplay(MAIDEN_CARD, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_2, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_3, Config.PURPLE);

		game.getPlayer(1).addCardToDisplay(MAIDEN_CARD, Config.BLUE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);

		game.getPlayer(1).addCardToHand(DISGRACE_CARD);
		String result = game.playCard(1, DISGRACE_CARD.getName());

		assertEquals(true, result.contains("actionCardPlayedMessage"));
		assertEquals(0, game.getPlayer(1).getHandCards().size());
		assertEquals(3, game.getDiscardPile().size());
		assertEquals(1, game.getPlayer(0).getDisplayCards().size());
		assertEquals(3, game.getPlayer(1).getDisplayCards().size());

		// It should still contain this card
		assertEquals(true,
				game.getPlayer(0).getDisplayCards().contains(MAIDEN_CARD));
		assertEquals(false,
				game.getPlayer(0).getDisplayCards().contains(SQUIRE_CARD_2));
	}

	@Test
	public void twoTournaments() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.startGame();

		game.withdrawPlayer(0, true);

		assertEquals(false, game.getPlayers()[0].getHandCards().isEmpty());
		assertEquals(false, game.getTokenPool().isEmpty());
		assertEquals(2, game.getTournamentNumber());
	}

	@Test
	public void playerUnableToStartTournament() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, ""); // jack
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.getPlayer(0).addCardToHand(new ActionCard("Charge"));
		game.getPlayer(1).addCardToHand(new SupporterCard("", 0));

		// This means that player 0 will win and is supposed to start the next
		// tournament
		game.withdrawPlayer(1, true);

		assertEquals(PLAYER_TWO_NAME, game.getCurrentPlayer().getName());
	}

	@Test
	public void drawPileRefillsWhenEmpty() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.getDiscardPile().add(new ActionCard(""));
		game.getDiscardPile().add(new ActionCard(""));
		game.getDiscardPile().add(new ActionCard(""));
		game.getDiscardPile().add(new ActionCard(""));

		game.getDrawPile().clearCards();

		// Add cards to the draw pile
		game.getDrawPile().addCard(new SupporterCard("", 0));
		game.getDrawPile().addCard(new SupporterCard("", 0));

		// Empty the draw pile
		// Draw pile should refill with the cards from the discard pile
		game.drawCard(0);
		game.drawCard(0);

		assertEquals(4, game.getDrawPile().getNumCards());
	}

	@Test
	public void tryPlayingRiposteCardMoreInfoNeeded() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.getPlayer(1).addCardToDisplay(MAIDEN_CARD, Config.BLUE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);

		game.getPlayer(0).addCardToDisplay(MAIDEN_CARD, Config.BLUE);
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);

		game.getPlayer(0).addCardToHand(RIPOSTE_CARD);

		String result = game.playCard(0, RIPOSTE_CARD.getName());
		assertEquals(true, result.contains("moreInformationNeeded"));
		assertEquals(2, game.getPlayer(1).getDisplayCards().size());
		assertEquals(0, game.getDiscardPile().size());
		assertEquals(2, game.getPlayer(0).getDisplayCards().size());
	}

	@Test
	public void playRiposteCardTwoPlayersNoShield() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.getPlayer(1).addCardToDisplay(MAIDEN_CARD, Config.BLUE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);

		game.getPlayer(0).addCardToDisplay(MAIDEN_CARD, Config.BLUE);
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);

		game.getPlayer(0).addCardToHand(RIPOSTE_CARD);

		// Player 0 will be taking the last card from player 1s display
		String info = "Riposte@" + PLAYER_TWO_NAME;
		game.playActionCardWithAdditionalInfo(0, info);

		assertEquals(1, game.getPlayer(1).getDisplayCards().size());
		assertEquals(1, game.getDiscardPile().size());
		assertEquals(true, game.getDiscardPile().contains(RIPOSTE_CARD));
		assertEquals(3, game.getPlayer(0).getDisplayCards().size());
	}

	@Test
	public void notAllowedToPlayRiposteCard() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.getPlayer(1).addCardToDisplay(MAIDEN_CARD, Config.BLUE);

		game.getPlayer(0).addCardToDisplay(MAIDEN_CARD, Config.BLUE);
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);

		game.getPlayer(0).addCardToHand(RIPOSTE_CARD);

		String result = game.playCard(0, RIPOSTE_CARD.getName());
		assertEquals(true, result.contains("false"));
		assertEquals(1, game.getPlayer(1).getDisplayCards().size());
		assertEquals(0, game.getDiscardPile().size());
		assertEquals(2, game.getPlayer(0).getDisplayCards().size());
	}

	@Test
	public void tryPlayingUnhorseMoreInfoNeeded() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");
		game.overrideTourColour(Config.PURPLE);

		game.getPlayer(0).addCardToHand(UNHORSE_CARD);

		String result = game.playCard(0, UNHORSE_CARD.getName());
		assertEquals(true, result.contains("moreInformationNeeded"));
		assertEquals(0, game.getDiscardPile().size());
		assertEquals(Config.PURPLE, game.getTournamentColour());
	}

	@Test
	public void notAllowedToPlayUnhorse() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");
		game.overrideTourColour(Config.BLUE);

		game.getPlayer(0).addCardToHand(UNHORSE_CARD);

		String result = game.playCard(0, UNHORSE_CARD.getName());
		assertEquals(true, result.contains("false"));
		assertEquals(0, game.getDiscardPile().size());
		assertEquals(Config.BLUE, game.getTournamentColour());
	}

	@Test
	public void playUnhorseFromPurpleToYellow() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");
		game.overrideTourColour(Config.PURPLE);

		game.getPlayer(0).addCardToHand(UNHORSE_CARD);

		String info = "Unhorse@" + Config.YELLOW;
		game.playActionCardWithAdditionalInfo(0, info);

		assertEquals(1, game.getDiscardPile().size());
		assertEquals(true, game.getDiscardPile().contains(UNHORSE_CARD));
		assertEquals(Config.YELLOW, game.getTournamentColour());
	}

	@Test
	public void tryPlayingChangeWeaponMoreInfoNeeded() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");
		game.overrideTourColour(Config.RED);

		game.getPlayer(0).addCardToHand(CHANGE_WEAPON_CARD);

		String result = game.playCard(0, CHANGE_WEAPON_CARD.getName());
		assertEquals(true, result.contains("moreInformationNeeded"));
		assertEquals(0, game.getDiscardPile().size());
		assertEquals(Config.RED, game.getTournamentColour());
	}

	@Test
	public void playChangeWeapon() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");
		game.overrideTourColour(Config.PURPLE);

		game.getPlayer(0).addCardToHand(CHANGE_WEAPON_CARD);

		String info = CHANGE_WEAPON_CARD.getName() + "@" + Config.YELLOW;
		game.playActionCardWithAdditionalInfo(0, info);
		assertEquals(1, game.getDiscardPile().size());
		assertEquals(true, game.getDiscardPile().contains(CHANGE_WEAPON_CARD));
		assertEquals(Config.YELLOW, game.getTournamentColour());
	}

	@Test
	public void notAllowedToPlayChangeWeapon() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");
		game.overrideTourColour(Config.PURPLE);

		game.getPlayer(0).addCardToHand(CHANGE_WEAPON_CARD);

		String result = game.playCard(0, CHANGE_WEAPON_CARD.getName());
		assertEquals(true, result.contains("false"));
		assertEquals(0, game.getDiscardPile().size());
		assertEquals(Config.PURPLE, game.getTournamentColour());
	}

	@Test
	public void notAllowedToPlayBreakLance() {
		game.setNumPlayers(3);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");
		game.addPlayer(PLAYER_THREE_NAME, Config.BLUE, "");

		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);

		game.getPlayer(2).addCardToDisplay(SQUIRE_CARD_2, Config.PURPLE);
		game.getPlayer(2).addCardToDisplay(SQUIRE_CARD_3, Config.PURPLE);

		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);

		game.getPlayer(0).addCardToHand(BREAK_LANCE_CARD);

		String result = game.playCard(0, BREAK_LANCE_CARD.getName());
		assertEquals(true, result.contains("false"));
		assertEquals(0, game.getDiscardPile().size());
		assertEquals(1, game.getPlayer(0).getHandCards().size());
		assertEquals(1, game.getPlayer(1).getDisplayCards().size());
		assertEquals(2, game.getPlayer(2).getDisplayCards().size());
	}

	@Test
	public void tryPlayingBreakLanceMoreInfoNeeded() {
		game.setNumPlayers(3);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");
		game.addPlayer(PLAYER_THREE_NAME, Config.BLUE, "");

		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);

		game.getPlayer(2).addCardToDisplay(SQUIRE_CARD_2, Config.PURPLE);
		game.getPlayer(2).addCardToDisplay(SQUIRE_CARD_3, Config.PURPLE);

		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);

		game.getPlayer(0).addCardToHand(BREAK_LANCE_CARD);

		String result = game.playCard(0, BREAK_LANCE_CARD.getName());
		assertEquals(true, result.contains("moreInformationNeeded"));
		assertEquals(0, game.getDiscardPile().size());
		assertEquals(1, game.getPlayer(0).getHandCards().size());
	}

	@Test
	public void playBreakLanceNoShield() {
		game.setNumPlayers(3);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");
		game.addPlayer(PLAYER_THREE_NAME, Config.BLUE, "");

		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);

		game.getPlayer(2).addCardToDisplay(PURPLE_CARD_7, Config.PURPLE);
		game.getPlayer(2).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(2).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(2).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);

		game.getPlayer(0).addCardToHand(BREAK_LANCE_CARD);

		String info = "Break Lance@" + PLAYER_THREE_NAME;
		game.playActionCardWithAdditionalInfo(0, info);
		assertEquals(4, game.getDiscardPile().size());
		assertEquals(true, game.getDiscardPile().contains(BREAK_LANCE_CARD));
		assertEquals(0, game.getPlayer(0).getHandCards().size());
		assertEquals(3, game.getPlayer(1).getDisplayCards().size());
		assertEquals(1, game.getPlayer(2).getDisplayCards().size());
		assertEquals(true,
				game.getPlayer(2).getDisplayCards().contains(PURPLE_CARD_7));
		assertEquals(false,
				game.getPlayer(2).getDisplayCards().contains(PURPLE_CARD_3));
	}

	@Test
	public void tryPlayingDodgeMoreInfoNeeded() {
		game.setNumPlayers(3);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");
		game.addPlayer(PLAYER_THREE_NAME, Config.BLUE, "");

		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.PURPLE);

		game.getPlayer(2).addCardToDisplay(SQUIRE_CARD_2, Config.PURPLE);
		game.getPlayer(2).addCardToDisplay(SQUIRE_CARD_2, Config.PURPLE);

		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);

		game.getPlayer(0).addCardToHand(DODGE_CARD);

		String result = game.playCard(0, DODGE_CARD.getName());
		assertEquals(true, result.contains("moreInformationNeeded"));
		assertEquals(0, game.getDiscardPile().size());
		assertEquals(1, game.getPlayer(0).getHandCards().size());
	}

	@Test
	public void notAllowedToPlayDodge() {
		game.setNumPlayers(3);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");
		game.addPlayer(PLAYER_THREE_NAME, Config.BLUE, "");

		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);

		game.getPlayer(0).addCardToHand(DODGE_CARD);

		String result = game.playCard(0, DODGE_CARD.getName());
		assertEquals(true, result.contains("false"));
		assertEquals(0, game.getDiscardPile().size());
		assertEquals(1, game.getPlayer(0).getHandCards().size());
	}

	@Test
	public void playDodgeNoShield() {
		game.setNumPlayers(3);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");
		game.addPlayer(PLAYER_THREE_NAME, Config.BLUE, "");

		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.PURPLE);

		game.getPlayer(2).addCardToDisplay(SQUIRE_CARD_2, Config.PURPLE);
		game.getPlayer(2).addCardToDisplay(SQUIRE_CARD_2, Config.PURPLE);

		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);

		game.getPlayer(0).addCardToHand(DODGE_CARD);

		String info = "Dodge@" + PLAYER_TWO_NAME + ","
				+ PURPLE_CARD_3.getName();
		game.playActionCardWithAdditionalInfo(0, info);

		assertEquals(2, game.getDiscardPile().size());
		assertEquals(true, game.getDiscardPile().contains(DODGE_CARD));
		assertEquals(0, game.getPlayer(0).getHandCards().size());
		assertEquals(2, game.getPlayer(0).getDisplayCards().size());
		assertEquals(1, game.getPlayer(1).getDisplayCards().size());
		assertEquals(2, game.getPlayer(2).getDisplayCards().size());
		assertEquals(true,
				game.getPlayer(1).getDisplayCards().contains(SQUIRE_CARD_2));
		assertEquals(false,
				game.getPlayer(1).getDisplayCards().contains(PURPLE_CARD_3));
	}

	@Test
	public void tryPlayingRetreatMoreInfoNeeded() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.PURPLE);

		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);

		game.getPlayer(0).addCardToHand(RETREAT_CARD);

		String result = game.playCard(0, RETREAT_CARD.getName());
		assertEquals(true, result.contains("moreInformationNeeded"));
		assertEquals(0, game.getDiscardPile().size());
		assertEquals(1, game.getPlayer(0).getHandCards().size());
	}

	@Test
	public void notAllowedToPlayRetreat() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.PURPLE);

		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);

		game.getPlayer(0).addCardToHand(RETREAT_CARD);

		String result = game.playCard(0, RETREAT_CARD.getName());
		assertEquals(true, result.contains("false"));
		assertEquals(0, game.getDiscardPile().size());
		assertEquals(1, game.getPlayer(0).getHandCards().size());
	}

	@Test
	public void playRetreatCardNoShield() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.PURPLE);

		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_2, Config.PURPLE);

		game.getPlayer(0).addCardToHand(RETREAT_CARD);

		String info = "Retreat@" + SQUIRE_CARD_2.getName();
		game.playActionCardWithAdditionalInfo(0, info);
		assertEquals(1, game.getDiscardPile().size());
		assertEquals(true, game.getDiscardPile().contains(RETREAT_CARD));
		assertEquals(1, game.getPlayer(0).getHandCards().size());
		assertEquals(1, game.getPlayer(0).getDisplayCards().size());
		assertEquals(true,
				game.getPlayer(0).getDisplayCards().contains(PURPLE_CARD_3));
		assertEquals(false,
				game.getPlayer(0).getDisplayCards().contains(SQUIRE_CARD_2));
		assertEquals(false,
				game.getPlayer(0).getHandCards().contains(PURPLE_CARD_3));
		assertEquals(true,
				game.getPlayer(0).getHandCards().contains(SQUIRE_CARD_2));
		assertEquals(2, game.getPlayer(1).getDisplayCards().size());
	}

	@Test
	public void tryPlayingKnockDownMoreInfoNeeded() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.getPlayer(1).addCardToHand(PURPLE_CARD_3);
		game.getPlayer(1).addCardToHand(SQUIRE_CARD_2);

		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);

		game.getPlayer(0).addCardToHand(KNOCK_DOWN_CARD);

		String result = game.playCard(0, KNOCK_DOWN_CARD.getName());
		assertEquals(true, result.contains("moreInformationNeeded"));
		assertEquals(0, game.getDiscardPile().size());
		assertEquals(1, game.getPlayer(0).getHandCards().size());
	}

	@Test
	public void notAllowedToPlayKnockDown() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);

		game.getPlayer(0).addCardToHand(KNOCK_DOWN_CARD);

		String result = game.playCard(0, KNOCK_DOWN_CARD.getName());
		assertEquals(true, result.contains("false"));
		assertEquals(0, game.getDiscardPile().size());
		assertEquals(1, game.getPlayer(0).getHandCards().size());
	}

	@Test
	public void playKnockDownNoShield() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.getPlayer(1).addCardToHand(PURPLE_CARD_3);
		game.getPlayer(1).addCardToHand(SQUIRE_CARD_2);

		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);

		game.getPlayer(0).addCardToHand(KNOCK_DOWN_CARD);

		String info = "Knock Down@" + PLAYER_TWO_NAME;
		game.playActionCardWithAdditionalInfo(0, info);
		assertEquals(1, game.getDiscardPile().size());
		assertEquals(true, game.getDiscardPile().contains(KNOCK_DOWN_CARD));
		assertEquals(1, game.getPlayer(0).getHandCards().size());
		assertEquals(1, game.getPlayer(1).getHandCards().size());
	}

	@Test
	public void tryPlayingOutwitMoreInfoNeeded() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.PURPLE);

		game.getPlayer(0).addSpecialCard(SHIELD_CARD);
		game.getPlayer(0).addSpecialCard(STUNNED_CARD);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);

		game.getPlayer(0).addCardToHand(OUTWIT_CARD);

		String result = game.playCard(0, OUTWIT_CARD.getName());
		assertEquals(true, result.contains("moreInformationNeeded"));
		assertEquals(0, game.getDiscardPile().size());
		assertEquals(1, game.getPlayer(0).getHandCards().size());
	}

	@Test
	public void notAllowedToPlayOutwit() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);

		game.getPlayer(0).addCardToHand(OUTWIT_CARD);

		String result = game.playCard(0, OUTWIT_CARD.getName());
		assertEquals(true, result.contains("false"));
		assertEquals(0, game.getDiscardPile().size());
		assertEquals(1, game.getPlayer(0).getHandCards().size());
	}

	@Test
	public void playOutwitSwitchingDisplayForShield() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.getPlayer(1).addSpecialCard(SHIELD_CARD);

		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);

		game.getPlayer(0).addCardToHand(OUTWIT_CARD);

		String info = "Outwit@" + PURPLE_CARD_3.getName() + ","
				+ PLAYER_TWO_NAME + "," + SHIELD_CARD.getName();
		game.playActionCardWithAdditionalInfo(0, info);
		assertEquals(1, game.getDiscardPile().size());
		assertEquals(true, game.getDiscardPile().contains(OUTWIT_CARD));
		assertEquals(true,
				game.getPlayer(1).getDisplayCards().contains(PURPLE_CARD_3));
		assertEquals(false,
				game.getPlayer(0).getDisplayCards().contains(PURPLE_CARD_3));

		assertEquals(true,
				game.getPlayer(0).hasSpecialCard(SHIELD_CARD.getName()));
		assertEquals(false,
				game.getPlayer(1).hasSpecialCard(SHIELD_CARD.getName()));
	}

	@Test
	public void playShieldCard() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.getPlayer(0).addCardToHand(SHIELD_CARD);
		game.getPlayer(0).addCardToHand(BLUE_CARD_3);
		game.getPlayer(0).addCardToHand(BREAK_LANCE_CARD);

		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_7, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_3, Config.PURPLE);

		game.getPlayer(1).addCardToHand(BLUE_CARD_3);
		game.getPlayer(1).addCardToHand(BREAK_LANCE_CARD);

		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_7, Config.PURPLE);
		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);

		game.playCard(0, "Shield");
		// Now action cards played on player 0 should have no affect anymore

		assertEquals(true, game.getPlayer(0).hasSpecialCard("Shield"));

		game.getPlayer(1).addCardToHand(OUTMANEUVER_CARD);
		String result = game.playCard(1, OUTMANEUVER_CARD.getName());
		assertEquals(true, result.contains("false"));
		assertEquals(3, game.getPlayer(0).getDisplayCards().size());

		game.getPlayer(1).addCardToHand(CHARGE_CARD);
		result = game.playCard(1, CHARGE_CARD.getName());
		assertEquals(true, result.contains("false"));
		assertEquals(3, game.getPlayer(0).getDisplayCards().size());

		game.getPlayer(1).addCardToHand(COUNTER_CHARGE_CARD);
		result = game.playCard(1, COUNTER_CHARGE_CARD.getName());
		assertEquals(true, result.contains("false"));
		assertEquals(3, game.getPlayer(0).getDisplayCards().size());

		game.getPlayer(1).addCardToHand(DISGRACE_CARD);
		result = game.playCard(1, DISGRACE_CARD.getName());
		assertEquals(true, result.contains("false"));
		assertEquals(3, game.getPlayer(0).getDisplayCards().size());

		game.getPlayer(1).addCardToHand(RIPOSTE_CARD);
		result = game.playCard(1, RIPOSTE_CARD.getName());
		assertEquals(false, result.contains(PLAYER_ONE_NAME));

		game.getPlayer(1).addCardToHand(BREAK_LANCE_CARD);
		result = game.playCard(1, BREAK_LANCE_CARD.getName());
		assertEquals(false, result.contains(PLAYER_ONE_NAME));

		game.getPlayer(1).addCardToHand(DODGE_CARD);
		result = game.playCard(1, DODGE_CARD.getName());
		assertEquals(false, result.contains(PLAYER_ONE_NAME));

		game.getPlayer(0).addCardToHand(RETREAT_CARD);
		result = game.playCard(0, RETREAT_CARD.getName());
		assertEquals(true, result.contains("false"));

		game.getPlayer(1).addCardToHand(KNOCK_DOWN_CARD);
		result = game.playCard(1, KNOCK_DOWN_CARD.getName());
		assertEquals(true, result.contains("false"));

		game.getPlayer(1).addCardToHand(ADAPT_CARD);
		result = game.playCard(1, KNOCK_DOWN_CARD.getName());
		assertEquals(true, result.contains("false"));
	}

	@Test
	public void withdrawPlayerRemovesShieldAndStunned() {
		game.setNumPlayers(3);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.startGame();
		game.overrideTourColour(Config.BLUE);

		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);
		game.getPlayer(0).addSpecialCard(SHIELD_CARD);
		game.getPlayer(0).addSpecialCard(STUNNED_CARD);

		assertEquals("#", game.withdrawPlayer(0, true));
		assertEquals(true, game.getPlayer(0).getDisplayCards().isEmpty());
		assertEquals(false, game.getPlayer(0).hasSpecialCard("Shield"));
		assertEquals(false, game.getPlayer(0).hasSpecialCard("Stunned"));
		assertEquals(5, game.getDiscardPile().size());
	}

	public void tryPlayingAdaptMoreInfoNeeded() {
		game.setNumPlayers(3);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");
		game.addPlayer(PLAYER_THREE_NAME, Config.PURPLE, "");

		game.getPlayer(2).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(2).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(2).addCardToDisplay(SQUIRE_CARD_2, Config.PURPLE);
		game.getPlayer(2).addCardToDisplay(SQUIRE_CARD_2, Config.PURPLE);

		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.PURPLE);

		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);

		game.getPlayer(0).addCardToHand(ADAPT_CARD);

		String result = game.playCard(0, ADAPT_CARD.getName());
		assertEquals(true, result.contains("adaptNeedMoreInfo"));
		assertEquals(1, game.getDiscardPile().size());
		assertEquals(0, game.getPlayer(0).getHandCards().size());
	}

	@Test
	public void notAllowedToPlayAdapt() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_7, Config.PURPLE);

		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);

		game.getPlayer(0).addCardToHand(ADAPT_CARD);

		String result = game.playCard(0, ADAPT_CARD.getName());

		assertEquals(true, result.contains("false"));
		assertEquals(0, game.getDiscardPile().size());
		assertEquals(1, game.getPlayer(0).getHandCards().size());
	}

	@Test
	public void playAdaptNoShield() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_3, Config.PURPLE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.PURPLE);
		game.getPlayer(1).addCardToDisplay(GREEN_CARD_1, Config.GREEN);
		game.getPlayer(1).addCardToDisplay(GREEN_CARD_1, Config.GREEN);

		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_3, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);

		game.adaptCardsChosen(1, "1-" + GREEN_CARD_1.getName() + ",3-"
				+ SQUIRE_CARD_3.getName());

		game.getPlayer(1).addCardToHand(ADAPT_CARD);
		game.playCard(1, ADAPT_CARD.getName());
		game.adaptCardsChosen(0, "3-" + PURPLE_CARD_3.getName());

		assertEquals(6, game.getDiscardPile().size());
		assertEquals(true, game.getDiscardPile().contains(ADAPT_CARD));

		assertEquals(1, game.getPlayer(0).getDisplayCards().size());
		assertEquals(false,
				game.getPlayer(0).getDisplayCards().contains(SQUIRE_CARD_3));
		assertEquals(true,
				game.getPlayer(0).getDisplayCards().contains(PURPLE_CARD_3));

		assertEquals(3, game.getPlayer(1).getDisplayCards().size());
		assertEquals(false,
				game.getPlayer(1).getDisplayCards().contains(PURPLE_CARD_3));
		assertEquals(true,
				game.getPlayer(1).getDisplayCards().contains(SQUIRE_CARD_3));
		assertEquals(true,
				game.getPlayer(1).getDisplayCards().contains(SQUIRE_CARD_2));
		assertEquals(true,
				game.getPlayer(1).getDisplayCards().contains(GREEN_CARD_1));
	}

	@Test
	public void tryPlayingStunnedCard() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.getPlayer(0).addCardToHand(STUNNED_CARD);
		game.getPlayer(0).addCardToHand(BLUE_CARD_3);
		game.getPlayer(0).addCardToHand(BREAK_LANCE_CARD);

		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_7, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_3, Config.PURPLE);

		game.getPlayer(1).addCardToHand(BLUE_CARD_3);
		game.getPlayer(1).addCardToHand(SQUIRE_CARD_3);
		game.getPlayer(1).addCardToHand(SQUIRE_CARD_2);

		String result = game.playCard(0, "Stunned");
		assertEquals(true, result.contains("moreInformationNeeded"));
	}

	@Test
	public void playStunnedCard() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.getPlayer(0).addCardToHand(STUNNED_CARD);
		game.getPlayer(0).addCardToHand(BLUE_CARD_3);
		game.getPlayer(0).addCardToHand(BREAK_LANCE_CARD);

		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_7, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_3, Config.PURPLE);

		game.getPlayer(1).addCardToHand(BLUE_CARD_3);
		game.getPlayer(1).addCardToHand(SQUIRE_CARD_3);
		game.getPlayer(1).addCardToHand(SQUIRE_CARD_2);

		String info = "Stunned@" + PLAYER_TWO_NAME;
		game.playActionCardWithAdditionalInfo(0, info);
		// Now player 1 can't add more than one card to their display
		assertEquals(2, game.getPlayer(0).getHandCards().size());

		game.overrideTourColour(Config.BLUE);
		game.playCard(1, BLUE_CARD_3.getName());
		game.playCard(1, SQUIRE_CARD_3.getName());
		game.playCard(1, SQUIRE_CARD_2.getName());

		assertEquals(true, game.getPlayer(1).hasSpecialCard("Stunned"));
		assertEquals(2, game.getPlayer(1).getHandCards().size());
		assertEquals(1, game.getPlayer(1).getDisplayCards().size());

		game.goToNextPlayer(false);
		game.goToNextPlayer(false);

		game.playCard(1, SQUIRE_CARD_3.getName());
		assertEquals(1, game.getPlayer(1).getHandCards().size());
		assertEquals(2, game.getPlayer(1).getDisplayCards().size());
	}

	@Test
	public void withdrawPlayerGoesToTheCorrectPlayer() {
		game.setNumPlayers(4);
		game.addPlayer(PLAYER_ONE_NAME, Config.PURPLE, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.RED, "");
		game.addPlayer(PLAYER_THREE_NAME, Config.BLUE, "");
		game.addPlayer(PLAYER_FOUR_NAME, Config.YELLOW, "");

		game.startGame();
		game.withdrawPlayer(1, true);

		assertEquals(PLAYER_THREE_NAME, game.getCurrentPlayer().getName());
	}

	@Test
	public void playDropWeaponCardAskForIvanhoe() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.overrideTourColour(Config.RED);
		game.getPlayer(0).addCardToHand(DROP_WEAPON_CARD);

		game.getPlayer(1).addCardToHand(IVANHOE_CARD);

		String result = game.playCard(0, DROP_WEAPON_CARD.getName());
		assertEquals(true, result.contains("Ivanhoe"));
		assertEquals(1, game.getPlayer(0).getHandCards().size());
		assertEquals(0, game.getDiscardPile().size());
	}

	@Test
	public void noToIvanhoeDropWeaponGetsPlayed() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.overrideTourColour(Config.RED);
		game.getPlayer(0).addCardToHand(DROP_WEAPON_CARD);

		game.getPlayer(1).addCardToHand(IVANHOE_CARD);

		String info = "No=" + DROP_WEAPON_CARD + "=0";
		game.processIvanhoeCard(info);

		assertEquals(1, game.getPlayer(1).getHandCards().size());
		assertEquals(0, game.getPlayer(0).getHandCards().size());
		assertEquals(Config.GREEN, game.getTournamentColour());
		assertEquals(1, game.getDiscardPile().size());
	}

	@Test
	public void playKnockDownCardAskForIvanhoe() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.getPlayer(1).addCardToHand(PURPLE_CARD_3);
		game.getPlayer(1).addCardToHand(SQUIRE_CARD_2);

		game.getPlayer(0).addCardToHand(IVANHOE_CARD);

		String info = KNOCK_DOWN_CARD.getName() + "@" + PLAYER_TWO_NAME;

		String result = game.checkForIvanhoeAdditionalInfoCard(0, info);
		assertEquals(true, result.contains("Ivanhoe"));
		assertEquals(0, game.getDiscardPile().size());
		assertEquals(1, game.getPlayer(0).getHandCards().size());
		assertEquals(2, game.getPlayer(1).getHandCards().size());
	}

	@Test
	public void noToIvanhoeKnockDownGetsPlayed() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.getPlayer(1).addCardToHand(PURPLE_CARD_3);
		game.getPlayer(1).addCardToHand(SQUIRE_CARD_2);
		game.getPlayer(1).addCardToHand(IVANHOE_CARD);

		game.getPlayer(0).addCardToHand(KNOCK_DOWN_CARD);

		String info = "No=" + KNOCK_DOWN_CARD.getName() + "=0="
				+ PLAYER_TWO_NAME;
		game.processIvanhoeCard(info);

		assertEquals(1, game.getDiscardPile().size());
		assertEquals(1, game.getPlayer(0).getHandCards().size());
		assertEquals(2, game.getPlayer(1).getHandCards().size());
	}

	@Test
	public void yesToIvanhoeUnhorseGetsDiscarded() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.overrideTourColour(Config.PURPLE);
		game.getPlayer(0).addCardToHand(UNHORSE_CARD);

		game.getPlayer(1).addCardToHand(IVANHOE_CARD);

		String info = "Yes=" + UNHORSE_CARD.getName() + "=0";
		game.processIvanhoeCard(info);

		assertEquals(0, game.getPlayer(1).getHandCards().size());
		assertEquals(0, game.getPlayer(0).getHandCards().size());
		assertEquals(Config.PURPLE, game.getTournamentColour());
		assertEquals(2, game.getDiscardPile().size());
		assertEquals(true, game.getDiscardPile().contains(IVANHOE_CARD));
		assertEquals(true, game.getDiscardPile().contains(UNHORSE_CARD));
	}

	@Test
	public void yesToIvanhoeChangeWeaponGetsDiscarded() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.overrideTourColour(Config.RED);
		game.getPlayer(0).addCardToHand(CHANGE_WEAPON_CARD);

		game.getPlayer(1).addCardToHand(IVANHOE_CARD);

		String info = "Yes=" + CHANGE_WEAPON_CARD.getName() + "=0";
		game.processIvanhoeCard(info);

		assertEquals(0, game.getPlayer(1).getHandCards().size());
		assertEquals(0, game.getPlayer(0).getHandCards().size());
		assertEquals(Config.RED, game.getTournamentColour());
		assertEquals(2, game.getDiscardPile().size());
		assertEquals(true, game.getDiscardPile().contains(IVANHOE_CARD));
		assertEquals(true, game.getDiscardPile().contains(CHANGE_WEAPON_CARD));
	}

	@Test
	public void yesToIvanhoeDropWeaponGetsDiscarded() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.overrideTourColour(Config.RED);
		game.getPlayer(0).addCardToHand(DROP_WEAPON_CARD);

		game.getPlayer(1).addCardToHand(IVANHOE_CARD);

		String info = "Yes=" + DROP_WEAPON_CARD.getName() + "=0";
		game.processIvanhoeCard(info);

		assertEquals(0, game.getPlayer(1).getHandCards().size());
		assertEquals(0, game.getPlayer(0).getHandCards().size());
		assertEquals(Config.RED, game.getTournamentColour());
		assertEquals(2, game.getDiscardPile().size());
		assertEquals(true, game.getDiscardPile().contains(IVANHOE_CARD));
		assertEquals(true, game.getDiscardPile().contains(DROP_WEAPON_CARD));
	}

	@Test
	public void yesToIvanhoeShieldGetsDiscarded() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.overrideTourColour(Config.RED);
		game.getPlayer(0).addCardToHand(SHIELD_CARD);

		game.getPlayer(1).addCardToHand(IVANHOE_CARD);

		String info = "Yes=" + SHIELD_CARD.getName() + "=0";
		game.processIvanhoeCard(info);

		assertEquals(0, game.getPlayer(1).getHandCards().size());
		assertEquals(0, game.getPlayer(0).getHandCards().size());
		assertEquals(Config.RED, game.getTournamentColour());
		assertEquals(2, game.getDiscardPile().size());
		assertEquals(true, game.getDiscardPile().contains(IVANHOE_CARD));
		assertEquals(true, game.getDiscardPile().contains(SHIELD_CARD));
	}

	@Test
	public void yesToIvanhoeStunnedGetsDiscarded() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.overrideTourColour(Config.RED);
		game.getPlayer(0).addCardToHand(STUNNED_CARD);

		game.getPlayer(1).addCardToHand(IVANHOE_CARD);

		String info = "Yes=" + STUNNED_CARD.getName() + "=0";
		game.processIvanhoeCard(info);

		assertEquals(0, game.getPlayer(1).getHandCards().size());
		assertEquals(0, game.getPlayer(0).getHandCards().size());
		assertEquals(Config.RED, game.getTournamentColour());
		assertEquals(2, game.getDiscardPile().size());
		assertEquals(true, game.getDiscardPile().contains(IVANHOE_CARD));
		assertEquals(true, game.getDiscardPile().contains(STUNNED_CARD));
	}

	@Test
	public void yesToIvanhoeBreakLanceGetsDiscarded() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.overrideTourColour(Config.RED);
		game.getPlayer(0).addCardToHand(BREAK_LANCE_CARD);

		game.getPlayer(1).addCardToHand(IVANHOE_CARD);
		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_7, Config.PURPLE);
		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_7, Config.PURPLE);

		String info = "Yes=" + BREAK_LANCE_CARD.getName() + "=0";
		game.processIvanhoeCard(info);

		assertEquals(0, game.getPlayer(1).getHandCards().size());
		assertEquals(0, game.getPlayer(0).getHandCards().size());
		assertEquals(Config.RED, game.getTournamentColour());
		assertEquals(2, game.getDiscardPile().size());
		assertEquals(true, game.getDiscardPile().contains(IVANHOE_CARD));
		assertEquals(true, game.getDiscardPile().contains(BREAK_LANCE_CARD));
	}

	@Test
	public void yesToIvanhoeRiposteGetsDiscarded() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.overrideTourColour(Config.RED);
		game.getPlayer(0).addCardToHand(RIPOSTE_CARD);

		game.getPlayer(1).addCardToHand(IVANHOE_CARD);
		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_7, Config.PURPLE);
		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_7, Config.PURPLE);

		String info = "Yes=" + RIPOSTE_CARD.getName() + "=0";
		game.processIvanhoeCard(info);

		assertEquals(0, game.getPlayer(1).getHandCards().size());
		assertEquals(0, game.getPlayer(0).getHandCards().size());
		assertEquals(2, game.getPlayer(1).getDisplayCards().size());
		assertEquals(0, game.getPlayer(0).getDisplayCards().size());
		assertEquals(Config.RED, game.getTournamentColour());
		assertEquals(2, game.getDiscardPile().size());
		assertEquals(true, game.getDiscardPile().contains(IVANHOE_CARD));
		assertEquals(true, game.getDiscardPile().contains(RIPOSTE_CARD));
	}

	@Test
	public void yesToIvanhoeDodgeGetsDiscarded() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.overrideTourColour(Config.RED);
		game.getPlayer(0).addCardToHand(DODGE_CARD);

		game.getPlayer(1).addCardToHand(IVANHOE_CARD);
		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_7, Config.PURPLE);
		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_7, Config.PURPLE);

		String info = "Yes=" + DODGE_CARD.getName() + "=0";
		game.processIvanhoeCard(info);

		assertEquals(0, game.getPlayer(1).getHandCards().size());
		assertEquals(0, game.getPlayer(0).getHandCards().size());
		assertEquals(2, game.getPlayer(1).getDisplayCards().size());
		assertEquals(0, game.getPlayer(0).getDisplayCards().size());
		assertEquals(Config.RED, game.getTournamentColour());
		assertEquals(2, game.getDiscardPile().size());
		assertEquals(true, game.getDiscardPile().contains(IVANHOE_CARD));
		assertEquals(true, game.getDiscardPile().contains(DODGE_CARD));
	}

	@Test
	public void yesToIvanhoeRetreatGetsDiscarded() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.overrideTourColour(Config.RED);
		game.getPlayer(0).addCardToHand(RETREAT_CARD);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_7, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_7, Config.PURPLE);

		game.getPlayer(1).addCardToHand(IVANHOE_CARD);

		String info = "Yes=" + RETREAT_CARD.getName() + "=0";
		game.processIvanhoeCard(info);

		assertEquals(0, game.getPlayer(1).getHandCards().size());
		assertEquals(0, game.getPlayer(0).getHandCards().size());
		assertEquals(2, game.getPlayer(0).getDisplayCards().size());
		assertEquals(0, game.getPlayer(1).getDisplayCards().size());
		assertEquals(Config.RED, game.getTournamentColour());
		assertEquals(2, game.getDiscardPile().size());
		assertEquals(true, game.getDiscardPile().contains(IVANHOE_CARD));
		assertEquals(true, game.getDiscardPile().contains(RETREAT_CARD));
	}

	@Test
	public void yesToIvanhoeKnockDownGetsDiscarded() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.getPlayer(0).addCardToHand(KNOCK_DOWN_CARD);
		game.getPlayer(0).addCardToHand(BLUE_CARD_3);

		game.getPlayer(1).addCardToHand(IVANHOE_CARD);

		String info = "Yes=" + KNOCK_DOWN_CARD.getName() + "=0="
				+ PLAYER_TWO_NAME;
		game.processIvanhoeCard(info);

		assertEquals(0, game.getPlayer(1).getHandCards().size());
		assertEquals(1, game.getPlayer(0).getHandCards().size());
		assertEquals(2, game.getDiscardPile().size());
		assertEquals(true, game.getDiscardPile().contains(IVANHOE_CARD));
		assertEquals(true, game.getDiscardPile().contains(KNOCK_DOWN_CARD));
	}

	@Test
	public void yesToIvanhoeOutmaneuverGetsDiscarded() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.overrideTourColour(Config.RED);
		game.getPlayer(0).addCardToHand(OUTMANEUVER_CARD);

		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_7, Config.PURPLE);
		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_7, Config.PURPLE);
		game.getPlayer(1).addCardToHand(IVANHOE_CARD);

		String info = "Yes=" + OUTMANEUVER_CARD.getName() + "=0";
		game.processIvanhoeCard(info);

		assertEquals(0, game.getPlayer(1).getHandCards().size());
		assertEquals(0, game.getPlayer(0).getHandCards().size());
		assertEquals(2, game.getPlayer(1).getDisplayCards().size());
		assertEquals(0, game.getPlayer(0).getDisplayCards().size());
		assertEquals(Config.RED, game.getTournamentColour());
		assertEquals(2, game.getDiscardPile().size());
		assertEquals(true, game.getDiscardPile().contains(IVANHOE_CARD));
		assertEquals(true, game.getDiscardPile().contains(OUTMANEUVER_CARD));
	}

	@Test
	public void yesToIvanhoeChargeGetsDiscarded() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.overrideTourColour(Config.RED);
		game.getPlayer(0).addCardToHand(CHARGE_CARD);

		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_7, Config.PURPLE);
		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_7, Config.PURPLE);
		game.getPlayer(1).addCardToHand(IVANHOE_CARD);

		String info = "Yes=" + CHARGE_CARD.getName() + "=0";
		game.processIvanhoeCard(info);

		assertEquals(0, game.getPlayer(1).getHandCards().size());
		assertEquals(0, game.getPlayer(0).getHandCards().size());
		assertEquals(2, game.getPlayer(1).getDisplayCards().size());
		assertEquals(0, game.getPlayer(0).getDisplayCards().size());
		assertEquals(Config.RED, game.getTournamentColour());
		assertEquals(2, game.getDiscardPile().size());
		assertEquals(true, game.getDiscardPile().contains(IVANHOE_CARD));
		assertEquals(true, game.getDiscardPile().contains(CHARGE_CARD));
	}

	@Test
	public void yesToIvanhoeCounterChargeGetsDiscarded() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.overrideTourColour(Config.RED);
		game.getPlayer(0).addCardToHand(COUNTER_CHARGE_CARD);

		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_7, Config.PURPLE);
		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_7, Config.PURPLE);
		game.getPlayer(1).addCardToHand(IVANHOE_CARD);

		String info = "Yes=" + COUNTER_CHARGE_CARD.getName() + "=0";
		game.processIvanhoeCard(info);

		assertEquals(0, game.getPlayer(1).getHandCards().size());
		assertEquals(0, game.getPlayer(0).getHandCards().size());
		assertEquals(2, game.getPlayer(1).getDisplayCards().size());
		assertEquals(0, game.getPlayer(0).getDisplayCards().size());
		assertEquals(Config.RED, game.getTournamentColour());
		assertEquals(2, game.getDiscardPile().size());
		assertEquals(true, game.getDiscardPile().contains(IVANHOE_CARD));
		assertEquals(true, game.getDiscardPile().contains(COUNTER_CHARGE_CARD));
	}

	@Test
	public void yesToIvanhoeAdaptGetsDiscarded() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.getPlayer(0).addCardToHand(ADAPT_CARD);

		game.getPlayer(1).addCardToHand(IVANHOE_CARD);

		String info = "Yes=" + ADAPT_CARD.getName() + "=0";
		String result = game.processIvanhoeCard(info);

		assertEquals(true, result.contains("actionCardPlayedMessage"));
		assertEquals(0, game.getPlayer(1).getHandCards().size());
		assertEquals(0, game.getPlayer(0).getHandCards().size());
		assertEquals(2, game.getDiscardPile().size());
		assertEquals(true, game.getDiscardPile().contains(IVANHOE_CARD));
		assertEquals(true, game.getDiscardPile().contains(ADAPT_CARD));
	}

	@Test
	public void yesToIvanhoeOutwitGetsDiscarded() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.overrideTourColour(Config.RED);
		game.getPlayer(0).addCardToHand(OUTWIT_CARD);

		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_7, Config.PURPLE);
		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_7, Config.PURPLE);
		game.getPlayer(1).addCardToHand(IVANHOE_CARD);

		String info = "Yes=" + OUTWIT_CARD.getName() + "=0";
		game.processIvanhoeCard(info);

		assertEquals(0, game.getPlayer(1).getHandCards().size());
		assertEquals(0, game.getPlayer(0).getHandCards().size());
		assertEquals(2, game.getPlayer(1).getDisplayCards().size());
		assertEquals(0, game.getPlayer(0).getDisplayCards().size());
		assertEquals(Config.RED, game.getTournamentColour());
		assertEquals(2, game.getDiscardPile().size());
		assertEquals(true, game.getDiscardPile().contains(IVANHOE_CARD));
		assertEquals(true, game.getDiscardPile().contains(OUTWIT_CARD));
	}

	@Test
	public void playAdaptCardAskForIvanhoe() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_3, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_3, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_2, Config.PURPLE);
		game.getPlayer(0).addCardToHand(ADAPT_CARD);

		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_3, Config.PURPLE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_3, Config.PURPLE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.PURPLE);
		game.getPlayer(1).addCardToHand(IVANHOE_CARD);

		String result = game.playCard(0, ADAPT_CARD.getName());
		assertEquals(true, result.contains("Ivanhoe"));
		assertEquals(1, game.getPlayer(0).getHandCards().size());
		assertEquals(1, game.getPlayer(1).getHandCards().size());
		assertEquals(3, game.getPlayer(0).getDisplayCards().size());
		assertEquals(3, game.getPlayer(1).getDisplayCards().size());
		assertEquals(0, game.getDiscardPile().size());
	}

	@Test
	public void noToIvanhoeAdaptAsksForMoreInfo() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_3, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_3, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_2, Config.PURPLE);
		game.getPlayer(0).addCardToHand(ADAPT_CARD);

		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_3, Config.PURPLE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_3, Config.PURPLE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.PURPLE);
		game.getPlayer(1).addCardToHand(IVANHOE_CARD);

		String info = "No=" + ADAPT_CARD + "=0";
		String result = game.processIvanhoeCard(info);

		assertEquals(true, result.contains("adaptNeed"));
		assertEquals(1, game.getPlayer(1).getHandCards().size());
		assertEquals(0, game.getPlayer(0).getHandCards().size());
		assertEquals(1, game.getDiscardPile().size());
	}

	@Test
	public void playerWithdrawWithMaidenInDisplay() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.getPlayer(0).addCardToHand(BLUE_CARD_3);

		game.getPlayer(1).addToken(Config.BLUE);
		game.getPlayer(1).addToken(Config.RED);
		game.getPlayer(1).addToken(Config.YELLOW);
		game.getPlayer(1).addCardToDisplay(MAIDEN_CARD, Config.BLUE);

		String result = game.withdrawPlayer(1, true);
		assertEquals(true, result.contains("maidenPickTokenToReturn"));
	}

	@Test
	public void processReturnToken() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");

		game.startGame();

		game.addTokenToPlayer(1, Config.BLUE);
		game.addTokenToPlayer(1, Config.RED);
		game.addTokenToPlayer(1, Config.YELLOW);

		assertEquals(22, game.getTokenPool().size());
		game.processReturnToken(1, Config.RED);
		assertEquals(23, game.getTokenPool().size());
		assertEquals(false, game.getPlayer(1).getTokens().contains(Config.RED));
		assertEquals(true, game.getPlayer(1).getTokens().contains(Config.BLUE));
		assertEquals(true, game.getPlayer(1).getTokens()
				.contains(Config.YELLOW));
	}

	@Test
	public void fullNumberOfCards() {
		assertEquals(110, game.getDrawPile().getNumCards());
	}

	@Test
	public void playerStartsOthersDrawWithdraw() {
		game.setNumPlayers(3);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.BLUE, "");
		game.addPlayer(PLAYER_THREE_NAME, Config.PURPLE, "");

		game.getPlayer(0).addCardToHand(SQUIRE_CARD_2);
		game.startGame();

		game.setTournamentColour(Config.RED);

		game.drawCard(0);
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);

		game.goToNextPlayer(true);
		game.drawCard(1);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);
		game.withdrawPlayer(1, true);

		game.drawCard(2);
		String result = game.withdrawPlayer(2, true);

		// When other players withdraw, player 0 wins
		assertEquals(true, result.contains(PLAYER_ONE_NAME));
	}

	@Test
	public void onlyOneMaidenAllowed() {
		game.setNumPlayers(3);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.BLUE, "");
		game.addPlayer(PLAYER_THREE_NAME, Config.PURPLE, "");

		game.getPlayer(0).addCardToHand(MAIDEN_CARD);
		game.getPlayer(0).addCardToHand(MAIDEN_CARD);
		game.startGame();

		game.setTournamentColour(Config.RED);

		game.playCard(0, "Maiden 6");
		game.playCard(0, "Maiden 6");

		// You are only allowed to add one maiden to your display
		assertEquals(1, game.getPlayer(0).getDisplayCards().size());
	}

	@Test
	public void comingToTheEndOfTheDeck() {
		game.setNumPlayers(5);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.BLUE, "");
		game.addPlayer(PLAYER_THREE_NAME, Config.GREEN, "");
		game.addPlayer(PLAYER_FOUR_NAME, Config.YELLOW, "");
		game.addPlayer(PLAYER_FIVE_NAME, Config.PURPLE, "");

		game.startGame();
		// 8 cards to each of the 5 players
		// Removes 40 cards from the deck, there are 70 left

		assertEquals(70, game.getDrawPile().getNumCards());

		ArrayDeque<Card> handCopy = new ArrayDeque<Card>();

		for (Card card : game.getPlayer(0).getHandCards()) {
			handCopy.add(card);
		}

		for (Card c : handCopy) {
			game.moveCardFromHandToDiscardPile(0, c.getName());
		}

		// Now the discard pile has the 8 cards from player 0's hand
		assertEquals(8, game.getDiscardPile().size());

		assertEquals(70, game.getDrawPile().getNumCards());

		// Put the remaining 70 cards into player 0's hand
		for (int i = 0; i < 70; i++) {
			game.drawCard(0);
		}

		// The draw pile should NOT be empty
		// It should refill with the 8 cards from the discard pile
		assertEquals(8, game.getDrawPile().getNumCards());
		assertEquals(0, game.getDiscardPile().size());
	}

	@Test
	public void automaticallyWithdrawingPlayerWithLowestScore() {
		game.setNumPlayers(3);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.BLUE, "");
		game.addPlayer(PLAYER_THREE_NAME, Config.PURPLE, "");

		game.startGame();
		// Player 0 has a score of 3
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_3, Config.RED);
		game.goToNextPlayer(true);
		// It is now player 1s turn

		// When I go to the next player, it should not withdraw player 0 because
		// they have the highest display
		assertEquals(false, game.getPlayer(0).isWithdrawn());
		assertEquals(1, game.getCurrentPlayerNumber());

		// Player 2 has a score of 3
		game.getPlayer(2).addCardToDisplay(SQUIRE_CARD_3, Config.RED);

		game.goToNextPlayer(true);
		// Is it now player 2's turn
		// Player 1 is withdrawn because they have a score of 0
		assertEquals(2, game.getCurrentPlayerNumber());
		assertEquals(true, game.getPlayer(1).isWithdrawn());

		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_2, Config.RED);
		String result = game.goToNextPlayer(true);
		// Automatically withdraws player 2, and player 0 wins

		assertEquals(true, result.contains(PLAYER_ONE_NAME));
	}

	@Test
	public void winningATournament() {
		game.setNumPlayers(3);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.BLUE, "");
		game.addPlayer(PLAYER_THREE_NAME, Config.PURPLE, "");

		game.startGame();
		game.overrideTourColour(Config.BLUE);
		game.withdrawPlayer(0, false);
		String result = game.withdrawPlayer(1, false);

		// Player 3 wins
		assertEquals(true, result.contains(PLAYER_THREE_NAME));
	}

	@Test
	public void automaticallyWithdrawingPlayerWhenNoCardPlayed() {
		game.setNumPlayers(3);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.BLUE, "");
		game.addPlayer(PLAYER_THREE_NAME, Config.PURPLE, "");

		game.startGame();
		// Player 0 has a score of 3
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_3, Config.RED);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_3, Config.RED);
		game.goToNextPlayer(true);
		// It is now player 1s turn

		// When I go to the next player, it should not withdraw player 0 because
		// they have the highest display
		assertEquals(false, game.getPlayer(0).isWithdrawn());
		assertEquals(1, game.getCurrentPlayerNumber());

		// Player 1 has not played a card yet this turn

		game.goToNextPlayer(true);
		// Is it now player 2's turn
		// Player 1 is withdrawn because they haven't played a card yet
		assertEquals(2, game.getCurrentPlayerNumber());
		assertEquals(true, game.getPlayer(1).isWithdrawn());
	}

	@Test
	public void automaticallyWithdrawingPlayerAnnouncesWinner() {
		game.setNumPlayers(3);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.BLUE, "");
		game.addPlayer(PLAYER_THREE_NAME, Config.PURPLE, "");

		game.startGame();
		// Player 0 has a score of 3
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_3, Config.RED);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_3, Config.RED);
		game.goToNextPlayer(true);
		// It is now player 1s turn

		// When I go to the next player, it should not withdraw player 0 because
		// they have the highest display
		assertEquals(false, game.getPlayer(0).isWithdrawn());
		assertEquals(1, game.getCurrentPlayerNumber());

		// Player 1 has not played a card yet this turn

		game.goToNextPlayer(true);
		// Is it now player 2's turn
		// Player 1 is withdrawn because they haven't played a card yet
		assertEquals(2, game.getCurrentPlayerNumber());
		assertEquals(true, game.getPlayer(1).isWithdrawn());

		String result = game.goToNextPlayer(true);

		// Player 2 gets automatically withdrawn because they didn't play a card
		assertEquals(true, result.contains(PLAYER_ONE_NAME));
	}

	@Test
	public void onePlayerStartsOnlyOnePlayOneCard() {
		// One player draws/starts, others draw but only one participates by
		// playing a card
		game.setNumPlayers(5);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.BLUE, "");
		game.addPlayer(PLAYER_THREE_NAME, Config.PURPLE, "");
		game.addPlayer(PLAYER_FOUR_NAME, Config.PURPLE, "");
		game.addPlayer(PLAYER_FIVE_NAME, Config.PURPLE, "");

		game.startGame();

		// Player 0 starts
		game.drawCard(0);
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_3, Config.BLUE);
		game.goToNextPlayer(true);

		// Players 1,2,3 draw but don't play a card (and get withdrawn)
		for (int i = 1; i < 4; i++) {
			game.drawCard(i);
			game.goToNextPlayer(true);
		}
		// Players 1,2,3 should be automatically withdrawn because they didn't
		// play
		assertEquals(true, game.getPlayer(1).isWithdrawn());
		assertEquals(true, game.getPlayer(2).isWithdrawn());
		assertEquals(true, game.getPlayer(3).isWithdrawn());

		// Player 4 plays a card and remains in tournament
		game.getPlayer(4).addCardToDisplay(SQUIRE_CARD_3, Config.BLUE);
		game.goToNextPlayer(true);
		assertEquals(false, game.getPlayer(4).isWithdrawn());
	}

	@Test
	public void onePlayerStartsOnlyOnePlayMultipleCard() {
		// One player draws/starts, others draw but only one participates by
		// playing several cards
		game.setNumPlayers(5);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.BLUE, "");
		game.addPlayer(PLAYER_THREE_NAME, Config.PURPLE, "");
		game.addPlayer(PLAYER_FOUR_NAME, Config.PURPLE, "");
		game.addPlayer(PLAYER_FIVE_NAME, Config.PURPLE, "");

		game.startGame();

		// Player 0 starts
		game.drawCard(0);
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_3, Config.BLUE);
		game.goToNextPlayer(true);

		// Players 1,2,3 draw but don't play a card (and get withdrawn)
		for (int i = 1; i < 4; i++) {
			game.drawCard(i);
			game.goToNextPlayer(true);
		}
		// Players 1,2,3 should be automatically withdrawn because they didn't
		// play
		assertEquals(true, game.getPlayer(1).isWithdrawn());
		assertEquals(true, game.getPlayer(2).isWithdrawn());
		assertEquals(true, game.getPlayer(3).isWithdrawn());

		// Player 4 plays a card and remains in tournament
		game.getPlayer(4).addCardToDisplay(SQUIRE_CARD_3, Config.BLUE);
		game.getPlayer(4).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);
		game.getPlayer(4).addCardToDisplay(SQUIRE_CARD_3, Config.BLUE);
		game.goToNextPlayer(true);
		assertEquals(false, game.getPlayer(4).isWithdrawn());
	}

	@Test
	public void onePlayerStartsSomePlayOneCard() {
		// One player draws/starts, others draw but some participate by
		// playing a card
		game.setNumPlayers(5);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.BLUE, "");
		game.addPlayer(PLAYER_THREE_NAME, Config.PURPLE, "");
		game.addPlayer(PLAYER_FOUR_NAME, Config.PURPLE, "");
		game.addPlayer(PLAYER_FIVE_NAME, Config.PURPLE, "");

		game.startGame();

		// Player 0 starts
		game.drawCard(0);
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_3, Config.BLUE);
		game.goToNextPlayer(true);

		// Players 1,2 draw but don't play a card (and get withdrawn)
		for (int i = 1; i <= 2; i++) {
			game.drawCard(i);
			game.goToNextPlayer(true);
		}
		// Players 1,2 should be automatically withdrawn because they didn't
		// play
		assertEquals(true, game.getPlayer(1).isWithdrawn());
		assertEquals(true, game.getPlayer(2).isWithdrawn());

		// Player 3 plays a card and remains in tournament
		game.getPlayer(3).addCardToDisplay(SQUIRE_CARD_3, Config.BLUE);
		game.goToNextPlayer(true);
		assertEquals(false, game.getPlayer(3).isWithdrawn());

		// Player 4 plays a card and remains in tournament
		game.getPlayer(4).addCardToDisplay(SQUIRE_CARD_3, Config.BLUE);
		game.goToNextPlayer(true);
		assertEquals(false, game.getPlayer(4).isWithdrawn());
	}

	@Test
	public void onePlayerStartsSomePlaySeveralCards() {
		// One player draws/starts, others draw but some participate by
		// playing several cards card
		game.setNumPlayers(5);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.BLUE, "");
		game.addPlayer(PLAYER_THREE_NAME, Config.PURPLE, "");
		game.addPlayer(PLAYER_FOUR_NAME, Config.PURPLE, "");
		game.addPlayer(PLAYER_FIVE_NAME, Config.PURPLE, "");

		game.startGame();

		// Player 0 starts
		game.drawCard(0);
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_3, Config.BLUE);
		game.goToNextPlayer(true);

		// Players 1,2 draw but don't play a card (and get withdrawn)
		for (int i = 1; i <= 2; i++) {
			game.drawCard(i);
			game.goToNextPlayer(true);
		}
		// Players 1,2 should be automatically withdrawn because they didn't
		// play
		assertEquals(true, game.getPlayer(1).isWithdrawn());
		assertEquals(true, game.getPlayer(2).isWithdrawn());

		// Player 3 plays a card and remains in tournament
		game.getPlayer(3).addCardToDisplay(SQUIRE_CARD_3, Config.BLUE);
		game.getPlayer(3).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);
		game.getPlayer(3).addCardToDisplay(SQUIRE_CARD_3, Config.BLUE);
		game.goToNextPlayer(true);
		assertEquals(false, game.getPlayer(3).isWithdrawn());

		// Player 4 plays a card and remains in tournament
		game.getPlayer(4).addCardToDisplay(SQUIRE_CARD_3, Config.BLUE);
		game.getPlayer(4).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);
		game.getPlayer(4).addCardToDisplay(SQUIRE_CARD_3, Config.BLUE);
		game.goToNextPlayer(true);
		assertEquals(false, game.getPlayer(4).isWithdrawn());
	}

	@Test
	public void onePlayerStartsAllPlayOneCard() {
		// One player draws/starts, others draw but some participate by
		// playing several cards card
		game.setNumPlayers(5);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.BLUE, "");
		game.addPlayer(PLAYER_THREE_NAME, Config.PURPLE, "");
		game.addPlayer(PLAYER_FOUR_NAME, Config.PURPLE, "");
		game.addPlayer(PLAYER_FIVE_NAME, Config.PURPLE, "");

		game.startGame();

		// Player 0 starts
		game.drawCard(0);
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_3, Config.BLUE);
		game.goToNextPlayer(true);

		// Everyone should play a card
		for (int i = 1; i <= 4; i++) {
			game.getPlayer(i).addCardToDisplay(SQUIRE_CARD_3, Config.BLUE);
			game.goToNextPlayer(true);
		}
		// Everyone should still be active in the tournament
		assertEquals(false, game.getPlayer(1).isWithdrawn());
		assertEquals(false, game.getPlayer(2).isWithdrawn());
		assertEquals(false, game.getPlayer(3).isWithdrawn());
		assertEquals(false, game.getPlayer(4).isWithdrawn());
	}

	@Test
	public void onePlayerStartsAllPlaySeveralCards() {
		// One player draws/starts, others draw but some participate by
		// playing several cards card
		game.setNumPlayers(5);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.BLUE, "");
		game.addPlayer(PLAYER_THREE_NAME, Config.PURPLE, "");
		game.addPlayer(PLAYER_FOUR_NAME, Config.PURPLE, "");
		game.addPlayer(PLAYER_FIVE_NAME, Config.PURPLE, "");

		game.startGame();

		// Player 0 starts
		game.drawCard(0);
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_3, Config.BLUE);
		game.goToNextPlayer(true);

		// Everyone should play a card
		for (int i = 1; i <= 4; i++) {
			game.getPlayer(i).addCardToDisplay(SQUIRE_CARD_3, Config.BLUE);
			game.getPlayer(i).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);
			game.getPlayer(i).addCardToDisplay(SQUIRE_CARD_3, Config.BLUE);
			game.goToNextPlayer(true);
		}
		// Everyone should still be active in the tournament
		assertEquals(false, game.getPlayer(1).isWithdrawn());
		assertEquals(false, game.getPlayer(2).isWithdrawn());
		assertEquals(false, game.getPlayer(3).isWithdrawn());
		assertEquals(false, game.getPlayer(4).isWithdrawn());
	}

	@Test
	public void playingAnActionCardNoAutomaticWithdraw() {
		// One player draws/starts, others draw but some participate by
		// playing several cards card
		game.setNumPlayers(5);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.BLUE, "");
		game.addPlayer(PLAYER_THREE_NAME, Config.GREEN, "");
		game.addPlayer(PLAYER_FOUR_NAME, Config.YELLOW, "");
		game.addPlayer(PLAYER_FIVE_NAME, Config.PURPLE, "");

		game.startGame();
		// Remove any ivanhoe cards from the players hands
		for (Player p : game.getPlayers()) {
			p.removeCardFromHand("Ivanhoe");
		}
		game.overrideTourColour(Config.RED);

		// Player 0 starts
		game.drawCard(0);
		game.getPlayer(0).addCardToHand(DROP_WEAPON_CARD);
		game.playCard(0, DROP_WEAPON_CARD.getName());
		game.goToNextPlayer(true);

		// Player 0 should not be withdrawn because they played an action card
		// during their turn
		assertEquals(false, game.getPlayer(0).isWithdrawn());
	}

	@Test
	public void tryPlayingChargeCardOnlyGreenOnes() {
		game.setNumPlayers(5);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.BLUE, "");
		game.addPlayer(PLAYER_THREE_NAME, Config.GREEN, "");
		game.addPlayer(PLAYER_FOUR_NAME, Config.YELLOW, "");
		game.addPlayer(PLAYER_FIVE_NAME, Config.PURPLE, "");

		game.startGame();
		// Remove any ivanhoe cards from the players hands
		for (Player p : game.getPlayers()) {
			p.removeCardFromHand("Ivanhoe");
		}
		game.overrideTourColour(Config.GREEN);

		game.getPlayer(0).addCardToDisplay(GREEN_CARD_1, Config.GREEN);
		game.getPlayer(1).addCardToDisplay(GREEN_CARD_1, Config.GREEN);
		game.getPlayer(1).addCardToDisplay(GREEN_CARD_1, Config.GREEN);
		game.getPlayer(2).addCardToDisplay(GREEN_CARD_1, Config.GREEN);
		game.getPlayer(2).addCardToDisplay(GREEN_CARD_1, Config.GREEN);
		game.getPlayer(2).addCardToDisplay(GREEN_CARD_1, Config.GREEN);
		game.getPlayer(3).addCardToDisplay(GREEN_CARD_1, Config.GREEN);
		game.getPlayer(3).addCardToDisplay(GREEN_CARD_1, Config.GREEN);
		game.getPlayer(3).addCardToDisplay(GREEN_CARD_1, Config.GREEN);
		game.getPlayer(3).addCardToDisplay(GREEN_CARD_1, Config.GREEN);
		game.getPlayer(4).addCardToDisplay(GREEN_CARD_1, Config.GREEN);
		game.getPlayer(4).addCardToDisplay(GREEN_CARD_1, Config.GREEN);
		game.getPlayer(4).addCardToDisplay(GREEN_CARD_1, Config.GREEN);

		game.getPlayer(1).addCardToHand(CHARGE_CARD);
		String result = game.playCard(1, CHARGE_CARD.getName());
		assertEquals(true, result.contains("actionCardPlayedMessage"));
		assertEquals(1, game.getPlayer(0).getDisplayCards().size());
		assertEquals(1, game.getPlayer(2).getDisplayCards().size());
		assertEquals(1, game.getPlayer(3).getDisplayCards().size());
		assertEquals(1, game.getPlayer(4).getDisplayCards().size());
		assertEquals(8, game.getDiscardPile().size());

		// It's important to note, that the action cards that we did for
		// iteration 1 (including this card) do not affect the player that has
		// played the action card. Because we understood the rules in this way
		// and were told we did not need to change this.
	}

	@Test
	public void playerHasWonGame() {
		game.setNumPlayers(5);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.BLUE, "");
		game.addPlayer(PLAYER_THREE_NAME, Config.GREEN, "");
		game.addPlayer(PLAYER_FOUR_NAME, Config.YELLOW, "");
		game.addPlayer(PLAYER_FIVE_NAME, Config.PURPLE, "");

		game.addTokenToPlayer(4, Config.RED);
		game.addTokenToPlayer(4, Config.YELLOW);
		game.addTokenToPlayer(4, Config.GREEN);
		game.addTokenToPlayer(4, Config.BLUE);
		String result = game.checkForWinner();

		assertEquals(true, result.contains(PLAYER_FIVE_NAME));
	}

	@Test
	public void multipleRoundsWithSupporters() {
		game.setNumPlayers(4);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE, "");
		game.addPlayer(PLAYER_THREE_NAME, Config.GREEN, ""); // This player will
															// start
		game.addPlayer(PLAYER_FOUR_NAME, Config.BLUE, "");

		game.getPlayer(0).addCardToHand(SQUIRE_CARD_2);
		game.getPlayer(1).addCardToHand(SQUIRE_CARD_2);
		game.getPlayer(2).addCardToHand(SQUIRE_CARD_2);
		game.getPlayer(3).addCardToHand(SQUIRE_CARD_2);

		game.startGame();
		game.overrideTourColour(Config.BLUE);

		assertEquals(2, game.getCurrentPlayerNumber());

		// One round where everyone plays one supporter and stays in the
		// tournament
		game.playCard(2, SQUIRE_CARD_2.getName());
		game.goToNextPlayer(true);
		assertEquals(3, game.getCurrentPlayerNumber());

		game.playCard(3, SQUIRE_CARD_2.getName());
		game.goToNextPlayer(true);
		assertEquals(0, game.getCurrentPlayerNumber());

		game.playCard(0, SQUIRE_CARD_2.getName());
		game.goToNextPlayer(true);
		assertEquals(1, game.getCurrentPlayerNumber());

		game.playCard(1, SQUIRE_CARD_2.getName());
		game.goToNextPlayer(true);
		assertEquals(2, game.getCurrentPlayerNumber());

		assertEquals(false, game.getPlayer(0).isWithdrawn());
		assertEquals(false, game.getPlayer(1).isWithdrawn());
		assertEquals(false, game.getPlayer(2).isWithdrawn());
		assertEquals(false, game.getPlayer(3).isWithdrawn());

		// Round 2: everyone plays a few supporters
		game.getPlayer(0).addCardToHand(SQUIRE_CARD_2);
		game.getPlayer(1).addCardToHand(SQUIRE_CARD_2);
		game.getPlayer(2).addCardToHand(SQUIRE_CARD_2);
		game.getPlayer(3).addCardToHand(SQUIRE_CARD_2);
		game.getPlayer(0).addCardToHand(SQUIRE_CARD_3);
		game.getPlayer(1).addCardToHand(SQUIRE_CARD_3);
		game.getPlayer(2).addCardToHand(SQUIRE_CARD_3);
		game.getPlayer(3).addCardToHand(SQUIRE_CARD_3);
		game.getPlayer(0).addCardToHand(MAIDEN_CARD);
		game.getPlayer(1).addCardToHand(MAIDEN_CARD);
		game.getPlayer(2).addCardToHand(MAIDEN_CARD);
		game.getPlayer(3).addCardToHand(MAIDEN_CARD);

		game.playCard(2, SQUIRE_CARD_2.getName());
		game.playCard(2, SQUIRE_CARD_3.getName());
		game.playCard(2, MAIDEN_CARD.getName());
		game.goToNextPlayer(true);
		assertEquals(3, game.getCurrentPlayerNumber());

		game.playCard(3, SQUIRE_CARD_2.getName());
		game.playCard(3, SQUIRE_CARD_3.getName());
		game.playCard(3, MAIDEN_CARD.getName());
		game.goToNextPlayer(true);
		assertEquals(0, game.getCurrentPlayerNumber());

		game.playCard(0, SQUIRE_CARD_2.getName());
		game.playCard(0, SQUIRE_CARD_3.getName());
		game.playCard(0, MAIDEN_CARD.getName());
		game.goToNextPlayer(true);
		assertEquals(1, game.getCurrentPlayerNumber());

		game.playCard(1, SQUIRE_CARD_2.getName());
		game.playCard(1, SQUIRE_CARD_3.getName());
		game.playCard(1, MAIDEN_CARD.getName());
		game.goToNextPlayer(true);
		assertEquals(2, game.getCurrentPlayerNumber());

		assertEquals(false, game.getPlayer(0).isWithdrawn());
		assertEquals(false, game.getPlayer(1).isWithdrawn());
		assertEquals(false, game.getPlayer(2).isWithdrawn());
		assertEquals(false, game.getPlayer(3).isWithdrawn());

		// Round 3: Some players play more cards than others, players get
		// automatically withdrawn for not having a high score
		game.getPlayer(0).addCardToHand(SQUIRE_CARD_2);
		game.getPlayer(2).addCardToHand(SQUIRE_CARD_2);
		game.getPlayer(3).addCardToHand(SQUIRE_CARD_2);
		game.getPlayer(0).addCardToHand(SQUIRE_CARD_3);
		game.getPlayer(2).addCardToHand(SQUIRE_CARD_3);
		game.getPlayer(0).addCardToHand(MAIDEN_CARD);
		game.getPlayer(1).addCardToHand(MAIDEN_CARD);
		game.getPlayer(2).addCardToHand(MAIDEN_CARD);

		game.playCard(2, SQUIRE_CARD_2.getName());
		game.playCard(2, SQUIRE_CARD_3.getName());
		game.playCard(2, MAIDEN_CARD.getName());
		game.goToNextPlayer(true);
		assertEquals(3, game.getCurrentPlayerNumber());

		game.playCard(3, SQUIRE_CARD_2.getName());
		game.goToNextPlayer(true);
		assertEquals(0, game.getCurrentPlayerNumber());

		game.playCard(0, SQUIRE_CARD_2.getName());
		game.playCard(0, SQUIRE_CARD_3.getName());
		game.playCard(0, MAIDEN_CARD.getName());
		game.goToNextPlayer(true);
		assertEquals(1, game.getCurrentPlayerNumber());

		game.playCard(1, SQUIRE_CARD_2.getName());
		game.goToNextPlayer(true);
		assertEquals(2, game.getCurrentPlayerNumber());

		assertEquals(false, game.getPlayer(0).isWithdrawn());
		assertEquals(true, game.getPlayer(1).isWithdrawn());
		assertEquals(false, game.getPlayer(2).isWithdrawn());
		assertEquals(true, game.getPlayer(3).isWithdrawn());
	}
	
	@Test
	public void startingWithOneSupporter() {
		game.setNumPlayers(3);
		game.addPlayer(PLAYER_ONE_NAME, Config.PURPLE, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.BLUE, "");// This player starts
		game.addPlayer(PLAYER_THREE_NAME, Config.RED, "");
		
		game.startGame();
		
		game.getPlayer(1).addCardToHand(SQUIRE_CARD_3);
		game.playCard(1, SQUIRE_CARD_3.getName());
		game.goToNextPlayer(true);
		// Player 1 started the tournament with one supporter
		
		assertEquals(false, game.getPlayer(0).isWithdrawn());
		assertEquals(false, game.getPlayer(1).isWithdrawn());
		assertEquals(false, game.getPlayer(2).isWithdrawn());
	}
	
	@Test
	public void startingWithMultipleSupporters() {
		game.setNumPlayers(3);
		game.addPlayer(PLAYER_ONE_NAME, Config.PURPLE, "");
		game.addPlayer(PLAYER_TWO_NAME, Config.BLUE, "");// This player starts
		game.addPlayer(PLAYER_THREE_NAME, Config.RED, "");
		
		game.startGame();
		
		game.getPlayer(1).addCardToHand(SQUIRE_CARD_3);
		game.getPlayer(1).addCardToHand(SQUIRE_CARD_2);
		game.getPlayer(1).addCardToHand(MAIDEN_CARD);
		game.playCard(1, SQUIRE_CARD_3.getName());
		game.playCard(1, SQUIRE_CARD_2.getName());
		game.playCard(1, MAIDEN_CARD.getName());
		game.goToNextPlayer(true);
		// Player 1 started the tournament with multiple supporters
		
		assertEquals(false, game.getPlayer(0).isWithdrawn());
		assertEquals(false, game.getPlayer(1).isWithdrawn());
		assertEquals(false, game.getPlayer(2).isWithdrawn());
	}

	@After
	public void tearDown() {
		game = null;
	}
}
