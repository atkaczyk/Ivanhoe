package testSuite;

import logicTest.TestGame;
import logicTest.TestPlayer;
import logicTest.TestSimpleCard;
import logicTest.TestActionCard;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestGame.class,
	TestPlayer.class,
	TestSimpleCard.class,
	TestActionCard.class
})

public class TestSuite {
	
}
