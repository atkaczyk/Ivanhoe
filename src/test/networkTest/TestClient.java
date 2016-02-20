package networkTest;

import static org.junit.Assert.*;

import network.Server;
import network.Client;

import org.junit.After;
import org.junit.Test;

import utils.Config;

public class TestClient {
	public static final Boolean CONNECTED = true;
	private static final String JOIN_COMMAND_ONE = "join sophia 123.4.5.6";
	private static final String SELECT_COMMAND_ONE = "select sophia joe swing 1 duck 1";
	private static final String ROLL_COMMAND_ONE = "roll sophia 1";
	private static final String PLAYER_NAME_ONE = "sophia";
	private static final int ONE_PLAYER = 1;

	private static Server server = new Server(Config.DEFAULT_PORT);
	private static Client clientOne = new Client(Config.DEFAULT_HOST,
			Config.DEFAULT_PORT);
	private static Client clientTwo = new Client(Config.DEFAULT_HOST,
			Config.DEFAULT_PORT);
	private static Client clientThree = new Client(Config.DEFAULT_HOST,
			Config.DEFAULT_PORT);

	@Test
	public void connect() {
		assertEquals(clientOne.isConnected(), CONNECTED);
		assertEquals(clientTwo.isConnected(), CONNECTED);
		assertEquals(clientThree.isConnected(), CONNECTED);
	}

	@Test
	public void testMultipleConnections() {
		assertEquals(clientOne.isConnected(), CONNECTED);
		assertEquals(clientTwo.isConnected(), CONNECTED);
		assertEquals(clientThree.isConnected(), CONNECTED);
	}

//	@Test
//	public void oneClientJoinGame() {
//		server.handle(clientOne.getID(), JOIN_COMMAND_ONE);
//
//		assertEquals(server.getGame().getPlayers().length, ONE_PLAYER);
//	}
	
	@Test
	public void drawTokenFromPool(){
		server.handle(clientOne.getID(), "drawToken");
		
		assertEquals(1, server.getGame().getTokensPicked());
		
	}

	//@Test
	//public void oneClientSelectCommand() {
		//server.handle(clientTwo.getID(), JOIN_COMMAND_ONE);
		//server.handle(clientTwo.getID(), SELECT_COMMAND_ONE);

		//assertNotEquals(server.getGame().getPlayers().get(PLAYER_NAME_ONE)
				//.getAttack(), null);
	//}
	
	//@Test
	//public void clientRoll() {
	//	server.handle(clientTwo.getID(), JOIN_COMMAND_ONE);
	//	server.handle(clientTwo.getID(), SELECT_COMMAND_ONE);
//		server.handle(clientTwo.getID(), ROLL_COMMAND_ONE);
//		
//		assertNotEquals(server.getRolls().size(), 0);
//	}
	
	
	
	@After
	public void tearDown() {
		server.resetGame();
	}

}
