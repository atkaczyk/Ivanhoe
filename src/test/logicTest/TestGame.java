package logicTest;

import static org.junit.Assert.*;
import logic.Game;

import org.junit.Test;

public class TestGame {
	private static final int NUM_OF_PLAYERS = 2;
	
	@Test
	public void numPlayersSet() {
		Game game = new Game();
		game.setNumPlayers(NUM_OF_PLAYERS);
		
		assertEquals(NUM_OF_PLAYERS, game.getNumPlayers());
	}
}
