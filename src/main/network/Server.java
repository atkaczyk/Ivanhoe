/**
 * 
 * @author Howard Scott Needham
 * @Version 1.0
 * 
 * 1: open a server side socket/port and establish a connection 	(Constructor)
 * 2:	start the servers main thread to listen for client request 	(start/run methods)
 * 3:	The run method is a thread that listens for client requests. When a client
 * 	requests a connection it starts a new server thread that is dedicated to the
 * 	client and is then registered in the clients hashmap - addThread () method
 * 4:	The handle method is called from the servers client thread to handle the incoming message
 * 	Notice how we filter the message - this is where you would plug in your rules engine etc .....
 * 
 * 	The server thread listens on the assigned port and when it receives a message it passes it
 * 	back to the server for processing. It is usually best practice to handle this in a seperate
 * 	class that is dedicated to handling/processing messages
 */

package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import logic.Game;
import logic.Player;
import utils.Config;
import utils.Trace;

public class Server implements Runnable {
	//private int maxNumClients = 5;
	int clientCount = 0;
	private Thread thread = null;
	private ServerSocket server = null;
	private HashMap<Integer, ServerThread> serverThreads;
	private static final Object serverThreadsLock = new Object();

	private Logger logger = Trace.getInstance().getLogger(this);
	private Game game = new Game();

	//maps server thread id to its corresponding game player number
	private Map<Integer, Integer> playerNumbers = new HashMap<Integer, Integer>();
	
	private List<String> rolls = new ArrayList<String>();

	public Server(int port) {
		try {
			/** Set up our message filter object */

			System.out
					.println("Binding to port " + port + ", please wait  ...");
			Trace.getInstance().write(this, "Binding to port " + port);
			logger.info("Binding to port " + port);
			/**
			 * I use a HashMap to keep track of the client connections and their
			 * related thread
			 */
			serverThreads = new HashMap<Integer, ServerThread>();

			/** Establish the servers main port that it will listen on */
			server = new ServerSocket(port);
			/**
			 * Allows a ServerSocket to bind to the same address without raising
			 * an "already bind exception"
			 */
			server.setReuseAddress(true);
			start();
		} catch (IOException ioe) {
			Trace.getInstance().exception(ioe);
			logger.fatal(ioe);
		}
	}

	/** Now we start the servers main thread */
	public void start() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
			Trace.getInstance().write(this, "Server started: " + server,
					thread.getId());
			logger.info(String.format("Server started: %s %d", server,
					thread.getId()));
		}
	}

	/** The main server thread starts and is listening for clients to connect */
	public void run() {
		while (thread != null) {
			try {
				Trace.getInstance().write(this, "Waiting for a client ...");
				logger.info("Waiting for a client ...");
				addThread(server.accept());
			} catch (IOException e) {
				Trace.getInstance().exception(this, e);
			}
		}
	}

	/**
	 * Client connection is accepted and now we need to handle it and register
	 * it and with the server | HashTable
	 **/
	private void addThread(Socket socket) {
		Trace.getInstance().write(this, "Client accepted", socket);
		logger.info(String.format("Client connected: %s", socket));
		if (clientCount < game.getNumPlayers()) {
			try {
				/** Create a separate server thread for each client */
				ServerThread serverThread = new ServerThread(this, socket);
				/** Open and start the thread */
				serverThread.open();
				serverThread.start();
				
				synchronized (serverThreadsLock) {
					serverThreads.put(serverThread.getID(), serverThread);
				}
				this.clientCount++;
			} catch (IOException e) {
				Trace.getInstance().exception(this, e);
			}
		} else {
			logger.info(String.format("Client Tried to connect: %s", socket));
			logger.info(String.format(
					"Client refused: maximum number of clients reached: d",
					game.getNumPlayers()));

			Trace.getInstance().write(this, "Client Tried to connect", socket);
			Trace.getInstance().write(this,
					"Client refused: maximum number of clients reached",
					game.getNumPlayers());
		}
		if(serverThreads.keySet().size() == game.getNumPlayers()){
			System.out.println();	
		}
	}

	/**
	 * Handle the information that gets sent to the Server from the Client
	 * 
	 * @param ID
	 *            The int client ID of the client sending the info
	 * @param input
	 * 			  The String input from the specified client ID
	 */
	public synchronized void handle(int ID, String input) {
		synchronized (serverThreadsLock) {
			if (input.equals("quit!")) {
				logger.info(String.format("Removing Client: %d", ID));
				Trace.getInstance().write(this, "Removing Client:", ID);

				if (serverThreads.containsKey(ID)) {
					serverThreads.get(ID).send("quit!" + "\n");
					remove(ID);
				}

			} else if (input.equals("shutdown!")) {
				shutdown();
			} else {
				//from client, get logic, send back to clients
				if (input.contains("drawToken")){
					int tokenColour = game.getNextToken();
					String msg = "drawToken," + tokenColour;
					broadcastMessageToPlayer(msg, ID, 0);
				}
				if (input.contains("joinGame")){
					String[] a = input.split(",");
					game.addPlayer(a[1], Integer.parseInt(a[2]));
					playerNumbers.put(ID, game.getPlayersRegistered()-1);
					
					// after adding a player, then game.isReadyToStart() then when all players have been added to the game
					if (game.isReadyToStart()){
						// call game.start() to initialize the deck and everything 
						game.startGame();
						for (int id: playerNumbers.keySet()){
							broadcastMessageToPlayer("openMainGameScreen", id, 1);
						}
					}					
				}
				if (input.contains("updateGameInformation")){
					update(ID); //call update function
					
					if (game.getCurrentPlayerNumber() == playerNumbers.get(ID)) {
						broadcastMessageToPlayer("launchTournamentColour", ID, 0);
					}
				}
				if (input.contains("TournamentColourRequest")){
					String[] tournamentC = input.split(":");
					int colour = Integer.parseInt(tournamentC[1]);
					boolean result = game.setTournamentColour(colour);
					if (result == false){
						broadcastMessageToPlayer("launchTournamentColour",ID,0);
					}
					if (result == true){
						broadcastToAllPlayers("setTournamentColour~"+colour); 
					}
				}
				if (input.contains("requestToPlayThisCard,")){
					String[] cardInfo = input.split(",");
					int playerNum = playerNumbers.get(ID); //gives the player number
					String result = game.playCard(playerNum, cardInfo[1]);
					if(result.contains("true")){
						getPlayerHand(ID);
						updateAll();
					}
					if(result.contains("false")){
						String[] msg = result.split(":");
						broadcastMessageToPlayer("ERROR~"+msg[1], ID, 0);
					}
					if(result.contains("moreInformationNeeded~")){
						broadcastMessageToPlayer(result, ID, 0);
					}
					if(result.contains("adaptNeedMoreInfo~")){
						//adaptNeedMoreInfo~playerNum-2@squire,green,blue+3@yellow,green#playerNum-2@squire,green,blue+3@yellow,green
						//broadcast separate message to each player
						String[] msgs = result.split("~");
						//playernum-1,3,4,5-#nameother-1,2,3-
						String[] msg = msgs[1].split("#");
						//playerNum-2@squire,green,blue+3@yellow,green#
					
						for (int m=0; m<msg.length; m++){
							String pNums[] = msg[m].split("-"); //get playernum before the -
							int pNum = Integer.parseInt(pNums[0]);
							//look in map to get matching pnum with id
							//send pNums[1] to specific ID
							for (int id: playerNumbers.keySet()){
								if (playerNumbers.get(id) == pNum){
									broadcastMessageToPlayer("adaptNeedMoreInfo~"+pNums[1], id, 1);
								}
							}
						}
						
					}
					if(result.contains("actionCardPlayedMessage")){
						broadcastToOtherPlayers(result, ID);
						updateAll();
					}
					if (result.contains("askForIvanhoe")) {
						for (int id: playerNumbers.keySet()){
							if (playerNumbers.get(id) == game.getPlayerWithIvanhoe()){
								broadcastMessageToPlayer(result, id, 1);
							}
						}
					}
					
				}
				else if (input.contains("Ivanhoe~")) {
					System.out.print("\n\n\n~~~~~~~~~~AHAHAHAHA"+input+"\n\n\n");
				}
				else if(input.contains("actionInfoGathered~")){
					String[] info = input.split("~");
					int playerNum = playerNumbers.get(ID); //gives the player number
					System.out.println("SERVER: actionInfoGathered~: "+info[1]);
					String result = game.playActionCardWithAdditionalInfo(playerNum, info[1]);
					if(result.contains("actionCardPlayedMessage")){
						broadcastToOtherPlayers(result, ID);
						updateAll();
					}
				}
				else if(input.contains("adaptGiveInfo@")){
					String[] cards = input.split("@");
					int playerNum = playerNumbers.get(ID); //gives the player number
					game.adaptCardsChosen(playerNum, cards[1]);
					updateAll();
		   		}
				else if (input.contains("requestToDrawCard")){
					int playerNum = playerNumbers.get(ID);
					game.drawCard(playerNum);
					update(ID); //call update function
				}
				else if (input.contains("requestToEndTurn")){
					game.goToNextPlayer();
					updateAll();
				}
				else if (input.contains("requestToWithdraw")){
					int playerNum = playerNumbers.get(ID); //gives the player number
					String result = game.withdrawPlayer(playerNum);
					
					//no one has won the tournament yet
					if(result.equals("")){
						//Ending turn
						updateAll();
					}
					
					//someone has won the tournament
					else{
						//first check to see if they are the winner of the whole game. USING final winner check.
						//REMAKE
						
						String fWinnerR = game.checkForWinner(); //returns name of winning player, and empty if no winner
						if(!(fWinnerR.equals(""))){
							// If there is a winner of the game
							broadcastToAllPlayers("gameWinner~"+fWinnerR);
						}
						//continue with the rest of the tournament win check
						else{
												
							//END REMAKE
												
							String nameOfWinner = result.split(",")[0];
							int playerW = 0;
							for (int num: playerNumbers.values()) {
								if (game.getPlayer(num).getName().equals(nameOfWinner)) {
									playerW = num;
									break;
								}
							}
						
							//if it was a purple tournament
							if(game.getTournamentColour() == Config.PURPLE){
								//send to client and ask to pick colour
								String colours = "PurpleWinTokenChoice~" + game.getTokensRemainingForPlayer(playerW);
								int winnerID = 0;
								for (int stID : playerNumbers.keySet()) {
									if (playerNumbers.get(stID) == playerW){
										winnerID = stID;
										break;
									}
								}					
								broadcastMessageToPlayer(colours, winnerID, 1);
							//if it was another colour tournament
							}else{
								game.addTokenToPlayer(playerW ,game.getTournamentColour());
								updateAll();
								broadcastToAllPlayers("tournamentWinner~"+result);
								
								int currID = 0;
								
								for (int serverThreadID : playerNumbers.keySet()) {
									if (playerNumbers.get(serverThreadID) == game.getCurrentPlayerNumber()){
										currID = serverThreadID;
										break;
									}
								}
								
								broadcastMessageToPlayer("launchTournamentColour", currID, 0);
								//handle(ID, "updateGameInformation"); //should send tournament colour pop-up to the correct person
							}
						}
					}
				}
				//the purple tournament win 
				else if(input.contains("PurpleWinTokenColourChoice~")){
					String[] choice = input.split("~");
					int playerNum = playerNumbers.get(ID); //gives the player number
					game.addTokenToPlayer(playerNum, Integer.parseInt(choice[1]));
					
					broadcastToAllPlayers("tournamentWinner~"+game.getPlayer(playerNumbers.get(ID)).getName() + "," + game.getTournamentNumber() + ","
							+ game.getTournamentColour());
					updateAll(); //maybe put this before the broadcast
				}
				else if (input.contains("finalWinnerCheck")){
					//String result = game.checkForWinner(); //returns name of winning player, and empty if no winner
					//if(result.equals("")){
						// If there is no winner yet, start a new tournament
						////updateAll();
						//handle(ID, "updateGameInformation");
					//}
					//else{
						//broadcastToAllPlayers("gameWinner~"+result);
					//}
				}
				if (input.contains("gameReady")){
					//String[] playerInfo = message.split(" ");
	   				//String name = playerInfo[1];
	   				//String tokenNumber = playerInfo[2];
	   				//game.addPlayer(name, tokenNumber);
	   				//check to see if game is ready using game.isReadyToStart()
				}
			}
		}
	}

	private void displaySelectInstructions() {
		String message = String
				.format("ENTER COMMANDS%s\nType the command \""
						+ "select <your player name> <target player> <attack> <attack speed> "
						+ "<defense> <defense speed> \"\n", "");
//		for (ServerThread to : serverThreads.values()) {
//			to.send("STARTING ROUND " + game.getCurrentRound() + "\n");
//
//			to.send(message);
//		}
//		logOutput("STARTING ROUND " + game.getCurrentRound() + "\n" + message);
	}

	/** Try and shutdown the client cleanly */
	public synchronized void remove(int ID) {
		synchronized (serverThreadsLock) {
			if (serverThreads.containsKey(ID)) {
				ServerThread toTerminate = serverThreads.get(ID);
				toTerminate.send("quit!" + "\n");
				serverThreads.remove(ID);
				clientCount--;

				toTerminate.close();
				toTerminate = null;
			}
		}
	}

	/** Shutdown the server cleanly */
	public void shutdown() {
		synchronized (serverThreadsLock) {
			Set<Integer> ids = serverThreads.keySet();

			if (thread != null) {
				thread = null;
			}

			try {

				for (Integer key : ids) {
					serverThreads.get(key).close();
					serverThreads.put(key, null);
				}
				serverThreads.clear();
				server.close();
			} catch (IOException e) {
				Trace.getInstance().exception(this, e);
			}

			logger.info(String.format("Server Shutdown cleanly %s", server));
			Trace.getInstance().write(this, "Server Shutdown cleanly", server);
			Trace.getInstance().close();
		}
	}

	public synchronized void logOutput(String message) {
		synchronized (serverThreadsLock) {
			for (ServerThread to : serverThreads.values()) {
				logger.info(String.format("SENT Message to %s:%d: %s",
						to.getSocketAddress(), to.getID(), message));
			}
		}
	}

	public synchronized void broadcastMessageToClients(String messageToOthers, String messageToSpecified, int senderID){
		synchronized (serverThreadsLock) {
			if (serverThreads.containsKey(senderID)) {
				ServerThread from = serverThreads.get(senderID);

				for (ServerThread to : serverThreads.values()) {
					if (to.getID() != senderID) {
						to.send(String.format("%5d: %s\n", senderID, messageToOthers));
						logger.info(String.format(
								"SENT Sending Message from %s:%d to %s:%d: %s",
								from.getSocketAddress(), from.getID(),
								to.getSocketAddress(), to.getID(), messageToOthers));
						Trace.getInstance().logchat(this,
								serverThreads.get(senderID), to, messageToOthers);
					} else {
						to.send(String.format("%5d: %s\n", senderID, messageToSpecified));
						logger.info(String
								.format("RECEIVED Received Message from %s:%d to %s:%d: %s",
										to.getSocketAddress(), to.getID(),
										from.getSocketAddress(), from.getID(),
										messageToSpecified));
						Trace.getInstance().logchat(this,
								serverThreads.get(senderID), from, messageToSpecified);
					}
				}
			}
		}
	}
	
	
	//if direction is 0 then the server received that message from the given client
	//if direction is 1 then the server is sending that message to the given client
	public synchronized void broadcastMessageToPlayer(String message, int senderID, int direction) {
		synchronized (serverThreadsLock) {
			if (serverThreads.containsKey(senderID)) {
				ServerThread current = serverThreads.get(senderID);
				current.send(String.format("%s\n", message));
				if (direction == 0){
					logger.info(String
							.format("SERVER RECIEVED MESSAGE FROM %s %d: %s",
									current.getSocketAddress(), current.getID(),
									message));
				}
				if (direction == 1){
					logger.info(String
							.format("SERVER SENDING MESSAGE TO CLIENT %s %d: %s",
									current.getSocketAddress(), current.getID(),
									message));
				}
				
				Trace.getInstance().logchat(this, serverThreads.get(senderID), current, message);
				//current.send(String.format("%5d: %s\n", senderID, message));
			}
		}
	}
	
	
	//broadcast a message to all players
	public synchronized void broadcastToAllPlayers(String message){
		synchronized (serverThreadsLock) {
			for (ServerThread to : serverThreads.values()) {
				to.send(String.format("%s\n", message));
				logger.info(String.format("SERVER BROADCASTING MESSAGE TO ALL PLAYERS: %s", message));
				Trace.getInstance().logchatToAll(this, message);
			}		
		}
	}
	
	
	public synchronized void broadcastToOtherPlayers(String message, int senderID) {
		synchronized (serverThreadsLock) {
			if (serverThreads.containsKey(senderID)) {
				ServerThread from = serverThreads.get(senderID);

				for (ServerThread to : serverThreads.values()) {
					if (to.getID() != senderID) {
						to.send(String.format("%5d: %s\n", senderID, message));
						logger.info(String.format(
								"SENT Sending Message from %s:%d to %s:%d: %s",
								from.getSocketAddress(), from.getID(),
								to.getSocketAddress(), to.getID(), message));
						Trace.getInstance().logchat(this,
								serverThreads.get(senderID), to, message);
					} 
				}
			}
		}
	}

	public void resetGame() {
		game = new Game();
	}

	public Game getGame() {
		return game;
	}

	public List<String> getRolls() {
		return rolls;
	}

	public void displayGameStart(int numOfPlayers) {
		synchronized (serverThreadsLock) {
			String message = "STARTING THE GAME! " + game.getNumPlayers()
					+ " players \n";
			for (ServerThread to : serverThreads.values()) {
				to.send(message);
				System.out.println(message);
			}
			logOutput(message);
		}
	}
	
	
	public void update(int ID){
		//update the game info for all players
		String gameInfo = getAllPlayerInfo();
		broadcastToAllPlayers(gameInfo);
		
		//update the player hand for the specific player
		getPlayerHand(ID);
		playerSpecificInfo(ID);
		getTournamentInfo(ID);
	}
	
	// | will be before each player's information
	public String getAllPlayerInfo() {
		String result = "PLAYERINFORMATION~";
		
		for (int i=0; i<game.getNumPlayers(); i++) {
			result+="@"+getPlayerInfo(game.getPlayer(i));
			result+="#"+getPlayerDisplayCards(game.getPlayer(i));
		}
		return result;
	}
		
	// , will separate information
	public String getPlayerInfo(Player p){
		String result = "";
		
		result += p.getName() + ",";

		List<Integer> tokens = p.getTokens();
		for (int colour=0; colour<=4; colour++) {
			if (tokens.contains(colour)) {
				result += colour;
			}
		}
		result += ",";
		
		result += p.hasSpecialCard("Shield") + ",";
		result += p.hasSpecialCard("Stunned") + ",";
		result += p.hasSpecialCard("Ivanhoe") + ",";
		
		result += p.getDisplayTotal(game.getTournamentColour()) + ",";
		
		result += p.isWithdrawn() + ",";
		
		if(game.getCurrentPlayer().getName().equals(p.getName())){
			result += true;
		}else{
			result += false;
		}
		
		return result;
	}
	
	public void getPlayerHand(int ID){
		String handInfo = "";
		
		for (int id: playerNumbers.keySet()){
			if (id == ID){
				handInfo = "PLAYERHAND~";
				handInfo += getPlayerHandCards(game.getPlayer(playerNumbers.get(id)));
				broadcastMessageToPlayer(handInfo, id, 1);
			}
		}
	}
	
	public void playerSpecificInfo(int ID){	
		for (int id: playerNumbers.keySet()){
			int playerNumber = playerNumbers.get(id);
			String playerSI = "PLAYERSPECIFICINFO~";
			playerSI += (game.getCurrentPlayerNumber() == playerNumber) + "," + game.getPlayer(playerNumber).getName();
			broadcastMessageToPlayer(playerSI, id, 1);	
		}
	}
	
	public void getTournamentInfo(int ID){
		String tournamentInfo = "TOURNAMENTINFO~";
		tournamentInfo += game.getTournamentColour()+","; //tournament colour
		tournamentInfo += game.getTournamentNumber(); //tournament number
		broadcastToAllPlayers(tournamentInfo);
	}
	
	public String getPlayerDisplayCards(Player p){
		String result = "";
		result += p.getDisplayAsString();
		return result;
	}
	
	public String getPlayerHandCards(Player p){
		String result = "";
		result += p.getHandAsString();
		return result;
	}
	
	public void updateAll() {
		for (int id: playerNumbers.keySet()) {
			update(id);
		}
	}
	
	public void actionCards(String msg){
		System.out.println("MoreInfo Action Cards: "+msg);
		if (msg.contains("BreakLance")){
			
		}else if(msg.contains("Riposte")){
			
		}else if(msg.contains("Dodge")){
			
		}else if(msg.contains("Retreat")){
			
		}else if(msg.contains("KnockDown")){
			
		}else if(msg.contains("Outmaneuver")){
			
		}else if(msg.contains("Charge")){
			
		}else if(msg.contains("Countercharge")){
			
		}else if(msg.contains("Disgrace")){
			
		}else if(msg.contains("Adapt")){
			
		}else if(msg.contains("Outwit")){
			
		}
	}
}