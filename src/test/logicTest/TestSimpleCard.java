package logicTest;

import static org.junit.Assert.*;
import logic.Card;
import logic.SimpleCard;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.Config;

public class TestSimpleCard {
	private static final String CARD_NAME = "Supporter";
	private static final int CARD_NUMBER = 2;
	
	@Test
	public void nameSet() {
		Card card = new SimpleCard(CARD_NAME, CARD_NUMBER);
		
		assertEquals(CARD_NAME, card.getName());
	}
	
	@Test
	public void numberSet() {
		Card card = new SimpleCard(CARD_NAME, CARD_NUMBER);
		
		assertEquals(CARD_NUMBER, ((SimpleCard) card).getNumber());
	}
}