package testSuite;

import logicTest.TestActionCard;
import logicTest.TestDeck;
import logicTest.TestGame;
import logicTest.TestPlayer;
import logicTest.TestColourCard;
import logicTest.TestSupporterCard;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestGame.class,
	TestPlayer.class,
	TestColourCard.class,
	TestActionCard.class,
	TestDeck.class,
	TestSupporterCard.class
})

public class TestSuite {
	
}
