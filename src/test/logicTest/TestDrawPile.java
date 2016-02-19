package logicTest;

import static org.junit.Assert.*;
import logic.DrawPile;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.PrintHelper;

public class TestDrawPile {
	
	@Test
	public void initializeDeck() {
		DrawPile cards = new DrawPile();
		
		//PrintHelper.printCards(cards.getAllCards());
		
		assertNotEquals(null, cards.getCard());
	}
	
}
