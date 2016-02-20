package logicTest;

import static org.junit.Assert.*;

import java.util.ArrayDeque;

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
	private static final ArrayDeque<Card> HAND_WITH_ALL_COLOURS = new ArrayDeque<Card>();
	static {
		HAND_WITH_ALL_COLOURS.add(new ColourCard("", 2, Config.RED));
		HAND_WITH_ALL_COLOURS.add(new ColourCard("", 2, Config.YELLOW));
		HAND_WITH_ALL_COLOURS.add(new ColourCard("", 2, Config.GREEN));
		HAND_WITH_ALL_COLOURS.add(new ColourCard("", 2, Config.BLUE));
		HAND_WITH_ALL_COLOURS.add(new ColourCard("", 2, Config.PURPLE));
	}
	private static Card SUPPORTER_CARD = new SupporterCard("", 6);

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

		assertEquals(true, game.getPlayers()[0].getActive());
		assertEquals(true, game.getPlayers()[1].getActive());
	}

	@Test
	public void startGameTwoPlayersFirstPlayerStarts() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);

		game.startGame();

		assertEquals(PLAYER_ONE_NAME, game.getCurrentPlayer().getName());
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

		game.getCurrentPlayer().addCardToHand(SUPPORTER_CARD);

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

		game.getCurrentPlayer().addCardToHand(SUPPORTER_CARD);

		game.setTournamentColour(Config.PURPLE);
		assertEquals(false, game.setTournamentColour(Config.PURPLE));
	}

	@After
	public void tearDown() {
		game = null;
	}
}
