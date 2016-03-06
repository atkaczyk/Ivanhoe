package networkTest;

import static org.junit.Assert.*;

import network.Server;
import network.Client;
import logic.Game;

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
	
	private Game game = new Game();

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
	
	@Test
	public void lauchGameReadyScreen(){
		//clientOne.handle("launch game ready screen");
		
		//assertEquals(true, clientOne.getGameScreenLaunched());
	}
	
//	@Test
//	public void personAddedAsPlayer(){
//		server.handle(clientOne.getID(), "joinGame name 1");
//		
//		assertEquals(1, server.getGame().getPlayersRegistered());
//	}
	
//	@Test
//	public void manyPeopleAddedAsPlayer(){
//		server.handle(clientOne.getID(), "joinGame vici 1");
//		server.handle(clientTwo.getID(), "joinGame sophia 2");
//		server.handle(clientThree.getID(), "joinGame alisa 2");
//		assertEquals(3, server.getGame().getPlayersRegistered());
//	}

	@Test
	public void firstPlayer(){
		//clientOne.handle("launch game ready screen");
	}
	
	@Test
	public void joinGameButtonPressed(){
		clientOne.handle("joinGame");
		assertEquals(true, clientOne.getJoinGame());
	}
	
	@Test
	public void gameReadyButtonPressed(){
		clientOne.handle("gameReady");
		assertEquals(true, clientOne.getGameReady());
	}
	

	
//	@Test
//	public void updateAllPlayersInfo(){
//		clientOne.handle("GAMEINFORMATION~");
//		assertEquals(true, clientOne.getUpdateAllPlayersInfo());
//	}
	
//	@Test
//	public void updateShowPlayerHand(){
//		clientOne.handle("PLAYERHAND~");
//		assertEquals(true, clientOne.getUpdateShowPlayerHand());
//	}
	
	@Test
	public void getPlayerActive(){
		clientOne.handle("PLAYERSPECIFICINFO~");
		assertEquals(true, clientOne.getPlayerActive());
	}
	@Test
	public void testSendingTournamentInfo(){
		clientOne.handle("TOURNAMENTINFO~");
		assertEquals(true, clientOne.getTournamentInfo());
	}
	
	@Test
	public void playerPlaysCardFromGui(){
		String msg = " ";
		clientOne.cardPlayed(msg);
		assertEquals(true, clientOne.getCardPlayed());
	}

	@Test
	public void requestToEndTurn(){
		clientOne.handle("requestToEndTurn");
		assertEquals(1, game.getCurrentPlayerNumber());
	}
	
	@Test
	public void requestToWithdraw(){
		clientOne.handle("requestToWithdraw");
		assertEquals(true, clientOne.getWithdraw());
	}
	
	@Test
	public void winnerFound(){
		clientTwo.handle("tournamentWinner~");
		assertEquals(true, clientTwo.getWinner());
	}
	@Test
	public void finalWinnerCheckTest(){
		clientOne.handle("finalWinnerCheck");
		assertEquals(true, clientOne.getFinalWinner());
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
