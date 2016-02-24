package logicTest;

import static org.junit.Assert.*;
import logic.Card;
import logic.ColourCard;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.Config;

public class TestColourCard {
	private static final String CARD_NAME = "Purple";
	private static final int CARD_NUMBER = 2;
	private static final int CARD_COLOUR = Config.PURPLE;
	
	@Test
	public void initializeCard() {
		Card card = new ColourCard(CARD_NAME, CARD_NUMBER, CARD_COLOUR);
		
		assertEquals(CARD_NAME, card.getName());
		assertEquals(CARD_NUMBER, ((ColourCard) card).getNumber());
		assertEquals(CARD_COLOUR, ((ColourCard) card).getColour());
	}
}