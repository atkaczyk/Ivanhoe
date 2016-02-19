package logicTest;

import static org.junit.Assert.*;
import logic.Card;
import logic.ActionCard;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.Config;

public class TestActionCard {
	private static final String CARD_NAME = "Dodge";
	
	@Test
	public void initializeCard() {
		Card card = new ActionCard(CARD_NAME);
		
		assertEquals(CARD_NAME, card.getName());
	}
}