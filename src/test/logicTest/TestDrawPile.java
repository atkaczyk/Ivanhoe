package logicTest;

import static org.junit.Assert.*;
import logic.ActionCard;
import logic.Card;
import logic.DrawPile;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestDrawPile {
	private static final Card ACTION_CARD = new ActionCard("Stunned");
	
	@Test
	public void initializeDeck() {
		DrawPile cards = new DrawPile();
		
		assertNotEquals(0, cards.getNumCards());
	}
	
	@Test
	public void addCard() {
		DrawPile cards = new DrawPile();
		int before = cards.getNumCards();
		cards.addCard(ACTION_CARD);
		assertEquals(before+1, cards.getNumCards());
	}
	
	@Test
	public void clear() {
		DrawPile cards = new DrawPile();
		cards.clearCards();
		
		assertEquals(0, cards.getNumCards());
	}
	
}
