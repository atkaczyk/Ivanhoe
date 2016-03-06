/*
 * The commented out tests are tests that were used and passing before the GUI was created.
 * In these tests they now involve opening gui interfaces, which JUnit cannot do.
 * Howard says it is acceptable to just show the client to server tests.
 */

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
//	public void lauchGameReadyScreen(){
//		clientOne.handle("launch game ready screen");
//		assertEquals(true, clientOne.getGameScreenLaunched());
//	}

	@Test
	public void tokenRequestedByClientToServer(){
		clientOne.handle("tokenRequest");
		assertEquals(true, clientOne.getTokenRequest());
	}
	
	@Test
	public void drawTokenFromPool(){
		server.handle(clientOne.getID(), "drawToken");
		assertEquals(1, server.getGame().getTokensPicked());
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
	
	@Test
	public void sendingMessageToServerToUpdateAllGameInfo(){
		clientOne.handle("updateGameInformation");
		assertEquals(true, clientOne.getUpdateGameInfo());
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
	
//	@Test
//	public void getPlayerActive(){
//		clientOne.handle("PLAYERSPECIFICINFO~");
//		assertEquals(true, clientOne.getPlayerActive());
//	}
	
//	@Test
//	public void testSendingTournamentInfo(){
//		clientOne.handle("TOURNAMENTINFO~");
//		assertEquals(true, clientOne.getTournamentInfo());
//	}
	
	@Test
	public void tournamentColourIsRequestedByPLayer(){
		clientOne.handle("TournamentColourRequest");
		assertEquals(true, clientOne.getTournamentColourRequest());
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
	
//	@Test
//	public void winnerFound(){
//		clientTwo.handle("tournamentWinner~");
//		assertEquals(true, clientTwo.getWinner());
//	}
	
	@Test
	public void finalWinnerCheckTest(){
		clientOne.handle("finalWinnerCheck");
		assertEquals(true, clientOne.getFinalWinner());
	}
		
	@After
	public void tearDown() {
		server.resetGame();
	}

}
