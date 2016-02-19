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
	private static final int CARD_COLOUR = Config.WHITE;
	
	@Test
	public void initializeCard() {
		Card card = new SimpleCard(CARD_NAME, CARD_NUMBER, CARD_COLOUR);
		
		assertEquals(CARD_NAME, card.getName());
		assertEquals(CARD_NUMBER, ((SimpleCard) card).getNumber());
		assertEquals(CARD_COLOUR, ((SimpleCard) card).getColour());
	}
}