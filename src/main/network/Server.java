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

import logic.Game;
import logic.Player;

import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import java.io.*;

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
				serverThread.send("launch game ready screen\n");
				
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
			//game.readyScreen();		
		}
		//if(serverThreads.keySet().size() == game.getNumPlayers()){
		//	System.out.println("sending to showReadyScreen()");
		//	game.showReadyScreen();		
		//}
	}

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
				if (input.contains("drawToken")){
					game.getNextToken();
				}
				if (input.contains("join")) {
					String[] separated = input.split(" ");
//					game.addPlayer(separated[1], new Player(separated[1],
//							separated[2]));
//					if (game.isReadyForCommands()) {
//						displaySelectInstructions();
//					}
				} else if (input.contains("select")) {
//					game.parseCommand(input);
//					if (game.isReadyForRolls()) {
//						String message = String
//								.format("ROLL TO RESOLVE%s\nType the command \"roll <player name> <number from 1-6>\"\n",
//										"");
//						for (ServerThread to : serverThreads.values()) {
//							to.send(message);
//						}
//						logOutput(message);
//					}
				} else if (input.contains("roll")) {
					rolls.add(input);
				}
				broadcastToOtherPlayers(input, ID);

				if (rolls.size() == game.getNumPlayers()) {
					// All rolls and commands have been entered, ready for
					// round
					// to resolve
					String[] rollCommands = new String[rolls.size()];
					for (int i = 0; i < rolls.size(); i++) {
						rollCommands[i] = rolls.get(i);
					}
//					game.resolveRound(rollCommands);
					printOutcome();
					boolean endOfRound = false;
					String message = "";
					boolean gameOver = false;
					for (ServerThread to : serverThreads.values()) {
						message = "END OF ROUND!\n\n";
						to.send(message);
//						if (!game.isOver()) {
//							gameOver = true;
//						} else {
//							message = "GAME OVER!\n\n";
//							to.send(message);
//							endOfRound = true;
//							System.out
//									.println("Type \"shutdown\" to shutdown the server or "
//											+ " \"startGame <number of players> <number of rounds> to start a new game.\"");
//						}
					}
					if (gameOver) {
						displaySelectInstructions();
					}
					if (endOfRound) {
						logOutput(message);
					}

					rolls.clear();
					//game.resetForNewRound();
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

	public synchronized void broadcastToOtherPlayers(String message,
			int senderID) {
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
					} else {
						to.send(String.format("%5d: %s\n", senderID, message));
						logger.info(String
								.format("RECEIVED Received Message from %s:%d to %s:%d: %s",
										to.getSocketAddress(), to.getID(),
										from.getSocketAddress(), from.getID(),
										message));
						Trace.getInstance().logchat(this,
								serverThreads.get(senderID), from, message);
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

	private void printOutcome() {
//		String message = "OUTCOME OF ROUND " + (game.getCurrentRound() - 1)
//				+ ":\n";

//		for (Player player : game.getPlayers().values()) {
//			message += String.format("%8s - %2d wound(s)\n", player.getName(),
//					player.getWounds());
//		}
//		logOutput(message);
//		synchronized (serverThreadsLock) {
//			for (ServerThread to : serverThreads.values()) {
//				to.send(String.format(message));
//			}
//		}
	}

	public void displayGameStart(int numOfPlayers) {
		System.out.println("in display game start server");
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

}