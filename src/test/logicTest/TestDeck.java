package logicTest;

import static org.junit.Assert.*;
import logic.Deck;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.PrintHelper;

public class TestDeck {
	
	@Test
	public void initializeDeck() {
		Deck deck = new Deck();
		
		PrintHelper.printCards(deck.getAllCards());
		
		assertNotEquals(null, deck.getCard());
	}
	
}
