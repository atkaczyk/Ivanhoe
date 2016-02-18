package logicTest;

import static org.junit.Assert.*;
import logic.Player;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestPlayer {
	public static final String NAME = "Sam";
	
	Player player;
	
	@Before
	public void setUp() {
		player = new Player();
	}
	
	@Test
	public void playerNameSet() {
		player.setName(NAME);
		
		assertEquals(NAME, player.getName());
	}
	
	@After
	public void tearDown() {
		player = null;
	}
}
