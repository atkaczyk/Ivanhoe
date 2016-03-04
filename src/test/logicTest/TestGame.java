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

	// Action Cards
	private static final Card DROP_WEAPON_CARD = new ActionCard("Drop Weapon");
	private static final Card OUTMANEUVER_CARD = new ActionCard("Outmaneuver");
	private static final Card CHARGE_CARD = new ActionCard("Charge");

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
		assertEquals(false, game.getTokens().isEmpty());
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

		assertEquals("false", game.playCard(0, PURPLE_CARD_3.getName()));
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

		assertEquals("true", game.playCard(0, OUTMANEUVER_CARD.getName()));
		assertEquals(0, game.getPlayer(0).getHandCards().size());
		assertEquals(1, game.getDiscardPileSize());
		assertEquals(1, game.getPlayer(1).getDisplayCards().size());

		// It should still contain this card
		assertEquals(true,
				game.getPlayer(1).getDisplayCards().contains(MAIDEN_CARD));
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

		assertEquals("false", game.playCard(0, DROP_WEAPON_CARD.getName()));
		assertEquals(1, game.getPlayer(0).getHandCards().size());
		assertEquals(Config.PURPLE, game.getTournamentColour());
		assertEquals(0, game.getDiscardPileSize());
	}

	@After
	public void tearDown() {
		game = null;
	}
}
