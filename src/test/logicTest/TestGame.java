package logicTest;

import static org.junit.Assert.*;
import logic.Game;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestGame {
	private static final int NUM_OF_PLAYERS = 2;
	private static final String PLAYER_ONE_NAME = "Sam";
	
	Game game;
	
	@Before
	public void setUp() {
		game = new Game();
	}
	
	@Test
	public void numPlayersSet() {
		game.setNumPlayers(NUM_OF_PLAYERS);
		
		assertEquals(NUM_OF_PLAYERS, game.getNumPlayers());
	}
	
	@Test
	public void addPlayer() {
		game.setNumPlayers(NUM_OF_PLAYERS);
		game.addPlayer(PLAYER_ONE_NAME);
		
		assertNotEquals(null, game.getPlayers()[0]);
	}
	
	@After
	public void tearDown() {
		game = null;
	}
}
