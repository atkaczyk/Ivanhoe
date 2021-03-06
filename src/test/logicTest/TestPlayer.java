package logicTest;

import static org.junit.Assert.*;
import logic.ActionCard;
import logic.Card;
import logic.ColourCard;
import logic.Player;
import logic.SimpleCard;
import logic.SupporterCard;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.Config;

public class TestPlayer {
	private static final String NAME = "Sam";
	private static final Card SQUIRE_CARD = new SupporterCard("Squire", 3);
	private static final Card MAIDEN_CARD = new SupporterCard("Maiden 6", 6);
	private static final Card SHIELD_CARD = new ActionCard("Shield");
	private static final Card GREEN_CARD = new ColourCard("Green (No Weapon) 1", 1, Config.GREEN);
	private static final Card BLUE_CARD = new ColourCard("Blue (Axe) 2", 2, Config.BLUE);
	private static final Card YELLOW_CARD = new ColourCard("Yellow", 2, Config.YELLOW);
	private static final Card RED_CARD = new ColourCard("Red", 2, Config.RED);
	private static final Card PURPLE_CARD = new ColourCard("Purple", 2, Config.PURPLE);
	
	private static final Card IVANHOE_CARD = new ActionCard("Ivanhoe");
	
	Player player;
	
	
	@Before
	public void setUp() {
		player = new Player(NAME);
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
		
		assertEquals(((SupporterCard) SQUIRE_CARD).getNumber(), player.getDisplayTotal(Config.YELLOW));
	}
	
	@Test
	public void addSquireCardToDisplayGreenTournamentColour() {
		player.addCardToDisplay(SQUIRE_CARD, Config.GREEN);
		
		assertEquals(1, player.getDisplayTotal(Config.GREEN));
	}
	
	@Test
	public void addFirstMaidenCardToDisplay() {
		player.addCardToDisplay(MAIDEN_CARD, Config.BLUE);
		
		assertEquals(((SupporterCard) MAIDEN_CARD).getNumber(), player.getDisplayTotal(Config.BLUE));
	}
	
	@Test
	public void addSecondMaidenCardToDisplay() {
		player.addCardToDisplay(MAIDEN_CARD, Config.BLUE);
		player.addCardToDisplay(MAIDEN_CARD, Config.BLUE);
		
		assertEquals(6, player.getDisplayTotal(Config.BLUE));
	}
	
	@Test
	public void addOneMaidenCardGreenTournament() {
		player.addCardToDisplay(MAIDEN_CARD, Config.GREEN);
		
		assertEquals(1, player.getDisplayTotal(Config.GREEN));
	}
	
	@Test
	public void addToken() {
		player.addToken(Config.RED);
		
		assertEquals(1, player.getTokens().size());
	}
	
	@Test
	public void addTokenOfSameColour() {
		player.addToken(Config.BLUE);
		player.addToken(Config.BLUE);
		player.addToken(Config.RED);
		
		assertEquals(2, player.getTokens().size());
	}
	
	@Test
	public void removeToken() {
		player.addToken(Config.RED);
		player.removeToken(Config.RED);
		
		assertEquals(false, player.getTokens().contains(Config.RED));
	}
	
	@Test
	public void addShieldCard() {
		player.addSpecialCard(SHIELD_CARD);
		
		assertEquals(true, player.hasSpecialCard("Shield"));
	}
	
	@Test
	public void playerWithdraws() {
		player.withdraw();
		
		assertEquals(true, player.isWithdrawn());
	}
	
	@Test
	public void clearDisplay() {
		player.addCardToDisplay(MAIDEN_CARD, Config.BLUE);
		player.clearDisplay();
		
		assertEquals(0, player.getDisplayTotal(Config.BLUE));
		assertEquals(0, player.getDisplayCards().size());
	}
	
	@Test
	public void addGreenCardOnGreenTournament() {
		player.addCardToDisplay(GREEN_CARD, Config.GREEN);
		
		assertEquals(1, player.getDisplayTotal(Config.GREEN));
		assertEquals(1, player.getDisplayCards().size());
	}
	
	@Test
	public void addGreenCardOnBlueTournament() {
		player.addCardToDisplay(GREEN_CARD, Config.BLUE);
		
		assertEquals(0, player.getDisplayTotal(Config.BLUE));
		assertEquals(0, player.getDisplayCards().size());
	}
	
	@Test
	public void addBlueCardOnBlueTournament() {
		player.addCardToDisplay(BLUE_CARD, Config.BLUE);
		
		assertEquals(((ColourCard) BLUE_CARD).getNumber(), player.getDisplayTotal(Config.BLUE));
		assertEquals(1, player.getDisplayCards().size());
	}
	
	@Test
	public void addBlueCardOnGreenTournament() {
		player.addCardToDisplay(BLUE_CARD, Config.GREEN);
		
		assertEquals(0, player.getDisplayTotal(Config.GREEN));
		assertEquals(0, player.getDisplayCards().size());
	}
	
	@Test
	public void changingFromGreenToBlue() {
		player.addCardToDisplay(BLUE_CARD, Config.BLUE);
		
		assertEquals(1, player.getDisplayTotal(Config.GREEN));
		assertEquals(((ColourCard) BLUE_CARD).getNumber(), player.getDisplayTotal(Config.BLUE));
	}
	
	@Test
	public void isWinnerWithFourColours() {
		player.addToken(Config.PURPLE);
		player.addToken(Config.BLUE);
		player.addToken(Config.GREEN);
		player.addToken(Config.YELLOW);
		
		assertEquals(true, player.isWinnerOfGame(4));
	}
	
	@Test
	public void notWinnerWithFourColours() {
		player.addToken(Config.PURPLE);
		player.addToken(Config.BLUE);
		player.addToken(Config.GREEN);
		
		assertEquals(false, player.isWinnerOfGame(4));
	}
	
	@Test
	public void isWinnerWithFiveColours() {
		player.addToken(Config.PURPLE);
		player.addToken(Config.BLUE);
		player.addToken(Config.GREEN);
		player.addToken(Config.YELLOW);
		player.addToken(Config.RED);
		
		assertEquals(true, player.isWinnerOfGame(5));
	}
	
	@Test
	public void notWinnerWithFiveColours() {
		player.addToken(Config.PURPLE);
		player.addToken(Config.BLUE);
		player.addToken(Config.GREEN);
		
		assertEquals(false, player.isWinnerOfGame(5));
	}
	
	@Test
	public void addBlueCardToYellowTournament() {
		player.addCardToDisplay(BLUE_CARD, Config.YELLOW);
		
		assertEquals(0, player.getDisplayTotal(Config.YELLOW));
		assertEquals(0, player.getDisplayCards().size());
	}
	
	@Test
	public void addYellowCardToYellowTournament() {
		player.addCardToDisplay(YELLOW_CARD, Config.YELLOW);
		
		assertEquals(((SimpleCard) YELLOW_CARD).getNumber(), player.getDisplayTotal(Config.YELLOW));
		assertEquals(1, player.getDisplayCards().size());
	}
	
	@Test
	public void addBlueCardToRedTournament() {
		player.addCardToDisplay(BLUE_CARD, Config.RED);
		
		assertEquals(0, player.getDisplayTotal(Config.RED));
		assertEquals(0, player.getDisplayCards().size());
	}
	
	@Test
	public void addRedCardToRedTournament() {
		player.addCardToDisplay(RED_CARD, Config.RED);
		
		assertEquals(((SimpleCard) RED_CARD).getNumber(), player.getDisplayTotal(Config.RED));
		assertEquals(1, player.getDisplayCards().size());
	}
	
	public void addRedCardToBlueTournament() {
		player.addCardToDisplay(RED_CARD, Config.BLUE);
		
		assertEquals(0, player.getDisplayTotal(Config.BLUE));
		assertEquals(0, player.getDisplayCards().size());
	}
	
	@Test
	public void addBlueCardToBlueTournament() {
		player.addCardToDisplay(BLUE_CARD, Config.BLUE);
		
		assertEquals(((SimpleCard) BLUE_CARD).getNumber(), player.getDisplayTotal(Config.BLUE));
		assertEquals(1, player.getDisplayCards().size());
	}
	
	public void addRedCardToPurpleTournament() {
		player.addCardToDisplay(RED_CARD, Config.PURPLE);
		
		assertEquals(0, player.getDisplayTotal(Config.PURPLE));
		assertEquals(0, player.getDisplayCards().size());
	}
	
	@Test
	public void addPurpleCardToPurpleTournament() {
		player.addCardToDisplay(PURPLE_CARD, Config.PURPLE);
		
		assertEquals(((SimpleCard) PURPLE_CARD).getNumber(), player.getDisplayTotal(Config.PURPLE));
		assertEquals(1, player.getDisplayCards().size());
	}
	
	@Test
	public void addIvanhoeToHand() {
		player.addCardToHand(IVANHOE_CARD);
		
		assertEquals(true, player.hasIvanhoeCard());
	}
	
	@After
	public void tearDown() {
		player = null;
	}
}
