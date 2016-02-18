package logicTest;

import static org.junit.Assert.*;
import logic.Game;
import logic.Player;

import org.junit.Test;

public class TestPlayer {
	public static final String NAME = "Sam";
	
	@Test
	public void playerNameSet() {
		Player player = new Player();
		player.setName(NAME);
		
		assertEquals(NAME, player.getName());
	}
}
