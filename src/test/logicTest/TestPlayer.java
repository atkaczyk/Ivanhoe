package logicTest;

import static org.junit.Assert.*;
import logic.Card;
import logic.Player;
import logic.SupporterCard;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestPlayer {
	private static final String NAME = "Sam";
	private static final Card CARD = new SupporterCard("Squire", 3);
	
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
	
	@Test
	public void addCard() {
		player.addCard(CARD);
		
		assertEquals(false, player.isEmptyHanded());
	}
	
	@After
	public void tearDown() {
		player = null;
	}
}
