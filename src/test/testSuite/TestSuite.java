package testSuite;

import logicTest.TestActionCard;
import logicTest.TestDeck;
import logicTest.TestGame;
import logicTest.TestPlayer;
import logicTest.TestSimpleCard;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestGame.class,
	TestPlayer.class,
	TestSimpleCard.class,
	TestActionCard.class,
	TestDeck.class
})

public class TestSuite {
	
}
