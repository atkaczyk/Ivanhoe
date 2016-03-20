package logicTest;

import static org.junit.Assert.*;

import java.util.ArrayDeque;

import logic.ActionCard;
import logic.Card;
import logic.ColourCard;
import logic.Game;
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
		game.addPlayer(PLAYER_ONE_NAME, Config.PURPLE);

		assertNotEquals(null, game.getPlayers()[0]);
	}

	@Test
	public void startingPlayer() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		assertEquals(PLAYER_TWO_NAME, game.getCurrentPlayer().getName());
	}

	@Test
	public void gameReady() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		assertEquals(true, game.isReadyToStart());
	}

	@Test
	public void gameNotReady() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);

		assertEquals(false, game.isReadyToStart());
	}

	@Test
	public void gameStart() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.startGame();

		assertEquals(false, game.getPlayers()[0].getHandCards().isEmpty());
		assertEquals(false, game.getTokenPool().isEmpty());
	}

	@Test
	public void playersActiveAfterStart() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.startGame();

		assertEquals(false, game.getPlayers()[0].isWithdrawn());
		assertEquals(false, game.getPlayers()[1].isWithdrawn());
	}

	@Test
	public void startGameTwoPlayersFirstPlayerStarts() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.startGame();

		assertEquals(PLAYER_ONE_NAME, game.getCurrentPlayer().getName());
	}

	@Test
	public void getCurrentPlayerNumber() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);
		
		game.startGame();

		assertEquals(0, game.getCurrentPlayerNumber());
	}

	@Test
	public void startGameTwoPlayersSecondPlayerStarts() {
		game.addPlayer(PLAYER_ONE_NAME, Config.PURPLE);
		game.addPlayer(PLAYER_TWO_NAME, Config.RED);

		game.startGame();

		assertEquals(PLAYER_TWO_NAME, game.getCurrentPlayer().getName());
	}

	@Test
	public void pickTournColourColourInHand() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

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
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.startGame();
		game.getCurrentPlayer().clearHand();

		game.getCurrentPlayer().addCardToHand(MAIDEN_CARD);

		game.setTournamentColour(Config.BLUE);
		assertEquals(Config.BLUE, game.getTournamentColour());
	}

	@Test
	public void pickTournColourInvalidHand() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.startGame();
		game.getCurrentPlayer().clearHand();

		assertEquals(false, game.setTournamentColour(Config.BLUE));
	}

	@Test
	public void invalidPurpleTokenPreviousPurpleTournament() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.startGame();
		game.getCurrentPlayer().clearHand();

		game.getCurrentPlayer().addCardToHand(MAIDEN_CARD);

		game.setTournamentColour(Config.PURPLE);
		assertEquals(false, game.setTournamentColour(Config.PURPLE));
	}

	@Test
	public void getPlayerNumber() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);

		assertEquals(PLAYER_ONE_NAME, game.getPlayer(0).getName());
	}

	@Test
	public void tournamentStart() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.startGame();

		assertEquals(1, game.getTournamentNumber());
		assertEquals(false, game.getPlayer(0).isWithdrawn());
		assertEquals(false, game.getPlayer(1).isWithdrawn());
	}

	@Test
	public void playerDrawsCard() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.drawCard(0);

		assertEquals(1, game.getPlayer(0).getHandCards().size());
	}

	@Test
	public void twoPlayersStartTournament() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		// player one goes first
		int before = game.getDrawPile().getNumCards();
		game.drawCard(0);
		assertEquals(1, game.getPlayer(0).getHandCards().size());
		assertEquals(before - 1, game.getDrawPile().getNumCards());
	}

	@Test
	public void playingSupporterCard() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.getPlayer(0).addCardToHand(SQUIRE_CARD_2);

		assertEquals("true", game.playCard(0, SQUIRE_CARD_2.getName()));
		assertEquals(1, game.getPlayer(0).getDisplayCards().size());
		assertEquals(0, game.getPlayer(0).getHandCards().size());
	}

	@Test
	public void playingValidColourCard() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.overrideTourColour(Config.PURPLE);
		game.getPlayer(0).addCardToHand(PURPLE_CARD_3);

		assertEquals("true", game.playCard(0, PURPLE_CARD_3.getName()));
		assertEquals(1, game.getPlayer(0).getDisplayCards().size());
		assertEquals(0, game.getPlayer(0).getHandCards().size());
	}

	@Test
	public void playingInvalidColourCard() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.getPlayer(0).addCardToHand(PURPLE_CARD_3);
		game.overrideTourColour(Config.RED);

		String result = game.playCard(0, PURPLE_CARD_3.getName());
		assertEquals(true, result.contains("false"));
		assertEquals(0, game.getPlayer(0).getDisplayCards().size());
		assertEquals(1, game.getPlayer(0).getHandCards().size());
	}

	@Test
	public void startingTournamentClearsDisplay() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.startGame();
		game.getPlayer(0).addCardToDisplay(MAIDEN_CARD, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);

		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);

		game.startTournament();

		// When a tournament is started it should clear all the player displays
		// and move the cards to the discard pile
		assertEquals(0, game.getPlayer(0).getDisplayCards().size());
		assertEquals(0, game.getPlayer(1).getDisplayCards().size());
		assertEquals(8, game.getDiscardPileSize());

	}

	@Test
	public void twoPlayersWithdrawOne() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.startGame();
		game.overrideTourColour(Config.BLUE);

		String expected = PLAYER_TWO_NAME + "," + game.getTournamentNumber()
				+ "," + game.getTournamentColour();

		assertEquals(expected, game.withdrawPlayer(0));
	}

	@Test
	public void threePlayersWithdrawTwo() {
		game.setNumPlayers(3);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.YELLOW);
		game.addPlayer(PLAYER_THREE_NAME, Config.PURPLE);

		game.startGame();

		game.withdrawPlayer(0);

		game.overrideTourColour(Config.BLUE);

		String expected = PLAYER_THREE_NAME + "," + game.getTournamentNumber()
				+ "," + game.getTournamentColour();

		assertEquals(expected, game.withdrawPlayer(1));
	}

	@Test
	public void threePlayersWithdrawOne() {
		game.setNumPlayers(3);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.YELLOW);
		game.addPlayer(PLAYER_THREE_NAME, Config.PURPLE);

		game.startGame();
		game.overrideTourColour(Config.BLUE);

		assertEquals("", game.withdrawPlayer(1));
	}

	@Test
	public void fivePlayersWithdrawFour() {
		game.setNumPlayers(5);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.YELLOW);
		game.addPlayer(PLAYER_THREE_NAME, Config.PURPLE);
		game.addPlayer(PLAYER_FOUR_NAME, Config.BLUE);
		game.addPlayer(PLAYER_FIVE_NAME, Config.GREEN);

		game.startGame();

		game.withdrawPlayer(0);
		game.withdrawPlayer(1);
		game.withdrawPlayer(4);

		game.overrideTourColour(Config.BLUE);

		String expected = PLAYER_THREE_NAME + "," + game.getTournamentNumber()
				+ "," + game.getTournamentColour();

		assertEquals(expected, game.withdrawPlayer(3));
	}

	@Test
	public void twoPlayersOneWins() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

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
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

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
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);
		game.addPlayer(PLAYER_THREE_NAME, Config.BLUE);
		game.addPlayer(PLAYER_FOUR_NAME, Config.BLUE);

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
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);
		game.addPlayer(PLAYER_THREE_NAME, Config.BLUE);
		game.addPlayer(PLAYER_FOUR_NAME, Config.BLUE);

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
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.overrideTourColour(Config.RED);
		game.getPlayer(0).addCardToHand(DROP_WEAPON_CARD);

		assertEquals("true", game.playCard(0, DROP_WEAPON_CARD.getName()));
		assertEquals(0, game.getPlayer(0).getHandCards().size());
		assertEquals(Config.GREEN, game.getTournamentColour());
		assertEquals(1, game.getDiscardPileSize());
	}

	@Test
	public void playOutmaneuverTwoPlayersTwoCardsInDisplay() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.getPlayer(1).addCardToDisplay(MAIDEN_CARD, Config.BLUE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);

		game.getPlayer(0).addCardToHand(OUTMANEUVER_CARD);

		assertEquals("true", game.playCard(0, OUTMANEUVER_CARD.getName()));
		assertEquals(0, game.getPlayer(0).getHandCards().size());
		assertEquals(2, game.getDiscardPileSize());
		assertEquals(1, game.getPlayer(1).getDisplayCards().size());

		// It should still contain this card
		assertEquals(true,
				game.getPlayer(1).getDisplayCards().contains(MAIDEN_CARD));
		// This card should have gotten removed by the outmaneuver
		assertEquals(false,
				game.getPlayer(1).getDisplayCards().contains(SQUIRE_CARD_2));
	}

	@Test
	public void playOutmaneuverFivePlayersManyCardsInDisplay() {
		game.setNumPlayers(5);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.YELLOW);
		game.addPlayer(PLAYER_THREE_NAME, Config.GREEN);
		game.addPlayer(PLAYER_FOUR_NAME, Config.BLUE);
		game.addPlayer(PLAYER_FIVE_NAME, Config.PURPLE);

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
		assertEquals(3, game.getDiscardPileSize());

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
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.getPlayer(1).addCardToDisplay(MAIDEN_CARD, Config.BLUE);

		game.getPlayer(0).addCardToHand(OUTMANEUVER_CARD);

		String result = game.playCard(0, OUTMANEUVER_CARD.getName());
		assertEquals(true, result.contains("false"));
		assertEquals(1, game.getPlayer(0).getHandCards().size());
		assertEquals(0, game.getDiscardPileSize());
		assertEquals(1, game.getPlayer(1).getDisplayCards().size());

		// It should still contain this card
		assertEquals(true,
				game.getPlayer(1).getDisplayCards().contains(MAIDEN_CARD));
	}

	@Test
	public void notAllowedToPlayOutmaneuverWithdrawn() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.getPlayer(1).withdraw();

		game.getPlayer(0).addCardToHand(OUTMANEUVER_CARD);

		String result = game.playCard(0, OUTMANEUVER_CARD.getName());
		assertEquals(true, result.contains("false"));
		assertEquals(1, game.getPlayer(0).getHandCards().size());
		assertEquals(0, game.getDiscardPileSize());
		assertEquals(0, game.getPlayer(1).getDisplayCards().size());
	}

	@Test
	public void playChargeCardTwoPlayersTwoOfLowestValueToRemove() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

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
		assertEquals(3, game.getDiscardPileSize());
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
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

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
		assertEquals(5, game.getDiscardPileSize());
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
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.overrideTourColour(Config.PURPLE);
		game.getPlayer(0).addCardToHand(DROP_WEAPON_CARD);

		String result = game.playCard(0, DROP_WEAPON_CARD.getName());
		assertEquals(true, result.contains(result));
		assertEquals(1, game.getPlayer(0).getHandCards().size());
		assertEquals(Config.PURPLE, game.getTournamentColour());
		assertEquals(0, game.getDiscardPileSize());
	}

	@Test
	public void notAllowedToPlayOutmaneuver() {
		game.setNumPlayers(5);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.YELLOW);
		game.addPlayer(PLAYER_THREE_NAME, Config.GREEN);
		game.addPlayer(PLAYER_FOUR_NAME, Config.BLUE);
		game.addPlayer(PLAYER_FIVE_NAME, Config.PURPLE);

		game.getPlayer(1).addCardToDisplay(MAIDEN_CARD, Config.BLUE);
		game.getPlayer(2).addCardToDisplay(MAIDEN_CARD, Config.BLUE);
		game.getPlayer(2).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);
		game.getPlayer(4).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);

		game.getPlayer(2).addCardToHand(OUTMANEUVER_CARD);

		String result = game.playCard(2, OUTMANEUVER_CARD.getName());
		assertEquals(true, result.contains("false"));
		assertEquals(0, game.getPlayer(0).getHandCards().size());
		assertEquals(0, game.getDiscardPileSize());

		assertEquals(0, game.getPlayer(0).getDisplayCards().size());
		assertEquals(1, game.getPlayer(1).getDisplayCards().size());
		assertEquals(2, game.getPlayer(2).getDisplayCards().size());
		assertEquals(0, game.getPlayer(3).getDisplayCards().size());
		assertEquals(1, game.getPlayer(4).getDisplayCards().size());
	}

	@Test
	public void notAllowedToPlayCharge() {
		game.setNumPlayers(3);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);
		game.addPlayer(PLAYER_THREE_NAME, Config.PURPLE);

		game.getPlayer(0).addCardToDisplay(BLUE_CARD_3, Config.BLUE);

		game.getPlayer(1).addCardToHand(CHARGE_CARD);
		String result = game.playCard(1, CHARGE_CARD.getName());

		assertEquals(true, result.contains("false"));
		assertEquals(1, game.getPlayer(1).getHandCards().size());
		assertEquals(0, game.getDiscardPileSize());
		assertEquals(1, game.getPlayer(0).getDisplayCards().size());
		assertEquals(0, game.getPlayer(2).getDisplayCards().size());
	}

	@Test
	public void notAllowedToPlayChargeBecauseWithdrawn() {
		game.setNumPlayers(3);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);
		game.addPlayer(PLAYER_THREE_NAME, Config.PURPLE);

		game.getPlayer(0).addCardToDisplay(BLUE_CARD_3, Config.BLUE);
		game.getPlayer(0).addCardToDisplay(BLUE_CARD_3, Config.BLUE);
		game.getPlayer(0).withdraw();

		game.getPlayer(1).addCardToHand(CHARGE_CARD);
		String result = game.playCard(1, CHARGE_CARD.getName());

		assertEquals(true, result.contains("false"));
		assertEquals(1, game.getPlayer(1).getHandCards().size());
		assertEquals(0, game.getDiscardPileSize());
		assertEquals(0, game.getPlayer(0).getDisplayCards().size());
		assertEquals(0, game.getPlayer(2).getDisplayCards().size());
	}

	@Test
	public void withdrawAPlayerClearsTheirDisplay() {
		game.setNumPlayers(3);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.startGame();
		game.overrideTourColour(Config.BLUE);

		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);

		assertEquals("", game.withdrawPlayer(0));
		assertEquals(true, game.getPlayer(0).getDisplayCards().isEmpty());
		assertEquals(3, game.getDiscardPileSize());
	}

	@Test
	public void startGameWithFullTokenPool() {
		game.setNumPlayers(3);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.startGame();

		assertEquals(25, game.getTokenPool().size());
	}

	@Test
	public void addTokenToPlayer() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.startGame();

		game.addTokenToPlayer(0, Config.BLUE);
		assertEquals(true, game.getPlayer(0).getTokens().contains(Config.BLUE));
		assertEquals(24, game.getTokenPool().size());
	}

	@Test
	public void addTwoOfSameColourTokensToPlayer() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.startGame();

		game.addTokenToPlayer(0, Config.BLUE);
		game.addTokenToPlayer(0, Config.BLUE);
		assertEquals(1, game.getPlayer(0).getTokens().size());
		assertEquals(24, game.getTokenPool().size());
	}

	@Test
	public void addThreeTokensToPlayer() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.startGame();

		game.addTokenToPlayer(0, Config.BLUE);
		game.addTokenToPlayer(0, Config.YELLOW);
		game.addTokenToPlayer(0, Config.GREEN);
		assertEquals(3, game.getPlayer(0).getTokens().size());
		assertEquals(22, game.getTokenPool().size());
	}

	@Test
	public void playCounterChargeCardTwoPlayersTwoOfHighestValueToRemove() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

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
		assertEquals(3, game.getDiscardPileSize());
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
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

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
		assertEquals(5, game.getDiscardPileSize());
		assertEquals(1, game.getPlayer(0).getDisplayCards().size());
		assertEquals(4, game.getPlayer(1).getDisplayCards().size());

		// It should still contain this card
		assertEquals(true,
				game.getPlayer(0).getDisplayCards().contains(PURPLE_CARD_7));
	}

	@Test
	public void notAllowedToPlayCountercharge() {
		game.setNumPlayers(3);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);
		game.addPlayer(PLAYER_THREE_NAME, Config.PURPLE);

		game.getPlayer(0).addCardToDisplay(BLUE_CARD_3, Config.BLUE);

		game.getPlayer(1).addCardToHand(COUNTER_CHARGE_CARD);
		String result = game.playCard(1, COUNTER_CHARGE_CARD.getName());

		assertEquals(true, result.contains("false"));
		assertEquals(1, game.getPlayer(1).getHandCards().size());
		assertEquals(0, game.getDiscardPileSize());
		assertEquals(1, game.getPlayer(0).getDisplayCards().size());
		assertEquals(0, game.getPlayer(2).getDisplayCards().size());
	}

	@Test
	public void playDisgraceCard() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

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
		assertEquals(2, game.getDiscardPileSize());
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
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_7, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_7, Config.PURPLE);

		game.getPlayer(1).addCardToDisplay(MAIDEN_CARD, Config.BLUE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);

		game.getPlayer(1).addCardToHand(DISGRACE_CARD);
		String result = game.playCard(1, DISGRACE_CARD.getName());

		assertEquals(true, result.contains("false"));
		assertEquals(1, game.getPlayer(1).getHandCards().size());
		assertEquals(0, game.getDiscardPileSize());
		assertEquals(2, game.getPlayer(0).getDisplayCards().size());
		assertEquals(3, game.getPlayer(1).getDisplayCards().size());

		// It should still contain this card
		assertEquals(true,
				game.getPlayer(0).getDisplayCards().contains(PURPLE_CARD_7));
	}

	@Test
	public void playDisgraceCardAllSupporters() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

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
		assertEquals(3, game.getDiscardPileSize());
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
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.startGame();

		game.withdrawPlayer(0);

		assertEquals(false, game.getPlayers()[0].getHandCards().isEmpty());
		assertEquals(false, game.getTokenPool().isEmpty());
		assertEquals(2, game.getTournamentNumber());
	}

	@Test
	public void playerUnableToStartTournament() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED); // jack
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.getPlayer(0).addCardToHand(new ActionCard("Charge"));
		game.getPlayer(1).addCardToHand(new SupporterCard("", 0));

		// This means that player 0 will win and is supposed to start the next
		// tournament
		game.withdrawPlayer(1);

		assertEquals(PLAYER_TWO_NAME, game.getCurrentPlayer().getName());
	}

	@Test
	public void drawPileRefillsWhenEmpty() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

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
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.getPlayer(1).addCardToDisplay(MAIDEN_CARD, Config.BLUE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);

		game.getPlayer(0).addCardToDisplay(MAIDEN_CARD, Config.BLUE);
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);

		game.getPlayer(0).addCardToHand(RIPOSTE_CARD);

		String result = game.playCard(0, RIPOSTE_CARD.getName());
		assertEquals(true, result.contains("moreInformationNeeded"));
		assertEquals(2, game.getPlayer(1).getDisplayCards().size());
		assertEquals(0, game.getDiscardPileSize());
		assertEquals(2, game.getPlayer(0).getDisplayCards().size());
	}

	@Test
	public void playRiposteCardTwoPlayers() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.getPlayer(1).addCardToDisplay(MAIDEN_CARD, Config.BLUE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);

		game.getPlayer(0).addCardToDisplay(MAIDEN_CARD, Config.BLUE);
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);

		game.getPlayer(0).addCardToHand(RIPOSTE_CARD);

		// Player 0 will be taking the last card from player 1s display
		String info = "Riposte@" + PLAYER_TWO_NAME;
		game.playActionCardWithAdditionalInfo(0, info);

		assertEquals(1, game.getPlayer(1).getDisplayCards().size());
		assertEquals(1, game.getDiscardPileSize());
		assertEquals(3, game.getPlayer(0).getDisplayCards().size());
	}

	@Test
	public void notAllowedToPlayRiposteCard() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.getPlayer(1).addCardToDisplay(MAIDEN_CARD, Config.BLUE);

		game.getPlayer(0).addCardToDisplay(MAIDEN_CARD, Config.BLUE);
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);

		game.getPlayer(0).addCardToHand(RIPOSTE_CARD);

		String result = game.playCard(0, RIPOSTE_CARD.getName());
		assertEquals(true, result.contains("false"));
		assertEquals(1, game.getPlayer(1).getDisplayCards().size());
		assertEquals(0, game.getDiscardPileSize());
		assertEquals(2, game.getPlayer(0).getDisplayCards().size());
	}

	@Test
	public void tryPlayingUnhorseMoreInfoNeeded() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);
		game.overrideTourColour(Config.PURPLE);

		game.getPlayer(0).addCardToHand(UNHORSE_CARD);

		String result = game.playCard(0, UNHORSE_CARD.getName());
		assertEquals(true, result.contains("moreInformationNeeded"));
		assertEquals(0, game.getDiscardPileSize());
		assertEquals(Config.PURPLE, game.getTournamentColour());
	}

	@Test
	public void notAllowedToPlayUnhorse() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);
		game.overrideTourColour(Config.BLUE);

		game.getPlayer(0).addCardToHand(UNHORSE_CARD);

		String result = game.playCard(0, UNHORSE_CARD.getName());
		assertEquals(true, result.contains("false"));
		assertEquals(0, game.getDiscardPileSize());
		assertEquals(Config.BLUE, game.getTournamentColour());
	}

	@Test
	public void playUnhorseFromPurpleToYellow() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);
		game.overrideTourColour(Config.PURPLE);

		game.getPlayer(0).addCardToHand(UNHORSE_CARD);

		String info = "Unhorse@" + Config.YELLOW;
		game.playActionCardWithAdditionalInfo(0, info);

		assertEquals(1, game.getDiscardPileSize());
		assertEquals(Config.YELLOW, game.getTournamentColour());
	}

	@Test
	public void tryPlayingChangeWeaponMoreInfoNeeded() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);
		game.overrideTourColour(Config.RED);

		game.getPlayer(0).addCardToHand(CHANGE_WEAPON_CARD);

		String result = game.playCard(0, CHANGE_WEAPON_CARD.getName());
		assertEquals(true, result.contains("moreInformationNeeded"));
		assertEquals(0, game.getDiscardPileSize());
		assertEquals(Config.RED, game.getTournamentColour());
	}

	@Test
	public void notAllowedToPlayChangeWeapon() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);
		game.overrideTourColour(Config.PURPLE);

		game.getPlayer(0).addCardToHand(CHANGE_WEAPON_CARD);

		String result = game.playCard(0, CHANGE_WEAPON_CARD.getName());
		assertEquals(true, result.contains("false"));
		assertEquals(0, game.getDiscardPileSize());
		assertEquals(Config.PURPLE, game.getTournamentColour());
	}

	@Test
	public void playUnhorseFromBlueToYellow() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);
		game.overrideTourColour(Config.BLUE);

		game.getPlayer(0).addCardToHand(CHANGE_WEAPON_CARD);

		String info = "Change Weapon@" + Config.YELLOW;
		game.playActionCardWithAdditionalInfo(0, info);

		assertEquals(1, game.getDiscardPileSize());
		assertEquals(Config.YELLOW, game.getTournamentColour());
	}

	@Test
	public void notAllowedToPlayBreakLance() {
		game.setNumPlayers(3);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);
		game.addPlayer(PLAYER_THREE_NAME, Config.BLUE);

		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);

		game.getPlayer(2).addCardToDisplay(SQUIRE_CARD_2, Config.PURPLE);
		game.getPlayer(2).addCardToDisplay(SQUIRE_CARD_3, Config.PURPLE);

		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);

		game.getPlayer(0).addCardToHand(BREAK_LANCE_CARD);

		String result = game.playCard(0, BREAK_LANCE_CARD.getName());
		assertEquals(true, result.contains("false"));
		assertEquals(0, game.getDiscardPileSize());
		assertEquals(1, game.getPlayer(0).getHandCards().size());
		assertEquals(1, game.getPlayer(1).getDisplayCards().size());
		assertEquals(2, game.getPlayer(2).getDisplayCards().size());
	}

	@Test
	public void tryPlayingBreakLanceMoreInfoNeeded() {
		game.setNumPlayers(3);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);
		game.addPlayer(PLAYER_THREE_NAME, Config.BLUE);

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
		assertEquals(0, game.getDiscardPileSize());
		assertEquals(1, game.getPlayer(0).getHandCards().size());
	}

	@Test
	public void playBreakLance() {
		game.setNumPlayers(3);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);
		game.addPlayer(PLAYER_THREE_NAME, Config.BLUE);

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
		assertEquals(4, game.getDiscardPileSize());
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
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);
		game.addPlayer(PLAYER_THREE_NAME, Config.BLUE);

		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.PURPLE);

		game.getPlayer(2).addCardToDisplay(SQUIRE_CARD_2, Config.PURPLE);
		game.getPlayer(2).addCardToDisplay(SQUIRE_CARD_2, Config.PURPLE);

		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);

		game.getPlayer(0).addCardToHand(DODGE_CARD);

		String result = game.playCard(0, DODGE_CARD.getName());
		assertEquals(true, result.contains("moreInformationNeeded"));
		assertEquals(0, game.getDiscardPileSize());
		assertEquals(1, game.getPlayer(0).getHandCards().size());
	}

	@Test
	public void notAllowedToPlayDodge() {
		game.setNumPlayers(3);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);
		game.addPlayer(PLAYER_THREE_NAME, Config.BLUE);

		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);

		game.getPlayer(0).addCardToHand(DODGE_CARD);

		String result = game.playCard(0, DODGE_CARD.getName());
		assertEquals(true, result.contains("false"));
		assertEquals(0, game.getDiscardPileSize());
		assertEquals(1, game.getPlayer(0).getHandCards().size());
	}

	@Test
	public void playDodge() {
		game.setNumPlayers(3);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);
		game.addPlayer(PLAYER_THREE_NAME, Config.BLUE);

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

		assertEquals(2, game.getDiscardPileSize());
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
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.PURPLE);

		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);

		game.getPlayer(0).addCardToHand(RETREAT_CARD);

		String result = game.playCard(0, RETREAT_CARD.getName());
		assertEquals(true, result.contains("moreInformationNeeded"));
		assertEquals(0, game.getDiscardPileSize());
		assertEquals(1, game.getPlayer(0).getHandCards().size());
	}

	@Test
	public void notAllowedToPlayRetreat() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.PURPLE);

		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);

		game.getPlayer(0).addCardToHand(RETREAT_CARD);

		String result = game.playCard(0, RETREAT_CARD.getName());
		assertEquals(true, result.contains("false"));
		assertEquals(0, game.getDiscardPileSize());
		assertEquals(1, game.getPlayer(0).getHandCards().size());
	}

	@Test
	public void playRetreatCard() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.PURPLE);

		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_2, Config.PURPLE);

		game.getPlayer(0).addCardToHand(RETREAT_CARD);

		String info = "Retreat@" + SQUIRE_CARD_2.getName();
		game.playActionCardWithAdditionalInfo(0, info);
		assertEquals(1, game.getDiscardPileSize());
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
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.getPlayer(1).addCardToHand(PURPLE_CARD_3);
		game.getPlayer(1).addCardToHand(SQUIRE_CARD_2);

		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);

		game.getPlayer(0).addCardToHand(KNOCK_DOWN_CARD);

		String result = game.playCard(0, KNOCK_DOWN_CARD.getName());
		assertEquals(true, result.contains("moreInformationNeeded"));
		assertEquals(0, game.getDiscardPileSize());
		assertEquals(1, game.getPlayer(0).getHandCards().size());
	}

	@Test
	public void notAllowedToPlayKnockDown() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);

		game.getPlayer(0).addCardToHand(KNOCK_DOWN_CARD);

		String result = game.playCard(0, KNOCK_DOWN_CARD.getName());
		assertEquals(true, result.contains("false"));
		assertEquals(0, game.getDiscardPileSize());
		assertEquals(1, game.getPlayer(0).getHandCards().size());
	}

	@Test
	public void playKnockDown() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.getPlayer(1).addCardToHand(PURPLE_CARD_3);
		game.getPlayer(1).addCardToHand(SQUIRE_CARD_2);

		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);

		game.getPlayer(0).addCardToHand(KNOCK_DOWN_CARD);

		String info = "Knock Down@" + PLAYER_TWO_NAME;
		game.playActionCardWithAdditionalInfo(0, info);
		assertEquals(1, game.getDiscardPileSize());
		assertEquals(1, game.getPlayer(0).getHandCards().size());
		assertEquals(1, game.getPlayer(1).getHandCards().size());
	}

	@Test
	public void tryPlayingOutwitMoreInfoNeeded() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(1).addCardToDisplay(SQUIRE_CARD_2, Config.PURPLE);

		game.getPlayer(0).addSpecialCard(SHIELD_CARD);
		game.getPlayer(0).addSpecialCard(STUNNED_CARD);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);

		game.getPlayer(0).addCardToHand(OUTWIT_CARD);

		String result = game.playCard(0, OUTWIT_CARD.getName());
		assertEquals(true, result.contains("moreInformationNeeded"));
		assertEquals(0, game.getDiscardPileSize());
		assertEquals(1, game.getPlayer(0).getHandCards().size());
	}

	@Test
	public void notAllowedToPlayOutwit() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);

		game.getPlayer(0).addCardToHand(OUTWIT_CARD);

		String result = game.playCard(0, OUTWIT_CARD.getName());
		assertEquals(true, result.contains("false"));
		assertEquals(0, game.getDiscardPileSize());
		assertEquals(1, game.getPlayer(0).getHandCards().size());
	}

	@Test
	public void playOutwitSwitchingDisplayForShield() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.getPlayer(1).addSpecialCard(SHIELD_CARD);

		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);

		game.getPlayer(0).addCardToHand(OUTWIT_CARD);

		String info = "Outwit@" + PURPLE_CARD_3.getName() + ","
				+ PLAYER_TWO_NAME + "," + SHIELD_CARD.getName();
		game.playActionCardWithAdditionalInfo(0, info);
		assertEquals(1, game.getDiscardPileSize());
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
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

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
	}
	
	@Test
	public void withdrawPlayerRemovesShieldAndStunned() {
		game.setNumPlayers(3);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.startGame();
		game.overrideTourColour(Config.BLUE);

		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_2, Config.BLUE);
		game.getPlayer(0).addSpecialCard(SHIELD_CARD);
		game.getPlayer(0).addSpecialCard(STUNNED_CARD);
		
		assertEquals("", game.withdrawPlayer(0));
		assertEquals(true, game.getPlayer(0).getDisplayCards().isEmpty());
		assertEquals(false, game.getPlayer(0).hasSpecialCard("Shield"));
		assertEquals(false, game.getPlayer(0).hasSpecialCard("Stunned"));
		assertEquals(5, game.getDiscardPileSize());
	}	
	
	public void tryPlayingAdaptMoreInfoNeeded() {
		game.setNumPlayers(3);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);
		game.addPlayer(PLAYER_THREE_NAME, Config.PURPLE);

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
		assertEquals(1, game.getDiscardPileSize());
		assertEquals(0, game.getPlayer(0).getHandCards().size());
	}

	@Test
	public void notAllowedToPlayAdapt() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(1).addCardToDisplay(PURPLE_CARD_7, Config.PURPLE);

		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);

		game.getPlayer(0).addCardToHand(ADAPT_CARD);

		String result = game.playCard(0, ADAPT_CARD.getName());

		assertEquals(true, result.contains("false"));
		assertEquals(0, game.getDiscardPileSize());
		assertEquals(1, game.getPlayer(0).getHandCards().size());
	}

	@Test
	public void adaptChoicesGiven() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

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
		
		game.adaptCardsChosen(0, "3-" + PURPLE_CARD_3.getName());

		assertEquals(5, game.getDiscardPileSize());
		
		assertEquals(1, game.getPlayer(0).getDisplayCards().size());
		assertEquals(false, game.getPlayer(0).getDisplayCards().contains(SQUIRE_CARD_3));
		assertEquals(true, game.getPlayer(0).getDisplayCards().contains(PURPLE_CARD_3));

		assertEquals(3, game.getPlayer(1).getDisplayCards().size());
		assertEquals(false, game.getPlayer(1).getDisplayCards().contains(PURPLE_CARD_3));
		assertEquals(true, game.getPlayer(1).getDisplayCards().contains(SQUIRE_CARD_3));
		assertEquals(true, game.getPlayer(1).getDisplayCards().contains(SQUIRE_CARD_2));
		assertEquals(true, game.getPlayer(1).getDisplayCards().contains(GREEN_CARD_1));
	}
	
	@Test
	public void tryPlayingStunnedCard() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

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
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.getPlayer(0).addCardToHand(STUNNED_CARD);
		game.getPlayer(0).addCardToHand(BLUE_CARD_3);
		game.getPlayer(0).addCardToHand(BREAK_LANCE_CARD);

		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_3, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(PURPLE_CARD_7, Config.PURPLE);
		game.getPlayer(0).addCardToDisplay(SQUIRE_CARD_3, Config.PURPLE);

		game.getPlayer(1).addCardToHand(BLUE_CARD_3);
		game.getPlayer(1).addCardToHand(SQUIRE_CARD_3);
		game.getPlayer(1).addCardToHand(SQUIRE_CARD_2);

		String info = "Stunned@"+PLAYER_TWO_NAME;
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
		
		game.goToNextPlayer();
		game.goToNextPlayer();
		
		game.playCard(1, SQUIRE_CARD_3.getName());
		assertEquals(1, game.getPlayer(1).getHandCards().size());
		assertEquals(2, game.getPlayer(1).getDisplayCards().size());
	}
	
	@Test
	public void withdrawPlayerGoesToTheCorrectPlayer() {
		game.setNumPlayers(4);
		game.addPlayer(PLAYER_ONE_NAME, Config.PURPLE);
		game.addPlayer(PLAYER_TWO_NAME, Config.RED);
		game.addPlayer(PLAYER_THREE_NAME, Config.BLUE);
		game.addPlayer(PLAYER_FOUR_NAME, Config.YELLOW);
		
		game.startGame();
		game.withdrawPlayer(1);
				
		assertEquals(PLAYER_THREE_NAME, game.getCurrentPlayer().getName());
	}
	
	@Test
	public void playDropWeaponCardAskForIvanhoe() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.overrideTourColour(Config.RED);
		game.getPlayer(0).addCardToHand(DROP_WEAPON_CARD);
		
		game.getPlayer(1).addCardToHand(IVANHOE_CARD);

		String result = game.playCard(0, DROP_WEAPON_CARD.getName());
		assertEquals(true, result.contains("Ivanhoe"));
		assertEquals(1, game.getPlayer(0).getHandCards().size());
		assertEquals(0, game.getDiscardPileSize());
	}
	
	@Test
	public void noToIvanhoeDropWeaponGetsPlayed() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.overrideTourColour(Config.RED);
		game.getPlayer(0).addCardToHand(DROP_WEAPON_CARD);
		
		game.getPlayer(1).addCardToHand(IVANHOE_CARD);

		String info = "No="+DROP_WEAPON_CARD+"=0";
		game.processIvanhoeCard(info);
		
		assertEquals(1, game.getPlayer(1).getHandCards().size());
		assertEquals(0, game.getPlayer(0).getHandCards().size());
		assertEquals(Config.GREEN, game.getTournamentColour());
		assertEquals(1, game.getDiscardPileSize());
	}
	
	@Test
	public void yesToIvanhoeDropWeaponGetsPlayed() {
		game.setNumPlayers(2);
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.overrideTourColour(Config.RED);
		game.getPlayer(0).addCardToHand(DROP_WEAPON_CARD);
		
		game.getPlayer(1).addCardToHand(IVANHOE_CARD);

		String info = "Yes="+DROP_WEAPON_CARD+"=0";
		game.processIvanhoeCard(info);
		
		assertEquals(0, game.getPlayer(1).getHandCards().size());
		assertEquals(0, game.getPlayer(0).getHandCards().size());
		assertEquals(Config.RED, game.getTournamentColour());
		assertEquals(2, game.getDiscardPileSize());
	}

	@After
	public void tearDown() {
		game = null;
	}
}
