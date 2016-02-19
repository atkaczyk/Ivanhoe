package logicTest;

import static org.junit.Assert.*;
import logic.Deck;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestDeck {
	
	@Test
	public void initializeDeck() {
		Deck deck = new Deck();
		
		assertNotEquals(null, deck.getCard());
	}
	
}
