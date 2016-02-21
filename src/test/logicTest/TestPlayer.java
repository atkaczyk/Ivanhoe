package logicTest;

import static org.junit.Assert.*;
import logic.Card;
import logic.Player;
import logic.SupporterCard;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.Config;

public class TestPlayer {
	private static final String NAME = "Sam";
	private static final Card SQUIRE_CARD = new SupporterCard("Squire", 3);
	
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
	public void addCardToHand() {
		player.addCardToHand(SQUIRE_CARD);
		
		assertEquals(true, player.getHandCards().contains(SQUIRE_CARD));
	}
	
	@Test
	public void addCardToDisplay() {
		player.addCardToDisplay(SQUIRE_CARD, Config.YELLOW);
		
		assertEquals(true, player.getDisplayCards().contains(SQUIRE_CARD));
	}
	
	@Test
	public void addSquireCardToDisplayNormalTournamentColour() {
		player.addCardToDisplay(SQUIRE_CARD, Config.YELLOW);
		
		assertEquals(((SupporterCard) SQUIRE_CARD).getNumber(), player.getDisplayTotal());
	}
	
	@After
	public void tearDown() {
		player = null;
	}
}
