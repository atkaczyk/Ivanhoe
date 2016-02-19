package logicTest;

import static org.junit.Assert.*;
import logic.Card;
import logic.ColourCard;
import logic.SupporterCard;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.Config;

public class TestSupporterCard {
	private static final String CARD_NAME = "Supporter";
	private static final int CARD_NUMBER = 2;
	
	@Test
	public void initializeCard() {
		Card card = new SupporterCard(CARD_NAME, CARD_NUMBER);
		
		assertEquals(CARD_NAME, card.getName());
		assertEquals(CARD_NUMBER, ((ColourCard) card).getNumber());
	}
}