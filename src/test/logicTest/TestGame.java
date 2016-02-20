package logicTest;

import static org.junit.Assert.*;
import logic.Game;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.Config;

public class TestGame {
	private static final int NUM_OF_PLAYERS = 2;
	private static final String PLAYER_ONE_NAME = "Jack";
	private static final String PLAYER_TWO_NAME = "Chloe";
	
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
		
		assertEquals(1, game.getTournamentNumber());
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
	public void startTournamentTwoPlayersFirstPlayerStarts() {
		game.addPlayer(PLAYER_ONE_NAME, Config.RED);
		game.addPlayer(PLAYER_TWO_NAME, Config.PURPLE);
		
		game.startGame();
		
		assertEquals(PLAYER_ONE_NAME, game.getCurrentPlayer().getName());
	}
	
	@Test
	public void startTournamentTwoPlayersSecondPlayerStarts() {
		game.addPlayer(PLAYER_ONE_NAME, Config.PURPLE);
		game.addPlayer(PLAYER_TWO_NAME, Config.RED);
		
		game.startGame();
		
		assertEquals(PLAYER_TWO_NAME, game.getCurrentPlayer().getName());
	}
	
	@After
	public void tearDown() {
		game = null;
	}
}
