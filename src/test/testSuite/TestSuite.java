package testSuite;

import logicTest.TestActionCard;
import logicTest.TestDrawPile;
import logicTest.TestGame;
import logicTest.TestPlayer;
import logicTest.TestColourCard;
import logicTest.TestSupporterCard;
import networkTest.TestClient;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestGame.class,
	TestPlayer.class,
	TestColourCard.class,
	TestActionCard.class,
	TestDrawPile.class,
	TestSupporterCard.class//,
	//TestClient.class
})

public class TestSuite {
	
}
